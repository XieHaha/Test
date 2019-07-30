package com.keydom.ih_common.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.R;

/**
 * @Name：com.keydom.ih_common.utils
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/14 下午4:56
 * 修改人：xusong
 * 修改时间：18/11/14 下午4:56
 */
public class DialogCreator {


    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.common_loading_view, null);
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_view);
        ImageView mLoadImg = (ImageView) v.findViewById(R.id.loading_img);
        TextView mLoadText = (TextView) v.findViewById(R.id.loading_txt);
        AnimationDrawable mDrawable = (AnimationDrawable) mLoadImg.getDrawable();
        mDrawable.start();
        mLoadText.setText(msg);
        final Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setCancelable(true);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        return loadingDialog;
    }


    public static Dialog createDelDialog(Context context,
                                         View.OnClickListener listener, boolean isUpdate) {
        Dialog dialog = new Dialog(context, R.style.ih_default_dialog_style);
        View v = LayoutInflater.from(context).inflate(R.layout.ih_dialog_delete_layout, null);
        dialog.setContentView(v);
        final LinearLayout deleteLl = (LinearLayout) v.findViewById(R.id.delete_ll);
        final LinearLayout updateLl = (LinearLayout) v.findViewById(R.id.update_ll);
        TextView updateTv = (TextView) v.findViewById(R.id.tv_update);
        if (isUpdate) {
            updateLl.setVisibility(View.VISIBLE);
        } else {
            updateLl.setVisibility(View.GONE);
        }

        deleteLl.setOnClickListener(listener);
        updateLl.setOnClickListener(listener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


}
