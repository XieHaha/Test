package com.keydom.mianren.ih_doctor.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：ViewPager适配器
 * @Author：song
 * @Date：18/11/5 下午5:20
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:20
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{

    private List<String> titleList;
    private List<Fragment> mFragmList;
    private FragmentManager fragmentManager;
    public ViewPagerAdapter(FragmentManager fm,List<Fragment> list){
        super(fm);
        this.fragmentManager=fm;
        this.mFragmList=list;
    }
    public ViewPagerAdapter(FragmentManager fm,List<Fragment> list,List<String> titleList) {
        super(fm);
        this.fragmentManager=fm;
        this.mFragmList=list;
        this.titleList=titleList;
    }

    @Override
    public Fragment getItem(int index) {
        // TODO Auto-generated method stub
        return mFragmList.get(index);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFragmList.size();
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
