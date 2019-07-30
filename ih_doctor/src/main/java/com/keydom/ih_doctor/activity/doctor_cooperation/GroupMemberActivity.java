package com.keydom.ih_doctor.activity.doctor_cooperation;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.doctor_cooperation.controller.GroupMemberController;
import com.keydom.ih_doctor.activity.doctor_cooperation.view.GroupMemberView;
import com.keydom.ih_doctor.adapter.GroupMemberRecyclrViewAdapter;
import com.keydom.ih_doctor.bean.DeptDoctorBean;
import com.keydom.ih_doctor.constant.Const;

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
public class GroupMemberActivity extends BaseControllerActivity<GroupMemberController> implements GroupMemberView {

    /**
     * 初始化默认的团队ID
     */
    private int groupId = -1;
    /**
     * 团队所有医生列表
     */
    private List<DeptDoctorBean> mList = new ArrayList<>();
    /**
     * 团队所有医生临时列表（搜索用）
     */
    private List<DeptDoctorBean> tempList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageView addMember;
    private GroupMemberRecyclrViewAdapter groupMemberRecyclrViewAdapter;
    private TextView searchTv;
    private EditText searchInputEv;

    /**
     * 启动团队成员页面
     *
     * @param context
     */
    public static void start(Context context, int groupId) {
        Intent starter = new Intent(context, GroupMemberActivity.class);
        starter.putExtra(Const.DATA, groupId);
        context.startActivity(starter);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_group_member_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        groupId = getIntent().getIntExtra(Const.DATA, Const.INT_DEFAULT);
        setTitle("团队成员");
        recyclerView = this.findViewById(R.id.group_member_rv);
        searchTv = this.findViewById(R.id.search_tv);
        searchInputEv = this.findViewById(R.id.search_input_ev);
        addMember = this.findViewById(R.id.add_member);
        groupMemberRecyclrViewAdapter = new GroupMemberRecyclrViewAdapter(this, mList);
        recyclerView.setAdapter(groupMemberRecyclrViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        addMember.setOnClickListener(getController());
        pageLoading();
        getController().ihGroupQueryDoctorTeamAllUser();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().ihGroupQueryDoctorTeamAllUser();
            }
        });
        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMember(searchInputEv.getText().toString().trim());
                CommonUtils.hideSoftKeyboard(GroupMemberActivity.this);
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
    }


    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        return map;
    }

    @Override
    public void getGroupMemberSuccess(List<DeptDoctorBean> list) {
        pageLoadingSuccess();
        mList.clear();
        tempList.clear();
        mList.addAll(list);
        tempList.addAll(list);
        groupMemberRecyclrViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getGroupMemberFailed(String errMsg) {
        pageLoadingFail();
    }

    @Override
    public long getGroupId() {
        return groupId;
    }

    @Override
    public List<DeptDoctorBean> getSelectedList() {
        return mList;
    }

    /**
     * 关键字搜索团队成员，只支持本地搜索
     *
     * @param key 搜索关键字
     */
    private void searchMember(String key) {
        mList.clear();
        mList.addAll(tempList);
        Iterator<DeptDoctorBean> it = mList.iterator();
        while (it.hasNext()) {
            if (!it.next().getName().contains(key)) {
                it.remove();
            }
        }
        groupMemberRecyclrViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Const.DOCTOR_SLEECT:
                    List<DeptDoctorBean> list = (List<DeptDoctorBean>) data.getSerializableExtra(Const.DATA);
                    if (list != null && list.size() > 0) {
                        mList.addAll(list);
                        tempList.addAll(list);
                        groupMemberRecyclrViewAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
