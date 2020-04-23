package com.keydom.mianren.ih_doctor.fragment.controller;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.SearchActivity;
import com.keydom.mianren.ih_doctor.activity.nurse_service.NurseServiceOrderListActivity;
import com.keydom.mianren.ih_doctor.activity.online_consultation.ConsultationOrderActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.DiagnoseOrderListActivity;
import com.keydom.mianren.ih_doctor.activity.personal.PersonalInfoActivity;
import com.keydom.mianren.ih_doctor.bean.HomeBean;
import com.keydom.mianren.ih_doctor.bean.HomeMsgBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.ServiceConst;
import com.keydom.mianren.ih_doctor.fragment.view.WorkFragmentView;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.MainApiService;
import com.keydom.mianren.ih_doctor.net.PersonalApiService;
import com.keydom.mianren.ih_doctor.utils.SelectHospitalPopUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Description：
 * @Author：song
 * @Date：18/11/16 下午2:26
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:26
 */
@SuppressLint("NewApi")
public class WorkFragmentController extends ControllerImpl<WorkFragmentView> implements View.OnClickListener, View.OnScrollChangeListener {

    @SingleClick(1000)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.receive_online_re:
                if (SharePreferenceManager.isAutony()) {
                    if (!MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_ONLINE_DIAGNOSE_SERVICE_IMG_CODE, ServiceConst.DOCTOR_ONLINE_DIAGNOSE_SERVICE_VIDEO_CODE, ServiceConst.NURSE_IMG_CONSULT_SERVICE_CODE, ServiceConst.NURSE_VIDEO_CONSULT_SERVICE_CODE, ServiceConst.NURSE_SERVICE_CODE, ServiceConst.MEDICINE_CONSULT_SERVICE_CODE_IMG, ServiceConst.MEDICINE_CONSULT_SERVICE_CODE_VIDEO})) {
                        showNotAccessDialog();
                        return;
                    }
                    if (SharePreferenceManager.getRoleId() == Const.ROLE_NURSE) {
                        if (SharePreferenceManager.getPositionId() == Const.HEAD_NURSE_CODE) {
                            NurseServiceOrderListActivity.headNurseStart(getContext());
                        } else {
                            NurseServiceOrderListActivity.commonNurseStart(getContext());
                        }
                    } else if (SharePreferenceManager.getRoleId() == Const.ROLE_DOCTOR) {
                        DiagnoseOrderListActivity.startDiagnose(getContext());
                    } else if (SharePreferenceManager.getRoleId() == Const.ROLE_MEDICINE) {
                        DiagnoseOrderListActivity.startConsult(getContext());
                    } else {
                        showNotAccessDialog();
                    }
                } else {
                    ToastUtil.showMessage(mContext, "还未实名认证，请实名认证再开通相关服务");
                }

                break;
            case R.id.cooperate_online_re:
                //                if(SharePreferenceManager.isAutony()){
                //                    if (MyApplication.serviceEnable(new String[]{ServiceConst
                //                    .DOCTOR_COOPERATE_SERVICE_CODE_Z,ServiceConst
                //                    .DOCTOR_COOPERATE_SERVICE_CODE_H, ServiceConst
                //                    .NURSE_COOPERATE_SERVICE_CODE, ServiceConst
                //                    .MEDICINE_COOPERATE_SERVICE_CODE})) {
                //                        DoctorCooperationActivity.start(getContext());
                //                    } else {
                //                        showNotAccessDialog();
                //                    }
                //                }else{
                //                    ToastUtil.showMessage(mContext,"还未实名认证，请实名认证再开通相关服务");
                //                }

                ConsultationOrderActivity.start(getContext());
                break;
            case R.id.calculator_re:
                ToastUtil.showMessage(getContext(), "计算器");
                break;
            case R.id.dianose_tool_re:
                ToastUtil.showMessage(getContext(), "诊断工具");
                break;
            case R.id.medical_science_re:
                ToastUtil.showMessage(getContext(), "万方医学");
                break;
            case R.id.guide_re:
                ToastUtil.showMessage(getContext(), "医药指南");
                break;
            case R.id.edit:
                PersonalInfoActivity.start(getContext());
                break;
            case R.id.search_btn:
                SearchActivity.start(getContext());
                break;
            case R.id.top_hospital_name:
                SelectHospitalPopUtil.getInstance().initPopWindow(getContext()).showHospitalPopupWindow(getView().getTitleLayout());
                break;
        }
    }

    //    @Override
    //    public void onScroll(int scrollY) {
    //        if (scrollY != 0) {
    //            getView().getTitleLayout().setBackgroundColor(ContextCompat.getColor(getContext
    //            (), R.color.status_bar_color_work));
    //        } else {
    //            getView().getTitleLayout().setBackgroundColor(ContextCompat.getColor(getContext
    //            (), R.color.tran));
    //        }
    //    }

    /**
     * 根据角色ID获取首页数据
     */
    public void getHome(boolean show) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("roleId", SharePreferenceManager.getRoleId());
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MainApiService.class).home(map), new HttpSubscriber<HomeBean>(getContext(),getDisposable(),show) {
            @Override
            public void requestComplete(@Nullable HomeBean data) {
                getView().getHomeDataSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                getView().getHomeDataFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 无权限提示弹窗
     */
    public void showNotAccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("未开通服务");
        builder.setMessage("您暂未开通该服务，无法使用这个功能！");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    public void getHomeCountMsg() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).homeCountMessage(String.valueOf(SharePreferenceManager.getRoleId())), new HttpSubscriber<HomeMsgBean>() {
            @Override
            public void requestComplete(@Nullable HomeMsgBean data) {
                getView().getHomeCountMsgSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY != 0) {
            getView().getTitleLayout().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.status_bar_color_work));
        } else {
            getView().getTitleLayout().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.tran));
        }
    }
}
