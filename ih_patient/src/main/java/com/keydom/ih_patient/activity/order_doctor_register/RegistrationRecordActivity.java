package com.keydom.ih_patient.activity.order_doctor_register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_doctor_register.controller.RegistrationRecordController;
import com.keydom.ih_patient.activity.order_doctor_register.view.RegistrationRecordView;
import com.keydom.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.fragment.RegisterRecordFragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约记录页面
 */
public class RegistrationRecordActivity extends BaseControllerActivity<RegistrationRecordController> implements RegistrationRecordView {

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, RegistrationRecordActivity.class));
    }
    private TabLayout registration_record_tab;
    private ViewPager registration_record_vp;
    private ViewPagerAdapter viewPagerAdapter;
    private FragmentManager fm;
    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> list=new ArrayList<>();
    @Override
    public int getLayoutRes() {
        return R.layout.activity_registration_record_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("挂号记录");
        registration_record_tab=this.findViewById(R.id.registration_record_tab);
        registration_record_vp=this.findViewById(R.id.registration_record_vp);
        list.add("未完成");
        list.add("已完成");
        fm=getSupportFragmentManager();
        RegisterRecordFragment doingRecordFragment=new RegisterRecordFragment();
        Bundle bundle=new Bundle();
        bundle.putString("type",Type.DOINGREGISTRATIONRECORD);
        doingRecordFragment.setArguments(bundle);
        fragmentList.add(doingRecordFragment);

        RegisterRecordFragment doneRecordFragment=new RegisterRecordFragment();
        Bundle bundle_f=new Bundle();
        bundle_f.putString("type",Type.DONEREGISTRATIONRECORD);
        doneRecordFragment.setArguments(bundle_f);
        fragmentList.add(doneRecordFragment);
        if(viewPagerAdapter==null){
            viewPagerAdapter=new ViewPagerAdapter(fm,fragmentList,list);
        }
        registration_record_vp.setAdapter(viewPagerAdapter);
        registration_record_tab.setupWithViewPager(registration_record_vp);

    }
}
