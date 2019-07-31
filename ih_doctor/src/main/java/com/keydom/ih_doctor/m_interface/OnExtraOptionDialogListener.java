package com.keydom.ih_doctor.m_interface;

import android.view.View;

import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.m_interface
 * @Description：处方审核dialog
 * @Author：song
 * @Date：18/12/15 下午4:52
 * 修改人：xusong
 * 修改时间：18/12/15 下午4:52
 */
public interface OnExtraOptionDialogListener {
    void commit(View v, Map<String, Object> value);

    void extraClick(View v, Object extraValue, OnNurseResultListener listener);
}
