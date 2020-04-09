package com.keydom.mianren.ih_patient.activity.online_diagnoses_order;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.controller.DiagnosesApplyController;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.view.DiagnosesApplyView;
import com.keydom.mianren.ih_patient.adapter.GridViewPlusImgAdapter;
import com.keydom.mianren.ih_patient.bean.DoctorInfo;
import com.keydom.mianren.ih_patient.bean.DoctorMainBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.bean.PayOrderBean;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.utils.JsonUtils;
import com.keydom.mianren.ih_patient.view.CustomRecognizerDialog;
import com.keydom.mianren.ih_patient.view.DiagnosesApplyDialog;
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
    private TextView nameTv, jobTitleTv, photoDiagnosesTv, videoDiagnosesTv,
            departTv, adeptTv, inquisitionNumTv, goodEvaluateNumTv,
            averageReplyTimeTv, choosePatientCardTv, choosePatientTv, commitTv, descFontNumTv;
    private InterceptorEditText descEdt;
    private RelativeLayout mDoctorRootRl;
    private CircleImageView headImg;
    private GridViewForScrollView imgGv;
    private String type;
    private DoctorMainBean.InfoBean info;
    private DoctorInfo receptionDoctorInfo;
    private MedicalCardInfo medicalCardInfo;
    private ManagerUserBean managerUserBean;
    private boolean isCardPatientMatch = false;
    private GridViewPlusImgAdapter mAdapter;
    public List<String> dataList = new ArrayList<>();
    private PayOrderBean orderInfo;
    private ImageView mVoiceInputIv;

    // 语音听写UI
    private CustomRecognizerDialog mIatDialog;

    /**
     * 启动
     */
    public static void start(Context context, String type, DoctorMainBean.InfoBean info) {
        Intent intent = new Intent(context, DiagnosesApplyActivity.class);
        intent.putExtra("type", type);
        Bundle bundle = new Bundle();
        if (null != info) {
            bundle.putSerializable("info", info);
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = code -> {
        if (code != ErrorCode.SUCCESS) {
            Log.e("xunfei", "初始化失败，错误码：" + code + ",请点击网址https://www.xfyun" +
                    ".cn/document/error-code查询解决方案");
        }
    };

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            if (null != descEdt) {
                String text = JsonUtils.handleXunFeiJson(results);
                if (TextUtils.isEmpty(descEdt.getText().toString())) {
                    descEdt.setText(text);
                    descEdt.setSelection(descEdt.getText().length());
                } else {
                    descEdt.setText(descEdt.getText().toString() + text);
                    descEdt.setSelection(descEdt.getText().length());
                }
            }
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            ToastUtil.showMessage(DiagnosesApplyActivity.this, error.getPlainDescription(true));

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
        } else if (DiagnosesApplyDialog.VIP_DIAGNOSES.equals(type)) {
            setTitle("在线问诊");
        } else {
            setTitle("图文问诊");
        }
        nameTv = findViewById(R.id.name_tv);
        mVoiceInputIv = findViewById(R.id.diagnoses_apply_layout_voice_input_iv);
        descFontNumTv = findViewById(R.id.desc_font_num_tv);
        jobTitleTv = findViewById(R.id.job_title_tv);
        photoDiagnosesTv = findViewById(R.id.photo_diagnoses_tv);
        videoDiagnosesTv = findViewById(R.id.video_diagnoses_tv);
        departTv = findViewById(R.id.depart_tv);
        adeptTv = findViewById(R.id.adept_tv);
        inquisitionNumTv = findViewById(R.id.inquisition_num_tv);
        goodEvaluateNumTv = findViewById(R.id.good_evaluate_num_tv);
        averageReplyTimeTv = findViewById(R.id.average_reply_time_tv);
        choosePatientCardTv = findViewById(R.id.choose_patient_card_tv);
        choosePatientCardTv.setOnClickListener(getController());
        choosePatientTv = findViewById(R.id.choose_patient_tv);
        choosePatientTv.setOnClickListener(getController());
        commitTv = findViewById(R.id.commit_tv);
        commitTv.setOnClickListener(getController());
        descEdt = findViewById(R.id.desc_edt);
        mDoctorRootRl = findViewById(R.id.diagnoses_apply_layout_doctor_root_rl);
        descEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                descFontNumTv.setText(charSequence.length() + "/500");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        headImg = findViewById(R.id.head_img);
        imgGv = findViewById(R.id.img_gv);
        mAdapter = new GridViewPlusImgAdapter(this, dataList);
        imgGv.setAdapter(mAdapter);
        imgGv.setOnItemClickListener(getController());
        if (info != null) {
            mDoctorRootRl.setVisibility(View.VISIBLE);
            if (info.getIsInquiry() == 0) {
                photoDiagnosesTv.setVisibility(View.INVISIBLE);
                videoDiagnosesTv.setVisibility(View.INVISIBLE);
            } else {
                photoDiagnosesTv.setVisibility(View.VISIBLE);
                videoDiagnosesTv.setVisibility(View.VISIBLE);
            }
            jobTitleTv.setText(info.getJobTitle());
            nameTv.setText(info.getName());
            goodEvaluateNumTv.setText(info.getFeedbackRate());
            averageReplyTimeTv.setText(info.getAverageResponseTime() + "分钟");
            inquisitionNumTv.setText(info.getInquisitionAmount() + "");
            adeptTv.setText("擅长：" + info.getAdept());
            departTv.setText("科室： " + info.getDeptName());
            GlideUtils.load(headImg, info.getAvatar() == null ? "" :
                            Const.IMAGE_HOST + info.getAvatar(), 0,
                    R.mipmap.test_doctor_head_icon, false
                    , null);
        } else {
            mDoctorRootRl.setVisibility(View.GONE);
            getController().getReceptionDoctor();
        }
        EventBus.getDefault().register(getContext());

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new CustomRecognizerDialog(this, mInitListener);
        mIatDialog.setListener(mRecognizerDialogListener);
        mVoiceInputIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPremissions();
            }
        });
    }

    @SuppressLint("CheckResult")
    public void initPremissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
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
                            ToastUtil.showMessage(DiagnosesApplyActivity.this, "请开始说话…");

                        } else {
                            ToastUtil.showMessage(DiagnosesApplyActivity.this, "请开启录音需要的权限");

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
            choosePatientCardTv.setText(medicalCardInfo.getName());
            getController().getManagerUserList();
        }
        if (event.getType() == EventType.SENDSELECTDIAGNOSESPATIENT) {
            if (medicalCardInfo != null)
                medicalCardInfo = null;
            managerUserBean = (ManagerUserBean) event.getData();
            choosePatientCardTv.setText(managerUserBean.getName());
            if (managerUserBean.getPastMedicalHistory() == null || "".equals(managerUserBean.getPastMedicalHistory()))
                choosePatientTv.setText("该就诊人无病史，点击前往编辑");
            else
                choosePatientTv.setText(managerUserBean.getPastMedicalHistory());
            //            choosePatientTv.setClickable(false);
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
            choosePatientTv.setText(managerUserBean.getPastMedicalHistory());
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
            if (medicalCardInfo != null && medicalCardInfo.getIdCard() != null) {
                if (medicalCardInfo.getIdCard().equals(managerUserBean.getCardId())) {
                    if (managerUserBean.getPastMedicalHistory() == null || "".equals(managerUserBean.getPastMedicalHistory()))
                        choosePatientTv.setText("该就诊人无病史，点击前往编辑");
                    else
                        choosePatientTv.setText(managerUserBean.getPastMedicalHistory());
                    this.managerUserBean = managerUserBean;
                    isCardPatientMatch = true;
                    break;
                } else {
                    isCardPatientMatch = false;
                    choosePatientTv.setText("");
                }
            } else if (this.managerUserBean != null && this.managerUserBean.getCardId() != null) {
                if (this.managerUserBean.getCardId().equals(managerUserBean.getCardId())) {
                    if (managerUserBean.getPastMedicalHistory() == null || "".equals(managerUserBean.getPastMedicalHistory()))
                        choosePatientTv.setText("该就诊人无病史，点击前往编辑");
                    else
                        choosePatientTv.setText(managerUserBean.getPastMedicalHistory());
                    this.managerUserBean = managerUserBean;
                    break;
                }
            }

        }
        if (!isCardPatientMatch && medicalCardInfo != null)
            ToastUtil.showMessage(getContext(), "该卡没有对应的就诊人，请重新换一张就诊卡或者创建对应就诊人");
    }

    @Override
    public void getPatientListFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "匹配就诊人既往病史失败" + errMsg + "请手动选择");
    }

    @Override
    public void getReceptionDoctorSuccess(DoctorInfo doctorInfo) {
        receptionDoctorInfo = doctorInfo;
    }

    @Override
    public int getImgSize() {
        return dataList.size();
    }

    @Override
    public boolean getLastItemClick(int position) {
        return position == dataList.size();
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
            map.put("age", managerUserBean.getAge());
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
        if (descEdt.getText().toString().trim().length() < 10) {
            ToastUtil.showMessage(getContext(), "病情描述至少要求写入10字，请修改后再尝试提交");
            return null;
        } else {
            map.put("conditionDesc", descEdt.getText().toString().trim());

        }

        if (null != info) {
            map.put("doctorCode", info.getUuid());
        } else {
            if (receptionDoctorInfo != null) {
                map.put("doctorCode", receptionDoctorInfo.getUserCode());

            } else {
                map.put("doctorCode", "00152C00002");
            }
        }

        if (DiagnosesApplyDialog.VIDEODIAGNOSES.equals(type)) {
            map.put("type", 1);
        } else if (DiagnosesApplyDialog.VIP_DIAGNOSES.equals(type)) {
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
        return managerUserBean != null;
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
        } else if (DiagnosesApplyDialog.VIP_DIAGNOSES.equals(type)) {
            payDesc = "视频问诊";
        } else {
            payDesc = "图文问诊-" + info.getName();
        }
        return payDesc;
    }

    @Override
    public String getPicUrl(int position) {
        return Const.IMAGE_HOST + dataList.get(position);
    }

    @Override
    public List<String> getPicList() {
        return dataList;
    }

    @Override
    public int getType() {
        return null == info ? com.keydom.mianren.ih_patient.constant.Const.PATIENT_TYPE_ALL :
                info.getIsDoctor();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                for (int i = 0; i < selectList.size(); i++) {
                    getController().uploadFile(selectList.get(i).getPath());
                }
            }
        }
    }
}
