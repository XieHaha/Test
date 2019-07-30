package com.keydom.ih_doctor.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.adapter.PatientViewPagerAdapter;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.fragment.controller.PatientManageFragmentController;
import com.keydom.ih_doctor.fragment.view.PatientManageFragmentView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.fragment
 * @Description：患者管理主页面
 * @Author：song
 * @Date：18/11/5 下午5:27
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:27
 */
public class PatientManageFragment extends BaseControllerFragment<PatientManageFragmentController> implements PatientManageFragmentView {

    public ViewPager patientVp;
    public TabLayout patientTab;
    private PatientViewPagerAdapter patientViewPagerAdapter;
    private FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private RelativeLayout authorizeRl, createGroupRl, topTitleLayout;
    private TextView topHospitalName;
    private Button searchButton;
    private RefreshLayout refreshLayout;
    private int currentTab = 0;

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        topHospitalName = (TextView) getView().findViewById(R.id.top_hospital_name);
        topTitleLayout = (RelativeLayout) getView().findViewById(R.id.top_title_layout);
        topHospitalName.setOnClickListener(getController());
        searchButton = (Button) getView().findViewById(R.id.search_btn);
        refreshLayout = getView().findViewById(R.id.refreshLayout);
        searchButton.setOnClickListener(getController());
        list.add("消息");
        list.add("所有人");
        list.add("群聊");
        fm = getChildFragmentManager();
        fragmentList.add(new PatientMsgFragment());
        fragmentList.add(new PatientContactFragment());
        fragmentList.add(new PatientGroupFragment());
        initView();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.PATIENT_UPDATE_USER_LIST).build());
                EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_MSG_LIST).build());
                refreshLayout.finishRefresh(1500);
            }
        });
    }

    /**
     * 初始化页面
     */
    private void initView() {
        patientVp = (ViewPager) getView().findViewById(R.id.patient_vp);
        patientTab = (TabLayout) getView().findViewById(R.id.patient_tab);
        createGroupRl = (RelativeLayout) getView().findViewById(R.id.create_group_rl);
        authorizeRl = (RelativeLayout) getView().findViewById(R.id.authorize_rl);
        patientViewPagerAdapter = new PatientViewPagerAdapter(fm, fragmentList, list);
        patientVp.setAdapter(patientViewPagerAdapter);
        patientTab.setupWithViewPager(patientVp);
        createGroupRl.setOnClickListener(getController());
        authorizeRl.setOnClickListener(getController());

    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_patient_manage;
    }

    @Override
    public void lazyLoad() {
        topHospitalName.setText(MyApplication.userInfo.getHospitalName());
    }

    @Override
    public RelativeLayout getTitleLayout() {
        return topTitleLayout;
    }
}
