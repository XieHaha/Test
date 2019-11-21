package com.keydom.ih_patient.activity.nursing_service;

import android.os.Bundle;

import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.ui.FaceLivenessActivity;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.NursingService;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 人脸识别页面
 */
public class FaceDetectExpActivity extends FaceLivenessActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        super.onLivenessCompletion(status, message, base64ImageMap);
        if (status == FaceStatusEnum.OK && mIsCompletion) {
            Map<String,Object> map=new HashMap<>();
            map.put("id",Global.getUserId());
            map.put("faceImage",base64ImageMap.get("bestImage0"));
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NursingService.class).personVerify(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Boolean>() {
                @Override
                public void requestComplete(@Nullable Boolean data) {
                    if(data){
                        EventBus.getDefault().post(new Event(EventType.FACIALRECOGNITION,null));
                        finish();
                    }else {
                        ToastUtil.showMessage(FaceDetectExpActivity.this,"验证不通过，请确认是本人操作");
                        finish();
                    }

                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    ToastUtil.showMessage(FaceDetectExpActivity.this,"验证失败"+msg+"请稍后重试");
                    finish();
                    return super.requestError(exception, code, msg);
                }
            });

        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
            ToastUtil.showMessage(this,"人脸图像采集超时");
        }
    }


    @Override
    public void finish() {
        super.finish();
    }

}
