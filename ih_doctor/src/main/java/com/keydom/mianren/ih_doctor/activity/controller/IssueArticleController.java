package com.keydom.mianren.ih_doctor.activity.controller;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.bean.Article;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.ApiService;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.IssueArticleActivity;
import com.keydom.mianren.ih_doctor.activity.view.IssueArticleView;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
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
 * @Description：发布文章控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class IssueArticleController extends ControllerImpl<IssueArticleView> implements View.OnClickListener, AdapterView.OnItemClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_tag_tv:
                ((IssueArticleActivity) getContext()).showInputDialog();
                break;
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (getView().getLastItemClick(position)) {
            if (getView().getImageLimit() <= 0) {
                ToastUtil.showMessage(getContext(), "最多只能上传9张图片");
                return;
            }
            PictureSelector.create((Activity) getContext())
                    .openGallery(PictureMimeType.ofImage()).maxSelectNum(getView().getImageLimit())
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }

    }


    /**
     * 发布文章以及修改文章<br/>
     * 参数中传入了articlId的就是修改文章，否则就是新发布文章
     *
     * @param map 发布文章参数
     */
    public void issueArticle(HashMap<String, Object> map) {
        if (map == null) {
            ToastUtil.showMessage(getContext(), "请完成文章内容!");
            return;
        }
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).addArticle(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(getView().getContext(), getView().getDisposables(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                hideLoading();
                getView().issueSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().issueFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 上传图片
     *
     * @param path 上传的图片路径（本地路径）
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
     * 获取文章详情
     *
     * @param map 文章详情参数
     */
    public void getArticleDetails(HashMap<String, Object> map) {

        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ApiService.class).articleInfo(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Article>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Article data) {
                if (getView() != null) {
                    hideLoading();
                    getView().articleInfoSuccess(data);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                if (getView() != null) {
                    hideLoading();
                    getView().articleInfoFailed(msg);
                }
                return super.requestError(exception, code, msg);
            }
        });
    }


}
