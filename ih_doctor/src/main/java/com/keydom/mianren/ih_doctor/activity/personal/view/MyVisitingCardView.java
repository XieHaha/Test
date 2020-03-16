package com.keydom.mianren.ih_doctor.activity.personal.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.UserCard;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：我的名片view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface MyVisitingCardView extends BaseView {


    /**
     * 获取名片成功
     */
    void getCardSuccess(UserCard card);

    /**
     * 获取名片失败
     */
    void getCardFailed(String errMsg);

}