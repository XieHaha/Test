package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisEvaluateView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.net.AmniocentesisService;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @date 20/3/9 16:09
 * @des 羊水穿刺预约评估
 */
public class AmniocentesisEvaluateController extends ControllerImpl<AmniocentesisEvaluateView> implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.amniocentesis_evaluate_one_fetus_layout:
                getView().onFetusSelect(1);
                break;
            case R.id.amniocentesis_evaluate_two_fetus_layout:
                getView().onFetusSelect(2);
                break;
            case R.id.amniocentesis_evaluate_more_fetus_layout:
                getView().onFetusSelect(3);
                break;
            case R.id.amniocentesis_evaluate_hiv_negative_layout:
                getView().onHivSelect(0);
                break;
            case R.id.amniocentesis_evaluate_hiv_positive_layout:
                getView().onHivSelect(1);
                break;
            case R.id.amniocentesis_evaluate_blood_negative_layout:
                getView().onBloodSelect(0);
                break;
            case R.id.amniocentesis_evaluate_blood_positive_layout:
                getView().onBloodSelect(1);
                break;
            case R.id.amniocentesis_evaluate_ultrasound_yes_layout:
                getView().onUltrasoundSelect(0);
                break;
            case R.id.amniocentesis_evaluate_ultrasound_no_layout:
                getView().onUltrasoundSelect(1);
                break;
            case R.id.amniocentesis_evaluate_hypertension_have_layout:
                getView().onHypertensionSelect(0);
                break;
            case R.id.amniocentesis_evaluate_hypertension_none_layout:
                getView().onHypertensionSelect(1);
                break;
            case R.id.amniocentesis_evaluate_diabetes_have_layout:
                getView().onDiabetesSelect(0);
                break;
            case R.id.amniocentesis_evaluate_diabetes_none_layout:
                getView().onDiabetesSelect(1);
                break;
            case R.id.amniocentesis_evaluate_next_tv:
                if (getView().isSelect()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("fetusNum", getView().getFetusSelect());
                    map.put("hivAttribute", getView().getHivSelect());
                    map.put("isGlycuresis", getView().getDiabetesSelect());
                    map.put("isHypertension", getView().getHypertensionSelect());
                    map.put("isUltrasonicException", getView().getUltrasoundSelect());
                    map.put("rhAttribute", getView().getBloodSelect());
                    //amniocentesisEvaluate(map);
                    EventBus.getDefault().post(new Event(EventType.AMNIOCENTESIS_WEB_AGREE, map));
                } else {
                    ToastUtil.showMessage(mContext, "请完成羊水穿刺预约评估");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 羊水穿刺评估
     */
    private void amniocentesisEvaluate(Map<String, Object> map) {

        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(AmniocentesisService.class)
                        .amniocentesisEvaluate(HttpService.INSTANCE.object2Body(map)),
                new HttpSubscriber<String>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable String data) {
                        getView().onAmniocentesisEvaluateSuccess();
                    }

                    @Override
                    public boolean requestError(@NotNull ApiException exception, int code,
                                                @NotNull String msg) {
                        ToastUtils.showShort(msg);
                        return super.requestError(exception, code, msg);
                    }
                });
    }

}
