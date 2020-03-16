package com.keydom.mianren.ih_doctor.activity.personal.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.DeptBean;
import com.keydom.mianren.ih_doctor.bean.PersonalInfoBean;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：个人信息view
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface PersonalInfoView extends BaseView {
    /**
     * 获取个人信息成功
     */
    void getInfoSuccess(PersonalInfoBean bean);

    /**
     * 获取个人信息失败
     */
    void getInfoFailed(String errMsg);

    /**
     * 更新信息成功
     */
    void updateInfoSuccess(String msg);

    /**
     * 更新失败
     */
    void updateInfoFailed(String errMsg);

    /**
     *上传图片成功
     */
    void uploadImgSuccess(String path,int type);

    /**
     * 上传失败
     */
    void uploadImgFailed(String errMsg);

    /**
     * 获取描述
     */
    String getDec();

    /**
     * 获取擅长
     */
    String getGoodBe();

    /**
     * 获取名字
     */
    String getName();

    /**
     * 设置性别
     */
    void setSex(int sex);

    /**
     * 设置科室
     */
    void setDept(DeptBean dept);

    /**
     * 获取更新请求参数
     */
    Map<String, Object> getUpdateMap();

    /**
     * 获取个人信息实体
     */
    PersonalInfoBean getBean();


}