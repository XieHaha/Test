package com.keydom.mianren.ih_doctor.activity.online_consultation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.keydom.ih_common.avchatkit.TeamAVChatProfile;
import com.keydom.ih_common.avchatkit.teamavchat.activity.TeamAVChatFragment;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.MessageEvent;
import com.keydom.ih_common.bean.VoiceBean;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.ConsultationApplyDialog;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationRoomController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.fragment.ConsultationAdviceFragment;
import com.keydom.mianren.ih_doctor.activity.online_consultation.fragment.ConsultationInfoFragment;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationRoomView;
import com.keydom.mianren.ih_doctor.bean.AuditInfoBean;
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
 * @date 4???2??? 15:03
 * ?????????
 */
public class ConsultationRoomActivity extends BaseControllerActivity<ConsultationRoomController> implements ConsultationRoomView {
    @BindView(R.id.consultation_room_tab_layout)
    TabLayout consultationRoomTabLayout;
    @BindView(R.id.consultation_room_view_pager)
    ViewPager consultationRoomViewPager;
    private Fragment[] mFragmentArrays;
    private String[] mTabTitles;

    private ConsultationBean consultationBean;
    /**
     * ??????????????????moudle
     */
    private VoiceBean voiceBean;
    private String orderId, applyId, recordId, tid, inquiryId;

    private ArrayList<String> doctorCodes = new ArrayList<>();
    private ArrayList<AuditInfoBean> auditInfoBeans;

    /**
     * ????????????
     */
    private String applyReason;

    /**
     * ??????????????????
     */
    private boolean isApply;
    /**
     * ???????????????????????????
     */
    private boolean outConsultationDoctor;
    /**
     * ????????????????????????
     */
    private int position;

    /**
     * ????????????
     */
    public static final int REQUEST_CODE_END = 100;

