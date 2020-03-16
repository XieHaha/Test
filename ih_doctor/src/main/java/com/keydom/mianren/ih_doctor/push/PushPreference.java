package com.keydom.mianren.ih_doctor.push;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.keydom.Common;

import java.util.Set;

public class PushPreference {
    private static final String KEY_ALIAS = "alias";
    private static final String KEY_TAGS = "tags";

    public static void saveAlias(String alias) {
        saveString(KEY_ALIAS, alias);
    }

    public static String getAlias() {
        return getString(KEY_ALIAS);
    }

    public static void saveTags(Set<String> tags) {
        saveString(KEY_TAGS, JSON.toJSONString(tags));
    }

    public static Set<String> getTags() {
        return JSON.parseObject(getString(KEY_TAGS), Set.class);
    }

    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    private static SharedPreferences getSharedPreferences() {
        return Common.INSTANCE.getApplication().getSharedPreferences("push", Context.MODE_PRIVATE);
    }
}
