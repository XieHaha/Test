package com.keydom.ih_patient.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.DialogCreator;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.AgreementActivity;
import com.keydom.ih_patient.bean.PackageData;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.callback.OnCheckDialogListener;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.net.UserService;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.baidu.mapapi.BMapManager.getContext;

/**
 * 选择弹框工具类
 */
public class SelectDialogUtils {

    /**
     * 查询所有地区并打开选择器
     */
    public static void showRegionSelectDialog(Context context, String provinceName, String cityName, String areaName, GeneralCallback.SelectRegionListener selectRegionListener) {
        if (Global.getData() == null || Global.getProvinceItems() == null || Global.getCityItems() == null || Global.getAreaItems() == null) {
            Dialog loading = DialogCreator.createLoadingDialog(context, "请稍等");
            loading.show();
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getRegionList(), new HttpSubscriber<List<PackageData.ProvinceBean>>() {
                @Override
                public void requestComplete(@Nullable final List<PackageData.ProvinceBean> data) {
                    List<String> options1Items = new ArrayList<>();
                    List<List<String>> options2Items = new ArrayList<>();
                    List<List<List<String>>> options3Items = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        options1Items.add(data.get(i).getName());
                        List<String> CityList = new ArrayList<>();
                        List<List<String>> Province_AreaList = new ArrayList<>();
                        for (int j = 0; j < data.get(i).getCity().size(); j++) {
                            CityList.add(data.get(i).getCity().get(j).getName());
                            List<String> City_AreaList = new ArrayList<>();
                            if (data.get(i).getCity().get(j).getArea().size() == 0) {
                                City_AreaList.add("");
                            } else {
                                for (int k = 0; k < data.get(i).getCity().get(j).getArea().size(); k++) {
                                    City_AreaList.add(data.get(i).getCity().get(j).getArea().get(k).getName());
                                }
                            }
                            Province_AreaList.add(City_AreaList);
                        }
                        options2Items.add(CityList);
                        options3Items.add(Province_AreaList);
                        Global.setData(data);
                        Global.setProvinceItems(options1Items);
                        Global.setCityItems(options2Items);
                        Global.setAreaItems(options3Items);
                        loading.hide();
                    }
                    int position_f = 0;
                    int position_s = 0;
                    int position_t = 0;
                    if (provinceName != null && cityName != null && areaName != null) {
                        for (int i = 0; i < options1Items.size(); i++) {
                            if (provinceName.equals(options1Items.get(i)))
                                position_f = i;
                        }
                        for (int i = 0; i < options2Items.get(position_f).size(); i++) {
                            if (cityName.equals(options2Items.get(position_f).get(i)))
                                position_s = i;
                        }
                        for (int i = 0; i < options3Items.get(position_f).get(position_s).size(); i++) {
                            if (areaName.equals(options3Items.get(position_f).get(position_s).get(i)))
                                position_t = i;
                        }
                    }

                    OptionsPickerView optionsPickerView = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            if (selectRegionListener != null) {
                                selectRegionListener.getSelectedRegion(data, options1, options2, options3);
                            }
                        }
                    }).setSelectOptions(position_f, position_s, position_t).build();
                    optionsPickerView.setPicker(options1Items, options2Items, options3Items);
                    optionsPickerView.show();
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    loading.hide();
                    ToastUtil.shortToast(getContext(), "获取地区列表失败：" + msg + "，请稍后重试");
                    return super.requestError(exception, code, msg);
                }
            });
        } else {
            int position_f = 0;
            int position_s = 0;
            int position_t = 0;
            if (provinceName != null && cityName != null && areaName != null) {
                for (int i = 0; i < Global.getProvinceItems().size(); i++) {
                    if (provinceName.equals(Global.getProvinceItems().get(i)))
                        position_f = i;
                }
                for (int i = 0; i < Global.getCityItems().get(position_f).size(); i++) {
                    if (cityName.equals(Global.getCityItems().get(position_f).get(i)))
                        position_s = i;
                }
                for (int i = 0; i < Global.getAreaItems().get(position_f).get(position_s).size(); i++) {
                    if (areaName.equals(Global.getAreaItems().get(position_f).get(position_s).get(i)))
                        position_t = i;
                }
            }
            OptionsPickerView optionsPickerView = new OptionsPickerBuilder(context, (options1, options2, options3, v) -> {
                if (selectRegionListener != null) {
                    selectRegionListener.getSelectedRegion(Global.getData(), options1, options2, options3);
                }
            }).setSelectOptions(position_f, position_s, position_t).build();
            optionsPickerView.setPicker(Global.getProvinceItems(), Global.getCityItems(), Global.getAreaItems());
            optionsPickerView.show();
        }
    }

    /**
     * 查询所有民族并打开选择器
     */
    public static void showNationSelectDialog(Context context, String nationName, GeneralCallback.SelectNationListener selectNationListener) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getNationList("nation"), new HttpSubscriber<List<PackageData.NationBean>>() {
            @Override
            public void requestComplete(@Nullable final List<PackageData.NationBean> data) {
                int defaultPosition = 0;
                final List<String> nationList = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    nationList.add(data.get(i).getNationName());
                    if (nationName != null)
                        if (nationName.equals(data.get(i).getNationName()))
                            defaultPosition = i;
                }
                OptionsPickerView nationPickerView = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        selectNationListener.getSelectedNation(data.get(options1));
                    }
                }).setSelectOptions(defaultPosition).build();
                nationPickerView.setPicker(nationList);
                nationPickerView.show();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtil.shortToast(getContext(), "获取民族列表失败：" + msg + "，请稍后重试");
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 打开通用支付窗口
     *
     * @param context               上下文
     * @param feeStr                支付金额
     * @param descStr               订单描述
     * @param selectPayMentListener 选择支付方式回调
     */
    public static void showPayDialog(Context context, String feeStr, String descStr, GeneralCallback.SelectPayMentListener selectPayMentListener) {
        final String[] payType = {Type.ALIPAY};
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setCancelable(false);

        bottomSheetDialog.setCanceledOnTouchOutside(false);
        final boolean[] isAgree = {false};
        View view = LayoutInflater.from(getContext()).inflate(R.layout.general_pay_dialog_layout, null);
        bottomSheetDialog.setContentView(view);
        final TextView order_price_tv = view.findViewById(R.id.order_price_tv);
        order_price_tv.setText("¥" + feeStr + "元");
        final TextView order_name_tv = view.findViewById(R.id.order_name_tv);
        order_name_tv.setText(descStr + "");
        final TextView ali_pay_tv = view.findViewById(R.id.ali_pay_tv);
        final TextView wechat_pay_tv = view.findViewById(R.id.wechat_pay_tv);
        final TextView unionpay_pay_tv = view.findViewById(R.id.unionpay_pay_tv);
        TextView pay_agreement_tv = view.findViewById(R.id.pay_agreement_tv);
        TextView pay_commit_tv = view.findViewById(R.id.pay_commit_tv);
        final ImageView ali_pay_selected_img = view.findViewById(R.id.ali_pay_selected_img);
        final ImageView wechat_pay_selected_img = view.findViewById(R.id.wechat_pay_selected_img);
        final ImageView unionpay_pay_selected_img = view.findViewById(R.id.unionpay_pay_selected_img);
        CheckBox payAgreementCb = view.findViewById(R.id.pay_agreement_cb);
        payAgreementCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAgree[0] = isChecked;
            }
        });
        ImageView close_img = view.findViewById(R.id.close_img);
        ali_pay_selected_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ali_pay_selected_img.setImageResource(R.mipmap.pay_selected_icon);
                wechat_pay_selected_img.setImageResource(R.mipmap.pay_unselected_icon);
                unionpay_pay_selected_img.setImageResource(R.mipmap.pay_unselected_icon);
                ali_pay_tv.setTextColor(context.getResources().getColor(R.color.pay_selected));
                wechat_pay_tv.setTextColor(context.getResources().getColor(R.color.pay_unselected));
                unionpay_pay_tv.setTextColor(context.getResources().getColor(R.color.pay_unselected));
                payType[0] = Type.ALIPAY;
            }


        });
        wechat_pay_selected_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ali_pay_selected_img.setImageResource(R.mipmap.pay_unselected_icon);
                wechat_pay_selected_img.setImageResource(R.mipmap.pay_selected_icon);
                unionpay_pay_selected_img.setImageResource(R.mipmap.pay_unselected_icon);
                ali_pay_tv.setTextColor(context.getResources().getColor(R.color.pay_unselected));
                wechat_pay_tv.setTextColor(context.getResources().getColor(R.color.pay_selected));
                unionpay_pay_tv.setTextColor(context.getResources().getColor(R.color.pay_unselected));
                payType[0] = Type.WECHATPAY;
            }


        });
        unionpay_pay_selected_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ali_pay_selected_img.setImageResource(R.mipmap.pay_unselected_icon);
                wechat_pay_selected_img.setImageResource(R.mipmap.pay_unselected_icon);
                unionpay_pay_selected_img.setImageResource(R.mipmap.pay_selected_icon);
                ali_pay_tv.setTextColor(context.getResources().getColor(R.color.pay_unselected));
                wechat_pay_tv.setTextColor(context.getResources().getColor(R.color.pay_unselected));
                unionpay_pay_tv.setTextColor(context.getResources().getColor(R.color.pay_selected));
                payType[0] = Type.UNIONPAY;
            }


        });
        pay_commit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (isAgree[0]) {
                    Logger.e("register_doctor_commit_tv->clock");
                    selectPayMentListener.getSelectPayMent(payType[0]);

                    bottomSheetDialog.dismiss();
//                } else {
//                    ToastUtil.shortToast(getContext(), "请阅读并同意支付协议");
//                }

            }
        });
        pay_agreement_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.e("跳转支付协议页面");
                AgreementActivity.startPayAgreement(context);
            }
        });
        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.e("close_img->clock");
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();

    }


    /**
     * 处方审核dialog
     *
     * @param context
     * @param listener
     * @return
     */
    public static Dialog createCheckDialog(final Context context, final OnCheckDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.prescription_dialog_layout, null);
        dialog.setContentView(view);
        final ImageView cancel = (ImageView) view.findViewById(R.id.check_dialog_close);
        final TextView commit = (TextView) view.findViewById(R.id.check_dialog_submit);
        final EditText checkDialogInput = view.findViewById(R.id.check_dialog_input);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
                dialog.hide();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDialogInput.getText().toString() == null || "".equals(checkDialogInput.getText().toString())) {
                    ToastUtil.shortToast(context, "请输入审核意见");
                    return;
                }
                dialog.dismiss();
                dialog.cancel();
                dialog.hide();
                listener.commit(v, checkDialogInput.getText().toString());
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }
}
