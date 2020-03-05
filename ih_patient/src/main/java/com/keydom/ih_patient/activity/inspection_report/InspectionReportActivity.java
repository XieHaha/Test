package com.keydom.ih_patient.activity.inspection_report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.inspection_report.controller.InspectionReportController;
import com.keydom.ih_patient.activity.inspection_report.view.InspectionReportView;
import com.keydom.ih_patient.activity.login.LoginActivity;
import com.keydom.ih_patient.adapter.ExaReportCardPopupWindowAdapter;
import com.keydom.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.fragment.InspectionReportFragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LiuJie
 * @Date: 2019/3/4 0004
 * @Desc: 报告查询页面
 */
public class InspectionReportActivity extends BaseControllerActivity<InspectionReportController> implements InspectionReportView {

    /**
     * 启动方法
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, InspectionReportActivity.class));
    }

    private TextView choose_patient_tv;
    private LinearLayout back_layout;
    private TabLayout inspection_report_tab;
    private ViewPager inspection_report_vp;
    private ViewPagerAdapter viewPagerAdapter;
    private FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private PopupWindow cardpopupWindow;
    private List<MedicalCardInfo> cardList = new ArrayList<>();
    //选中的就诊卡卡号
    private String selectedCardNum = "";
    private List<GeneralCallback.ExaReportActivityListener> listenerList = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_inspection_report_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        choose_patient_tv = this.findViewById(R.id.choose_patient_tv);
        choose_patient_tv.setOnClickListener(getController());
        back_layout = this.findViewById(R.id.back_layout);
        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        inspection_report_tab = this.findViewById(R.id.inspection_report_tab);
        inspection_report_vp = this.findViewById(R.id.inspection_report_vp);
        list.add("检查");
        list.add("检验");
        fm = getSupportFragmentManager();
        InspectionReportFragment bodyCheckReportFragment = new InspectionReportFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", Type.BODYCHECKTYPE);
        bodyCheckReportFragment.setArguments(bundle);
        fragmentList.add(bodyCheckReportFragment);
        InspectionReportFragment inspectionReportFragment = new InspectionReportFragment();
        Bundle bundle_f = new Bundle();
        bundle_f.putString("type", Type.INSPECTIONTYPE);
        inspectionReportFragment.setArguments(bundle_f);
        fragmentList.add(inspectionReportFragment);
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter(fm, fragmentList, list);
        }
        inspection_report_vp.setAdapter(viewPagerAdapter);
        inspection_report_tab.setupWithViewPager(inspection_report_vp);
        if (Global.getUserId() == -1) {
            new GeneralDialog(getContext(), "该功能需要登录才能使用，是否立即登录？", new GeneralDialog.OnCloseListener() {
                @Override
                public void onCommit() {
                    LoginActivity.start(getContext());
                }
            }, new GeneralDialog.CancelListener() {
                @Override
                public void onCancel() {
                    finish();
                }
            }).setTitle("提示").setCancel(false).setPositiveButton("登录").show();
        } else
            getController().fillData();
    }

    //弹出切换就诊卡的窗口
    @Override
    public void showCardPopupWindow() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.general_popupwindow_layout, null, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.popup_rv);
        ExaReportCardPopupWindowAdapter exaReportCardPopupWindowAdapter = new ExaReportCardPopupWindowAdapter(this, cardList);
        recyclerView.setAdapter(exaReportCardPopupWindowAdapter);
        cardpopupWindow = new PopupWindow(getContext(), null, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardpopupWindow.setContentView(view);
        cardpopupWindow.setFocusable(true);
        cardpopupWindow.setWidth(choose_patient_tv.getWidth());
        cardpopupWindow.showAsDropDown(choose_patient_tv);

    }

    @Override
    public void fillDataList(List<MedicalCardInfo> dataList) {
        if (dataList.size() != 0) {
            cardList.clear();
            cardList.addAll(dataList);
            choose_patient_tv.setText(dataList.get(0).getName());
            if (!choose_patient_tv.isClickable())
                choose_patient_tv.setClickable(true);
            selectedCardNum = dataList.get(0).getEleCardNumber();

        } else {
            choose_patient_tv.setText("无就诊卡");
            choose_patient_tv.setClickable(false);
        }
        for (GeneralCallback.ExaReportActivityListener listener : listenerList) {
            listener.refreshSelectedCard(selectedCardNum);
        }
    }

    @Override
    public void fillDataListFailed(String errMsg) {

    }

    /**
     * 保存选中的就诊卡
     */
    public void saveSelectCard(String eleCardNumber, String userName) {
        cardpopupWindow.dismiss();
        choose_patient_tv.setText(userName);
        selectedCardNum = eleCardNumber;
        for (GeneralCallback.ExaReportActivityListener listener : listenerList) {
            listener.refreshSelectedCard(selectedCardNum);
        }
    }

    /**
     * 添加监听
     */
    public void registerListener(GeneralCallback.ExaReportActivityListener listener) {
        listenerList.add(listener);
    }
}
