package com.keydom.ih_doctor.activity.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.MainActivity;
import com.keydom.ih_doctor.activity.personal.controller.PersonalInfoController;
import com.keydom.ih_doctor.activity.personal.view.PersonalInfoView;
import com.keydom.ih_doctor.bean.DeptBean;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.bean.PersonalInfoBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：个人信息页面
 * @Author：song
 * @Date：18/11/14 下午3:05
 * 修改人：xusong
 * 修改时间：18/11/14 下午3:05
 */
public class PersonalInfoActivity extends BaseControllerActivity<PersonalInfoController> implements PersonalInfoView {


    /**
     * 用户姓名
     */
    public static final int USER_NAME = 3001;
    /**
     * 用户擅长
     */
    public static final int BE_GOOD = 3002;
    /**
     * 用户简介
     */
    public static final int DEC = 3003;
    /**
     * 用户头像
     */
    public static final int USER_ICON = 3004;
    /**
     * 用户名片
     */
    public static final int USER_CARD = 3005;
    /**
     * 实名
     */
    public static final int REAL_NAME = 3006;
    private CircleImageView userIcon;
    private TextView userName, userSex, realName, certificate, hospital, dept, goodBe, dec;
    private Button finishBt;
    private PersonalInfoBean personalInfoBean;
    private TypeEnum mType;
    private ImageView certificateImg;

    /**
     * 启动
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, PersonalInfoActivity.class);
        context.startActivity(starter);
    }

    /**
     * 启动
     */
    public static void start(Context context, TypeEnum type) {
        Intent starter = new Intent(context, PersonalInfoActivity.class);
        starter.putExtra(Const.TYPE, type);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_personal_info_layout;
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("个人信息");
        mType = (TypeEnum) getIntent().getSerializableExtra(Const.TYPE);
        userIcon = findViewById(R.id.user_icon);
        certificateImg = findViewById(R.id.certificate_img);
        userName = findViewById(R.id.user_name);
        userSex = findViewById(R.id.user_sex);
        realName = findViewById(R.id.real_name);
        finishBt = findViewById(R.id.finish);
        certificate = findViewById(R.id.certificate);
        hospital = findViewById(R.id.hospital);
        dept = findViewById(R.id.dept);
        goodBe = findViewById(R.id.good_be);
        dec = findViewById(R.id.dec);
        userIcon.setOnClickListener(getController());
        userName.setOnClickListener(getController());
        userSex.setOnClickListener(getController());
        realName.setOnClickListener(getController());
        certificate.setOnClickListener(getController());
        hospital.setOnClickListener(getController());
        dept.setOnClickListener(getController());
        goodBe.setOnClickListener(getController());
        dec.setOnClickListener(getController());
        pageLoading();
        getController().getInfo();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getInfo();
            }
        });
        if (mType == TypeEnum.FIRST_FINISH_INFO) {
            setRightTxt("完成");
            setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
                @Override
                public void OnRightTextClick(View v) {
                    SharePreferenceManager.setFirstFinishInfo(false);
                    MainActivity.start(getContext(),false,true);
                }
            });

            setLeftBtnListener(new IhTitleLayout.OnLeftButtonClickListener() {
                @Override
                public void onLeftButtonClick(View v) {
                    if (SharePreferenceManager.getFirstFinishInfo()) {
                        SharePreferenceManager.setFirstFinishInfo(false);
                        MainActivity.start(getContext(),false,true);
                    } else {
                        finish();
                    }
                }
            });

