package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.utils.CalculateTimeUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.constant.GenderEnum;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.text.ParseException;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：消息列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class FriendMsgRecyclrViewAdapter extends BaseEmptyAdapter<RecentContact> {


    public FriendMsgRecyclrViewAdapter(Context context, List<RecentContact> data) {
        super(data, context);
    }

    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.patient_msg_item, parent, false);
        return new FriendMsgRecyclrViewAdapter.ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FriendMsgRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView friendNameTv, lastMsg, lastChatTime, chatNum, friendAgeTv;
        public ImageView sexImage;
        public CircleImageView friendIconCiv;

        public ViewHolder(View itemView) {
            super(itemView);
            lastMsg = (TextView) itemView.findViewById(R.id.last_msg);
            lastChatTime = (TextView) itemView.findViewById(R.id.last_chat_time);
            chatNum = (TextView) itemView.findViewById(R.id.chat_num);
            friendNameTv = (TextView) itemView.findViewById(R.id.friend_name_tv);
            friendAgeTv = (TextView) itemView.findViewById(R.id.friend_age_tv);
            sexImage = (ImageView) itemView.findViewById(R.id.friend_sex_iv);
            friendIconCiv = (CircleImageView) itemView.findViewById(R.id.friend_icon_civ);
        }

        public void bind(final int position) {
            final RecentContact bean = mDatas.get(position);
            if (bean.getSessionType() == SessionTypeEnum.Team) {
                Team team = ImClient.getTeamProvider().getTeamById(bean.getContactId());
                sexImage.setVisibility(View.GONE);
                if (team != null) {
                    GlideUtils.load(friendIconCiv, team.getIcon(), 0, 0, false, null);
                    friendNameTv.setText(team.getName());
                }
                friendAgeTv.setText("");
            } else {
                sexImage.setVisibility(View.VISIBLE);
                NimUserInfo userInfo = (NimUserInfo) ImClient.getUserInfoProvider().getUserInfo(bean.getContactId());
                if (userInfo != null) {
                    GlideUtils.load(friendIconCiv, userInfo.getAvatar(), 0, 0, false, null);
                    friendNameTv.setText(userInfo.getName());
                    if (userInfo.getBirthday() != null && !"".equals(userInfo.getBirthday())) {
                        try {
                            friendAgeTv.setText(CalculateTimeUtils.getAgeByBirth(userInfo.getBirthday()) + "岁");
                        } catch (ParseException e) {
                            e.printStackTrace();
                            friendAgeTv.setText("");
                        }
                    }else{
                        friendAgeTv.setText("");
                    }
                    if (userInfo.getGenderEnum() == GenderEnum.MALE) {
                        sexImage.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.patient_cicle_blue));
                    } else if (userInfo.getGenderEnum() == GenderEnum.FEMALE) {
                        sexImage.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.patient_cicle_green));
                    } else {
                        sexImage.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.patient_cicle_green));
                    }

                }
            }
            lastMsg.setText(bean.getContent());
            lastChatTime.setText(CalculateTimeUtils.format(bean.getTime()));
            if (bean.getUnreadCount() == 0) {
                chatNum.setVisibility(View.GONE);
            } else {
                chatNum.setVisibility(View.VISIBLE);
                chatNum.setText(String.valueOf(bean.getUnreadCount()));
            }
            chatNum.setText(bean.getUnreadCount() == 0 ? "" : String.valueOf(bean.getUnreadCount()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    if (mDatas.get(position).getSessionType() == SessionTypeEnum.Team) {
                        chatNum.setVisibility(View.GONE);
                        ImClient.startTeamChart(mContext, mDatas.get(position).getContactId(), null);
                    } else if (mDatas.get(position).getSessionType() == SessionTypeEnum.P2P) {
                        chatNum.setVisibility(View.GONE);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(ImConstants.CHATTING, true);
                        ImClient.startConversation(mContext, mDatas.get(position).getContactId(), bundle);
//                        ImClient.startConversation(mContext, mDatas.get(position).getContactId(), null);
                    }
                    NIMClient.getService(MsgService.class).clearUnreadCount(mDatas.get(position).getContactId(), mDatas.get(position).getSessionType());
                }
            });
        }
    }
}
