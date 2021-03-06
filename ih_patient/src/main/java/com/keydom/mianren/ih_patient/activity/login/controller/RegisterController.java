package com.keydom.mianren.ih_patient.activity.login.controller;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.manager.ImPreferences;
import com.keydom.ih_common.im.manager.NimUserInfoCache;
import com.keydom.ih_common.im.manager.TeamDataCache;
import com.keydom.ih_common.im.utils.MD5;
import com.keydom.ih_common.minterface.OnLoginListener;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.PhoneUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.common_document.CommonDocumentActivity;
import com.keydom.mianren.ih_patient.activity.index_main.MainActivity;
import com.keydom.mianren.ih_patient.activity.login.view.IRegisterView;
import com.keydom.mianren.ih_patient.activity.user_info_operate.UserInfoOperateActivity;
import com.keydom.mianren.ih_patient.bean.CommonDocumentBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.UserInfo;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.LoginService;
import com.keydom.mianren.ih_patient.net.UserService;
import com.keydom.mianren.ih_patient.utils.LocalizationUtils;
import com.keydom.mianren.ih_patient.utils.RegularUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * ???????????????
 */
public class RegisterController extends ControllerImpl<IRegisterView> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private boolean isAgreement = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_identifying_code_bt:
                if (StringUtils.isEmpty(getView().getPhoneNum()) || !PhoneUtils.isMobileEnable(getView().getPhoneNum())) {
                    ToastUtil.showMessage(getContext(), "??????????????????????????????");
                } else {
                    if (getView().getType() == 0) {
                        getMsgCode(getView().getPhoneNum());
                    } else {
                        sendBindPhone();
                    }

                }

                break;
            case R.id.register_next_btn:
                if (StringUtils.isEmpty(getView().getPhoneNum()) || !PhoneUtils.isMobileEnable(getView().getPhoneNum())) {
                    ToastUtil.showMessage(getContext(), "??????????????????????????????");
                } else if (StringUtils.isEmpty(getView().getMsgCode())) {
                    ToastUtil.showMessage(getContext(), "??????????????????");
                } else {
                    if (getView().isBind()) {
                        verificationCodeTeral();
                    } else {
                        inspecteMsgCode();
                    }
                }
                break;
            case R.id.next_step:
                if (isAgreement) {
                    if (StringUtils.isEmpty(getView().getPassWord()) || StringUtils.isEmpty(getView().getAccount())) {
                        ToastUtil.showMessage(getContext(), "?????????????????????????????????");
                    } else if (!getView().getPassWord().equals(getView().getRePassWord())) {
                        ToastUtil.showMessage(getContext(), "???????????????????????????????????????");
                    } else {
                        if (RegularUtils.PassWordValidate(getView().getPassWord())) {
                            doRegister();
                        } else {
                            ToastUtil.showMessage(getContext(), "?????????????????????????????????????????????");
                        }
                    }
        /*else if(!RegularUtils.PassWordValidate(getView().getPassWord())){
                        ToastUtil.showMessage(getContext(),"???????????????????????????????????????????????????????????????");
                    }*/
                } else {
                    Toast.makeText(getContext(), "?????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fill_personal_tv:
                UserInfoOperateActivity.start(getContext(),UserInfoOperateActivity.EDITTYPE);
                getView().Finish();
                break;
            case R.id.complete_regist_tv:
                MainActivity.start(getContext(),false);
                getView().Finish();
                break;
            case R.id.jump_to_user_agreement_tv:
//                AgreementActivity.startRegisterAgreement(getContext());
                CommonDocumentActivity.start(getContext(),CommonDocumentBean.CODE_8);
                break;

        }
    }

    /**
     * ????????????
     */
    private void doRegister() {
        Map<String, Object> map = new HashMap<>();
        map.put("userAccount", getView().getAccount());
        map.put("password", MD5.getStringMD5(getView().getPassWord()));
        map.put("phoneNumber", getView().getPhoneNum());
        map.put("type", getView().getType());
        map.put("uid", getView().getUid());
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).doRegister(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<UserInfo>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable UserInfo data) {
                hideLoading();
                ToastUtil.showMessage(getContext(), "????????????");
                ImClient.loginIM(data.getId() + "", data.getImToken(), new OnLoginListener() {
                    @Override
                    public void success(String msg) {
                        ImClient.getUserInfoProvider().setAccount(data.getUserAccount());
                        NimUserInfoCache.getInstance().buildCache();
                        TeamDataCache.getInstance().buildCache();
                        ImPreferences.saveUserAccount(data.getId() + "");
                        ImPreferences.saveUserToken(data.getImToken());
//                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                        getView().registerSuccess(data);
                    }

                    @Override
                    public void failed(String errMsg) {
//                        ToastUtil.showMessage(getContext(), "IM????????????" + errMsg);
                        Logger.e(errMsg);
                    }
                });

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().registerFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * ???????????????
     */
    private void getMsgCode(String s) {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).sendCode(s,null), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {

            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().getMsgCodeSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getMsgCodeFailed(msg);
                return super.requestError(exception, code, msg);

            }
        });
    }

    /**
     * ???????????????
     */
    private void inspecteMsgCode() {
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", getView().getPhoneNum());
        map.put("verificationCode", getView().getMsgCode());
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).verificationCode(map), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().msgInspectSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().msgInspectFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    /**
     * ???????????????
     */
    private void sendBindPhone() {
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNumber", getView().getPhoneNum());
        map.put("type", getView().getType());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).sendCodeTrilateral(map), new HttpSubscriber<Object>(getContext(), getDisposable(), true, true
        ) {
            @Override
            public void requestComplete(@Nullable Object data) {
                getView().getMsgCodeSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                if (code == 201) {
                    getView().bindPhone();
                    getView().getMsgCodeSuccess();
                }
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * ???????????????
     */
    private void verificationCodeTeral() {
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNumber", getView().getPhoneNum());
        map.put("type", getView().getType());
        map.put("verificationCode", getView().getMsgCode());
        map.put("uid", getView().getUid());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(LoginService.class).verificationCodeTeral(map), new HttpSubscriber<UserInfo>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable UserInfo data) {
                if (getView().isBind()) {
                    ToastUtil.showMessage(getContext(), "????????????" + data.getId());
                    ImClient.loginIM(data.getId() + "", data.getImToken(), new OnLoginListener() {
                        @Override
                        public void success(String msg) {
                            ImClient.getUserInfoProvider().setAccount(data.getUserAccount());
                            NimUserInfoCache.getInstance().buildCache();
                            TeamDataCache.getInstance().buildCache();
                            ImPreferences.saveUserAccount(data.getId() + "");
                            ImPreferences.saveUserToken(data.getImToken());
                            LocalizationUtils.fileSave2Local(getContext(),data,"userInfo");
                            Global.setUserId(data.getId());
                            Global.setMember(data.getMember());
                            EventBus.getDefault().post(new Event(EventType.UPDATELOGINSTATE,null));
                            App.userInfo=data;
                            SharePreferenceManager.setUserCode(data.getUserAccount());
                            MainActivity.start(getContext(),false);
                        }

                        @Override
                        public void failed(String errMsg) {
                            ToastUtil.showMessage(getContext(), "IM????????????" + errMsg);
                            Logger.e(errMsg);
                        }
                    });
                } else {
                    getView().msgInspectSuccess();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        isAgreement = b;
    }
}
