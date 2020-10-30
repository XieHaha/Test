package com.keydom.mianren.ih_patient.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.R;
import com.keydom.ih_common.minterface.OnPrivateDialogListener;


/**
 * @Name：com.keydom.ih_common.utils
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/14 下午4:56
 * 修改人：xusong
 * 修改时间：18/11/14 下午4:56
 */
public class DialogUtils {

    public static final String SELECT_DATE = "select_date";
    public static final String SELECT_START_TIME = "select_start_time";
    public static final String SELECT_END_TIME = "select_end_time";
    public static final String INPUT_VALUE = "input_value";
    public static final String SELECT_USER = "select_user";

    public static Dialog createUpdateDialog(final Context context,String version,String content,final OnPrivateDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update, null);
        dialog.setContentView(view);
        final TextView cancelTv = (TextView) view.findViewById(R.id.dialog_update_cancel_tv);
        final TextView commitTv = (TextView) view.findViewById(R.id.dialog_update_confirm_tv);
        final TextView titleTv = (TextView) view.findViewById(R.id.dialog_update_title_tv);
        final TextView contentTv = (TextView) view.findViewById(R.id.dialog_update_content_tv);
        final ImageView closeIv = (ImageView) view.findViewById(R.id.dialog_update_close_iv);

        if(!TextUtils.isEmpty(content)){
            contentTv.setText(content);
        }

        if(!TextUtils.isEmpty(version)){
            titleTv.setText("新版本：V " + version);
        }

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if(null != listener){
                    listener.cancel();
                }
            }
        });
        commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if(null != listener){
                    listener.confirm();
                }
            }
        });

        if(null != listener){
            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.cancel();
                }
            });
        }

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

}
