package com.keydom.ih_patient.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 网络监听广播
 */
public class NetWorkBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!NetUtils.isNetworkConnected())
            ToastUtil.showMessage(context,"网络不可用");
        else
            EventBus.getDefault().post(new Event(EventType.NETWORKRECOVER,null));
    }
}
