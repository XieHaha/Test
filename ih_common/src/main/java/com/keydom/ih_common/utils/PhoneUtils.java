package com.keydom.ih_common.utils;

public class PhoneUtils {


    public static boolean isMobileEnable(final String s) {
        if (s == null || s.length() == 0) {
            return false;
        } else {
            if (s.length() == 11) {
                return true;
            } else {
                return false;
            }
        }
    }
}
