package com.keydom.ih_patient.activity.online_diagnoses_order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
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
import com.keydom.ih_patient.utils.ToastUtil;
import com.keydom.ih_patient.view.DiagnosesApplyDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            ToastUtil.shortToast(getContext(), "该卡没有对应的就诊人，请重新换一张就诊卡或者创建对应就诊人");
    }

    @Override
    public void getPatientListFailed(String errMsg) {
        ToastUtil.shortToast(getContext(), "匹配就诊人既往病史失败" + errMsg + "请手动选择");
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
        ToastUtil.shortToast(getContext(), "图片上传失败" + msg);
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
            ToastUtil.shortToast(getContext(), "暂无选中就诊人，请尝试重新选取");
            return null;
        }
        if (!"".equals(getImageStr())) {
            map.put("conditionData", getImageStr());
        } else {
          /*  ToastUtil.shortToast(getContext(), "请至少选择一张病情图片依据");
            return null;*/
        }
        if (desc_edt.getText().toString().trim().length() < 20) {
            ToastUtil.shortToast(getContext(), "病情描述至少要求写入20字，请修改后再尝试提交");
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
        ToastUtil.shortToast(getContext(), "获取订单失败" + msg);
    }

    @Override
    public void applyDiagnosesFailed(String errMsg) {
        ToastUtil.shortToast(getContext(), "申请失败" + errMsg);
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
