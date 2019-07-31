package com.keydom.ih_doctor.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.controller.CommonInputController;
import com.keydom.ih_doctor.activity.personal.PersonalInfoActivity;
import com.keydom.ih_doctor.activity.view.CommonInputView;
import com.keydom.ih_doctor.bean.PersonalInfoBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Name：com.kentra.yxyz.activity
 * @Description：通用编辑页面
 * @Author：song
 * @Date：18/11/2 下午5:47
 * 修改人：xusong
 * 修改时间：18/11/2 下午5:47
 */
public class CommonInputActivity extends BaseControllerActivity<CommonInputController> implements CommonInputView {

    /**
     * 文本编辑页面标题
     */
    private String mTitle = "";
    /**
     * 文本编辑页面内容
     */
    private String mContent = "";
    /**
     * 文本编辑限制的最大长度
     */
    private int limite = 0;
    /**
     * 文本编辑限制的最小长度
     */
    private int leastSize = 0;
    /**
     * 判断是否在当前页面调用接口更新，目前只有个人信息在本页面进行更新，其他的根据需要自行修改。
     */
    private int mType;
    private EditText inputEt;
    private TextView limiteSizeTv;
    /**
     * 个人资料对象
     */
    private PersonalInfoBean personalInfoBean;


    /**
     * @param context
     * @param requestCode
     * @param content
     * @param title
     */
    public static void start(Context context, int requestCode, String title, String content, int size) {
        Intent starter = new Intent(context, CommonInputActivity.class);
        starter.putExtra(Const.DATA, content);
        starter.putExtra(Const.TITLE, title);
        starter.putExtra(Const.TYPE, requestCode);
        starter.putExtra(Const.LIMITE, size);
        ((Activity) context).startActivityForResult(starter, requestCode);
    }

    /**
     * 启动通用编辑页面
     *
     * @param context     上下文
     * @param requestCode 请求code
     * @param title       编辑标题
     * @param content     编辑初始内容
     * @param size        编辑最大长度
     * @param leastSize   编辑最小长度
     */
    public static void start(Context context, int requestCode, String title, String content, int size, int leastSize) {
        Intent starter = new Intent(context, CommonInputActivity.class);
        starter.putExtra(Const.DATA, content);
        starter.putExtra(Const.TITLE, title);
        starter.putExtra(Const.TYPE, requestCode);
        starter.putExtra(Const.LIMITE, size);
        starter.putExtra(Const.LEAST_SIZE, leastSize);
        ((Activity) context).startActivityForResult(starter, requestCode);
    }

    /**
     * 更新个人信息
     *
     * @param context     上下文
     * @param requestCode 请求code
     * @param title       编辑标题
     * @param content     编辑初始内容
     * @param bean        个人信息实体
     * @param size        最大编辑长度
     */
    public static void startUpdate(Context context, int requestCode, String title, String content, PersonalInfoBean bean, int size) {
        Intent starter = new Intent(context, CommonInputActivity.class);
        starter.putExtra(Const.DATA, content);
        starter.putExtra(Const.TITLE, title);
        starter.putExtra(Const.TYPE, requestCode);
        starter.putExtra(Const.PERSONAL_INFO, bean);
        starter.putExtra(Const.LIMITE, size);
        ((Activity) context).startActivityForResult(starter, requestCode);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_common_input;
    }


    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mTitle = getIntent().getStringExtra(Const.TITLE);
        mContent = getIntent().getStringExtra(Const.DATA);
        limite = getIntent().getIntExtra(Const.LIMITE, 0);
        leastSize = getIntent().getIntExtra(Const.LEAST_SIZE, 0);
        personalInfoBean = (PersonalInfoBean) getIntent().getSerializableExtra(Const.PERSONAL_INFO);
        mType = getIntent().getIntExtra(Const.TYPE, 0);
        setRightTxt("保存");
        setTitle(mTitle);
        inputEt = findViewById(R.id.sub_item_entrust_et);
        limiteSizeTv = findViewById(R.id.limite_size);
        inputEt.setText(mContent);
        inputEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(limite)});
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @SingleClick(1000)
            @Override
            public void OnRightTextClick(View v) {
                if (mType == PersonalInfoActivity.USER_NAME || mType == PersonalInfoActivity.BE_GOOD || mType == PersonalInfoActivity.DEC) {
                    getController().updateInfo();
                } else {
                    String str = CommonUtils.filterEmoji(inputEt.getText().toString());
                    if (leastSize != 0 && str.length() < leastSize) {
                        ToastUtil.shortToast(CommonInputActivity.this, "请至少填写" + leastSize + "个字");
                        return;
                    }
                    save();
                }
            }
        });
        limiteSizeTv.setText(inputEt.getText().length() + "/" + limite);
        inputEt.addTextChangedListener(new TextWatcher() {
            int mStart = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStart = start;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (CommonUtils.containsEmoji(s.toString())) {
                    Toast.makeText(getContext(), "不支持输入表情", Toast.LENGTH_SHORT).show();
                    if (mStart >= 0)
                        inputEt.setText(inputEt.getText().subSequence(0, mStart));
                    inputEt.setSelection(inputEt.getText().length());
                } else {
                    limiteSizeTv.setText(inputEt.getText().length() + "/" + limite);
                }

            }
        });

    }

    @Override
    public void updateSuccess(String msg) {
        save();
    }

    @Override
    public void updateFailed(String errMsg) {
        ToastUtil.shortToast(this, errMsg);
    }

    @Override
    public Map<String, Object> getUpdatePersonalInfoMap() {
        switch (mType) {
            case PersonalInfoActivity.USER_NAME:
                personalInfoBean.setName(CommonUtils.filterEmoji(inputEt.getText().toString()));
                break;
            case PersonalInfoActivity.BE_GOOD:
                personalInfoBean.setAdept(CommonUtils.filterEmoji(inputEt.getText().toString()));
                break;
            case PersonalInfoActivity.DEC:
                personalInfoBean.setIntro(CommonUtils.filterEmoji(inputEt.getText().toString()));
                break;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("adept", personalInfoBean.getAdept());
        map.put("autonymState", personalInfoBean.getAutonymState());
        map.put("avatar", personalInfoBean.getAvatar());
        map.put("deptId", personalInfoBean.getDeptId());
        map.put("deptName", personalInfoBean.getDeptName());
        map.put("intro", personalInfoBean.getIntro());
        map.put("name", personalInfoBean.getName());
        map.put("qualificationsCard", personalInfoBean.getQualificationsCard());
        map.put("sex", personalInfoBean.getSex());
        return map;
    }

    /**
     * 直接返回当前编辑的文本到上一个页面
     */
    public void save() {
        Intent intent = new Intent();
        intent.putExtra(Const.DATA, CommonUtils.filterEmoji(inputEt.getText().toString()));
        setResult(RESULT_OK, intent);
        finish();
    }
}
