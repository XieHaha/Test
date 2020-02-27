package com.keydom.ih_patient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.RegistrationRecordInfo;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.net.OrderService;
import com.keydom.ih_patient.utils.SelectDialogUtils;
import com.keydom.ih_patient.utils.pay.alipay.Alipay;
import com.keydom.ih_patient.utils.pay.weixin.WXPay;
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
 * 产科住院
 */
public class ObstetricRecordAdapter extends RecyclerView.Adapter<ObstetricRecordAdapter.VH> {
    private Context context;
    private List<String> dataList;

    /**
     * 构造方法
     */
    public ObstetricRecordAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_obstetric_hospital
                        , parent, false);
        return new ObstetricRecordAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {

    }

    /**
     * 展示支付弹框
     */
    private void showPayDialog(RegistrationRecordInfo registrationRecordInfo) {
        SelectDialogUtils.showPayDialog(context, registrationRecordInfo.getFee() + "",
                "预约挂号-" + registrationRecordInfo.getDoctor(),
                new GeneralCallback.SelectPayMentListener() {
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
                        new Alipay(context, object.getString("return_msg"),
                                new Alipay.AlipayResultCallBack() {
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
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
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
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
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
        private TextView tvName, tvBed, tvDoctor, tvNotice, tvAnesthetist, tvDate, tvStatus;

        public VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_name);
            tvBed = v.findViewById(R.id.tv_bed);
            tvDoctor = v.findViewById(R.id.tv_doctor);
            tvNotice = v.findViewById(R.id.tv_notice);
            tvAnesthetist = v.findViewById(R.id.tv_anesthetist);
            tvDate = v.findViewById(R.id.tv_date);
            tvStatus = v.findViewById(R.id.tv_status);
        }
    }
}
