package com.keydom.ih_patient.activity.index_main.Controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.index_main.ChooseCityActivity;
import com.keydom.ih_patient.activity.index_main.view.ChooseCityView;
import com.keydom.ih_patient.bean.CityBean;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.UserService;
import com.keydom.ih_patient.utils.DepartmentDataHelper;
import com.keydom.ih_patient.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 搜索城市控制器
 */
public class ChooseCityController extends ControllerImpl<ChooseCityView> implements View.OnClickListener {

    /**
     * 通过关键字搜索城市
     */
    public void queryCityListByKeyword(String keyWord){
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).findOrderByPinyin(keyWord), new HttpSubscriber<CityBean>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable CityBean data) {
                getView().getCityListSuccess(DepartmentDataHelper.getCityBeanAfterHandle(data));
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getCityListFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_tv:
                queryCityListByKeyword(getView().getSearchKeyWord());
                break;
            case R.id.now_location_tv:
                if(Global.getLocationCityCode()!=null&&!"".equals(Global.getLocationCityCode())){
                    Global.setSelectedCityName(Global.getLocationCity());
                    Global.setSelectedCityCode(Global.getLocationCityCode());
                    ((ChooseCityActivity)getContext()).finish();
                }else {
                    ToastUtil.shortToast(getContext(),"当前城市下暂无服务医院，请从下列城市中选择");
                }

                break;
        }
    }
}
