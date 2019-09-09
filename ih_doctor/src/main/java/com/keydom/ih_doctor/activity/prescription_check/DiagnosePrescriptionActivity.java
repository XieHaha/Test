package com.keydom.ih_doctor.activity.prescription_check;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.model.custom.DisposalAdviceAttachment;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_common.view.MRadioButton;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.im.ConversationActivity;
import com.keydom.ih_doctor.activity.medical_record_templet.MedicalRecordTempletActivity;
import com.keydom.ih_doctor.activity.online_diagnose.DiagnoseInputActivity;
import com.keydom.ih_doctor.activity.patient_main_suit.PatientMainSuitActivity;
import com.keydom.ih_doctor.activity.prescription_check.controller.DiagnosePrescriptionController;
import com.keydom.ih_doctor.activity.prescription_check.view.DiagnosePrescriptionView;
import com.keydom.ih_doctor.adapter.DrugDetailListAdapter;
import com.keydom.ih_doctor.adapter.PrescriptionAdapter;
import com.keydom.ih_doctor.bean.CommitPrescriptionSavedBean;
import com.keydom.ih_doctor.bean.DiagnoseHandleBean;
import com.keydom.ih_doctor.bean.DoctorPrescriptionDetailBean;
import com.keydom.ih_doctor.bean.DrugBean;
import com.keydom.ih_doctor.bean.DrugListBean;
import com.keydom.ih_doctor.bean.Event;
import com.keydom.ih_doctor.bean.InquiryBean;
import com.keydom.ih_doctor.bean.MedicalRecordTempletBean;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.bean.PrescriptionBodyBean;
import com.keydom.ih_doctor.bean.PrescriptionBottomBean;
import com.keydom.ih_doctor.bean.PrescriptionDrugDetailBean;
import com.keydom.ih_doctor.bean.PrescriptionHeadBean;
import com.keydom.ih_doctor.bean.PrescriptionMessageBean;
import com.keydom.ih_doctor.bean.PrescriptionModelBean;
import com.keydom.ih_doctor.bean.PrescriptionTemplateBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.constant.ServiceConst;
import com.keydom.ih_doctor.utils.DateUtils;
import com.keydom.ih_doctor.utils.LocalizationUtils;
import com.keydom.ih_doctor.utils.ToastUtil;
import com.keydom.ih_doctor.view.DiagnosePrescriptionItemView;
import com.keydom.ih_doctor.view.SwipeItemLayout;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private DiagnosePrescriptionItemView mainDec, medicalHistory, oversensitiveHistory, checkRes, simpleDiagnose, dealIdea;
    private RelativeLayout addPrescriptionRl, prescriptionModelRl;
    private TextView modalName, selectSave, submitWithModel, submit, feeCount;
    private CardView addMedicine;
    private RecyclerView recyclerView;
    private RelativeLayout rediagnoseRl;
    private ScrollView mScroller;
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
    private EditText handle_entrust_et;
    private Button submit_btn;
    private boolean isNeedLocalSave = true;
    private boolean isChange = false;
    private DoctorPrescriptionDetailBean localSaveData;
    private List<MultiItemEntity> dataList = new ArrayList<>();
    private List<PrescriptionTemplateBean> templateList = new ArrayList<>();
    private List<CommitPrescriptionSavedBean> commitPrescriptionSavedBeanList = new ArrayList<>();
    private boolean isHavePrescription = false;

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
     * @param context
     * @param bean    问诊单对象
     */
    public static void startHandle(Context context, InquiryBean bean) {
        Intent starter = new Intent(context, DiagnosePrescriptionActivity.class);
        starter.putExtra(Const.DATA, bean);
        starter.putExtra(Const.TYPE, CREATE_HANDLE);
        ((Activity) context).startActivityForResult(starter, ConversationActivity.SEND_MESSAGE);
    }

    /**
     * 初始化界面
     */
    private void initView() {
        mScroller=this.findViewById(R.id.mScroller);
        reDiagnoseRb = (MRadioButton) this.findViewById(R.id.re_diagnose_rb);
        rediagnoseRl = (RelativeLayout) this.findViewById(R.id.rediagnose_rl);
        recyclerView = (RecyclerView) this.findViewById(R.id.prescription_rv);
        diagnose_handle_ll = (LinearLayout) this.findViewById(R.id.diagnose_handle_ll);
        diagnose_prescription_ll = (LinearLayout) this.findViewById(R.id.diagnose_prescription_ll);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
       /* drugDetailListAdapter = new DrugDetailListAdapter(getContext(),selectDrugList, new DrugDetailListAdapter.DeleteListener() {
            @Override
            public void delete(int positon) {
                new GeneralDialog(getContext(), "确认删除该药品？", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        selectDrugList.remove(positon);
                        setDrugFee();
                        drugDetailListAdapter.setNewData(selectDrugList);
                    }
                }).setTitle("提示").setNegativeButton("取消").setPositiveButton("确认").show();

            }
        });*/

        prescriptionAdapter = new PrescriptionAdapter(this, dataList);
        recyclerView.setAdapter(prescriptionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        mainDec = (DiagnosePrescriptionItemView) this.findViewById(R.id.main_dec);
        medicalHistory = (DiagnosePrescriptionItemView) this.findViewById(R.id.medical_history);
        oversensitiveHistory = (DiagnosePrescriptionItemView) this.findViewById(R.id.oversensitive_history);
        checkRes = (DiagnosePrescriptionItemView) this.findViewById(R.id.check_res);
        simpleDiagnose = (DiagnosePrescriptionItemView) this.findViewById(R.id.simple_diagnose);
        dealIdea = (DiagnosePrescriptionItemView) this.findViewById(R.id.deal_idea);
        addPrescriptionRl = (RelativeLayout) this.findViewById(R.id.add_prescription_rl);
        prescriptionModelRl = (RelativeLayout) this.findViewById(R.id.prescription_model_rl);
        modalName = (TextView) this.findViewById(R.id.modal_name);
        selectSave = (TextView) this.findViewById(R.id.select_save);
        submitWithModel = (TextView) this.findViewById(R.id.submit_with_model);
        submit = (TextView) this.findViewById(R.id.submit);
        feeCount = (TextView) this.findViewById(R.id.fee_count);
        addMedicine = (CardView) this.findViewById(R.id.add_medicine);
        addPrescriptionRl.setOnClickListener(getController());
        prescriptionModelRl.setOnClickListener(getController());
        addMedicine.setOnClickListener(getController());
        selectSave.setOnClickListener(getController());
        submitWithModel.setOnClickListener(getController());
        submit.setOnClickListener(getController());
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
        submit_btn = this.findViewById(R.id.submit_btn);
        handle_entrust_et = this.findViewById(R.id.handle_entrust_et);
        submit_btn.setOnClickListener(getController());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_diagnose_prescription;
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
            localSaveData = (DoctorPrescriptionDetailBean) LocalizationUtils.readFileFromLocal(getContext(), fileName);
            if (localSaveData != null) {
                new GeneralDialog(getContext(), "该存在编辑信息，是否继续编辑？", new GeneralDialog.OnCloseListener() {
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
                PatientMainSuitActivity.start(DiagnosePrescriptionActivity.this, mainDec.getInputStr());
            }
        });
        simpleDiagnose.setAddOnClikListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiagnoseInputActivity.start(DiagnosePrescriptionActivity.this, simpleDiagnose.getInputStr());
            }
        });
