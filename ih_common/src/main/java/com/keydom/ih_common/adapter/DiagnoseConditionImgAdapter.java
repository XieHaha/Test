package com.keydom.ih_common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.R;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.CommonUtils;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class DiagnoseConditionImgAdapter extends RecyclerView.Adapter<DiagnoseConditionImgAdapter.ViewHolder> {


    private Context context;
    private List<String> data;

    public DiagnoseConditionImgAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.condition_icon_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(Const.IMAGE_HOST + data.get(position)).into(holder.icon);
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CommonUtils.previewImage(context, Const.IMAGE_HOST + data.get(position));
                CommonUtils.previewImageList(context,data,position,true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.condition_img);

        }
    }
}
