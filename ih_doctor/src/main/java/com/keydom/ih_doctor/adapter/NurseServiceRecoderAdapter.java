package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.nurse_service.CommonNurseServiceOrderDetailActivity;
import com.keydom.ih_doctor.activity.nurse_service.CommonNurseServiceWorkingOrderDetailActivity;
import com.keydom.ih_doctor.bean.NurseServiceRecoderBean;
import com.keydom.ih_doctor.net.NurseServiceApiService;
import com.keydom.ih_doctor.utils.CalculateTimeUtils;
import com.keydom.ih_doctor.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.kentra.yxyz.adapter
 * @Description：护理服务记录列表适配器
 * @Author：song
 * @Date：18/11/6 下午6:52
 * 修改人：xusong
 * 修改时间：18/11/6 下午6:52
 */
public class NurseServiceRecoderAdapter extends RecyclerView.Adapter<NurseServiceRecoderAdapter.ViewHolder> {


    private Context context;
    private List<NurseServiceRecoderBean> data;

    public NurseServiceRecoderAdapter(Context context, List<NurseServiceRecoderBean> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nurse_service_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        NurseServiceRecoderBean bean = data.get(position);
        holder.receiveUserName.setText(bean.getAcceptName());
        holder.receiveTime.setText(bean.getAcceptTime());
        holder.receiveDept.setText(bean.getAcceptDeptName());
        holder.sendUserName.setText(bean.getServiceProject());
        holder.serviceNurseName.setText(bean.getServerNurseName());
        holder.visitTime.setText(bean.getConfirmTime());
        holder.serviceStartTime.setText(bean.getServiceBeginTime());
        holder.serviceEndTime.setText(bean.getServiceEndTime());
        holder.nextTime.setText((bean.getNextAppointTime() == null || "".equals(bean.getNextAppointTime()) ? "" : CalculateTimeUtils.requestDate(CalculateTimeUtils.getNextNurseServiceTime(bean.getNextAppointTime())) + " " + bean.getNextAppointPeriod()));
        holder.consume.setText(bean.getEquipment());
        holder.remark.setText(bean.getRemark());

        holder.baseInfoTipTv.setText("第" + CommonUtils.numberToChinese(bean.getServiceFrequency()) + "次服务");
        holder.subOrderBox.removeAllViews();
        if (bean.getSubOrderDetails() != null && bean.getSubOrderDetails().size() > 0) {
            for (int i = 0; i < bean.getSubOrderDetails().size(); i++) {
                holder.subOrderBox.addView(getSubOrderView(holder, bean.getSubOrderDetails().get(i), i + 1));
            }
        }
        holder.baseInfoTipTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.serviceOrderDetailLl.getVisibility() == View.VISIBLE) {
                    holder.serviceOrderDetailLl.setVisibility(View.GONE);
                    Drawable rightimg = context.getResources().getDrawable(R.mipmap.arrow_bottom_grey);
                    Drawable leftimg = context.getResources().getDrawable(R.mipmap.point_green);
                    rightimg.setBounds(0, 0, rightimg.getMinimumWidth(), rightimg.getMinimumHeight());
                    leftimg.setBounds(0, 0, leftimg.getMinimumWidth(), leftimg.getMinimumHeight());
                    holder.baseInfoTipTv.setCompoundDrawables(leftimg, null, rightimg, null);
                } else {
                    holder.serviceOrderDetailLl.setVisibility(View.VISIBLE);
                    Drawable rightimg = context.getResources().getDrawable(R.mipmap.arrow_top);
                    Drawable leftimg = context.getResources().getDrawable(R.mipmap.point_green);
                    rightimg.setBounds(0, 0, rightimg.getMinimumWidth(), rightimg.getMinimumHeight());
                    leftimg.setBounds(0, 0, leftimg.getMinimumWidth(), leftimg.getMinimumHeight());
                    holder.baseInfoTipTv.setCompoundDrawables(leftimg, null, rightimg, null);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView baseInfoTipTv;
        public LinearLayout serviceOrderDetailLl, subOrderBox;
        public TextView receiveUserName, receiveTime, receiveDept, sendUserName, serviceNurseName, visitTime, serviceStartTime, serviceEndTime, nextTime, consume, remark;

        public ViewHolder(View itemView) {
            super(itemView);
            baseInfoTipTv = (TextView) itemView.findViewById(R.id.base_info_tip_tv);
            serviceOrderDetailLl = (LinearLayout) itemView.findViewById(R.id.service_order_detail_ll);
            subOrderBox = (LinearLayout) itemView.findViewById(R.id.sub_order_box);
            receiveUserName = (TextView) itemView.findViewById(R.id.receive_user_name);
            receiveTime = (TextView) itemView.findViewById(R.id.receive_time);
            receiveDept = (TextView) itemView.findViewById(R.id.receive_dept);
            sendUserName = (TextView) itemView.findViewById(R.id.send_user_name);
            serviceNurseName = (TextView) itemView.findViewById(R.id.service_nurse_name);
            visitTime = (TextView) itemView.findViewById(R.id.visit_time);
            serviceStartTime = (TextView) itemView.findViewById(R.id.service_start_time);
            serviceEndTime = (TextView) itemView.findViewById(R.id.service_end_time);
            nextTime = (TextView) itemView.findViewById(R.id.next_time);
            consume = (TextView) itemView.findViewById(R.id.consume);
            remark = (TextView) itemView.findViewById(R.id.remark);
        }
    }


