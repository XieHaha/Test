package com.keydom.ih_common.net.service

import com.alibaba.fastjson.JSON
import com.keydom.ih_common.net.factory.FastJsonConverterFactory
import com.keydom.ih_common.net.interceptor.LoggingInterceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Chao  2018/5/8 on 13:39
 * description 网络服务构建类
*/


object HttpNewService {

    private const val DEFAULT_TIMEOUT: Long = 30

    //定制OkHttp
    //设置超时时间
    //设置缓存
    private val okHttpClient: OkHttpClient
        get() {
            val httpClientBuilder = OkHttpClient.Builder()
            httpClientBuilder.retryOnConnectionFailure(true)
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//            val httpCacheDirectory = File(PathUtils.getCache(Constants.NETWORK_PATH))
//            httpClientBuilder.cache(Cache(httpCacheDirectory, (10 * 1024 * 1024).toLong()))

            httpClientBuilder.addInterceptor(LoggingInterceptor())
            return httpClientBuilder.build()
        }

    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.tianfuzhl.com.cn/")
                .client(okHttpClient)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(serviceClass)
    }

    fun object2Body(obj: Any): RequestBody {
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), JSON.toJSONString(obj))
    }

    fun json2Body(obj: Any): RequestBody {
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), obj.toString())
    }
}
