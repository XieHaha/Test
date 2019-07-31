package com.keydom.ih_common.im.listener.observer;

import com.netease.nimlib.sdk.team.model.Team;

import java.util.List;

/**
 * 群数据变更监听接口
 */

public interface TeamDataChangedObserver {

    /**
     * 群更新
     *
     * @param teams 群列表
     */
    void onUpdateTeams(List<Team> teams);

    /**
     * 群删除
     *
     * @param team) 群
     */
    void onRemoveTeam(Team team);
}
