package com.keydom.ih_common.push;

import android.content.Context;
import android.text.TextUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;

public class PushManager {

    private final static int SEQUENCE_ALIAS = 0x10086;
    private final static int SEQUENCE_TAGS = 0x10000;

    /**
     * 设置推送别名
     *
     * @param context 上下文
     * @param alias   别名
     */
    public static void setAlias(Context context, String alias) {
        if (TextUtils.isEmpty(PushPreference.getAlias())) {
            JPushInterface.setAlias(context, SEQUENCE_ALIAS, alias);
        }
    }

    /**
     * 删除推送别名
     *
     * @param context 上下文
     */
    public static void deleteAlias(Context context){
        JPushInterface.deleteAlias(context,SEQUENCE_ALIAS);
    }

    /**
     * 设置推送tag
     *
     * @param context 上下文
     * @param tags    tag集合
     */
    public static void setTags(Context context, Set<String> tags) {
        if (PushPreference.getTags().isEmpty()) {
            JPushInterface.setTags(context, SEQUENCE_TAGS, tags);
        }
    }
}
