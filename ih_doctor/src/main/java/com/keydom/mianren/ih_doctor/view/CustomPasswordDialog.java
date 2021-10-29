package com.keydom.mianren.ih_doctor.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

import com.keydom.mianren.ih_doctor.R;


/**
 * 问诊订单对话框
 */
public class CustomPasswordDialog extends Dialog {
    private Context context;
    private CustomPasswordInputView inputView;
    private OnCommitListener onCommitListener;

    public CustomPasswordDialog(Context context) {
        super(context, R.style.input_dialog);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_password);
        inputView = findViewById(R.id.custom_password_input_et);
        inputView.setOnCompleteListener(new CustomPasswordInputView.OnPasswordCompleteListener() {
            @Override
            public void onComplete(String password) {
                if (onCommitListener != null) {
                    onCommitListener.onCommit(password);
                }
                dismiss();
            }
        });
        setCanceledOnTouchOutside(true);

        showIMEOtherWay(inputView);
    }


    /**
     * 其他方式显示键盘
     */
    public static void showIMEOtherWay(final View view) {
        (new Handler()).postDelayed(() -> {
            view.dispatchTouchEvent(
                    MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_DOWN, 0, 0, 0));
            view.dispatchTouchEvent(
                    MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_UP, 0, 0, 0));
        }, 200);
    }

    public void setOnCommitListener(OnCommitListener onCommitListener) {
        this.onCommitListener = onCommitListener;
    }

    /**
     * 提交监听
     */
    public interface OnCommitListener {
        void onCommit(String password);
    }
}
