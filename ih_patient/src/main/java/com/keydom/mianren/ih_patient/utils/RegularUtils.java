package com.keydom.mianren.ih_patient.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则
 */
public class RegularUtils {
    public static boolean PassWordValidate(String regStr){
        String PASS_WROD="^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{6,20}";
        Pattern pattern = Pattern.compile(PASS_WROD);
        Matcher matcher = pattern.matcher(regStr);
        return matcher.matches();
    }
}
