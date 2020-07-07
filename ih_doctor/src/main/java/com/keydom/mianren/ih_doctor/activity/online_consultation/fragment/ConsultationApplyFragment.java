package com.keydom.mianren.ih_doctor.activity.online_consultation.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.BaseFileUtils;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_consultation.controller.ConsultationApplyController;
import com.keydom.mianren.ih_doctor.activity.online_consultation.view.ConsultationApplyView;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseChangePlusImgAdapter;
import com.keydom.mianren.ih_doctor.bean.DeptDoctorBean;
import com.keydom.ih_common.bean.DoctorInfo;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.utils.DateUtils;
import com.keydom.mianren.ih_doctor.utils.JsonUtils;
import com.keydom.mianren.ih_doctor.view.CustomRecognizerDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import butterknife.BindView;

import static com.keydom.mianren.ih_doctor.activity.doctor_cooperation.SelectDoctorActivity.DOCTOR_SELECT_GROUP_CONSULTATION_RESULT;

/**
 * @date 3月24日
 * 会诊
 */
public class ConsultationApplyFragment extends BaseControllerFragment<ConsultationApplyController> implements ConsultationApplyView {
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
    @BindView(R.id.consultation_apply_grade_layout)
    RelativeLayout consultationApplyGradeLayout;
    @BindView(R.id.consultation_apply_grade_tv)
    TextView consultationApplyGradeTv;
    @BindView(R.id.consultation_apply_time_layout)
    RelativeLayout consultationApplyTimeLayout;
    @BindView(R.id.consultation_apply_time_tv)
    TextView consultationApplyTimeTv;
    @BindView(R.id.consultation_apply_commit_tv)
    TextView consultationApplyCommitTv;
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
     * 订单数据
     */
    private InquiryBean inquiryBean;

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
    private ArrayList<DeptDoctorBean> doctorList = new ArrayList<>();
    /**
     * 排班医生
     */
    private List<DoctorInfo> mdtDoctors = new ArrayList<>();
    /**
     * 存放医生布局列表
     */
    private Stack<View> mStack = new Stack<>();

    /**
     * 语音听写UI
     */
    private CustomRecognizerDialog mIatDialog, medicalDialog;

    private ArrayList<String> gradeStr = new ArrayList<String>();

    /**
     * 会诊时间
     */
    private Date mdtDate;

    /**
     * 会诊等级
     */
    private int mdtGrade = -1;

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
                @Override
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