//        setDrugFee();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 获得病历模板bean
     *
     * @param event
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
     *
     * @param event
     */
    @Subscribe
    public void getPrescriptionTemplate(Event event) {

        if (event.getType() == EventType.CHOOSE_PRESCRIPTION_TEMPLET) {
            PrescriptionDrugDetailBean bean = (PrescriptionDrugDetailBean) event.getData();
            if(prescription_type==-1){
                prescription_type = bean.getCate();
                saveData.add(bean.getItems().get(0));
                prescriptionAdapter.setNewData(packagingData(saveData));
                isHavePrescription = true;
            }else {
                if(prescription_type==bean.getCate()){
                    saveData.add(bean.getItems().get(0));
                    prescriptionAdapter.setNewData(packagingData(saveData));
                }else
                    ToastUtil.shortToast(getContext(),"选择的处方模板与当前处方类型不一致，请重新选择");
            }
        }
    }

    /**
     * 设置药品费用
     */
    private String setDrugFee(List<DrugBean> dataList) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        for (DrugBean bean : dataList) {
            bigDecimal = bigDecimal.add(bean.getPrice().multiply(new BigDecimal(bean.getQuantity())));
        }
//        feeCount.setText("￥" + bigDecimal.toString());
        return bigDecimal.toString();
    }

    /**
     * 获取处方药品
     *
     * @param event
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
            } else
                saveData.get(drugListBean.getPosition()).addAll(list);
            prescriptionAdapter.setNewData(packagingData(saveData));
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
        checkRes.setText(bean.getAuxiliaryInspect());
        simpleDiagnose.setText(bean.getInitDiagnosis());
        dealIdea.setText(bean.getHandleOpinion());
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
        this.savePrescriptionTemplate = isModel;
//        selectSave.setText(value);
    }

    @Override
    public void saveCaseModel(boolean isModel) {
        this.saveCaseTemplate = isModel;
    }

    @Override
    public boolean isSaveModel() {
        if (savePrescriptionTemplate)
            return true;
        return false;
    }


    /**
     * 0  都不存   1 存为处方模版    2  存为病历模版   3两个都存
     *
     * @return
     */
    @Override
    public Map<String, Object> getSaveMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("isReturnVisit", isReturnVisit);
        map.put("mainComplaint", mainDec.getInputStr());
        map.put("auxiliaryInspect", checkRes.getInputStr());
        map.put("handleOpinion", dealIdea.getInputStr());
        map.put("historyIllness", medicalHistory.getInputStr());
        map.put("historyAllergy", oversensitiveHistory.getInputStr());
        map.put("diagnosis", simpleDiagnose.getInputStr());
        map.put("items", getCommitItems());
        map.put("cate", prescription_type);
        map.put("dept", MyApplication.userInfo.getDeptName());
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
            CommitPrescriptionSavedBean commitPrescriptionSavedBean = new CommitPrescriptionSavedBean();
            if (templateList.get(i).isSavedAsTemplate())
                commitPrescriptionSavedBean.setIsSaveTemplate(1);
            else
                commitPrescriptionSavedBean.setIsSaveTemplate(0);
            commitPrescriptionSavedBean.setPrescriptionTemplateName(templateList.get(i).getModelNameTemp()==null?"":templateList.get(i).getModelNameTemp());
            commitPrescriptionSavedBean.setPrescriptionTemplateType(templateList.get(i).getModelTypeTemp()==null?"":templateList.get(i).getModelTypeTemp());
            List<CommitPrescriptionSavedBean.DrugSavedBean> items=new ArrayList<>();
            for (int j = 0; j < saveData.get(i).size(); j++) {
                CommitPrescriptionSavedBean.DrugSavedBean drugSavedBean=new CommitPrescriptionSavedBean.DrugSavedBean();
                drugSavedBean.setDrugsName(saveData.get(i).get(j).getDrugsName());
                drugSavedBean.setFrequency(saveData.get(i).get(j).getFrequency());
                drugSavedBean.setDosage(String.valueOf(saveData.get(i).get(j).getSingleDose()));
                drugSavedBean.setDosageUnit(saveData.get(i).get(j).getDosageUnit());
                drugSavedBean.setQuantity(saveData.get(i).get(j).getQuantity());
                drugSavedBean.setSpec(saveData.get(i).get(j).getSpec());
                drugSavedBean.setUsage(saveData.get(i).get(j).getUsage());
                drugSavedBean.setDays(saveData.get(i).get(j).getDays());
                drugSavedBean.setWay(saveData.get(i).get(j).getWay());
                drugSavedBean.setDoctorAdvice(saveData.get(i).get(j).getDoctorAdvice());
                drugSavedBean.setId(saveData.get(i).get(j).getId());
                drugSavedBean.setPackUnit(saveData.get(i).get(j).getPackUnit());
                drugSavedBean.setSingleMaximum(saveData.get(i).get(j).getSingleMaximum());
                drugSavedBean.setMaximumMedicationDays(saveData.get(i).get(j).getMaximumMedicationDays());
                items.add(drugSavedBean);
            }
            commitPrescriptionSavedBean.setItems(items);
            commitPrescriptionSavedBeanList.add(commitPrescriptionSavedBean);
        }

        return commitPrescriptionSavedBeanList;
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
        checkRes.setText(bean.getAuxiliaryInspect());
        simpleDiagnose.setText(bean.getDiagnosis());
        dealIdea.setText(bean.getHandleOpinion());
    }

    @Override
    public void saveFailed(String errMsg) {
        ToastUtil.shortToast(this, errMsg);
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
            prescriptionAdapter.setNewData(packagingData(bean.getList()));
            isHavePrescription = true;
        }
    }

    @Override
    public void getPrescriptionDetailFailed(String errMsg) {
        pageLoadingFail();
    }


    @Override
    public Map<String, Object> getHandleMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("inquiryId", inquiryBean.getId());
        map.put("suggest", handle_entrust_et.getText().toString());
        return map;
    }

    @Override
    public void handleSuccess(DiagnoseHandleBean bean) {
        List<IMMessage> messageList = new ArrayList<>();
        DisposalAdviceAttachment attachment = new DisposalAdviceAttachment();
        attachment.setId(bean.getId());
        attachment.setContent(bean.getSuggest());
        messageList.add(ImClient.createInspectionMessage(inquiryBean.getUserCode(), SessionTypeEnum.P2P, "处置建议", attachment));
        Intent intent = new Intent();
        intent.putExtra(Const.DATA, (Serializable) messageList);
        setResult(ConversationActivity.SEND_MESSAGE, intent);
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.DIAGNOSE_ORDER_UPDATE).build());
        finish();
    }

    @Override
    public void handleFailed(String errMsg) {
        ToastUtil.shortToast(this, errMsg);
    }

    @Override
    public boolean checkSubmit() {
        if ("".equals(handle_entrust_et.getText().toString())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPrescription() {
        if (StringUtils.isEmpty(simpleDiagnose.getInputStr()) || StringUtils.isEmpty(mainDec.getInputStr())) {
            ToastUtil.shortToast(getContext(), "请完善诊断信息！");
            return false;
        }
        if (saveData.size() == 0) {
            ToastUtil.shortToast(getContext(), "请添加处方！");
            return false;
        } else {
            for (int i = 0; i < saveData.size(); i++) {
                if (saveData.get(i).size() == 0) {
                    ToastUtil.shortToast(getContext(), "处方" + DateUtils.numberToCH(i + 1) + "还未添加药品！");
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
        saveData.remove(position);
        prescriptionAdapter.setNewData(packagingData(saveData));
        if (saveData.size() == 0){
            isHavePrescription = false;
            prescription_type=-1;
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
                    simpleDiagnose.setText(diagnoseStr);
                    break;
                default:
            }
        }
    }

    @Override
    public void finish() {
        if (type == CREATE_PRESCRIPTION) {
            if (isNeedLocalSave && isHaveEditInfo())
                savaLocalData();

        }
        super.finish();

    }

    private void savaLocalData() {
        localSaveData = new DoctorPrescriptionDetailBean();
        localSaveData.setMainComplaint(mainDec.getInputStr());
        localSaveData.setHistoryIllness(medicalHistory.getInputStr());
        localSaveData.setHistoryAllergy(oversensitiveHistory.getInputStr());
        localSaveData.setAuxiliaryInspect(checkRes.getInputStr());
        localSaveData.setDiagnosis(simpleDiagnose.getInputStr());
        localSaveData.setHandleOpinion(dealIdea.getInputStr());
        localSaveData.setList(saveData);
        String fileName = "diagnoseId" + inquiryBean.getId();
        LocalizationUtils.fileSave2Local(getContext(), localSaveData, fileName);
    }

    private boolean isHaveEditInfo() {
        if (!"".equals(mainDec.getInputStr()) || !"".equals(checkRes.getInputStr()) || !"".equals(dealIdea.getInputStr()) || !"".equals(medicalHistory.getInputStr()) || !"".equals(oversensitiveHistory.getInputStr()) || !"".equals(simpleDiagnose.getInputStr()) || saveData.size() != 0)
            return true;
        else
            return false;
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
            if (prescription_type == 0) {
                prescriptionHeadBean.setTitleName("处方" + DateUtils.numberToCH(i + 1) + "（儿科）");
            } else {
                prescriptionHeadBean.setTitleName("处方" + DateUtils.numberToCH(i + 1) + "（普通）");
            }
            dataList.add(prescriptionHeadBean);
            for (int j = 0; j < originalData.get(i).size(); j++) {
                PrescriptionBodyBean prescriptionBodyBean = new PrescriptionBodyBean();
                prescriptionBodyBean.setPosition(i);
                prescriptionBodyBean.setChildPosition(j);
                prescriptionBodyBean.setDrugBean(originalData.get(i).get(j));
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
    public void updateTemplateList(List<PrescriptionModelBean> prescriptionModelBeanList){
        for (int i = 0; i <templateList.size() ; i++) {
            if(prescriptionModelBeanList.get(i).getModelName()!=null&&!"".equals(prescriptionModelBeanList.get(i).getModelName())&&prescriptionModelBeanList.get(i).getModelType()!=null&&!"".equals(prescriptionModelBeanList.get(i).getModelType())){
                templateList.get(i).setSavedAsTemplate(true);
                templateList.get(i).setModelNameTemp(prescriptionModelBeanList.get(i).getModelName());
                templateList.get(i).setModelTypeTemp(prescriptionModelBeanList.get(i).getModelType());
            }
        }
    }
}
