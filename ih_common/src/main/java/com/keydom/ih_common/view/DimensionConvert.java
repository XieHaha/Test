package com.keydom.ih_common.view;

import android.content.Context;

/**
 * @Name：com.kentra.yxyz.view
 * @Description：dp转换为PX
 * @Author：song
 * @Date：18/11/6 下午3:45
 * 修改人：xusong
 * 修改时间：18/11/6 下午3:45
 */
public class DimensionConvert {

        public static int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        public static int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }
    }
