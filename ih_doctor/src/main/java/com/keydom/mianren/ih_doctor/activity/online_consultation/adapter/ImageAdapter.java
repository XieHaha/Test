package com.keydom.mianren.ih_doctor.activity.online_consultation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.adapter
 * @Description：添加图片适配器
 * @Author：song
 * @Date：18/11/19 下午12:51
 * 修改人：xusong
 * 修改时间：18/11/19 下午12:51
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    LayoutInflater layoutInflater;
    private RoundedImageView mImageView;
    private RoundedImageView deleteImg;

    private boolean isAdd;

    public ImageAdapter(Context context, List<String> list, boolean isAdd) {
        this.context = context;
        this.list = list;
        this.isAdd = isAdd;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (isAdd) {
            return list.size() + 1;
        }
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
        mImageView = convertView.findViewById(R.id.item_icon);
        deleteImg = convertView.findViewById(R.id.delete_img);
        if (position == list.size() || !isAdd) {
            deleteImg.setVisibility(View.GONE);
        }
        if (position < list.size()) {
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //                    CommonUtils.previewImage(context, Const.IMAGE_HOST +
                    //                    list.get(position));
                    CommonUtils.previewImageList(context, list, position, true);

                }
            });
            GlideUtils.load(mImageView, Const.IMAGE_HOST + list.get(position), 0, 0, false, null);
        } else {
            mImageView.setBackgroundResource(R.mipmap.plus);
        }


        deleteImg.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                new GeneralDialog(context, "确定删除该图片？", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        list.remove(position);
                        ImageAdapter.this.notifyDataSetChanged();
                    }
                }).show();
            }
        });
        return convertView;
    }

}
