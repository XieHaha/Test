package com.keydom.mianren.ih_doctor.activity.doctor_cooperation.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.DeptDoctorBean;
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
public interface SelectDoctorView extends BaseView {


    /**
     * 获取查询参数
     *
     * @return 请求参数
     */
    Map<String, Object> getQueryMap();

    /**
     * 获取添加成员参数
     *
     * @return 请求添加成员接口参数
     */
    Map<String, Object> getAddMap();

    /**
     * 获取医护人员列表成功
     *
     * @param list 人员列表数据
     */
    void getDoctorListSuccess(List<DeptDoctorBean> list, TypeEnum typeEnum);

    /**
     * 获取医护人员列表失败
     *
     * @param errMsg 失败信息
     */
    void getDoctorListFailed(String errMsg);

    /**
     * 添加团队成员成功
     *
     * @param msg 成功信息
     */
    void addDoctorSuccess(String msg);

    /**
     * 添加团队成员失败
     *
     * @param errMsg 失败信息
     */
    void addDoctorFailed(String errMsg);

    /**
     * 选择科室方法
     *
     * @param position 选择科室位于列表中的位置
     */
    void selectDept(int position);

}