package com.keydom.mianren.ih_patient.activity.nursing_service;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.nursing_service.controller.ChooseNursingController;
import com.keydom.mianren.ih_patient.activity.nursing_service.view.ChooseNursingView;
import com.keydom.mianren.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.mianren.ih_patient.bean.ChooseNursingBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.NursingProjectInfo;
import com.keydom.mianren.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择护理服务页面
 */
public class ChooseNursingServiceActivity extends BaseControllerActivity<ChooseNursingController> implements ChooseNursingView {
    public static final String PROJECT_LIST = "projectList";

    private TabLayout nursing_project_tablayout;
    private ViewPager nursing_project_vp;
    private FragmentManager fm;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private List<NursingProjectInfo> selectProjectList;
    public static List<NursingProjectInfo> mChooseProjects;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_nursing_choose_service_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (mChooseProjects != null) {
            mChooseProjects.clear();
        }
        mChooseProjects = new ArrayList<>();
        setTitle("选择项目");
        setRightTxt("确定");
        selectProjectList = (List<NursingProjectInfo>) getIntent().getSerializableExtra(PROJECT_LIST);
        if (selectProjectList != null) {
            mChooseProjects = selectProjectList;
        }
        nursing_project_tablayout = this.findViewById(R.id.nursing_project_tablayout);
        nursing_project_vp = this.findViewById(R.id.nursing_project_vp);
       /* Map<String, Long> projectTypeMap = Global.getProjectTypeMap();
        String key1 = "基础护理";
        String key2 = "专科护理";
        String key3 = "产后母婴";
        list.add(key1);
        list.add(key2);
        list.add(key3);
        NursingFragment nursingFragment1 = NursingFragment.newInstance(projectTypeMap.get(key1) == null ? -1 : projectTypeMap.get(key1), selectProjectList);
        NursingFragment nursingFragment2 = NursingFragment.newInstance(projectTypeMap.get(key2) == null ? -1 : projectTypeMap.get(key2), selectProjectList);
        NursingFragment nursingFragment3 = NursingFragment.newInstance(projectTypeMap.get(key3) == null ? -1 : projectTypeMap.get(key3), selectProjectList);
        fm = getSupportFragmentManager();
        fragmentList.add(nursingFragment1);
        fragmentList.add(nursingFragment2);
        fragmentList.add(nursingFragment3);*/
        setRightBtnListener(v -> {
//            List<NursingProjectInfo> projects1 = nursingFragment1.getProjects();
//            List<NursingProjectInfo> projects2 = nursingFragment2.getProjects();
//            List<NursingProjectInfo> projects3 = nursingFragment3.getProjects();
//            List<NursingProjectInfo> projects = new ArrayList<>();
//            projects.addAll(projects1);
//            projects.addAll(projects2);
//            projects.addAll(projects3);
            EventBus.getDefault().post(new Event(EventType.SENDSELECTNURSINGPROJECT, mChooseProjects));
            mChooseProjects.clear();
            finish();
        });
        fm = getSupportFragmentManager();
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter(fm, fragmentList, list);
        }
        nursing_project_vp.setAdapter(viewPagerAdapter);
        nursing_project_vp.setOffscreenPageLimit(3);
        nursing_project_tablayout.setupWithViewPager(nursing_project_vp);
        getController().getNursingProject();
    }

    @Override
    public void getTypeSuccess(List<ChooseNursingBean> dataList) {
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                list.add(dataList.get(i).getName());
                NursingFragment nursingFragment = NursingFragment.newInstance(dataList.get(i).getId(), selectProjectList);
                fragmentList.add(nursingFragment);
            }
            viewPagerAdapter.notifyDataSetChanged();


        }
    }

    @Override
    public void getTypeFailed(String errMsg) {

    }
}
