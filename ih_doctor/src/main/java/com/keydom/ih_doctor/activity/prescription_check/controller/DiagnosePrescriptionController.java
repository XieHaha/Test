package com.keydom.ih_doctor.activity.prescription_check.controller;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.PrescriptionTempletActivity;
import com.keydom.ih_doctor.activity.prescription_check.view.DiagnosePrescriptionView;
import com.keydom.ih_doctor.bean.DiagnoseHandleBean;
import com.keydom.ih_doctor.bean.DoctorPrescriptionDetailBean;
import com.keydom.ih_doctor.bean.PrescriptionMessageBean;
import com.keydom.ih_doctor.bean.PrescriptionModelBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.m_interface.OnModelAndCaseDialogListener;
import com.keydom.ih_doctor.m_interface.OnModelDialogListener;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.DiagnoseApiService;
import com.keydom.ih_doctor.net.PrescriptionService;
import com.keydom.ih_doctor.utils.DialogUtils;
import com.keydom.ih_doctor.utils.SignUtils;
import com.keydom.ih_doctor.view.BottomAddPrescriptionDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class DiagnosePrescriptionController extends ControllerImpl<DiagnosePrescriptionView> implements View.OnClickListener {
    BottomAddPrescriptionDialog dialog;
    private String modelNameTemp = "";
    private String modelTypeTemp = "";
    private BigDecimal sumDrugFee;

    public BigDecimal getSumDrugFee() {
        return sumDrugFee;
    }

    public void setSumDrugFee(BigDecimal sumDrugFee) {
        this.sumDrugFee = sumDrugFee;
    }

 	@SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add_prescription_rl:
                if (getView().isHavePrescription()) {
                    getView().creatPrescription();
                } else {
                    if (dialog == null) {
                        dialog = new BottomAddPrescriptionDialog(getContext());
                    }
                    dialog.setCancelListener(this);
                    dialog.reset();
                    dialog.show();

                }

                break;
            case R.id.prescription_model_rl:
                PrescriptionTempletActivity.start(getContext(),getView().getIsOutPrescription());
                break;
                //TODO : 待修改
/*            case R.id.add_medicine:
                DrugChooseActivity.start(getContext(), getView().getSelectList(), Integer.valueOf(IsPrescriptionStyle));
                Logger.e("值="+IsPrescriptionStyle);
                break;*/
            case R.id.submit_with_model:
                if (getView().checkPrescription()) {
                    DialogUtils.savePrescriptionAndCaseDialog(getContext(), getView().getTemplateList(), new OnModelAndCaseDialogListener() {
                        @Override
                        public void dialogClick(View v, String modelType, String modelName, List<PrescriptionModelBean> prescriptionModelBeanList) {
                            modelNameTemp=modelName;
                            modelTypeTemp=modelType;
                            getView().updateTemplateList(prescriptionModelBeanList);
/*                            getView().saveCaseModel(false);
                            save(modelNameTemp, modelTypeTemp, "", "","2");*/
                            SignUtils.sign(getContext(), getView().getSaveMap().toString(), Const.SIGN_CHECK_PRESCRIPTION, new SignUtils.SignCallBack() {
                                @Override
                                public void signSuccess(String signature, String jobId) {
                                    getView().saveCaseModel(false);
                                    save(modelNameTemp, modelTypeTemp, signature, jobId,"2");
                                }
                            });
                        }
                    }).show();
                }else {
                    ToastUtil.showMessage(getContext(), "请完善处方信息！");
                }
                break;
            case R.id.submit:
                if (getView().checkPrescription()) {
/*                    getView().saveCaseModel(false);
                    save(modelNameTemp, modelTypeTemp, "", "","1");*/
                    SignUtils.sign(getContext(), getView().getSaveMap().toString(), Const.SIGN_CHECK_PRESCRIPTION, new SignUtils.SignCallBack() {
                        @Override
                        public void signSuccess(String signature, String jobId) {
                            getView().saveCaseModel(false);
                            save(modelNameTemp, modelTypeTemp, signature, jobId,"1");
                        }
                    });
                }else {
                    ToastUtil.showMessage(getContext(), "请完善处方信息！");
                }


//                if (getView().isSaveModel()) {
//                    DialogUtils.saveModelDialog(getContext(), new OnModelDialogListener() {
//                        @Override
//                        public void dialogClick(View v, String modelType, String modelName) {
//                            save(modelName, modelType);
//                        }
//                    }).show();
//                } else {

//                }
                break;
            case R.id.select_save:
                if (!"".equals(modelNameTemp) && !"".equals(modelTypeTemp)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("保存模版");
                    builder.setMessage("已经保存了处方模版，是否替换！");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogUtils.saveModelDialog(getContext(), new OnModelDialogListener() {
                                @Override
                                public void dialogClick(View v, String modelType, String modelName) {
                                    getView().savePrescriptionModel(true, modelNameTemp);
                                    modelNameTemp = modelName;
                                    modelTypeTemp = modelType;
                                }
                            }).show();
                        }
                    });
                    builder.create().show();
                } else {
                    DialogUtils.saveModelDialog(getContext(), new OnModelDialogListener() {
                        @Override
                        public void dialogClick(View v, String modelType, String modelName) {
                            getView().savePrescriptionModel(true, modelNameTemp);
                            modelNameTemp = modelName;
                            modelTypeTemp = modelType;
                        }
                    }).show();
                }

