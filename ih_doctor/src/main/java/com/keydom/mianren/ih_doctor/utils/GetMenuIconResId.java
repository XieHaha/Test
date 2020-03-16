package com.keydom.mianren.ih_doctor.utils;

import com.keydom.mianren.ih_doctor.R;

/**
 * @Name：com.keydom.ih_doctor.utils
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/21 下午12:57
 * 修改人：xusong
 * 修改时间：18/11/21 下午12:57
 */
public class GetMenuIconResId {
    private static GetMenuIconResId instance;

    public GetMenuIconResId() {
    }

    private int resId = -1;

    public static GetMenuIconResId getInstance() {
        if (instance == null) {
            instance = new GetMenuIconResId();
        }
        return instance;
    }

    public int getId(String name) {
        resId = -1;
        switch (name) {
            case "处方审核":
                resId = R.mipmap.audit;
                break;
            case "发布信息":
                resId = R.mipmap.issue;
                break;
            case "护理服务":
                resId = R.mipmap.nurse_visit;
                break;
            case "门诊排班":
                resId = R.mipmap.consulting;
                break;
            case "上门护理":
                resId = R.mipmap.nurse_visit;
                break;
            case "在线咨询":
                resId = R.mipmap.online_consult;
                break;
            case "处方查询":
                resId = R.mipmap.prescription_query;
                break;
            case "健康管理":
                resId = R.mipmap.issue;
                break;
        }
        return resId;
    }


}
