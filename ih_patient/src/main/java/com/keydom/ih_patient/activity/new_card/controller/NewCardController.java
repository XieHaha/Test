package com.keydom.ih_patient.activity.new_card.controller;

import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.new_card.view.NewCardView;
import com.keydom.ih_patient.bean.PackageData;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.net.CardService;
import com.keydom.ih_patient.net.UserService;
import com.keydom.ih_patient.utils.SelectDialogUtils;
import com.keydom.ih_patient.utils.ToastUtil;
import com.keydom.ih_patient.utils.pay.WindowUtils;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 添加新卡控制器
 */
public class NewCardController extends ControllerImpl<NewCardView> implements View.OnClickListener {

    String type;
    boolean isOnlyIdCard;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isOnlyIdCard() {
        return isOnlyIdCard;
    }

    public void setOnlyIdCard(boolean onlyIdCard) {
        isOnlyIdCard = onlyIdCard;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.new_card_commit) {
            if (type.equals("card_id_card")) {
                if (isOnlyIdCard) {
                    certificateCommit();
                } else {
                    commit();
                }
            } else {
                commit();
            }

        } else if (view.getId() == R.id.user_birth_tv) {
            WindowUtils.hiddingInputMethod(view, getContext());
            showDateDialog(Type.BIRTHDATE, getView().getBirthTime());
        } else if (view.getId() == R.id.id_card_validity_period_tv) {
            WindowUtils.hiddingInputMethod(view, getContext());
            showDateDialog(Type.ORDERCUREAPPLY, getView().getValidityPeriod());
        } else if (view.getId() == R.id.id_card_region_tv || view.getId() == R.id.other_certificate_address_now_tv) {
            WindowUtils.hiddingInputMethod(view, getContext());
            SelectDialogUtils.showRegionSelectDialog(getContext(), getView().getProvinceName(), getView().getCityName(), getView().getAreaName(), new GeneralCallback.SelectRegionListener() {
                @Override
                public void getSelectedRegion(List<PackageData.ProvinceBean> data, int position1, int position2, int position3) {
                    getView().saveRegion(data, position1, position2, position3);
                }
            });
        } else if (view.getId() == R.id.user_national_tv) {
            WindowUtils.hiddingInputMethod(view, getContext());
            SelectDialogUtils.showNationSelectDialog(getContext(), getView().getNation(), new GeneralCallback.SelectNationListener() {
                @Override
                public void getSelectedNation(PackageData.NationBean nationBean) {
                    getView().saveNation(nationBean);
                }
            });
        } else if (view.getId() == R.id.user_sex_tv) {
            WindowUtils.hiddingInputMethod(view, getContext());
            final List<String> sexList = new ArrayList<>();
            sexList.add("男");
            sexList.add("女");
//            sexList.add("未知的性别");
            OptionsPickerView sexPickerView = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    if (sexList.get(options1).equals("男")) {
                        getView().setSex("男", "0");
                    } else if (sexList.get(options1).equals("女")) {
                        getView().setSex("女", "1");
                    }/*else if(sexList.get(options1).equals("未知的性别")){
                        getView().setSex("未知的性别","2");
                    }*/
                }
            }).build();
            sexPickerView.setPicker(sexList);
            sexPickerView.show();
        }
    }

    public void matchNation(String nationName) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getNationList("nation"), new HttpSubscriber<List<PackageData.NationBean>>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable final List<PackageData.NationBean> data) {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getNationName().contains(nationName)) {
                        getView().matchNation(data.get(i));
                        break;
                    }
                }
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                ToastUtil.shortToast(getContext(), "民族匹配失败：" + msg + "，请自行选择");
                return super.requestError(exception, code, msg);
            }
        });
    }

    private void showDateDialog(final String type, String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            if (dateStr != null) {
                date = formatter.parse(dateStr);
            } else
                date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    if (type.equals(Type.BIRTHDATE)) {
                        getView().setBirthDate(date);
                    } else {
                        getView().setIdCardValidityPeriodDate(date);
                    }
                    Logger.e(date.toString());
                }
            }).setDate(calendar).build();
            pvTime.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    /**
     * 提交
     */
    public void commit() {
        Map<String, Object> map = getView().getParams();
        if (map != null) {
            showLoading();
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(CardService.class).newCard(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
                @Override
                public void requestComplete(@Nullable Object data) {
                    hideLoading();
                    getView().commitSuccess();
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    hideLoading();
                    getView().commitFailed(msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }
    }

    /**
     * 实名认证提交
     */
    public void certificateCommit() {
        Map<String, Object> map = getView().getParams();
        if (map != null) {
            showLoading();
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).patientValidateByIdCard(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(), getDisposable(), false) {
                @Override
                public void requestComplete(@Nullable Object data) {
                    hideLoading();
                    getView().commitSuccess();
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    hideLoading();
                    getView().commitFailed(msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }
    }
}
