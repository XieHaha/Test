package com.keydom.mianren.ih_patient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_patient.R;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

/**
 * @Name：com.kentra.yxyz.activity
 * @Description：协议页面
 * @Author：song
 * @Date：18/11/2 下午5:47
 * 修改人：xusong
 * 修改时间：18/11/2 下午5:47
 */
public class AgreementActivity extends BaseActivity {

    public static int AGREEMENT_PAY = 555;
    public static int AGREEMENT_REGISTER = 556;
    public static int AGREEMENT_NURSE_SERVICE = 557;
    public static int AGREEMENT_IN_HOSPITAL = 558;
    public static int AGREEMENT_ONLINE_DIAGNOSE = 559;
    public static String htmlStr = "";
    private TextView qaTv;
    private int mType;

    /**
     * 启动支付服务协议页面
     *
     * @param context
     */
    public static void startPayAgreement(Context context) {
        Intent starter = new Intent(context, AgreementActivity.class);
        starter.putExtra(Const.TYPE, AGREEMENT_PAY);
        context.startActivity(starter);
    }

    /**
     * 启动护理服务协议页面
     *
     * @param context
     */
    public static void startNurseAgreement(Context context) {
        Intent starter = new Intent(context, AgreementActivity.class);
        starter.putExtra(Const.TYPE, AGREEMENT_NURSE_SERVICE);
        context.startActivity(starter);
    }
    /**
     * 启动注册协议页面
     *
     * @param context
     */
    public static void startRegisterAgreement(Context context) {
        Intent starter = new Intent(context, AgreementActivity.class);
        starter.putExtra(Const.TYPE, AGREEMENT_REGISTER);
        context.startActivity(starter);
    }
    /**
     * 启动入院注意事项页面
     *
     * @param context
     */
    public static void startBeInHospitalAgreement(Context context) {
        Intent starter = new Intent(context, AgreementActivity.class);
        starter.putExtra(Const.TYPE, AGREEMENT_IN_HOSPITAL);
        context.startActivity(starter);
    }

    /**
     * 在线问诊用户协议
     *
     * @param context
     */
    public static void startOnLineDiagnoseAgreement(Context context) {
        Intent starter = new Intent(context, AgreementActivity.class);
        starter.putExtra(Const.TYPE, AGREEMENT_IN_HOSPITAL);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qaTv = (TextView) this.findViewById(R.id.qa_tv);
        mType = getIntent().getIntExtra(Const.TYPE, -1);
        getTitleLayout().initViewsVisible(true,true,false);
        if (mType == AGREEMENT_PAY) {
            htmlStr = CommonUtils.getFromRaw(this, R.raw.pay);
            setTitle("支付服务协议");
        } else if (mType == AGREEMENT_REGISTER) {
            htmlStr = CommonUtils.getFromRaw(this, R.raw.register);
            setTitle("用户注册服务协议");
        } else if (mType == AGREEMENT_NURSE_SERVICE) {
            htmlStr = CommonUtils.getFromRaw(this, R.raw.nurse_service);
            setTitle("护理服务用户协议");
        } else if (mType == AGREEMENT_IN_HOSPITAL) {
            htmlStr = CommonUtils.getFromRaw(this, R.raw.be_in_hospital);
            setTitle("住院须知");
        } else if (mType == AGREEMENT_ONLINE_DIAGNOSE) {
            htmlStr = CommonUtils.getFromRaw(this, R.raw.diagnose_service);
            setTitle("在线问诊用户协议");
        }
        setText();
    }

    /**
     * 设置协议内容
     */
    private void setText() {
        RichText.from(htmlStr).bind(this)
                .showBorder(false)
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                .into(qaTv);
    }
}
