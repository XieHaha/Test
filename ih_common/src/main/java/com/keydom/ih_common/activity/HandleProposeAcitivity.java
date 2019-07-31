package com.keydom.ih_common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.R;
import com.keydom.ih_common.activity.controller.HandleProposeController;
import com.keydom.ih_common.activity.view.HandleProposeView;
import com.keydom.ih_common.base.BaseControllerActivity;

public class HandleProposeAcitivity extends BaseControllerActivity<HandleProposeController> implements HandleProposeView {
    public static void start(Context context,String contantStr){
        Intent intent=new Intent(context,HandleProposeAcitivity.class);
        intent.putExtra("contantStr",contantStr);
        context.startActivity(intent);
    }
    private TextView contantTv;
    private String contantStr;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_handle_propose_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("处置建议");
        contantStr=getIntent().getStringExtra("contantStr");
        contantTv = findViewById(R.id.sub_item_entrust_tv);
        if (contantStr != null)
            contantTv.setText(contantStr);
        else{
            ToastUtils.showShort("处置建议获取失败，请稍后重试");
            finish();
        }
    }
}
