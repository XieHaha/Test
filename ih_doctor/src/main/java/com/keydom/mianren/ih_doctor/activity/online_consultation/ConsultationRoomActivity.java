package com.keydom.mianren.ih_doctor.activity.online_consultation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.keydom.ih_common.avchatkit.teamavchat.activity.TeamAVChatFragment;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationRoomController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.fragment.ConsultationAdviceFragment;
import com.keydom.mianren.ih_doctor.activity.online_consultation.fragment.ConsultationInfoFragment;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationRoomView;
import com.keydom.mianren.ih_doctor.bean.ConsultationDetailBean;
import com.keydom.mianren.ih_doctor.constant.Const;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

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

    private ConsultationDetailBean detailBean;
    private String orderId, applyId, recordId;

    /**
     * 启动会诊订单列表页面
     */
    public static void start(Context context, ConsultationDetailBean detailBean) {
        Intent intent = new Intent(context, ConsultationRoomActivity.class);
        intent.putExtra(Const.DATA, detailBean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_consultation_room;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        detailBean = (ConsultationDetailBean) getIntent().getSerializableExtra(Const.DATA);
        if (detailBean != null) {
            setTitle(detailBean.getPatientName());
            orderId = detailBean.getApplicationId();
            applyId = detailBean.getApplyDoctor().getId();
            recordId = detailBean.getRecordId();
        }

        LinearLayout linearLayout = (LinearLayout) consultationRoomTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
        initOrderListFragment();

        if (TextUtils.equals(applyId, String.valueOf(MyApplication.userInfo.getId()))) {
            setRightTxt(getString(R.string.txt_exit_consultation));
            setRightBtnListener(v -> endConsultation());
        }
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
        mFragmentArrays[0] = ConsultationInfoFragment.newInstance(orderId);
        ArrayList<String> accounts = new ArrayList<>();
        accounts.add("00180c00003");
        mFragmentArrays[1] = TeamAVChatFragment.newInstance(false, "2812993693", accounts);
        mFragmentArrays[2] = ConsultationAdviceFragment.newInstance(recordId);
        consultationRoomViewPager.setOffscreenPageLimit(3);
        PagerAdapter pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        consultationRoomViewPager.setAdapter(pagerAdapter);
        consultationRoomTabLayout.setupWithViewPager(consultationRoomViewPager);
    }

    private void endConsultation() {
        new GeneralDialog(this, "结束会诊?",
                () -> getController().endConsultationOrder(recordId, "")).show();
    }

    @Override
    public void endConsultationSuccess() {
        finish();
    }

    @Override
    public void endConsultationFailed(String msg) {
        ToastUtil.showMessage(this, msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
}
