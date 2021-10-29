package com.keydom.mianren.ih_doctor.activity.electronic_signature;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.controller.SiChuanCAController;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.view.SiChuanCAView;

import org.jetbrains.annotations.Nullable;


/**
 * @link Author: song
 * <p>
 * Create: 19/3/6 下午3:48
 * <p>
 * Changes (from 19/3/6)
 * <p>
 * 19/3/6 : Create InspectionDetailActivity.java (song);
 */
public class SiChuanCAActivity extends BaseControllerActivity<SiChuanCAController> implements SiChuanCAView {
    LinearLayout signTipsLayout;
    TextView signTipTv;
    ImageView signDeleteIv;
    TextView signUserNameTv;
    TextView signUserIdTv;
    ImageView signUserSignatureIv;
    TextView signHintTv;
    TextView signNextTv;
    LinearLayout signRootLayout;
    ImageView signSelectIv;
    TextView signProtocolTv;
    public static final int RESULT_CODE = 100;
    private boolean isSign = false;

    /**
     * 启动电子签名设置页面
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, SiChuanCAActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_sichuan_ca_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("电子签章");
        getController().isSign();
        getController().getSign();

        signTipsLayout = findViewById(R.id.sign_tips_layout);
        signTipTv = findViewById(R.id.sign_tip_tv);
        signDeleteIv = findViewById(R.id.sign_delete_iv);
        signUserNameTv = findViewById(R.id.sign_user_name_tv);
        signUserIdTv = findViewById(R.id.sign_user_id_tv);
        signUserSignatureIv = findViewById(R.id.sign_user_signature_iv);
        signHintTv = findViewById(R.id.sign_hint_tv);
        signNextTv = findViewById(R.id.sign_next_tv);
        signRootLayout = findViewById(R.id.sign_root_layout);
        signSelectIv = findViewById(R.id.sign_select_iv);
        signProtocolTv = findViewById(R.id.sign_protocol_tv);

        signUserNameTv.setText(MyApplication.userInfo.getName());
        signUserIdTv.setText(SharePreferenceManager.getIdCard());
        signNextTv.setOnClickListener(getController());
        signSelectIv.setOnClickListener(getController());
        signProtocolTv.setOnClickListener(getController());
    }

    @Override
    public boolean isSign() {
        return isSign;
    }

    @Override
    public void setSelect() {
        signSelectIv.setSelected(!signSelectIv.isSelected());
    }

    @Override
    public boolean isSelect() {
        return signSelectIv.isSelected();
    }

    @Override
    public void requestUserSignSuccess(String result) {
        if ("false".equals(result)) {
            isSign = false;
            signTipsLayout.setVisibility(View.VISIBLE);
            signTipTv.setText("电子签章未设置口令，请设置口令");
            signNextTv.setSelected(false);
            signNextTv.setText(R.string.txt_sign_register);
        } else {
            isSign = true;
            signNextTv.setSelected(true);
            signNextTv.setText(R.string.txt_sign_reset);
            signTipsLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void requestUserSignFailed(String result) {
        ToastUtil.showMessage(this, result);
    }

    @Override
    public void getSignSuccess(String sign) {
        if (TextUtils.isEmpty(sign)) {
            signUserSignatureIv.setImageResource(R.mipmap.icon_sign_file);
            signHintTv.setText("电子签章未添加，请联系管理员上传！");
            signHintTv.setTextColor(R.color.color_fa6f14);
            signRootLayout.setVisibility(View.INVISIBLE);
        } else {
            signRootLayout.setVisibility(View.VISIBLE);
            signHintTv.setText("如需修改电子签章请联系管理员");
            signHintTv.setTextColor(R.color.tab_nol_color);
            GlideUtils.load(signUserSignatureIv, Const.IMAGE_HOST + sign, -1, -1, false, null);
        }
    }

    @Override
    public void getSignFailed(String msg) {
        ToastUtil.showMessage(this, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == RESULT_CODE) {
            getController().isSign();
            getController().getSign();
        }
    }
}
