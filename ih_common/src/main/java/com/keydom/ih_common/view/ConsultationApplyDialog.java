package com.keydom.ih_common.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.R;

public class ConsultationApplyDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt, reasonTv;
    private TextView submitTxt;
    private TextView cancelTxt;

    private Context mContext;
    private String doctorName, patientName, reason;
    private OnCloseListener listener;
    private CancelListener cancelListener;

    public ConsultationApplyDialog(Context context, String doctorName, String patientName,
                                   String reason,
                                   OnCloseListener listener,
                                   CancelListener cancelListener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.reason = reason;
        this.listener = listener;
        this.cancelListener = cancelListener;
    }


    public ConsultationApplyDialog setCancel(boolean cancelable) {
        this.setCancelable(cancelable);
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_consultation_apply);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        contentTxt = this.findViewById(R.id.content);
        reasonTv = this.findViewById(R.id.reason);
        submitTxt = this.findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = this.findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);

        contentTxt.setText(getContent());
        reasonTv.setText(reason);
    }

    /**
     * 获取申请人信息
     */
    private SpannableStringBuilder getContent() {
        //  医生申请参与  视频会诊
        String info = String.format(mContext.getString(R.string.txt_apply_info), doctorName
                , patientName);
        SpannableStringBuilder ssb = new SpannableStringBuilder(info);
        ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,
                R.color.colorPrimary)), 0, doctorName.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorPrimary)),
                doctorName.length() + 8, doctorName.length() + 8 + patientName.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.cancel) {
            if (cancelListener != null) {
                cancelListener.onCancel();
            }
            this.dismiss();

        } else if (i == R.id.submit) {
            if (listener != null) {
                listener.onCommit();
            }
            this.dismiss();

        }
    }

    public interface OnCloseListener {
        void onCommit();
    }

    public interface CancelListener {
        void onCancel();
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.listener = onCloseListener;
    }
}
