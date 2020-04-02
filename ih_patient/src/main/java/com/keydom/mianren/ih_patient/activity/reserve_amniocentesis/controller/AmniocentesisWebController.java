package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.AmniocentesisRecordActivity;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisWebView;
import com.keydom.mianren.ih_patient.bean.AmniocentesisBean;
import com.keydom.mianren.ih_patient.bean.AmniocentesisReserveBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.net.AmniocentesisService;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @date 20/3/11 14:26
 * @des 羊水穿刺预约web
 */
public class AmniocentesisWebController extends ControllerImpl<AmniocentesisWebView> implements IhTitleLayout.OnRightTextClickListener, View.OnClickListener {

    @Override
    public void OnRightTextClick(View v) {
        AmniocentesisRecordActivity.start(mContext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.amniocentesis_web_agree_layout:
                getView().onReserveProtocolSelect(true);
                break;
            case R.id.amniocentesis_web_disagree_layout:
                getView().onReserveProtocolSelect(false);
                break;
            case R.id.amniocentesis_web_next_tv:
                switch (getView().getProtocol()) {
                    case AMNIOCENTESIS_WEB_RESERVE:
                        if (!getView().isSelectReserveProtocol()) {
                            ToastUtil.showMessage(mContext, "请同意以上协议内容");
                            return;
                        }
                        EventBus.getDefault().post(new Event(EventType.AMNIOCENTESIS_EVALUATE,
                                null));
                        break;
                    case AMNIOCENTESIS_AGREE_PROTOCOL:
                        if (!getView().isSelectAgreeProtocol()) {
                            ToastUtil.showMessage(mContext, "请同意以上协议内容");
                            return;
                        }
                        EventBus.getDefault().post(new Event(EventType.AMNIOCENTESIS_APPLY, null));
                        break;
                    case AMNIOCENTESIS_NOTICE:
                        if (!getView().isSelectNoticeProtocol()) {
                            ToastUtil.showMessage(mContext, "请同意以上协议内容");
                            return;
                        }
                        amniocentesisApply();
                        break;
                    default:
                        break;
                }
                break;
            case R.id.amniocentesis_web_agree_protocol_layout:
                getView().onAgreeProtocolSelect();
                break;
            case R.id.amniocentesis_web_agree_protocol_layout1:
                getView().onNoticeProtocolSelect(1);
                break;
            case R.id.amniocentesis_web_notice_layout:
                getView().onNoticeProtocolSelect(2);
                break;
            default:
                break;
        }
    }

    /**
     * 羊水穿刺预约
     */
    private void amniocentesisApply() {
        AmniocentesisReserveBean bean = getView().getReserveBean();
        if (bean == null) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("birthday", bean.getBirthday());
        map.put("endMensesTime", bean.getEndMensesTime());
        map.put("expectedBirthTime", bean.getExpectedBirthTime());
        map.put("familyMemberName", bean.getFamilyMemberName());
        map.put("familyMemberPhone", bean.getFamilyMemberPhone());
        map.put("familyAddress", bean.getFamilyAddress());
        map.put("idCard", bean.getIdCard());
        map.put("name", bean.getName());
        map.put("reason", bean.getReason());
        map.put("referralHospital", bean.getReferralHospital());
        map.put("surgeryTime", bean.getSurgeryTime());
        map.put("telephone", bean.getTelephone());
        map.put("smsCode", bean.getSmsCode());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(AmniocentesisService.class)
                        .amniocentesisApply(HttpService.INSTANCE.object2Body(map)),
                new HttpSubscriber<AmniocentesisBean>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable AmniocentesisBean data) {
                        amniocentesisEvaluate(data.getId());
                    }

                    @Override
                    public boolean requestError(@NotNull ApiException exception, int code,
                                                @NotNull String msg) {
                        ToastUtils.showShort(msg);
                        return super.requestError(exception, code, msg);
                    }
                });
    }

    /**
     * 羊水穿刺评估
     */
    private void amniocentesisEvaluate(int id) {
        Map<String, Object> map = getView().getParamsMap();
        map.put("id", id);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(AmniocentesisService.class)
                        .amniocentesisEvaluate(HttpService.INSTANCE.object2Body(map)),
                new HttpSubscriber<String>(getContext(), getDisposable(), true, false) {
                    @Override
                    public void requestComplete(@Nullable String data) {
                        EventBus.getDefault().post(new Event(EventType.AMNIOCENTESIS_RESULT, null));
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
