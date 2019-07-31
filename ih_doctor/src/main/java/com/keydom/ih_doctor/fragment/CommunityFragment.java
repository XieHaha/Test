package com.keydom.ih_doctor.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseFragment;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.IssueArticleActivity;
import com.keydom.ih_doctor.activity.SearchActivity;
import com.keydom.ih_doctor.adapter.PatientViewPagerAdapter;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.utils.SelectHospitalPopUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.fragment
 * @Description：社区主页面
 * @Author：song
 * @Date：18/11/5 下午5:27
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:27
 */
public class CommunityFragment extends BaseFragment {


    private TabLayout comunityTab;
    private ViewPager comunityVp;
    private PatientViewPagerAdapter communityViewPagerAdapter;
    private ImageView issueArticle;
    private FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private TextView topHospitalName;
    private Button searchButton;
    private RelativeLayout topTitleLayout;

    /**
     * 初始化页面fragment
     */
    private void initView() {
        searchButton = (Button) getView().findViewById(R.id.search_btn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                SearchActivity.start(getContext());
            }
        });
        comunityTab = (TabLayout) getView().findViewById(R.id.comunity_tab);
        comunityVp = (ViewPager) getView().findViewById(R.id.comunity_vp);
        issueArticle = (ImageView) getView().findViewById(R.id.issue_article);
        topTitleLayout = (RelativeLayout) getView().findViewById(R.id.top_title_layout);
        issueArticle.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                IssueArticleActivity.start(getContext());
            }
        });
        if (communityViewPagerAdapter == null) {
            communityViewPagerAdapter = new PatientViewPagerAdapter(fm, fragmentList, list);
        }
        comunityVp.setAdapter(communityViewPagerAdapter);
        comunityTab.setupWithViewPager(comunityVp);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_community;
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        list.add("同行动态");
        list.add("我的关注");
        fm = getChildFragmentManager();
        fragmentList.add(new CommunityTrendsFragment());
        fragmentList.add(new CommunityAttentionFragment());
        topHospitalName = (TextView) getView().findViewById(R.id.top_hospital_name);
        topHospitalName.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                SelectHospitalPopUtil.getInstance().initPopWindow(getContext()).showHospitalPopupWindow(topTitleLayout);
            }
        });
    }

    @Override
    public void lazyLoad() {
        initView();
        topHospitalName.setText(MyApplication.userInfo.getHospitalName());
    }
}
