package com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.controller.ObstetricHospitalController;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.fragment.ObstetricHospitalFragment;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.view.ObstetricHospitalView;
import com.keydom.mianren.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.mianren.ih_patient.constant.Type;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 产科住院记录
 */
public class ObstetricHospitalListActivity extends BaseControllerActivity<ObstetricHospitalController> implements ObstetricHospitalView {
    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, ObstetricHospitalListActivity.class));
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
        setTitle(getString(R.string.txt_obstetric_hospital_reserve));
        registration_record_tab = this.findViewById(R.id.registration_record_tab);
        registration_record_vp = this.findViewById(R.id.registration_record_vp);
        list.add("未住院");
        list.add("已住院");
        fm = getSupportFragmentManager();
        ObstetricHospitalFragment notHospitalized = new ObstetricHospitalFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", Type.NOTHOSPITALIZED);
        notHospitalized.setArguments(bundle);
        fragmentList.add(notHospitalized);

        ObstetricHospitalFragment hospitalized = new ObstetricHospitalFragment();
        Bundle bundle_f = new Bundle();
        bundle_f.putString("type", Type.HOSPITALIZED);
        hospitalized.setArguments(bundle_f);
        fragmentList.add(hospitalized);
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter(fm, fragmentList, list);
        }
        registration_record_vp.setAdapter(viewPagerAdapter);
        registration_record_tab.setupWithViewPager(registration_record_vp);

    }
}
