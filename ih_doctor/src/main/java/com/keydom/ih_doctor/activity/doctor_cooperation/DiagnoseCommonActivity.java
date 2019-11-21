package com.keydom.ih_doctor.activity.doctor_cooperation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.doctor_cooperation.controller.DiagnoseCommonController;
import com.keydom.ih_doctor.activity.doctor_cooperation.view.DiagnoseCommonView;
import com.keydom.ih_doctor.adapter.DiagnoseCommonRecyclrViewAdapter;
import com.keydom.ih_doctor.bean.ChangeDiagnoseRecoderBean;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：公用转诊列表页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DiagnoseCommonActivity extends BaseControllerActivity<DiagnoseCommonController> implements DiagnoseCommonView {

    /**
     * 等待接收
     */
    private static final int WAITTING_RECEIVE_VALUE = 0;
    /**
     * 问诊记录
     */
    private static final int RECODER_VALUE = 1;
    /**
     * 问诊单列表适配器
     */
    private DiagnoseCommonRecyclrViewAdapter diagnoseCommonRecyclrViewAdapter;
    /**
     * 区分页面类型
     */
    private TypeEnum mType;
    /**
     * 请求参数，0待接收列表，1记录列表
     */
    private int state = RECODER_VALUE;
    /**
     * 转诊单列表
     */
    private List<ChangeDiagnoseRecoderBean> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;


    /**
     * 转诊接收
     *
     * @param context
     */
    public static void startDiagnoseChangeReceive(Context context) {
        Intent starter = new Intent(context, DiagnoseCommonActivity.class);
        starter.putExtra(Const.TYPE, TypeEnum.DIAGNOSE_CHANGE_RECEIVE);
        context.startActivity(starter);
    }

    /**
     * 转诊记录
     *
     * @param context
     */
    public static void startDiagnoseChangeRecoder(Context context) {
        Intent starter = new Intent(context, DiagnoseCommonActivity.class);
        starter.putExtra(Const.TYPE, TypeEnum.DIAGNOSE_CHANGE_RECODER);
        context.startActivity(starter);
    }

    /**
     * 会诊接收
     *
     * @param context
     */
    public static void startDiagnoseGroupReceive(Context context) {
        Intent starter = new Intent(context, DiagnoseCommonActivity.class);
        starter.putExtra(Const.TYPE, TypeEnum.DIAGNOSE_GROUP_RECEIVE);
        context.startActivity(starter);
    }

    /**
     * 会诊记录
     *
     * @param context
     */
    public static void startDiagnoseGroupRecoder(Context context) {
        Intent starter = new Intent(context, DiagnoseCommonActivity.class);
        starter.putExtra(Const.TYPE, TypeEnum.DIAGNOSE_GROUP_RECODER);
        context.startActivity(starter);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_diagnose_common_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mType = (TypeEnum) getIntent().getSerializableExtra(Const.TYPE);
        EventBus.getDefault().register(this);
        if (mType == TypeEnum.DIAGNOSE_CHANGE_RECEIVE) {
            setTitle("转诊接收");
            state = WAITTING_RECEIVE_VALUE;
        } else if (mType == TypeEnum.DIAGNOSE_CHANGE_RECODER) {
            setTitle("转诊记录");
            state = RECODER_VALUE;
        } else if (mType == TypeEnum.DIAGNOSE_GROUP_RECEIVE) {
            setTitle("会诊接收");
        } else if (mType == TypeEnum.DIAGNOSE_GROUP_RECODER) {
            setTitle("会诊记录");
        } else {
            ToastUtil.showMessage(this, "参数错误");
            finish();
        }
        recyclerView = this.findViewById(R.id.diagnose_list_rv);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        diagnoseCommonRecyclrViewAdapter = new DiagnoseCommonRecyclrViewAdapter(this, mList, mType);
        recyclerView.setAdapter(diagnoseCommonRecyclrViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pageLoading();
        getController().getRecoder(TypeEnum.REFRESH);
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getRecoder(TypeEnum.REFRESH);
            }
        });
    }


    @Override
    public void getListSuccess(List<ChangeDiagnoseRecoderBean> list, TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            mList.clear();
        }
        pageLoadingSuccess();
        mList.addAll(list);
        if(mList.size()==0)
            MyApplication.receiveReferral=0;
        diagnoseCommonRecyclrViewAdapter.notifyDataSetChanged();
        getController().currentPagePlus();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void getListFailed(String errMsg) {
        pageLoadingFail();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("state", state);
        return map;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 更新列表
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.DIAGNOSE_ORDER_UPDATE) {
            updateList(Long.valueOf(messageEvent.getData().toString()));
            DiagnoseCommonActivity.startDiagnoseChangeRecoder(getContext());
        }
    }

    /**
     * 更新列表，并刷新显示
     *
     * @param id
     */
    private void updateList(long id) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getId() == id) {
                mList.remove(i);
                break;
            }
        }

        diagnoseCommonRecyclrViewAdapter.notifyDataSetChanged();
    }


}
