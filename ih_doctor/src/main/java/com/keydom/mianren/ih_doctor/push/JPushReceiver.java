package com.keydom.mianren.ih_doctor.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.keydom.ih_common.bean.MessageEvent;
import com.keydom.ih_common.constant.EventType;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.activity.WelcomeActivity;
import com.keydom.mianren.ih_doctor.activity.online_consultation.ConsultationOrderActivity;
import com.keydom.mianren.ih_doctor.bean.AuditInfoBean;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Logger.e(TAG + ":[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logger.e(TAG + ":[MyReceiver] 接收Registration Id : " + regId);

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Logger.e(TAG + ":[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                //                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Logger.e(TAG + ":[MyReceiver] 接收到推送下来的通知的ID: " + notificationId);
                String jsonString = bundle.getString(JPushInterface.EXTRA_EXTRA);
                String title = bundle.getString(JPushInterface.EXTRA_TITLE);
                if ("问诊权限修改".equals(title)) {
                    EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NOTIFY_DOCTOR_SPEAK_PERMISSION).build());
                } else {
                    try {
                        AuditInfoBean notifyKeyBean = new Gson().fromJson(jsonString,
                                AuditInfoBean.class);
                        //字段统一
                        notifyKeyBean.setId(notifyKeyBean.getAuditId());
                        if (TextUtils.equals(notifyKeyBean.getType(), "0")) {
                            EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NOTIFY_APPLY_JOIN_CONSULTATION).setData(notifyKeyBean).build());
                        } else {
                            if (TextUtils.isEmpty(title) || title.contains("拒绝")) {
                                //拒绝暂不通知
                            } else {
                                EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.NOTIFY_AGREE_JOIN_CONSULTATION).setData(notifyKeyBean).build());
                            }
                        }

                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        Logger.e(TAG + "  Get message extra JSON error!");
                    }
                }

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Logger.e(TAG + ":[MyReceiver] 用户点击打开了通知");
                //打开自定义的Activity
                //                Intent i = new Intent(context, TestActivity.class);
                //                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
                //                .FLAG_ACTIVITY_CLEAR_TOP);
                //                context.startActivity(i);
                if (MyApplication.isNeedInit) {
                    WelcomeActivity.startFromJpush(context, true);
                } else {
                    ConsultationOrderActivity.start(context);
                }
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Logger.e(TAG + ":[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE
                        , false);
                Logger.w(TAG + ":[MyReceiver]" + intent.getAction() + " connected state change to" +
                        " " + connected);
            } else {
                Logger.e(TAG + ":[MyReceiver] Unhandled intent - " + intent.getAction());
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
                        JSONObject json =
                                JSON.parseObject(bundle.getString(JPushInterface.EXTRA_EXTRA));

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
