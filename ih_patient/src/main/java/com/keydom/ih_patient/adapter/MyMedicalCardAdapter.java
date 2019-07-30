package com.keydom.ih_patient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.MedicalCardQrActivity;
import com.keydom.ih_patient.activity.my_medical_card.MedicalCardDetailActivity;
import com.keydom.ih_patient.activity.my_medical_card.MyMedicalCardActivity;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
/**
 * 我的就诊卡适配器
 */
public class MyMedicalCardAdapter extends RecyclerView.Adapter<MyMedicalCardAdapter.VH> {
    private MyMedicalCardActivity context;
    private List<MedicalCardInfo> dataList;
    /**
     * 构造方法
     */
    public MyMedicalCardAdapter(MyMedicalCardActivity context, List<MedicalCardInfo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_medical_card_item, parent, false);
        return new MyMedicalCardAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.nameTv.setText((dataList.get(position).getName()==null)?"未知":dataList.get(position).getName());
        holder.sexTv.setText("性别:"+CommonUtils.getPatientSex(dataList.get(position).getSex()));
        holder.cardNumTv.setText("就诊卡号："+(dataList.get(position).getEleCardNumber()));
        holder.cardHospitalTv.setText("指定医院："+dataList.get(position).getHospitalName());
        holder.cardQrImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MedicalCardQrActivity.start(context,dataList.get(position));

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MedicalCardDetailActivity.start(context,dataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private TextView nameTv,sexTv,cardNumTv,cardHospitalTv;
        private ImageView cardQrImg;
        public VH(View v) {
            super(v);
            nameTv=v.findViewById(R.id.my_medical_card_user_name);
            sexTv=v.findViewById(R.id.my_medical_card_user_sex);
            cardNumTv=v.findViewById(R.id.my_medical_card_num);
            cardHospitalTv=v.findViewById(R.id.my_medical_card_hospital);
            cardQrImg=v.findViewById(R.id.my_medical_card_qr_img);
        }
    }
}
