package com.keydom.ih_patient.fragment.controller;

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
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.view.MyScrollView;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_user_manager.ManageUserActivity;
import com.keydom.ih_patient.activity.inspection_report.InspectionReportActivity;
import com.keydom.ih_patient.activity.location_manage.LocationManageActivity;
import com.keydom.ih_patient.activity.login.LoginActivity;
import com.keydom.ih_patient.activity.medical_record.MedicalRecordActivity;
import com.keydom.ih_patient.activity.my_chat_group.ChatGoupActivity;
import com.keydom.ih_patient.activity.my_doctor_or_nurse.MyDoctorOrNurseActivity;
import com.keydom.ih_patient.activity.my_medical_card.MyMedicalCardActivity;
import com.keydom.ih_patient.activity.my_message.MyMessageActivity;
import com.keydom.ih_patient.activity.nursing_order.NursingOrderActivity;
import com.keydom.ih_patient.activity.online_diagnoses_order.OnlineDiagnonsesOrderActivity;
import com.keydom.ih_patient.activity.order_doctor_register.RegistrationRecordActivity;
import com.keydom.ih_patient.activity.payment_records.PaymentRecordActivity;
import com.keydom.ih_patient.activity.prescription_check.PrescriptionListActivity;
import com.keydom.ih_patient.activity.setting.FeedBackActivity;
import com.keydom.ih_patient.activity.setting.SettingActivity;
import com.keydom.ih_patient.activity.subscribe_examination_order.SubscribeExaminationActivity;
import com.keydom.ih_patient.activity.user_info_operate.UserInfoOperateActivity;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.fragment.TabMineFragment;
import com.keydom.ih_patient.fragment.view.TabMineView;
import com.keydom.ih_patient.net.MessageService;
import com.keydom.ih_patient.utils.GestureVerificationUtils;
import com.keydom.ih_patient.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 个人中心控制器
 */
