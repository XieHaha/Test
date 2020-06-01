package com.keydom.mianren.ih_doctor.m_interface;

import com.keydom.mianren.ih_doctor.bean.CheckOutGroupBean;

import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.m_interface
 * @Description：描述信息
 * @Author：song
 * @Date：19/1/3 上午9:53
 * 修改人：xusong
 * 修改时间：19/1/3 上午9:53
 */
public interface SelectInspectItemListener {

    void selectItem(List<CheckOutGroupBean> list);


}