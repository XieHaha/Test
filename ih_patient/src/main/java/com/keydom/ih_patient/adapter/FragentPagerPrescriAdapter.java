package com.keydom.ih_patient.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.keydom.ih_patient.bean.PrescriptionBean;
import com.keydom.ih_patient.bean.entity.pharmacy.PharmacyBean;

import java.util.List;

public class FragentPagerPrescriAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<PharmacyBean> titles;
    public FragentPagerPrescriAdapter(FragmentManager manager, List<Fragment> fragments, List<PharmacyBean> titles) {
        super(manager);
        this.fragments = fragments;
        this.titles = titles;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).getPrescriptionType();
    }
}
