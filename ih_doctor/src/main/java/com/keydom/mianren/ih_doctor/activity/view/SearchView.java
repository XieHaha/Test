package com.keydom.mianren.ih_doctor.activity.view;

import com.alibaba.fastjson.JSONObject;
import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_common.bean.SearchResultBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface SearchView extends BaseView {

    Map<String, Object> getSearchMap();

    Map<String, Object> getPageSearchMap();

    void searchSuccess(SearchResultBean bean);

    void searchFailed(String msg);

    void pageSearchSuccess(TypeEnum typeEnum, List<JSONObject> list);

    String getSearchKeyWord();
}