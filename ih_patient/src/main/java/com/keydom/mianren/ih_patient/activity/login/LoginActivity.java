package com.keydom.mianren.ih_patient.activity.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.push.PushManager;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.ClearEditText;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.index_main.MainActivity;
import com.keydom.mianren.ih_patient.activity.login.controller.LoginController;
import com.keydom.mianren.ih_patient.activity.login.view.ILoginView;
import com.keydom.mianren.ih_patient.bean.AuthResult;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.UserInfo;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.utils.LocalizationUtils;
import com.keydom.mianren.ih_patient.utils.pay.weixin.WXInit;
import com.orhanobut.logger.Logger;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @Name：com.keydom.ih_patient.activity
 * @Description：登录页面
 * @Author：song
 * @Date：18/11/9 上午11:13
 * 修改人：xusong
 * 修改时间：18/11/9 上午11:13
 */
public class LoginActivity extends BaseControllerActivity<LoginController> implements ILoginView {
    public final static int FLAG_ALIPAY_LOGIN = 2;

    public final static int ALI_LOGIN = 1;
    public final static int WX_LOGIN = 4;
    public final static int QQ_LOGIN = 3;
    private boolean isLoginLocked=false;

    /**
     * 启动
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);//.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(starter);
    }
    private Button loginBtn;
    private TextView registerText,loginWarningTv,forgetPasswordTv;
    private ClearEditText userIdCedt,userPasswordCedt,validateEdt;
    private LinearLayout login_base_layout;
    private RelativeLayout validateLayout;
    private ImageView validateImg;
    private EditText messageCodeEdt;
    private boolean isExitApp=false;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_login_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.login));
        getTitleLayout().initViewsVisible(false,true,false);
        loginBtn=findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(getController());
        registerText=findViewById(R.id.regist);
        registerText.setOnClickListener(getController());
        loginWarningTv=findViewById(R.id.login_warning_tv);

        login_base_layout=findViewById(R.id.login_base_layout);
        validateLayout=findViewById(R.id.validate_layout);
        validateEdt=findViewById(R.id.validate_edt);
        validateImg=findViewById(R.id.validate_img);
        validateImg.setOnClickListener(getController());
        messageCodeEdt=this.findViewById(R.id.message_code_cedt);
        forgetPasswordTv=findViewById(R.id.forget_password_txt);
        forgetPasswordTv.setOnClickListener(getController());
        userIdCedt=findViewById(R.id.user_id);
        userIdCedt.setText(SharePreferenceManager.getUserCode());
        userPasswordCedt=findViewById(R.id.user_password);
        findViewById(R.id.qq_login).setOnClickListener(v -> QQLogin());
        findViewById(R.id.wx_login).setOnClickListener(v -> WXLogin());
        findViewById(R.id.ali_login).setOnClickListener(v -> getController().getAliAuth());
        checkPermission();
        EventBus.getDefault().register(getContext());
    }

    @Override
    public void loginSuccess(UserInfo data) {
        isQQFirst = true;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            if(isLoginLocked)
                isLoginLocked=false;
            LocalizationUtils.fileSave2Local(getContext(),data,"userInfo");
            Global.setUserId(data.getId());
            Global.setMember(data.getMember());
            App.userInfo=data;
            PushManager.setAlias(getContext(), data.getId()+"");
            EventBus.getDefault().post(new Event(EventType.UPDATELOGINSTATE,null));
            App.isNeedInit=false;
            SharePreferenceManager.setToken("User " + data.getToken());
            MainActivity.start(this,false);
        }
    }


    /**
     * 检查相关权限
     */
    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .MOUNT_UNMOUNT_FILESYSTEMS)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 1);

        } else {

        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        } else {

        }


    }


    @Override
    public void loginFailed(String msg) {
        ToastUtil.showMessage(this,msg);
    }

    @Override
    public String getAccount() {
        return userIdCedt.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return userPasswordCedt.getText().toString().trim();
    }

    @Override
    public void loginLocked() {
        isLoginLocked=true;
        validateLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void getValidateCodeSuccess(String validateCode) {
        byte[] decodedString = Base64.decode(validateCode, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        validateImg.setPadding(0,0,0,0);
        validateImg.setImageBitmap(decodedByte);
    }

    @Override
    public void getValidateCodeFailed(String errMsg) {
        ToastUtil.showMessage(this,errMsg);
        validateImg.setPadding(20,5,5,20);
        validateImg.setImageResource(R.drawable.refresh);
    }

    @Override
    public String getAccountMobile() {
        return userIdCedt.getText().toString().trim();
    }


    @Override
    public void showWarnning() {
        loginWarningTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWarnning() {
        loginWarningTv.setVisibility(View.GONE);
    }

    @Override
    public void getAliAuth(String authCode) {
        AliLogin(authCode);
    }

    @Override
    public void goBindPhone(String uid,int type) {
        Intent i = new Intent(this,RegisterActivity.class);
        i.putExtra(RegisterActivity.TYPE,type);
        i.putExtra(RegisterActivity.UID,uid);
        ActivityUtils.startActivity(i);
        isQQFirst = true;
    }

    @Override
    public boolean isLoginLocked() {
        return isLoginLocked;
    }

    @Override
    public String getValidateCode() {
        return validateEdt.getText().toString().trim();
    }

    @Override
    public void toChangePwd(String msg) {
        ToastUtil.showMessage(App.mApplication,msg);
        UpdatePasswordActivity.start(getContext());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){


            if (isExitApp) {
                this.finish();
            } else {
                exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 退出app
     */
    private void exitApp() {
        isExitApp = true;
        ToastUtil.showMessage(LoginActivity.this,"再按一次返回退出");
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                isExitApp = false;
                this.cancel();
            }
        }.start();
    }

    private Tencent mTencent;
    private String QQ_APP_ID = "101544116";
    private IUiListener loginListener;
    private String SCOPE = "all";
    private boolean isQQFirst = true;
    /**
     * QQ登录
     */
    private void QQLogin(){
        mTencent = Tencent.createInstance(QQ_APP_ID,getApplicationContext());
        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                //登录成功后回调该方法
                String token;
                String expires_in;
                String uniqueCode;
                try {
                    token = ((JSONObject) o).getString("access_token");
                    expires_in = ((JSONObject) o).getString("expires_in");
                    uniqueCode = ((JSONObject) o).optString("openid"); //QQ的openid
                    mTencent.setOpenId(uniqueCode);
                    mTencent.setAccessToken(token, expires_in);
                    if (isQQFirst){
                        isQQFirst = false;
                        getController().loginTrilateral(uniqueCode,QQ_LOGIN);
                    }
                    LogUtils.d("token:"+token+"     expires_in:"+expires_in+"      uniqueCode :"+uniqueCode);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //获取用户信息
            }

            @Override
            public void onError(UiError uiError) {
                //登录失败后回调该方法
                LogUtils.e("LoginError:"+uiError.toString());
            }

            @Override
            public void onCancel() {
                //取消登录后回调该方法
            }
        };
        mTencent.login(this, SCOPE, loginListener);
    }

    /**
     * 获取QQ用户数据
     */
    private void getUserInfo() {
        QQToken token = mTencent.getQQToken();
         com.tencent.connect.UserInfo mInfo = new com.tencent.connect.UserInfo(LoginActivity.this, token);
        mInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                JSONObject jb = (JSONObject) object;
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN) {
            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
                Tencent.handleResultData(data, loginListener);
            }
        }
    }

    /**
     * 微信登录
     */
    private void WXLogin(){
        WXInit.getInstance().doLogin(new WXInit.WXLoginResultCallBack() {
            @Override
            public void onSuccess(String userInfo) {

                OkHttpClient okHttpClient = new OkHttpClient();
                final Request request = new Request.Builder().url(userInfo).get().build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseInfo = response.body().string();
                        String access = null;
                        String openId = null;
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo);
                            access = jsonObject.getString("access_token");
                            openId = jsonObject.getString("openid");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(openId!=null){
                            Logger.e("微信登陆成功 openid==  "+openId);
                            EventBus.getDefault().post(new Event(EventType.WXLOGINOPENID,openId));
                        }
                    }
                });
            }

            @Override
            public void onError(int error_code, String error_msg) {
                LogUtils.e("微信登陆失败"+error_msg);
            }

            @Override
            public void onCancel() {

            }
        });
    }
    @Subscribe(threadMode =ThreadMode.MAIN)
    public void getOpenId(Event event){
        if(event.getType()==EventType.WXLOGINOPENID){
            String openId= (String) event.getData();
            getController().loginTrilateral(openId,WX_LOGIN);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FLAG_ALIPAY_LOGIN: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    LogUtils.d("授权结果:"+authResult);
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern0_token 的value
                        // 传入，则支付账户为该授权账户
                        getController().loginTrilateral(authResult.getUserId(),ALI_LOGIN);
                    } else {
                        // 其他状态值则为授权失败
                        ToastUtils.showShort("授权失败");
                    }
                    break;
                }
            }
        }
    };

    /**
     * 支付宝登录
     */
    private void AliLogin(String authInfo){
        Runnable authRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(LoginActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);
                Message msg = new Message();
                msg.what = FLAG_ALIPAY_LOGIN;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(getContext());
    }
}
