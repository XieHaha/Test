package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthArchivesController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthArchivesView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.HealthArchivesBean;
import com.keydom.mianren.ih_patient.bean.PatientRelativesBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 健康档案
 */
public class HealthArchivesActivity extends BaseControllerActivity<HealthArchivesController> implements HealthArchivesView {
    @BindView(R.id.health_archives_base_info_tv)
    TextView healthArchivesBaseInfoTv;
    @BindView(R.id.health_archives_base_info_layout)
    LinearLayout healthArchivesBaseInfoLayout;
    @BindView(R.id.health_archives_add_contact_tv)
    TextView healthArchivesAddContactTv;
    @BindView(R.id.health_archives_select_past_tv)
    TextView healthArchivesSelectPastTv;
    @BindView(R.id.health_archives_past_flow_layout)
    TagFlowLayout healthArchivesPastFlowLayout;
    @BindView(R.id.health_archives_genetic_tv)
    TextView healthArchivesGeneticTv;
    @BindView(R.id.health_archives_genetic_flow_layout)
    TagFlowLayout healthArchivesGeneticFlowLayout;
    @BindView(R.id.health_archives_add_surgery_tv)
    TextView healthArchivesAddSurgeryTv;
    @BindView(R.id.health_archives_look_more_tv)
    TextView healthArchivesLookMoreTv;
    @BindView(R.id.health_archives_drink)
    TextView healthArchivesDrink;
    @BindView(R.id.health_archives_non_drink)
    TextView healthArchivesNonDrink;
    @BindView(R.id.health_archives_drink_frequency_layout)
    LinearLayout healthArchivesDrinkFrequencyLayout;
    @BindView(R.id.health_archives_drink_quantity_layout)
    LinearLayout healthArchivesDrinkQuantityLayout;
    @BindView(R.id.health_archives_drink_year_layout)
    LinearLayout healthArchivesDrinkYearLayout;
    @BindView(R.id.health_archives_smoke)
    TextView healthArchivesSmoke;
    @BindView(R.id.health_archives_non_smoke)
    TextView healthArchivesNonSmoke;
    @BindView(R.id.health_archives_smoke_frequency_layout)
    LinearLayout healthArchivesSmokeFrequencyLayout;
    @BindView(R.id.health_archives_smoke_quantity_layout)
    LinearLayout healthArchivesSmokeQuantityLayout;
    @BindView(R.id.health_archives_smoke_year_layout)
    LinearLayout healthArchivesSmokeYearLayout;
    @BindView(R.id.health_contact_people_layout)
    LinearLayout healthContactPeopleLayout;

    private String patientId;

    /**
     * 健康档案数据集
     */
    private HealthArchivesBean archivesBean;
    /**
     * 紧急联系人
     */
    private List<PatientRelativesBean> relativesBeans;
    private Map<String, Object> params;

    /**
     * 当前编辑的紧急联系人
     */
    private int curRelationPosition = -1;


