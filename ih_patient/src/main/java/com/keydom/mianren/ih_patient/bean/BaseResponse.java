package com.keydom.mianren.ih_patient.bean;

/**
 * 请求解析base类
 * @Author：song
 * @Date：18/11/8 下午9:26
 */
public class BaseResponse<T> {
    public int status;
    public String message;
    public T data;
    public boolean isSuccess(){
        return status == 200;
    }
}