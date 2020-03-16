package com.keydom.mianren.ih_patient.activity.reserve_examination;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_examination.controller.ExaminationReserveListController;
import com.keydom.mianren.ih_patient.activity.reserve_examination.fragment.ExaminationReserveFragment;
import com.keydom.mianren.ih_patient.activity.reserve_examination.view.ExaminationReserveListView;
import com.keydom.mianren.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.mianren.ih_patient.constant.Type;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 检验检查预约记录
 */
public class ExaminationReserveListActivity extends BaseControllerActivity<ExaminationReserveListController> implements ExaminationReserveListView {
    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, ExaminationReserveListActivity.class));
    }

    private TabLayout registration_record_tab;
    private ViewPager registration_record_vp;
    private ViewPagerAdapter viewPagerAdapter;
    private FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_registration_record_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_check_reserve));
        registration_record_tab = this.findViewById(R.id.registration_record_tab);
        registration_record_vp = this.findViewById(R.id.registration_record_vp);
        list.add("检验");
        list.add("检查");
        fm = getSupportFragmentManager();
        ExaminationReserveFragment notHospitalized = new ExaminationReserveFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", Type.INSPECT);
        notHospitalized.setArguments(bundle);
        fragmentList.add(notHospitalized);

        ExaminationReserveFragment hospitalized = new ExaminationReserveFragment();
        Bundle bundle_f = new Bundle();
        bundle_f.putString("type", Type.EXAMINATION);
        hospitalized.setArguments(bundle_f);
        fragmentList.add(hospitalized);
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter(fm, fragmentList, list);
        }
        registration_record_vp.setAdapter(viewPagerAdapter);
        registration_record_tab.setupWithViewPager(registration_record_vp);

    }
}
