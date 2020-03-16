package com.keydom.mianren.ih_doctor.activity.personal.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.AttentionBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：我的关注view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface MyAttentionView extends BaseView {


    /**
     * 获取关注成功
     */
    void getAttentionSuccess(ArrayList<AttentionBean> attentionBeans, TypeEnum type);

    /**
     * 获取关注失败
     */
    void getAttentionFailed(String errMsg);

    /**
     * 取消成功
     */
    void cancelSuccess(int position,String msg);

    /**
     * 取消失败
     */
    void cancelFailed(String errMsg);

    /**
     * 获取搜索字段
     */
    String getSearchStr();

    /**
     * 获取刷新控件
     */
    RefreshLayout getRefreshLayout();

}