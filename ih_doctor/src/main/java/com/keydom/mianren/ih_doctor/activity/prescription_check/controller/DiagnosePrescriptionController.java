package com.keydom.mianren.ih_doctor.activity.prescription_check.controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.SiChuanCAActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.PrescriptionTempletActivity;
import com.keydom.mianren.ih_doctor.activity.prescription_check.PrescriptionActivity;
import com.keydom.mianren.ih_doctor.activity.prescription_check.view.DiagnosePrescriptionView;
import com.keydom.mianren.ih_doctor.bean.DiagnoseHandleBean;
import com.keydom.mianren.ih_doctor.bean.DoctorPrescriptionDetailBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionMessageBean;
import com.keydom.mianren.ih_doctor.bean.PrescriptionModelBean;
import com.keydom.mianren.ih_doctor.bean.SignPdfInfoBean;
import com.keydom.mianren.ih_doctor.bean.UseDrugReasonBean;
import com.keydom.mianren.ih_doctor.m_interface.OnModelAndCaseDialogListener;
import com.keydom.mianren.ih_doctor.m_interface.OnModelDialogListener;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.DiagnoseApiService;
import com.keydom.mianren.ih_doctor.net.PrescriptionService;
import com.keydom.mianren.ih_doctor.net.SignService;
import com.keydom.mianren.ih_doctor.utils.DialogUtils;
import com.keydom.mianren.ih_doctor.utils.SignUtils;
import com.keydom.mianren.ih_doctor.view.BottomAddPrescriptionDialog;
import com.keydom.mianren.ih_doctor.view.CustomPasswordDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Name???com.keydom.ih_doctor.activity.controller
 * @Author???song
 * @Date???18/11/16 ??????9:09
 * ????????????xusong
 * ???????????????18/11/16 ??????9:09
 */
