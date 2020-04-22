package com.keydom.ih_common.avchatkit.selector.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.R;
import com.keydom.ih_common.avchatkit.selector.ContactSelectAdapter;
import com.keydom.ih_common.avchatkit.selector.controller.ContactSelectController;
import com.keydom.ih_common.avchatkit.selector.view.ContactSelectView;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.listener.SimpleCallback;
import com.keydom.ih_common.im.manager.ImPreferences;
import com.keydom.ih_common.view.IhTitleLayout;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * 联系人选择器
 * <p/>
 * Created by huangjun on 2015/3/3.
 */
public class ContactSelectActivity extends BaseControllerActivity<ContactSelectController> implements ContactSelectView, BaseQuickAdapter.OnItemClickListener {
    RecyclerView recyclerView;
    private List<UserInfo> nimUserInfos;
    private ContactSelectAdapter selectAdapter;
    private List<String> selectIds = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.activity_contact_select;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("邀请成员");
        initRightBtn();
        String teamId = getIntent().getStringExtra("teamId");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectAdapter = new ContactSelectAdapter(nimUserInfos);
        selectAdapter.setOnItemClickListener(this);
        selectAdapter.setSelectId(selectIds);
        recyclerView.setAdapter(selectAdapter);

        ImClient.getTeamProvider().fetchTeamMemberList(teamId,
                new SimpleCallback<List<TeamMember>>() {
                    @Override
                    public void onResult(boolean success, List<TeamMember> result, int code) {
                        filterData(result);
                    }
                });

        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                if (selectIds != null && selectIds.size() > 0) {

                }
            }
        });
    }

    private void filterData(List<TeamMember> result) {
        nimUserInfos = new ArrayList<>();
        for (TeamMember member : result) {
            UserInfo userInfo = ImClient.getUserInfoProvider().getUserInfo(member.getAccount());
            if (TextUtils.equals(userInfo.getAccount(),
                    ImPreferences.getUserAccount().toLowerCase())) {
                continue;
            }
            nimUserInfos.add(userInfo);
        }
        selectAdapter.setNewData(nimUserInfos);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String id = nimUserInfos.get(position).getAccount();
        if (selectIds.contains(id)) {
            selectIds.remove(id);
        } else {
            selectIds.add(id);
        }
        selectAdapter.setSelectId(selectIds);
        selectAdapter.notifyDataSetChanged();

        initRightBtn();
    }

    private void initRightBtn() {
        if (selectIds != null && selectIds.size() > 0) {
            setRightTxt("(" + selectIds.size() + "/8)确定");
            setRightColor(R.color.bottom_member_blue);
        } else {
            setRightTxt("确定");
            setRightColor(R.color.help_color);
        }
    }

}