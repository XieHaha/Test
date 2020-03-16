package com.keydom.mianren.ih_doctor.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.mianren.ih_doctor.bean.MessageBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 消息接口
 */
public interface MessageService {

    /**
     * 查询用户未读总条数
     * @param doctorPhone 医生电话号码
     */
    @GET("/api/messageNotification/countMessage")
    Observable<HttpResult<Integer>> countMessage(@Query("doctorPhone") String doctorPhone);

    /**
     * 用户消息列表查询
     * @param map
     */
    @GET("/api/messageNotification/userMessageInfos")
    Observable<HttpResult<PageBean<MessageBean>>> userMessageInfos(@QueryMap Map<String, Object> map);

    /**
     * 更新消息状态
     * @param rowId 消息ID
     */
    @GET("/api/messageNotification/updateMessageStatus")
    Observable<HttpResult<Object>> updateMessageStatus(@Query("rowId") long rowId);
}
