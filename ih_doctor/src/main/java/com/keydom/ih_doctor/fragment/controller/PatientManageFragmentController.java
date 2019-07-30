package com.keydom.ih_doctor.fragment.controller;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.SearchActivity;
import com.keydom.ih_doctor.activity.patient_manage.NewGroupChatActivity;
import com.keydom.ih_doctor.activity.patient_manage.TentativeDiagnosisActivity;
import com.keydom.ih_doctor.activity.patient_manage.WarrantListActivity;
import com.keydom.ih_doctor.fragment.view.PatientManageFragmentView;
import com.keydom.ih_doctor.utils.SelectHospitalPopUtil;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/14 上午10:56
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:56
 */
public class PatientManageFragmentController extends ControllerImpl<PatientManageFragmentView> implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.authorize_rl:
                if (MyApplication.accessInfoBean != null && MyApplication.accessInfoBean.getAccess() == 1) {
                    WarrantListActivity.start(getContext());
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("无法授权");
                    builder.setMessage("团队负责人才可以进行授权！");
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.create().show();
                }
                break;
            case R.id.create_group_rl:
                NewGroupChatActivity.start(getContext());
                break;
            case R.id.search_btn:
                SearchActivity.start(getContext());
                break;
            case R.id.top_hospital_name:
                SelectHospitalPopUtil.getInstance().initPopWindow(getContext()).showHospitalPopupWindow(getView().getTitleLayout());
                break;
            default:
        }
    }
}
