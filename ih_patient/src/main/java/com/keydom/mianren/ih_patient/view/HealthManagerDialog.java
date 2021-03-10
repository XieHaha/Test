package com.keydom.mianren.ih_patient.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.keydom.mianren.ih_patient.R;

/**
 * 健康管理弹窗
 *
 * @author 顿顿
 */
public class HealthManagerDialog extends Dialog implements View.OnClickListener {
    TextView dialogHealthHomeTv, dialogHealthManagerTv;
    private Context context;
    private OnCommitListener onCommitListener;

    /**
     * 构建方法
     */
    public HealthManagerDialog(@NonNull Context context, OnCommitListener listener) {
        super(context, com.keydom.ih_common.R.style.dialog);
        this.context = context;
        onCommitListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_manager_dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        dialogHealthHomeTv = findViewById(R.id.dialog_health_home_tv);
        dialogHealthManagerTv = findViewById(R.id.dialog_health_manager_tv);
        dialogHealthHomeTv.setOnClickListener(this);
        dialogHealthManagerTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.dialog_health_home_tv) {
            if (onCommitListener != null) {
                onCommitListener.backHome();
            }
        } else if (i == R.id.dialog_health_manager_tv) {
            if (onCommitListener != null) {
                onCommitListener.backHealthManager();
            }
        }
    }

    /**
     * 提交监听
     */
    public interface OnCommitListener {
        /**
         * 健康管理
         */
        void backHealthManager();

        /**
         * 返回首页
         */
        void backHome();
    }
}
