package com.keydom.ih_patient.net;

import com.keydom.ih_common.net.result.HttpResult;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 上传接口
 */
public interface UploadService {

    /**
     * 上传图片
     */
    @Multipart
    @POST("api/file/upload")
    Observable<HttpResult<String>> upload(@Part MultipartBody.Part file);
}
