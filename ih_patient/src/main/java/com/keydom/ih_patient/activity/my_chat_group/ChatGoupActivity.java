package com.keydom.ih_patient.activity.my_chat_group;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.listener.observer.TeamDataChangedObserver;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.my_chat_group.controller.ChatGoupController;
import com.keydom.ih_patient.activity.my_chat_group.view.ChatGoupView;
import com.keydom.ih_patient.adapter.GroupChatRecyclrViewAdapter;
import com.netease.nimlib.sdk.team.model.Team;

import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ChatGoupActivity extends BaseControllerActivity<ChatGoupController> implements ChatGoupView {
    public static void start(Context context){
        context.startActivity(new Intent(context,ChatGoupActivity.class));
    }
    public RecyclerView groupRv;
    /**
     * 团队列表
     */
    private List<Team> mData;
    /**
     * 团队列表适配器
     */
    private GroupChatRecyclrViewAdapter mAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_chat_group_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initView();
        registerTeamUpdateObserver(true);
    }

    /**
     * 初始化页面
     */
    private void initView() {
        setTitle("我的群");
        groupRv =findViewById(R.id.group_msg_rv);
        mAdapter = new GroupChatRecyclrViewAdapter(getContext(), mData = ImClient.getTeamProvider().getAllTeams());
        groupRv.setNestedScrollingEnabled(false);
        groupRv.setAdapter(mAdapter);
        groupRv.setLayoutManager(new LinearLayoutManager(getContext()));
//        groupRv.addItemDecoration(dividerItemDecoration);
    }

    /**
     * 注册团队更新回调
     *
     * @param register
     */
    private void registerTeamUpdateObserver(boolean register) {
        ImClient.getTeamChangedObservable().registerTeamDataChangedObserver(teamDataChangedObserver, register);
    }

    TeamDataChangedObserver teamDataChangedObserver = new TeamDataChangedObserver() {
        @Override
        public void onUpdateTeams(List<Team> teams) {
            if (teams == null) {
                return;
            }

            for (Iterator<Team> it = mData.iterator(); it.hasNext(); ) {
                Team t = it.next();
                for (Iterator<Team> rit = teams.iterator(); rit.hasNext(); ) {
                    Team rt = rit.next();
                    if (rt.getId().equals(t.getId())) {
//                        mData.remove(t);
                        rit.remove();
                    }
                }
            }
            mData.addAll(teams);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onRemoveTeam(Team team) {
            mData.remove(team);
            mAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 移除群
     *
     * @param list 群对象列表
     */
    private void removeDuplicate(List<Team> list) {
        HashSet hashSet = new HashSet(list);
        mData.clear();
        mData.addAll(hashSet);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.unRegister();
        registerTeamUpdateObserver(false);
    }
}
