package com.keydom.mianren.ih_patient.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.keydom.ih_common.utils.ActivityUtil;
import com.keydom.mianren.ih_patient.activity.get_drug.GetDrugActivity;
import com.keydom.mianren.ih_patient.activity.get_drug.ZxingActivity;
import com.keydom.mianren.ih_patient.activity.logistic.LogisticDetailActivity;
import com.keydom.mianren.ih_patient.activity.logistic.QueryLogisticActivity;
import com.keydom.mianren.ih_patient.activity.logistic.TestPayActivity;
import com.keydom.mianren.ih_patient.activity.prescription.ChoosePharmacyActivity;
import com.keydom.mianren.ih_patient.activity.prescription.PrescriptionGetDetailActivity;

public class GotoActivityUtil {
    /**
     * 进入取药收药界面
     *
     * @param activity
     */
    public static void gotoGetDrugActivity(Activity activity) {
        ActivityUtil.next(activity, GetDrugActivity.class);
    }
    /**
     * 进入二维码界面
     * @param activity
     */
    public static void gotoZxingActivity(Activity activity, String entity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ZxingActivity.ENTITY, entity);
        ActivityUtil.next(activity, ZxingActivity.class,bundle);
    }

    /**
     * 进入物流查询界面
     */
    public static void gotoQueryLogisticActivity(Activity activity) {
        ActivityUtil.next(activity, QueryLogisticActivity.class);
    }

    /**
     * 进入物流详情界面
     */
    public static void gotoLogisticDetailActivity(Activity activity, String id) {
        Bundle bundle = new Bundle();
        bundle.putString(LogisticDetailActivity.ID, id);
        ActivityUtil.next(activity, LogisticDetailActivity.class, bundle);
    }

    /**
     * 进入测试支付界面
     */
    public static void gotoTestPayActivity(Activity activity) {
        ActivityUtil.next(activity, TestPayActivity.class);
    }

    /**
     * 进入选择药店界面
     */
    public static void gotoChoosePharmacyActivity(Activity activity,String id) {
        Intent i = new Intent(activity, ChoosePharmacyActivity.class);
        i.putExtra(ChoosePharmacyActivity.ID, id);
        ActivityUtils.startActivity(i);
    }



    /**
     * 进入处方详情界面
     */
    public static void gotoPrescriptionGetDetailActivity(Activity activity, String prescriptionId,int acquireMedicine) {
        Bundle bundle = new Bundle();
        bundle.putString(PrescriptionGetDetailActivity.PRESCRIPTION_ID, prescriptionId);
        bundle.putInt(PrescriptionGetDetailActivity.ACQUIRE_MEDICINE, acquireMedicine);
        ActivityUtil.next(activity, PrescriptionGetDetailActivity.class, bundle);
    }
}

