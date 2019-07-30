package com.keydom.ih_common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keydom.ih_common.R;

/**
 * @Name：com.keydom.ih_common.utils
 * @Description：描述信息
 * @Author：song
 * @Date：18/12/4 下午3:47
 * 修改人：xusong
 * 修改时间：18/12/4 下午3:47
 */
public class CostomToastUtils {

    private static CostomToastUtils instance = null;

    public static CostomToastUtils getInstance() {
        if (instance == null) {
            instance = new CostomToastUtils();
        }
        return instance;
    }

    public void ToastMessage(Context context, String msg) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.common_toast_view, null);
        ImageView iv = view.findViewById(R.id.toast_img);
        iv.setImageResource(R.mipmap.group_selected);
        TextView title = view.findViewById(R.id.toast_txt);
        title.setText(msg);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 12, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
