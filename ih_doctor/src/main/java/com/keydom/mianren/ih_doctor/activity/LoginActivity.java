package com.keydom.mianren.ih_doctor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Global;
import com.keydom.ih_common.push.PushManager;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.controller.LoginController;
import com.keydom.mianren.ih_doctor.activity.personal.PersonalInfoActivity;
import com.keydom.mianren.ih_doctor.activity.view.LoginView;
import com.keydom.mianren.ih_doctor.bean.LoginBean;
import com.keydom.mianren.ih_doctor.bean.UserInfo;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

/**
 * @Name：com.kentra.yxyz.activity
 * @Description：登录Activity
 * @Author：song
 * @Date：18/11/2 下午4:37
 * 修改人：xusong
 * 修改时间：18/11/2 下午4:37
 */
public class LoginActivity extends BaseControllerActivity<LoginController> implements LoginView {

    private Button loginBt;
    private TextView forgetTxt;
    private EditText userIdEt, userPwdEt, verificationCode;
    private RelativeLayout getCodeRl;
    private ImageView mImageView;
    /**
     * 是否需要上传验证码
     */
    private boolean codeShow = false;

    /**
     * 打开登陆页面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter =
                new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_login_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(false, true, false);
        setTitle("登录");
        loginBt = findViewById(R.id.login_btn);
        forgetTxt = findViewById(R.id.forget_password_txt);
        userIdEt = findViewById(R.id.user_id);
        userPwdEt = findViewById(R.id.user_password);
        getCodeRl = findViewById(R.id.get_identifying_code_rl);
        verificationCode = findViewById(R.id.verificationCode);
        mImageView = findViewById(R.id.identifying_code_iv);
        forgetTxt.setOnClickListener(getController());
        loginBt.setOnClickListener(getController());
        userIdEt.setText(SharePreferenceManager.getUserId());
        userPwdEt.setText(SharePreferenceManager.getUserPwd());
        mImageView.setOnClickListener(getController());

    }

    @Override
    public void loginSuccess(LoginBean info) {
        MyApplication.userInfo = new UserInfo();
        Global.setUserId(info.getId());
        SharePreferenceManager.setUserId(userIdEt.getText().toString());
        SharePreferenceManager.setUserPwd(userPwdEt.getText().toString());
        SharePreferenceManager.setToken("Bearer " + info.getToken());
        SharePreferenceManager.setImToken(info.getImToken());
        SharePreferenceManager.setUserCode(info.getUserCode());
        SharePreferenceManager.setPhoneNumber(info.getPhoneNumber());
        SharePreferenceManager.setHospitalId(info.getHospitalId());
        SharePreferenceManager.setIdCard(info.getIdCard());
        SharePreferenceManager.setAutonyState(info.getAutonymState());
        if (info.getRoleIds() != null && info.getRoleIds().size() > 0) {
            //            if (info.getRoleIds().size() == 1) {
            SharePreferenceManager.setRoleId(info.getRoleIds().get(0));
            SharePreferenceManager.setPositionId(info.getNurseMonitorState());
            PushManager.setAlias(getContext(), info.getPhoneNumber());
            MyApplication.isNeedInit = false;
            if (info.isAutony()) {
                MainActivity.start(LoginActivity.this, false, false);
            } else {
                PersonalInfoActivity.start(LoginActivity.this, TypeEnum.FIRST_FINISH_INFO);
            }

            CommonUtils.hideSoftKeyboard(this);
            //            } else {
            //                DialogUtils.showSingleAlertDialog(LoginActivity.this, info
            //                .getRoleIds(), new OnSelectRoleListener() {
            //                    @Override
            //                    public void selectRole(Integer roleId) {
            //                        SharePreferenceManager.setRoleId(roleId);
            //                        SharePreferenceManager.setPositionId(info
            //                        .getNurseMonitorState());
            //                        MainActivity.start(LoginActivity.this);
            //                        CommonUtils.hideSoftKeyboard(LoginActivity.this);
            //                    }
            //
            //                    @Override
            //                    public void selectCancel() {
            //                        finish();
            //                    }
            //                });
            //            }


        } else {
            SharePreferenceManager.setRoleId(-1);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("无角色权限");
            builder.setMessage("没有分配角色，请联系管理员分配角色后重新登录！");
            builder.setNegativeButton("确定", (dialog, which) -> {
            });
            builder.setCancelable(false);
            builder.create().show();
        }
    }

    @Override
    public void loginFailed(int code, String msg) {
        if (code == Const.UPDATE_PASSWORD) {
            SharePreferenceManager.setUserId(userIdEt.getText().toString());
            SharePreferenceManager.setUserPwd(userPwdEt.getText().toString());
            SharePreferenceManager.setIsAgreement(true);
            SharePreferenceManager.setFirstFinishInfo(true);
            SharePreferenceManager.setIsFirst(true);
            userPwdEt.setText("");
            SetPasswordActivity.start(getContext(), userIdEt.getText().toString(), true);
        } else if (code == 305 || code == 307) {
            showCodeEt();
            getController().getLoginCode(getUserName());
        } else {
            ToastUtil.showMessage(getContext(), msg);
        }

    }

    @Override
    public void getLoginCodeSuccess(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        mImageView.setImageBitmap(decodedByte);
    }

    @Override
    public void getLoginCodeFailed(String msg) {
        ToastUtil.showMessage(getContext(), msg);
    }

    @Override
    public String getPassword() {
        return userPwdEt.getText().toString();
    }

    private void showCodeEt() {
        codeShow = true;
        getCodeRl.setVisibility(View.VISIBLE);
    }

    @Override
    public String getUserName() {
        return userIdEt.getText().toString();
    }

    @Override
    public String getCode() {
        return verificationCode.getText().toString();
    }

    @Override
    public boolean isCode() {
        return codeShow;
    }


}
