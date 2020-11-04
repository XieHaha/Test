package com.keydom.mianren.ih_doctor.activity.patient_main_suit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.patient_main_suit.controller.PatientMainSuitController;
import com.keydom.mianren.ih_doctor.activity.patient_main_suit.view.PatientMainSuitView;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zhy.view.flowlayout.TagView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/8 on 16:53
 * des:患者主诉页面
 */
public class PatientMainSuitActivity extends BaseControllerActivity<PatientMainSuitController> implements PatientMainSuitView, View.OnClickListener {

    /**
     * 选择患者主诉
     */
    public static final int SELECT_PATIENT_MAIN_DEC = 900;
    private EditText mEdit;
    private TagFlowLayout mFlowView;
    /**
     * 主诉内容
     */
    private String content;

    /**
     * 启动
     */
    public static void start(Context context, String content) {
        Intent starter = new Intent(context, PatientMainSuitActivity.class);
        starter.putExtra(Const.DATA, content);
        ((Activity) context).startActivityForResult(starter, SELECT_PATIENT_MAIN_DEC);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_patient_main_suit;
    }

    /**
     * 查找控件
     */
    private void getView() {
        mEdit = findViewById(R.id.main_suit_et);
        mFlowView = findViewById(R.id.flow_layout);
        findViewById(R.id.time_0).setOnClickListener(this);
        findViewById(R.id.time_1).setOnClickListener(this);
        findViewById(R.id.time_2).setOnClickListener(this);
        findViewById(R.id.time_3).setOnClickListener(this);
        findViewById(R.id.time_4).setOnClickListener(this);
        findViewById(R.id.time_5).setOnClickListener(this);
        findViewById(R.id.time_6).setOnClickListener(this);
        findViewById(R.id.time_7).setOnClickListener(this);
        findViewById(R.id.time_8).setOnClickListener(this);
        findViewById(R.id.time_9).setOnClickListener(this);
        findViewById(R.id.time_morning).setOnClickListener(this);
        findViewById(R.id.time_noon).setOnClickListener(this);
        findViewById(R.id.time_night).setOnClickListener(this);
        findViewById(R.id.time_year).setOnClickListener(this);
        findViewById(R.id.time_month).setOnClickListener(this);
        findViewById(R.id.time_week).setOnClickListener(this);
        findViewById(R.id.time_day).setOnClickListener(this);
        findViewById(R.id.time_hour).setOnClickListener(this);
        findViewById(R.id.time_min).setOnClickListener(this);
    }

    /**
     * 设置主诉flowlayout
     */
    private void setFlowLayout(List<String> data) {
        TagAdapter<String> tagAdapter = new TagAdapter<String>(data) {
            @Override
            public View getView(com.zhy.view.flowlayout.FlowLayout parent, int position, String o) {
                TextView tv =
                        (TextView) LayoutInflater.from(getContext()).inflate(R.layout.flow_text_selecor,
                        mFlowView, false);
                tv.setText(o);
                return tv;
            }
        };
        mFlowView.setAdapter(tagAdapter);
        mFlowView.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View v, int position, FlowLayout parent) {
                if (v instanceof com.zhy.view.flowlayout.TagView) {
                    View mTv = ((TagView) v).getTagView();
                    if (mTv instanceof TextView) {
                        mEdit.setText(mEdit.getText().toString() + ((TextView) mTv).getText().toString());
                        mEdit.setSelection(mEdit.getText().toString().length());
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("患者主诉");
        setRightTxt("确定");
        getView();
        content = getIntent().getStringExtra(Const.DATA);
        mEdit.setText(content);
        setRightBtnListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(Const.DATA, mEdit.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });
        List<String> list = new ArrayList<>();
        list.add("咳嗽");
        list.add("咳痰");
        list.add("咯学或血痰");
        list.add("胸闷");
        list.add("胸痛");
        list.add("盗汗");
        list.add("乏力");
        list.add("腰背痛");
        setFlowLayout(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time_0:
            case R.id.time_1:
            case R.id.time_2:
            case R.id.time_3:
            case R.id.time_4:
            case R.id.time_5:
            case R.id.time_6:
            case R.id.time_7:
            case R.id.time_8:
            case R.id.time_9:
            case R.id.time_morning:
            case R.id.time_noon:
            case R.id.time_night:
            case R.id.time_year:
            case R.id.time_month:
            case R.id.time_week:
            case R.id.time_day:
            case R.id.time_hour:
            case R.id.time_min:
                mEdit.setText(mEdit.getText().toString() + ((TextView) v).getText().toString());
                mEdit.setSelection(mEdit.getText().toString().length());
                break;
            default:
        }
    }
}
