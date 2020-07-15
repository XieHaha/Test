package com.keydom.mianren.ih_patient.utils.pay.weixin;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * 微信支付
 * Created by tsy on 16/6/1.
 */
public class WXInit {

    private static WXInit mWXPay;
    private IWXAPI mWXApi;
    private String mPayParam;
    private WXPayResultCallBack mCallback;
    private WXLoginResultCallBack mLoginCallback;

    /**
     * 未安装微信或微信版本过低
     */
    public static final int NO_OR_LOW_WX = 1;
    /**
     * 支付参数错误
     */
    public static final int ERROR_PAY_PARAM = 2;
    /**
     * 支付失败
     */
    public static final int ERROR_PAY = 3;

    public static String WX_APP_ID = "wx8c08e2a7ec34269e";
    /**
     * 获取第一步的code后，请求以下链接获取access_token
     */
    private String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /**
     * 获取用户个人信息
     */
    private String WX_APP_SECRET = "137b3992b25c35deccc19d7d58e72da3";


    /**
     * 支付回调监听
     */
    public interface WXPayResultCallBack {
        void onSuccess(); //支付成功

        void onError(int error_code);   //支付失败

        void onCancel();    //支付取消
    }

    public interface WXLoginResultCallBack {
        void onSuccess(String userInfo); //登录成功

        void onError(int error_code, String error_msg);   //登录失败

        void onCancel();//取消登录
    }

    /**
     * 初始化微信数据
     *
     * @param context
     * @param wx_appid
     */
    public WXInit(Context context, String wx_appid) {
        mWXApi = WXAPIFactory.createWXAPI(context, null);
        mWXApi.registerApp(wx_appid);
    }

    /**
     * 初始化
     *
     * @param context
     * @param wx_appid
     */
    public static void init(Context context, String wx_appid) {
        if (mWXPay == null) {
            mWXPay = new WXInit(context, wx_appid);
        }
    }

    public static WXInit getInstance() {
        return mWXPay;
    }

    public IWXAPI getWXApi() {
        return mWXApi;
    }

    /**
     * 发起登录
     *
     * @param loginCallback
     */
    public void doLogin(WXLoginResultCallBack loginCallback) {
        this.mLoginCallback = loginCallback;
        if (mLoginCallback == null) {
            ToastUtils.showShort("callback为空");
            return;
        }

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        req.state = String.valueOf(System.currentTimeMillis());
        mWXApi.sendReq(req);
    }

    /**
     * //登录回调响应
     *
     * @param baseResp
     */

    public void onLoginResp(BaseResp baseResp) {
        if (mLoginCallback == null) {
            return;
        }
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:

                String code = String.valueOf(((SendAuth.Resp) baseResp).code);
                //获取用户信息

                String get_access_token = getCodeRequest(code);

                mLoginCallback.onSuccess(get_access_token);


                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                mLoginCallback.onError(BaseResp.ErrCode.ERR_AUTH_DENIED, "用户拒绝授权");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消登录
                mLoginCallback.onCancel();
                break;
            default:
                break;
        }

        mLoginCallback = null;
    }

    /**
     * 发起微信支付
     */
    public void doPay(String pay_param, WXPayResultCallBack callback) {
        mPayParam = pay_param;
        mCallback = callback;

        if (!check()) {
            if (mCallback != null) {
                mCallback.onError(NO_OR_LOW_WX);
            }
            return;
        }

        JSONObject param = null;
        try {
            param = new JSONObject(mPayParam);
        } catch (JSONException e) {
            e.printStackTrace();
            if (mCallback != null) {
                mCallback.onError(ERROR_PAY_PARAM);
            }
            return;
        }
        if (TextUtils.isEmpty(param.optString("appid")) || TextUtils.isEmpty(param.optString("partnerid"))
                || TextUtils.isEmpty(param.optString("prepayid")) ||
                TextUtils.isEmpty(param.optString("noncestr")) || TextUtils.isEmpty(param.optString("timestamp")) ||
                TextUtils.isEmpty(param.optString("sign"))) {
            if (mCallback != null) {
                mCallback.onError(ERROR_PAY_PARAM);
            }
            return;
        }

        PayReq req = new PayReq();
        req.appId = param.optString("appid");
        req.partnerId = param.optString("partnerid");
        req.prepayId = param.optString("prepayid");
        if (TextUtils.isEmpty(param.optString("package"))) {
            req.packageValue = "Sign=WXInit";
        } else {
            req.packageValue = param.optString("package");
        }
        req.nonceStr = param.optString("noncestr");
        req.timeStamp = param.optString("timestamp");
        req.sign = param.optString("sign");

        mWXApi.sendReq(req);
    }

    //支付回调响应
    public void onResp(int error_code) {
        if (mCallback == null) {
            return;
        }

        if (error_code == 0) {   //成功
            mCallback.onSuccess();
        } else if (error_code == -1) {   //错误
            mCallback.onError(ERROR_PAY);
        } else if (error_code == -2) {   //取消
            mCallback.onCancel();
        }

        mCallback = null;
    }

    //检测是否支持微信支付
    private boolean check() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }


    /**
     * 获取access_token的URL（微信）
     *
     * @param code 授权时，微信回调给的
     * @return URL
     */
    private String getCodeRequest(String code) {
        String result = null;
        GetCodeRequest = GetCodeRequest.replace("APPID",
                urlEnodeUTF8(WX_APP_ID));
        GetCodeRequest = GetCodeRequest.replace("SECRET",
                urlEnodeUTF8(WX_APP_SECRET));
        GetCodeRequest = GetCodeRequest.replace("CODE", urlEnodeUTF8(code));
        result = GetCodeRequest;
        return result;
    }

    private String urlEnodeUTF8(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
