package com.keydom.mianren.ih_patient.fragment.controller;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.MyScrollView;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.ManageUserActivity;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.ManageUserSelectActivity;
import com.keydom.mianren.ih_patient.activity.global_search.SearchActivity;
import com.keydom.mianren.ih_patient.activity.index_main.ChooseCityActivity;
import com.keydom.mianren.ih_patient.activity.inspection_report.InspectionReportActivity;
import com.keydom.mianren.ih_patient.activity.location_manage.LocationManageActivity;
import com.keydom.mianren.ih_patient.activity.login.LoginActivity;
import com.keydom.mianren.ih_patient.activity.medical_mail.MedicalMailListActivity;
import com.keydom.mianren.ih_patient.activity.my_chat_group.ChatGoupActivity;
import com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.MyDoctorOrNurseActivity;
import com.keydom.mianren.ih_patient.activity.my_medical_card.MyMedicalCardActivity;
import com.keydom.mianren.ih_patient.activity.my_message.MyMessageActivity;
import com.keydom.mianren.ih_patient.activity.nursing_order.NursingOrderActivity;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.OnlineDiagnonsesOrderActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.RegistrationRecordActivity;
import com.keydom.mianren.ih_patient.activity.payment_records.PaymentRecordActivity;
import com.keydom.mianren.ih_patient.activity.reserve_examination.ExaminationReserveListActivity;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.ObstetricHospitalListActivity;
import com.keydom.mianren.ih_patient.activity.reserve_painless_delivery.PainlessDeliveryListActivity;
import com.keydom.mianren.ih_patient.activity.setting.FeedBackActivity;
import com.keydom.mianren.ih_patient.activity.setting.SettingActivity;
import com.keydom.mianren.ih_patient.activity.subscribe_examination_order.SubscribeExaminationActivity;
import com.keydom.mianren.ih_patient.activity.user_info_operate.UserInfoOperateActivity;
import com.keydom.mianren.ih_patient.callback.SingleClick;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.Type;
import com.keydom.mianren.ih_patient.fragment.view.TabMineView;
import com.keydom.mianren.ih_patient.net.MessageService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * ?????????????????????
 */
public class TabMineController extends ControllerImpl<TabMineView> implements View.OnClickListener, MyScrollView.OnScrollListener {
    @SingleClick(1000)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_edt:
                getView().showHospitalPopupWindow();
                break;
            case R.id.mine_location_control:
                if (loginStatus()) {
                    LocationManageActivity.start(getContext(), Type.STARTLOCATIONWITHOUTRESULT);
                }
                break;
            case R.id.mine_function_card:
                if (loginStatus()) {
                    MyMedicalCardActivity.start(getContext());
                }
                break;
            case R.id.iv_setting_vip:
            case R.id.iv_setting:
                if (loginStatus()) {
                    UserInfoOperateActivity.start(getContext(), UserInfoOperateActivity.EDITTYPE);
                }
                break;
            case R.id.mine_item_feedback:
                if (loginStatus()) {
                    FeedBackActivity.start(getContext());
                }
                break;
            case R.id.mine_booking_order:
                if (loginStatus()) {
                    SubscribeExaminationActivity.start(getContext());
                }
                break;
            case R.id.item_register:
            case R.id.mine_registered_mail_order:
                if (loginStatus()) {
                    RegistrationRecordActivity.start(getContext());
                }
                break;
            case R.id.mine_medical_mail_order:
                if (loginStatus()) {
                    MedicalMailListActivity.start(getContext());
                }
                break;
            case R.id.mine_report_order:
                if (loginStatus()) {
                    InspectionReportActivity.start(getContext(), null,-1);
                }
                break;
            case R.id.mine_people_control:
                if (loginStatus()) {
                    ManageUserActivity.start(getContext(), ManageUserActivity.FROMUSERINDEX);
                }
                break;
            case R.id.mine_payment_records:
                if (loginStatus()) {
                    PaymentRecordActivity.start(getContext());
                }
                break;
            case R.id.jump_to_login_tv:
                LoginActivity.start(getContext());
                break;
            case R.id.mine_item_setting:
                SettingActivity.start(getContext());
                break;
            case R.id.mine_nursing_orders:
                if (loginStatus()) {
                    ActivityUtils.startActivity(NursingOrderActivity.class);
                }
                break;
            case R.id.mine_physician_orders:
                if (loginStatus()) {
                    OnlineDiagnonsesOrderActivity.start(getContext(),
                            OnlineDiagnonsesOrderActivity.WAITEDIAGNOSES);
                }
                break;
            case R.id.mine_function_doctor:
                if (loginStatus()) {
                    Intent i = new Intent(getContext(), MyDoctorOrNurseActivity.class);
                    i.putExtra(MyDoctorOrNurseActivity.TYPE, MyDoctorOrNurseActivity.DOCTOR);
                    ActivityUtils.startActivity(i);
                }
                break;
            case R.id.mine_function_nurse:
                if (loginStatus()) {
                    Intent i = new Intent(getContext(), MyDoctorOrNurseActivity.class);
                    i.putExtra(MyDoctorOrNurseActivity.TYPE, MyDoctorOrNurseActivity.NURSE);
                    ActivityUtils.startActivity(i);
                }
                break;
            case R.id.mine_item_my_message:
                if (loginStatus()) {
                    MyMessageActivity.start(getContext(), Type.MYMESSAGE, null);
                }
                break;
            case R.id.iv_user_header_vip:
            case R.id.iv_user_header:
                if (loginStatus()) {
                    CommonUtils.previewImage(getContext(), getView().getImgStr());
                }
                break;
            case R.id.mine_item_my_chatgroup:
                if (loginStatus()) {
                    ChatGoupActivity.start(getContext());
                }
                break;
            case R.id.search_layout:
                SearchActivity.start(getContext());
                break;
            case R.id.location_layout:
                ChooseCityActivity.start(getContext());
                break;
            case R.id.item_check:
                if (loginStatus()) {
                    ExaminationReserveListActivity.start(getContext());
                }
                break;
            case R.id.item_obstetric:
                if (loginStatus()) {
                    ObstetricHospitalListActivity.start(getContext());
                }
                break;
            case R.id.item_painless_delivery:
                if (loginStatus()) {
                    PainlessDeliveryListActivity.start(getContext());
                }
                break;
            case R.id.mine_current_visit_change:
                if (loginStatus()) {
                    ManageUserSelectActivity.start(getContext(), String.valueOf(getView().getCurUserId()));
                }
                break;
            default:
                break;
        }
    }

    private boolean loginStatus() {
        if (Global.getUserId() != -1) {
            return true;
        } else {
            ToastUtil.showMessage(getContext(), R.string.unlogin_hint);
            return false;
        }
    }

    @Override
    public void onScroll(int scrollY) {
        if (scrollY > 0) {
            getView().getTitleLayout().setBackgroundColor(ContextCompat.getColor(getContext(),
                    R.color.color_399cf9));
        } else {
            getView().getTitleLayout().setBackgroundColor(ContextCompat.getColor(getContext(),
                    R.color.color_00000000));
        }
    }

    /**
     * ????????????????????????
     */
    public void getMyUnreadMessageNum() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MessageService.class).countMessage(Global.getUserId()), new HttpSubscriber<Integer>(getContext(), getDisposable(), false, false) {
            @Override
            public void requestComplete(@Nullable Integer data) {
                getView().getUnreadMessagaCountSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getUnreadMessageCountFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }
}
