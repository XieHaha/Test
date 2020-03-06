package com.keydom.ih_patient.activity.diagnose_user_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_user_manager.controller.ManageUserController;
import com.keydom.ih_patient.activity.diagnose_user_manager.view.ManageUserView;
import com.keydom.ih_patient.adapter.ManageUserNewAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.ManagerUserBean;
import com.keydom.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 就诊人管理页面
 */
public class ManageUserNewActivity extends BaseControllerActivity<ManageUserController> implements ManageUserView {
    //选择
    public static final String FROM_SELECT = "from_select";
    private RecyclerView recyclerView;
    private ManageUserNewAdapter adapter;
    private String type;
    /**
     * 当前选中就诊人id
     */
    private long curId;

    /**
     * 启动页面
     */
    public static void start(Context context, long id, String type) {
        Intent intent = new Intent(context, ManageUserNewActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_manage_user_new;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        curId = getIntent().getLongExtra("id", -1);
        setTitle("选择就诊人");
        recyclerView = this.findViewById(R.id.user_rv);
        adapter = new ManageUserNewAdapter(new ArrayList<>());
        adapter.setId(curId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getController().getManagerUserList();
        if (FROM_SELECT.equals(type)) {
            adapter.setOnItemClickListener((adapter, view, position) -> {
                EventBus.getDefault().post(new Event(EventType.SENDPATIENTINFO,
                        adapter.getData().get(position)));
                finish();
            });
        }
    }

    @Override
    public void getMangerUserList(List<ManagerUserBean> data) {
        adapter.setNewData(data);
    }

    @Override
    public void deleteSuccess(int pos) {

    }
}
