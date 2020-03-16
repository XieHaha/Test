package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * @Description：添加图片适配器
 * @Author：song
 * @Date：18/11/19 下午12:51
 * 修改人：xusong
 * 修改时间：18/11/19 下午12:51
 */
public class GridViewPlusImgAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    LayoutInflater layoutInflater;
    private RoundedImageView mImageView;
    private RoundedImageView deleteImg;
    /**
     * 构造方法
     */
    public GridViewPlusImgAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size() + 1;
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
        if (position == list.size()) {
            deleteImg.setVisibility(View.GONE);
        }
        if (position < list.size()) {
            Glide.with(context).load(Const.IMAGE_HOST + list.get(position)).into(mImageView);
        } else {
            mImageView.setBackgroundResource(R.mipmap.add_img_icon);
        }
        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GeneralDialog(context, "确定删除该图片？", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        list.remove(position);
                        GridViewPlusImgAdapter.this.notifyDataSetChanged();
                    }
                }).show();
            }
        });
        return convertView;
    }

}
