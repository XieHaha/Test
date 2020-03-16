package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_patient.R;

import java.util.List;

/**
 * 问诊订单想详情适配器
 *
 * @Author：song
 * @Date：18/11/6 下午6:52
 */
public class DiagnoseOrderDetailAdapter extends RecyclerView.Adapter<DiagnoseOrderDetailAdapter.ViewHolder> {


    private Context context;
    private List<String> data;

    /**
     * 构造方法
     */
    public DiagnoseOrderDetailAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diagnose_order_detail_img_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(Const.IMAGE_HOST + data.get(position)).into(holder.itemIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.previewImageList(context,data,position,true);
              /*  if (data.get(position) != null && !"".equals(data.get(position)))
                    CommonUtils.previewImage(context, Const.IMAGE_HOST + data.get(position));*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            itemIcon = (ImageView) itemView.findViewById(R.id.item_icon);

        }
    }
}
