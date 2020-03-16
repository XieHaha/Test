package com.keydom.mianren.ih_doctor.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.m_interface.OnCheckDialogListener;


/**
 * [底部弹出dialog]
 **/
public class InputFlagDialog extends Dialog implements View.OnClickListener {

    private EditText flagEt;
    private TextView cancel, submit;
    private OnCheckDialogListener mListener;

    /**
     * @param context
     */
    public InputFlagDialog(Context context, OnCheckDialogListener listener) {
        super(context, R.style.dialogFullscreen);
        mListener = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_flag);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        layoutParams.horizontalMargin = 0.1f;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(layoutParams);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        cancel = findViewById(R.id.cancel);
        submit = findViewById(R.id.submit);
        flagEt = findViewById(R.id.flag_et);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                mListener.commit(v, flagEt.getText().toString());
                flagEt.setText("");
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

}
