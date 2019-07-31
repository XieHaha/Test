package com.keydom.ih_doctor.activity.controller;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.fastjson.JSONObject;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.SearchResultBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.view.SearchView;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.MainApiService;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：公用搜索页面控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class SearchController extends ControllerImpl<SearchView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_close_tv) {
            hideSoftKeyboard(((Activity) getContext()));
            ((Activity) getContext()).finish();

        }
    }

    /**
     * 隐藏键盘<br/>
     * 结束搜索页面的时候需要调用该方法<br/>
     *
     * @param activity
     */
    public void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public void search() {
        if (Const.CHECK_AND_ACCEP || getView().getSearchKeyWord() == null || "".equals(getView().getSearchKeyWord())) {
            getView().searchSuccess(null);
            return;
        }
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).doctorSearch(getView().getSearchMap()), new HttpSubscriber<SearchResultBean>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable SearchResultBean data) {
                getView().searchSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().searchFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    public void pageSearch(TypeEnum typeEnum) {
        if (typeEnum == TypeEnum.REFRESH) {
            setCurrentPage(1);
        }
        if (getView().getSearchKeyWord() == null || "".equals(getView().getSearchKeyWord())) {
            getView().pageSearchSuccess(typeEnum, null);
            return;
        }
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).pageSearch(getView().getPageSearchMap()), new HttpSubscriber<List<JSONObject>>(getContext(), getDisposable(), true) {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable List<JSONObject> data) {
                getView().pageSearchSuccess(typeEnum, data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().searchFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
