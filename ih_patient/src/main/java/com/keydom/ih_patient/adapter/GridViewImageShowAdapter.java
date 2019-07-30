package com.keydom.ih_patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class GridViewImageShowAdapter  extends BaseAdapter {
    private Context context;
    private List<String> list;
    LayoutInflater layoutInflater;
    private RoundedImageView mImageView;
    private RoundedImageView deleteImg;
    /**
     * 构造方法
     */
    public GridViewImageShowAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.grid_img_item, null);
        mImageView = (RoundedImageView) convertView.findViewById(R.id.item_icon);
        deleteImg = (RoundedImageView) convertView.findViewById(R.id.delete_img);
        deleteImg.setVisibility(View.GONE);
        Glide.with(context).load(Const.IMAGE_HOST + list.get(position)).into(mImageView);
        return convertView;
    }

}
