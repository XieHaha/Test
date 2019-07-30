package com.keydom.ih_doctor.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_doctor.bean.MessageBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 消息接口
 */
public interface MessageService {

    /**
     * 查询用户未读总条数
     * @param userId 用户ID
     */
    @GET("/api/messageNotification/countMessage")
    Observable<HttpResult<Integer>> countMessage(@Query("userId") long userId);

    /**
     * 用户消息列表查询
     * @param userId 用户ID
     */
    @GET("/api/messageNotification/userMessageInfos")
    Observable<HttpResult<List<MessageBean>>> userMessageInfos(@Query("doctorPhone") String doctorPhone);

    /**
     * 更新消息状态
     * @param rowId 消息ID
     */
    @GET("/api/messageNotification/updateMessageStatus")
    Observable<HttpResult<Object>> updateMessageStatus(@Query("rowId") long rowId);
}
