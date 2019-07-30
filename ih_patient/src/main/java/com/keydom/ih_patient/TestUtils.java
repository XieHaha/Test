package com.keydom.ih_patient;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor
 * @Description：测试类
 * @Author：song
 * @Date：18/11/7 下午6:55
 * 修改人：xusong
 * 修改时间：18/11/7 下午6:55
 */
public class TestUtils {

    public static List<String> dataList=null;
    public static List<String> test(){
        if(dataList!=null){
            return dataList;
        }
        dataList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            dataList.add(String.valueOf(i));
        }
        return dataList;
    }
}
