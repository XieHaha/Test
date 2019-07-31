package com.keydom.ih_common.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.R;

public class GeneralDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private CancelListener cancelListener;
    private String positiveName;
    private String negativeName="取消";
    private String title;
    private boolean positiveIsGone;
    private boolean negativeIsGone;
    public GeneralDialog(Context context) {
        super(context);
        this.mContext = context;
    }
    public GeneralDialog(Context context, String content) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
    }

    public GeneralDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public GeneralDialog(Context context, String content, OnCloseListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    public GeneralDialog(Context context, String content, OnCloseListener listener,CancelListener cancelListener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
        this.cancelListener=cancelListener;
    }

    protected GeneralDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public GeneralDialog setTitle(String title){
        this.title = title;
        return this;
    }
    public GeneralDialog setCancel(boolean cancelable){
        this.setCancelable(cancelable);
        return this;
    }

    public GeneralDialog setPositiveButton(String name){
        this.positiveName = name;
        return this;
    }

    public GeneralDialog setPositiveButtonIsGone(boolean isGone){
        this.positiveIsGone = isGone;
        return this;
    }

    public GeneralDialog setNegativeButtonIsGone(boolean isGone){
        this.negativeIsGone = isGone;
        return this;
    }

    public GeneralDialog setNegativeButton(String name){
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        contentTxt = this.findViewById(R.id.content);
        titleTxt =  this.findViewById(R.id.title);
        submitTxt =  this.findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt =  this.findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);

        contentTxt.setText(content);
        if(!TextUtils.isEmpty(positiveName)){
            submitTxt.setText(positiveName);
        }

        if(!TextUtils.isEmpty(negativeName)){
            cancelTxt.setText(negativeName);
        }

        submitTxt.setVisibility(positiveIsGone?View.GONE:View.VISIBLE);
        cancelTxt.setVisibility(negativeIsGone?View.GONE:View.VISIBLE);

        if(!TextUtils.isEmpty(title)){
            titleTxt.setText(title);
        }

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
    public interface OnCloseListener{
        void onCommit();
    }

    public interface CancelListener{
        void onCancel();
    }
    public void  setOnCloseListener(OnCloseListener onCloseListener){
        this.listener=onCloseListener;
    }
}