//                final List<String> list = new ArrayList<>();
//                list.add("存为模版");
//                list.add("不存为模版");
//                OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
//                        getView().savePrescriptionModel(options1 == 0 ? true : false, list.get(options1));
//                    }
//                }).build();
//                pvOptions.setPicker(list);
//                pvOptions.show();
                break;
            case R.id.tv_hos:
                getView().setIsOutPrescription(0);
                break;
            case R.id.tv_wai:
                getView().setIsOutPrescription(1);
                break;
            case R.id.add_common_prescription:
                getView().addCommonPrescription();
                if (dialog != null) {
                    dialog.dismiss();
                }
                break;
            case R.id.add_paediatrics_prescription:
                getView().addPaediatricsPrescription();
                if (dialog != null) {
                    dialog.dismiss();
                }
                break;
            case R.id.submit_btn:
                if (getView().checkSubmit()) {
                    doctorHandleSuggest();
                } else {
                    ToastUtil.showMessage(getContext(), "请输入处置建议");
                }
                break;


        }

    }


    /**
     * 提交
     *
     * @param name 模版名称
     * @param type 模版类型
     */
    public void save(String name, String type, String signature, String jobId,String saveTemplate) {
        showLoading();
        Map<String, Object> map = getView().getSaveMap();
        map.put("medicaltemplateName", name);
        map.put("medicaltemplateType", type);
        map.put("saveTemplate",saveTemplate);
        map.put("signature", signature);
        map.put("signJobId", jobId);
		map.put("prescriptionTemplateName", modelNameTemp);
        map.put("prescriptionTemplateType", modelTypeTemp);
        if(getView().getIsOutPrescription() == -1){
            map.put("type", "0");//默认传0
        }else{
            map.put("type", String.valueOf(getView().getIsOutPrescription()));
        }
        map.put("fee", sumDrugFee.toString());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).save(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<PrescriptionMessageBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PrescriptionMessageBean data) {
                getView().saveSuccess(data);
                hideLoading();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().saveFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }


    /**
     * 获取处方详情，修改处方的时候调用
     */
    public void getPrescriptionDetail() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getDetailById(getView().getDetailMap()), new HttpSubscriber<DoctorPrescriptionDetailBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable DoctorPrescriptionDetailBean data) {
                hideLoading();
                getView().getPrescriptionDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getPrescriptionDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    /**
     * 提交处置建议
     */
    public void doctorHandleSuggest() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).doctorHandleSuggest(getView().getHandleMap()), new HttpSubscriber<DiagnoseHandleBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable DiagnoseHandleBean data) {
                getView().handleSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().handleFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
