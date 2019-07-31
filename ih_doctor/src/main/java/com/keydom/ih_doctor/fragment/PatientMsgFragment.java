package com.keydom.ih_doctor.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.listener.OnRecentContactsListener;
import com.keydom.ih_common.im.listener.observer.TeamDataChangedObserver;
import com.keydom.ih_common.im.listener.observer.TeamMemberDataChangedObserver;
import com.keydom.ih_common.im.listener.observer.UserInfoObserver;
import com.keydom.ih_common.im.model.event.RecentContactEvent;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.adapter.FriendMsgRecyclrViewAdapter;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.fragment.controller.PatientMsgFragmentController;
import com.keydom.ih_doctor.fragment.view.PatientMsgFragmentView;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * @Name：com.kentra.yxyz.fragment
 * @Description：患者管理－消息页面
 * @Author：song
 * @Date：18/11/5 下午5:27
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:27
 */
public class PatientMsgFragment extends BaseControllerFragment<PatientMsgFragmentController> implements PatientMsgFragmentView {

    public RecyclerView friendMsgRv;
    /**
     * 最新联系人列表
     */
    private List<RecentContact> mList = new ArrayList<>();
    /**
     * 最近联系人列表适配器
     */
    FriendMsgRecyclrViewAdapter friendMsgRecyclrViewAdapter;


    private UserInfoObserver userInfoObserver;

    /**
     * 此处的EventBus事件在{@link com.keydom.ih_doctor.adapter.GroupChatRecyclrViewAdapter#onUnReadCountEvent(RecentContactEvent)} )}中处理
     */
    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initView();
        ImClient.queryRecentContacts(new OnRecentContactsListener() {
            @Override
            public void onRecentResult(List<RecentContact> recentContacts) {
                sortRecentContacts(recentContacts);
                RecentContactEvent event = new RecentContactEvent();
                event.setRecentContacts(recentContacts);
                EventBus.getDefault().postSticky(event);
                mList.clear();
                mList.addAll(recentContacts);
                friendMsgRecyclrViewAdapter.notifyDataSetChanged();
            }
        });
        registerObservers(true);
    }


    /**
     * 初始化页面
     */
    private void initView() {
        friendMsgRecyclrViewAdapter = new FriendMsgRecyclrViewAdapter(getContext(), mList);
        friendMsgRv = getView().findViewById(R.id.friend_msg_rv);
        friendMsgRv.setAdapter(friendMsgRecyclrViewAdapter);
        friendMsgRv.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        friendMsgRv.setNestedScrollingEnabled(false);
//        friendMsgRv.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        registerObservers(false);
        super.onDestroy();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_patient_msg;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_ALL, SessionTypeEnum.None);
//    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        enableMsgNotification(isVisibleToUser);
    }

    /**
     * 设置最近联系人的消息为已读
     * <p>
     * account, 聊天对象帐号，或者以下两个值：
     * {@link MsgService#MSG_CHATTING_ACCOUNT_ALL} 目前没有与任何人对话，但能看到消息提醒（比如在消息列表界面），不需要在状态栏做消息通知
     * {@link MsgService#MSG_CHATTING_ACCOUNT_NONE} 目前没有与任何人对话，需要状态栏消息通知
     */
    private void enableMsgNotification(boolean enable) {
        if (enable) {
            NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE, SessionTypeEnum.None);
        } else {
            NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_ALL, SessionTypeEnum.None);
        }
    }

    /**
     * ********************** 收消息，处理状态变化 ************************
     */
    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeRecentContact(messageObserver, register);

//        ImClient.getTeamChangedObservable().registerTeamDataChangedObserver(teamDataChangedObserver, register);
        ImClient.getTeamChangedObservable().registerTeamMemberDataChangedObserver(teamMemberDataChangedObserver, register);

        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }
    }

    /**
     * 此处的EventBus事件在{@link com.keydom.ih_doctor.adapter.GroupChatRecyclrViewAdapter#onUnReadCountEvent(RecentContactEvent)}中处理
     */
    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> recentContacts) {
            onRecentContactChanged(recentContacts);
            RecentContactEvent event = new RecentContactEvent();
            event.setRecentContacts(recentContacts);
            EventBus.getDefault().post(event);
        }
    };

    private void onRecentContactChanged(List<RecentContact> recentContacts) {
        int index;
        for (RecentContact r : recentContacts) {
            index = -1;
            for (int i = 0; i < mList.size(); i++) {
                if (r.getContactId().equals(mList.get(i).getContactId())
                        && r.getSessionType() == (mList.get(i).getSessionType())) {
                    index = i;
                    break;
                }
            }

            if (index >= 0) {
                mList.remove(index);
            }

            mList.add(r);
            sortRecentContacts(mList);
            friendMsgRecyclrViewAdapter.notifyDataSetChanged();
        }
    }

    private void sortRecentContacts(List<RecentContact> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp);
    }

    private static Comparator<RecentContact> comp = new Comparator<RecentContact>() {

        @Override
        public int compare(RecentContact o1, RecentContact o2) {
            long time = o1.getTime() - o2.getTime();
            return time == 0 ? 0 : (time > 0 ? -1 : 1);
        }
    };


    TeamDataChangedObserver teamDataChangedObserver = new TeamDataChangedObserver() {

        @Override
        public void onUpdateTeams(List<Team> teams) {
            friendMsgRecyclrViewAdapter.notifyDataSetChanged();
        }

        @Override
        public void onRemoveTeam(Team team) {

        }
    };

    TeamMemberDataChangedObserver teamMemberDataChangedObserver = new TeamMemberDataChangedObserver() {
        @Override
        public void onUpdateTeamMember(List<TeamMember> members) {
            friendMsgRecyclrViewAdapter.notifyDataSetChanged();
        }

        @Override
        public void onRemoveTeamMember(List<TeamMember> member) {

        }
    };

    private void registerUserInfoObserver() {
        if (userInfoObserver == null) {
            userInfoObserver = new UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
                    sortRecentContacts(mList);
                    friendMsgRecyclrViewAdapter.notifyDataSetChanged();
                }
            };
        }
        ImClient.getUserInfoObservable().registerObserver(userInfoObserver, true);
    }

    private void unregisterUserInfoObserver() {
        if (userInfoObserver != null) {
            ImClient.getUserInfoObservable().registerObserver(userInfoObserver, false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.UPDATE_MSG_LIST) {
            ImClient.queryRecentContacts(new OnRecentContactsListener() {
                @Override
                public void onRecentResult(List<RecentContact> recentContacts) {
                    mList.clear();
                    mList.addAll(recentContacts);
                    friendMsgRecyclrViewAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
