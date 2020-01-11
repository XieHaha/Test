package com.keydom.ih_patient.net;

import com.keydom.ih_common.bean.PageBean;
import com.keydom.ih_common.net.result.HttpResult;
import com.keydom.ih_patient.bean.RenewalRecordItem;
import com.keydom.ih_patient.bean.VIPCardInfoResponse;
import com.keydom.ih_patient.bean.VIPDetailBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VIPCardService {



    /**
     * 获取当前用户的会员卡
     */
    @GET("user/vipCard/getMyVipCard")
    Observable<HttpResult<VIPDetailBean>> getMyVipCard(@Query("registerUserId") long id);

    /**
     * 获取当前会员卡权益
     */
    @GET("user/vipCardInfo/getVipCard")
    Observable<HttpResult<VIPCardInfoResponse>> getVipCard();

    /**
     * 续约记录
     */
    @GET("user/vipCard/getRenewalRecordForMobile")
    Observable<HttpResult<PageBean<RenewalRecordItem>>> getRenewalRecord(@Query("registerUserId") long id,@Query("currentPage") int currentPage,@Query("pageSize") int pageSize);

    /**
     * 办理会员卡-移动端
     */
    @POST("user/vipCard/addCardForMobile")
    Observable<HttpResult<Object>> addCardForMobile(@Body RequestBody body);


    /**
     * 续约
     */
    @POST("user/vipCard/renewalCard")
    Observable<HttpResult<Object>> renewalCard(@Body RequestBody body);
}
