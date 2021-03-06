package com.keydom.mianren.ih_patient.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.keydom.ih_common.bean.MessageEvent;
import com.keydom.ih_common.bean.SpeakLimitBean;
import com.keydom.ih_common.bean.UpdateDoctorBean;
import com.keydom.ih_common.constant.EventType;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.activity.index_main.MainActivity;
import com.keydom.mianren.ih_patient.activity.my_message.MyMessageActivity;
import com.keydom.mianren.ih_patient.constant.Type;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.e("---------------------------------广播被接受了");
        try {
            Bundle bundle = intent.getExtras();
            Logger.d(TAG+":[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logger.d(TAG+":[MyReceiver] 接收Registration Id : " + regId);

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG+":[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                String title = bundle.getString(JPushInterface.EXTRA_TITLE);
                if("问诊权限修改".equals(title)) {
                    String jsonString = bundle.getString(JPushInterface.EXTRA_EXTRA);
                    UpdateDoctorBean doctorBean = new Gson().fromJson(jsonString,UpdateDoctorBean.class);
                    List<SpeakLimitBean> limitBeans = new Gson().fromJson(doctorBean.getUpdateDoctors(), new TypeToken<List<SpeakLimitBean>>() {}.getType());
                    EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NOTIFY_PATIENT_SPEAK_PERMISSION).setData(limitBeans).build());
                }
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG+":[MyReceiver] 接收到推送下来的通知");
                int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Logger.d(TAG+":[MyReceiver] 接收到推送下来的通知的ID: " + notificationId);
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                if("问诊权限修改".equals(title)) {
                    String jsonString = bundle.getString(JPushInterface.EXTRA_EXTRA);
                    UpdateDoctorBean doctorBean = new Gson().fromJson(jsonString,UpdateDoctorBean.class);
                    List<SpeakLimitBean> limitBeans = new Gson().fromJson(doctorBean.getUpdateDoctors(), new TypeToken<List<SpeakLimitBean>>() {}.getType());
                    EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NOTIFY_PATIENT_SPEAK_PERMISSION).setData(limitBeans).build());
                }

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Logger.d(TAG+":[MyReceiver] 用户点击打开了通知");
                Logger.e("---------------------------------广播点击了");
                if(App.isNeedInit){
                    MainActivity.start(context,true);
                    Logger.e("---------------------------------跳转到首页了");
                }
                else{
                    MyMessageActivity.start(context,Type.MYMESSAGE,null);
                    Logger.e("---------------------------------跳转到消息了");

                }

                //打开自定义的Activity
//                Intent i = new Intent(context, TestActivity.class);
//                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Logger.d(TAG+":[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logger.w(TAG+":[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Logger.d(TAG+":[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            switch (key) {
                case JPushInterface.EXTRA_NOTIFICATION_ID:
                    sb.append("\nkey:").append(key).append(", value:").append(bundle.getInt(key));
                    break;
                case JPushInterface.EXTRA_CONNECTION_CHANGE:
                    sb.append("\nkey:").append(key).append(", value:").append(bundle.getBoolean(key));
                    break;
                case JPushInterface.EXTRA_EXTRA:
                    if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                        Logger.i(TAG + ":This message has no Extra data");
                        continue;
                    }

                    try {
                        JSONObject json = JSON.parseObject(bundle.getString(JPushInterface.EXTRA_EXTRA));

                        for (String myKey : json.keySet()) {
                            sb.append("\nkey:").append(key).append(", value: [").append(myKey).append(" - ").append(json.get(myKey)).append("]");
                        }
                    } catch (JSONException e) {
                        Logger.e(TAG + ":Get message extra JSON error!");
                    }

                    break;
                default:
                    sb.append("\nkey:").append(key).append(", value:").append(bundle.get(key));
                    break;
            }
        }
        return sb.toString();
    }

}
