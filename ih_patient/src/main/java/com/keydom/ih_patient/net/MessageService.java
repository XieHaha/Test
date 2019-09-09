package com.keydom.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_patient.bean.MessageBean;
import com.keydom.ih_patient.bean.NoticeBean;

import java.util.List;
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
     * @param userId 用户ID
     */
    @GET("/api/messageNotification/countMessage")
    Observable<HttpResult<Integer>> countMessage(@Query("userId")long userId);

    /**
     * 用户消息列表查询
     * @param map
     */
    @GET("/api/messageNotification/userMessageInfos")
    Observable<HttpResult<List<MessageBean>>> userMessageInfos(@QueryMap Map<String,Object> map);

    /**
     * 公告列表查询
     * @param map
     */
    @GET("/user/patient/getNoticeList")
    Observable<HttpResult<NoticeBean>> getNoticeList(@QueryMap Map<String,Object> map);

    /**
     * 更新消息状态
     * @param rowId 消息ID
     */
    @GET("/api/messageNotification/updateMessageStatus")
    Observable<HttpResult<Object>> updateMessageStatus(@Query("rowId")long rowId);
}
