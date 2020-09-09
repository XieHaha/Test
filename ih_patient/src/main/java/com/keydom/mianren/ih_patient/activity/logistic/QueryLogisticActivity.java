package com.keydom.mianren.ih_patient.activity.logistic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.adapter.QuryLogisticAdapter;
import com.keydom.mianren.ih_patient.bean.entity.GetLogisicBean;
import com.keydom.mianren.ih_patient.bean.entity.LogisticsEntity;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.net.PrescriptionService;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.keydom.mianren.ih_patient.utils.GotoActivityUtil;
import com.keydom.mianren.ih_patient.view.SpaceItemDecoration;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryLogisticActivity extends BaseActivity {
    private EditText mEditText;
    private TextView mTextView, emptyTv;
    private RecyclerView mRecyclerView;
    private QuryLogisticAdapter quryLogisticAdapter;
    private SmartRefreshLayout smartRefreshLayout;
    private RelativeLayout emptyLayout;
    private String mWaybill = null;
    private String mUserId;
    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ImageEngine.init(this);// 初始化图片加载库

        //TODO : 用户UserID 没有数据，暂时用1，1有数据
        mUserId = String.valueOf(Global.getUserId());
        getHttpUserId(mUserId, page, Const.PAGE_SIZE);
        //初始化组件
        mEditText = findViewById(R.id.search_edt);
        mTextView = findViewById(R.id.search_tv);
        mRecyclerView = findViewById(R.id.recycler_get_drug);
        smartRefreshLayout = findViewById(R.id.refresh_layout);
        emptyTv = findViewById(R.id.empty_text);
        emptyLayout = findViewById(R.id.state_retry2);

        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置RecyclerView 布局
        mRecyclerView.setLayoutManager(layoutmanager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(0, 19));
        quryLogisticAdapter = new QuryLogisticAdapter(this);
        mRecyclerView.setAdapter(quryLogisticAdapter);

        /**
         * 刷新方法
         */
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                // getHttp(mWaybill);
                page = 1;
                getHttpUserId(mUserId, page, Const.PAGE_SIZE);
                smartRefreshLayout.finishRefresh();
            }
        });


        //下拉加载更多
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getHttpUserId(mUserId, page, Const.PAGE_SIZE);
/*                if (0 == quryLogisticAdapter.getItemCount()) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    smartRefreshLayout.finishLoadMore();
                }*/

                //                if (mDatas.size() < 5) {
                //                    refreshLayout.finishLoadMoreWithNoMoreData();
                //                } else {
                //                    smartRefreshLayout.finishLoadMore();
                //                }
            }
        });

        quryLogisticAdapter.setOnItemClickListener(new QuryLogisticAdapter.OnItemClickListener() {
            @Override
            public void onClick(String v) {
                if (!CommUtil.isEmpty(v)) {
                    GotoActivityUtil.gotoLogisticDetailActivity(QueryLogisticActivity.this, v);
                }
            }
        });
        /**
         * 点击搜索
         */
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                Intent intent=new Intent(QueryLogisticActivity.this,
                //                ChoosePharmacyActivity.class);
                //                startActivity(intent);
                String value = mEditText.getText().toString();
                if (!CommUtil.isEmpty(value)) {
                    // TODO: 2019/5/6   正则表达式判断  接口
                    getHttp(value);
                    mWaybill = value;
                } else {
                    Toast.makeText(QueryLogisticActivity.this, "请填写单号", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_query_logistic;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("物流查询");
    }

    /**
     * 根据运单号获取物流列表接口
     *
     * @param id
     */
    public void getHttp(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("waybill", id);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getLogisticsList(map), new HttpSubscriber<LogisticsEntity>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable LogisticsEntity logisticsEntity) {
                Logger.e("uuuu=" + logisticsEntity);
                emptyLayout.setVisibility(View.GONE);
                if (logisticsEntity != null) {
                    List<LogisticsEntity> logisticsEntities = new ArrayList<>();
                    logisticsEntities.add(logisticsEntity);
                    refreshView(logisticsEntities);
                } else {
                    pageEmpty();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                Logger.e("msg=" + msg);
                Logger.e("code=" + code);
                loadingFail(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 根据运单号获取物流列表接口
     *
     * @param id
     */
    public void getHttpUserId(String id, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", id);
        map.put("current", page);
        map.put("size", pageSize);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getLogistics(map), new HttpSubscriber<GetLogisicBean>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable GetLogisicBean bean) {
                //  Logger.e("uuuu=" + logisticsEntity);
                emptyLayout.setVisibility(View.GONE);
                if (bean != null && !CommUtil.isEmpty(bean.getRecords())) {
                    //                    List<LogisticsEntity> logisticsEntities=new ArrayList<>();
                    //                    logisticsEntities.add(logisticsEntity);logisticsEntity
                    refreshView(bean.getRecords());
                } else {
                    pageEmpty();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                Logger.e("msg=" + msg);
                Logger.e("code=" + code);
                loadingFail(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 根据运单号获取物流列表接口
     *
     * @param waybill
     */
    public void getHttpByWallBill(String waybill, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("waybill", waybill);
        map.put("current", page);
        map.put("size", pageSize);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getLogisticsByWallBill(map), new HttpSubscriber<GetLogisicBean>(this, getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable GetLogisicBean getLogisicBean) {
                //  Logger.e("uuuu=" + logisticsEntity);
                emptyLayout.setVisibility(View.GONE);
                if (!CommUtil.isEmpty(getLogisicBean.getRecords())) {
                    //                    List<LogisticsEntity> logisticsEntities=new ArrayList<>();
                    //                    logisticsEntities.add(logisticsEntity);logisticsEntity
                    refreshView(getLogisicBean.getRecords());
                } else {
                    pageEmpty();
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                Logger.e("msg=" + msg);
                Logger.e("code=" + code);
                loadingFail(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 刷新数据
     *
     * @param data
     */
    private void refreshView(List<LogisticsEntity> data) {

        if (!CommUtil.isEmpty(data)) {
            if (page == 1) {
                quryLogisticAdapter.setList(data);
            } else {

                quryLogisticAdapter.addList(data);
            }
            page++;
        }

        if (data.size() < Const.PAGE_SIZE) {
            smartRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            smartRefreshLayout.finishLoadMore();
        }
    }

    /**
     * 加载数据失败页面显示
     */
    public void loadingFail(String msg) {
        emptyLayout.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(msg)) {
            emptyTv.setText("数据返回异常点击重试");
        } else {
            emptyTv.setText(msg + "点击重试");
        }
        emptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHttpUserId(mUserId, page, Const.PAGE_SIZE);
            }
        });
    }
}
