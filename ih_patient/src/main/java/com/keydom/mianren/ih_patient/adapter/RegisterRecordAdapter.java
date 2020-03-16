package com.keydom.mianren.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.RegistrationRecordDetailActivity;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.RegistrationRecordInfo;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.net.OrderService;
import com.keydom.mianren.ih_patient.utils.SelectDialogUtils;
import com.keydom.mianren.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.mianren.ih_patient.utils.pay.weixin.WXPay;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 挂号记录适配器
 */
public class RegisterRecordAdapter extends RecyclerView.Adapter<RegisterRecordAdapter.VH> {
    private Context context;
    private List<RegistrationRecordInfo> dataList;

    /**
     * 构造方法
     */
    public RegisterRecordAdapter(Context context, List<RegistrationRecordInfo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.registration_record_item, parent, false);
        return new RegisterRecordAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.name_tv.setText(dataList.get(position).getName());
        holder.register_time_tv.setText(dataList.get(position).getRegistrationDate() + "(" + dataList.get(position).getTimeDesc() + ")");
        holder.register_department_tv.setText(dataList.get(position).getDept());
        holder.register_doctor_tv.setText(dataList.get(position).getDoctor());
        holder.register_remark_tv.setText("请于当日" + dataList.get(position).getTimeDesc() + "到医院就诊");
        if (dataList.get(position).getState() == 0 || dataList.get(position).getState() == 1) {
            holder.remove_register_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new GeneralDialog(context, "您确认退掉该订单？", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            removeOrder(dataList.get(position).getId(), position);
                        }
                    }).setTitle("提示").setPositiveButton("确定").show();

                }
            });
        } else {
            holder.remove_register_tv.setVisibility(View.GONE);
        }
        if (dataList.get(position).getState() == 0) {
            holder.pay_tv.setVisibility(View.VISIBLE);
            holder.pay_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPayDialog(dataList.get(position));
                }
            });
        } else {
            holder.pay_tv.setVisibility(View.GONE);
        }
        if (dataList.get(position).getState() == -1) {
            holder.state_layout.setVisibility(View.VISIBLE);
            if (dataList.get(position).getRefundState() == 0)
                holder.state_tv.setText("已取消");
            else if (dataList.get(position).getRefundState() == 1)
                holder.state_tv.setText("退款中");
            else
                holder.state_tv.setText("已退款");
        } else {
            holder.state_layout.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrationRecordDetailActivity.start(context, dataList.get(position));
            }
        });

    }

    /**
     * 展示支付弹框
     */
    private void showPayDialog(RegistrationRecordInfo registrationRecordInfo) {
        SelectDialogUtils.showPayDialog(context, registrationRecordInfo.getFee() + "", "预约挂号-" + registrationRecordInfo.getDoctor(), new GeneralCallback.SelectPayMentListener() {
            @Override
            public void getSelectPayMent(String type) {
                Map<String, Object> map = new HashMap<>();
                map.put("orderNumber", registrationRecordInfo.getOrderNo());
                map.put("totalMoney", registrationRecordInfo.getFee());
                if (Type.ALIPAY.equals(type)) {
                    map.put("type", 2);
                    hospitalFeeByOrderNumber(map, 2);
                } else if (Type.WECHATPAY.equals(type)) {
                    map.put("type", 1);
                    hospitalFeeByOrderNumber(map, 1);
                }
            }
        });
    }

    /**
     * 创建订单
     */
    public void hospitalFeeByOrderNumber(Map<String, Object> map, int type) {

        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).hospitalFeeByOrderNumber(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<String>() {
            @Override
            public void requestComplete(@Nullable String data) {
                if (type == 2) {
                    try {
                        JSONObject object = new JSONObject(data);
                        Logger.e("return_msg:" + object.getString("return_msg"));
                        new Alipay(context, object.getString("return_msg"), new Alipay.AlipayResultCallBack() {
                            @Override
                            public void onSuccess() {
                                ToastUtil.showMessage(context, "支付成功");
                                EventBus.getDefault().post(new Event(EventType.DOCTORREGISTERORDERPAYED, null));
                            }

                            @Override
                            public void onDealing() {

                            }

                            @Override
                            public void onError(int error_code) {
                                ToastUtil.showMessage(context, "支付失败" + error_code
                                );
                            }

                            @Override
                            public void onCancel() {

                            }
                        }).doPay();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (type == 1) {
                    WXPay.getInstance().doPay(context, data, new WXPay.WXPayResultCallBack() {
                        @Override
                        public void onSuccess() {
                            EventBus.getDefault().post(new Event(EventType.DOCTORREGISTERORDERPAYED, null));
                            ToastUtils.showShort("支付成功");
                        }

                        @Override
                        public void onError(int error_code) {
                            ToastUtil.showMessage(context, "支付失败" + error_code
                            );
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtil.showMessage(context, "拉取订单失败，请重试");
                return super.requestError(exception, code, msg);
            }
        });


    }

    /**
     * 删除订单
     */
    private void removeOrder(long id, final int position) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).backno(map), new HttpSubscriber<Object>() {
            @Override
            public void requestComplete(@Nullable Object data) {
                ToastUtil.showMessage(context, "退号成功");
                dataList.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtil.showMessage(context, "退号失败：" + msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView name_tv, register_time_tv, register_department_tv, register_doctor_tv, register_remark_tv, remove_register_tv, pay_tv, state_tv;
        private LinearLayout state_layout;

        public VH(View v) {
            super(v);
            name_tv = v.findViewById(R.id.name_tv);
            register_time_tv = v.findViewById(R.id.register_time_tv);
            register_department_tv = v.findViewById(R.id.register_department_tv);
            register_doctor_tv = v.findViewById(R.id.register_doctor_tv);
            register_remark_tv = v.findViewById(R.id.register_remark_tv);
            remove_register_tv = v.findViewById(R.id.remove_register_tv);
            pay_tv = v.findViewById(R.id.pay_tv);
            state_tv = v.findViewById(R.id.state_tv);
            state_layout = v.findViewById(R.id.state_layout);
        }
    }
}