public class TabMineController extends ControllerImpl<TabMineView> implements View.OnClickListener, MyScrollView.OnScrollListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_edt:
                getView().showHospitalPopupWindow();
                break;
            case R.id.mine_location_control:
                if (Global.getUserId() != -1) {
                    LocationManageActivity.start(getContext(), Type.STARTLOCATIONWITHOUTRESULT);
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.mine_function_card:
                if (Global.getUserId() != -1) {
                    MyMedicalCardActivity.start(getContext());
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }

                break;
            case R.id.mine_setting_img:
                if (Global.getUserId() != -1) {
                    UserInfoOperateActivity.start(getContext(),UserInfoOperateActivity.EDITTYPE);
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }

                break;
            case R.id.mine_item_feedback:
                if (Global.getUserId() != -1) {
                    FeedBackActivity.start(getContext());
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.mine_booking_order:
                if (Global.getUserId() != -1) {
                    SubscribeExaminationActivity.start(getContext());
//                    GestureVerificationUtils.isGesturePassed(getContext(),TabMineFragment.BOOKINGORDER);
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.mine_registered_mail_order:
                if (Global.getUserId() != -1) {
                    RegistrationRecordActivity.start(getContext());
//                    GestureVerificationUtils.isGesturePassed(getContext(),TabMineFragment.REGISTRATIONRECORD);
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }

                break;
            case R.id.mine_inspection_report:
                if (Global.getUserId() != -1) {
                    if(isCanGesture()){
                        if (isNeedGesture())
                            GestureVerificationUtils.isGesturePassed(getContext(), TabMineFragment.INSPECTIONORDER);
                        else
                            InspectionReportActivity.start(getContext());
                    }else {
                        long time = System.currentTimeMillis() - SharePreferenceManager.getLockTime();
                        time = time / 1000 / 60;
                        ToastUtil.shortToast(getContext(),"已锁定，请"+(5-time)+"分钟后稍后尝试");
                    }

                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.mine_people_control:
                if (Global.getUserId() != -1) {
                    ManageUserActivity.start(getContext(), ManageUserActivity.FROMUSERINDEX);
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.mine_payment_records:
                if (Global.getUserId() != -1) {
                    PaymentRecordActivity.start(getContext());
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.jump_to_login_tv:
                LoginActivity.start(getContext());
                break;
            case R.id.mine_item_setting:
                SettingActivity.start(getContext());
                break;
            case R.id.mine_nursing_orders:
                if (Global.getUserId() != -1) {
                    ActivityUtils.startActivity(NursingOrderActivity.class);
//                    GestureVerificationUtils.isGesturePassed(getContext(),TabMineFragment.NURSINGORDER);
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.mine_physician_orders:
                if (Global.getUserId() != -1) {
                    OnlineDiagnonsesOrderActivity.start(getContext(), OnlineDiagnonsesOrderActivity.WAITEDIAGNOSES);
//                    GestureVerificationUtils.isGesturePassed(getContext(),TabMineFragment.DIAGNOSEORDER);
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.mine_case_record:
                if (Global.getUserId() != -1) {
                    if(isCanGesture()){
                        if (isNeedGesture())
                            GestureVerificationUtils.isGesturePassed(getContext(), TabMineFragment.MEDICALRECORD);
                        else
                            ActivityUtils.startActivity(MedicalRecordActivity.class);
                    }else {
                        long time = System.currentTimeMillis() - SharePreferenceManager.getLockTime();
                        time = time / 1000 / 60;
                        ToastUtil.shortToast(getContext(),"已锁定，请"+(5-time)+"分钟后稍后尝试");
                    }

                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.mine_function_doctor:
                if (Global.getUserId() != -1) {
                    Intent i = new Intent(getContext(), MyDoctorOrNurseActivity.class);
                    i.putExtra(MyDoctorOrNurseActivity.TYPE, MyDoctorOrNurseActivity.DOCTOR);
                    ActivityUtils.startActivity(i);
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.mine_function_nurse:
                if (Global.getUserId() != -1) {
                    Intent i = new Intent(getContext(), MyDoctorOrNurseActivity.class);
                    i.putExtra(MyDoctorOrNurseActivity.TYPE, MyDoctorOrNurseActivity.NURSE);
                    ActivityUtils.startActivity(i);
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.mine_electronic_prescription:
                if (Global.getUserId() != -1) {
                    if(isCanGesture()){
                        if (isNeedGesture())
                            GestureVerificationUtils.isGesturePassed(getContext(), TabMineFragment.ELECTRONICPRESCRIBING);
                        else
                            ActivityUtils.startActivity(PrescriptionListActivity.class);
                    }else {
                        long time = System.currentTimeMillis() - SharePreferenceManager.getLockTime();
                        time = time / 1000 / 60;
                        ToastUtil.shortToast(getContext(),"已锁定，请"+(5-time)+"分钟后稍后尝试");
                    }

                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.mine_item_my_message:
                if (Global.getUserId() != -1) {
                    MyMessageActivity.start(getContext(), Type.MYMESSAGE, null);
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.mine_user_head_img:
                if (Global.getUserId() != -1) {
                    CommonUtils.previewImage(getContext(), getView().getImgStr());
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            case R.id.mine_item_my_chatgroup:
                if (Global.getUserId() != -1) {
                    ChatGoupActivity.start(getContext());
                } else {
                    ToastUtil.shortToast(getContext(), getContext().getString(R.string.unlogin_hint));
                }
                break;
            default:
        }
    }

    @Override
    public void onScroll(int scrollY) {
        if (scrollY > 0) {
            getView().getTitleLayout().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mine_color));
        } else {
            getView().getTitleLayout().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.tran));
        }
    }

    /**
     * 获取我的消息条数
     */
    public void getMyUnreadMessageNum() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MessageService.class).countMessage(Global.getUserId()), new HttpSubscriber<Integer>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable Integer data) {
                getView().getUnreadMessagaCountSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getUnreadMessageCountFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    private boolean isNeedGesture() {
        long time = System.currentTimeMillis() - Global.getCurrentTimeMillis();
        time = time / 1000 / 60;
        if (time >= 5)
            return true;
        else
            return false;
    }
    private boolean isCanGesture(){
        long time = System.currentTimeMillis() - SharePreferenceManager.getLockTime();
        time = time / 1000 / 60;
        if (time >= 5)
            return true;
        else
            return false;
    }
}