//            finishBt.setVisibility(View.VISIBLE);
//            finishBt.setOnClickListener(getController());
        }
    }


    @Override
    public void getInfoSuccess(PersonalInfoBean bean) {
        personalInfoBean = bean;
        setInfo(bean);
        pageLoadingSuccess();
    }

    /**
     * 设置页面信息
     */
    private void setInfo(PersonalInfoBean bean) {
        GlideUtils.load(userIcon, Const.IMAGE_HOST + bean.getAvatar(), 0, 0, false, null);
        userName.setText(bean.getName());
        userSex.setText(CommonUtils.getSex(bean.getSex()));
        realName.setText(bean.getAutonymState() == 1 ? "已认证" : "身份认证");
        if (bean.getAutonymState() == 1) {
            realName.setEnabled(false);
        }
        certificate.setEnabled(false);
        if (bean.getQualificationsCard() != null && !"".equals(bean.getQualificationsCard())) {
            certificate.setText("");
            certificate.setHint("");
            certificateImg.setVisibility(View.VISIBLE);
            certificateImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtils.previewImage(PersonalInfoActivity.this, Const.IMAGE_HOST + bean.getQualificationsCard());
                }
            });
            Glide.with(PersonalInfoActivity.this).load(Const.IMAGE_HOST + bean.getQualificationsCard()).into(certificateImg);
        } else {
            certificateImg.setVisibility(View.GONE);
            certificate.setHint("请上传执业医师资格证");
        }
        hospital.setText(bean.getHospitalName());
        dept.setText(bean.getDeptName());
        goodBe.setText(bean.getAdept());
        dec.setText(bean.getIntro());
    }

    @Override
    public void getInfoFailed(String errMsg) {
        pageLoadingFail();
    }

    @Override
    public void updateInfoSuccess(String msg) {
        setInfo(personalInfoBean);
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_USER_INFO).build());
    }

    @Override
    public void updateInfoFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void uploadImgSuccess(String path, int type) {
        if (type == USER_CARD) {
            personalInfoBean.setQualificationsCard(path);
        } else if (type == USER_ICON) {
            personalInfoBean.setAvatar(path);
        }

    }

    @Override
    public void uploadImgFailed(String errMsg) {
        if(errMsg==null||"".equals(errMsg.trim())){
            ToastUtil.showMessage(this, errMsg);
        }else{
            ToastUtil.showMessage(this, "保存失败!");
        }

    }

    @Override
    public String getDec() {
        return dec.getText().toString().trim();
    }

    @Override
    public String getGoodBe() {
        return goodBe.getText().toString().trim();
    }

    @Override
    public String getName() {
        return userName.getText().toString().trim();
    }

    @Override
    public void setSex(int sex) {
        personalInfoBean.setSex(String.valueOf(sex));
    }

    @Override
    public void setDept(DeptBean dept) {
        personalInfoBean.setDeptName(dept.getName());
        personalInfoBean.setDeptId(dept.getId());
    }

    @Override
    public Map<String, Object> getUpdateMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("adept", personalInfoBean.getAdept());
        map.put("autonymState", personalInfoBean.getAutonymState());
        map.put("avatar", personalInfoBean.getAvatar());
//        map.put("deptId", personalInfoBean.getDeptId());
//        map.put("deptName", personalInfoBean.getDeptName());
        map.put("intro", personalInfoBean.getIntro());
//        map.put("name", personalInfoBean.getName());
        map.put("qualificationsCard", personalInfoBean.getQualificationsCard());
//        map.put("sex", personalInfoBean.getSex());
        return map;
    }

    @Override
    public PersonalInfoBean getBean() {
        return personalInfoBean;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case USER_NAME:
                    String nameStr = (String) data.getSerializableExtra(Const.DATA);
                    userName.setText(nameStr);
                    EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_USER_INFO).build());
                    break;
                case DEC:
                    String decStr = (String) data.getSerializableExtra(Const.DATA);
                    dec.setText(decStr);
                    EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_USER_INFO).build());
                    break;
                case BE_GOOD:
                    String beGoodStr = (String) data.getSerializableExtra(Const.DATA);
                    goodBe.setText(beGoodStr);
                    EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.UPDATE_USER_INFO).build());
                    break;
                case USER_ICON:
                    List<LocalMedia> iconselectList = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < iconselectList.size(); i++) {
                        getController().uploadFile(iconselectList.get(i).getPath(), USER_ICON);
                    }
                    break;
                case USER_CARD:
                    List<LocalMedia> cardselectList = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < cardselectList.size(); i++) {
                        getController().uploadFile(cardselectList.get(i).getPath(), USER_CARD);
                    }
                    break;
                case REAL_NAME:
                    getController().getInfo();
                    break;
                default:
            }
        }

    }

}
