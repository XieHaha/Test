package com.keydom.mianren.ih_doctor.activity.personal.controller;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.activity.personal.FeedBackActivity;
import com.keydom.mianren.ih_doctor.activity.personal.view.FeedBackView;
import com.keydom.mianren.ih_doctor.net.MainApiService;
import com.keydom.mianren.ih_doctor.net.PersonalApiService;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：意见反馈控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class FeedBackController extends ControllerImpl<FeedBackView> implements AdapterView.OnItemClickListener {

    /**
     * 上传图片
     *
     * @param path 图片地址
     */
    public void uploadFile(String path) {
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).upload(body), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().uploadSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().uploadFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 提交意见反馈
     */
    public void feedBack() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).feedBack(HttpService.INSTANCE.object2Body(getView().getFeedBackMap())), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().feedBackSuccess(data);
                hideLoading();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().uploadFailed(msg);
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (getView().getLastItemClick(position)) {
            if (getView().getImgSize() < FeedBackActivity.IMAGE_LIMIT) {
                PictureSelector.create((Activity) getContext())
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(FeedBackActivity.IMAGE_LIMIT - getView().getImgSize())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            } else {
                ToastUtil.showMessage(mContext, "最多只能选择" + FeedBackActivity.IMAGE_LIMIT + "张图片");
            }

        }
    }
}
