package com.keydom.ih_patient.activity.reserve_amniocentesis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.reserve_amniocentesis.controller.AmniocentesisApplyController;
import com.keydom.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisApplyView;

import butterknife.BindView;

/**
 * @date 20/3/9 16:07
 * @des 羊水穿刺预约申请
 */
public class AmniocentesisApplyActivity extends BaseControllerActivity<AmniocentesisApplyController> implements AmniocentesisApplyView {
    @BindView(R.id.amniocentesis_apply_surgery_time_tv)
    TextView amniocentesisApplySurgeryTimeTv;
    @BindView(R.id.amniocentesis_apply_surgery_time_layout)
    LinearLayout amniocentesisApplySurgeryTimeLayout;
    @BindView(R.id.amniocentesis_apply_name_et)
    InterceptorEditText amniocentesisApplyNameEt;
    @BindView(R.id.amniocentesis_apply_id_card_et)
    InterceptorEditText amniocentesisApplyIdCardEt;
    @BindView(R.id.amniocentesis_apply_birth_tv)
    TextView amniocentesisApplyBirthTv;
    @BindView(R.id.amniocentesis_apply_birth_layout)
    LinearLayout amniocentesisApplyBirthLayout;
    @BindView(R.id.amniocentesis_apply_verify_code_et)
    InterceptorEditText amniocentesisApplyVerifyCodeEt;
    @BindView(R.id.amniocentesis_apply_last_menstruation_tv)
    TextView amniocentesisApplyLastMenstruationTv;
    @BindView(R.id.amniocentesis_apply_last_menstruation_layout)
    LinearLayout amniocentesisApplyLastMenstruationLayout;
    @BindView(R.id.amniocentesis_apply_due_date_tv)
    TextView amniocentesisApplyDueDateTv;
    @BindView(R.id.amniocentesis_apply_due_date_layout)
    LinearLayout amniocentesisApplyDueDateLayout;
    @BindView(R.id.amniocentesis_apply_family_name_et)
    InterceptorEditText amniocentesisApplyFamilyNameEt;
    @BindView(R.id.amniocentesis_apply_family_phone_et)
    InterceptorEditText amniocentesisApplyFamilyPhoneEt;
    @BindView(R.id.amniocentesis_apply_family_address_et)
    InterceptorEditText amniocentesisApplyFamilyAddressEt;
    @BindView(R.id.amniocentesis_apply_transfer_hospital_et)
    InterceptorEditText amniocentesisApplyTransferHospitalEt;
    @BindView(R.id.amniocentesis_apply_recycler_view)
    RecyclerView amniocentesisApplyRecyclerView;
    @BindView(R.id.amniocentesis_apply_next_tv)
    TextView amniocentesisApplyNextTv;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, AmniocentesisApplyActivity.class));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_amniocentesis_apply;
    }

}
