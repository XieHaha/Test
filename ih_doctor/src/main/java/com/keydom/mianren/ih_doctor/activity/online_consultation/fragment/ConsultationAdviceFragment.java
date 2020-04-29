package com.keydom.mianren.ih_doctor.activity.online_consultation.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationAdviceController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationAdviceView;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.utils.JsonUtils;
import com.keydom.mianren.ih_doctor.view.CustomRecognizerDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @date 20/4/9 11:27
 * @des 会诊室-会诊意见
 */
public class ConsultationAdviceFragment extends BaseControllerFragment<ConsultationAdviceController> implements ConsultationAdviceView {
    @BindView(R.id.consultation_advice_edit_et)
    InterceptorEditText consultationAdviceEditEt;
    @BindView(R.id.consultation_advice_voice_iv)
    ImageView consultationAdviceVoiceIv;
    @BindView(R.id.consultation_advice_commit_tv)
    TextView consultationAdviceCommitTv;
    @BindView(R.id.consultation_advice_recycler_view)
    RecyclerView consultationAdviceRecyclerView;

    /**
     * 语音听写UI
     */
    private CustomRecognizerDialog mIatDialog;

    private String orderId;
    /**
     * 会诊意见
     */
    private String consultationAdvice;

    public static ConsultationAdviceFragment newInstance(String id) {
        ConsultationAdviceFragment fragment = new ConsultationAdviceFragment();
        Bundle args = new Bundle();
        args.putString(Const.ORDER_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_consultation_advice;
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = code -> {
        if (code != ErrorCode.SUCCESS) {
            Log.e("xunfei", "初始化失败，错误码：" + code + ",请点击网址https://www.xfyun.cn/document" +
                    "/error-code查询解决方案");
        }
    };

    @SuppressLint("CheckResult")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        orderId = bundle.getString(Const.ORDER_ID);

        consultationAdviceRecyclerView.setNestedScrollingEnabled(true);
        consultationAdviceCommitTv.setOnClickListener(getController());

        initVoice();

        getController().getConsultationAdviceList();
    }

    /**
     * 语音输入
     */
    private void initVoice() {
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new CustomRecognizerDialog(getContext(), mInitListener);
        mIatDialog.setListener(voiceListener);
        consultationAdviceVoiceIv.setOnClickListener(v -> {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {
                            if (mIatDialog.isShowing()) {
                                mIatDialog.dismiss();
                            }
                            mIatDialog.show();
                            ToastUtil.showMessage(getContext(), "请开始说话…");
                        } else {
                            ToastUtil.showMessage(getContext(), "请开启录音需要的权限");
                        }
                    });
        });

    }

    /**
     * 听写UI监听器  会诊理由及目的
     */
    private RecognizerDialogListener voiceListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            if (null != consultationAdviceEditEt) {
                String text = JsonUtils.handleXunFeiJson(results);
                String oldText =
                        consultationAdviceEditEt.getText().toString().trim();
                StringBuilder builder = new StringBuilder(oldText);
                builder.append(text);
                consultationAdviceEditEt.setText(builder.toString());
                consultationAdviceEditEt.setSelection(builder.toString().length());
            }
        }

        public void onError(SpeechError error) {
            ToastUtil.showMessage(getContext(), error.getPlainDescription(true));
        }

    };

    @Override
    public Map<String, Object> getCommitParams() {
        Map<String, Object> params = new HashMap<>();
        //type 1 文字，2、语音
        params.put("type", 1);
        params.put("content", consultationAdvice);
        params.put("recordId", orderId);
        return null;
    }

    @Override
    public String getConsultationAdvice() {
        consultationAdvice = consultationAdviceEditEt.getText().toString().trim();
        return consultationAdvice;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public void commitSuccess() {

    }

    @Override
    public void commitFailed(String msg) {

    }
}
