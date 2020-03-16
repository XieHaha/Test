package com.keydom.mianren.ih_doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.consulting_arrange.ArrangeCircleActivity;
import com.keydom.mianren.ih_doctor.activity.consulting_arrange.ConsultingChangeActivity;
import com.keydom.mianren.ih_doctor.bean.ConsultingBean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.ScheduingService;
import com.keydom.mianren.ih_doctor.utils.CalculateTimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：门诊排班－循环排班适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class ConsultingCircleWithStopAdapter extends BaseEmptyAdapter<ConsultingBean> {


    public ConsultingCircleWithStopAdapter(List<ConsultingBean> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.consulting_circle_stop_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ConsultingCircleWithStopAdapter.ViewHolder) holder).bind(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTv, timeTv;
        public ImageView deleteImg, itemIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            dateTv = (TextView) itemView.findViewById(R.id.consulting_item_date_tv);
            timeTv = (TextView) itemView.findViewById(R.id.consulting_item_time_tv);
            deleteImg = (ImageView) itemView.findViewById(R.id.consulting_delete);
            itemIcon = (ImageView) itemView.findViewById(R.id.consulting_item_icon);

        }

        public void bind(final int position) {


            if (((ArrangeCircleActivity) mContext).getType() == ArrangeCircleActivity.CONSULTING_STOP) {
                dateTv.setText("停诊日期:");
                timeTv.setText(mDatas.get(position).getBeginDate() + "~" + (mDatas.get(position).getEndDate() == null ? "永久" : mDatas.get(position).getEndDate()));
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.point_red));
            } else {
                dateTv.setText(CalculateTimeUtils.getWeek(mDatas.get(position).getWeek()));
                timeTv.setText(CalculateTimeUtils.getRoundTime(mDatas.get(position).getBeginPointTime()) + "-" + CalculateTimeUtils.getRoundTime(mDatas.get(position).getEndPointTime()));
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.point_green));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    if (((ArrangeCircleActivity) mContext).getType() == ArrangeCircleActivity.CONSULTING_STOP) {
                        ConsultingChangeActivity.start(mContext, ConsultingChangeActivity.CONSULTING_STOP_UPDATE, mDatas.get(position), null);
                    } else {
                        ConsultingChangeActivity.start(mContext, ConsultingChangeActivity.CONSULTING_CIRCLE_UPDATE, mDatas.get(position), null);

                    }
                }
            });
            deleteImg.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {


                    if (((ArrangeCircleActivity) mContext).getType() == ArrangeCircleActivity.CONSULTING_STOP) {

                        new GeneralDialog(mContext, "确定删除该停诊记录?", new GeneralDialog.OnCloseListener() {
                            @Override
                            public void onCommit() {
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("id", mDatas.get(position).getId());
                                ((ArrangeCircleActivity) mContext).getController().showLoading();
                                ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ScheduingService.class).deleteStopConsulting(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>() {
                                    @Override
                                    public void requestComplete(@Nullable String data) {
                                        ((ArrangeCircleActivity) mContext).getController().hideLoading();
                                        mDatas.remove(mDatas.get(position));
                                        notifyDataSetChanged();
                                        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.CONSULTING_UPDATE).build());
                                        ToastUtil.showMessage(mContext, "删除成功");
                                    }

                                    @Override
                                    public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                                        ((ArrangeCircleActivity) mContext).getController().hideLoading();
                                        ToastUtil.showMessage(mContext, "删除失败");
                                        return super.requestError(exception, code, msg);
                                    }
                                });
                            }
                        }).show();

                    } else {
                        new GeneralDialog(mContext, "确定删除该条循环排班记录?", new GeneralDialog.OnCloseListener() {
                            @Override
                            public void onCommit() {
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("id", mDatas.get(position).getId());
                                ((ArrangeCircleActivity) mContext).getController().showLoading();
                                ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(ScheduingService.class).deleteLoopConsulting(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>() {
                                    @Override
                                    public void requestComplete(@Nullable String data) {
                                        ((ArrangeCircleActivity) mContext).getController().hideLoading();
                                        mDatas.remove(mDatas.get(position));
                                        notifyDataSetChanged();
                                        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.CONSULTING_UPDATE).build());
                                    }

                                    @Override
                                    public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                                        ((ArrangeCircleActivity) mContext).getController().hideLoading();
                                        ToastUtil.showMessage(mContext, "删除失败");
                                        return super.requestError(exception, code, msg);
                                    }
                                });
                            }
                        }).show();

                    }


                }
            });
        }
    }
}
