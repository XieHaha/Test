package com.keydom.mianren.ih_doctor.activity.prescription_check.controller;

import android.view.View;

import com.alibaba.fastjson.JSON;
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
import com.keydom.mianren.ih_doctor.activity.prescription_check.PrescriptionActivity;
import com.keydom.mianren.ih_doctor.activity.prescription_check.view.PrescriptionView;
import com.keydom.mianren.ih_doctor.bean.DoctorPrescriptionDetailBean;
import com.keydom.mianren.ih_doctor.bean.SignPdfInfoBean;
import com.keydom.mianren.ih_doctor.m_interface.OnCheckDialogListener;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.PrescriptionService;
import com.keydom.mianren.ih_doctor.net.SignService;
import com.keydom.mianren.ih_doctor.utils.DialogUtils;
import com.keydom.mianren.ih_doctor.utils.SignUtils;
import com.keydom.mianren.ih_doctor.view.CustomPasswordDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class PrescriptionController extends ControllerImpl<PrescriptionView> implements View.OnClickListener {

    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_yes:
                //1、第一步判断是否开启电子签名
                if (MyApplication.scSignAble) {
                    getView().auditPass();
                }
                break;
            case R.id.check_no:
                DialogUtils.createCheckDialog(getContext(), new OnCheckDialogListener() {
                    @Override
                    public void commit(View v, String value) {
                        getView().auditReturn(value);
                    }
                }).show();
                break;
            default:
                break;
        }
    }

    /**
     * 获取处方详情
     */
    public void getPrescriptionDetail() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getDetailById(getView().getDetailMap()), new HttpSubscriber<DoctorPrescriptionDetailBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable DoctorPrescriptionDetailBean data) {
                getView().getDetailSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getDetailFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }


    /**
     * 签章是否注册
     */
    public void isSign() {
        //2、第二步，判断是否注册电子签章
        SignUtils.isSign(getContext(), new SignUtils.SCSignCallBack() {
            @Override
            public void signSuccess() {
                //已注册
                verifySign();
            }

            @Override
            public void signFailed() {
                //未注册电子签章
                new GeneralDialog(mContext, "您还未设置电子签章", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        SiChuanCAActivity.start(getContext());
                    }
                }).setPositiveButton("去设置").show();
            }
        });
    }

    /**
     * 签章口令
     */
    public void verifySign() {
        //3、第三步，已注册电子签章后校验签章口令（实际未校验，而是请求接口得出结果）
        CustomPasswordDialog dialog = new CustomPasswordDialog(getContext());
        dialog.setOnCommitListener(new CustomPasswordDialog.OnCommitListener() {
            @Override
            public void onCommit(String password) {
                //4、第四步，校验通过后对处方单进行处理，截图并转为pdf
                getView().handleImageAndPdf(password);
            }
        });
        dialog.show();
    }

    /**
     * 签章文件
     */
    public void signPdf(int size, String password) {
        //5、第五步，上传生成的pdf文件及签章信息进行签章
        SignPdfInfoBean infoBean = new SignPdfInfoBean();
        infoBean.setCloudCertPass(password);
        infoBean.setLlx(CommonUtils.dip2px(getContext(), 250));
        infoBean.setLly(CommonUtils.dip2px(getContext(), 215));
        infoBean.setUrx(CommonUtils.dip2px(getContext(), 350));
        infoBean.setUry(CommonUtils.dip2px(getContext(), 265));
        infoBean.setPageList(CommonUtils.getPageString(size));

        List<MultipartBody.Part> part;
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                //自定义参数
                .addFormDataPart("voString", JSON.toJSONString(infoBean));

        File file = new File(PrescriptionActivity.PDF_PATH);
        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", file.getName(), reqFile);
        part = builder.build().parts();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).signPdf(part), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                audit(data);
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
     * 药师处方审核
     *
     * @param data file id
     */
    public void audit(String data) {
        //6、第六步，签章完成后获取到的文件id再进行处方审核操作
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).audit(HttpService.INSTANCE.object2Body(getView().getAuditMap(data))), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().auditSuccess(data);
                hideLoading();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().auditFailed(msg);
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 查看签章文件
     */
    public void fetchSignedFile() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).fetchSignedFile("1440501078470377474", String.valueOf(MyApplication.userInfo.getId())), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
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
     * ca统计
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
}
