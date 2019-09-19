package com.keydom.ih_patient.activity.prescription.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.entity.PharmacyEntity;
import com.keydom.ih_patient.utils.CommUtil;
import com.orhanobut.logger.Logger;

public class BaseInfoView extends RelativeLayout {
    TextView mTvName, mTvContent, mTvPatientName, mTvPatientContent, mTvRecordsName, mTvRecordsContent;
    TextView mTvDepartmentName, mTvDepartmentContent, mTvAddress, mTvAddressContent, mTvFirst, mTvFirstContent;
    TextView mTvData, mTvDataContent, mTvState, mTvStateContent, mTvZxSec;
    ImageView mImageZx;
    private OnImageClickListener mOnImageClickListener;
    private View view;
    private PharmacyEntity pharmacyEntity;

    public BaseInfoView(Context context) {
        super(context);
        init(context);
    }

    public BaseInfoView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseInfoView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
//        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        view = inflater.inflate(R.layout.view_base_info, null);
//        this.addView(view, layoutParams);
        LayoutInflater.from(context).inflate(R.layout.view_base_info, this);
        mTvName = findViewById(R.id.tv_name);
        mTvContent = findViewById(R.id.tv_content);
        mTvPatientContent = findViewById(R.id.tv_patient_content);
        mTvRecordsContent = findViewById(R.id.tv_records_content);
        mTvDepartmentContent = findViewById(R.id.tv_department_content);
        mTvAddressContent = findViewById(R.id.tv_address_content);
        mTvFirstContent = findViewById(R.id.tv_first_content);
        mTvDataContent = findViewById(R.id.tv_date_content);
        mTvStateContent = findViewById(R.id.tv_state_content);
        mImageZx = findViewById(R.id.image_zx);
        mImageZx.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnImageClickListener != null) {
                    mOnImageClickListener.onMoreClick(v,pharmacyEntity);
                    Logger.e("iooo-"+pharmacyEntity);
                }
            }
        });
    }

    public void getData(PharmacyEntity entity) {
        if(entity!=null){
            pharmacyEntity=entity;
            Logger.e("aaaaaa="+entity);
            Logger.e("qqqqqqq="+pharmacyEntity);
        }
        if (!CommUtil.isEmpty(entity.getPrescriptionId())) {
            mTvContent.setText(entity.getPrescriptionId());
        } else {
            mTvContent.setText("");
        }
        if (!CommUtil.isEmpty(entity.getName())) {
            mTvPatientContent.setText(entity.getName());
        } else {
            mTvPatientContent.setText("");
        }
        if (!CommUtil.isEmpty(entity.getCasehistoryNumber())) {
            mTvRecordsContent.setText(entity.getCasehistoryNumber());
        } else {
            mTvRecordsContent.setText("");
        }
        if (!CommUtil.isEmpty(entity.getDept())) {
            mTvDepartmentContent.setText(entity.getDept());
        } else {
            mTvDepartmentContent.setText("");
        }
        if (!CommUtil.isEmpty(entity.getHospitalAddress())) {
            mTvAddressContent.setText(entity.getHospitalAddress());
        } else {
            mTvAddressContent.setText("");
        }
        if (!CommUtil.isEmpty(entity.getClinical())) {
            mTvFirstContent.setText(entity.getClinical());
        } else {
            mTvFirstContent.setText("");
        }
        if (!CommUtil.isEmpty(entity.getCreateDate())) {
            mTvDataContent.setText(entity.getCreateDate());
        } else {
            mTvDataContent.setText("");
        }

        //0-未支付，1-已支付,2-已取药/已签收,3-已发货,4-拒收
        mTvStateContent.setText(entity.getPaystatus().getName());
    }


    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        mOnImageClickListener = onImageClickListener;
    }

    public interface OnImageClickListener {
        void onMoreClick(View v,PharmacyEntity entity);
    }
}
