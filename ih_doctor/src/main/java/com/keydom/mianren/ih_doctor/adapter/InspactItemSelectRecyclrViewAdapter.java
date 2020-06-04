package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.mianren.ih_doctor.R;
import com.keydom.ih_common.bean.CheckOutGroupBean;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：检查项目适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class InspactItemSelectRecyclrViewAdapter extends RecyclerView.Adapter<InspactItemSelectRecyclrViewAdapter.ViewHolder> {


    private Context context;
    private List<CheckOutGroupBean> data;

    public InspactItemSelectRecyclrViewAdapter(Context context, List<CheckOutGroupBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_inspact_item_select_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemName.setText(data.get(position).getInsCheckCateName());
        if (data.get(position).isSelect()) {
            holder.itemName.setTextColor(context.getResources().getColor(R.color.font_select));
            holder.itemIcon.setBackground(context.getResources().getDrawable(R.mipmap.point_green));
            holder.itemSelect.setVisibility(View.VISIBLE);
        } else {
            holder.itemName.setTextColor(context.getResources().getColor(R.color.fontColorPrimary));
            holder.itemIcon.setBackground(context.getResources().getDrawable(R.mipmap.point_blue));
            holder.itemSelect.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                data.get(position).setSelect(!data.get(position).isSelect());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemIcon, itemSelect;
        public TextView itemName;

        public ViewHolder(View itemView) {
            super(itemView);
            itemIcon = itemView.findViewById(R.id.inspact_icon);
            itemSelect = itemView.findViewById(R.id.inspact_select);
            itemName = itemView.findViewById(R.id.inspact_name);

        }
    }
}
