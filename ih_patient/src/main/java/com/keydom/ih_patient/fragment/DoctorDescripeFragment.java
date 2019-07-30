package com.keydom.ih_patient.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.fragment.controller.DoctorDescripeController;
import com.keydom.ih_patient.fragment.view.DoctorDescripeView;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 医生排版页面
 */
public class DoctorDescripeFragment extends BaseControllerFragment<DoctorDescripeController> implements DoctorDescripeView {
    private TextView doctor_des_tv;
    private String doctorDescripe;
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_doctor_descripe_layout;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doctor_des_tv=getView().findViewById(R.id.doctor_des_tv);
        Bundle bundle =this.getArguments();
        doctorDescripe=bundle.getString("doctorDescripe");
        RichText.initCacheDir(getContext());
        if(doctorDescripe!=null){
            RichText.from(doctorDescripe).bind(this)
                    .showBorder(false)
                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                    .into(doctor_des_tv);
        }

    }
}
