package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.consulting_arrange.ConsultingArrangeActivity;
import com.keydom.ih_doctor.activity.consulting_arrange.ConsultingChangeActivity;
import com.keydom.ih_doctor.bean.ConsultingBean;
import com.keydom.ih_doctor.net.ScheduingService;
import com.keydom.ih_doctor.utils.CalculateTimeUtils;
import com.keydom.ih_doctor.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：门诊排班适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class ConsultingRecyclrViewAdapter extends BaseEmptyAdapter<ConsultingBean> {


    public ConsultingRecyclrViewAdapter(List<ConsultingBean> mDatas, Context context) {
        super(mDatas, context);
    }

    /**
     * 设置开关关闭
     *
     * @param holder
     */
    private void setStop(ViewHolder holder) {
        holder.actionOpen.setVisibility(View.GONE);
        holder.actionClose.setVisibility(View.VISIBLE);
        holder.actionBg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.consulting_close_bg));
        holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.line_color));
        holder.stopTv.setVisibility(View.VISIBLE);
    }

    /**
     * 设置开关开启
     *
     * @param holder
     */
    private void setOpen(ViewHolder holder) {
        holder.actionOpen.setVisibility(View.VISIBLE);
        holder.actionClose.setVisibility(View.GONE);
        holder.actionBg.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.consulting_open_bg));
        holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        holder.stopTv.setVisibility(View.INVISIBLE);
    }


    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.consulting_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ConsultingRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTv, timeTv, stopTv;
        public ImageView itemIcon, actionBg, actionOpen, actionClose;
        public RelativeLayout actionRl;

        public ViewHolder(View itemView) {
            super(itemView);
            dateTv = (TextView) itemView.findViewById(R.id.consulting_item_date_tv);
            timeTv = (TextView) itemView.findViewById(R.id.consulting_item_time_tv);
            stopTv = (TextView) itemView.findViewById(R.id.consulting_stop_tip_tv);
            itemIcon = (ImageView) itemView.findViewById(R.id.consulting_item_icon);
            actionBg = (ImageView) itemView.findViewById(R.id.consulting_action_bg);
            actionOpen = (ImageView) itemView.findViewById(R.id.consulting_open);
            actionClose = (ImageView) itemView.findViewById(R.id.consulting_close);
            actionRl = (RelativeLayout) itemView.findViewById(R.id.action_rl);

        }

        public void bind(final int position) {


            Date date = CalculateTimeUtils.getDate(mDatas.get(position).getDate());
            dateTv.setText(CalculateTimeUtils.getUiDate(date) + "  " + CalculateTimeUtils.getWeekOfDate(date));
            timeTv.setText(CalculateTimeUtils.getRoundTime(mDatas.get(position).getBeginPointTime()) + "-" + CalculateTimeUtils.getRoundTime(mDatas.get(position).getEndPointTime()));
            if (mDatas.get(position).getIsStop() == 0) {
                setOpen(this);
            } else {
                setStop(this);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConsultingChangeActivity.start(mContext, ConsultingChangeActivity.CONSULTING_UPDATE, mDatas.get(position), null);
                }
            });
            actionRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", mDatas.get(position).getId());
                    map.put("date", mDatas.get(position).getDate());
                    map.put("beginPointTime", mDatas.get(position).getBeginPointTime());
                    map.put("endPointTime", mDatas.get(position).getEndPointTime());
                    map.put("isStop", (mDatas.get(position).getIsStop() != null && mDatas.get(position).getIsStop() != 0) ? 0 : 1);
                    ((ConsultingArrangeActivity) mContext).getController().showLoading();
                    ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ScheduingService.class).updateConsulting(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>() {
                        @Override
                        public void requestComplete(@Nullable String resData) {
                            ((ConsultingArrangeActivity) mContext).getController().hideLoading();
                            if (actionOpen.getVisibility() == View.GONE) {
                                setOpen(ViewHolder.this);
                                mDatas.get(position).setIsStop(0);
                            } else {
                                setStop(ViewHolder.this);
                                mDatas.get(position).setIsStop(1);
                            }
                        }

                        @Override
                        public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                            ((ConsultingArrangeActivity) mContext).getController().hideLoading();
                            ToastUtil.shortToast(mContext, "修改失败");
                            return super.requestError(exception, code, msg);
                        }
                    });

                }
            });
        }
    }
}
