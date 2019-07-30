package com.keydom.ih_patient.callback;

import android.view.View;

/**
 * @Name：com.keydom.ih_doctor.m_interface
 * @Description：处方审核dialog
 * @Author：song
 * @Date：18/12/15 下午4:52
 * 修改人：xusong
 * 修改时间：18/12/15 下午4:52
 */
public interface OnCheckDialogListener {
    void commit(View v, String value);
}
