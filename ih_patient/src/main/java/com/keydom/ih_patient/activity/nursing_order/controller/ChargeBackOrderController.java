package com.keydom.ih_patient.activity.nursing_order.controller;

import android.app.Activity;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.nursing_order.view.ChargeBackOrderView;
import com.keydom.ih_patient.adapter.NursingChargeBackAdapter;
import com.keydom.ih_patient.bean.NursingOrderChargeBackBean;
import com.keydom.ih_patient.bean.NursingOrderChargeBackItem;
import com.keydom.ih_patient.bean.NursingOrderDetailBean;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.NursingOrderService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created date: 2018/12/18 on 17:57
 * des:订单退款控制器
 */
public class ChargeBackOrderController extends ControllerImpl<ChargeBackOrderView> implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_back:
                ((Activity) getContext()).finish();
                break;
            case R.id.confirm:
                goChargeBack(getView().getChargeBackBean());
                break;
        }
    }

    /**
     * 确认退单弹框
     */
    private void goChargeBack(NursingOrderChargeBackBean data) {
        if (data == null) {
            return;
        }
        String orderNumber = data.getOrderNum();
        String totalMoney = String.valueOf(data.getTotalMoney());
        String content = "确认退款吗？";
        if (getView().getChargeBackBean().getStatus() == NursingOrderDetailBean.STATE5 || getView().getChargeBackBean().getStatus() == NursingOrderDetailBean.STATE6){
            content = "确认退单吗？";
        }
        new GeneralDialog(getContext(), content, new GeneralDialog.OnCloseListener() {
            @Override
            public void onCommit() {
                chargeBack(orderNumber, totalMoney);
            }
        }).setTitle("提示").setPositiveButton("确认").show();
    }

    /**
     * 发起退单
     */
    private void chargeBack(String orderNumber, String totalMoney) {
        showLoading();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Global.getUserId());
        map.put("orderNumber", orderNumber);
        map.put("totalMoney", totalMoney);

        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingOrderService.class).nursingOrderChargeBack(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                String content = "退款成功";
                if (getView().getChargeBackBean().getStatus() == NursingOrderDetailBean.STATE5 || getView().getChargeBackBean().getStatus() == NursingOrderDetailBean.STATE6){
                    content = "退单成功";
                }
                new GeneralDialog(getContext(), content, new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        getView().chargeBackSuccess();
                    }
                }).setTitle("提示").setPositiveButton("确认").setNegativeButtonIsGone(true).show();
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
     * 获取退单详情
     */
    public void getChargeBackData(String orderNum) {
        showLoading();
        Map map = new HashMap();
        map.put("orderNumber", orderNum);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingOrderService.class).getNursingOrderChargeBack(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<NursingOrderChargeBackBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable NursingOrderChargeBackBean data) {
                hideLoading();
                List<MultiItemEntity> multiItems = getMultiItems(data);
                getView().getData(multiItems);
                getView().getInitBean(data);
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
     * 处理退单服务费用
     */
    private List<MultiItemEntity> getMultiItems(NursingOrderChargeBackBean bean) {
        List<MultiItemEntity> items = new ArrayList<>();
        if (bean != null) {
            bean.setChargeBackType(NursingChargeBackAdapter.TYPE_1);
            items.add(bean);
            if (bean.getCanChargeBackMoney() != null && bean.getCanChargeBackMoney().size() != 0) {
                List<NursingOrderChargeBackItem> canChargeBackMoney = bean.getCanChargeBackMoney();
                NursingOrderChargeBackBean chargeBackBean = new NursingOrderChargeBackBean();
                chargeBackBean.setFrequency(1);
                chargeBackBean.setProjectName("可退服务费用");
                chargeBackBean.setChargeBackType(NursingChargeBackAdapter.TYPE_2);
                items.add(chargeBackBean);
                for (int i = 0; i < canChargeBackMoney.size(); i++) {
                    canChargeBackMoney.get(i).setCanChargeBack(true);
                    items.add(canChargeBackMoney.get(i));
                }
            }
            if (bean.getCanNotChargeBackMoney() != null && bean.getCanNotChargeBackMoney().size() != 0) {
                List<NursingOrderChargeBackItem> canNotChargeBackMoney = bean.getCanNotChargeBackMoney();
                NursingOrderChargeBackBean chargeBackBean = new NursingOrderChargeBackBean();
                chargeBackBean.setFrequency(2);
                chargeBackBean.setProjectName("上门途中服务扣除");
                chargeBackBean.setChargeBackType(NursingChargeBackAdapter.TYPE_2);
                items.add(chargeBackBean);
                for (int i = 0; i < canNotChargeBackMoney.size(); i++) {
                    items.add(canNotChargeBackMoney.get(i));
                }
            }
            NursingOrderChargeBackBean total = new NursingOrderChargeBackBean();
            total.setChargeBackType(NursingChargeBackAdapter.TYPE_4);
            total.setTotalReturnMoney(bean.getTotalReturnMoney());
            items.add(total);
        }
        return items;
    }
}
