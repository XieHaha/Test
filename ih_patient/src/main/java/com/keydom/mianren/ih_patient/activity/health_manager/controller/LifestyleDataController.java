package com.keydom.mianren.ih_patient.activity.health_manager.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.view.LifestyleDataView;
import com.keydom.mianren.ih_patient.net.ChronicDiseaseService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.keydom.mianren.ih_patient.activity.health_manager.LifestyleMainActivity.LIFESTYLE_DIET;

/**
 * @author 顿顿
 * @date 20/3/4 10:56
 * @des 生活数据
 */
public class LifestyleDataController extends ControllerImpl<LifestyleDataView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lifestyle_data_select_sure_tv:
                if (getView().getLifestyleType() == LIFESTYLE_DIET) {
                    if (getView().getSelectEatBeans().size() > 0) {
                        insertOrUpdateFoodRecord();
                    } else {
                        ToastUtil.showMessage(getContext(), "请选择");
                    }
                } else {
                    if (getView().getSelectSportBeans().size() > 0) {
                        insertOrUpdateExerciseRecord();
                    } else {
                        ToastUtil.showMessage(getContext(), "请选择");
                    }

                }
                break;
            default:
                break;
        }
    }

    /**
     * 新增或者修改就餐记录
     */
    public void insertOrUpdateFoodRecord() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).insertOrUpdateFoodRecord(HttpService.INSTANCE.object2Body(getView().getEatParams())), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().updateFoodRecordSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 新增或者修改运动记录
     */
    public void insertOrUpdateExerciseRecord() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ChronicDiseaseService.class).insertOrUpdateExerciseRecord(HttpService.INSTANCE.object2Body(getView().getSelectSportBeans())), new HttpSubscriber<String>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().updateSportsRecordSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtil.showMessage(getContext(), msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
