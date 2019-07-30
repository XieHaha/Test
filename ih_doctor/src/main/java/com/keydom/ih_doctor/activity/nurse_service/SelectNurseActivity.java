package com.keydom.ih_doctor.activity.nurse_service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.nurse_service.controller.SelectNurseController;
import com.keydom.ih_doctor.activity.nurse_service.view.SelectNurseView;
import com.keydom.ih_doctor.adapter.NurseSelectRecyclrViewAdapter;
import com.keydom.ih_doctor.bean.NurseBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class SelectNurseActivity extends BaseControllerActivity<SelectNurseController> implements SelectNurseView {


    private RecyclerView recyclerView;
    private NurseSelectRecyclrViewAdapter nurseSelectRecyclrViewAdapter;
    /**
     * 本人科室ID
     */
    private long deptId = MyApplication.userInfo.getDeptId();
    /**
     * 获取到的所有护士列表
     */
    private List<NurseBean> mList = new ArrayList<>();
    /**
     * 护士列表搜索列表
     */
    private List<NurseBean> mTempList = new ArrayList<>();
    private TextView searchTv;
    private EditText searchInputEv;

    /**
     * 本科室直接返回选择结果
     *
     * @param context
     */
    public static void startActivitySelfDeptOnlyResult(Context context) {
        Intent starter = new Intent(context, SelectNurseActivity.class);
        ((Activity) context).startActivityForResult(starter, Const.NURSE_SLEECT_ONLY_RESULT);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_select_nurse_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle("选择护士");
        searchTv = this.findViewById(R.id.search_tv);
        searchInputEv = this.findViewById(R.id.search_input_ev);
        recyclerView = this.findViewById(R.id.select_doctor_rv);
        nurseSelectRecyclrViewAdapter = new NurseSelectRecyclrViewAdapter(this, mList);

        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMember(searchInputEv.getText().toString().trim());
            }
        });

        searchInputEv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                searchMember(searchInputEv.getText().toString().trim());
            }
        });
        recyclerView.setAdapter(nurseSelectRecyclrViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        getController().getNurseList();
    }

    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("deptId", deptId);
        return map;
    }

    @Override
    public void getNurseListSuccess(List<NurseBean> list) {
        mTempList = getWithOutMeList(list);
        mList.addAll(mTempList);
        nurseSelectRecyclrViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getNurseListFailed(String errMsg) {
        ToastUtil.shortToast(this, errMsg);
    }

    private void searchMember(String key) {
        CommonUtils.hideSoftKeyboard(this);
        mList.clear();
        mList.addAll(mTempList);
        Iterator<NurseBean> it = mList.iterator();
        while (it.hasNext()) {
            if (!it.next().getName().contains(key)) {
                it.remove();
            }
        }
        nurseSelectRecyclrViewAdapter.notifyDataSetChanged();
    }

    public List<NurseBean> getWithOutMeList(List<NurseBean> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == MyApplication.userInfo.getId()) {
                list.remove(i);
            }
        }
        return list;
    }
}
