package com.keydom.mianren.ih_doctor.activity.prescription_check;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.model.custom.DisposalAdviceAttachment;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_common.view.MRadioButton;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.im.ConversationActivity;
import com.keydom.mianren.ih_doctor.activity.medical_record_templet.MedicalRecordTempletActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.DiagnoseInputActivity;
import com.keydom.mianren.ih_doctor.activity.patient_main_suit.PatientMainSuitActivity;
import com.keydom.mianren.ih_doctor.activity.prescription_check.controller.DiagnosePrescriptionController;
import com.keydom.mianren.ih_doctor.activity.prescription_check.view.DiagnosePrescriptionView;
import com.keydom.mianren.ih_doctor.adapter.PrescriptionAdapter;
import com.keydom.mianren.ih_doctor.bean.CommitPrescriptionSavedBean;
import com.keydom.mianren.ih_doctor.bean.DiagnoseHandleBean;
import com.keydom.mianren.ih_doctor.bean.DoctorPrescriptionDetailBean;
import com.keydom.mianren.ih_doctor.bean.DrugBean;
import com.keydom.mianren.ih_doctor.bean.DrugListBean;
import com.keydom.mianren.ih_doctor.bean.Event;
import com.keydom.mianren.ih_doctor.bean.ICD10Bean;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.bean.MedicalRecordTempletBean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.bean.PrescriptionBodyBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionBottomBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionDrugDetailBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionHeadBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionMessageBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionModelBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionTemplateBean;
import com.keydom.mianren.ih_doctor.bean.UseDrugReasonBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.ServiceConst;
import com.keydom.mianren.ih_doctor.utils.DateUtils;
import com.keydom.mianren.ih_doctor.utils.LocalizationUtils;
import com.keydom.mianren.ih_doctor.view.DiagnosePrescriptionItemView;
import com.keydom.mianren.ih_doctor.view.SwipeItemLayout;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class DiagnosePrescriptionActivity extends BaseControllerActivity<DiagnosePrescriptionController> implements DiagnosePrescriptionView {
    /**
     * 修改处方
     */
    public static final int UPDATE_PRESCRIPTION = 501;
    /**
     * 创建处方
     */
    public static final int CREATE_PRESCRIPTION = 502;
    /**
     * 创建处置建议
     */
    public static final int CREATE_HANDLE = 503;
    private MRadioButton reDiagnoseRb;
    private DiagnosePrescriptionItemView mainDec, medicalHistory, oversensitiveHistory,
            epidemicHistory, checkRes,
            simpleDiagnose, dealIdea;
    private RelativeLayout addPrescriptionRl, prescriptionModelRl, addPrescriptionItemRl;
    private TextView modalName, selectSave, submitWithModel, submit, feeCount, tvMorbidityDate;
    private CardView addMedicine;
    private RecyclerView recyclerView;
    private RelativeLayout rediagnoseRl;
    private LinearLayout layoutMorbidityDate;
    private ScrollView mScroller;
    /**
     * 长期用药原因
     */
    private LinearLayout drugUseReasonLayout;
    private TextView drugUseReasonTv;
    /**
     * 用药原因数据
     */
    private List<UseDrugReasonBean> useDrugReasonBeans;
    /**
     * 用药原因 name
     */
    private ArrayList<String> drugUseReasones = new ArrayList<>();
    /**
     * 当前选择的用药原因
     */
    private String drugUseReason, drugUseReasonCode;
    /**
     * 当前用药原因position
     */
    private int curReasonPosition;
    /**
     * 是否需要填写用药原因
     */
    private boolean needDrugUseReason;
    /**
     * 问诊单ID
     */
    private long id = 0;
    private PrescriptionAdapter prescriptionAdapter;
    private List<List<DrugBean>> saveData = new ArrayList<>();
    private InquiryBean inquiryBean;
    private DoctorPrescriptionDetailBean doctorPrescriptionDetailBean;
    private int type;
    private int prescription_type = -1;
    private LinearLayout diagnose_handle_ll, diagnose_prescription_ll;
    private boolean savePrescriptionTemplate = false;
    private boolean saveCaseTemplate = false;
    private String isReturnVisit = "1";
    private DiagnosePrescriptionItemView handleEntrust;
    private Button submit_btn;
    private boolean isNeedLocalSave = true;
    private boolean isChange = false;
    private DoctorPrescriptionDetailBean localSaveData;
    private List<MultiItemEntity> dataList = new ArrayList<>();
    private List<PrescriptionTemplateBean> templateList = new ArrayList<>();
    private List<CommitPrescriptionSavedBean> commitPrescriptionSavedBeanList = new ArrayList<>();
    private boolean isHavePrescription = false;
    /**
     * 默认外延处方
     */
    private int isOutPrescription = 1;

    /**
     * 初步诊断选择结果
     */
    private ArrayList<ICD10Bean> idcItems;

    /**
     * 启动处方修改页面
     *
     * @param context
     * @param id      处方ID
     */
    public static void startUpdate(Context context, long id) {
        Intent starter = new Intent(context, DiagnosePrescriptionActivity.class);
        starter.putExtra(Const.TYPE, UPDATE_PRESCRIPTION);
        starter.putExtra(Const.DATA, id);
        ((Activity) context).startActivityForResult(starter, ConversationActivity.UPDATE_STATUS);
    }

    /**
     * 启动处方新建页面
     *
     * @param context
     * @param bean    问诊单对象
     */
    public static void startCreate(Context context, InquiryBean bean) {
        Intent starter = new Intent(context, DiagnosePrescriptionActivity.class);
        starter.putExtra(Const.TYPE, CREATE_PRESCRIPTION);
        starter.putExtra(Const.DATA, bean);
        ((Activity) context).startActivityForResult(starter, ConversationActivity.UPDATE_STATUS);
    }

    /**
     * 启动处置建议新建页面
     *
     * @param bean 问诊单对象
     */
    public static void startHandle(Context context, InquiryBean bean) {
        Intent starter = new Intent(context, DiagnosePrescriptionActivity.class);
        starter.putExtra(Const.DATA, bean);
        starter.putExtra(Const.TYPE, CREATE_HANDLE);
        ((Activity) context).startActivityForResult(starter, ConversationActivity.SEND_MESSAGE);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_diagnose_prescription;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        mScroller = findViewById(R.id.mScroller);
        reDiagnoseRb = findViewById(R.id.re_diagnose_rb);
        rediagnoseRl = findViewById(R.id.rediagnose_rl);
        recyclerView = findViewById(R.id.prescription_rv);
        diagnose_handle_ll = findViewById(R.id.diagnose_handle_ll);
        diagnose_prescription_ll = findViewById(R.id.diagnose_prescription_ll);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        prescriptionAdapter = new PrescriptionAdapter(this, dataList);
        recyclerView.setAdapter(prescriptionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        mainDec = findViewById(R.id.main_dec);
        medicalHistory = findViewById(R.id.medical_history);
        oversensitiveHistory = findViewById(R.id.oversensitive_history);
        epidemicHistory = findViewById(R.id.epidemic_history);
        tvMorbidityDate = findViewById(R.id.tv_morbidity_date);
        layoutMorbidityDate = findViewById(R.id.layout_morbidity_date);
        checkRes = findViewById(R.id.check_res);
        simpleDiagnose = findViewById(R.id.simple_diagnose);
        dealIdea = findViewById(R.id.deal_idea);
        handleEntrust = findViewById(R.id.handle_entrust_et);
        addPrescriptionRl = findViewById(R.id.add_prescription_rl);
        prescriptionModelRl = findViewById(R.id.prescription_model_rl);
        modalName = findViewById(R.id.modal_name);
        selectSave = findViewById(R.id.select_save);
        submitWithModel = findViewById(R.id.submit_with_model);
        submit = findViewById(R.id.submit);
        feeCount = findViewById(R.id.fee_count);
        addMedicine = findViewById(R.id.add_medicine);

        //长期用药
        drugUseReasonLayout = findViewById(R.id.drug_use_reason_layout);
        drugUseReasonTv = findViewById(R.id.drug_use_reason_tv);
        drugUseReasonTv.setOnClickListener(getController());

        handleEntrust.setFragmentActivity(this);
        mainDec.setFragmentActivity(this);
        medicalHistory.setFragmentActivity(this);
        oversensitiveHistory.setFragmentActivity(this);
        epidemicHistory.setFragmentActivity(this);
        checkRes.setFragmentActivity(this);
        simpleDiagnose.setFragmentActivity(this);
        dealIdea.setFragmentActivity(this);
        addPrescriptionRl.setOnClickListener(getController());
        prescriptionModelRl.setOnClickListener(getController());
        addMedicine.setOnClickListener(getController());
        selectSave.setOnClickListener(getController());
        submitWithModel.setOnClickListener(getController());
        submit.setOnClickListener(getController());
        layoutMorbidityDate.setOnClickListener(getController());
        if (reDiagnoseRb.isChecked()) {
            isReturnVisit = "1";
            initPrescription();

        } else {
            isReturnVisit = "0";
            initHandle();
        }
        if (SharePreferenceManager.getRoleId() == Const.ROLE_NURSE || SharePreferenceManager.getRoleId() == Const.ROLE_MEDICINE) {
            rediagnoseRl.setVisibility(View.GONE);
        }
        reDiagnoseRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (type == CREATE_HANDLE) {//处置建议入口进入
                    if (isChecked) {
                        isReturnVisit = "1";
                        //问诊单、医生、具有就诊卡号
                        if (inquiryBean != null && SharePreferenceManager.getRoleId() == Const.ROLE_DOCTOR && inquiryBean.getType() != 1 && inquiryBean.getCardNumber() != null && !"".equals(inquiryBean.getCardNumber())) {
                            initPrescription();
                        }
                    } else {
                        isReturnVisit = "0";
                        initHandle();
                    }
                } else {//诊断处方入口
                    if (isChecked) {
                        //处方权限
                        if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE})) {
                            //咨询单、无就诊卡、不是医生
                            if (inquiryBean == null || inquiryBean.getType() == 1 || inquiryBean.getCardNumber() == null || "".equals(inquiryBean.getCardNumber()) || SharePreferenceManager.getRoleId() != Const.ROLE_DOCTOR) {
                                reDiagnoseRb.setChecked(false);
                                return;
                            }
                            isReturnVisit = "1";
                            initPrescription();

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("未开通服务");
                            builder.setMessage("您暂未开通该服务，无法使用这个功能！");
                            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    reDiagnoseRb.setChecked(false);
                                }
                            });
                            builder.create().show();
                        }

                    } else {
                        if (type == UPDATE_PRESCRIPTION) {
                            reDiagnoseRb.setChecked(true);
                            return;
                        }
                        isReturnVisit = "0";
                        initHandle();
                    }
                }

            }
        });
        submit_btn = findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(getController());
    }

    /**
     * 处置建议界面初始化
     */
    private void initHandle() {
        setTitle("处置建议");
        setRightImgVisibility(false);
        diagnose_prescription_ll.setVisibility(View.GONE);
        diagnose_handle_ll.setVisibility(View.VISIBLE);
        reDiagnoseRb.setChecked(false);
    }

    /**
     * 新建处方界面初始化
     */
    private void initPrescription() {
        setTitle("诊断与处方");
        setRightTxt("病历模板");
        setRightImgVisibility(true);
        diagnose_prescription_ll.setVisibility(View.VISIBLE);
        diagnose_handle_ll.setVisibility(View.GONE);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initView();
        type = getIntent().getIntExtra(Const.TYPE, -1);
        if (type == CREATE_PRESCRIPTION) {
            initPrescription();
            inquiryBean = (InquiryBean) getIntent().getSerializableExtra(Const.DATA);
            String fileName = "diagnoseId" + inquiryBean.getId();
            localSaveData =
                    (DoctorPrescriptionDetailBean) LocalizationUtils.readFileFromLocal(getContext(), fileName);
            if (localSaveData != null) {
                new GeneralDialog(getContext(), "该存在编辑信息，是否继续编辑？",
                        new GeneralDialog.OnCloseListener() {
                            @Override
                            public void onCommit() {
                                getPrescriptionDetailSuccess(localSaveData);
                            }
                        }, new GeneralDialog.CancelListener() {
                    @Override
                    public void onCancel() {
                        LocalizationUtils.deleteFileFromLocal(getContext(), fileName);
                    }
                }).setTitle("提示").setPositiveButton("确认").show();
            }

        } else if (type == UPDATE_PRESCRIPTION) {
            initPrescription();
            id = getIntent().getLongExtra(Const.DATA, 0);
            getController().getPrescriptionDetail();
        } else if (type == CREATE_HANDLE) {
            initHandle();
            inquiryBean = (InquiryBean) getIntent().getSerializableExtra(Const.DATA);
        }
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                MedicalRecordTempletActivity.start(getContext());
            }
        });
        mainDec.setAddOnClikListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientMainSuitActivity.start(DiagnosePrescriptionActivity.this,
                        mainDec.getInputStr());
            }
        });
        simpleDiagnose.setAddOnClikListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiagnoseInputActivity.start(DiagnosePrescriptionActivity.this,
                        simpleDiagnose.getInputStr());
            }
        });
        //        setDrugFee();
        //咨询单不显示复诊信息
        if (inquiryBean != null && inquiryBean.getType() == 1) {
            rediagnoseRl.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 获得病历模板bean
     */
    @Subscribe
    public void getMedicalTemplate(Event event) {
        if (event.getType() == EventType.CHOOSE_MEDICAL_RECORD_TEMPLET) {
            MedicalRecordTempletBean bean = (MedicalRecordTempletBean) event.getData();
            setMedicalCaseInfo(bean);
        }
    }


    /**
     * 获取处方bean
     */
    @Subscribe
    public void getPrescriptionTemplate(Event event) {
        if (event.getType() == EventType.CHOOSE_PRESCRIPTION_TEMPLET) {
            PrescriptionDrugDetailBean bean = (PrescriptionDrugDetailBean) event.getData();
            isOutPrescription = bean.getIsOutPrescription();

            if (prescription_type == -1) {
                prescription_type = bean.getCate();
                saveData.add(bean.getItems().get(0));
                prescriptionAdapter.setNewData(packagingData(saveData));
                isHavePrescription = true;
            } else {
                if (prescription_type == bean.getCate()) {
                    saveData.add(bean.getItems().get(0));
                    prescriptionAdapter.setNewData(packagingData(saveData));
                } else {
                    ToastUtil.showMessage(getContext(), "选择的处方模板与当前处方类型不一致，请重新选择");
                }
            }
        }
    }

    /**
     * 设置药品费用
     */
    private String setDrugFee(List<DrugBean> dataList) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        for (DrugBean bean : dataList) {
            bigDecimal =
                    bigDecimal.add(bean.getPrice().multiply(new BigDecimal(bean.getQuantity())));
        }
        bigDecimal = bigDecimal.setScale(2, RoundingMode.CEILING);
        getController().setSumDrugFee(bigDecimal);
        //        feeCount.setText("￥" + bigDecimal.toString());
        return bigDecimal.toString();
    }

    /**
     * 获取处方药品
     */
    @Subscribe
    public void getDrugList(Event event) {
        if (event.getType() == EventType.CHOOSE_DRUG_LIST) {
            DrugListBean drugListBean = (DrugListBean) event.getData();
            List<DrugBean> list = drugListBean.getDrugList();

            int samePosition = -1;
            boolean isUpdate = false;
            for (int i = 0; i < saveData.get(drugListBean.getPosition()).size(); i++) {
                if (saveData.get(drugListBean.getPosition()).get(i).getId() == list.get(0).getId()) {
                    samePosition = i;
                    isUpdate = true;
                    break;
                }
            }
            if (samePosition != -1 && isUpdate) {
                saveData.get(drugListBean.getPosition()).remove(samePosition);
                saveData.get(drugListBean.getPosition()).add(samePosition, list.get(0));
            } else {
                saveData.get(drugListBean.getPosition()).addAll(list);
            }
            prescriptionAdapter.setNewData(packagingData(saveData));
            if (needDrugUseReason) {
                drugUseReasonLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置病历信息
     *
     * @param bean 病历数据
     */
    private void setMedicalCaseInfo(MedicalRecordTempletBean bean) {
        mainDec.setText(bean.getMainComplaint());
        medicalHistory.setText(bean.getHistoryIllness());
        oversensitiveHistory.setText(bean.getHistoryAllergy());
        epidemicHistory.setText(bean.getEpidemicDiseaseHistory());
        tvMorbidityDate.setText(bean.getIllnessDate());
        checkRes.setText(bean.getAuxiliaryInspect());
        simpleDiagnose.setText(bean.getInitDiagnosis());
        dealIdea.setText(bean.getHandleOpinion());
        idcItems = bean.getIdcItems();
    }

    @Override
    public ArrayList<String> getDrugUseReasones() {
        return drugUseReasones;
    }

    @Override
    public void setDrugUseReason(int index) {
        UseDrugReasonBean bean = useDrugReasonBeans.get(index);
        curReasonPosition = index;
        drugUseReason = bean.getName();
        drugUseReasonCode = bean.getCode();
        drugUseReasonTv.setText(drugUseReason);
    }

    @Override
    public void addCommonPrescription() {
        prescription_type = 1;
        creatPrescription();
    }

    @Override
    public void addPaediatricsPrescription() {
        prescription_type = 0;
        creatPrescription();
    }

    @Override
    public void savePrescriptionModel(boolean isModel, String value) {
        savePrescriptionTemplate = isModel;
        //        selectSave.setText(value);
    }

    @Override
    public void saveCaseModel(boolean isModel) {
        this.saveCaseTemplate = isModel;
    }

    @Override
    public boolean isSaveModel() {
        return savePrescriptionTemplate;
    }

    @Override
    public int getType() {
        return type;
    }

    /**
     * 0  都不存   1 存为处方模版    2  存为病历模版   3两个都存
     */
    @Override
    public Map<String, Object> getSaveMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("medicalReasonsName", drugUseReason);
        map.put("medicalReasonsCode", drugUseReasonCode);
        map.put("isReturnVisit", isReturnVisit);
        map.put("mainComplaint", mainDec.getInputStr());
        map.put("auxiliaryInspect", checkRes.getInputStr());
        map.put("handleOpinion", dealIdea.getInputStr());
        map.put("historyIllness", medicalHistory.getInputStr());
        map.put("historyAllergy", oversensitiveHistory.getInputStr());
        map.put("epidemicDiseaseHistory", epidemicHistory.getInputStr());
        map.put("illnessDate", tvMorbidityDate.getText().toString());
        map.put("diagnosis", simpleDiagnose.getInputStr());
        map.put("items", getCommitItems());
        map.put("cate", prescription_type);
        map.put("dept", MyApplication.userInfo.getDeptName());
        map.put("saveTemplate", getSaveType());
        map.put("idcItems", idcItems);
        if (type == CREATE_PRESCRIPTION && inquiryBean != null) {
            map.put("inquiryId", inquiryBean.getId());
        } else if (type == UPDATE_PRESCRIPTION) {
            map.put("inquiryId", doctorPrescriptionDetailBean.getInquiryId());
            map.put("id", id);
        }
        return map;
    }

    private List<CommitPrescriptionSavedBean> getCommitItems() {
        commitPrescriptionSavedBeanList.clear();
        for (int i = 0; i < saveData.size(); i++) {
            CommitPrescriptionSavedBean commitPrescriptionSavedBean =
                    new CommitPrescriptionSavedBean();
            if (templateList.get(i).isSavedAsTemplate()) {
                commitPrescriptionSavedBean.setIsSaveTemplate(1);
            } else {
                commitPrescriptionSavedBean.setIsSaveTemplate(0);
            }
            commitPrescriptionSavedBean.setPrescriptionTemplateName(templateList.get(i).getModelNameTemp() == null ? "" : templateList.get(i).getModelNameTemp());
            commitPrescriptionSavedBean.setPrescriptionTemplateType(templateList.get(i).getModelTypeTemp() == null ? "" : templateList.get(i).getModelTypeTemp());
            List<CommitPrescriptionSavedBean.DrugSavedBean> items = new ArrayList<>();
            for (int j = 0; j < saveData.get(i).size(); j++) {
                CommitPrescriptionSavedBean.DrugSavedBean drugSavedBean =
                        new CommitPrescriptionSavedBean.DrugSavedBean();
                DrugBean drugBean = saveData.get(i).get(j);
                drugSavedBean.setId(drugBean.getId());
                drugSavedBean.setDrugsName(drugBean.getDrugsName());
                drugSavedBean.setFrequency(drugBean.getFrequency());
                drugSavedBean.setFrequencyEnglish(drugBean.getFrequencyEnglish());
                drugSavedBean.setDosage(drugBean.getDosage());
                drugSavedBean.setSingleDosage(drugBean.getSingleDosage());
                drugSavedBean.setDosageUnit(drugBean.getDosageUnit());
                drugSavedBean.setQuantity(drugBean.getQuantity());
                drugSavedBean.setSpec(drugBean.getSpec());
                drugSavedBean.setUsage(drugBean.getUsage());
                drugSavedBean.setDays(drugBean.getDays());
                drugSavedBean.setDaysUnit(drugBean.getDaysUnit());
                drugSavedBean.setWay(drugBean.getWay());
                drugSavedBean.setWayCode(drugBean.getWayCode());
                drugSavedBean.setWayEnglish(drugBean.getWayEnglish());
                drugSavedBean.setDoctorAdvice(drugBean.getDoctorAdvice());
                drugSavedBean.setId(drugBean.getId());
                drugSavedBean.setPackUnit(drugBean.getPackUnit());
                drugSavedBean.setMaximumMedicationDays(drugBean.getMaximumMedicationDays());
                drugSavedBean.setSingleMaximum(drugBean.getSingleMaximum());
                drugSavedBean.setPrice(drugBean.getPrice());
                drugSavedBean.setDrugsCode(drugBean.getDrugsCode());
                drugSavedBean.setDrugsId(drugBean.getDrugsId());
                drugSavedBean.setSpecificationImg(drugBean.getSpecificationImg());
                drugSavedBean.setBasicUnit(drugBean.getBasicUnit());
                drugSavedBean.setManufacturerName(drugBean.getManufacturerName());
                drugSavedBean.setFee(drugBean.getFee());
                drugSavedBean.setAmount(drugBean.getAmount());
                drugSavedBean.setStock(drugBean.getStock());
                drugSavedBean.setPreparation(drugBean.getPreparation());
                //                drugSavedBean.setSeq(drugBean.getDays());
                //                drugSavedBean.setState(drugBean.getDays());
                items.add(drugSavedBean);
            }
            commitPrescriptionSavedBean.setItems(items);
            commitPrescriptionSavedBeanList.add(commitPrescriptionSavedBean);
        }

        return commitPrescriptionSavedBeanList;
    }

    private int getSaveType() {
        int i = 0;
        if (saveCaseTemplate) {
            i = i + 2;
        }
        if (savePrescriptionTemplate) {
            i = i + 1;
        }
        return i;
    }


    @Override
    public List<List<DrugBean>> getSelectList() {
        return saveData;
    }


    @Override
    public Map<String, Object> getDetailMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("hospitalId", MyApplication.userInfo.getHospitalId());
        map.put("id", id);
        return map;
    }

    /**
     * @param date 发病日期
     */
    @Override
    public void setMorbidityDate(Date date) {
        tvMorbidityDate.setText(DateUtils.dateToString(date, DateUtils.YYYY_MM_DD));
    }

    @Override
    public void saveSuccess(PrescriptionMessageBean bean) {
        isNeedLocalSave = false;
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.DIAGNOSE_ORDER_UPDATE).build());
        Intent intent = new Intent();
        setResult(ConversationActivity.FINISH_PRESCRIPTION, intent);
        finish();
    }

    /**
     * 设置处方信息
     *
     * @param bean 处方数据
     */
    private void setPrescriptionInfo(DoctorPrescriptionDetailBean bean) {
        mainDec.setText(bean.getMainComplaint());
        medicalHistory.setText(bean.getHistoryIllness());
        oversensitiveHistory.setText(bean.getHistoryAllergy());
        epidemicHistory.setText(bean.getEpidemicDiseaseHistory());
        tvMorbidityDate.setText(bean.getIllnessDate());
        checkRes.setText(bean.getAuxiliaryInspect());
        simpleDiagnose.setText(bean.getDiagnosis());
        dealIdea.setText(bean.getHandleOpinion());
        drugUseReasonTv.setText(bean.getMedicalReasonsName());
    }

    @Override
    public void saveFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    /**
     * 获取处方详情
     *
     * @param bean 处方详情数据
     */
    @Override
    public void getPrescriptionDetailSuccess(DoctorPrescriptionDetailBean bean) {
        if (bean == null) {
            return;
        }
        doctorPrescriptionDetailBean = bean;
        setPrescriptionInfo(bean);


        if (bean.getList() != null && bean.getList().size() > 0) {
            prescription_type = bean.getCate();
            saveData.clear();
            saveData = bean.getList();
            isOutPrescription = bean.getType();
            prescriptionAdapter.setNewData(packagingData(bean.getList()));
            if (needDrugUseReason) {
                drugUseReasonLayout.setVisibility(View.VISIBLE);
            }
            isHavePrescription = true;
        }
    }

    @Override
    public void getPrescriptionDetailFailed(String errMsg) {
        pageLoadingFail();
    }

    @Override
    public void requestUseDrugReasonSuccess(List<UseDrugReasonBean> data) {
        useDrugReasonBeans = data;
        drugUseReasones.clear();
        for (UseDrugReasonBean bean : data) {
            drugUseReasones.add(bean.getName());
        }
        showReasonDialog();
    }

    @Override
    public void showReasonDialog() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(),
                (options1, option2, options3, view) -> setDrugUseReason(options1)).build();
        pvOptions.setPicker(drugUseReasones);
        pvOptions.setSelectOptions(curReasonPosition);
        pvOptions.show();
    }

    @Override
    public void requestUseDrugReasonFailed(String data) {
        //用药原因获取失败
    }

    @Override
    public Map<String, Object> getHandleMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("inquiryId", inquiryBean.getId());
        map.put("suggest", handleEntrust.getInputStr());
        return map;
    }

    @Override
    public void handleSuccess(DiagnoseHandleBean bean) {
        List<IMMessage> messageList = new ArrayList<>();
        DisposalAdviceAttachment attachment = new DisposalAdviceAttachment();
        attachment.setId(bean.getId());
        attachment.setContent(bean.getSuggest());
        if (TextUtils.isEmpty(inquiryBean.getGroupTid())) {
            messageList.add(ImClient.createInspectionMessage(inquiryBean.getUserCode(),
                    SessionTypeEnum.P2P, "处置建议", attachment));
        } else {
            messageList.add(ImClient.createInspectionMessage(inquiryBean.getGroupTid(),
                    SessionTypeEnum.Team, "处置建议", attachment));
        }
        Intent intent = new Intent();
        intent.putExtra(Const.DATA, (Serializable) messageList);
        setResult(ConversationActivity.SEND_MESSAGE, intent);
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.DIAGNOSE_ORDER_UPDATE).build());
        finish();
    }

    @Override
    public void handleFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public boolean checkSubmit() {
        if ("".equals(handleEntrust.getInputStr())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPrescription() {
        if (StringUtils.isEmpty(simpleDiagnose.getInputStr()) || StringUtils.isEmpty(mainDec.getInputStr())
                || StringUtils.isEmpty(medicalHistory.getInputStr()) || StringUtils.isEmpty(epidemicHistory.getInputStr())
                || StringUtils.isEmpty(tvMorbidityDate.getText().toString())) {
            ToastUtil.showMessage(getContext(), "请完善以上信息！");
            return false;
        }
        if (needDrugUseReason && TextUtils.isEmpty(drugUseReason)) {
            ToastUtil.showMessage(getContext(), "请选择长时用药原因");
            return false;
        }
        if (saveData.size() == 0) {
            ToastUtil.showMessage(getContext(), "请添加处方！");
            return false;
        } else {
            for (int i = 0; i < saveData.size(); i++) {
                if (saveData.get(i).size() == 0) {
                    ToastUtil.showMessage(getContext(), "处方" + DateUtils.numberToCH(i + 1) +
                            "还未添加药品！");
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void removeDrug(int position, int childPosition) {
        saveData.get(position).remove(childPosition);
        prescriptionAdapter.setNewData(packagingData(saveData));
    }

    @Override
    public void removePrescription(int position) {
        //移除处方后 需要隐藏用药原因
        needDrugUseReason = false;
        drugUseReasonLayout.setVisibility(View.GONE);

        saveData.remove(position);
        prescriptionAdapter.setNewData(packagingData(saveData));
        if (saveData.size() == 0) {
            isHavePrescription = false;
            prescription_type = -1;
            //默认外延处方
            isOutPrescription = 1;
        }
    }

    @Override
    public boolean isHavePrescription() {
        return isHavePrescription;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PatientMainSuitActivity.SELECT_PATIENT_MAIN_DEC:
                    String decStr = (String) data.getSerializableExtra(Const.DATA);
                    mainDec.setText(decStr);
                    break;
                case DiagnoseInputActivity.DIAGNOSE_RES_INPUT:
                    String diagnoseStr = (String) data.getSerializableExtra(Const.DATA);
                    idcItems = (ArrayList<ICD10Bean>) data.getSerializableExtra("listData");
                    simpleDiagnose.setText(diagnoseStr);
                    break;
                default:
            }
        }
    }

    @Override
    public void finish() {
        if (type == CREATE_PRESCRIPTION) {
            if (isNeedLocalSave && isHaveEditInfo()) {
                savaLocalData();
            }
        }
        super.finish();

    }

    private void savaLocalData() {
        localSaveData = new DoctorPrescriptionDetailBean();
        localSaveData.setMainComplaint(mainDec.getInputStr());
        localSaveData.setHistoryIllness(medicalHistory.getInputStr());
        localSaveData.setHistoryAllergy(oversensitiveHistory.getInputStr());
        localSaveData.setEpidemicDiseaseHistory(epidemicHistory.getInputStr());
        localSaveData.setIllnessDate(tvMorbidityDate.getText().toString());
        localSaveData.setAuxiliaryInspect(checkRes.getInputStr());
        localSaveData.setDiagnosis(simpleDiagnose.getInputStr());
        localSaveData.setHandleOpinion(dealIdea.getInputStr());
        localSaveData.setList(saveData);
        String fileName = "diagnoseId" + inquiryBean.getId();
        LocalizationUtils.fileSave2Local(getContext(), localSaveData, fileName);
    }

    private boolean isHaveEditInfo() {
        return !"".equals(mainDec.getInputStr()) || !"".equals(checkRes.getInputStr())
                || !"".equals(dealIdea.getInputStr()) || !"".equals(medicalHistory.getInputStr())
                || !"".equals(oversensitiveHistory.getInputStr()) || !"".equals(simpleDiagnose.getInputStr())
                || !"".equals(epidemicHistory.getInputStr()) || !"".equals(tvMorbidityDate.getText().toString()) || saveData.size() != 0;
    }

    public List<MultiItemEntity> packagingData(List<List<DrugBean>> originalData) {

        dataList.clear();
        templateList.clear();
        for (int i = 0; i < originalData.size(); i++) {
            PrescriptionTemplateBean prescriptionTemplateBean = new PrescriptionTemplateBean();
            prescriptionTemplateBean.setSavedAsTemplate(false);
            templateList.add(prescriptionTemplateBean);
            PrescriptionHeadBean prescriptionHeadBean = new PrescriptionHeadBean();
            prescriptionHeadBean.setPosition(i);

            prescriptionHeadBean.setIsOutPrescription(isOutPrescription);

            if (prescription_type == 0) {
                prescriptionHeadBean.setTitleName("处方" + DateUtils.numberToCH(i + 1) + "（儿科）");
            } else {
                prescriptionHeadBean.setTitleName("处方" + DateUtils.numberToCH(i + 1) + "（普通）");
            }
            dataList.add(prescriptionHeadBean);
            for (int j = 0; j < originalData.get(i).size(); j++) {
                DrugBean drugBean = originalData.get(i).get(j);
                if (TextUtils.equals(drugBean.getDaysUnit(), "W")) {
                    if (drugBean.getDays() > 1) {
                        needDrugUseReason = true;
                    }
                } else if (TextUtils.equals(drugBean.getDaysUnit(), "T") || TextUtils.equals(drugBean.getDaysUnit(), "D")) {
                    if (drugBean.getDays() > 7) {
                        needDrugUseReason = true;
                    }
                }
                PrescriptionBodyBean prescriptionBodyBean = new PrescriptionBodyBean();
                prescriptionBodyBean.setPosition(i);
                prescriptionBodyBean.setChildPosition(j);
                prescriptionBodyBean.setDrugBean(drugBean);
                dataList.add(prescriptionBodyBean);
            }
            PrescriptionBottomBean prescriptionBottomBean = new PrescriptionBottomBean();
            prescriptionBottomBean.setFee(setDrugFee(originalData.get(i)));
            prescriptionBottomBean.setBottomPosition(i);
            dataList.add(prescriptionBottomBean);
        }
        return dataList;
    }

    @Override
    public void creatPrescription() {
        saveData.add(new ArrayList<>());
        prescriptionAdapter.setNewData(packagingData(saveData));
        mScroller.fullScroll(ScrollView.FOCUS_DOWN);
        isHavePrescription = true;
    }

    @Override
    public List<PrescriptionTemplateBean> getTemplateList() {
        return templateList;
    }

    @Override
    public void updateTemplateList(List<PrescriptionModelBean> prescriptionModelBeanList) {
        for (int i = 0; i < templateList.size(); i++) {
            if (prescriptionModelBeanList.get(i).getModelName() != null && !"".equals(prescriptionModelBeanList.get(i).getModelName()) && prescriptionModelBeanList.get(i).getModelType() != null && !"".equals(prescriptionModelBeanList.get(i).getModelType())) {
                templateList.get(i).setSavedAsTemplate(true);
                templateList.get(i).setModelNameTemp(prescriptionModelBeanList.get(i).getModelName());
                templateList.get(i).setModelTypeTemp(prescriptionModelBeanList.get(i).getModelType());
            }
        }
    }

    @Override
    public int getIsOutPrescription() {
        return isOutPrescription;
    }

    @Override
    public long getPatientId() {
        if (inquiryBean != null) {
            return inquiryBean.getPatientId();
        }
        return -1;
    }

    @Override
    public void setIsOutPrescription(int isOutPrescription) {
        this.isOutPrescription = isOutPrescription;
    }
}
