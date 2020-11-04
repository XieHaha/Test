package com.keydom.mianren.ih_patient.activity.online_diagnoses_order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.controller.PatientMainSuitController;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.view.PatientMainSuitView;
import com.keydom.mianren.ih_patient.constant.Const;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zhy.view.flowlayout.TagView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created date: 2019/1/8 on 16:53
 *
 * @author 顿顿
 */
public class PatientMainSuitActivity extends BaseControllerActivity<PatientMainSuitController> implements PatientMainSuitView {

    /**
     * 选择病情描述
     */
    public static final int SELECT_PATIENT_MAIN_DEC = 900;
    private EditText mEdit;
    private TagFlowLayout mFlowView;
    /**
     * 病情描述内容
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
    }

    /**
     * 设置主诉flowlayout
     */
    private void setFlowLayout(List<String> data) {
        TagAdapter<String> tagAdapter = new TagAdapter<String>(data) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
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
                if (v instanceof TagView) {
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
        setTitle("病情描述");
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
        list.add("头晕");
        list.add("头痛");
        list.add("恶心");
        list.add("呕吐");
        list.add("乏力");
        list.add("畏寒");
        list.add("发热");
        list.add("心累");
        list.add("气促");
        list.add("胸闷");
        list.add("心慌");
        list.add("流涕");
        list.add("咽干");
        list.add("咽痛");
        list.add("咳嗽");
        list.add("咳痰");
        list.add("腹胀");
        list.add("腹痛");
        list.add("腹泻");
        list.add("便秘");
        list.add("黑瞢");
        setFlowLayout(list);
    }
}
