package com.keydom.ih_doctor.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
import com.keydom.ih_doctor.bean.NurseSubOrderBean;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.net.NurseServiceApiService;
import com.keydom.ih_doctor.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NurseChildRecordAdapter extends BaseQuickAdapter<NurseSubOrderBean, BaseViewHolder> {
    private Context context;
    private TypeEnum mType;
    public NurseChildRecordAdapter(Context context, @Nullable List<NurseSubOrderBean> data, TypeEnum type) {
        super(R.layout.nurse_service_sub_order_layout, data);
        this.context=context;
        mType=type;
    }

    @Override
    protected void convert(BaseViewHolder helper, NurseSubOrderBean item) {
        helper
                .setText(R.id.service_item_amount ,item.getFrequency() + "次")
                .setText(R.id.sub_order_number,item.getSubOrderNumber())
                .setText(R.id.sub_service_item,item.getServerProject())
                .setText(R.id.service_item_fee,"¥" + String.valueOf(item.getFee()) + "元")
                .setText(R.id.sub_order_tv,"子订单(" + CommonUtils.numberToChinese(helper.getPosition()+1)+")")
                .setText(R.id.service_item_create_time,item.getCreateTime())
                .setText(R.id.service_item_create_user,item.getNurseName())
                .setText(R.id.service_item_pay_status,item.getPay() == NurseServiceRecoderBean.ALREADY_PAY ? "已支付" : "未支付");
        TextView subOrderDelete=helper.getView(R.id.child_record_delete_tv);
        if(mType == TypeEnum.COMMON_NURSE_FRAGMENT_FINISH_ORDER){
            subOrderDelete.setVisibility(View.GONE);
        }else {
            subOrderDelete.setVisibility(View.VISIBLE);
            subOrderDelete.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    if (item.getPay() == NurseServiceRecoderBean.ALREADY_PAY) {
                        ToastUtil.shortToast(context,"订单已支付，不能删除");
                    } else {
                        new GeneralDialog(context, "是否删除子订单?", new GeneralDialog.OnCloseListener() {
                            @Override
                            public void onCommit() {
                                if (context instanceof CommonNurseServiceWorkingOrderDetailActivity) {
                                    ((CommonNurseServiceWorkingOrderDetailActivity) context).getController().showLoading();
                                } else if (context instanceof CommonNurseServiceOrderDetailActivity) {
                                    ((CommonNurseServiceOrderDetailActivity) context).getController().showLoading();
                                }

                                Map<String, Object> map = new HashMap<>();
                                map.put("subOrderNumber", item.getSubOrderNumber());
                                ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(NurseServiceApiService.class).nurseDeleteProjectToSubOrder(map), new HttpSubscriber<String>((context instanceof CommonNurseServiceWorkingOrderDetailActivity) ? ((CommonNurseServiceWorkingOrderDetailActivity) context) : ((CommonNurseServiceOrderDetailActivity) context), (context instanceof CommonNurseServiceWorkingOrderDetailActivity) ? ((CommonNurseServiceWorkingOrderDetailActivity) context).getController().getDisposable() : ((CommonNurseServiceOrderDetailActivity) context).getController().getDisposable(), false) {
                                    @Override
                                    public void requestComplete(@Nullable String data) {
                                        if (context instanceof CommonNurseServiceWorkingOrderDetailActivity) {
                                            ((CommonNurseServiceWorkingOrderDetailActivity) context).getController().getNurseServiceOrderDetail();
                                            ((CommonNurseServiceWorkingOrderDetailActivity) context).updateOrder();
                                            ((CommonNurseServiceWorkingOrderDetailActivity) context).getController().hideLoading();
                                        } else if (context instanceof CommonNurseServiceOrderDetailActivity) {
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

                }
            });
        }

        TextView subOrderUpdate=helper.getView(R.id.child_record_update_tv);
        if(mType == TypeEnum.COMMON_NURSE_FRAGMENT_FINISH_ORDER){
            subOrderUpdate.setVisibility(View.GONE);
        }else{
            subOrderUpdate.setVisibility(View.VISIBLE);
            subOrderUpdate.setOnClickListener(new View.OnClickListener() {
                @SingleClick(1000)
                @Override
                public void onClick(View v) {
                    if (item.getPay() == NurseServiceRecoderBean.ALREADY_PAY) {
                        ToastUtil.shortToast(context,"订单已支付，不能修改");
                    } else {
                        if (context instanceof CommonNurseServiceWorkingOrderDetailActivity) {
                            ((CommonNurseServiceWorkingOrderDetailActivity) context).getController().getSubOrderProjectsBySubOrderNumber(item.getSubOrderNumber(),item.getFrequency());
                        } else if (context instanceof CommonNurseServiceOrderDetailActivity) {
                            //((CommonNurseServiceOrderDetailActivity) context).getController().;
                        }
                    }

                }
            });
        }

    }
}
