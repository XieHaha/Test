package com.keydom.mianren.ih_doctor.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.utils.JsonUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;


public class DiagnosePrescriptionItemView extends RelativeLayout {
    private TextView addTv, tipTv;
    private InterceptorEditText inputEv;
    private ImageView mVoiceInputIv;
    private FragmentActivity mFragmentActivity;

    // 语音听写UI
    private CustomRecognizerDialog mIatDialog;

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {

            if (code != ErrorCode.SUCCESS) {
                Log.e("xunfei", "初始化失败，错误码：" + code + ",请点击网址https://www.xfyun" +
                        ".cn/document/error-code查询解决方案");
            }
        }
    };

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            if (null != inputEv) {
                String text = JsonUtils.handleXunFeiJson(results);
                if (TextUtils.isEmpty(inputEv.getText().toString())) {
                    inputEv.setText(text);
                    inputEv.setSelection(inputEv.getText().length());
                } else {
                    inputEv.setText(inputEv.getText().toString() + text);
                    inputEv.setSelection(inputEv.getText().length());
                }
            }

        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            ToastUtil.showMessage(MyApplication.mApplication, error.getPlainDescription(true));

        }

    };

    public FragmentActivity getFragmentActivity() {
        return mFragmentActivity;
    }

    public void setFragmentActivity(FragmentActivity fragmentActivity) {
        this.mFragmentActivity = fragmentActivity;
    }

    public DiagnosePrescriptionItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.diagnose_prescription_item, this, true);
        addTv = findViewById(R.id.add_tv);
        inputEv = findViewById(R.id.sub_item_entrust_et);
        tipTv = findViewById(R.id.tip_tv);
        mVoiceInputIv = findViewById(R.id.diagnose_prescription_voice_input_iv);
        String titleStr = attrs.getAttributeValue("http://schemas.android.com/apk/res-audo/com" +
                ".keydom.mianren.ih_doctor.view", "itemTip");
        String addStr = attrs.getAttributeValue("http://schemas.android.com/apk/res-audo/com" +
                ".keydom.mianren.ih_doctor.view", "itemAddText");
        String hintStr = attrs.getAttributeValue("http://schemas.android.com/apk/res-audo/com" +
                ".keydom.mianren.ih_doctor.view", "itemInputHint");
        String limitSize = attrs.getAttributeValue("http://schemas.android.com/apk/res-audo/com" +
                ".keydom.mianren.ih_doctor.view", "limit");
        setItemName(titleStr);
        setAddStr(addStr);
        setHintStr(hintStr);
        setMaxSize(limitSize);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new CustomRecognizerDialog(context, mInitListener);
        mIatDialog.setListener(mRecognizerDialogListener);
        mVoiceInputIv.setOnClickListener(v -> initPremissions());
    }

    @SuppressLint("CheckResult")
    public void initPremissions() {
        RxPermissions rxPermissions = new RxPermissions(mFragmentActivity);
        rxPermissions.request(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            if (mIatDialog.isShowing()) {
                                mIatDialog.dismiss();
                            }
                            mIatDialog.show();
                            ToastUtil.showMessage(MyApplication.mApplication, "请开始说话…");

                        } else {
                            ToastUtil.showMessage(MyApplication.mApplication, "请开启录音需要的权限");

                        }
                    }
                });

    }

    private void setItemName(String titleStr) {
        tipTv.setText(titleStr);
    }

    private void setMaxSize(String titleStr) {
        if (titleStr != null && !"".equals(titleStr))
            inputEv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.parseInt(titleStr))});
    }

    private void setAddStr(String addStr) {
        addTv.setText(addStr);
    }

    private void setHintStr(String hintStr) {
        inputEv.setHint(hintStr);
    }

    public void setText(String str) {
        inputEv.setText(str);
    }

    public String getInputStr() {
        return inputEv.getText().toString();
    }

    public void setAddOnClikListener(final OnClickListener onClikListener) {
        addTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClikListener.onClick(DiagnosePrescriptionItemView.this);
            }
        });

    }
}
