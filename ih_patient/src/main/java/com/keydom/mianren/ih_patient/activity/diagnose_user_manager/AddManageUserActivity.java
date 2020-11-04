package com.keydom.mianren.ih_patient.activity.diagnose_user_manager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.controller.AddManageUserController;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.view.AddManageUserView;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.bean.PackageData;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.utils.CommUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * created date: 2018/12/13 on 15:02
 * des:添加就诊人页面
 *
 * @author 顿顿
 */
public class AddManageUserActivity extends BaseControllerActivity<AddManageUserController> implements AddManageUserView {
    public static final String ELECTRONIC_CARD = "electronic_card";
    public static final String TYPE = "type";
    public static final String MANAGER_USER = "manager_user";
    /**
     * 新增就诊人类型标识
     */
    public static final int ADD = 1;
    /**
     * 修改就诊人类型标识
     */
    public static final int UPDATE = 2;
    private int mType;
    private boolean electronic;
    private EditText mName;
    private EditText mIdCard;
    private TextView mSex;
    private TextView mBirth;
    private EditText mPhone;
    private LinearLayout mSexChoose;
    private LinearLayout mBirthChoose;
    private LinearLayout mAreaChoose;
    private LinearLayout addressDetailLayout;
    private TextView mArea;
    private TextView nextTv;
    private EditText mAddress;
    private String provinceName, cityName, areaName;
    private String provinceCode, cityCode, areaCode;
    private ManagerUserBean mManager;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_add_manage_user;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(TYPE, 0);
        electronic = getIntent().getBooleanExtra(ELECTRONIC_CARD, false);
        setTitle(mType == ADD ? "新增就诊人信息" : "修改就诊人信息");
        getView();
        mManager = mType == ADD ? new ManagerUserBean() :
                (ManagerUserBean) getIntent().getSerializableExtra(MANAGER_USER);
        mManager.setManagerState(mType);
        if (mType == UPDATE) {
            mName.setText(mManager.getName());
            mSex.setText("0".equals(mManager.getSex()) ? "男" : "女");
            mManager.setSexIsChoose(true);
            mSex.setTextColor(this.getResources().getColor(R.color.color_333333));
            mBirth.setTextColor(this.getResources().getColor(R.color.color_333333));
            mArea.setTextColor(this.getResources().getColor(R.color.color_333333));
            mArea.setText(mManager.getArea());
            mBirth.setText(mManager.getBirthday());
            mPhone.setText(mManager.getPhone());
            mIdCard.setText(mManager.getCardId());
            mAddress.setText(mManager.getAddress());
            if (mManager.getArea() != null) {
                String[] strArr = mManager.getArea().split("-");
                if (strArr.length >= 1) {
                    provinceName = strArr[0];
                }
                if (strArr.length >= 2) {
                    cityName = strArr[1];
                }
                if (strArr.length >= 3) {
                    areaName = strArr[2];
                }
            }
        }

        mIdCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() == 18) {
                    Map<String, String> map = CommUtil.getBirAgeSex(s.toString());
                    if (map != null) {
                        mBirth.setText(map.get("birthday"));
                        mManager.setAge(map.get("age"));
                        mManager.setBirthday(map.get("birthday"));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 查找控件
     */
    private void getView() {
        mName = findViewById(R.id.name);
        mSexChoose = findViewById(R.id.sex_choose);
        mSexChoose.setOnClickListener(getController());
        mSex = findViewById(R.id.sex);
        mBirthChoose = findViewById(R.id.birth_choose);
        mBirthChoose.setOnClickListener(getController());
        mBirth = findViewById(R.id.birth);
        mPhone = findViewById(R.id.phone);
        mIdCard = findViewById(R.id.id_card);
        mAreaChoose = findViewById(R.id.area_choose);
        mAreaChoose.setOnClickListener(getController());
        mArea = findViewById(R.id.area);
        mAddress = findViewById(R.id.address);
        nextTv = findViewById(R.id.next_step);
        nextTv.setOnClickListener(getController());
        addressDetailLayout = findViewById(R.id.address_detail_layout);
        if (mType == ADD) {
            addressDetailLayout.setVisibility(View.GONE);
        } else {
            addressDetailLayout.setVisibility(View.VISIBLE);
        }
        if (electronic) {
            nextTv.setText(R.string.save);
        } else {
            nextTv.setText(R.string.txt_next);
        }
    }

    @Override
    public void setSex(String sex) {
        mSex.setTextColor(getResources().getColor(R.color.color_333333));
        mSex.setText(sex);
        mManager.setSex(sex.equals("男") ? "0" : "1");
        mManager.setSexIsChoose(true);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void setBirth(Date birth) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(birth);
        mBirth.setText(dateString);
        mManager.setBirthday(dateString);
    }

    @Override
    public ManagerUserBean getManager() {
        mManager.setRegisterUserId(Global.getUserId());
        mManager.setName(mName.getText().toString());
        mManager.setCardId(mIdCard.getText().toString());
        mManager.setPhone(mPhone.getText().toString());
        mManager.setAddress(mAddress.getText().toString());
        mManager.setArea(mArea.getText().toString());
        return mManager;
    }

    @Override
    public int getStatus() {
        return mType;
    }

    @Override
    public boolean isElectronic() {
        return electronic;
    }

    @Override
    public void addOrEditSuccess(ManagerUserBean manager) {
        EventBus.getDefault().post(manager);
        finish();
    }

    @Override
    public void saveRegion(List<PackageData.ProvinceBean> data, int options1, int option2,
                           int options3) {
        if (data.get(options1).getCity().size() == 0) {
            mArea.setText(data.get(options1).getName());
            provinceName = data.get(options1).getName();
            provinceCode = data.get(options1).getCode();
            cityName = "";
            areaName = "";
        } else if (data.get(options1).getCity().get(option2).getArea().size() == 0) {
            mArea.setText(data.get(options1).getName() + "-" + data.get(options1).getCity().get(option2).getName());
            provinceName = data.get(options1).getName();
            provinceCode = data.get(options1).getCode();
            cityName = data.get(options1).getCity().get(option2).getName();
            cityCode = data.get(options1).getCity().get(option2).getCode();
            areaName = "";

        } else {
            mArea.setText(data.get(options1).getName() + "-" + data.get(options1).getCity().get(option2).getName() + "-" + data.get(options1).getCity().get(option2).getArea().get(options3).getName());
            provinceName = data.get(options1).getName();
            provinceCode = data.get(options1).getCode();
            cityName = data.get(options1).getCity().get(option2).getName();
            cityCode = data.get(options1).getCity().get(option2).getCode();
            areaName = data.get(options1).getCity().get(option2).getArea().get(options3).getName();
            areaCode = data.get(options1).getCity().get(option2).getArea().get(options3).getCode();
        }
        mArea.setTextColor(this.getResources().getColor(R.color.color_333333));
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
    public String getProvinceCode() {
        return provinceCode;
    }

    @Override
    public String getCityCode() {
        return cityCode;
    }

    @Override
    public String getAreaCode() {
        return areaCode;
    }
}
