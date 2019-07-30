package com.keydom.ih_doctor.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.keydom.ih_doctor.R;


/**
 * [底部弹出dialog]
 **/
public class BottomAddPrescriptionDialog extends Dialog implements View.OnClickListener {

    private ImageButton cancelBtn;
    private TextView addCommonPrescription, addPaediatricsPrescription;
    private Context mContext;
    private View.OnClickListener cancelListener;

    /**
     * @param context
     */
    public BottomAddPrescriptionDialog(Context context) {
        super(context, R.style.dialogFullscreen);
        mContext = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_prescription_layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(layoutParams);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        cancelBtn = (ImageButton) findViewById(R.id.dialog_close);
        addCommonPrescription = (TextView) findViewById(R.id.add_common_prescription);
        addPaediatricsPrescription = (TextView) findViewById(R.id.add_paediatrics_prescription);
        cancelBtn.setOnClickListener(this);
        addCommonPrescription.setOnClickListener(this);
        addPaediatricsPrescription.setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dismiss();
        return true;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dialog_close) {
            dismiss();
            return;
        }
        if (cancelListener != null) {
            cancelListener.onClick(v);
        }
    }


    public View.OnClickListener getCancelListener() {
        return cancelListener;
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }

}
