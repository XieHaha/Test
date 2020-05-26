package com.keydom.mianren.ih_doctor.activity.online_consultation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.keydom.ih_common.avchatkit.AVChatKit;
import com.keydom.ih_common.avchatkit.teamavchat.activity.TeamAVChatFragment;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.MessageEvent;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationRoomController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.fragment.ConsultationAdviceFragment;
import com.keydom.mianren.ih_doctor.activity.online_consultation.fragment.ConsultationInfoFragment;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationRoomView;
import com.keydom.mianren.ih_doctor.bean.ConsultationBean;
import com.keydom.mianren.ih_doctor.bean.ConsultationDetailBean;
import com.keydom.mianren.ih_doctor.bean.Event;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @date 4月2日 15:03
 * 会诊室
 */
public class ConsultationRoomActivity extends BaseControllerActivity<ConsultationRoomController> implements ConsultationRoomView {
    @BindView(R.id.consultation_room_tab_layout)
    TabLayout consultationRoomTabLayout;
    @BindView(R.id.consultation_room_view_pager)
    ViewPager consultationRoomViewPager;
    private Fragment[] mFragmentArrays;
    private String[] mTabTitles;

    private ConsultationBean consultationBean;
    private String orderId, applyId, recordId, tid, inquiryId;

    private ArrayList<String> docotorCodes = new ArrayList<>();

    /**
     * 是否为发起人
     */
    private boolean isApply;
    /**
     * 还未加入的会诊医生
     */
    private boolean outConsultationDoctor;

    /**
     * 结束会诊
     */
    public static final int REQUEST_CODE_END = 100;

    /**
     * 启动会诊室 （接收页面）
     */
    public static void start(Context context, ConsultationDetailBean detailBean) {
        Intent intent = new Intent(context, ConsultationRoomActivity.class);
        intent.putExtra(Const.DATA, detailBean);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_END);
    }

    /**
     * 启动会诊室 （列表页面）
     */
    public static void start(Context context, ConsultationBean bean) {
        Intent intent = new Intent(context, ConsultationRoomActivity.class);
        intent.putExtra(Const.DATA_OTHER, bean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_consultation_room;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        consultationBean = (ConsultationBean) getIntent().getSerializableExtra(Const.DATA_OTHER);
        if (consultationBean != null) {
            setTitle(consultationBean.getPatientName());
            inquiryId = consultationBean.getUserOrderId();
            orderId = consultationBean.getApplicationId();
            applyId = consultationBean.getApplicantId();
            recordId = consultationBean.getRecordId();
            tid = consultationBean.getTid();
        }

        LinearLayout linearLayout = (LinearLayout) consultationRoomTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));

        //所有参与会诊的医生
        if (consultationBean != null && consultationBean.getDoctorCode() != null) {
            docotorCodes = consultationBean.getDoctorCode();
        }

        if (TextUtils.equals(applyId, String.valueOf(MyApplication.userInfo.getId()))) {
            isApply = true;
            setRightTxt(getString(R.string.txt_exit_consultation));
            setRightBtnListener(v -> endConsultation());
        } else {
            if (!docotorCodes.contains(AVChatKit.getAccount().toUpperCase())) {
                outConsultationDoctor = true;
                setRightTxt(getString(R.string.txt_add_consultation));
                setRightBtnListener(v -> applyJoinConsultation());
            }
        }
        initOrderListFragment();
    }


    /**
     * 设置fragment
     */
    private void initOrderListFragment() {
        mTabTitles = new String[3];
        mFragmentArrays = new Fragment[3];
        mTabTitles[0] = "病历资料";
        mTabTitles[1] = "会诊室";
        mTabTitles[2] = "会诊意见";
        consultationRoomTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mFragmentArrays[0] = ConsultationInfoFragment.newInstance(orderId, inquiryId);
        mFragmentArrays[1] = TeamAVChatFragment.newInstance(false, tid, getAccounts(),
                isApply);
        //        mFragmentArrays[1] = ConversationFragment.newInstance(orderId, tid);
        mFragmentArrays[2] = ConsultationAdviceFragment.newInstance(recordId);
        consultationRoomViewPager.setOffscreenPageLimit(3);
        PagerAdapter pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        consultationRoomViewPager.setAdapter(pagerAdapter);
        consultationRoomTabLayout.setupWithViewPager(consultationRoomViewPager);
    }

    private ArrayList<String> getAccounts() {
        ArrayList<String> accounts = new ArrayList<>();
        for (String doctorCode : docotorCodes) {
            if (doctorCode.equalsIgnoreCase(AVChatKit.getAccount())) {
                continue;
            }
            accounts.add(doctorCode.toLowerCase());
        }
        return accounts;
    }

    /**
     * 结束会诊（申请人权限）
     */
    private void endConsultation() {
        new GeneralDialog(this, "结束会诊?",
                () -> getController().endConsultationOrder(recordId, "")).show();
    }

    /**
     * 申请加入会诊（未在会诊中医生）
     */
    private void applyJoinConsultation() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        View view =getLayoutInflater().inflate(R.layout.item_consultation_order, null, false);
        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    public void endConsultationSuccess() {
        //通知列表更新数据
        EventBus.getDefault().post(new Event(EventType.UPDATECONSULTATION, null));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void endConsultationFailed(String msg) {
        ToastUtil.showMessage(this, msg);
    }

    @Override
    public void applyJoinConsultationSuccess() {
        new GeneralDialog(this, "您已提交申请，请等待审核").setNegativeButtonIsGone(true).show();
    }

    @Override
    public void applyJoinConsultationFailed(String msg) {
        ToastUtil.showMessage(this, msg);
    }

    @Override
    public Map<String, String> getApplyParams() {
        Map<String, String> params = new HashMap<>();
        params.put("applyDoctorId", String.valueOf(MyApplication.userInfo.getId()));
        params.put("applyReason", "原因");
        params.put("mdtApplicationId", orderId);
        return null;
    }

    final class TabViewPagerAdapter extends FragmentPagerAdapter {
        private TabViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }


        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }

    /**
     * 接收到会诊视频结束的音频
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == com.keydom.ih_common.constant.EventType.FILE) {
            File file = new File((String) messageEvent.getData());
            getController().uploadVoiceFile(file);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
