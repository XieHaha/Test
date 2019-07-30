package com.keydom.ih_doctor.m_interface;

/**
 * @Name：com.keydom.ih_doctor.m_interface
 * @Description：描述信息
 * @Author：song
 * @Date：18/12/15 下午4:52
 * 修改人：xusong
 * 修改时间：18/12/15 下午4:52
 */
public interface OnLoginListener {
    void success(String msg);

    void failed(String errMsg);
}
