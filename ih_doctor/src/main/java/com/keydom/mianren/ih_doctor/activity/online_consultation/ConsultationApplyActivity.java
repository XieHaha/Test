package com.keydom.mianren.ih_doctor.activity.online_consultation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.DiagnoseCommonActivity;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationApplyController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationApplyView;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseChangePlusImgAdapter;
import com.keydom.mianren.ih_doctor.bean.DeptDoctorBean;
import com.keydom.mianren.ih_doctor.bean.DiagnoseFillOutResBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.utils.JsonUtils;
import com.keydom.mianren.ih_doctor.view.CustomRecognizerDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import butterknife.BindView;

/**
 * @date 3月24日
 * 会诊
 */
public class ConsultationApplyActivity extends BaseControllerActivity<ConsultationApplyController> implements ConsultationApplyView {
    @BindView(R.id.consultation_apply_patient_header_iv)
    ImageView consultationApplyPatientHeaderIv;
    @BindView(R.id.consultation_apply_patient_name_tv)
    TextView consultationApplyPatientNameTv;
    @BindView(R.id.consultation_apply_patient_age_tv)
    TextView consultationApplyPatientAgeTv;
    @BindView(R.id.consultation_apply_patient_sex_tv)
    TextView consultationApplyPatientSexTv;
    @BindView(R.id.consultation_apply_doctor_box_layout)
    LinearLayout consultationApplyDoctorBoxLayout;
    @BindView(R.id.consultation_apply_doctor_tv)
    TextView consultationApplyDoctorTv;
    @BindView(R.id.consultation_apply_doctor_layout)
    RelativeLayout consultationApplyDoctorLayout;
    @BindView(R.id.consultation_apply_grade_tv)
    TextView consultationApplyGradeTv;
    @BindView(R.id.consultation_apply_inquiry_order_tv)
    TextView consultationApplyInquiryOrderTv;
    @BindView(R.id.consultation_apply_transfer_description_et)
    InterceptorEditText consultationApplyTransferDescriptionEt;
    @BindView(R.id.consultation_apply_transfer_description_voice)
    ImageView consultationApplyTransferDescriptionVoice;
    @BindView(R.id.consultation_apply_medical_summary_et)
    InterceptorEditText consultationApplyMedicalSummaryEt;
    @BindView(R.id.consultation_apply_medical_summary_voice)
    ImageView consultationApplyMedicalSummaryVoice;
    @BindView(R.id.consultation_apply_condition_image_grid)
    GridViewForScrollView consultationApplyConditionImageGrid;
    /**
     * 判断是在线问诊发起的转诊还是医生协作发起的转诊（在线问诊发起的转诊自动带了问诊单对象过来）
     */
    private int mType;
    /**
     * 图片适配器
     */
    private DiagnoseChangePlusImgAdapter mAdapter;
    /**
     * 选中的图片地址列表
     */
    private List<String> gridList = new ArrayList<>();
    /**
     * 科室医生列表
     */
    private List<DeptDoctorBean> doctorList = new ArrayList<>();
    /**
     * 存放医生布局列表
     */
    private Stack<View> mStack = new Stack<>();
    /**
     * 存放问诊单布局列表
     */
    private Stack<View> orderStack = new Stack<>();

    /**
     * 语音听写UI
     */
    private CustomRecognizerDialog mIatDialog, medicalDialog;


    /**
     * 病情资料限制上传的最大图片数量
     */
    public static final int MAX_IMAGE = 9;
    /**
     * 在线问诊转诊申请
     */
    public static final int DIAGNOSE_FILLOUT_APPLY = 1200;
    /**
     * 医生协作转诊申请
     */
    public static final int DOCTOR_GOURP_FILLOUT_APPLY = 1201;

    public static void start(Context context) {
        Intent intent = new Intent(context, ConsultationApplyActivity.class);
        context.startActivity(intent);
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

    /**
     * 听写UI监听器  会诊理由及目的
     */
    private RecognizerDialogListener transferDescriptionVoiceListener =
            new RecognizerDialogListener() {
                public void onResult(RecognizerResult results, boolean isLast) {
                    if (null != consultationApplyTransferDescriptionEt) {
                        String text = JsonUtils.handleXunFeiJson(results);
                        String oldText =
                                consultationApplyTransferDescriptionEt.getText().toString().trim();
                        StringBuilder builder = new StringBuilder(oldText);
                        builder.append(text);
                        consultationApplyTransferDescriptionEt.setText(builder.toString());
                        consultationApplyTransferDescriptionEt.setSelection(builder.toString().length());
                    }
                }

                public void onError(SpeechError error) {
                    ToastUtil.showMessage(ConsultationApplyActivity.this,
                            error.getPlainDescription(true));
                }

            };
    /**
     * 听写UI监听器  病情描述
     */
    private RecognizerDialogListener medicalSummaryVoiceListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            if (null != consultationApplyMedicalSummaryEt) {
                String text = JsonUtils.handleXunFeiJson(results);
                String oldText = consultationApplyMedicalSummaryEt.getText().toString().trim();
                StringBuilder builder = new StringBuilder(oldText);
                builder.append(text);
                consultationApplyMedicalSummaryEt.setText(builder.toString());
                consultationApplyMedicalSummaryEt.setSelection(builder.toString().length());
            }
        }

