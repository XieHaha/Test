package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.ICD10Bean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：ICD－10列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class ICD10ListAdapter extends BaseEmptyAdapter<ICD10Bean> {


    public ICD10ListAdapter(Context context, List<ICD10Bean> data) {
        super(data, context);
    }

    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.icd_10_list_item, parent, false);
        return new ICD10ListAdapter.ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ICD10ListAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView icdNameTv, icdNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            icdNameTv = itemView.findViewById(R.id.icd_name_tv);
            icdNumber = itemView.findViewById(R.id.icd_number);

        }

        public void bind(final int position) {
            icdNameTv.setText(mDatas.get(position).getName());
            icdNumber.setText(mDatas.get(position).getCode());
            itemView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.GET_ICD_10_VALUE).setData(mDatas.get(position).getName()).build());
                }
            });
        }
    }
}
