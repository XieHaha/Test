package com.keydom.mianren.ih_doctor.view;

import android.content.Context;
import android.widget.TextView;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.ui.RecognizerDialog;

public class CustomRecognizerDialog extends RecognizerDialog {
    public CustomRecognizerDialog(Context context, InitListener initListener) {
        super(context, initListener);
    }

    @Override
    public void show(){
        super.show();
        TextView txt = (TextView)this.getWindow().getDecorView().findViewWithTag("textlink");
        txt.setText("");
    }
}