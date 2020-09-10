package com.keydom.mianren.ih_patient.activity.inspection_report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.inspection_report.controller.InspectionReportController;
import com.keydom.mianren.ih_patient.activity.inspection_report.view.InspectionReportView;
import com.keydom.mianren.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.fragment.InspectionReportFragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LiuJie
 * @Date: 2019/3/4 0004
 * @Desc: 报告查询页面
 */
public class InspectionReportActivity extends BaseControllerActivity<InspectionReportController> implements InspectionReportView {
    private TextView choose_patient_tv;
    private LinearLayout back_layout;
    private TabLayout inspection_report_tab;
    private ViewPager inspection_report_vp;
    private ViewPagerAdapter viewPagerAdapter;
    private FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private int type = -1;

    private MedicalCardInfo cardInfo;

    /**
     * 启动方法
     */
    public static void start(Context context, MedicalCardInfo info, int type) {
        Intent intent = new Intent(context, InspectionReportActivity.class);
        intent.putExtra(Const.TYPE, type);
        intent.putExtra(Const.DATA, info);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_inspection_report_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getIntent().getIntExtra(Const.TYPE, -1);
        cardInfo = (MedicalCardInfo) getIntent().getSerializableExtra(Const.DATA);

        choose_patient_tv = this.findViewById(R.id.choose_patient_tv);
        back_layout = this.findViewById(R.id.back_layout);
        back_layout.setOnClickListener(view -> finish());
        inspection_report_tab = this.findViewById(R.id.inspection_report_tab);
        inspection_report_vp = this.findViewById(R.id.inspection_report_vp);
        switch (type) {
            case Type.BODYCHECKTYPE:
                setTitle("检查报告");
                createCheckFragment();
                break;
            case Type.INSPECTIONTYPE:
                setTitle("检验报告");
                createExamineFragment();
                break;
            default:
                setTitle("检验检查报告");
                createCheckFragment();
                createExamineFragment();
                list.add("检查");
                list.add("检验");
                break;
        }
        fm = getSupportFragmentManager();
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter(fm, fragmentList, list);
        }
        inspection_report_vp.setAdapter(viewPagerAdapter);
        inspection_report_tab.setupWithViewPager(inspection_report_vp);
    }

    /**
     * 检查  碎片
     */
    private void createCheckFragment() {
        InspectionReportFragment bodyCheckReportFragment = new InspectionReportFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Const.TYPE, Type.BODYCHECKTYPE);
        bundle.putSerializable(Const.DATA, cardInfo);
        bodyCheckReportFragment.setArguments(bundle);
        fragmentList.add(bodyCheckReportFragment);
    }

    /**
     * 检验  碎片
     */
    private void createExamineFragment() {
        InspectionReportFragment inspectionReportFragment = new InspectionReportFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Const.TYPE, Type.INSPECTIONTYPE);
        bundle.putSerializable(Const.DATA, cardInfo);
        inspectionReportFragment.setArguments(bundle);
        fragmentList.add(inspectionReportFragment);
    }
}
