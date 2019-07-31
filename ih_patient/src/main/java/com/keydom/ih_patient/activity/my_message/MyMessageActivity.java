package com.keydom.ih_patient.activity.my_message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.my_message.controller.MyMessagaeController;
import com.keydom.ih_patient.activity.my_message.view.MyMessageView;
import com.keydom.ih_patient.adapter.MyMessageAdapter;
import com.keydom.ih_patient.bean.MessageBean;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.utils.ToastUtil;
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
    public static void start(Context context,String type,List<Object> dataList){
        Intent intent=new Intent(context,MyMessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("type",type);
        Bundle bundle=new Bundle();
        bundle.putSerializable("dataList", (Serializable) dataList);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    private SmartRefreshLayout my_messag_refresh;
    private RecyclerView my_message_rv;
    private List<Object> dataList=new ArrayList<>();
    private MyMessageAdapter myMessageAdapter;
    private int page=1;
    //启动类型 Type.NOTICEMESSAGE 通知公告  Type.MYMESSAGE 我的消息
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

        if(Type.NOTICEMESSAGE.equals(type)){
            setTitle("通知公告");
            dataList= (List<Object>) getIntent().getSerializableExtra("dataList");
            my_messag_refresh.setEnableRefresh(true);
            myMessageAdapter=new MyMessageAdapter(getContext(),dataList);
        }else if(Type.MYMESSAGE.equals(type)){
            setTitle("我的消息");
            my_messag_refresh.setEnableRefresh(true);
            myMessageAdapter=new MyMessageAdapter(getContext(),dataList);
        }
        my_message_rv.setAdapter(myMessageAdapter);
        my_messag_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if(Type.MYMESSAGE.equals(type)){
                    getController().getMyMessageList(getMessageMap());
                }
            }
        });
        my_messag_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if(Type.MYMESSAGE.equals(type)){
                    page=1;
                    getController().getMyMessageList(getMessageMap());
                }
            }
        });

    }

    private Map<String,Object> getMessageMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("userId",Global.getUserId());
        map.put("pageSize",8);
        map.put("currentPage",page);
        return map;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Type.MYMESSAGE.equals(type)){
            getController().getMyMessageList(getMessageMap());
        }
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
        ToastUtil.shortToast(getContext(),"接口异常:"+errMsg);
    }
}
