package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller.AmniocentesisDetailController;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisDetailView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * @date 20/3/11 15:25
 * @des 羊水穿刺预约详情
 */
public class AmniocentesisDetailActivity extends BaseControllerActivity<AmniocentesisDetailController> implements AmniocentesisDetailView {
    @BindView(R.id.amniocentesis_detail_status_tv)
    TextView amniocentesisDetailStatusTv;
    @BindView(R.id.amniocentesis_detail_surgery_time_tv)
    TextView amniocentesisDetailSurgeryTimeTv;
    @BindView(R.id.amniocentesis_detail_name_tv)
    TextView amniocentesisDetailNameTv;
    @BindView(R.id.amniocentesis_detail_id_card_tv)
    TextView amniocentesisDetailIdCardTv;
    @BindView(R.id.amniocentesis_detail_birth_tv)
    TextView amniocentesisDetailBirthTv;
    @BindView(R.id.amniocentesis_detail_mine_phone_tv)
    TextView amniocentesisDetailMinePhoneTv;
    @BindView(R.id.amniocentesis_detail_last_menstruation_tv)
    TextView amniocentesisDetailLastMenstruationTv;
    @BindView(R.id.amniocentesis_detail_due_date_tv)
    TextView amniocentesisDetailDueDateTv;
    @BindView(R.id.amniocentesis_detail_family_name_tv)
    TextView amniocentesisDetailFamilyNameTv;
    @BindView(R.id.amniocentesis_detail_family_phone_tv)
    TextView amniocentesisDetailFamilyPhoneTv;
    @BindView(R.id.amniocentesis_detail_family_address_tv)
    TextView amniocentesisDetailFamilyAddressTv;
    @BindView(R.id.amniocentesis_detail_hospital_tv)
    TextView amniocentesisDetailHospitalTv;
    @BindView(R.id.amniocentesis_detail_reason_tv)
    TextView amniocentesisDetailReasonTv;
    @BindView(R.id.amniocentesis_detail_fetus_tv)
    TextView amniocentesisDetailFetusTv;
    @BindView(R.id.amniocentesis_detail_hiv_tv)
    TextView amniocentesisDetailHivTv;
    @BindView(R.id.amniocentesis_detail_blood_tv)
    TextView amniocentesisDetailBloodTv;
    @BindView(R.id.amniocentesis_detail_hypertension_tv)
    TextView amniocentesisDetailHypertensionTv;
    @BindView(R.id.amniocentesis_detail_diabetes_tv)
    TextView amniocentesisDetailDiabetesTv;
    @BindView(R.id.amniocentesis_detail_next_tv)
    TextView amniocentesisDetailNextTv;

    private int amniocentesisId;

    public static final String AMNIOCENTESIS_ID = "amniocentesis_id";

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, AmniocentesisDetailActivity.class);
        intent.putExtra(AMNIOCENTESIS_ID, id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_amniocentesis_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_amniocentesis_record_cancel));

        if (getIntent() != null) {
            amniocentesisId = getIntent().getIntExtra(AMNIOCENTESIS_ID, -1);
        }

        pageLoading();
        getController().getAmniocentesisDetail(amniocentesisId);

        amniocentesisDetailNextTv.setOnClickListener(v -> finish());
        setReloadListener((v, status) -> {
            pageLoading();
            getController().getAmniocentesisDetail(amniocentesisId);
        });
    }

    /**
     * 数据绑定
     */
    private void bindData() {
        amniocentesisDetailStatusTv.setText("数据");
        amniocentesisDetailSurgeryTimeTv.setText("数据");
        amniocentesisDetailNameTv.setText("数据");
        amniocentesisDetailIdCardTv.setText("数据");
        amniocentesisDetailBirthTv.setText("数据");
        amniocentesisDetailMinePhoneTv.setText("数据");
        amniocentesisDetailLastMenstruationTv.setText("数据");
        amniocentesisDetailDueDateTv.setText("数据");
        amniocentesisDetailFamilyNameTv.setText("数据");
        amniocentesisDetailFamilyPhoneTv.setText("数据");
        amniocentesisDetailFamilyAddressTv.setText("数据");
        amniocentesisDetailHospitalTv.setText("数据");
        amniocentesisDetailReasonTv.setText("数据");
        amniocentesisDetailFetusTv.setText("数据");
        amniocentesisDetailHivTv.setText("数据");
        amniocentesisDetailBloodTv.setText("数据");
        amniocentesisDetailHypertensionTv.setText("数据");
        amniocentesisDetailDiabetesTv.setText("数据");
    }

    @Override
    public void onAmniocentesisDetailSuccess() {
        pageLoadingSuccess();
        bindData();
    }

    @Override
    public void onAmniocentesisDetailFailed() {
        pageLoadingFail();
    }
}
