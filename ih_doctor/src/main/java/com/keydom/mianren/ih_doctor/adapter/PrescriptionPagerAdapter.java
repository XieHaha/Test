package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.keydom.mianren.ih_doctor.bean.PrescriptionModelBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionTemplateBean;
import com.keydom.mianren.ih_doctor.view.PrescriptionModelView;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionPagerAdapter extends PagerAdapter {
    private List<PrescriptionModelView> viewList=new ArrayList<>();
    private List<PrescriptionModelBean>  modelBeanList=new ArrayList<>();
    private List<PrescriptionTemplateBean> templateBeanList;
    private List<String> titleList;
    public PrescriptionPagerAdapter(Context context, List<String> titleList, List<PrescriptionTemplateBean> templateBeanList){
        this.titleList=titleList;
        this.templateBeanList=templateBeanList;
        for (int i = 0; i <templateBeanList.size() ; i++) {
            PrescriptionModelView prescriptionModelView=new PrescriptionModelView(context);
            if(templateBeanList.get(i).isSavedAsTemplate()){
                prescriptionModelView.setModelName(templateBeanList.get(i).getModelNameTemp());
                prescriptionModelView.setModelType(templateBeanList.get(i).getModelTypeTemp());
            }
            viewList.add(prescriptionModelView);
        }
    }
    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    public List<PrescriptionModelBean> getModelList(){
        modelBeanList.clear();
        for (int i = 0; i < viewList.size(); i++) {
            PrescriptionModelBean prescriptionModelBean=new PrescriptionModelBean();
            prescriptionModelBean.setModelName(viewList.get(i).getModelName());
            prescriptionModelBean.setModelType(viewList.get(i).getModelType());
            modelBeanList.add(prescriptionModelBean);
        }

        return modelBeanList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(viewList.get(position));
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
}
