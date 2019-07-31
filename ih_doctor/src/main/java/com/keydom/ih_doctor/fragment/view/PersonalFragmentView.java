package com.keydom.ih_doctor.fragment.view;

import android.widget.RelativeLayout;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.PersonalHomeBean;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.fragment.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 下午2:25
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:25
 */
public interface PersonalFragmentView extends BaseView {

    /**
     * 获取个人信息成功
     *
     * @param bean 个人信心对象
     */
    void getPersonalDataSuccess(PersonalHomeBean bean);

    /**
     * 获取个人信息失败
     *
     * @param errMsg 失败提示信息
     */
    void getPersonalDataFailed(String errMsg);

    /**
     * 获取请求参数
     *
     * @return
     */
    Map<String, Object> getHomeMap();

    /**
     * 隐藏完善信息按钮
     */
    void hideInfoLl();

    /**
     * 获取标题栏
     *
     * @return 标题栏对象
     */
    RelativeLayout getTitleLayout();
}
