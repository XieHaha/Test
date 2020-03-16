package com.keydom.mianren.ih_doctor.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.adapter
 * @Description：患者管理viewPager适配器
 * @Author：song
 * @Date：18/11/7 下午1:19
 * 修改人：xusong
 * 修改时间：18/11/7 下午1:19
 */
public class PatientViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titleList;
    private FragmentManager fragmentManager;
    public PatientViewPagerAdapter(FragmentManager fm,List<Fragment> list,List<String> titleList) {
        super(fm);
        this.fragmentManager=fm;
        this.fragmentList=list;
        this.titleList=titleList;
    }

    @Override
    public Fragment getItem(int position) {
        if(fragmentList!=null&&fragmentList.size()>0){
            return fragmentList.get(position);
        }else{
            return null;
        }

    }

    @Override
    public int getCount() {
        if(fragmentList!=null&&fragmentList.size()>0){
            return fragmentList.size();
        }else{
            return 0;
        }

    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(titleList!=null&&titleList.size()>position){
            return titleList.get(position);
        }else{
            return "";
        }


    }
}
