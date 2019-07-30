package com.keydom.ih_doctor.activity.prescription_check.controller;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.DrugChooseActivity;
import com.keydom.ih_doctor.activity.online_diagnose.PrescriptionTempletActivity;
import com.keydom.ih_doctor.activity.prescription_check.view.DiagnosePrescriptionView;
import com.keydom.ih_doctor.bean.DiagnoseHandleBean;
import com.keydom.ih_doctor.bean.DoctorPrescriptionDetailBean;
import com.keydom.ih_doctor.bean.PrescriptionMessageBean;
import com.keydom.ih_doctor.bean.PrescriptionModelBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.m_interface.OnModelAndCaseDialogListener;
import com.keydom.ih_doctor.m_interface.OnModelDialogListener;
import com.keydom.ih_doctor.net.DiagnoseApiService;
import com.keydom.ih_doctor.net.PrescriptionService;
import com.keydom.ih_doctor.utils.DialogUtils;
import com.keydom.ih_doctor.utils.SignUtils;
import com.keydom.ih_doctor.utils.ToastUtil;
import com.keydom.ih_doctor.view.BottomAddPrescriptionDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
                    dialog.show();
                }

                break;
            case R.id.prescription_model_rl:
                PrescriptionTempletActivity.start(getContext());
                break;
            case R.id.submit_with_model:
                if (getView().checkPrescription()) {
                    DialogUtils.savePrescriptionAndCaseDialog(getContext(), getView().getTemplateList(), new OnModelAndCaseDialogListener() {
                        @Override
                        public void dialogClick(View v, String modelType, String modelName, List<PrescriptionModelBean> prescriptionModelBeanList) {
                            modelNameTemp=modelName;
                            modelTypeTemp=modelType;
                            getView().updateTemplateList(prescriptionModelBeanList);
                            SignUtils.sign(getContext(), getView().getSaveMap().toString(), Const.SIGN_CHECK_PRESCRIPTION, new SignUtils.SignCallBack() {
                                @Override
                                public void signSuccess(String signature, String jobId) {
                                    getView().saveCaseModel(false);
                                    save(modelNameTemp, modelTypeTemp, signature, jobId,"2");
                                }
                            });
                        }
                    }).show();
                }
                break;
            case R.id.submit:
                if (getView().checkPrescription()) {
                    SignUtils.sign(getContext(), getView().getSaveMap().toString(), Const.SIGN_CHECK_PRESCRIPTION, new SignUtils.SignCallBack() {
                        @Override
                        public void signSuccess(String signature, String jobId) {
                            getView().saveCaseModel(false);
                            save(modelNameTemp, modelTypeTemp, signature, jobId,"1");
                        }
                    });
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
                    ToastUtil.shortToast(getContext(), "请输入处置建议");
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
