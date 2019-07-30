package com.keydom.ih_doctor.utils;

import android.content.Context;

import com.keydom.ih_doctor.R;

import java.util.Random;

/**
 * @Name：com.keydom.ih_doctor.utils
 * @Description：描述信息
 * @Author：song
 * @Date：18/12/25 上午9:26
 * 修改人：xusong
 * 修改时间：18/12/25 上午9:26
 */
public class ColorUtils {

    public static int getCustomizedColor(Context context) {
        int[] customizedColors = context.getResources().getIntArray(R.array.customizedColors);
        int customizedColor = customizedColors[new Random().nextInt(customizedColors.length)];
        return customizedColor;
    }

}