public class DiagnosePrescriptionController extends ControllerImpl<DiagnosePrescriptionView> implements View.OnClickListener {
    BottomAddPrescriptionDialog dialog;
    private String modelNameTemp = "";
    private String modelTypeTemp = "";
    private String saveTemplate = "";
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
                    //??????????????????
                    getView().setPrescriptionType(1);
                    //??????????????????
                    getView().setIsOutPrescription(1);
                    getView().creatPrescription();
                }
                break;
            case R.id.prescription_model_rl:
                PrescriptionTempletActivity.start(getContext(), getView().getIsOutPrescription(),
                        getView().getPatientId());
                break;
            case R.id.submit_with_model:
                if (getView().checkPrescription()) {
                    DialogUtils.savePrescriptionAndCaseDialog(getContext(),
                            getView().getTemplateList(), new OnModelAndCaseDialogListener() {
                                @Override
                                public void dialogClick(View v, String modelType, String modelName,
                                                        List<PrescriptionModelBean> prescriptionModelBeanList) {
                                    modelNameTemp = modelName;
                                    modelTypeTemp = modelType;
                                    getView().updateTemplateList(prescriptionModelBeanList);
                                    getView().saveCaseModel(false);
                                    saveTemplate = "2";
                                    submitPrescription();
                                }
                            }).show();
                }
                break;
            case R.id.submit:
                if (getView().checkPrescription()) {
                    getView().saveCaseModel(false);
                    saveTemplate = "1";
                    submitPrescription();
                }
                break;
            case R.id.select_save:
                if (!"".equals(modelNameTemp) && !"".equals(modelTypeTemp)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("????????????");
                    builder.setMessage("?????????????????????????????????????????????");
                    builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogUtils.saveModelDialog(getContext(), new OnModelDialogListener() {
                                @Override
                                public void dialogClick(View v, String modelType,
                                                        String modelName) {
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
                //                list.add("????????????");
                //                list.add("???????????????");
                //                OptionsPickerView pvOptions = new OptionsPickerBuilder
                //                (mContext, new OnOptionsSelectListener() {
                //                    @Override
                //                    public void onOptionsSelect(int options1, int option2, int
                //                    options3, View v) {
                //                        getView().savePrescriptionModel(options1 == 0 ? true :
                //                        false, list.get(options1));
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
                    ToastUtil.showMessage(getContext(), "?????????????????????");
                }
                break;
            case R.id.drug_use_reason_tv:
                if (getView().getDrugUseReasones().size() > 0) {
                    getView().showReasonDialog();
                } else {
                    getUseDrugReason();
                }
                break;
            case R.id.layout_morbidity_date:
                KeyboardUtils.hideSoftInput((Activity) getContext());
                Calendar endDate = Calendar.getInstance();
                TimePickerView pickerView = new TimePickerBuilder(getContext(),
                        (date, v1) -> getView().setMorbidityDate(date))
                        .setRangDate(null, endDate).build();
                pickerView.show();
                break;
            default:
                break;
        }
    }

    public void submitPrescription() {
        if (getView().checkPrescription()) {
            //1??????????????????????????????????????????
            if (MyApplication.scSignAble) {
                //2?????????????????????????????????????????????
                SignUtils.isSign(getContext(), new SignUtils.SCSignCallBack() {
                    @Override
                    public void signSuccess() {
                        //?????????
                        verifySign();
                    }

                    @Override
                    public void signFailed() {
                        //?????????????????????
                        new GeneralDialog(mContext, "???????????????????????????",
                                new GeneralDialog.OnCloseListener() {
                                    @Override
                                    public void onCommit() {
                                        SiChuanCAActivity.start(getContext());
                                    }
                                }).setPositiveButton("?????????").show();
                    }
                });
            } else {
                save("");
            }
        } else {
            ToastUtil.showMessage(getContext(), "????????????????????????");
        }
    }

    /**
     * ????????????
     */
    public void verifySign() {
        //3???????????????????????????????????????????????????????????????????????????????????????????????????????????????
        CustomPasswordDialog dialog = new CustomPasswordDialog(getContext());
        dialog.setOnCommitListener(new CustomPasswordDialog.OnCommitListener() {
            @Override
            public void onCommit(String password) {
                //4????????????????????????????????????????????????????????????????????????pdf
                getView().handleImageAndPdf(password);
            }
        });
        dialog.show();
    }

    /**
     * ????????????
     */
    public void signPdf(int size, String password) {
        //5??????????????????????????????pdf?????????????????????????????????
        SignPdfInfoBean infoBean = new SignPdfInfoBean();
        infoBean.setCloudCertPass(password);
        infoBean.setLlx(CommonUtils.dip2px(getContext(), 250));
        infoBean.setLly(CommonUtils.dip2px(getContext(), 215));
        infoBean.setUrx(CommonUtils.dip2px(getContext(), 350));
        infoBean.setUry(CommonUtils.dip2px(getContext(), 265));
        infoBean.setPageList(CommonUtils.getPageString(size));

        List<MultipartBody.Part> part;
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//????????????
                //???????????????
                .addFormDataPart("voString", JSON.toJSONString(infoBean));

        File file = new File(PrescriptionActivity.PDF_PATH);
        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", file.getName(), reqFile);
        part = builder.build().parts();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).signPdf(part), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String doctorSignFileId) {
                save(doctorSignFileId);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * ??????
     */
    public void save(String doctorSignFileId) {
        Map<String, Object> map = getView().getSaveMap();
        map.put("medicaltemplateName", modelNameTemp);
        map.put("medicaltemplateType", modelTypeTemp);
        map.put("saveTemplate", saveTemplate);
        map.put("prescriptionTemplateName", modelNameTemp);
        map.put("prescriptionTemplateType", modelTypeTemp);
        if (getView().getIsOutPrescription() == -1) {
            map.put("type", "0");//?????????0
        } else {
            map.put("type", String.valueOf(getView().getIsOutPrescription()));
        }
        map.put("fee", sumDrugFee.toString());
        map.put("doctorSignFileId", doctorSignFileId);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).save(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<PrescriptionMessageBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable PrescriptionMessageBean data) {
                getView().saveSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().saveFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * ca??????
     */
    private void caCount(int type) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).caCount(type), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * ??????????????????
     */
    private void getUseDrugReason() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getUseDrugReason(), new HttpSubscriber<List<UseDrugReasonBean>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable List<UseDrugReasonBean> data) {
                getView().requestUseDrugReasonSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().requestUseDrugReasonFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * ????????????????????????????????????????????????
     */
    public void getPrescriptionDetail() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getDetailById(getView().getDetailMap()), new HttpSubscriber<DoctorPrescriptionDetailBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable DoctorPrescriptionDetailBean data) {
                getView().getPrescriptionDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getPrescriptionDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    /**
     * ??????????????????
     */
    private void doctorHandleSuggest() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).doctorHandleSuggest(getView().getHandleMap()), new HttpSubscriber<DiagnoseHandleBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable DiagnoseHandleBean data) {
                getView().handleSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().handleFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
