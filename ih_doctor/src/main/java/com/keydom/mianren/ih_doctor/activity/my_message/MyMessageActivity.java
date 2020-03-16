package com.keydom.mianren.ih_doctor.activity.my_message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.my_message.controller.MyMessagaeController;
import com.keydom.mianren.ih_doctor.activity.my_message.view.MyMessageView;
import com.keydom.mianren.ih_doctor.adapter.MyMessageAdapter;
import com.keydom.mianren.ih_doctor.bean.MessageBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private int page=1;
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
        my_messag_refresh.setEnableRefresh(true);
        my_messag_refresh.setEnableLoadMore(true);
        myMessageAdapter=new MyMessageAdapter(getContext(),dataList);
        my_message_rv.setAdapter(myMessageAdapter);
        my_messag_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getController().getMyMessageList(getMessageMap());
            }
        });
        my_messag_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page=1;
                getController().getMyMessageList(getMessageMap());

            }
        });
    }
    private Map<String,Object> getMessageMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("doctorPhone",SharePreferenceManager.getPhoneNumber());
        map.put("pageSize",8);
        map.put("currentPage",page);
        return map;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getController().getMyMessageList(getMessageMap());
    }

    @Override
    public void getMessageListSuccess(List<MessageBean> messageList) {
        my_messag_refresh.finishLoadMore();
        my_messag_refresh.finishRefresh();
        if(messageList!=null&&messageList.size()>0){
            if(page==1){
                dataList.clear();
                dataList.addAll(messageList);
                myMessageAdapter.notifyDataSetChanged();

            }else {
                dataList.addAll(messageList);
                myMessageAdapter.notifyDataSetChanged();
            }
            page++;
        }else {
            if(page==1){
                dataList.clear();
                dataList.addAll(messageList);
                myMessageAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void getMessageListFailed(String errMsg) {
        ToastUtil.showMessage(getContext(),"接口异常:"+errMsg);
    }
}
