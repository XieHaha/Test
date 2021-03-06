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
import com.keydom.ih_common.bean.DoctorInfo;
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
import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * ????????????????????????
 */
public class DiagnosesApplyActivity extends BaseControllerActivity<DiagnosesApplyController> implements DiagnosesApplyView {
    private TextView nameTv, jobTitleTv, photoDiagnosesTv, videoDiagnosesTv,
            departTv, adeptTv, inquisitionNumTv, goodEvaluateNumTv,
            averageReplyTimeTv, choosePatientCardTv, choosePatientTv, commitTv, descFontNumTv;
    private TextView addTv;
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

    // ????????????UI
    private CustomRecognizerDialog mIatDialog;

    /**
     * ??????
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
     * ?????????????????????
     */
    private InitListener mInitListener = code -> {
        if (code != ErrorCode.SUCCESS) {
            Log.e("xunfei", "??????????????????????????????" + code + ",???????????????https://www.xfyun" +
                    ".cn/document/error-code??????????????????");
        }
    };

    /**
     * ??????UI?????????
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        @Override
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
         * ??????????????????.
         */
        @Override
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
            setTitle("????????????");
        } else if (DiagnosesApplyDialog.VIP_DIAGNOSES.equals(type)) {
            setTitle("????????????");
        } else {
            setTitle("????????????");
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
        addTv = findViewById(R.id.add_tv);
        addTv.setOnClickListener(getController());
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
            averageReplyTimeTv.setText(info.getAverageResponseTime() + "??????");
            inquisitionNumTv.setText(info.getInquisitionAmount() + "");
            adeptTv.setText("?????????" + info.getAdept());
            departTv.setText("????????? " + info.getDeptName());
            GlideUtils.load(headImg, info.getAvatar() == null ? "" :
                            Const.IMAGE_HOST + info.getAvatar(), 0,
                    R.mipmap.test_doctor_head_icon, false
                    , null);
        } else {
            mDoctorRootRl.setVisibility(View.GONE);
            //            getController().getReceptionDoctor();
        }
        EventBus.getDefault().register(getContext());

        // ???????????????Dialog?????????????????????UI???????????????????????????SpeechRecognizer
        // ??????UI????????????????????????sdk??????????????????notice.txt,?????????????????????????????????
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
                            ToastUtil.showMessage(DiagnosesApplyActivity.this, "??????????????????");

                        } else {
                            ToastUtil.showMessage(DiagnosesApplyActivity.this, "??????????????????????????????");

                        }
                    }
                });

    }

    /**
     * ?????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPatientCard(Event event) {
        if (event.getType() == EventType.SENDSELECTNURSINGPATIENT) {
            medicalCardInfo = (MedicalCardInfo) event.getData();
            choosePatientCardTv.setText(medicalCardInfo.getName());
            getController().getManagerUserList();
        }
        if (event.getType() == EventType.SENDSELECTDIAGNOSESPATIENT) {
            if (medicalCardInfo != null) {
                medicalCardInfo = null;
            }
            managerUserBean = (ManagerUserBean) event.getData();
            choosePatientCardTv.setText(managerUserBean.getName());
            if (TextUtils.isEmpty(managerUserBean.getPastMedicalHistory())) {
                choosePatientTv.setText("??????????????????????????????????????????");
            } else {
                choosePatientTv.setText(managerUserBean.getPastMedicalHistory());
            }
            //            choosePatientTv.setClickable(false);
        }
    }

    /**
     * ??????????????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkPatientInfoChange(Event event) {
        if (event.getType() == EventType.CHECKPATIENTINFOCHANGE) {
            getController().getManagerUserList();
        }
    }

    /**
     * ??????????????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updatePatient(Event event) {
        if (event.getType() == EventType.UPDATEPATIENT) {
            getController().getManagerUserList();
        }
    }

    /**
     * ??????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPatientInfo(Event event) {
        if (event.getType() == EventType.SENDPATIENTINFO) {
            managerUserBean = (ManagerUserBean) event.getData();
            choosePatientTv.setText(managerUserBean.getPastMedicalHistory());
        }
    }

    @Override
    public String getPatientInputValue() {
        return descEdt.getText().toString();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(getContext());
        super.onDestroy();
    }

    @Override
    public void getPatientListSuccess(List<ManagerUserBean> data) {
        for (ManagerUserBean managerUserBean : data) {
            String history = managerUserBean.getPastMedicalHistory();
            String newHistory = "";
            if (!TextUtils.isEmpty(history)) {
                String[] histories = history.split(",");
                List<String> list = new ArrayList<>();
                for (String s : histories) {
                    if (!"???".equals(s)) {
                        list.add(s);
                    }
                }
                newHistory = StringUtil.join(list, ",");
            }
            if (medicalCardInfo != null && medicalCardInfo.getIdCard() != null) {
                if (medicalCardInfo.getIdCard().equals(managerUserBean.getCardId())) {
                    if (TextUtils.isEmpty(history)) {
                        choosePatientTv.setText("??????????????????????????????????????????");
                    } else {
                        choosePatientTv.setText(newHistory);
                    }
                    this.managerUserBean = managerUserBean;
                    isCardPatientMatch = true;
                    break;
                } else {
                    isCardPatientMatch = false;
                    choosePatientTv.setText("");
                }
            } else if (this.managerUserBean != null && this.managerUserBean.getCardId() != null) {
                if (this.managerUserBean.getCardId().equals(managerUserBean.getCardId())) {
                    if (TextUtils.isEmpty(history)) {
                        choosePatientTv.setText("??????????????????????????????????????????");
                    } else {
                        choosePatientTv.setText(newHistory);
                    }
                    this.managerUserBean = managerUserBean;
                    break;
                }
            }

        }
        if (!isCardPatientMatch && medicalCardInfo != null) {
            ToastUtil.showMessage(getContext(), "???????????????????????????????????????????????????????????????????????????????????????");
        }
    }

    @Override
    public void getPatientListFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "?????????????????????????????????" + errMsg + "???????????????");
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
     * ????????????string
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
        ToastUtil.showMessage(getContext(), "??????????????????" + msg);
    }

    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<>();
        if (medicalCardInfo != null) {
            map.put("eleCardNumber", medicalCardInfo.getEleCardNumber());
            map.put("age", medicalCardInfo.getAge());
        }
        if (managerUserBean != null) {
            map.put("patientId", managerUserBean.getId());
            map.put("age", managerUserBean.getAge());
        } else {
            ToastUtil.showMessage(getContext(), "?????????????????????????????????????????????");
            return null;
        }
        if (!"".equals(getImageStr())) {
            map.put("conditionData", getImageStr());
        } else {
            //            ToastUtil.showMessage(getContext(), "???????????????????????????????????????");
            //            return null;
        }
        if (descEdt.getText().toString().trim().length() < 10) {
            //            ToastUtil.showMessage(getContext(), "??????????????????????????????10?????????????????????????????????");
            //            return null;
        } else {
            map.put("conditionDesc", descEdt.getText().toString().trim());
        }

        if (null != info) {
            map.put("doctorCode", info.getUuid());
        } else {
            //            if (receptionDoctorInfo != null) {
            //                map.put("doctorCode", receptionDoctorInfo.getUserCode());
            //            } else {
            //                map.put("doctorCode", "00152C00002");
            //            }
            //            map.put("doctorCode", "00152C00004");
        }

        if (DiagnosesApplyDialog.VIDEODIAGNOSES.equals(type) || DiagnosesApplyDialog.VIP_DIAGNOSES.equals(type)) {
            map.put("type", 1);
            //            map.put("inquisyType", 0);
        } else {
            map.put("type", 0);
            //            map.put("inquisyType", 1);
        }
        return map;
    }


    @Override
    public void getOrderInfo(PayOrderBean data) {
        orderInfo = data;
    }

    @Override
    public void getOrderInfoFailed(String msg) {
        ToastUtil.showMessage(getContext(), "??????????????????" + msg);
    }

    @Override
    public void applyDiagnosesFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "????????????" + errMsg);
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
            payDesc = "????????????-" + info.getName();
        } else if (DiagnosesApplyDialog.VIP_DIAGNOSES.equals(type)) {
            payDesc = "????????????";
        } else {
            payDesc = "????????????-" + info.getName();
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
            } else if (requestCode == PatientMainSuitActivity.SELECT_PATIENT_MAIN_DEC) {
                String decStr = (String) data.getSerializableExtra(Const.DATA);
                descEdt.setText(decStr);
                descEdt.setSelection(decStr.length());
            }
        }
    }
}
