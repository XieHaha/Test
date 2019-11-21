package com.keydom.ih_doctor.activity.prescription_check;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.prescription_check.controller.PrescriptionCheckController;
import com.keydom.ih_doctor.activity.prescription_check.view.PrescriptionCheckView;
import com.keydom.ih_doctor.constant.ServiceConst;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.fragment.PrescriptionFragment;

import org.jetbrains.annotations.Nullable;

/**
 * @Name：com.keydom.ih_doctor.activity
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class PrescriptionCheckActivity extends BaseControllerActivity<PrescriptionCheckController> implements PrescriptionCheckView {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment[] mFragmentArrays;
    private String[] mTabTitles;
    private EditText searchInputEv;
    private TextView searchTv;
    private String startCode="";
    private LinearLayout order_num_layout;

    /**
     * 开启处方审核主页
     *
     * @param context
     */
    public static void start(Context context,String startCode) {

        Intent starter = new Intent(context, PrescriptionCheckActivity.class);
        starter.putExtra("startCode",startCode);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_prescription_check_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        startCode=getIntent().getStringExtra("startCode");
        order_num_layout=this.findViewById(R.id.order_num_layout);
        order_num_layout.setVisibility(View.GONE);
        tabLayout = this.findViewById(R.id.tablayout);
        viewPager = this.findViewById(R.id.tab_viewpager);
        searchInputEv = this.findViewById(R.id.search_input_ev);
        searchTv = this.findViewById(R.id.search_tv);
        searchTv.setOnClickListener(getController());
        if (ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE.equals(startCode)) {
            setTitle("处方查询");
            initDoctor();
        } else if (ServiceConst.MEDICINE_PRESCRIPTION_SERVICE_CODE.equals(startCode)) {
            setTitle("处方审核");
            initDrugControl();
        } else {
            ToastUtil.showMessage(this, "无该模块权限");
            finish();
        }

    }


    /**
     * 医生角色页面初始化
     */
    private void initDoctor() {
        PagerAdapter pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        mTabTitles = new String[3];
        mFragmentArrays = new Fragment[3];
        mTabTitles[0] = "未审核";
        mTabTitles[1] = "已退回";
        mTabTitles[2] = "已通过";
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mFragmentArrays[0] = PrescriptionFragment.newInstance(TypeEnum.CHECK_NOT_FINISH,startCode);
        mFragmentArrays[1] = PrescriptionFragment.newInstance(TypeEnum.CHECK_RETURN,startCode);
        mFragmentArrays[2] = PrescriptionFragment.newInstance(TypeEnum.CHECK_PASS,startCode);

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    /**
     * 药师角色页面初始化
     */
    private void initDrugControl() {
        mTabTitles = new String[4];
        mFragmentArrays = new Fragment[4];
        mTabTitles[0] = "未审核";
        mTabTitles[1] = "已审核";
        mTabTitles[2] = "已配送";
        mTabTitles[3] = "已过期";
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mFragmentArrays[0] = PrescriptionFragment.newInstance(TypeEnum.CHECK_NOT_FINISH,startCode);
        mFragmentArrays[1] = PrescriptionFragment.newInstance(TypeEnum.CHECK_FINISH,startCode);
        mFragmentArrays[2] = PrescriptionFragment.newInstance(TypeEnum.CHECK_SEND,startCode);
        mFragmentArrays[3] = PrescriptionFragment.newInstance(TypeEnum.CHECK_TIME_OUT,startCode);
        viewPager.setOffscreenPageLimit(4);
        PagerAdapter pagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public String getSearchValue() {
        return searchInputEv.getText().toString().trim();
    }


    final class TabViewPagerAdapter extends FragmentPagerAdapter {
        public TabViewPagerAdapter(FragmentManager fm) {
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
