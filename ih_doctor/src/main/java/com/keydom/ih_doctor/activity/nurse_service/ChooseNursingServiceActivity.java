package com.keydom.ih_doctor.activity.nurse_service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.nurse_service.controller.ChooseNursingController;
import com.keydom.ih_doctor.activity.nurse_service.view.ChooseNursingView;
import com.keydom.ih_doctor.adapter.ViewPagerAdapter;
import com.keydom.ih_doctor.bean.CategoryBean;
import com.keydom.ih_doctor.bean.NursingProjectInfo;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.fragment.NursingFragment;
import com.keydom.ih_doctor.utils.ToastUtil;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChooseNursingServiceActivity extends BaseControllerActivity<ChooseNursingController> implements ChooseNursingView {
    /**
     * 接收项目列表数据的key
     */
    public static final String PROJECT_LIST = "projectList";
    private TabLayout nursing_project_tablayout;
    private ViewPager nursing_project_vp;
    private FragmentManager fm;
    /**
     * viewPager适配器
     */
    private ViewPagerAdapter viewPagerAdapter;
    /**
     * fragment list
     */
    private List<Fragment> fragmentList = new ArrayList<>();
    /**
     * fragment list key
     */
    private List<String> list = new ArrayList<>();
    /**
     * 选择的护理服务项目列表
     */
    private List<NursingProjectInfo> selectProjectList;

    /**
     * 启动选择护理项目页面
     *
     * @param context
     * @param mSelectProjectList
     */
    public static void start(Context context, List<NursingProjectInfo> mSelectProjectList) {
        Intent starter = new Intent(context, ChooseNursingServiceActivity.class);
        starter.putExtra(PROJECT_LIST, (Serializable) mSelectProjectList);
        ((Activity) context).startActivityForResult(starter, Const.NURSE_SERVICE_ITEM_SELECT);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_nursing_choose_service_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("选择项目");
        setRightTxt("确定");

        getController().getNurseServiceType();
        selectProjectList = (List<NursingProjectInfo>) getIntent().getSerializableExtra(PROJECT_LIST);
        nursing_project_tablayout = this.findViewById(R.id.nursing_project_tablayout);
        nursing_project_vp = this.findViewById(R.id.nursing_project_vp);
//        String key1 = "基础护理";
//        String key2 = "专科护理";
//        String key3 = "产后母婴";
//        list.add(key1);
//        list.add(key2);
//        list.add(key3);
//        NursingFragment nursingFragment1 = NursingFragment.newInstance(1, selectProjectList);
//        NursingFragment nursingFragment2 = NursingFragment.newInstance(2, selectProjectList);
//        NursingFragment nursingFragment3 = NursingFragment.newInstance(5, selectProjectList);

//        fragmentList.add(nursingFragment1);
//        fragmentList.add(nursingFragment2);
//        fragmentList.add(nursingFragment3);
        setRightBtnListener(v -> {
//            List<NursingProjectInfo> projects1 = nursingFragment1.getProjects();
//            List<NursingProjectInfo> projects2 = nursingFragment2.getProjects();
//            List<NursingProjectInfo> projects3 = nursingFragment3.getProjects();
            List<NursingProjectInfo> projects = new ArrayList<>();
            for (int i = 0; i < fragmentList.size(); i++) {
                NursingFragment mNursingFragment = (NursingFragment) fragmentList.get(i);
                projects.addAll(mNursingFragment.getProjects());
            }

//            projects.addAll(projects1);
//            projects.addAll(projects2);
//            projects.addAll(projects3);
            Intent intent = new Intent();
            intent.putExtra(Const.DATA, (Serializable) projects);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });


    }

    @Override
    public void getCotegorySuccess(List<CategoryBean> categoryBeans) {
        if (categoryBeans != null && categoryBeans.size() > 0) {
            int size=0;
            for (CategoryBean bean : categoryBeans) {
                if(bean.getName()!=null&&!"在线咨询".equals(bean.getName())){
                    list.add(String.valueOf(bean.getName()));
                    fragmentList.add(NursingFragment.newInstance(bean.getId(), selectProjectList));
                    size++;
                }
            }

            if(size>5)
                nursing_project_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            else
                nursing_project_tablayout.setTabMode(TabLayout.MODE_FIXED);

            fm = getSupportFragmentManager();
            if (viewPagerAdapter == null) {
                viewPagerAdapter = new ViewPagerAdapter(fm, fragmentList, list);
            }
            nursing_project_vp.setAdapter(viewPagerAdapter);
            nursing_project_vp.setOffscreenPageLimit(categoryBeans.size());//设置page数量
            nursing_project_tablayout.setupWithViewPager(nursing_project_vp);
        } else {
            ToastUtil.shortToast(ChooseNursingServiceActivity.this, "类型获取失败，请重试！");
        }
    }

    @Override
    public void getCotegoryFailed(String errMsg) {
        ToastUtil.shortToast(ChooseNursingServiceActivity.this, "类型获取失败，请重试！");
        finish();
    }
}
