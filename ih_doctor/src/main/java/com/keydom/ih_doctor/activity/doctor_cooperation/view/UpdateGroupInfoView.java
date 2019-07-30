package com.keydom.ih_doctor.activity.doctor_cooperation.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.GroupInfoBean;
import com.keydom.ih_doctor.constant.TypeEnum;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.view
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:14
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:14
 */
public interface UpdateGroupInfoView extends BaseView {

    /**
     * 添加和修改共用,获取请求参数
     *
     * @return 请求参数集合
     */
    Map<String, Object> getMap();

    /**
     * 上传头像成功
     *
     * @param url 上传的头像地址
     */
    void uploadSuccess(String url);

    /**
     * 上传图像失败
     *
     * @param errMsg 失败信息
     */
    void uploadFailed(String errMsg);

    /**
     * 添加医生团队成功
     *
     * @param bean 新添加的团队对象
     */
    void addSuccess(GroupInfoBean bean);

    /**
     * 添加团队失败
     *
     * @param errMsg 失败信息
     */
    void addFailed(String errMsg);

    /**
     * 更新团队成功
     *
     * @param success 成功信息
     */
    void updateSuccess(String success);

    /**
     * 更新团队失败
     *
     * @param errMsg 失败信息
     */
    void updateFailed(String errMsg);

    /**
     * 获取操作类型
     *
     * @return
     */
    TypeEnum getType();

    /**
     * 检查输入是否合法
     *
     * @return true合法  false 不合法
     */
    boolean checkInput();

    /**
     * 设置医生团队名称，从共用输入页面带回
     *
     * @param str
     */
    void setGroupName(String str);

    /**
     * 设置医生团队科室
     *
     * @param str
     */
    void setGroupAdep(String str);

    /**
     * 获取团队名称
     *
     * @return
     */
    String getGroupName();

    /**
     * 获取团队擅长
     *
     * @return
     */
    String getGroupGood();

    void editInfo();
}