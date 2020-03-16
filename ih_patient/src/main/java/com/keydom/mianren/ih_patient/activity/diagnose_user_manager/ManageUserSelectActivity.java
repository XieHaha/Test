package com.keydom.mianren.ih_patient.activity.diagnose_user_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.controller.ManageUserSelectController;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.view.ManageUserSelectView;
import com.keydom.mianren.ih_patient.adapter.ManageUserNewAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 就诊人选择页面
 */
public class ManageUserSelectActivity extends BaseControllerActivity<ManageUserSelectController> implements ManageUserSelectView {
    private RecyclerView recyclerView;
    private ManageUserNewAdapter adapter;
    /**
     * 当前选中就诊人id
     */
    private long curId;

    /**
     * 启动页面
     */
    public static void start(Context context, long id) {
        Intent intent = new Intent(context, ManageUserSelectActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_manage_user_new;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        curId = getIntent().getLongExtra("id", -1);
        setTitle("选择就诊人");
        recyclerView = this.findViewById(R.id.user_rv);
        adapter = new ManageUserNewAdapter(new ArrayList<>());
        adapter.setId(curId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getController().getManagerUserList();
        adapter.setOnItemClickListener((adapter, view, position) -> {
            EventBus.getDefault().post(new Event(EventType.SENDPATIENTINFO,
                    adapter.getData().get(position)));
            finish();
        });
    }

    @Override
    public void getMangerUserList(List<ManagerUserBean> data) {
        adapter.setNewData(data);
    }

}
