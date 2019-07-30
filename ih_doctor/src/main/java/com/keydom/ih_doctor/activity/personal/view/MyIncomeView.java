package com.keydom.ih_doctor.activity.personal.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.MyIncomeBean;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：我的收入view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface MyIncomeView extends BaseView {


    /**
     * 获取收入成功
     */
    void getWalletSuccess(MyIncomeBean bean);

    /**
     * 获取收入失败
     */
    void getWalletFailed(String errMsg);

}