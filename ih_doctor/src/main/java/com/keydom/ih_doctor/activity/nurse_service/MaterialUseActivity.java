package com.keydom.ih_doctor.activity.nurse_service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.adapter.MaterialUseAdapter;
import com.keydom.ih_doctor.bean.Event;
import com.keydom.ih_doctor.bean.MaterialBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.m_interface.SingleClick;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

public class MaterialUseActivity extends BaseActivity {

    /**
     * 选择的耗材列表
     */
    private List<MaterialBean> selectList;
    private RecyclerView recyclerView;
    /**
     * 耗材选择数量适配器
     */
    private MaterialUseAdapter materialUseAdapter;
    private TextView saveDrug;

    /**
     * 启动耗材数量设置页面
     *
     * @param context
     * @param list    添加的耗材列表
     */
    public static void start(Context context, List<MaterialBean> list) {
        Intent intent = new Intent(context, MaterialUseActivity.class);
        intent.putExtra(Const.DATA, (Serializable) list);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_medicine_dosage_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        saveDrug = this.findViewById(R.id.save_drug);
        selectList = (List<MaterialBean>) getIntent().getSerializableExtra(Const.DATA);
        setTitle("用法用量");
        recyclerView = findViewById(R.id.medicine_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        materialUseAdapter = new MaterialUseAdapter(selectList);
        recyclerView.setAdapter(materialUseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        saveDrug.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (checkSubmit()) {
                    Event event = new Event(EventType.CHOOSE_MATERIAL_USE, selectList);
                    EventBus.getDefault().post(event);
                    ActivityUtils.finishActivity(MaterialChooseActivity.class);
                    finish();
                } else {
                    ToastUtil.showMessage(MaterialUseActivity.this, "请完善药品信息");
                }
            }
        });
    }

    /**
     * 检查是否可以提交
     *
     * @return
     */
    private boolean checkSubmit() {
        for (MaterialBean bean : selectList) {
            if (bean.getMaterialAmount() == 0) {
                return false;
            }
        }
        return true;
    }

}
