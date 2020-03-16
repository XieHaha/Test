package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.GroupInfoBean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.view.BottomGroupCutDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：切换团队适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class GroupCutDialogRecyclrViewAdapter extends RecyclerView.Adapter<GroupCutDialogRecyclrViewAdapter.ViewHolder> {


    private Context context;
    private List<GroupInfoBean> data;
    private BottomGroupCutDialog dialog;

    public GroupCutDialogRecyclrViewAdapter(Context context, List<GroupInfoBean> data,BottomGroupCutDialog dialog) {
        this.context = context;
        this.data = data;
        this.dialog=dialog;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_group_cut_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.groupName.setText(data.get(position).getGroupName());
        if (data.get(position).getId() == SharePreferenceManager.getGroup()) {
            holder.groupName.setTextColor(context.getResources().getColor(R.color.font_select));
            holder.groupTag.setVisibility(View.VISIBLE);
            holder.groupIcon.setBackground(context.getResources().getDrawable(R.mipmap.point_green));
            holder.groupSelect.setVisibility(View.VISIBLE);
        } else {
            holder.groupName.setTextColor(context.getResources().getColor(R.color.fontColorPrimary));
            holder.groupTag.setVisibility(View.GONE);
            holder.groupIcon.setBackground(context.getResources().getDrawable(R.mipmap.point_blue));
            holder.groupSelect.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                SharePreferenceManager.setGroup(data.get(position).getId());
                notifyDataSetChanged();
                EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.GROUP_CUT).build());
                dialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView groupIcon, groupSelect;
        public TextView groupName, groupTag;

        public ViewHolder(View itemView) {
            super(itemView);
            groupIcon = itemView.findViewById(R.id.group_icon);
            groupSelect = itemView.findViewById(R.id.group_select);
            groupName = itemView.findViewById(R.id.patient_name);
            groupTag = itemView.findViewById(R.id.group_tag);

        }
    }
}
