package com.keydom.ih_common.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.R;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class CommonProgressDialog extends AlertDialog {

    private String content;
    private CommonProgressDialog.OnCancelListener listener;

    private CommonProgressDialog(Context context, String content, boolean isCancel, CommonProgressDialog.OnCancelListener listener) {
        super(context, R.style.loading_dialog);
        this.content = content;
        this.listener = listener;
        setCancelable(isCancel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_loading_view);
        ImageView mLoadImg = findViewById(R.id.loading_img);
        TextView mLoadText = findViewById(R.id.loading_txt);
        AnimationDrawable mDrawable = (AnimationDrawable) mLoadImg.getDrawable();
        mDrawable.start();
        mLoadText.setText(content);
        setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    dismiss();
                    if (listener != null) {
                        listener.onCancel();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private static Map<Context, CommonProgressDialog> map = new HashMap<>();
    private static CommonProgressDialog showDialog = null;
    private static WeakReference<Context> loadingContext = null;

    public static void showLoading(Context context, String content, boolean isCancel, OnCancelListener listener) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            boolean flag = map.get(context) == null || !map.get(context).isShowing();
            if (flag && !activity.isFinishing()) {
                CommonProgressDialog loadingDialog = new CommonProgressDialog(context, content, isCancel, listener);
                loadingDialog.show();
                loadingContext = new WeakReference<>(context);
                map.put(context, loadingDialog);
            }
        }
    }

    public static void loadingDismiss(Context context) {
        if (map.get(context) != null) {
            map.get(context).dismiss();
        }
        map.put(context, null);
        map.remove(context);
    }

    public interface OnCancelListener {
        void onCancel();
    }
}