    private View getSubOrderView(final ViewHolder holder, NurseServiceRecoderBean.SubOrder bean, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.nurse_service_sub_order_layout, null, true);
        TextView subOrderAmonutTv = view.findViewById(R.id.service_item_amount);
        TextView subOrderNumber = view.findViewById(R.id.sub_order_number);
        TextView subServiceItem = view.findViewById(R.id.sub_service_item);
        TextView serviceItemFee = view.findViewById(R.id.service_item_fee);
        TextView subOrderTv = view.findViewById(R.id.sub_order_tv);
        TextView serviceItemCreateTime = view.findViewById(R.id.service_item_create_time);
        TextView serviceItemCreateUser = view.findViewById(R.id.service_item_create_user);
        TextView serviceItemPayStatus = view.findViewById(R.id.service_item_pay_status);
        ImageView subOrderDelete = view.findViewById(R.id.sub_order_delete);
        subOrderAmonutTv.setText(bean.getFrequency() + "次");
        subOrderNumber.setText(bean.getSubOrderNumber());
        subServiceItem.setText(bean.getServerProject());
        serviceItemFee.setText("¥" + String.valueOf(bean.getFee()) + "元");
        serviceItemPayStatus.setText((bean.getPay() == NurseServiceRecoderBean.ALREADY_PAY) ? "已支付" : "未支付");
        serviceItemCreateTime.setText(bean.getCreateTime());
        serviceItemCreateUser.setText(bean.getNurseName());
        subOrderTv.setText("新增子订单" + position);
        if (bean.getPay() == NurseServiceRecoderBean.ALREADY_PAY) {
            subOrderDelete.setVisibility(View.GONE);
        } else {
            subOrderDelete.setVisibility(View.VISIBLE);
        }
        subOrderDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new GeneralDialog(context, "是否删除子订单?", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        if (context instanceof CommonNurseServiceWorkingOrderDetailActivity) {
                            ((CommonNurseServiceWorkingOrderDetailActivity) context).getController().showLoading();
                        } else if (context instanceof CommonNurseServiceOrderDetailActivity) {
                            ((CommonNurseServiceOrderDetailActivity) context).getController().showLoading();
                        }

                        Map<String, Object> map = new HashMap<>();
                        map.put("subOrderNumber", bean.getSubOrderNumber());
                        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).nurseDeleteProjectToSubOrder(map), new HttpSubscriber<String>((context instanceof CommonNurseServiceWorkingOrderDetailActivity) ? ((CommonNurseServiceWorkingOrderDetailActivity) context) : ((CommonNurseServiceOrderDetailActivity) context), (context instanceof CommonNurseServiceWorkingOrderDetailActivity) ? ((CommonNurseServiceWorkingOrderDetailActivity) context).getController().getDisposable() : ((CommonNurseServiceOrderDetailActivity) context).getController().getDisposable(), false) {
                            @Override
                            public void requestComplete(@Nullable String data) {
                                if (context instanceof CommonNurseServiceWorkingOrderDetailActivity) {
                                    holder.subOrderBox.removeView(view);
                                    ((CommonNurseServiceWorkingOrderDetailActivity) context).getController().getNurseServiceOrderDetail();
                                    ((CommonNurseServiceWorkingOrderDetailActivity) context).updateOrder();
                                    ((CommonNurseServiceWorkingOrderDetailActivity) context).getController().hideLoading();
                                } else if (context instanceof CommonNurseServiceOrderDetailActivity) {
                                    holder.subOrderBox.removeView(view);
                                    ((CommonNurseServiceOrderDetailActivity) context).getController().getNurseServiceOrderDetail();
                                    ((CommonNurseServiceOrderDetailActivity) context).updateOrder();
                                    ((CommonNurseServiceOrderDetailActivity) context).getController().hideLoading();
                                }


                            }

                            @Override
                            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                                if (context instanceof CommonNurseServiceWorkingOrderDetailActivity) {
                                    ((CommonNurseServiceWorkingOrderDetailActivity) context).getController().hideLoading();
                                } else if (context instanceof CommonNurseServiceOrderDetailActivity) {
                                    ((CommonNurseServiceOrderDetailActivity) context).getController().hideLoading();
                                }

                                ToastUtil.shortToast(context, msg);
                                return super.requestError(exception, code, msg);
                            }
                        });
                    }
                }).show();

            }
        });
        return view;
    }
}
