package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.keydom.ih_common.utils.BaseFileUtils;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：问诊单病情资料图片适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class DiagnoseOrderDetailAdapter extends RecyclerView.Adapter<DiagnoseOrderDetailAdapter.ViewHolder> {


    private Context context;
    private List<String> data;

    public DiagnoseOrderDetailAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diagnose_order_detail_img_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GlideUtils.load(holder.itemIcon, BaseFileUtils.getHeaderUrl(data.get(position)), 0, 0, false,
                null);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                CommonUtils.previewImageList(context, data, position,
                        BaseFileUtils.needAddHead(data.get(position)));
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
