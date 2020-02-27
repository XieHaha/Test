package com.keydom.ih_patient.activity.medical_mail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.medical_mail.controller.MedicalMailController;
import com.keydom.ih_patient.activity.medical_mail.fragment.MedicalMailOrderFragment;
import com.keydom.ih_patient.activity.medical_mail.view.MedicalMailView;
import com.keydom.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.ih_patient.constant.Type;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 病案邮寄订单
 */
public class MedicalMailListActivity extends BaseControllerActivity<MedicalMailController> implements MedicalMailView {
    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, MedicalMailListActivity.class));
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_medical_mail_order;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_medical_mail));
        tabLayout = this.findViewById(R.id.registration_record_tab);
        viewPager = this.findViewById(R.id.registration_record_vp);
        list.add("未邮寄");
        list.add("已邮寄");
        fm = getSupportFragmentManager();
        MedicalMailOrderFragment notHospitalized = new MedicalMailOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", Type.NOTHOSPITALIZED);
        notHospitalized.setArguments(bundle);
        fragmentList.add(notHospitalized);

        MedicalMailOrderFragment hospitalized = new MedicalMailOrderFragment();
        Bundle bundle_f = new Bundle();
        bundle_f.putString("type", Type.HOSPITALIZED);
        hospitalized.setArguments(bundle_f);
        fragmentList.add(hospitalized);
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter(fm, fragmentList, list);
        }
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
