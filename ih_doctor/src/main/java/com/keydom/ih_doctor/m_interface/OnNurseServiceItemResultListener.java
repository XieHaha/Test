package com.keydom.ih_doctor.m_interface;

import com.keydom.ih_doctor.bean.NursingProjectInfo;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.m_interface
 * @Description：描述信息
 * @Author：song
 * @Date：18/12/15 下午4:52
 * 修改人：xusong
 * 修改时间：18/12/15 下午4:52
 */
public interface OnNurseServiceItemResultListener {
    void nurseServiceItemResult(List<NursingProjectInfo> list);
}
