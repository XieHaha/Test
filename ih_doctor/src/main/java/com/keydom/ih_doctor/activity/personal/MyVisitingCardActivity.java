package com.keydom.ih_doctor.activity.personal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ShareUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.personal.controller.MyVisitingCardController;
import com.keydom.ih_doctor.activity.personal.view.MyVisitingCardView;
import com.keydom.ih_doctor.bean.UserCard;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.utils.ToastUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：我的名片页面
 * @Author：song
 * @Date：18/11/14 下午3:05
 * 修改人：xusong
 * 修改时间：18/11/14 下午3:05
 */
public class MyVisitingCardActivity extends BaseControllerActivity<MyVisitingCardController> implements MyVisitingCardView {


    private CircleImageView userIcon;
    private ImageView qrCode;
    private TextView userName, userPosition, userHospital, userDepartment;

    /**
     * 启动
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, MyVisitingCardActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_visiting_card_layout;
    }

    @Override
    public void getCardSuccess(UserCard card) {
        pageLoadingSuccess();
        GlideUtils.load(userIcon, Const.IMAGE_HOST + card.getAvatar(), 0, 0, false, null);
        userName.setText(card.getName());
        userDepartment.setText(card.getDepartment());
        userHospital.setText(card.getHospital());
        userPosition.setText(card.getJobTitle());
        Bitmap mBitmap = CodeUtils.createImage(card.getQrcode(), 400, 400, null);
        qrCode.setImageBitmap(mBitmap);
    }

    @Override
    public void getCardFailed(String errMsg) {
        pageLoadingFail();
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("我的名片");
        setRightImg(getResources().getDrawable(R.mipmap.share));
        setRightImgListener(new IhTitleLayout.OnRightImgClickListener() {
            @Override
            public void OnRightImgClick(View v) {
                ShareUtils.showShareUtils(MyVisitingCardActivity.this, new ShareUtils.IOnShareCallBack() {
                    @Override
                    public void onShareSelect(int type) {
                        ToastUtil.shortToast(getContext(), "分享");
                    }
                });

            }
        });
        userIcon = (CircleImageView) this.findViewById(R.id.user_icon);
        qrCode = (ImageView) this.findViewById(R.id.qr_iv);
        userName = (TextView) this.findViewById(R.id.user_name);
        userDepartment = (TextView) this.findViewById(R.id.user_department);
        userPosition = (TextView) this.findViewById(R.id.user_position);
        userHospital = (TextView) this.findViewById(R.id.uesr_hospital);
        pageLoading();
        getController().getCard();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getCard();
            }
        });
    }
}
