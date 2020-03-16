package com.keydom.mianren.ih_doctor.view;

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

import com.keydom.mianren.ih_doctor.R;


/**
 * [底部弹出dialog]
 **/
public class BottomAddPrescriptionDialog extends Dialog implements View.OnClickListener {

    private ImageButton cancelBtn;
    private TextView addCommonPrescription, addPaediatricsPrescription;
    private Context mContext;
    private View.OnClickListener cancelListener;
    private TextView mTvHos, mTvWai;

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

        mTvHos = findViewById(R.id.tv_hos);
        mTvWai = findViewById(R.id.tv_wai);
        mTvHos.setOnClickListener(this);
        mTvWai.setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dismiss();
        return true;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.dialog_close:
                dismiss();
                break;
            case R.id.tv_hos:
                mTvHos.setTextColor(mContext.getResources().getColor(R.color.tab_color));
                mTvHos.setBackgroundColor(mContext.getResources().getColor(R.color.primary_bg_color));

                mTvWai.setTextColor(mContext.getResources().getColor(R.color.cu_black));
                mTvWai.setBackgroundColor(mContext.getResources().getColor(R.color.wai_yan));
                break;
            case R.id.tv_wai:
                mTvWai.setTextColor(mContext.getResources().getColor(R.color.tab_color));
                mTvWai.setBackgroundColor(mContext.getResources().getColor(R.color.primary_bg_color));

                mTvHos.setTextColor(mContext.getResources().getColor(R.color.cu_black));
                mTvHos.setBackgroundColor(mContext.getResources().getColor(R.color.wai_yan));
                break;
            default:
        }
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

    public void reset() {
        if(null != mTvHos){
            mTvHos.performClick();
        }
    }
}
