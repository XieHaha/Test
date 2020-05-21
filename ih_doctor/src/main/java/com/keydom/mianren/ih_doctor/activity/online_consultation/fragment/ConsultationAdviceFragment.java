package com.keydom.mianren.ih_doctor.activity.online_consultation.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.adapter.ConsultationAdviceAdapter;
import com.keydom.mianren.ih_doctor.activity.online_consultation.adapter.ConsultationVoiceAdapter;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationAdviceController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationAdviceView;
import com.keydom.mianren.ih_doctor.bean.ConsultationAdviceBean;
import com.keydom.mianren.ih_doctor.bean.VoiceBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.utils.JsonUtils;
import com.keydom.mianren.ih_doctor.view.CustomRecognizerDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @date 20/4/9 11:27
 * @des 会诊室-会诊意见
 */
public class ConsultationAdviceFragment extends BaseControllerFragment<ConsultationAdviceController> implements ConsultationAdviceView, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.consultation_advice_edit_et)
    InterceptorEditText consultationAdviceEditEt;
    @BindView(R.id.consultation_advice_voice_iv)
    ImageView consultationAdviceVoiceIv;
    @BindView(R.id.consultation_advice_commit_tv)
    TextView consultationAdviceCommitTv;
    @BindView(R.id.consultation_advice_voice_layout)
    RelativeLayout consultationAdviceVoiceLayout;
    @BindView(R.id.consultation_advice_voice_recycler)
    RecyclerView consultationAdviceVoiceRecyclerView;
    @BindView(R.id.consultation_advice_recycler_view)
    RecyclerView consultationAdviceRecyclerView;

    /**
     * 语音听写UI
     */
    private CustomRecognizerDialog mIatDialog;
    /**
     * 会诊意见
     */
    private ConsultationAdviceAdapter adviceAdapter;
    /**
     * 会诊意见语音
     */
    private ConsultationVoiceAdapter voiceAdapter;

    private String recordId;
    /**
     * 会诊意见
     */
    private String consultationAdvice;

    private List<ConsultationAdviceBean> adviceBeans;
    private List<VoiceBean> voiceBeans = new ArrayList<>();

    public static ConsultationAdviceFragment newInstance(String id) {
        ConsultationAdviceFragment fragment = new ConsultationAdviceFragment();
        Bundle args = new Bundle();
        args.putString(Const.RECORD_ID, id);
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
        recordId = bundle.getString(Const.RECORD_ID);

        consultationAdviceRecyclerView.setNestedScrollingEnabled(false);
        consultationAdviceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        consultationAdviceCommitTv.setOnClickListener(getController());
        adviceAdapter = new ConsultationAdviceAdapter(getContext(), adviceBeans);
        consultationAdviceRecyclerView.setAdapter(adviceAdapter);

        //音频文件
        consultationAdviceVoiceRecyclerView.setNestedScrollingEnabled(false);
        voiceAdapter = new ConsultationVoiceAdapter(getContext(), voiceBeans);
        voiceAdapter.setOnItemChildClickListener(this);
        consultationAdviceVoiceRecyclerView.setAdapter(voiceAdapter);
        consultationAdviceVoiceLayout.setOnTouchListener(getController());

        //语音输入
        initVoice();
        //会诊意见列表
        getController().getConsultationAdviceList();
    }

    /**
     * 语音输入
     */
    @SuppressLint("CheckResult")
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
        params.put("recordId", recordId);
        return params;
    }

    @Override
    public String getConsultationAdvice() {
        consultationAdvice = consultationAdviceEditEt.getText().toString().trim();
        return consultationAdvice;
    }

    @Override
    public String getRecordId() {
        return recordId;
    }

    @Override
    public View getRoot() {
        return consultationAdviceVoiceLayout.getRootView();
    }

    @Override
    public void onRecordSuccess(VoiceBean bean) {
        voiceBeans.add(bean);
        voiceAdapter.notifyDataSetChanged();
    }

    @Override
    public void commitSuccess() {
        consultationAdviceEditEt.setText("");
        ToastUtil.showMessage(getContext(), "会诊意见提交成功");
        getController().getConsultationAdviceList();
    }

    @Override
    public void commitFailed(String msg) {
        ToastUtil.showMessage(getContext(), msg);
    }

    @Override
    public void getConsultationAdviceSuccess(List<ConsultationAdviceBean> data) {
        adviceBeans = data;
        adviceAdapter.setNewData(adviceBeans);
    }

    @Override
    public void getConsultationAdviceFailed(String msg) {
        ToastUtil.showMessage(getContext(), msg);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.consultation_voice_delete) {
            voiceBeans.remove(position);
            voiceAdapter.notifyItemRemoved(position);
            voiceAdapter.notifyItemRangeChanged(position,
                    voiceAdapter.getItemCount() - position);
        }
    }


}
