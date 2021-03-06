package com.keydom.mianren.ih_doctor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.controller.AgreementController;
import com.keydom.mianren.ih_doctor.activity.view.AgreementView;
import com.keydom.mianren.ih_doctor.bean.AgreementBean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import org.greenrobot.eventbus.EventBus;

/**
 * @Name：com.kentra.yxyz.activity
 * @Description：协议页面
 * @Author：song
 * @Date：18/11/2 下午5:47
 * 修改人：xusong
 * 修改时间：18/11/2 下午5:47
 */
public class AgreementActivity extends BaseControllerActivity<AgreementController> implements AgreementView {

    /**
     * 用户注册协议
     */
    public static int REGISTER_PAGE_AGREEMENT = 555;
    /**
     * 服务开通协议
     */
    public static int SERVICE_AGREEMENT = 556;

    /**
     * 开启单个服务
     */
    public static int SERVICE_SINGLE = 557;
    private TextView qaTv;
    private TextView agreementTipTv;
    private Button establishedBt;
    private LinearLayout submitLin;
    /**
     * 页面类型
     */
    private int mType;
    /**
     * 协议内容
     */
    private String htmlStr = "";

    private long serviceId;

    /**
     * 启动用户服务协议页面
     *
     * @param context
     */
    public static void startService(Context context) {
        Intent starter = new Intent(context, AgreementActivity.class);
        starter.putExtra(Const.TYPE, SERVICE_AGREEMENT);
        context.startActivity(starter);
    }


    /**
     * 启动用户注册协议
     *
     * @param context
     */
    public static void startRegisterPage(Context context) {
        Intent starter = new Intent(context, AgreementActivity.class);
        starter.putExtra(Const.TYPE, REGISTER_PAGE_AGREEMENT);
        context.startActivity(starter);
    }


    /**
     * 启动单次用户服务协议页面
     *
     * @param context
     */
    public static void startSingleService(Context context, long serviceId) {
        Intent starter = new Intent(context, AgreementActivity.class);
        starter.putExtra(Const.TYPE, SERVICE_SINGLE);
        starter.putExtra(Const.DATA, serviceId);
        context.startActivity(starter);
    }


    /**
     * 设置协议内容
     */
    private void setText(String data) {
        RichText.from(data).bind(this)
                .showBorder(false)
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                .into(qaTv);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_agreement;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        qaTv = (TextView) this.findViewById(R.id.qa_tv);
        mType = getIntent().getIntExtra(Const.TYPE, -1);
        agreementTipTv = (TextView) this.findViewById(R.id.agreement_tip_tv);
        establishedBt = (Button) this.findViewById(R.id.established);
        submitLin = (LinearLayout) this.findViewById(R.id.submit_lin);
        serviceId = getIntent().getLongExtra(Const.DATA, 0);
        establishedBt.setOnClickListener(getController());
        if (mType == SERVICE_AGREEMENT || mType == SERVICE_SINGLE) {
            setRightTxt("跳过");
            if (mType == SERVICE_SINGLE)
                setRightImgVisibility(false);
            agreementTipTv.setVisibility(View.VISIBLE);
            setRightBtnListener(getController());
            if (SharePreferenceManager.getRoleId() == Const.ROLE_DOCTOR || SharePreferenceManager.getRoleId() == Const.ROLE_MEDICINE) {
                setTitle("言行医至问诊服务协议");
                agreementTipTv.setText(Html.fromHtml("提交后即代表您同意<font color='#3F98F7'>《言行医至问诊服务协议》</font>"));
                agreementTipTv.setOnClickListener(getController());
//                htmlStr = CommonUtils.getFromRaw(this, R.raw.diagnose_service);
                getController().getOfficialDispatchAllMsgByCode("011");
            } else {
                setTitle("言行医至护理服务协议");
                agreementTipTv.setText(Html.fromHtml("提交后即代表您同意<font color='#3F98F7'>《言行医至护理服务协议》</font>"));
                agreementTipTv.setOnClickListener(getController());
//                htmlStr = CommonUtils.getFromRaw(this, R.raw.nurse_service);
                getController().getOfficialDispatchAllMsgByCode("012");
            }
        } else if (mType == REGISTER_PAGE_AGREEMENT) {
            submitLin.setVisibility(View.GONE);
            setRightImgVisibility(false);
            setTitle("用户服务协议");
            //htmlStr = CommonUtils.getFromRaw(this, R.raw.register);
            getController().getOfficialDispatchAllMsgByCode("008");
        }

    }

    @Override
    public void openSuccess(String msg) {
        SharePreferenceManager.setIsAgreement(false);
        MainActivity.start(this,false,false);
    }

    @Override
    public void openFailed(String errMsg) {
        ToastUtil.showMessage(AgreementActivity.this, errMsg);
    }

    @Override
    public void enableServiceSuccess(String msg) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_USER_INFO).build());
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_MY_SERVICE).build());
        finish();
    }

    @Override
    public void enableServiceFailed(String errMsg) {
        ToastUtil.showMessage(AgreementActivity.this, errMsg);
    }

    @Override
    public long getServiceId() {
        return serviceId;
    }

    @Override
    public int getAgreementType() {
        return mType;
    }

    @Override
    public void getOfficialDispatchSuccess(AgreementBean data) {
        if(null != data && !TextUtils.isEmpty(data.getContent())){
            setText(data.getContent());
        }else{
            setText("");
        }

    }

    @Override
    public void getOfficialDispatchFailed(String errMsg) {
        ToastUtil.showMessage(getContext(),"获取协议失败");
    }
}
