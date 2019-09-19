package com.keydom.ih_patient.activity.prescription.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.entity.PharmacyEntity;
import com.keydom.ih_patient.utils.CommUtil;



public class ReviewInfo extends LinearLayout {
    private TextView mTvPrescribingContent, mTvReviewContent, mTvReviewDate, mTvCheckContent, mTvPillsContent;

    public ReviewInfo(Context context) {
        super(context);
        init(context);
    }

    public ReviewInfo(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReviewInfo(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_review_info, this);
        mTvPrescribingContent = findViewById(R.id.tv_prescribing_content);
        mTvReviewContent = findViewById(R.id.tv_review_content);
        mTvReviewDate = findViewById(R.id.tv_review_date);
        mTvCheckContent = findViewById(R.id.tv_check_content);
        mTvPillsContent = findViewById(R.id.tv_pills_date);
    }

    public void getData(PharmacyEntity entity) {
        if (!CommUtil.isEmpty(entity.getDoctorName())) {
            mTvPrescribingContent.setText(entity.getDoctorName());
        } else {
            mTvPrescribingContent.setText("");
        }
        if (!CommUtil.isEmpty(entity.getAuditerName())) {
            mTvReviewContent.setText(entity.getAuditerName());
        }else {
            mTvReviewContent.setText("");
        }
        if(!CommUtil.isEmpty(entity.getAuditerDate())){
            mTvReviewDate.setText(entity.getAuditerDate());
        }else {
            mTvReviewDate.setText("");
        }
        if(!CommUtil.isEmpty(entity.getCheckerName())){
            mTvCheckContent.setText(entity.getCheckerName());
        }else {
            mTvCheckContent.setText("");
        }
        if(!CommUtil.isEmpty(entity.getCheckerDate())){
            mTvPillsContent.setText(entity.getCheckerDate());
        }else {
            mTvPillsContent.setText("");
        }
    }
}
