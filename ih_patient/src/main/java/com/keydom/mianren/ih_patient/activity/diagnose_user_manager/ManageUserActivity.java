package com.keydom.mianren.ih_patient.activity.diagnose_user_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.controller.ManageUserController;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.view.ManageUserView;
import com.keydom.mianren.ih_patient.adapter.ManageUserRecyclrViewAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 就诊人管理页面
 */
public class ManageUserActivity extends BaseControllerActivity<ManageUserController> implements ManageUserView, ManageUserRecyclrViewAdapter.IOnManagerItemClickListener {
    //从个人中心进入标识
    public static final String FROMUSERINDEX="from_user_index";
    //从就诊申请进入标识
    public static final String FROMDIAGNOSES="from_diagnoses";
    private RecyclerView recyclerView;
    private ManageUserRecyclrViewAdapter manageUserRecyclrViewAdapter;
    private String type;
    private TextView mAddHint;

    /**
     * 启动页面
     */
    public static void start(Context context,String type) {
        Intent intent = new Intent(context, ManageUserActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_manage_user_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        type=getIntent().getStringExtra("type");
        setTitle("管理就诊人");
        setRightTxt("新增就诊人");
        setRightBtnListener(v -> {
                Intent i = new Intent(ManageUserActivity.this, AddManageUserActivity.class);
                i.putExtra(AddManageUserActivity.TYPE, AddManageUserActivity.ADD);
                ActivityUtils.startActivity(i);
        });
        setLeftBtnListener(v -> {
            if(FROMDIAGNOSES.equals(type)){
                EventBus.getDefault().post(new Event(EventType.CHECKPATIENTINFOCHANGE,null));
            }
            finish();
        });
        mAddHint = findViewById(R.id.add_hint);
        recyclerView = this.findViewById(R.id.user_rv);
        manageUserRecyclrViewAdapter = new ManageUserRecyclrViewAdapter(new ArrayList<ManagerUserBean>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(manageUserRecyclrViewAdapter);
        getController().getManagerUserList();
        if(FROMDIAGNOSES.equals(type)){
            manageUserRecyclrViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    EventBus.getDefault().post(new Event(EventType.SENDPATIENTINFO,manageUserRecyclrViewAdapter.getData().get(position)));
                    finish();
                }
            });
        }
    }

    @Override
    public void getMangerUserList(List<ManagerUserBean> data) {
        manageUserRecyclrViewAdapter.setNewData(data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(FROMDIAGNOSES.equals(type)){
                EventBus.getDefault().post(new Event(EventType.CHECKPATIENTINFOCHANGE,null));
            }
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    public void deleteSuccess(int pos) {
        manageUserRecyclrViewAdapter.remove(pos);
    }

    @Override
    public void onDelete(final ManagerUserBean item, final int pos) {
        new GeneralDialog(getContext(), "确认删除就诊人吗？", () -> getController().deleteManager(item.getId(), pos)).setTitle("提示").setPositiveButton("确认").show();
    }

    @Override
    public void onEdit(ManagerUserBean item, int pos) {
        Intent i = new Intent(this, AddManageUserActivity.class);
        i.putExtra(AddManageUserActivity.TYPE, AddManageUserActivity.UPDATE);
        i.putExtra(AddManageUserActivity.MANAGER_USER, item);
        ActivityUtils.startActivity(i);
    }

    /**
     * 保存就诊人成功事件监听
     */
    @Subscribe
    public void onAddOrEditResult(ManagerUserBean managerUserBean) {
        getController().getManagerUserList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
