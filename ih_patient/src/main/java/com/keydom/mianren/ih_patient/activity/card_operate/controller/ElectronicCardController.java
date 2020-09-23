package com.keydom.mianren.ih_patient.activity.card_operate.controller;


import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.card_operate.ElectronicCardDetailActivity;
import com.keydom.mianren.ih_patient.activity.card_operate.view.ElectronicCardView;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.AddManageUserActivity;
import com.keydom.mianren.ih_patient.bean.ElectronicCardRootBean;
import com.keydom.mianren.ih_patient.bean.MedicalCardInfo;
import com.keydom.mianren.ih_patient.net.HealthCardService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 卡控制器
 *
 * @author 顿顿
 */
public class ElectronicCardController extends ControllerImpl<ElectronicCardView> implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    /**
     * 查询电子健康卡
     */
    public void queryHealthCardList() {
        Map<String, Long> map = new HashMap<>();
        map.put("userId", App.userInfo.getId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(HealthCardService.class).queryHealthCardList(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<ElectronicCardRootBean>(getContext(), getDisposable(), true, false) {

            @Override
            public void requestComplete(@Nullable ElectronicCardRootBean data) {
                getView().queryHealthCardSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().queryHealthCardFailed(msg);
                return super.requestError(exception, code, msg);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.electronic_card_add_tv:
                Intent i = new Intent(getContext(), AddManageUserActivity.class);
                i.putExtra(AddManageUserActivity.TYPE, AddManageUserActivity.ADD);
                ActivityUtils.startActivity(i);
                break;
            case R.id.electronic_card_layout:
                ElectronicCardDetailActivity.start(getContext(), getView().getMineCardInfo());
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MedicalCardInfo cardInfo = (MedicalCardInfo) adapter.getItem(position);
        ElectronicCardDetailActivity.start(getContext(), cardInfo);
    }
}
