package com.keydom.ih_doctor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/7 下午6:55
 * 修改人：xusong
 * 修改时间：18/11/7 下午6:55
 */
public class TestUtils {

    public static List<String> dataList = null;

    public static List<String> test() {
        if (dataList != null) {
            return dataList;
        }
        dataList = new ArrayList<>();
        dataList.add("group1/M00/00/00/rBAEA1wEyiqARnIdAABvmPid1Kw438.jpg&600x500");
        dataList.add("group1/M00/00/06/rBAEA1wXm0-Aa-mXAAMSHVkZMh8643.jpg");
        dataList.add("group1/M00/00/00/rBAEA1wEyiqARnIdAABvmPid1Kw438.jpg&600x500");
        return dataList;
    }
}
