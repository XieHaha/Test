package com.keydom.ih_common.constant;

public class Global {
    private static long userId;

    public static long getUserId() {
        return userId;
    }

    public static void setUserId(long userId) {
        Global.userId = userId;
    }
}
