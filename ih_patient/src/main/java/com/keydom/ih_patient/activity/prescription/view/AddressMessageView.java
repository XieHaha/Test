package com.keydom.ih_patient.activity.prescription.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.entity.PharmacyEntity;
import com.keydom.ih_patient.utils.CommUtil;
public class AddressMessageView extends RelativeLayout {

    private TextView mPharmacy;
    private TextView mGetAddress;

    public AddressMessageView(Context context) {
        super(context);
        init(context);
    }

    public AddressMessageView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddressMessageView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }




    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_addrsess_message, this);
        mGetAddress=findViewById(R.id.tv_get_address);
        mPharmacy=findViewById(R.id.tv_drug);

    }
      public void getData(PharmacyEntity entity){
            if(!CommUtil.isEmpty(entity.getDrugstore())){
                mPharmacy.setText(entity.getDrugstore());
            }else {
                mPharmacy.setText("未知药房");
            }
            if(!CommUtil.isEmpty(entity.getDrugsStoreAddress())){
                mGetAddress.setText(entity.getDrugsStoreAddress());
            }else {
                mGetAddress.setText("暂无药房地址详细信息");
            }
      }

}
