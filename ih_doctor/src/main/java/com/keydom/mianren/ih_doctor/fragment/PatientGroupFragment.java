package com.keydom.mianren.ih_doctor.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.listener.observer.TeamDataChangedObserver;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.adapter.GroupChatRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.fragment.controller.PatientGroupFragmentController;
import com.keydom.mianren.ih_doctor.fragment.view.PatientGroupFragmentView;
import com.netease.nimlib.sdk.team.model.Team;
import com.orhanobut.logger.Logger;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.fragment
 * @Description：患者管理－群组页面
 * @Author：song
 * @Date：18/11/5 下午5:27
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:27
 */
public class PatientGroupFragment extends BaseControllerFragment<PatientGroupFragmentController> implements PatientGroupFragmentView {
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
    public void onDestroy() {
        super.onDestroy();
        registerTeamUpdateObserver(false);
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        initView();
        registerTeamUpdateObserver(true);
    }


    /**
     * 初始化页面
     */
    private void initView() {
        groupRv = (RecyclerView) getView().findViewById(R.id.group_msg_rv);
        mAdapter = new GroupChatRecyclrViewAdapter(getContext(), mData = ImClient.getTeamProvider().getAllTeams());
        groupRv.setNestedScrollingEnabled(false);
        groupRv.setAdapter(mAdapter);
        groupRv.setLayoutManager(new LinearLayoutManager(getContext()));
//        groupRv.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_patient_group;
    }

    @Override
    public void onResume() {
        super.onResume();
        mData.clear();
        mData.addAll(ImClient.getTeamProvider().getAllTeams());
        mAdapter.notifyDataSetChanged();
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
            Logger.e("团队列表有变化 刷新");
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
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.unRegister();
    }
}
