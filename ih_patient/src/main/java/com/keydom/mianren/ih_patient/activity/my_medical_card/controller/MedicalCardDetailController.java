package com.keydom.mianren.ih_patient.activity.my_medical_card.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.my_medical_card.view.MedicalCardDetailView;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.CardService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 我的就诊卡详情控制器
 */
public class MedicalCardDetailController extends ControllerImpl<MedicalCardDetailView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_detail_remove_bind:
                new GeneralDialog(getContext(), "您确认解绑该就诊卡？", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        MedicalCardInfo medicalCardInfo=getView().getCardObject();
                        String cardNum=(medicalCardInfo.getEleCardNumber()==null||"".equals(medicalCardInfo.getEleCardNumber())?medicalCardInfo.getEntCardNumber():medicalCardInfo.getEleCardNumber());
                        unBindCard(Global.getUserId(),cardNum,medicalCardInfo.getCardType());
                    }
                }).setTitle("提示").setPositiveButton("确定").show();


                break;

        }
    }

    /**
     * 解绑就诊卡
     */
    private void unBindCard(long userId, String cardNum, String cardType) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(CardService.class).unBindCard(userId,cardNum,cardType), new HttpSubscriber<Object>(getContext(),getDisposable(),false) {

            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().removeBindSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().removeBindFailed(msg);
                return super.requestError(exception, code, msg);

            }
        });
    }
}
