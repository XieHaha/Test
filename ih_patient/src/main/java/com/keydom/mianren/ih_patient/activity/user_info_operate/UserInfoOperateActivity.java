package com.keydom.mianren.ih_patient.activity.user_info_operate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.index_main.MainActivity;
import com.keydom.mianren.ih_patient.activity.user_info_operate.controller.UserInfoOperateController;
import com.keydom.mianren.ih_patient.activity.user_info_operate.view.UserInfoOperateView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.PackageData;
import com.keydom.mianren.ih_patient.bean.UserInfo;
import com.keydom.mianren.ih_patient.bean.event.CertificateSuccess;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.utils.LocalizationUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoOperateActivity extends BaseControllerActivity<UserInfoOperateController> implements UserInfoOperateView {
    public static final String EDITTYPE = "edit_type";
    public static final String READTYPE = "read_type";
    private CircleImageView user_head_img;
    private TextView user_name_tv, user_sex_tv, user_real_name_status_tv, user_region_tv, user_phone_tv, user_country_tv, user_nation_tv, user_id_card_pic_tv;
    private String provinceName, cityName, areaName;
    private String sex = "0", nation, country;
    private String type = "";

    public static void start(Context context, String type) {
        Intent intent = new Intent(context, UserInfoOperateActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_user_info_operate_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("个人信息");
        type = getIntent().getStringExtra("type");
        getTitleLayout().initViewsVisible(true, true, false);
        getTitleLayout().setOnLeftButtonClickListener(new IhTitleLayout.OnLeftButtonClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                if (EDITTYPE.equals(type)) {
                    EventBus.getDefault().post(new Event(EventType.UPDATEUSERINFO, null));
                    MainActivity.start(getContext(),false);
                } else
                    finish();


            }
        });
        user_head_img = this.findViewById(R.id.user_head_img);
        if (EDITTYPE.equals(type))
            user_head_img.setOnClickListener(getController());

        user_name_tv = this.findViewById(R.id.user_name_tv);
        if (EDITTYPE.equals(type))
            user_name_tv.setOnClickListener(getController());

        user_sex_tv = this.findViewById(R.id.user_sex_tv);
        if (EDITTYPE.equals(type))
            user_sex_tv.setOnClickListener(getController());

        user_real_name_status_tv = this.findViewById(R.id.user_real_name_status_tv);
        if (EDITTYPE.equals(type))
            user_real_name_status_tv.setOnClickListener(getController());

        user_region_tv = this.findViewById(R.id.user_region_tv);
        if (EDITTYPE.equals(type))
            user_region_tv.setOnClickListener(getController());

        user_phone_tv = this.findViewById(R.id.user_phone_tv);
        if (EDITTYPE.equals(type))
            user_phone_tv.setOnClickListener(getController());

        user_country_tv = this.findViewById(R.id.user_country_tv);
        if (EDITTYPE.equals(type))
            user_country_tv.setOnClickListener(getController());

        user_nation_tv = this.findViewById(R.id.user_nation_tv);
        if (EDITTYPE.equals(type))
            user_nation_tv.setOnClickListener(getController());

        user_id_card_pic_tv = this.findViewById(R.id.user_id_card_pic_tv);
        if (EDITTYPE.equals(type))
            user_id_card_pic_tv.setOnClickListener(getController());
        EventBus.getDefault().register(this);
        getController().initUserData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getIdCardPicUrl(Event event) {
        if (event.getType() == EventType.SENDPICURL) {
            List<String> urlList = (List<String>) event.getData();
            Map<String, Object> map = new HashMap<>();
            map.put("id", Global.getUserId());
            map.put("cardImage", urlList.get(0));
            map.put("cardImageBack", urlList.get(1));
            getController().upLoadInfo(map);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void idCardCertificateSuccess(Event event) {
        if (event.getType() == EventType.IDCARDCERTIFICATESUCCESS) {
            user_real_name_status_tv.setText("已认证");
            user_real_name_status_tv.setClickable(false);
            reloadData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void phoneCertificateSuccess(Event event) {
        if (event.getType() == EventType.PHONECERTIFICATESUCCESS) {
            String phoneNum = (String) event.getData();
            user_phone_tv.setText(phoneNum);
            Map<String, Object> map = new HashMap<>();
            map.put("id", Global.getUserId());
            map.put("phoneNumber", phoneNum);
            getController().upLoadInfo(map);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    getController().upLoadPic(selectList.get(0).getPath());
                    break;
                default:
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getRegion(PackageData.ProvinceBean provinceBean, int position_city, int position_area) {
        user_region_tv.setText(provinceBean.getName() + "-" + provinceBean.getCity().get(position_city).getName() + "-" + provinceBean.getCity().get(position_city).getArea().get(position_area).getName());
    }

    @Override
    public void getRegion(PackageData.ProvinceBean provinceBean, int position_city) {
        user_region_tv.setText(provinceBean.getName() + "-" + provinceBean.getCity().get(position_city).getName());
    }

    @Override
    public void getRegion(PackageData.ProvinceBean provinceBean) {
        user_region_tv.setText(provinceBean.getName());
    }

    @Override
    public void setSex(String sexStr) {
        user_sex_tv.setText(sexStr);
        if (sexStr.equals("男"))
            sex = "0";
        else
            sex = "1";
    }

    @Override
    public void setNation(String nationStr) {
        user_nation_tv.setText(nationStr);
        nation = nationStr;
    }

    @Override
    public void setName(String nameStr) {
        user_name_tv.setText(nameStr);
    }

    @Override
    public void setCountry(String countryStr) {
        user_country_tv.setText(countryStr);
        country = countryStr;
    }

    @Override
    public void initUserData(UserInfo data) {
        if (data == null) {
            Logger.e("userData为空");
        } else {
            if ("".equals(data.getUserImage()) || data.getUserImage() == null)
                user_head_img.setBackgroundColor(getResources().getColor(R.color.color_f9f9f9));
            else
                Glide.with(getContext()).load(Const.IMAGE_HOST + data.getUserImage()).into(user_head_img);
            user_name_tv.setText("".equals(data.getUserName()) || data.getUserName() == null ? "请输入姓名" : data.getUserName());
            if (data.getSex() == null || "".equals(data.getSex())) {
                user_sex_tv.setText("请选择性别");
            } else {
                user_sex_tv.setText(data.getSex().equals("0") ? "男" : "女");
                if (data.getSex().equals("0"))
                    sex = "0";
                else
                    sex = "1";
            }
            if (data.getProvinceName() == null || "".equals(data.getProvinceName())) {
                user_region_tv.setText("请选择地区");
            } else if (data.getCityName() == null || "".equals(data.getCityName())) {
                user_region_tv.setText(data.getProvinceName());
                provinceName = data.getProvinceName();
                cityName = "";
                areaName = "";
            } else if (data.getCountryName() == null || "".equals(data.getCountryName())) {
                user_region_tv.setText(data.getProvinceName() + "-" + data.getCityName());
                provinceName = data.getProvinceName();
                cityName = data.getCityName();
                areaName = "";
            } else {
                user_region_tv.setText(data.getProvinceName() + "-" + data.getCityName() + "-" + data.getCountyName());
                provinceName = data.getProvinceName();
                cityName = data.getCityName();
                areaName = data.getCountyName();
            }
            user_real_name_status_tv.setText(data.isCertification() ? "已认证" : "未认证");
            if (data.isCertification()) {
                user_real_name_status_tv.setClickable(false);
            } else {
                user_real_name_status_tv.setClickable(true);
            }
            user_phone_tv.setText(data.getPhoneNumber() == null || "".equals(data.getPhoneNumber()) ? "点击绑定手机号" : data.getPhoneNumber());
            if (data.getCountryName() != null && !"".equals(data.getCountryName())) {
                user_country_tv.setText(data.getCountryName());
                country = data.getCountryName();
            }
            if (data.getNationName() != null && !"".equals(data.getNationName())) {
                user_nation_tv.setText(data.getNationName());
                nation = data.getNationName();
            }
            user_id_card_pic_tv.setText(data.getCardImage() == null || "".equals(data.getCardImage()) ? "未上传" : "已上传");
            if (data.getCardImage() == null || "".equals(data.getCardImage())) {
                user_id_card_pic_tv.setClickable(true);
            } else {
                user_id_card_pic_tv.setClickable(false);
            }
            App.userInfo = data;
            LocalizationUtils.fileSave2Local(getContext(), data, "userInfo");
            Logger.e("用户信息写入成功");

        }

    }

    @Override
    public void reloadData() {
        getController().initUserData();
    }

    @Override
    public void uploadImgSuccess(String urlData) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", Global.getUserId());
        map.put("userImage", urlData);
        getController().upLoadInfo(map);
        Glide.with(getContext()).load(Const.IMAGE_HOST + urlData).into(user_head_img);
    }

    @Override
    public void uploadImgFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "头像上传失败：" + errMsg);
    }

    @Override
    public String getProvinceName() {
        return provinceName;
    }

    @Override
    public String getCityName() {
        return cityName;
    }

    @Override
    public String getAreaName() {
        return areaName;
    }

    @Override
    public String getSex() {
        return sex;
    }

    @Override
    public String getNation() {
        return nation;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (EDITTYPE.equals(type)) {
                EventBus.getDefault().post(new Event(EventType.UPDATEUSERINFO, null));
                MainActivity.start(getContext(),false);
            } else
                finish();
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 认证成功
     *
     * @param success 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCertificateSuccess(CertificateSuccess success) {
        user_real_name_status_tv.setText("已认证");
        user_real_name_status_tv.setClickable(false);
        reloadData();
    }
}
