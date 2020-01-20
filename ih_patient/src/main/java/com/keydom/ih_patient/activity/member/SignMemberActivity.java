package com.keydom.ih_patient.activity.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ActivityStackManager;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.controller.SignMemberController;
import com.keydom.ih_patient.activity.member.view.SignMemberView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.VIPCardInfoResponse;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

public class SignMemberActivity extends BaseControllerActivity<SignMemberController> implements SignMemberView {


    EditText mNameEt;
    EditText mIDEt;
    TextView mToPayTv;
    TextView mPriceTv;
    TextView mDescTv;
    CheckBox mAgreementCb;

    VIPCardInfoResponse mVIPCardInfo;
    private static final String VIP_CARD_INFO = "vip_card_info";

    /**
     * 启动
     */
    public static void start(Context context,VIPCardInfoResponse vipCardInfoResponse) {
        Intent intent = new Intent(context, SignMemberActivity.class);
        intent.putExtra(VIP_CARD_INFO,vipCardInfoResponse);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_sign_member;
    }


    @Override
    public void beforeInit() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().initViewsVisible(true, true, false);
        setTitle("仁医金卡签约");
        setWhiteBar();

        mVIPCardInfo = (VIPCardInfoResponse) getIntent().getSerializableExtra(VIP_CARD_INFO);

        mNameEt = findViewById(R.id.sign_member_name_edt);
        mIDEt = findViewById(R.id.sign_member_id_num_edt);
        mToPayTv = findViewById(R.id.pay_commit_tv);
        mPriceTv = findViewById(R.id.sign_member_price_tv);
        mDescTv = findViewById(R.id.sign_member_desc_tv);
        mAgreementCb = findViewById(R.id.sign_member_agreement_cb);

        mToPayTv.setOnClickListener(getController());

        getController().init();

        if(null != mVIPCardInfo){
            mPriceTv.setText(mVIPCardInfo.getPrice() + "元");
            mDescTv.setText(TextUtils.isEmpty(mVIPCardInfo.getDescription()) ? "" : mVIPCardInfo.getDescription());
        }
    }


    @Override
    public String getName() {
        String name = "";
        if (null != mNameEt) {
            name = mNameEt.getText().toString();
        }
        return name;
    }

    @Override
    public String getID() {
        String id = "";
        if (null != mIDEt) {
            id = mIDEt.getText().toString();
        }
        return id;
    }

    @Override
    public boolean isCheckAgreement() {
        if (null != mAgreementCb) {
            return mAgreementCb.isChecked();
        }
        return false;
    }


    @Override
    public void paySuccess() {
        Global.setMember(1);
        EventBus.getDefault().post(new Event(EventType.UPDATELOGINSTATE, null));
        ActivityStackManager.getInstance().finishActivity(MemberDetailActivity.class);
        MemberDetailActivity.start(this);
        finish();
    }

    @Override
    public VIPCardInfoResponse getVipCardInfo() {
        return mVIPCardInfo;
    }

}
