package com.keydom.ih_doctor.activity.online_diagnose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.online_diagnose.controller.DiagnoseInputController;
import com.keydom.ih_doctor.activity.online_diagnose.view.DiagnoseInputView;
import com.keydom.ih_doctor.adapter.ICD10ListAdapter;
import com.keydom.ih_doctor.bean.ICD10Bean;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：初步诊断快捷添加页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DiagnoseInputActivity extends BaseControllerActivity<DiagnoseInputController> implements DiagnoseInputView {

    /**
     * 回传数据code
     */
    public static final int DIAGNOSE_RES_INPUT = 901;
    private EditText diagnoseInputEt, searchInputEv;
    private TextView searchTv;
    private RecyclerView recyclerView;
    /**
     * 列表适配器
     */
    private ICD10ListAdapter icd10ListAdapter;
    /**
     * 搜索的关键字
     */
    private String inputStr = "";
    private RefreshLayout refreshLayout;
    /**
     * 查询到的列表
     */
    private List<ICD10Bean> mList = new ArrayList<>();

    public static void start(Context context, String content) {
        Intent starter = new Intent(context, DiagnoseInputActivity.class);
        starter.putExtra(Const.DATA, content);
        ((Activity) context).startActivityForResult(starter, DIAGNOSE_RES_INPUT);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_diagnose_input_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        initView();
        setTitle("初步诊断");
        setRightTxt("确定");
        EventBus.getDefault().register(this);
        inputStr = getIntent().getStringExtra(Const.DATA);
        diagnoseInputEt.setText(inputStr);
        diagnoseInputEt.setSelection(inputStr.length());
        getController().icdCateList();
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @SingleClick(1000)
            @Override
            public void OnRightTextClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Const.DATA, diagnoseInputEt.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        diagnoseInputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputStr = s.toString();
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.GET_ICD_10_VALUE) {
            if (inputStr == null || "".equals(inputStr)) {
                inputStr += String.valueOf(messageEvent.getData());
            } else {
                inputStr += "," + String.valueOf(messageEvent.getData());
            }
            diagnoseInputEt.setText(inputStr);
            diagnoseInputEt.setSelection(inputStr.length());
            com.keydom.ih_common.utils.CommonUtils.hideSoftKeyboard(this);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 初始化页面
     */
    private void initView() {
        searchInputEv = (EditText) findViewById(R.id.search_input_ev);
        searchTv = (TextView) findViewById(R.id.search_tv);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        diagnoseInputEt = this.findViewById(R.id.diagnose_input_et);
        recyclerView = this.findViewById(R.id.icd_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        searchTv.setOnClickListener(getController());
        showICD10List();
    }

    /**
     * 显示ICD-10列表
     */
    private void showICD10List() {
        if (icd10ListAdapter == null) {
            icd10ListAdapter = new ICD10ListAdapter(DiagnoseInputActivity.this, mList);
        }
        recyclerView.setAdapter(icd10ListAdapter);
    }

    @Override
    public void getICDListSuccess(List<ICD10Bean> list) {
        mList.addAll(list);
        icd10ListAdapter.notifyDataSetChanged();
        getController().currentPagePlus();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void getICDListFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public String getKeyWord() {
        return searchInputEv.getText().toString();
    }

    @Override
    public void clearList() {
        mList.clear();
    }
}
