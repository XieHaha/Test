package com.keydom.ih_patient.activity.online_diagnoses_order;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.common_document.CommonDocumentActivity;
import com.keydom.ih_patient.activity.online_diagnoses_order.controller.DiagnosesApplyController;
import com.keydom.ih_patient.activity.online_diagnoses_order.view.DiagnosesApplyView;
import com.keydom.ih_patient.adapter.GridViewPlusImgAdapter;
import com.keydom.ih_patient.bean.CommonDocumentBean;
import com.keydom.ih_patient.bean.DoctorMainBean;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.ManagerUserBean;
import com.keydom.ih_patient.bean.MedicalCardInfo;
import com.keydom.ih_patient.bean.PayOrderBean;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.utils.JsonUtils;
import com.keydom.ih_patient.view.DiagnosesApplyDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * 在线问诊申请页面
 */
public class DiagnosesApplyActivity extends BaseControllerActivity<DiagnosesApplyController> implements DiagnosesApplyView {

    /**
     * 启动
     */
    public static void start(Context context, String type, DoctorMainBean.InfoBean info) {
        Intent intent = new Intent(context, DiagnosesApplyActivity.class);
        intent.putExtra("type", type);
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", info);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private TextView name_tv, job_title_tv, photo_diagnoses_tv, video_diagnoses_tv,
            depart_tv, adept_tv, inquisition_num_tv, good_evaluate_num_tv,
            average_reply_time_tv, choose_patient_card_tv, choose_patient_tv, conmit_tv, desc_font_num_tv;
    private InterceptorEditText desc_edt;
    private CircleImageView head_img;
    private GridViewForScrollView img_gv;
    private String type;
    private DoctorMainBean.InfoBean info;
    private MedicalCardInfo medicalCardInfo;
    private ManagerUserBean managerUserBean;
    private boolean isCardPatientMatch = false;
    private GridViewPlusImgAdapter mAdapter;
    public List<String> dataList = new ArrayList<>();
    private PayOrderBean orderInfo;
    private ImageView mVoiceInputIv;

    // 语音听写UI
    private RecognizerDialog mIatDialog;

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {

            if (code != ErrorCode.SUCCESS) {
                Log.e("xunfei","初始化失败，错误码：" + code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
            }
        }
    };

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            if(null != desc_edt){
                String text = JsonUtils.handleXunFeiJson(results);
                if(TextUtils.isEmpty(desc_edt.getText().toString())){
                    desc_edt.setText(text);
                    desc_edt.setSelection(desc_edt.getText().length());
                }else{
                    desc_edt.setText(desc_edt.getText().toString() + text);
                    desc_edt.setSelection(desc_edt.getText().length());
                }
            }

        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            ToastUtil.showMessage(DiagnosesApplyActivity.this,error.getPlainDescription(true));

        }

    };

    @Override
    public int getLayoutRes() {
        return R.layout.activity_diagnoses_apply_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        type = getIntent().getStringExtra("type");
        info = (DoctorMainBean.InfoBean) getIntent().getSerializableExtra("info");
        if (DiagnosesApplyDialog.VIDEODIAGNOSES.equals(type)) {
            setTitle("视频问诊");
        } else {
            setTitle("图文问诊");
        }
        getTitleLayout().setRightTitle("服务介绍");
        getTitleLayout().setOnRightTextClickListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                CommonDocumentActivity.start(getContext(), CommonDocumentBean.CODE_10);
            }
        });
        name_tv = findViewById(R.id.name_tv);
        mVoiceInputIv = findViewById(R.id.diagnoses_apply_layout_voice_input_iv);
        desc_font_num_tv = findViewById(R.id.desc_font_num_tv);
        job_title_tv = findViewById(R.id.job_title_tv);
        photo_diagnoses_tv = findViewById(R.id.photo_diagnoses_tv);
        video_diagnoses_tv = findViewById(R.id.video_diagnoses_tv);
        depart_tv = findViewById(R.id.depart_tv);
        adept_tv = findViewById(R.id.adept_tv);
        inquisition_num_tv = findViewById(R.id.inquisition_num_tv);
        good_evaluate_num_tv = findViewById(R.id.good_evaluate_num_tv);
        average_reply_time_tv = findViewById(R.id.average_reply_time_tv);
        choose_patient_card_tv = findViewById(R.id.choose_patient_card_tv);
        choose_patient_card_tv.setOnClickListener(getController());
        choose_patient_tv = findViewById(R.id.choose_patient_tv);
        choose_patient_tv.setOnClickListener(getController());
        conmit_tv = findViewById(R.id.conmit_tv);
        conmit_tv.setOnClickListener(getController());
        desc_edt = findViewById(R.id.desc_edt);
        desc_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                desc_font_num_tv.setText(charSequence.length() + "/500");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        head_img = findViewById(R.id.head_img);
        img_gv = findViewById(R.id.img_gv);
        mAdapter = new GridViewPlusImgAdapter(this, dataList);
        img_gv.setAdapter(mAdapter);
        img_gv.setOnItemClickListener(getController());
        if (info != null) {
            if (info.getIsInquiry() == 0) {
                photo_diagnoses_tv.setVisibility(View.INVISIBLE);
                video_diagnoses_tv.setVisibility(View.INVISIBLE);
            } else {
                photo_diagnoses_tv.setVisibility(View.VISIBLE);
                video_diagnoses_tv.setVisibility(View.VISIBLE);
            }
            job_title_tv.setText(info.getJobTitle());
            name_tv.setText(info.getName());
            good_evaluate_num_tv.setText(info.getFeedbackRate());
            average_reply_time_tv.setText(info.getAverageResponseTime() + "分钟");
            inquisition_num_tv.setText(info.getInquisitionAmount() + "");
            adept_tv.setText("擅长：" + info.getAdept());
            depart_tv.setText("科室： " + info.getDeptName());
            GlideUtils.load(head_img, info.getAvatar() == null ? "" : Const.IMAGE_HOST + info.getAvatar(), 0, R.mipmap.test_doctor_head_icon, false, null);
        }
        EventBus.getDefault().register(getContext());

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(this, mInitListener);
        mIatDialog.setListener(mRecognizerDialogListener);
        mVoiceInputIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPremissions();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void initPremissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            if(mIatDialog.isShowing()){
                                mIatDialog.dismiss();
                            }
                            mIatDialog.show();
                            ToastUtil.showMessage(DiagnosesApplyActivity.this,"请开始说话…");

                        } else {
                            ToastUtil.showMessage(DiagnosesApplyActivity.this,"请开启录音需要的权限");

                        }
                    }
                });

    }

    /**
     * 获取患者就诊卡
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPatientCard(Event event) {
        if (event.getType() == EventType.SENDSELECTNURSINGPATIENT) {
            medicalCardInfo = (MedicalCardInfo) event.getData();
            choose_patient_card_tv.setText(medicalCardInfo.getName());
            getController().getManagerUserList();
        }
        if (event.getType() == EventType.SENDSELECTDIAGNOSESPATIENT) {
            if (medicalCardInfo != null)
                medicalCardInfo = null;
            managerUserBean = (ManagerUserBean) event.getData();
            choose_patient_card_tv.setText(managerUserBean.getName());
            if (managerUserBean.getPastMedicalHistory() == null || "".equals(managerUserBean.getPastMedicalHistory()))
                choose_patient_tv.setText("该就诊人无病史，点击前往编辑");
            else
                choose_patient_tv.setText(managerUserBean.getPastMedicalHistory());
//            choose_patient_tv.setClickable(false);
        }
    }

    /**
     * 判断患者信息是否更换
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkPatientInfoChange(Event event) {
        if (event.getType() == EventType.CHECKPATIENTINFOCHANGE) {
            getController().getManagerUserList();
        }
    }

    /**
     * 判断患者信息是否更换
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updatePatient(Event event) {
        if (event.getType() == EventType.UPDATEPATIENT) {
            getController().getManagerUserList();
        }
    }

    /**
     * 获取患者信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPatientInfo(Event event) {
        if (event.getType() == EventType.SENDPATIENTINFO) {
            managerUserBean = (ManagerUserBean) event.getData();
            choose_patient_tv.setText(managerUserBean.getPastMedicalHistory());
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(getContext());
        super.onDestroy();
    }

    @Override
    public void getPatientListSuccess(List<ManagerUserBean> data) {
        for (ManagerUserBean managerUserBean : data) {
            if (medicalCardInfo!=null&&medicalCardInfo.getIdCard() != null) {
                if (medicalCardInfo.getIdCard().equals(managerUserBean.getCardId())) {
                    if (managerUserBean.getPastMedicalHistory() == null || "".equals(managerUserBean.getPastMedicalHistory()))
                        choose_patient_tv.setText("该就诊人无病史，点击前往编辑");
                    else
                        choose_patient_tv.setText(managerUserBean.getPastMedicalHistory());
                    this.managerUserBean = managerUserBean;
                    isCardPatientMatch = true;
                    break;
                } else {
                    isCardPatientMatch = false;
                    choose_patient_tv.setText("");
                }
            }else if(this.managerUserBean!=null&&this.managerUserBean.getCardId()!=null){
                if (this.managerUserBean.getCardId().equals(managerUserBean.getCardId())) {
                    if (managerUserBean.getPastMedicalHistory() == null || "".equals(managerUserBean.getPastMedicalHistory()))
                        choose_patient_tv.setText("该就诊人无病史，点击前往编辑");
                    else
                        choose_patient_tv.setText(managerUserBean.getPastMedicalHistory());
                    this.managerUserBean = managerUserBean;
                    break;
                }
            }

        }
        if (!isCardPatientMatch&&medicalCardInfo!=null)
            ToastUtil.showMessage(getContext(), "该卡没有对应的就诊人，请重新换一张就诊卡或者创建对应就诊人");
    }

    @Override
    public void getPatientListFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "匹配就诊人既往病史失败" + errMsg + "请手动选择");
    }

    @Override
    public int getImgSize() {
        return dataList.size();
    }

    @Override
    public boolean getLastItemClick(int position) {
        if (position == dataList.size())
            return true;
        return false;
    }

    /**
     * 获取图片string
     */
    private String getImageStr() {
        String imageStr = "";
        for (int i = 0; i < dataList.size(); i++) {
            if (i == 0) {
                imageStr = dataList.get(i);
            } else {
                imageStr += "," + dataList.get(i);
            }
        }
        return imageStr;
    }

    @Override
    public void uploadSuccess(String msg) {
        dataList.add(msg);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void uploadFailed(String msg) {
        ToastUtil.showMessage(getContext(), "图片上传失败" + msg);
    }

    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<>();
        if (medicalCardInfo != null) {
            map.put("eleCardNumber", medicalCardInfo.getEleCardNumber());
        }
        if (managerUserBean != null) {
            map.put("patientId", managerUserBean.getId());
        } else {
            ToastUtil.showMessage(getContext(), "暂无选中就诊人，请尝试重新选取");
            return null;
        }
        if (!"".equals(getImageStr())) {
            map.put("conditionData", getImageStr());
        } else {
          /*  ToastUtil.showMessage(getContext(), "请至少选择一张病情图片依据");
            return null;*/
        }
        if (desc_edt.getText().toString().trim().length() < 10) {
            ToastUtil.showMessage(getContext(), "病情描述至少要求写入10字，请修改后再尝试提交");
            return null;
        } else {
            map.put("conditionDesc", desc_edt.getText().toString().trim());

        }
        map.put("doctorCode", info.getUuid());

        if (DiagnosesApplyDialog.VIDEODIAGNOSES.equals(type)) {
            map.put("type", 1);
        } else {
            map.put("type", 0);
        }
        return map;
    }


    @Override
    public void getOrderInfo(PayOrderBean data) {
        orderInfo = data;
    }

    @Override
    public void getOrderInfoFailed(String msg) {
        ToastUtil.showMessage(getContext(), "获取订单失败" + msg);
    }

    @Override
    public void applyDiagnosesFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "申请失败" + errMsg);
    }

    @Override
    public boolean isHavePatient() {
        if (managerUserBean != null)
            return true;
        else
            return false;
    }

    @Override
    public ManagerUserBean getPatient() {
        return managerUserBean;
    }

    @Override
    public String getPayDesc() {
        String payDesc = "";
        if (DiagnosesApplyDialog.VIDEODIAGNOSES.equals(type)) {
            payDesc = "视频问诊-" + info.getName();
        } else {
            payDesc = "图文问诊-" + info.getName();
        }
        return payDesc;
    }

    @Override
    public String getPicUrl(int position) {
        return Const.IMAGE_HOST+dataList.get(position);
    }

    @Override
    public List<String> getPicList() {
        return dataList;
    }

    @Override
    public int getType() {
        return info.getIsDoctor();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < selectList.size(); i++) {
                        getController().uploadFile(selectList.get(i).getPath());
                    }
                    break;
            }
        }
    }
}