    /**
     * 启动
     */
    public static void start(Context context, String patientId, int isPerfect) {
        Intent intent = new Intent(context, HealthArchivesActivity.class);
        intent.putExtra(Const.PATIENT_ID, patientId);
        intent.putExtra("isPerfect", isPerfect);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_archives;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setTitle(R.string.txt_health_archives);
        setRightTxt(getString(R.string.save));
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                if (archivesBean == null) {
                    ToastUtil.showMessage(HealthArchivesActivity.this, "请完善个人信息");
                    return;
                }
                getController().savePatientInfo();
            }
        });

        patientId = getIntent().getStringExtra(Const.PATIENT_ID);
        int isPerfect = getIntent().getIntExtra("isPerfect", -1);
        if (isPerfect == 0) {
            healthArchivesBaseInfoTv.setVisibility(View.VISIBLE);
        } else {
            healthArchivesBaseInfoTv.setVisibility(View.INVISIBLE);
        }
        getController().getPatientInfo();

        healthArchivesBaseInfoLayout.setOnClickListener(getController());
        healthArchivesAddContactTv.setOnClickListener(getController());
        healthArchivesSelectPastTv.setOnClickListener(getController());
        healthArchivesGeneticTv.setOnClickListener(getController());
        healthArchivesAddSurgeryTv.setOnClickListener(getController());
        healthArchivesLookMoreTv.setOnClickListener(getController());
        healthArchivesDrink.setOnClickListener(getController());
        healthArchivesNonDrink.setOnClickListener(getController());
        healthArchivesDrinkFrequencyLayout.setOnClickListener(getController());
        healthArchivesDrinkQuantityLayout.setOnClickListener(getController());
        healthArchivesDrinkYearLayout.setOnClickListener(getController());
        healthArchivesSmoke.setOnClickListener(getController());
        healthArchivesNonSmoke.setOnClickListener(getController());
        healthArchivesSmokeFrequencyLayout.setOnClickListener(getController());
        healthArchivesSmokeQuantityLayout.setOnClickListener(getController());
        healthArchivesSmokeYearLayout.setOnClickListener(getController());
        healthArchivesPastFlowLayout.setVisibility(View.GONE);
        healthArchivesGeneticFlowLayout.setVisibility(View.GONE);
    }

    /**
     * 数据初始化
     */
    private void bindData() {
        //紧急联系人
        relativesBeans = archivesBean.getPatientRelatives();
        addRelationshipView();
    }

    /**
     * 开通健康管理
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateArchives(Event event) {
        if (event.getType() == EventType.UPDATE_ARCHIVES) {
            archivesBean = (HealthArchivesBean) event.getData();
            healthArchivesBaseInfoTv.setVisibility(View.INVISIBLE);
        } else if (event.getType() == EventType.UPDATE_RELATIONSHIP) {
            PatientRelativesBean relativesBean = (PatientRelativesBean) event.getData();
            initRelationshipData(relativesBean);
        }
    }

    /**
     * 紧急联系人
     */
    private void initRelationshipData(PatientRelativesBean relativesBean) {
        relativesBean.setPatientId(archivesBean.getPatientId());
        if (curRelationPosition != -1) {
            relativesBeans.set(curRelationPosition, relativesBean);
        } else {
            relativesBeans.add(relativesBean);
        }
        archivesBean.setPatientRelatives(relativesBeans);
        addRelationshipView();
    }

    @Override
    public void setCurRelationPosition(int curRelationPosition) {
        this.curRelationPosition = curRelationPosition;
    }

    /**
     * 添加紧急联系人布局
     */
    private void addRelationshipView() {
        healthContactPeopleLayout.removeAllViews();
        for (int i = 0; i < relativesBeans.size(); i++) {
            PatientRelativesBean bean = relativesBeans.get(i);
            View view = getLayoutInflater().inflate(R.layout.item_health_contact_people, null);
            TextView name = view.findViewById(R.id.item_relation_name_tv);
            TextView relation = view.findViewById(R.id.item_relation_ship_tv);
            TextView phone = view.findViewById(R.id.item_relation_phone_tv);
            TextView delete = view.findViewById(R.id.item_relation_delete_tv);
            name.setText(bean.getName());
            relation.setText(bean.getRelation());
            phone.setText(bean.getPhoneNumber());
            int finalI = i;
            view.setOnClickListener(v -> {
                curRelationPosition = finalI;
                HealthContactActivity.start(this, bean);
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    relativesBeans.remove(bean);
                    healthContactPeopleLayout.removeView(view);
                }
            });
            healthContactPeopleLayout.addView(view);
        }
    }

    @Override
    public HealthArchivesBean getArchivesBean() {
        return archivesBean;
    }

    @Override
    public Map<String, Object> getParams() {
        params = new HashMap<>();
        params.put("id", archivesBean.getPatientId());
        params.put("name", archivesBean.getName());
        params.put("idCard", archivesBean.getIdCard());
        params.put("height", archivesBean.getHeight());
        params.put("weight", archivesBean.getWeight());
        params.put("address", archivesBean.getAddress());
        params.put("workUnits", archivesBean.getWorkUnits());
        params.put("sex", archivesBean.getSex());
        params.put("bmi", archivesBean.getBmi());
        params.put("birthDate", archivesBean.getBirthDate());
        params.put("phoneNumber", archivesBean.getPhoneNumber());
        params.put("professionalCategory", archivesBean.getProfessionalCategory());
        params.put("nation", archivesBean.getNation());
        params.put("maritalHistory", archivesBean.getMaritalHistory());
        params.put("fertilityStatus", archivesBean.getFertilityStatus());
        params.put("patientRelatives", archivesBean.getPatientRelatives());
        return params;
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public void getPatientInfoSuccess(HealthArchivesBean data) {
        archivesBean = data;
        bindData();
    }

    @Override
    public void savePatientInfoSuccess() {
        ToastUtil.showMessage(this, "保存成功");
        EventBus.getDefault().post(new Event(EventType.OPEN_HEALTH_MANAGER, null));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
