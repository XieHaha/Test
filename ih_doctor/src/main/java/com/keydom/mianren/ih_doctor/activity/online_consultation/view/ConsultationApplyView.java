package com.keydom.mianren.ih_doctor.activity.online_consultation.view;

import com.keydom.ih_common.base.BaseView;
import com.keydom.mianren.ih_doctor.bean.DeptDoctorBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @date 3月24日
 * 会诊
 */
public interface ConsultationApplyView extends BaseView {

    /**
     * 图片上传成功
     *
     * @param msg 上传的图片地址
     */
    void uploadSuccess(String msg);

    /**
     * 图片上传失败
     *
     * @param errMsg 失败信息
     */
    void uploadFailed(String errMsg);

    /**
     * 提交会诊成功
     *
     * @param msg 成功信息
     */
    void saveSuccess(String msg);

    /**
     * 提交会诊失败
     *
     * @param errMsg 失败信息
     */
    void saveFailed(String errMsg);

    /**
     * 判断是否点击的图片最后一项，点击最后一项跳转到选择图片页面，否则跳转到查看大图页面
     *
     * @param position 点击的position
     * @return 返回是否点击的最后一项 true 点击的最后一项，false 点击的不是最后一项
     */
    boolean getLastItemClick(int position);

    /**
     * 获取选中的医生列表
     *
     * @return 选中的医生列表对象
     */
    ArrayList<DeptDoctorBean> getSelectedDoctor();

    /**
     * 获取发起会诊需要的参数
     *
     * @return 请求参数
     */
    Map<String, Object> getOperateMap();

    /**
     * 获取图片数量
     *
     * @return 图片数量
     */
    int getImgSize();

    /**
     * 删除图片
     *
     * @param position 删除图片的位置
     */
    void deleteImg(int position);

    /**
     * 获取图片路径列表
     *
     * @return 图片地址列表对象
     */
    List<String> getImgList();

    void setGrade(int index);

    ArrayList<String> getGradeStr();

    void setApplyDate(Date date);

    boolean verifyCommit();
}