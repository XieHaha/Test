package com.keydom.mianren.ih_patient.activity.online_diagnoses_search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_search.controller.DiagnoseSearchController;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_search.view.DiagnoseSearchView;
import com.keydom.mianren.ih_patient.adapter.DiagnoseSearchAdapter;
import com.keydom.mianren.ih_patient.bean.RecommendDocAndNurBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问诊搜索页面
 */
public class DiagnoseSearchActivity extends BaseControllerActivity<DiagnoseSearchController> implements DiagnoseSearchView {

    /**
     * 启动
     */
    public static void start(Context context, int type,int isOnline,int isRecommend) {
        Intent intent = new Intent(context, DiagnoseSearchActivity.class);
        intent.putExtra("isOnline", isOnline);
        intent.putExtra("isRecommend", isRecommend);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }
    private static final int DOCTORTYPE=0;
    private static final int NURSETYPE=1;
    private EditText search_edt;
    private TextView search_close_tv;
    private RecyclerView doctor_or_department_rv;
    private DiagnoseSearchAdapter diagnoseSearchAdapter;
    private List<RecommendDocAndNurBean> recommendList=new ArrayList<>();
    private int type;
    private int isOnline;
    private int isRecommend;

    private RefreshLayout refreshLayout;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_diagnose_search_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getTitleLayout().setVisibility(View.GONE);
        type = getIntent().getIntExtra("type",0);
        isOnline = getIntent().getIntExtra("isOnline",0);
        isRecommend = getIntent().getIntExtra("isRecommend",0);
        search_edt = this.findViewById(R.id.search_edt);
        search_close_tv = this.findViewById(R.id.search_close_tv);
        search_close_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        doctor_or_department_rv = this.findViewById(R.id.doctor_or_department_rv);
        diagnoseSearchAdapter = new DiagnoseSearchAdapter(type,recommendList, this);
        doctor_or_department_rv.setAdapter(diagnoseSearchAdapter);
        if (type==DOCTORTYPE) {
            search_edt.setHint("搜索想问诊的医生");
        } else {
            search_edt.setHint("搜索想咨询的护士");
        }
        search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (type==DOCTORTYPE) {
                    if (charSequence.toString() != null && !"".equals(charSequence.toString())) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("hospitalId", App.hospitalId);
                        map.put("isOnline", isOnline);
                        map.put("isRecommend",isRecommend);
                        map.put("keyworld", charSequence.toString());
                        map.put("type", DOCTORTYPE);
                        map.put("currentPage", getController().getCurrentPage());
                        map.put("pageSize", Const.PAGE_SIZE);
                        getController().searchDoctor(map,TypeEnum.REFRESH);
                    }
                } else {
                    if (charSequence.toString() != null && !"".equals(charSequence.toString())) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("hospitalId", App.hospitalId);
                        map.put("isOnline", isOnline);
                        map.put("isRecommend",isRecommend);
                        map.put("keyworld", charSequence.toString());
                        map.put("type", NURSETYPE);
                        map.put("currentPage", getController().getCurrentPage());
                        map.put("pageSize", Const.PAGE_SIZE);
                        getController().searchDoctor(map,TypeEnum.REFRESH);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
    }

    @Override
    public void getSearchSuccess(List<RecommendDocAndNurBean> dataList, TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            recommendList.clear();
        }
        pageLoadingSuccess();
        recommendList.addAll(dataList);
        diagnoseSearchAdapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        getController().currentPagePlus();

    }

    @Override
    public void getSearchFailed(String Msg) {
        ToastUtil.showMessage(getContext(), "查询失败:"+Msg);
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        pageLoadingFail();
    }
}
