package com.keydom.mianren.ih_doctor.activity.issue_information.controller;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.NoticeInfoBean;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.ApiService;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.activity.issue_information.view.IssueNotificationView;
import com.keydom.mianren.ih_doctor.net.MainApiService;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class IssueNotificationController extends ControllerImpl<IssueNotificationView> implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (getView().getLastItemClick(position)) {
            if (getView().getImageLimit() <= 0) {
                ToastUtil.showMessage(getContext(), "最多只可上传9张图片");
                return;
            }
            PictureSelector.create((Activity) getContext())
                    .openGallery(PictureMimeType.ofImage()).maxSelectNum(getView().getImageLimit())
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }

    }


    /**
     * 发布通知公告
     *
     * @param map
     */
    public void issueArticle(HashMap<String, Object> map) {
        if (map == null) {
            ToastUtil.showMessage(getContext(), "请完成文章内容!");
            return;
        }
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).addNotification(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().issueSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 上传图片
     *
     * @param path
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
                getView().uploadImgSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().uploadImgFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 获取通知公告详情
     *
     * @param map
     */
    public void getNoticeDetail(HashMap<String, Object> map) {

        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).getNoticeInfo(map), new HttpSubscriber<NoticeInfoBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable NoticeInfoBean data) {
                hideLoading();
                getView().getNotificationSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getNotificationFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


}
