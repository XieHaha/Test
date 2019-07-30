package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.nurse_service.CommonNurseServiceOrderDetailActivity;
import com.keydom.ih_doctor.activity.nurse_service.CommonNurseServiceWorkingOrderDetailActivity;
import com.keydom.ih_doctor.activity.nurse_service.HeadNurseServiceOrderDetailActivity;
import com.keydom.ih_doctor.bean.NurseServiceListBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.utils.CalculateTimeUtils;

import java.util.List;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：护理服务列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class NurseServiceOrderRecyclrViewAdapter extends BaseEmptyAdapter<NurseServiceListBean> {


    /**
     * 和APPLICATION中的roleID一起判断显示的item类型
     */
    private TypeEnum type;

    public NurseServiceOrderRecyclrViewAdapter(Context context, TypeEnum type, List<NurseServiceListBean> data) {
        super(data, context);
        this.type = type;

    }


    @Override
    public RecyclerView.ViewHolder createMyViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.nurse_service_order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NurseServiceOrderRecyclrViewAdapter.ViewHolder) holder).bind(position);
    }

    private String getVisitTimeStr(String time) {
        String[] times = null;
        if (time != null || !"".equals(time)) {
            times = time.split("-");
        }
        return ((times != null && times.length > 0) ? times[0] : "");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView serviceName, nurseServiceDistanceTimeTv, orderNumber, serviceDept, serviceAddress, serviceTime, nurseServiceDistanceTimeTvTip;


        public ViewHolder(View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.service_name);
            nurseServiceDistanceTimeTv = itemView.findViewById(R.id.nurse_service_distance_time_tv);
            nurseServiceDistanceTimeTvTip = itemView.findViewById(R.id.nurse_service_distance_time_tv_tip);
            orderNumber = itemView.findViewById(R.id.order_number);
            serviceDept = itemView.findViewById(R.id.service_dept);
            serviceAddress = itemView.findViewById(R.id.service_address);
            serviceTime = itemView.findViewById(R.id.service_time);

        }

        public void bind(final int position) {
            final NurseServiceListBean bean = mDatas.get(position);
            String serverNameStr = "";
            if (bean.getSeverName() != null) {
                for (int i = 0; i < bean.getSeverName().size(); i++) {
                    if (i == 0) {
                        serverNameStr += bean.getSeverName().get(i);
                    } else {
                        serverNameStr += "、" + bean.getSeverName().get(i);
                    }

                }
            }
            serviceName.setText(serverNameStr);
            nurseServiceDistanceTimeTv.setVisibility(View.GONE);
            /**
             * 接口状态有问题，演示临时增加判断
             */
            if (bean.getState() == Const.NURSING_SERVICE_ORDER_STATE_ON_SERVICE) {//服务中
                if (type == TypeEnum.HEAD_NURSE_FRAGMENT_ALREADY_RECEIVE_ORDER) {
                    nurseServiceDistanceTimeTvTip.setVisibility(View.GONE);
                    nurseServiceDistanceTimeTv.setBackground(mContext.getResources().getDrawable(R.drawable.nurse_service_out_time_tv_bg));
                    nurseServiceDistanceTimeTv.setTextColor(mContext.getResources().getColor(R.color.servcie_out_time));
                    nurseServiceDistanceTimeTv.setVisibility(View.VISIBLE);
                    nurseServiceDistanceTimeTv.setText("已接单");
                } else {
                    nurseServiceDistanceTimeTvTip.setVisibility(View.GONE);
                    nurseServiceDistanceTimeTv.setVisibility(View.VISIBLE);
                    nurseServiceDistanceTimeTv.setText("服务中");
                    nurseServiceDistanceTimeTv.setTextColor(mContext.getResources().getColor(R.color.font_green));
                    nurseServiceDistanceTimeTv.setBackground(mContext.getResources().getDrawable(R.color.white));
                }
            } else if (bean.getState() == Const.NURSING_SERVICE_ORDER_STATE_FINISH) {
//                nurseServiceDistanceTimeTvTip.setVisibility(View.GONE);
//                nurseServiceDistanceTimeTv.setVisibility(View.GONE);
                nurseServiceDistanceTimeTvTip.setVisibility(View.GONE);
                nurseServiceDistanceTimeTv.setVisibility(View.VISIBLE);
                nurseServiceDistanceTimeTv.setText("已完成");
                nurseServiceDistanceTimeTv.setTextColor(mContext.getResources().getColor(R.color.font_green));
                nurseServiceDistanceTimeTv.setBackground(mContext.getResources().getDrawable(R.color.white));
            } else {
                if (type == TypeEnum.COMMON_NURSE_FRAGMNET_WORKING_ORDER) {
                    nurseServiceDistanceTimeTv.setVisibility(View.VISIBLE);
                    nurseServiceDistanceTimeTvTip.setVisibility(View.GONE);
                    nurseServiceDistanceTimeTv.setText("等待第" + CommonUtils.numberToChinese(bean.getServiceFrequency()) + "次服务");
                    nurseServiceDistanceTimeTv.setTextColor(mContext.getResources().getColor(R.color.position_bg_color));
                    nurseServiceDistanceTimeTv.setBackground(mContext.getResources().getDrawable(R.color.white));
                } else if (type == TypeEnum.COMMON_NURSE_FRAGMENT_FINISH_ORDER) {
                    nurseServiceDistanceTimeTvTip.setVisibility(View.GONE);
                    nurseServiceDistanceTimeTv.setVisibility(View.GONE);
                } else if (type == TypeEnum.HEAD_NURSE_FRAGMENT_ALREADY_RECEIVE_ORDER) {
                    nurseServiceDistanceTimeTvTip.setVisibility(View.GONE);
                    nurseServiceDistanceTimeTv.setBackground(mContext.getResources().getDrawable(R.drawable.nurse_service_out_time_tv_bg));
                    nurseServiceDistanceTimeTv.setTextColor(mContext.getResources().getColor(R.color.servcie_out_time));
                    nurseServiceDistanceTimeTv.setVisibility(View.VISIBLE);
                    nurseServiceDistanceTimeTv.setText("已接单");
                } else {
                    nurseServiceDistanceTimeTvTip.setVisibility(View.VISIBLE);
                    String timeDistance = CalculateTimeUtils.TimeDistance(CalculateTimeUtils.getYMDTime(bean.getVisitTime()) + " " + getVisitTimeStr(bean.getVisitPeriod()));
                    if (timeDistance.contains("超时")) {
                        nurseServiceDistanceTimeTvTip.setVisibility(View.GONE);
                        nurseServiceDistanceTimeTv.setBackground(mContext.getResources().getDrawable(R.drawable.nurse_service_out_time_tv_bg));
                        nurseServiceDistanceTimeTv.setTextColor(mContext.getResources().getColor(R.color.servcie_out_time));
                    } else {
                        nurseServiceDistanceTimeTv.setBackground(mContext.getResources().getDrawable(R.drawable.nurse_service_time_tv_bg));
                        nurseServiceDistanceTimeTv.setTextColor(mContext.getResources().getColor(R.color.servcie_waite));
                    }
                    nurseServiceDistanceTimeTv.setVisibility(View.VISIBLE);
                    nurseServiceDistanceTimeTv.setText(timeDistance);
                }

            }
            orderNumber.setText(bean.getOrderNumber());
            serviceDept.setText(bean.getDeptName());
            serviceAddress.setText(bean.getAddress());
            serviceTime.setText(CalculateTimeUtils.getYMDTime(bean.getVisitTime()) + " " + bean.getVisitPeriod());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (type == TypeEnum.HEAD_NURSE_FRAGMENT_ALREADY_RECEIVE_ORDER || type == TypeEnum.HEAD_NURSE_FRAGMENT_RECEIVE_ORDER) {//护士长
                        if (bean.getDeptName() != null && !"".equals(bean.getDeptName())) {
                            HeadNurseServiceOrderDetailActivity.start(mContext, true, type, String.valueOf(bean.getId()));
                        } else {
                            HeadNurseServiceOrderDetailActivity.start(mContext, false, type, String.valueOf(bean.getId()));
                        }

                    } else if (type == TypeEnum.COMMON_NURSE_FRAGMENT_RECEIVE_ORDER) {//护士待接收
                        CommonNurseServiceOrderDetailActivity.start(mContext, String.valueOf(bean.getId()));
                    } else if (type == TypeEnum.COMMON_NURSE_FRAGMNET_WORKING_ORDER || type == TypeEnum.COMMON_NURSE_FRAGMENT_FINISH_ORDER) {//护士已接收
                        if (bean.getState() == Const.NURSING_SERVICE_ORDER_STATE_ON_SERVICE || bean.getState() == Const.NURSING_SERVICE_ORDER_STATE_FINISH) {
                            CommonNurseServiceWorkingOrderDetailActivity.start(mContext, type, String.valueOf(bean.getId()));
                        } else {
                            CommonNurseServiceOrderDetailActivity.start(mContext, String.valueOf(bean.getId()));
                        }

                    }

                }
            });
        }
    }
}
