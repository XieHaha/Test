package com.keydom.mianren.ih_patient.activity.prescription.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.keydom.mianren.ih_patient.R;



public class PrescriptionView extends LinearLayout {
    public PrescriptionView(Context context) {
        super(context);
        init(context);
    }

    public PrescriptionView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PrescriptionView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_addrsess_message, this);

    }
}
