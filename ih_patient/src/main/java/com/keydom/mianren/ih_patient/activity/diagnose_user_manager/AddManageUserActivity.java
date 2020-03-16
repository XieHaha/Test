package com.keydom.mianren.ih_patient.activity.diagnose_user_manager;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * created date: 2018/12/13 on 15:02
 * des:添加就诊人页面
 */
public class AddManageUserActivity extends BaseControllerActivity<AddManageUserController> implements AddManageUserView {
    public static final String TYPE = "type";
    public static final String MANAGER_USER = "manager_user";
    //新增就诊人类型标识
    public static final int ADD = 1;
    //修改就诊人类型标识
    public static final int UPDATE = 2;
    private int mType;
    private EditText mName;
    private EditText mIdCard;
    private TextView mSex;
    private TextView mBirth;
    private EditText mPhone;
    private LinearLayout mSexChoose;
    private LinearLayout mBirthChoose;
    private LinearLayout mAreaChoose;
    private TextView mArea;
    private EditText mAddress;
    private String provinceName, cityName, areaName;
    private ManagerUserBean mManager;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_add_manage_user;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getView();
        mType = getIntent().getIntExtra(TYPE, 0);
        setTitle(mType == ADD ? "新增就诊人信息" : "修改就诊人信息");
        mManager = mType == ADD ? new ManagerUserBean() : (ManagerUserBean) getIntent().getSerializableExtra(MANAGER_USER);
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
                if (strArr.length >= 1)
                    provinceName = strArr[0];
                if (strArr.length >= 2)
                    cityName = strArr[1];
                if (strArr.length >= 3)
                    areaName = strArr[2];
            }
        }
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
        findViewById(R.id.next_step).setOnClickListener(getController());
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
        mBirth.setTextColor(getResources().getColor(R.color.color_333333));
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
    public void saveRegion(List<PackageData.ProvinceBean> data, int options1, int option2, int options3) {
        if (data.get(options1).getCity().size() == 0) {
            mArea.setText(data.get(options1).getName());
            provinceName = data.get(options1).getName();
            cityName = "";
            areaName = "";
        } else if (data.get(options1).getCity().get(option2).getArea().size() == 0) {
            mArea.setText(data.get(options1).getName() + "-" + data.get(options1).getCity().get(option2).getName());
            provinceName = data.get(options1).getName();
            cityName = data.get(options1).getCity().get(option2).getName();
            areaName = "";

        } else {
            mArea.setText(data.get(options1).getName() + "-" + data.get(options1).getCity().get(option2).getName() + "-" + data.get(options1).getCity().get(option2).getArea().get(options3).getName());
            provinceName = data.get(options1).getName();
            cityName = data.get(options1).getCity().get(option2).getName();
            areaName = data.get(options1).getCity().get(option2).getArea().get(options3).getName();
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

}
