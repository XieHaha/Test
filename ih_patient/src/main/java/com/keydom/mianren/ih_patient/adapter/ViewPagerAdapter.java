package com.keydom.mianren.ih_patient.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * viewpager适配器
 *
 * @Author：song
 * @Date：18/11/5 下午5:20
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:20
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<String> titleList;
    private List<Fragment> mFragmList;
    private FragmentManager fragmentManager;

    /**
     * 构造方法
     */
    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.fragmentManager = fm;
        this.mFragmList = list;
    }
    /**
     * 构造方法
     */
    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> titleList) {
        super(fm);
        this.fragmentManager = fm;
        this.mFragmList = list;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int index) {
        return mFragmList.get(index);
    }

    @Override
    public int getCount() {
        return mFragmList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList != null && titleList.size() > position) {
            return titleList.get(position);
        } else {
            return "";
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