    /**
     * ??????????????? ??????????????????
     */
    public static void start(Context context, ConsultationDetailBean detailBean) {
        Intent intent = new Intent(context, ConsultationRoomActivity.class);
        intent.putExtra(Const.DATA, detailBean);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_END);
    }

    /**
     * ??????????????? ??????????????????
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

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    dealConsultationApply((AuditInfoBean) msg.obj);
                    break;
                case 1:
                    setRightTxt("");
                    //????????????????????????
                    ((TeamAVChatFragment) mFragmentArrays[1]).setOutConsultationDoctor(false);
                    ((ConsultationAdviceFragment) mFragmentArrays[2]).setOutConsultationDoctor(false);
                    ToastUtil.showMessage(ConsultationRoomActivity.this, "????????????????????????");
                    break;
                default:
                    break;
            }
            return true;
        }
    });

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

        //?????????????????????
        TeamAVChatProfile.sharedInstance().setTeamAVChatting(false);

        LinearLayout linearLayout = (LinearLayout) consultationRoomTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));

        //???????????????????????????
        if (consultationBean != null && consultationBean.getDoctorCode() != null) {
            doctorCodes = consultationBean.getDoctorCode();
        }

        if (TextUtils.equals(applyId, String.valueOf(MyApplication.userInfo.getId()))) {
            isApply = true;
            setRightTxt(getString(R.string.txt_exit_consultation));
            setRightBtnListener(v -> endConsultationDialog());
        } else {
            if (!doctorCodes.contains(AVChatKit.getAccount().toUpperCase())) {
                outConsultationDoctor = true;
                setRightTxt(getString(R.string.txt_add_consultation));
                setRightBtnListener(v -> applyJoinConsultationDialog());
            } else {
                outConsultationDoctor = false;
            }
        }
        initOrderListFragment();
        //?????????????????????????????????
        if (isApply) {
            initApplyDoctors();
        }
    }


    /**
     * ??????fragment
     */
    private void initOrderListFragment() {
        mTabTitles = new String[3];
        mFragmentArrays = new Fragment[3];
        mTabTitles[0] = "????????????";
        mTabTitles[1] = "?????????";
        mTabTitles[2] = "????????????";
        consultationRoomTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mFragmentArrays[0] = ConsultationInfoFragment.newInstance(orderId, inquiryId);
        mFragmentArrays[1] = TeamAVChatFragment.newInstance(false, tid, getAccounts(),
                isApply, outConsultationDoctor);
        //        mFragmentArrays[1] = ConversationFragment.newInstance(orderId, tid);
        mFragmentArrays[2] = ConsultationAdviceFragment.newInstance(recordId,
                outConsultationDoctor);
        consultationRoomViewPager.setOffscreenPageLimit(3);
        PagerAdapter pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        consultationRoomViewPager.setAdapter(pagerAdapter);
        consultationRoomTabLayout.setupWithViewPager(consultationRoomViewPager);
    }

    /**
     * ?????????????????????
     */
    private void initApplyDoctors() {
        auditInfoBeans = new ArrayList<>();
        ArrayList<AuditInfoBean> list = consultationBean.getAuditInfo();
        if (list != null && list.size() > 0) {
            for (AuditInfoBean bean : list) {
                if (bean.getStatus() == 0) {
                    auditInfoBeans.add(bean);
                }
            }
        }

        if (auditInfoBeans.size() > 0) {
            position = 0;
            dealConsultationApply(auditInfoBeans.get(0));
        }
    }

    private ArrayList<String> getAccounts() {
        ArrayList<String> accounts = new ArrayList<>();
        for (String doctorCode : doctorCodes) {
            if (doctorCode.equalsIgnoreCase(AVChatKit.getAccount())) {
                continue;
            }
            accounts.add(doctorCode.toLowerCase());
        }
        return accounts;
    }

    /**
     * ?????????????????????????????????
     */
    private void endConsultationDialog() {
        new GeneralDialog(this, "?????????????",
                () -> getController().endConsultationOrder(recordId, "")).show();
    }

    /**
     * ?????????????????????????????????????????????
     */
    private void applyJoinConsultationDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        View view = getLayoutInflater().inflate(R.layout.dialog_apply_consultation, null, false);
        InterceptorEditText reasonEt = view.findViewById(R.id.dialog_apply_edit);
        view.findViewById(R.id.dialog_apply_close).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.dialog_apply_cancel).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.dialog_apply_sure).setOnClickListener(v -> {
            applyReason = reasonEt.getText().toString();
            if (TextUtils.isEmpty(applyReason)) {
                ToastUtil.showMessage(ConsultationRoomActivity.this, "????????????????????????");
                return;
            }
            getController().applyJoinConsultation();
            dialog.dismiss();
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private void dealConsultationApply(AuditInfoBean bean) {
        new ConsultationApplyDialog(this, bean.getApplyDoctorName(), bean.getPatientName(),
                bean.getApplyReason(),
                () -> getController().dealConsultationApply(true, bean),
                () -> getController().dealConsultationApply(false, bean)).show();
    }

    @Override
    public void endConsultationSuccess() {
        //????????????????????????
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
        new GeneralDialog(this, "????????????????????????????????????").setNegativeButtonIsGone(true).show();
    }

    @Override
    public void applyJoinConsultationFailed(String msg) {
        ToastUtil.showMessage(this, msg);
    }

    @Override
    public void dealConsultationApplySuccess(String doctorCode) {
        //????????????????????????
        EventBus.getDefault().post(new Event(EventType.UPDATECONSULTATION, null));
        if (!TextUtils.isEmpty(doctorCode)) {
            //????????????????????????
            ((TeamAVChatFragment) mFragmentArrays[1]).addNewDoctor(doctorCode);
        }
        ToastUtil.showMessage(this, "????????????");
        position++;
        if (auditInfoBeans.size() > position) {
            dealConsultationApply(auditInfoBeans.get(position));
        }
    }

    @Override
    public Map<String, String> getApplyParams() {
        Map<String, String> params = new HashMap<>();
        params.put("applyDoctorId", String.valueOf(MyApplication.userInfo.getId()));
        params.put("applyReason", applyReason);
        params.put("mdtApplicationId", orderId);
        return params;
    }

    @Override
    public Map<String, Object> getUploadVoiceParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("recordId", recordId);
        params.put("createTime", System.currentTimeMillis());
        params.put("duration", voiceBean == null ? 0 : voiceBean.getDuration());
        return params;
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
     * ????????????????????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == com.keydom.ih_common.constant.EventType.FILE) {
            voiceBean = (VoiceBean) messageEvent.getData();
            if (voiceBean != null) {
                File file = new File(voiceBean.getUrl());
                getController().uploadVoiceFile(file);
            }
        }
    }

    /**
     * ??????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void applyJoin(MessageEvent messageEvent) {
        if (messageEvent.getType() == com.keydom.ih_common.constant.EventType.APPLY_JOIN_CONSULTATION) {
            applyJoinConsultationDialog();
        }
    }

    /**
     * ??????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveApply(MessageEvent messageEvent) {
        if (messageEvent.getType() == com.keydom.ih_common.constant.EventType.NOTIFY_APPLY_JOIN_CONSULTATION) {
            Message message = handler.obtainMessage();
            message.obj = messageEvent.getData();
            message.what = 0;
            handler.sendMessage(message);
        }
    }

    /**
     * ??????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void agreeApply(MessageEvent messageEvent) {
        if (messageEvent.getType() == com.keydom.ih_common.constant.EventType.NOTIFY_AGREE_JOIN_CONSULTATION) {
            Message message = handler.obtainMessage();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
