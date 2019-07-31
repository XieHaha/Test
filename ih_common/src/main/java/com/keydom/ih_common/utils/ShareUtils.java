package com.keydom.ih_common.utils;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.keydom.ih_common.R;

/**
 * created date: 2019/1/23 on 15:34
 * des:
 * author: HJW HP
 */
public class ShareUtils {
    public static final int WX = 1;
    public static final int WX_CIRCLE = 2;
    public static final int QQ = 3;
    public static final int SINA = 4;

    public interface IOnShareCallBack {
        void onShareSelect(int type);
    }

    public static void showShareUtils(Context context, final IOnShareCallBack iOnShareCallBack) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.share_bottom_layout, null);
        bottomSheetDialog.setContentView(view);
        view.findViewById(R.id.wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnShareCallBack.onShareSelect(WX);
                bottomSheetDialog.dismiss();
            }
        });
        view.findViewById(R.id.wx_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnShareCallBack.onShareSelect(WX_CIRCLE);
                bottomSheetDialog.dismiss();
            }
        });
        view.findViewById(R.id.qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnShareCallBack.onShareSelect(QQ);
                bottomSheetDialog.dismiss();
            }
        });
        view.findViewById(R.id.weibo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnShareCallBack.onShareSelect(SINA);
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }
}