        public void onError(SpeechError error) {
            ToastUtil.showMessage(ConsultationApplyActivity.this, error.getPlainDescription(true));
        }

    };

    @Override
    public int getLayoutRes() {
        return R.layout.activity_consultation_apply;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(Const.TYPE, 0);
        initView();
        setTitle("申请");
        setRightTxt("提交");
        setRightBtnListener(v -> {
            if (doctorList == null || doctorList.size() <= 0) {
                ToastUtil.showMessage(this, "请选择医生");
                return;
            }
            String str =
                    CommonUtils.filterEmoji(consultationApplyTransferDescriptionEt.getText().toString().trim());
            if (str == null || str.length() < 20) {
                ToastUtil.showMessage(this, "转诊说明至少20字!");
                return;
            }
            getController().submit();
        });
    }

    @SuppressLint("CheckResult")
    private void initView() {
        mAdapter = new DiagnoseChangePlusImgAdapter(this, gridList);
        consultationApplyConditionImageGrid.setAdapter(mAdapter);
        consultationApplyConditionImageGrid.setOnItemClickListener(getController());
        consultationApplyDoctorTv.setOnClickListener(getController());

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new CustomRecognizerDialog(this, mInitListener);
        mIatDialog.setListener(transferDescriptionVoiceListener);
        consultationApplyTransferDescriptionVoice.setOnClickListener(v -> {
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
                            ToastUtil.showMessage(this, "请开始说话…");
                        } else {
                            ToastUtil.showMessage(this, "请开启录音需要的权限");
                        }
                    });
        });

        medicalDialog = new CustomRecognizerDialog(this, mInitListener);
        medicalDialog.setListener(medicalSummaryVoiceListener);
        consultationApplyMedicalSummaryVoice.setOnClickListener(v -> {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {
                            if (medicalDialog.isShowing()) {
                                medicalDialog.dismiss();
                            }
                            medicalDialog.show();
                            ToastUtil.showMessage(this, "请开始说话…");
                        } else {
                            ToastUtil.showMessage(this, "请开启录音需要的权限");
                        }
                    });
        });
    }

    /**
     * media.getPath(); 为原图path
     */
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
                case Const.DOCTOR_SLEECT_ONLY_RESULT:
                    break;
            }
        }
    }


    /**
     * 添加选择的医生到页面
     */
    private void addDoctor() {
        for (int i = 0; i < doctorList.size(); i++) {
            addDoctorView(doctorList.get(i).getName());
        }
    }

    /**
     * 界面上移除所有选择的医生
     */
    private void removeView() {
        for (int i = 0; i < mStack.size(); i++) {
            consultationApplyDoctorBoxLayout.removeView(mStack.get(i));
        }
    }

    /**
     * 界面上添加选中的医生
     */
    private void addDoctorView(String name) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.fill_out_doctor_tag, null, true);
        TextView tagTv = view.findViewById(R.id.doctor_name);
        tagTv.setText(name);
        consultationApplyDoctorBoxLayout.addView(view);
        mStack.push(view);
    }


    @Override
    public void uploadSuccess(String msg) {
        gridList.add(msg);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void uploadFailed(String errMsg) {
        if (errMsg == null || "".equals(errMsg.trim())) {
            ToastUtil.showMessage(this, errMsg);
        } else {
            ToastUtil.showMessage(this, "图片上传失败!");
        }
    }

    @Override
    public void saveSuccess(DiagnoseFillOutResBean bean) {
        if (mType == DIAGNOSE_FILLOUT_APPLY) {

            finish();
        } else {
            DiagnoseCommonActivity.startDiagnoseChangeRecoder(getContext());
            finish();
        }
    }

    @Override
    public void saveFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public boolean getLastItemClick(int position) {
        return position == gridList.size();
    }

    @Override
    public List<DeptDoctorBean> getSelectedDoctor() {
        return doctorList;
    }

    /**
     * 按照接口规则拼接上传的图片字符串
     */
    private String getImgStr() {
        //选中的图片拼接字符串
        String imgStr = "";
        for (String str : gridList) {
            if ("".equals(imgStr)) {
                imgStr = str;
            } else {
                imgStr = imgStr + "," + str;
            }
        }
        return imgStr;
    }

    @Override
    public Map<String, Object> getOperateMap() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    @Override
    public int getImgSize() {
        return gridList.size();
    }

    @Override
    public void deleteImg(int position) {
        gridList.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public List<String> getImgList() {
        return gridList;
    }

    @Override
    public int getDoctorType() {
        if (doctorList != null && doctorList.size() == 1) {
            return doctorList.get(0).getProjectStatus();
        }
        return 3;
    }
}
