package com.keydom.ih_doctor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.bean.NurseBean;
import com.keydom.ih_doctor.constant.Const;

import java.io.Serializable;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：选择护士列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class NurseSelectRecyclrViewAdapter extends BaseEmptyAdapter<NurseBean> {


    public NurseSelectRecyclrViewAdapter(Context context, List<NurseBean> data) {
        super(data, context);

    }


    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_doctor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NurseSelectRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView doctorIcon;
        public TextView doctorName, doctorJob, selectTv;

        public ViewHolder(View itemView) {
            super(itemView);
            doctorIcon = itemView.findViewById(R.id.doctor_icon);
            doctorName = itemView.findViewById(R.id.doctor_name);
            doctorJob = itemView.findViewById(R.id.doctor_job);
            selectTv = itemView.findViewById(R.id.select_tv);

        }

        public void bind(final int position) {
            final NurseBean bean = mDatas.get(position);
            GlideUtils.load(doctorIcon, Const.IMAGE_HOST + bean.getImage(), 0, 0, false, null);
            doctorJob.setText(bean.getJobTitleName());
            doctorName.setText(bean.getName());
            selectTv.setText("选择");
            selectTv.setBackground(mContext.getResources().getDrawable(R.drawable.doctor_unselect_bg));
            selectTv.setTextColor(mContext.getResources().getColor(R.color.white));
            selectTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(Const.DATA, (Serializable) bean);
                    ((Activity) mContext).setResult(Activity.RESULT_OK, intent);
                    ((Activity) mContext).finish();
                }
            });
        }
    }
}
