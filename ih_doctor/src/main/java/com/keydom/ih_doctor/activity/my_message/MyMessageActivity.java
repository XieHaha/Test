package com.keydom.ih_doctor.activity.my_message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;


import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.my_message.controller.MyMessagaeController;
import com.keydom.ih_doctor.activity.my_message.view.MyMessageView;
import com.keydom.ih_doctor.adapter.MyMessageAdapter;
import com.keydom.ih_doctor.bean.MessageBean;
import com.keydom.ih_doctor.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的消息页面
 */
public class MyMessageActivity extends BaseControllerActivity<MyMessagaeController> implements MyMessageView {
    /**
     * 启动
     */
    public static void start(Context context,List<Object> dataList){
        Intent intent=new Intent(context,MyMessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle=new Bundle();
        bundle.putSerializable("dataList", (Serializable) dataList);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    private SmartRefreshLayout my_messag_refresh;
    private RecyclerView my_message_rv;
    private List<Object> dataList=new ArrayList<>();
    private MyMessageAdapter myMessageAdapter;
    private String type;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_my_message_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type=getIntent().getStringExtra("type");

        getTitleLayout().initViewsVisible(true,true,false);
        my_messag_refresh=findViewById(R.id.my_messag_refresh);
        my_message_rv=findViewById(R.id.my_message_rv);

        setTitle("我的消息");
        my_messag_refresh.setEnableRefresh(false);
        myMessageAdapter=new MyMessageAdapter(getContext(),dataList);
        my_message_rv.setAdapter(myMessageAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getController().getMyMessageList();
    }

    @Override
    public void getMessageListSuccess(List<MessageBean> messageList) {
        dataList.clear();
        dataList.addAll(messageList);
        myMessageAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMessageListFailed(String errMsg) {
        ToastUtil.shortToast(getContext(),"接口异常:"+errMsg);
    }
}
