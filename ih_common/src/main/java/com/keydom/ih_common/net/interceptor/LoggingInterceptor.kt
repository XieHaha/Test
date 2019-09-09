package com.keydom.ih_common.net.interceptor

import com.keydom.ih_common.BuildConfig
import com.keydom.ih_common.utils.SharePreferenceManager
import com.orhanobut.logger.Logger
import okhttp3.*
import okio.Buffer
import java.io.IOException


/**
 * Created by Chao  2018/5/8 on 13:50
 * description 日志监听拦截器
 */
class LoggingInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response { //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        val request = chain.request().newBuilder().addHeader("Authorization", SharePreferenceManager.getToken()).build()
        val t1 = System.nanoTime()//请求发起的时间
        val method = request.method()
        val responseBuilder = Response.Builder()
                .code(400)
                .message("不支持保存表情，请检查后重试！")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .addHeader("content-type", "application/json")

        if ("POST" == method) {
            val sb = StringBuilder()
            when {
                request.body() is FormBody? -> {
                    val body = request.body() as FormBody?
                    for (i in 0 until body!!.size()) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",")
                    }
                    sb.delete(sb.length - 1, sb.length)
                    Logger.e(sb.toString())
//                    if (CommonUtils.containsEmoji(sb.toString())) {
//                        val responseString = "{\n" +    //模拟数据返回body
//                                "\t\"statusCode\": 400,\n" +
//                                "\t\"message\": \"不支持保存表情，请检查后重试！\",\n" +
//                                "\t\"result\": null\n" +
//                                "}"
//                        responseBuilder.body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))//将数据设置到body中
//                        return responseBuilder.build() //builder模式构建response
//
//                    }
                    if (BuildConfig.DEBUG)
                        Logger.e(String.format(" %n发送POST请求 %s on %s %n请求头:%n%s请求参数:{%s}", request.url(), chain.connection(), request.headers(), sb.toString()))
                }
                request.body() is MultipartBody? -> {
                    val body = request.body() as MultipartBody?
                    for (i in 0 until body!!.size()) {
                        body.part(i).headers()?.get("Content-Disposition")?.let {
                            sb.append("$it,")
                        }
                    }
                    sb.delete(sb.length - 1, sb.length)
                    Logger.e(sb.toString())
//                    if (CommonUtils.containsEmoji(sb.toString())) {
//                        val responseString = "{\n" +    //模拟数据返回body
//                                "\t\"statusCode\": 400,\n" +
//                                "\t\"message\": \"不支持保存表情，请检查后重试！\",\n" +
//                                "\t\"result\": null\n" +
//                                "}"
//                        responseBuilder.body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))//将数据设置到body中
//                        return responseBuilder.build() //builder模式构建response
//                    }
                    if (BuildConfig.DEBUG)
                        Logger.e(String.format(" %n发送POST请求 %s on %s %n请求头:%n%s请求参数:{%s}", request.url(), chain.connection(), request.headers(), sb.toString()))
                }
                else -> {
                    Logger.e(stringifyRequestBody(request))
//                    if (CommonUtils.containsEmoji(stringifyRequestBody(request))) {
//                        val responseString = "{\n" +    //模拟数据返回body
//                                "\t\"statusCode\": 400,\n" +
//                                "\t\"message\": \"不支持保存表情，请检查后重试！\",\n" +
//                                "\t\"result\": null\n" +
//                                "}"
//                        responseBuilder.body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))//将数据设置到body中
//                        return responseBuilder.build() //builder模式构建response
//                    }
                    if (BuildConfig.DEBUG)
                        Logger.e(String.format(" %n发送POST请求 %s on %s %n请求头:%n%s%n请求参数:%s", request.url(), chain.connection(), request.headers(), stringifyRequestBody(request)))
                }
            }
        } else {
//            if (CommonUtils.containsEmoji(request.url().toString())) {
//                val responseString = "{\n" +    //模拟数据返回body
//                        "\t\"statusCode\": 400,\n" +
//                        "\t\"message\": \"不支持保存表情，请检查后重试！\",\n" +
//                        "\t\"result\": null\n" +
//                        "}"
//                responseBuilder.body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))//将数据设置到body中
//                Logger.e(responseString)
//                return responseBuilder.build() //builder模式构建response
//            }
            if (BuildConfig.DEBUG)
                Logger.e(String.format(" %n发送GET请求 %s on %s%n%s", request.url(), chain.connection(), request.headers()))
        }
        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        val responseBody = response.peekBody((1024 * 1024).toLong())
        if (BuildConfig.DEBUG) {
            Logger.e(String.format(" %n接收响应: [%s] %.1fms %n%s", response.request().url(), (t2 - t1) / 1e6, response.headers()))
            Logger.e(responseBody.string())
        }
        return response
    }


    private fun stringifyRequestBody(request: Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }

    companion object {
        private const val TAG = "Interceptor"
    }
}