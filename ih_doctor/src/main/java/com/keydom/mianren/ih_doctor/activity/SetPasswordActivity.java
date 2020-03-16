package com.keydom.mianren.ih_doctor.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.manager.ImPreferences;
import com.keydom.ih_common.im.manager.NimUserInfoCache;
import com.keydom.ih_common.im.manager.TeamDataCache;
import com.keydom.ih_common.minterface.OnLoginListener;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.controller.SetPasswordController;
import com.keydom.mianren.ih_doctor.activity.view.SetPasswordView;
import com.keydom.mianren.ih_doctor.bean.LoginBean;
import com.keydom.mianren.ih_doctor.constant.Const;

/**
 * @Name：com.kentra.yxyz.activity
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/2 下午5:32
 * 修改人：xusong
 * 修改时间：18/11/2 下午5:32
 */
public class SetPasswordActivity extends BaseControllerActivity<SetPasswordController> implements SetPasswordView {

    private Button updateFinishBtn;
    private EditText passwordEt;
    private EditText rePasswordEt;
    private boolean isFromLogin = false;


    /**
     * 启动修改密码页面
     *
     * @param context
     * @param phoneNo 电话号码
     */
    public static void start(Context context, String phoneNo) {
        Intent starter = new Intent(context, SetPasswordActivity.class);
        starter.putExtra(Const.PHONE_NUM, phoneNo);
        context.startActivity(starter);
    }

    /**
     * 启动修改密码页面
     *
     * @param context
     * @param phoneNo 电话号码
     */
    public static void start(Context context, String phoneNo,boolean isFromLogin) {
        Intent starter = new Intent(context, SetPasswordActivity.class);
        starter.putExtra(Const.PHONE_NUM, phoneNo);
        starter.putExtra(Const.IS_FROM_LOGIN, isFromLogin);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_setpassword_layout;
    }

    @Override
    public void updateSuccess(LoginBean userInfo) {
        SharePreferenceManager.setUserCode(userInfo.getUserCode());
        SharePreferenceManager.setToken("Bearer " + userInfo.getToken());
        SharePreferenceManager.setImToken(userInfo.getImToken());
        SharePreferenceManager.setPhoneNumber(userInfo.getPhoneNumber());
        if (userInfo.getRoleIds() != null && userInfo.getRoleIds().size() > 0) {
            SharePreferenceManager.setRoleId(userInfo.getRoleIds().get(0));
            SharePreferenceManager.setPositionId(userInfo.getNurseMonitorState());
            SharePreferenceManager.setUserPwd(getPassword());
        } else {
            SharePreferenceManager.setRoleId(-1);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("无角色权限");
            builder.setMessage("没有分配角色，请联系管理员分配角色后重新登录！");
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setCancelable(false);
            builder.create().show();
            return;
        }
        if (TextUtils.isEmpty(ImClient.getUserInfoProvider().getAccount())) {
            ImClient.loginIM(userInfo.getUserCode(), userInfo.getImToken(), new OnLoginListener() {
                @Override
                public void success(String msg) {
                    ImClient.getUserInfoProvider().setAccount(userInfo.getUserCode());
                    NimUserInfoCache.getInstance().buildCache();
                    TeamDataCache.getInstance().buildCache();
                    ImPreferences.saveUserAccount(userInfo.getUserCode());
                    ImPreferences.saveUserToken(userInfo.getImToken());
                    MainActivity.start(SetPasswordActivity.this,false,false);
                }

                @Override
                public void failed(String errMsg) {
                    LoginActivity.start(SetPasswordActivity.this);
                    ToastUtil.showMessage(SetPasswordActivity.this, "即时通讯账号异常，请检查后重新登录");
                }
            });
        } else {
            if(isFromLogin){
                finish();
            }else{
                MainActivity.start(SetPasswordActivity.this,false,false);
            }

        }


    }

    @Override
    public void updateFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), errMsg);
    }

    @Override
    public String getPassword() {
        return passwordEt.getText().toString();
    }

    @Override
    public String getRePassword() {
        return rePasswordEt.getText().toString();
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("修改密码");
        updateFinishBtn = (Button) this.findViewById(R.id.update_finish_btn);
        passwordEt = (EditText) this.findViewById(R.id.password_input_et);
        rePasswordEt = (EditText) this.findViewById(R.id.re_password_input_et);
        updateFinishBtn.setOnClickListener(getController());

        isFromLogin = getIntent().getBooleanExtra(Const.IS_FROM_LOGIN,false);
    }
}
