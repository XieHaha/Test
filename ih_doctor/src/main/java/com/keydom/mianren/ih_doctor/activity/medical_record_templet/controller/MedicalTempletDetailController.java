package com.keydom.mianren.ih_doctor.activity.medical_record_templet.controller;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_doctor.activity.medical_record_templet.view.MedicalTempletDetailView;
import com.keydom.mianren.ih_doctor.bean.MedicalRecordTempletBean;
import com.keydom.mianren.ih_doctor.bean.MedicalTempletDetailBean;
import com.keydom.mianren.ih_doctor.net.DiagnoseApiService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/8 on 15:43
 * des:
 * author: HJW HP
 */
public class MedicalTempletDetailController extends ControllerImpl<MedicalTempletDetailView> {

    /**
     * 获取病历详情
     *
     * @param id 病历ID
     */
    public void getTemplateDetail(long id) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(DiagnoseApiService.class).detailMedicalTemplate(id), new HttpSubscriber<MedicalRecordTempletBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable MedicalRecordTempletBean data) {
                hideLoading();
                List<MedicalTempletDetailBean> medicalTempletDetailBeans = transfromList(data);
                getView().templateDetailrequestCallBack(medicalTempletDetailBeans, data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 获取病历详情项目列表
     *
     * @param bean
     * @return
     */
    private List<MedicalTempletDetailBean> transfromList(MedicalRecordTempletBean bean) {
        List<MedicalTempletDetailBean> list = new ArrayList<>();
        if (bean == null) {
            return list;
        }
        list.add(createItem("主诉：", bean.getMainComplaint()));
        list.add(createItem("现病史：", bean.getHistoryIllness()));
        list.add(createItem("过敏史：", bean.getHistoryAllergy()));
        list.add(createItem("辅助检查：", bean.getAuxiliaryInspect()));
        list.add(createItem("初步诊断：", bean.getInitDiagnosis()));
        list.add(createItem("处理意见：", bean.getHandleOpinion()));
        return list;
    }


    /**
     * 创建列表项对象
     *
     * @param title
     * @param content
     * @return
     */
    private MedicalTempletDetailBean createItem(String title, String content) {
        MedicalTempletDetailBean bean = new MedicalTempletDetailBean();
        bean.setTitle(title);
        bean.setContent("");
        if (!StringUtils.isEmpty(content)) {
            bean.setContent(content);
        }
        return bean;
    }
}
