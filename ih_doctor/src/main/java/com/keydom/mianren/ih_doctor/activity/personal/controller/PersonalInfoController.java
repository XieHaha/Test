package com.keydom.mianren.ih_doctor.activity.personal.controller;

import android.app.Activity;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.CommonInputActivity;
import com.keydom.mianren.ih_doctor.activity.MainActivity;
import com.keydom.mianren.ih_doctor.activity.personal.MyRealNameCertifyActivity;
import com.keydom.mianren.ih_doctor.activity.personal.PersonalInfoActivity;
import com.keydom.mianren.ih_doctor.activity.personal.view.PersonalInfoView;
import com.keydom.mianren.ih_doctor.bean.PersonalInfoBean;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.MainApiService;
import com.keydom.mianren.ih_doctor.net.PersonalApiService;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.keydom.mianren.ih_doctor.activity.personal.PersonalInfoActivity.USER_ICON;

/**
 * @Name：com.keydom.ih_doctor.activity.controller
 * @Description：个人信息控制器
 * @Author：song
 * @Date：18/11/16 上午9:09
 * 修改人：xusong
 * 修改时间：18/11/16 上午9:09
 */
public class PersonalInfoController extends ControllerImpl<PersonalInfoView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_icon:
                PictureSelector.create((Activity) getContext())
                        .openGallery(PictureMimeType.ofImage()).maxSelectNum(1)
                        .forResult(USER_ICON);
                break;
            case R.id.user_name:
//                CommonInputActivity.startUpdate(getContext(), PersonalInfoActivity.USER_NAME, "修改姓名", getView().getName(), getView().getBean(), 20);
                break;
            case R.id.user_sex:
//                showSex();
                break;
            case R.id.real_name:
                MyRealNameCertifyActivity.start(getContext(), PersonalInfoActivity.REAL_NAME);
                break;
            case R.id.certificate:
                PictureSelector.create((Activity) getContext())
                        .openGallery(PictureMimeType.ofImage()).maxSelectNum(1)
                        .forResult(PersonalInfoActivity.USER_CARD);
                break;
            case R.id.hospital:
                break;
            case R.id.dept:
//                showDept();
                break;
            case R.id.good_be:
                CommonInputActivity.startUpdate(getContext(), PersonalInfoActivity.BE_GOOD, "修改擅长", getView().getGoodBe(), getView().getBean(), 100);
                break;
            case R.id.dec:
                CommonInputActivity.startUpdate(getContext(), PersonalInfoActivity.DEC, "修改简介", getView().getDec(), getView().getBean(), 200);
                break;
            case R.id.finish:
                SharePreferenceManager.setFirstFinishInfo(false);
                MainActivity.start(getContext(),false,true);
                break;
            default:
        }
    }


    /**
     * 获取个人信息
     */
    public void getInfo() {
        HashMap<String, Object> map = new HashMap<>();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).info(map), new HttpSubscriber<PersonalInfoBean>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable PersonalInfoBean data) {
                getView().getInfoSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getInfoFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 上传图片
     */
    public void uploadFile(String path, final int type) {
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
                getView().uploadImgSuccess(data, type);
                updateInfo();
//                if (type == USER_ICON) {
//                    ImClient.updateUserInfo(UserInfoFieldEnum.AVATAR, data, null);
//                }
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
     * 更新个人信息
     */
    public void updateInfo() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).editInfo(HttpService.INSTANCE.object2Body(getView().getUpdateMap())), new HttpSubscriber<String>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable String data) {
                getView().updateInfoSuccess(data);
                hideLoading();
                Map<UserInfoFieldEnum, Object> map = new HashMap<>();
                for (String key : getView().getUpdateMap().keySet()) {
                    if ("name".equals(key)) {
                        map.put(UserInfoFieldEnum.Name, getView().getUpdateMap().get(key));
                    }
                    if ("sex".equals(key)) {
                        map.put(UserInfoFieldEnum.GENDER, getView().getUpdateMap().get(key));
                    }
                }
                if (map.size() != 0) {
                    ImClient.updateUserInfo(map, null);
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().updateInfoFailed(msg);
                hideLoading();
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 选择科室
     */
    public void showDept() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                getView().setDept(MyApplication.deptBeanList.get(options1));
                updateInfo();
            }
        }).build();
        pvOptions.setPicker(MyApplication.filterDept(false));
        pvOptions.show();
    }


    /**
     * 选择性别
     */
    public void showSex() {
        List<String> sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                getView().setSex(options1);
                updateInfo();
            }
        }).build();
        pvOptions.setPicker(sexList);
        pvOptions.show();
    }
}
