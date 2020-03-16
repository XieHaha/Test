package com.keydom.mianren.ih_doctor.activity.nurse_service.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.MaterialBean;

import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.fragment.view
 * @Author：song
 * @Date：18/11/16 下午2:25
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:25
 */
public interface FinishNurseServiceView extends BaseView {

    /**
     * 下一次上门时间选择
     *
     * @param date
     * @param start
     * @param end
     */
    void timeChoose(String date, String start, String end);

    /**
     * 获取完成服务请求参数
     *
     * @return
     */
    Map<String, Object> getFinishMap();

    /**
     * 完成服务成功
     *
     * @param msg 成功信息
     */
    void finishSuccess(String msg);

    /**
     * 完成服务失败
     *
     * @param errMsg 失败信息
     */
    void finishFailed(String errMsg);

    /**
     * 获取选中的完成项目
     *
     * @return
     */
    List<MaterialBean> getSelectMaterial();

    /**
     * 完成服务之前的检查
     *
     * @return
     */
    boolean finishCheck();
}
