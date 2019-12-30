package com.keydom.ih_patient.activity.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.controller.SignMemberController;
import com.keydom.ih_patient.activity.member.view.SignMemberView;

import org.jetbrains.annotations.Nullable;

public class SignMemberActivity extends BaseControllerActivity<SignMemberController> implements SignMemberView {


    EditText mNameEt;
    EditText mIDEt;
    TextView mAliPayTv;
    TextView mWechatPayTv;
    ImageView mAliPayIv;
    ImageView mWechatPayIv;
    TextView mToPayTv;
    RelativeLayout mWechatPayRootRl;
    RelativeLayout mAliPayRootRl;

    //支付方式 1微信 2支付宝
    private int payType = 2;

    /**
     * 启动
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, SignMemberActivity.class);
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
        getTitleLayout().initViewsVisible(true,true,false);
        setTitle("仁医金卡签约");
        mNameEt = findViewById(R.id.sign_member_name_edt);
        mIDEt = findViewById(R.id.sign_member_id_num_edt);
        mWechatPayRootRl = findViewById(R.id.sign_member_wechat_pay_root_rl);
        mAliPayRootRl = findViewById(R.id.sign_member_ali_pay_root_rl);
        mAliPayTv = findViewById(R.id.ali_pay_tv);
        mWechatPayTv = findViewById(R.id.wechat_pay_tv);
        mAliPayIv = findViewById(R.id.ali_pay_selected_img);
        mWechatPayIv = findViewById(R.id.wechat_pay_selected_img);
        mToPayTv = findViewById(R.id.pay_commit_tv);

        mWechatPayRootRl.setOnClickListener(getController());
        mAliPayRootRl.setOnClickListener(getController());
        mToPayTv.setOnClickListener(getController());
    }

    @Override
    public void showAliPaySelected() {
        payType = 2;
        mAliPayIv.setImageResource(R.mipmap.pay_selected_icon);
        mWechatPayIv.setImageResource(R.mipmap.pay_unselected_icon);
        mAliPayTv.setTextColor(getResources().getColor(R.color.pay_selected));
        mWechatPayTv.setTextColor(getResources().getColor(R.color.pay_unselected));
    }

    @Override
    public void showWechatPaySelected() {
        payType = 1;
        mAliPayIv.setImageResource(R.mipmap.pay_unselected_icon);
        mWechatPayIv.setImageResource(R.mipmap.pay_selected_icon);
        mAliPayTv.setTextColor(getResources().getColor(R.color.pay_unselected));
        mWechatPayTv.setTextColor(getResources().getColor(R.color.pay_selected));
    }

    @Override
    public String getName() {
        String name = "";
        if(null != mNameEt){
            name = mNameEt.getText().toString();
        }
        return name;
    }

    @Override
    public String getID() {
        String id = "";
        if(null != mIDEt){
            id = mIDEt.getText().toString();
        }
        return id;
    }

    @Override
    public int getPayType() {
        return payType;
    }

    @Override
    public void paySuccess() {

    }
}
