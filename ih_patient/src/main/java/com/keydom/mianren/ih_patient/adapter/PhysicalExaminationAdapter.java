package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.order_physical_examination.PhysicalExaminationDetailActivity;
import com.keydom.mianren.ih_patient.bean.PhysicalExaInfo;
import com.keydom.mianren.ih_patient.constant.Global;

import java.util.List;
/**
 * 体检套餐详情适配器
 */
public class PhysicalExaminationAdapter extends RecyclerView.Adapter<PhysicalExaminationAdapter.VH> {
    private Context context;
    /**
     * 数据集合
     */
    private List<PhysicalExaInfo> dataList;
    /**
     * 构造方法
     */
    public PhysicalExaminationAdapter(Context context,List<PhysicalExaInfo> dataList) {
        this.context = context;
        this.dataList=dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.physical_exa_item, parent, false);
        return new PhysicalExaminationAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.physical_name_tv.setText(dataList.get(position).getName());
        holder.physical_sell_num_tv.setText("已售"+dataList.get(position).getSold()+"份");
        holder.physical_exa_price_tv.setText("¥"+dataList.get(position).getFee()+"元");
        holder.physical_exa_dsc_tv.setText("项目："+dataList.get(position).getSummary());
        GlideUtils.load(holder.physical_head_img, Const.IMAGE_HOST+dataList.get(position).getIcon(), 0, R.mipmap.mine_unkown_pic, false, null);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.setSelectedPhysicalExa(dataList.get(position));
                PhysicalExaminationDetailActivity.start(context);
            }
        });
        if(dataList.get(position).getIsTop()==0){
            holder.physical_label_tv.setVisibility(View.GONE);
        }else
            holder.physical_label_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        private TextView physical_name_tv,physical_label_tv,physical_sell_num_tv,physical_exa_price_tv,physical_exa_dsc_tv;
        private ImageView physical_head_img;

        public VH(View v) {
            super(v);
            physical_name_tv=v.findViewById(R.id.physical_name_tv);
            physical_label_tv=v.findViewById(R.id.physical_label_tv);
            physical_sell_num_tv=v.findViewById(R.id.physical_sell_num_tv);
            physical_exa_price_tv=v.findViewById(R.id.physical_exa_price_tv);
            physical_exa_dsc_tv=v.findViewById(R.id.physical_exa_dsc_tv);
            physical_head_img=v.findViewById(R.id.physical_head_img);
        }
    }
}
