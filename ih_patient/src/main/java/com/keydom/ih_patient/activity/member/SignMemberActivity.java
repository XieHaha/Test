package com.keydom.ih_patient.activity.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.member.controller.SignMemberController;
import com.keydom.ih_patient.activity.member.view.SignMemberView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

public class SignMemberActivity extends BaseControllerActivity<SignMemberController> implements SignMemberView {


    EditText mNameEt;
    EditText mIDEt;
    TextView mToPayTv;



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
        setWhiteBar();

        mNameEt = findViewById(R.id.sign_member_name_edt);
        mIDEt = findViewById(R.id.sign_member_id_num_edt);
        mToPayTv = findViewById(R.id.pay_commit_tv);

        mToPayTv.setOnClickListener(getController());
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
    public void paySuccess() {

    }

    @Override
    public void addCardForMobileSuccess() {
        EventBus.getDefault().post(new Event(EventType.UPDATELOGINSTATE,null));
        finish();
    }
}
