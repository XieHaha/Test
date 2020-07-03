package com.keydom.mianren.ih_patient.activity.order_doctor_register;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ShareUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.controller.DoctorIndexController;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.view.DoctorIndexView;
import com.keydom.mianren.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.mianren.ih_patient.fragment.DoctorDescripeFragment;
import com.keydom.mianren.ih_patient.fragment.DoctorSchedulFragment;
import com.keydom.mianren.ih_patient.utils.StatusBarUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 医生详情view
 */
public class DoctorIndexActivity extends BaseControllerActivity<DoctorIndexController> implements DoctorIndexView {
    /**
     * 启动
     */
    public static void start(Context context, DoctorInfo doctorInfo) {
        Intent intent = new Intent(context, DoctorIndexActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("doctorInfo", doctorInfo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 启动
     */
    public static void start(Context context, String doctorCode) {
        Intent intent = new Intent(context, DoctorIndexActivity.class);
        intent.putExtra("doctorCode", doctorCode);
        context.startActivity(intent);
    }

    private TextView doctor_name_tv, doctor_dsc_tv, doctor_online_tv, job_tv, dept_tv;
    private LinearLayout llRightComplete, llLeftGoBack;
    private RelativeLayout diagnoses_layout;
    private CircleImageView doctor_head_img;
    private ImageView doctor_qr_img;
    private TabLayout doctor_index_tab;
    private ViewPager doctor_index_vp;
    private ViewPagerAdapter viewPagerAdapter;
    private FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private DoctorInfo doctorInfo;
    private String doctorCode;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_doctor_index_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.other_login_color);

        doctorInfo = (DoctorInfo) getIntent().getSerializableExtra("doctorInfo");
        if (doctorInfo != null)
            doctorCode = doctorInfo.getUserCode();
        else
            doctorCode = getIntent().getStringExtra("doctorCode");
        doctor_name_tv = this.findViewById(R.id.doctor_name_tv);
        job_tv = findViewById(R.id.job_tv);
        dept_tv = findViewById(R.id.dept_tv);
        diagnoses_layout = findViewById(R.id.diagnoses_layout);
        doctor_dsc_tv = this.findViewById(R.id.doctor_dsc_tv);
        doctor_online_tv = this.findViewById(R.id.doctor_online_tv);

        llRightComplete = this.findViewById(R.id.llRightComplete);
        llRightComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.showShareUtils(getContext(), new ShareUtils.IOnShareCallBack() {
                    @Override
                    public void onShareSelect(int type) {

                    }
                });
            }
        });
        llLeftGoBack = this.findViewById(R.id.llLeftGoBack);
        llLeftGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        doctor_head_img = this.findViewById(R.id.doctor_head_img);
        doctor_qr_img = this.findViewById(R.id.doctor_qr_img);
        doctor_index_tab = this.findViewById(R.id.doctor_index_tab);
        doctor_index_vp = this.findViewById(R.id.doctor_index_vp);
        list.add("门诊排班");
        list.add("医生简介");
        fm = getSupportFragmentManager();
        if (doctorInfo == null) {
            getController().getDoctorDetail(doctorCode);
        } else {
            loadData();
        }
    }

    /**
     * 加载初始化数据
     */
    private void loadData() {

        DoctorSchedulFragment doctorSchedulFragment = new DoctorSchedulFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("deptId", doctorInfo.getDeptId());
        bundle.putLong("deptUserId", doctorInfo.getId());
        bundle.putString("doctorName", doctorInfo.getName());
        bundle.putLong("doctorId", doctorInfo.getId());
        doctorSchedulFragment.setArguments(bundle);
        DoctorDescripeFragment doctorDescripeFragment = new DoctorDescripeFragment();
        Bundle bundle_f = new Bundle();
        bundle_f.putString("doctorDescripe", doctorInfo.getIntro());
        doctorDescripeFragment.setArguments(bundle_f);
        fragmentList.add(doctorSchedulFragment);
        fragmentList.add(doctorDescripeFragment);
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter(fm, fragmentList, list);
        }
        doctor_index_vp.setAdapter(viewPagerAdapter);
        doctor_index_tab.setupWithViewPager(doctor_index_vp);

        doctor_name_tv.setText(doctorInfo.getName());
        doctor_dsc_tv.setText(doctorInfo.getAdept());
        if (doctorInfo.getImage() != null && !"".equals(doctorInfo.getImage())) {
            Glide.with(getContext()).load(Const.IMAGE_HOST + doctorInfo.getImage()).into(doctor_head_img);
            GlideUtils.load(doctor_head_img, Const.IMAGE_HOST + doctorInfo.getImage(), 0, R.mipmap.test_doctor_head_icon, false, null);
        }
        if (doctorInfo.getQrcode() != null && !"".equals(doctorInfo.getQrcode())) {
            Bitmap image = CodeUtils.createImage(doctorInfo.getQrcode(), 400, 400, null);
            doctor_qr_img.setImageBitmap(image);
        }
        if (doctorInfo.getJobTitleName() != null && !"".equals(doctorInfo.getJobTitleName())) {
            job_tv.setText(doctorInfo.getJobTitleName());
        } else {
            job_tv.setVisibility(View.GONE);
        }
        if (doctorInfo.getDeptName() != null && !"".equals(doctorInfo.getDeptName())) {
            dept_tv.setText(doctorInfo.getDeptName());
        } else {
            dept_tv.setVisibility(View.GONE);
        }

        if (doctorInfo.getIsInterrogation() == 1){
            doctor_online_tv.setBackgroundResource(R.drawable.doctor_online_chat_bg);
            doctor_online_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DoctorOrNurseDetailActivity.startDoctorPage(getContext(), 0, doctorCode);
                }
            });
        }
        else{
            doctor_online_tv.setOnClickListener(null);
            doctor_online_tv.setBackgroundResource(R.drawable.doctor_unline_chat_bg);


        }

    }

    @Override
    public void getDoctorDetailSuccess(DoctorInfo doctorInfo) {
        this.doctorInfo = doctorInfo;
        loadData();
    }

    @Override
    public void getDoctorDetailFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "医生数据获取失败" + errMsg);
    }
}