                @Override
                public void onError(SpeechError error) {
                    ToastUtil.showMessage(getContext(), error.getPlainDescription(true));
                }

            };
    /**
     * 听写UI监听器  病情描述
     */
    private RecognizerDialogListener medicalSummaryVoiceListener = new RecognizerDialogListener() {
        @Override
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

        @Override
        public void onError(SpeechError error) {
            ToastUtil.showMessage(getContext(), error.getPlainDescription(true));
        }

    };

    @Override
    public int getLayoutRes() {
        return R.layout.activity_consultation_apply;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        gradeStr.add("紧急");
        gradeStr.add("普通");
        GlideUtils.load(consultationApplyPatientHeaderIv,
                BaseFileUtils.getHeaderUrl(inquiryBean.getUserAvatar()), 0, R.mipmap.user_icon,
                false, null);
        consultationApplyPatientNameTv.setText(inquiryBean.getName());
        consultationApplyPatientSexTv.setText(CommonUtils.getSex(inquiryBean.getSex()));
        consultationApplyPatientAgeTv.setText(inquiryBean.getAge());

        mAdapter = new DiagnoseChangePlusImgAdapter(getContext(), gridList);
        consultationApplyConditionImageGrid.setAdapter(mAdapter);
        consultationApplyConditionImageGrid.setOnItemClickListener(getController());
        consultationApplyDoctorLayout.setOnClickListener(getController());
        consultationApplyGradeLayout.setOnClickListener(getController());
        consultationApplyTimeLayout.setOnClickListener(getController());
        consultationApplyCommitTv.setOnClickListener(getController());

        initVoiceView();

        getController().getMdtSchDoctor();
    }

    /**
     * 语音输入
     */
    @SuppressLint("CheckResult")
    private void initVoiceView() {
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new CustomRecognizerDialog(getContext(), mInitListener);
        mIatDialog.setListener(transferDescriptionVoiceListener);
        consultationApplyTransferDescriptionVoice.setOnClickListener(v -> {
            RxPermissions rxPermissions = new RxPermissions(getActivity());
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

        medicalDialog = new CustomRecognizerDialog(getContext(), mInitListener);
        medicalDialog.setListener(medicalSummaryVoiceListener);
        consultationApplyMedicalSummaryVoice.setOnClickListener(v -> {
            RxPermissions rxPermissions = new RxPermissions(getActivity());
            rxPermissions.request(Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {
                            if (medicalDialog.isShowing()) {
                                medicalDialog.dismiss();
                            }
                            medicalDialog.show();
                            ToastUtil.showMessage(getContext(), "请开始说话…");
                        } else {
                            ToastUtil.showMessage(getContext(), "请开启录音需要的权限");
                        }
                    });
        });
    }

    public void setInquiryBean(InquiryBean inquiryBean) {
        this.inquiryBean = inquiryBean;
    }

    /**
     * media.getPath(); 为原图path
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < selectList.size(); i++) {
                        getController().uploadFile(selectList.get(i).getPath());
                    }
                    break;
                case DOCTOR_SELECT_GROUP_CONSULTATION_RESULT:
                    //选择会诊医生回调
                    doctorList = (ArrayList<DeptDoctorBean>) data.getSerializableExtra(Const.DATA);
                    if (doctorList.size() > 0) {
                        consultationApplyDoctorTv.setHint("");
                    } else {
                        consultationApplyDoctorTv.setHint(R.string.txt_select_consultation_doctor_hint);
                    }
                    addDoctor();
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 添加选择的医生到页面
     */
    private void addDoctor() {
        consultationApplyDoctorBoxLayout.removeAllViews();
        for (int i = 0; i < mdtDoctors.size(); i++) {
            addDoctorView(mdtDoctors.get(i).getName());
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
        LayoutInflater inflater = LayoutInflater.from(getContext());
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
            ToastUtil.showMessage(getContext(), errMsg);
        } else {
            ToastUtil.showMessage(getContext(), "图片上传失败!");
        }
    }

    @Override
    public void saveSuccess(String bean) {
        getActivity().finish();
    }

    @Override
    public void saveFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), errMsg);
    }

    @Override
    public boolean getLastItemClick(int position) {
        return position == gridList.size();
    }

    @Override
    public ArrayList<DeptDoctorBean> getSelectedDoctor() {
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
    public void requestMdtSchDoctorSuccess(List<DoctorInfo> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        mdtDoctors = data;
        addDoctor();
    }

    @Override
    public void requestMdtSchDoctorFailed(String msg) {
        ToastUtil.showMessage(getContext(), msg);
    }

    @Override
    public Map<String, Object> getOperateMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("applicantId", MyApplication.userInfo.getId());
        map.put("doctorId", getDoctorIds());
        //        map.put("illnessAbstract", consultationApplyMedicalSummaryEt.getText().toString
        //        ());
        map.put("level", mdtGrade);
        //        map.put("mdtTime", System.currentTimeMillis());
        //        map.put("medicalHistoryImgUrl", gridList);
        //        map.put("reasonAim", consultationApplyTransferDescriptionEt.getText().toString());
        map.put("userOrderId", inquiryBean.getId());
        return map;
    }

    private ArrayList<Long> getDoctorIds() {
        ArrayList<Long> ids = new ArrayList<>();
        for (DoctorInfo bean : mdtDoctors) {
            ids.add(bean.getId());
        }
        return ids;
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
    public ArrayList<String> getGradeStr() {
        return gradeStr;
    }

    @Override
    public void setGrade(int index) {
        mdtGrade = index;
        consultationApplyGradeTv.setHint("");
        consultationApplyGradeTv.setText(gradeStr.get(index));
    }

    @Override
    public void setApplyDate(Date date) {
        mdtDate = date;
        consultationApplyTimeTv.setText(String.format(getString(R.string.txt_three_value_space),
                DateUtils.dateToString(date, DateUtils.MM_DD_CH), DateUtils.getWeekString(date),
                DateUtils.dateToString(date, DateUtils.HH_MM)));
    }

    @Override
    public boolean verifyCommit() {
        //        String str = consultationApplyTransferDescriptionEt.getText().toString().trim();
        //        String str1 = consultationApplyMedicalSummaryEt.getText().toString().trim();
        //        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str1) || mdtDate == null ||
        //        mdtGrade == -1
        //                || doctorList.size() == 0) {
        //            ToastUtil.showMessage(getContext(), "请完善信息!");
        //            return false;
        //        }
        if (mdtGrade == -1) {
            ToastUtil.showMessage(getContext(), "请完善信息!");
            return false;
        }
        return true;
    }
}
