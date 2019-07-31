package com.keydom.ih_doctor.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_doctor.R;


public class DiagnosePrescriptionItemView extends RelativeLayout {
    private TextView addTv, inputEv, tipTv;

    public DiagnosePrescriptionItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.diagnose_prescription_item, this, true);
        addTv = findViewById(R.id.add_tv);
        inputEv = findViewById(R.id.sub_item_entrust_et);
        tipTv = findViewById(R.id.tip_tv);
        String titleStr = attrs.getAttributeValue("http://schemas.android.com/apk/res-audo/com.keydom.ih_doctor.view", "itemTip");
        String addStr = attrs.getAttributeValue("http://schemas.android.com/apk/res-audo/com.keydom.ih_doctor.view", "itemAddText");
        String hintStr = attrs.getAttributeValue("http://schemas.android.com/apk/res-audo/com.keydom.ih_doctor.view", "itemInputHint");
        String limitSize = attrs.getAttributeValue("http://schemas.android.com/apk/res-audo/com.keydom.ih_doctor.view", "limit");
        setItemName(titleStr);
        setAddStr(addStr);
        setHintStr(hintStr);
        setMaxSize(limitSize);
    }

    private void setItemName(String titleStr) {
        tipTv.setText(titleStr);
    }

    private void setMaxSize(String titleStr) {
        if (titleStr != null && !"".equals(titleStr))
            inputEv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.parseInt(titleStr))});
    }

    private void setAddStr(String addStr) {
        addTv.setText(addStr);
    }

    private void setHintStr(String hintStr) {
        inputEv.setHint(hintStr);
    }

    public void setText(String str) {
        inputEv.setText(str);
    }

    public String getInputStr() {
        return inputEv.getText().toString();
    }

    public void setAddOnClikListener(final OnClickListener onClikListener) {
        addTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClikListener.onClick(DiagnosePrescriptionItemView.this);
            }
        });

    }
}
