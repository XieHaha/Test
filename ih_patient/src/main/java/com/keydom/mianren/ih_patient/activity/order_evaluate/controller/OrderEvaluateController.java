package com.keydom.mianren.ih_patient.activity.order_evaluate.controller;

import android.app.Activity;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.nursing_order.SentListActivity;
import com.keydom.mianren.ih_patient.activity.order_evaluate.view.OrderEvaluateView;
import com.keydom.mianren.ih_patient.bean.DiagnosesOrderBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.NursingOrderBean;
import com.keydom.mianren.ih_patient.bean.SubscribeExaminationBean;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.NursingOrderService;
import com.keydom.mianren.ih_patient.net.ReservationService;
import com.keydom.mianren.ih_patient.net.UserService;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * created date: 2018/12/26 on 18:54
 * des:订单评论控制器
 */
public class OrderEvaluateController extends ControllerImpl<OrderEvaluateView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluate1:
                getView().onEvaluate1Click();
                break;
            case R.id.evaluate2:
                getView().onEvaluate2Click();
                break;
            case R.id.evaluate3:
                getView().onEvaluate3Click();
                break;
            case R.id.evaluate4:
                getView().onEvaluate4Click();
                break;
        }
    }

    /**
     * 提交成功弹框
     */
    private void showDialog(){
        new GeneralDialog(getContext(), getContext().getResources().getString(R.string.evaluate_success),
                () ->{
                    ((Activity)getContext()).finish();
        })
                .setPositiveButton("确认")
                .setNegativeButtonIsGone(true)
                .show();
    }

    /**
     * 提交评论
     */
    public void submitSubscribeExamEvaluate(SubscribeExaminationBean bean, int star, String evaluate) {
        showLoading();
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber", bean.getOrderNumber());
        map.put("evaluateUserId", Global.getUserId());
        map.put("starClass", star);
        map.put("evaluate", evaluate);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ReservationService.class).healthCheckComboOrderEvaluate(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                showDialog();
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
     * 护理订单评价
     */

    public void submitNursingEvaluate(NursingOrderBean bean, int star, String evaluate) {
        showLoading();
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber", bean.getOrderNumber());
        map.put("evaluateUserId", Global.getUserId());
        map.put("starClass", star);
        map.put("evaluate", evaluate);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingOrderService.class).nursingOrderEvaluate(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                ActivityUtils.finishActivity(SentListActivity.class);
                Event event = new Event(EventType.Evaluted_success,null);
                EventBus.getDefault().post(event);
                showDialog();
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
     * 问诊订单评价
     */
    public void doComment(DiagnosesOrderBean item, int star, String evaluate) {
        Map<String, Object> map =new HashMap<>();
        map.put("orderId",item.getId());
        map.put("grade",star);
        map.put("commentLabel",evaluate);

        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).doComment(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                EventBus.getDefault().post(new Event(EventType.REFRESHDIAGNOSESORDER,null));
                showDialog();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
