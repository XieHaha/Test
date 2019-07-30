package com.keydom.ih_doctor.activity.online_diagnose;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.controller.PrescriptionTempletController;
import com.keydom.ih_doctor.activity.online_diagnose.view.PrescriptionTempletView;
import com.keydom.ih_doctor.adapter.PrescriptionTempletAdapter;
import com.keydom.ih_doctor.bean.PrescriptionTempletBean;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @link
 *
 * Author: song
 *
 * Create: 19/3/7 上午10:49
 *
 * Changes (from 19/3/7)
 *
 * 19/3/7 : Create PrescriptionTempletActivity.java (song);
 *
 *
 *
 */
public class PrescriptionTempletActivity extends BaseControllerActivity<PrescriptionTempletController> implements PrescriptionTempletView {

    /**
     * 医院模版类型
     */
    public static final String HOSPITAL = "1";
    /**
     * 个人模版类型
     */
    public static final String PERSONAL = "0";

    /**
     * 开启处方模版页面
     * @param context
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, PrescriptionTempletActivity.class));
    }

    private TextView type_tv, search_tv;
    private EditText search_et;
    private RecyclerView recyclerView;
    private PrescriptionTempletAdapter prescriptionTempletAdapter;
    private List<PrescriptionTempletBean> dataList = new ArrayList<>();
    private List<PrescriptionTempletBean> tempList = new ArrayList<>();
    private String mType = PERSONAL;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_prescription_templet_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("处方模板");
        type_tv = findViewById(R.id.type_tv);
        search_tv = findViewById(R.id.search_tv);
        search_et = findViewById(R.id.search_et);
        recyclerView = findViewById(R.id.recyclerView);
        type_tv.setOnClickListener(getController());
        prescriptionTempletAdapter = new PrescriptionTempletAdapter(dataList);
        search_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTemplet(search_et.getText().toString().trim());
                CommonUtils.hideSoftKeyboard(PrescriptionTempletActivity.this);
            }
        });
        prescriptionTempletAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PrescriptionTempletDetailActivity.start(getContext(), prescriptionTempletAdapter.getData().get(position).getId(), prescriptionTempletAdapter.getData().get(position).getTemplateName());
            }
        });
        recyclerView.setAdapter(prescriptionTempletAdapter);
        getController().getPrescriptionTempletList();
    }

    /**
     * 关键字搜索模版
     * @param key
     */
    private void searchTemplet(String key) {
        dataList.clear();
        dataList.addAll(tempList);
        Iterator<PrescriptionTempletBean> it = dataList.iterator();
        while (it.hasNext()) {
            if (!it.next().getTemplateName().contains(key)) {
                it.remove();
            }
        }
        prescriptionTempletAdapter.notifyDataSetChanged();
    }

    @Override
    public void getTempletListSuccess(List<PrescriptionTempletBean> data) {
        tempList.addAll(data);
        this.dataList = data;
        prescriptionTempletAdapter.setNewData(dataList);
    }

    @Override
    public void getTempletListFailed(String errMsg) {
        pageLoadingFail();
    }


    @Override
    public void setDept(String dept, String type) {
        type_tv.setText(dept);
        mType = type;
    }

    @Override
    public String getType() {
        return mType;
    }
}
