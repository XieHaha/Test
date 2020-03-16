package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.im.model.event.RecentContactEvent;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.im.PatientTeamChatActivity;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.model.Team;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：团队聊天适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class GroupChatRecyclrViewAdapter extends BaseEmptyAdapter<Team> {


    public GroupChatRecyclrViewAdapter(Context context, List<Team> data) {
        super(data, context);
        EventBus.getDefault().register(this);
    }

    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.patient_group_msg_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GroupChatRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.friend_icon_civ);
            name = itemView.findViewById(R.id.friend_name_tv);
            count = itemView.findViewById(R.id.group_chat_num);
        }

        public void bind(final int position) {
            final Team team = mDatas.get(position);
            name.setText(team.getName());
            GlideUtils.load(icon, team.getIcon(), 0, 0, false, null);
            if (mCount.containsKey(team.getId()) && mCount.get(team.getId()) != 0) {
                count.setText(mCount.get(team.getId()));
                count.setVisibility(View.VISIBLE);
            } else {
                count.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PatientTeamChatActivity.startTeamChat(mContext, team.getId());
                    //ImClient.startTeamChart(mContext, team.getId(), null);
                }
            });
            /*if (!Const.CHECK_AND_ACCEP) {
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewGroupChatActivity.startForUpdate(mContext, team.getId());
                    }
                });
            }*/
        }
    }

    private Map<String, Integer> mCount = new HashMap<>();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUnReadCountEvent(RecentContactEvent event) {
        Map<String, Integer> map = new HashMap<>();
        for (RecentContact contact : event.getRecentContacts()) {
            if (contact.getSessionType() == SessionTypeEnum.Team) {
                for (Team team : mDatas) {
                    if (team.getId().equals(contact.getContactId())) {
                        map.put(team.getId(), contact.getUnreadCount());
                    }
                }
            }
        }
        mCount = map;
    }

    public void unRegister() {
        EventBus.getDefault().unregister(this);
    }
}
