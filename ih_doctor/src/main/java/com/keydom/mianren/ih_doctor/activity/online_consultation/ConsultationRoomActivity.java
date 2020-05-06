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
import android.view.View;
import android.widget.LinearLayout;

import com.keydom.ih_common.avchatkit.teamavchat.activity.TeamAVChatFragment;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationRoomController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.fragment.ConsultationAdviceFragment;
import com.keydom.mianren.ih_doctor.activity.online_consultation.fragment.ConsultationInfoFragment;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationRoomView;
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

    private String orderId, applyId,recordId;

    /**
     * 启动会诊订单列表页面
     */
    public static void start(Context context, String orderId, String applyId,String recordId) {
        Intent intent = new Intent(context, ConsultationRoomActivity.class);
        intent.putExtra(Const.ORDER_ID, orderId);
        intent.putExtra("applyId", applyId);
        intent.putExtra(Const.RECORD_ID, recordId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_consultation_room;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        orderId = getIntent().getStringExtra(Const.ORDER_ID);
        applyId = getIntent().getStringExtra("applyId");
        recordId = getIntent().getStringExtra(Const.RECORD_ID);

        LinearLayout linearLayout = (LinearLayout) consultationRoomTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
        initOrderListFragment();

        if (TextUtils.equals(applyId, String.valueOf(MyApplication.userInfo.getId()))) {
            setRightTxt(getString(R.string.txt_exit_consultation));
            setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
                @Override
                public void OnRightTextClick(View v) {
                    ToastUtil.showMessage(ConsultationRoomActivity.this, "退出");
                }
            });
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
