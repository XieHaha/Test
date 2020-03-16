package com.keydom.mianren.ih_patient.activity.index_main.Controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.index_main.view.IndexMainView;
import com.keydom.mianren.ih_patient.activity.member.MemberDetailActivity;
import com.keydom.mianren.ih_patient.bean.CityBean;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.UserService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 菜单控制器
 */
public class IndexMainController extends ControllerImpl<IndexMainView> implements View.OnClickListener {
    /**
     * 初始化位置
     */
    public void initLocation(String keyWord) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).findByKeyword(keyWord), new HttpSubscriber<List<CityBean.itemBean>>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable List<CityBean.itemBean> data) {
                if (data != null && data.size() != 0) {
                    if (!"".equals(keyWord))
                        Global.setLocationCityCode(data.get(0).getCode());
                    Global.setSelectedCityCode(data.get(0).getCode());
                    Global.setSelectedCityName(data.get(0).getName());
                    getView().initView();
                    getView().locationComplete();

                } else {
                    initLocation("");
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().initView();
                getView().locationComplete();
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_member:
                MemberDetailActivity.start(getContext());
                break;
        }
    }
}
