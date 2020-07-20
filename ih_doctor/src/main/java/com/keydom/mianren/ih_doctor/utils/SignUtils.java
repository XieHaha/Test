package com.keydom.mianren.ih_doctor.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.ApplySignatureActivity;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.SignatureActivity;
import com.keydom.mianren.ih_doctor.bean.SignIdBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.net.SignService;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import cn.org.bjca.signet.component.core.activity.SignetCoreApi;
import cn.org.bjca.signet.component.core.bean.results.SignDataResult;
import cn.org.bjca.signet.component.core.bean.results.UserStateResult;
import cn.org.bjca.signet.component.core.callback.CheckStateCallBack;
import cn.org.bjca.signet.component.core.callback.SignDataCallBack;

public class SignUtils {
    static int maxReSignTimes = 3;

    /**
     * @param context
     * @param signData 签名数据
     * @param type     任务类型
     * @param callBack 回调
     */
    public static void sign(Context context, String signData, int type, SignCallBack callBack) {
        if (Const.CHECK_AND_ACCEP) {
            callBack.signSuccess("", "");
            return;
        }

        String msspID = MyApplication.userInfo.getMsspId();
        if (msspID == null || "".equals(msspID)) {
            toRegisterSign(context);
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("msspId", MyApplication.userInfo.getMsspId());
        map.put("userId", MyApplication.userInfo.getId());
        map.put("signData", Base64.encodeToString(signData.getBytes(), Base64.DEFAULT));
        map.put("signType", type);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(SignService.class).addAuthJob(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>(context, ((BaseActivity) context).getDisposable(), false) {
            @Override
            public void requestComplete(@org.jetbrains.annotations.Nullable String data) {
                SignIdBean signIdBean = JSON.parseObject(data, new TypeReference<SignIdBean>() {
                });
                SignetCoreApi.useCoreFunc(new SignDataCallBack(context, msspID, signIdBean.getSignJobId()) {
                    @Override
                    public void onSignDataResult(SignDataResult result) {
                        switch (result.getErrCode()) {
                            case "0x80000001":
                            case "0x81800009"://调用签名接口时，传入的 signJobId 已被其他用 户签署过，或 signJobId 生成时指定其他用户签署，导致当前用户无法进行签署。
                                if (maxReSignTimes > 0) {
                                    sign(context, signData, type, callBack);
                                    maxReSignTimes--;
                                }
                                break;
                            case "0x81200003"://用户未激活
                            case "0x81200006"://密码错误次数过多，被锁定
                            case "0x14300001"://本地无证书，重新激活
                            case "0x12200000":
                            case "0x8120000B"://用户已激活且在设备上有证书但证书已废止 引导用户找回证书
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("无法签名");
                                builder.setMessage(result.getErrMsg());
                                builder.setNegativeButton("去激活/找回", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SignatureActivity.start(context);
                                    }
                                });
                                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.create().show();
                                break;
                            case "0x8120000A"://云服务平台内无此用户 引导用户注册并生成证书
                                toRegisterSign(context);
                                break;
                            case "0x00000000"://交互成功
                                maxReSignTimes = 3;
                                String jobId = (result.getSignDataInfos() != null && result.getSignDataInfos().size() > 0) ? result.getSignDataInfos().get(0).getSignDataJobID() : "";
                                String signature = (result.getSignDataInfos() != null && result.getSignDataInfos().size() > 0) ? result.getSignDataInfos().get(0).getSignature() : "";
                                callBack.signSuccess(signature, jobId);
                                break;
                            default:
                                break;
                        }
                    }
                });
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtil.showMessage(context, msg);
                return super.requestError(exception, code, msg);
            }
        });

    }


    public void checkSign(Context context) {
        SignetCoreApi.useCoreFunc(new CheckStateCallBack(context, MyApplication.userInfo.getMsspId()) {
            @Override
            public void onCheckKeyStateResult(UserStateResult userStateResult) {
                switch (userStateResult.getErrCode()) {
                    case "0x8120000A"://云服务平台内无此用户 引导用户注册并生成证书
                        break;
                    case "0x81200003"://云服务平台内有此用户但用户未激活过证书 引导用户注册并生成证书
                        break;
                    case "0x14300001"://用户已激活但在设备上无证书 引导用户找回证书
                        break;
                    case "0x81200006"://用户已激活且在设备上有证书但证书被锁定 引导用户等待或找回证书
                        break;
                    case "0x8120000B"://用户已激活且在设备上有证书但证书已废止 引导用户找回证书
                        break;
                    case "0":
                        break;
                    default:break;
                }
            }
        });
    }

    public static void toRegisterSign(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("无效用户");
        builder.setMessage("查询不到当前用户，请先注册电子签名!");
        builder.setNegativeButton("去注册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ApplySignatureActivity.start(context);
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    public interface SignCallBack {
        /**
         * 签名验证成功
         */
        void signSuccess(String signature, String jobId);

        /**
         * 签名验证失败
         */
//        void signFailed(String code);

    }


}
