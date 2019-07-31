package com.keydom.ih_doctor.fragment.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.ImPatientInfo;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.fragment.view
 * @Description：患者管理所有人页面接口
 * @Author：song
 * @Date：18/11/16 下午2:25
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:25
 */
public interface PatientContactFragmentView extends BaseView {

    /**
     * 获取用户成功
     *
     * @param list 联系人列表
     */
    void getUserListSuccess(List<ImPatientInfo> list);

    /**
     * 获取用户失败
     *
     * @param errMsg 失败提示信息
     */
    void getUserListFailed(String errMsg);

    /**
     * 获取列表请求参数
     *
     * @return 请求参数
     */
    Map<String, Object> getListMap();
}
