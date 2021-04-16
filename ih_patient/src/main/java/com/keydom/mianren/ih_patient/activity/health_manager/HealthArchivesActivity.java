package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthArchivesController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthArchivesView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.HealthArchivesBean;
import com.keydom.mianren.ih_patient.bean.PatientRelativesBean;
import com.keydom.mianren.ih_patient.bean.PatientSurgeryHistoryBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    @BindView(R.id.health_archives_drink_root_layout)
    LinearLayout healthArchivesDrinkRootLayout;
    @BindView(R.id.health_archives_smoke_root_layout)
    LinearLayout healthArchivesSmokeRootLayout;
    @BindView(R.id.health_contact_people_layout)
    LinearLayout healthContactPeopleLayout;
    @BindView(R.id.health_surgery_history_layout)
    LinearLayout healthSurgeryHistoryLayout;
    @BindView(R.id.health_archives_drink_frequency_tv)
    TextView healthArchivesDrinkFrequencyTv;
    @BindView(R.id.health_archives_drink_quantity_tv)
    TextView healthArchivesDrinkQuantityTv;
    @BindView(R.id.health_archives_drink_year_tv)
    TextView healthArchivesDrinkYearTv;
    @BindView(R.id.health_archives_smoke_frequency_tv)
    TextView healthArchivesSmokeFrequencyTv;
    @BindView(R.id.health_archives_smoke_quantity_tv)
    TextView healthArchivesSmokeQuantityTv;
    @BindView(R.id.health_archives_smoke_year_tv)
    TextView healthArchivesSmokeYearTv;

    private String patientId;

    /**
     * 健康档案数据集
     */
    private HealthArchivesBean archivesBean;
    /**
     * 紧急联系人
     */
    private List<PatientRelativesBean> relativesBeans;
    /**
     * 手术史
     */
    private List<PatientSurgeryHistoryBean> surgeryHistoryBeans;
    private List<String> drinkDegreeData;
    private List<String> drinkNumData;
    private List<String> smokeDegreeData;
    private List<String> smokeNumData;
    private List<String> drinkOrSmokeYearData;
    /**
     * 既往病史   遗传病史
     */
    private String selectMedical, selectGenetic;
    private Map<String, Object> params;

    /**
     * 当前编辑的紧急联系人
     */
    private int curRelationPosition = -1;
    /**
     * 当前编辑的手术史
     */
    private int curSurgeryPosition = -1;

    /**
     * 页面更新提示
     */
    private boolean isUpdate = false;

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
        setLeftBtnListener(v -> finishPage());

        localData();

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
    }

    /**
     * 本地数据加载
     */
    private void localData() {
        String[] drinkDegree = getResources().getStringArray(R.array.drink_degree);
        drinkDegreeData = new ArrayList<>(drinkDegree.length);
        Collections.addAll(drinkDegreeData, drinkDegree);
        String[] drinkNum = getResources().getStringArray(R.array.drink_num);
        drinkNumData = new ArrayList<>(drinkNum.length);
        Collections.addAll(drinkNumData, drinkNum);

        String[] smokeDegree = getResources().getStringArray(R.array.smoke_degree);
        smokeDegreeData = new ArrayList<>(smokeDegree.length);
        Collections.addAll(smokeDegreeData, smokeDegree);
        String[] smokeNum = getResources().getStringArray(R.array.smoke_num);
        smokeNumData = new ArrayList<>(smokeNum.length);
        Collections.addAll(smokeNumData, smokeNum);

        String[] year = getResources().getStringArray(R.array.drink_or_smoke_year);
        drinkOrSmokeYearData = new ArrayList<>(year.length);
        Collections.addAll(drinkOrSmokeYearData, year);
    }

    /**
     * 数据初始化
     */
    private void bindData() {
        //既往病史
        selectMedical = archivesBean.getMedicalHistory();
        if (!TextUtils.isEmpty(selectMedical)) {
            setMedicalFlows();
        }
        //遗传病史
        selectGenetic = archivesBean.getGeneticHistory();
        if (!TextUtils.isEmpty(selectGenetic)) {
            setGeneticFlows();
        }
        //紧急联系人
        relativesBeans = archivesBean.getPatientRelatives();
        addRelationshipView();
        //手术史
        surgeryHistoryBeans = archivesBean.getPatientSurgeryHistories();
        addSurgeryHistoryView();

        //吸烟饮酒
        setSelectDrink(archivesBean.getIsDrink());
        healthArchivesDrinkFrequencyTv.setText(archivesBean.getDrinkDegree());
        healthArchivesDrinkQuantityTv.setText(archivesBean.getDrinkMl());
        healthArchivesDrinkYearTv.setText(archivesBean.getDrinkYear());
        setSelectSmoke(archivesBean.getIsSmoke());
        healthArchivesSmokeFrequencyTv.setText(archivesBean.getSmokeDegree());
        healthArchivesSmokeQuantityTv.setText(archivesBean.getSmokeAmount());
        healthArchivesSmokeYearTv.setText(archivesBean.getSmokeYear());
    }

    /**
     * 健康管理
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateArchives(Event event) {
        isUpdate = true;
        if (event.getType() == EventType.UPDATE_ARCHIVES) {
            archivesBean = (HealthArchivesBean) event.getData();
            healthArchivesBaseInfoTv.setVisibility(View.INVISIBLE);
        } else if (event.getType() == EventType.UPDATE_RELATIONSHIP) {
            PatientRelativesBean relativesBean = (PatientRelativesBean) event.getData();
            initRelationshipData(relativesBean);
        } else if (event.getType() == EventType.UPDATE_MEDICAL_PAST) {
            selectMedical = (String) event.getData();
            archivesBean.setMedicalHistory(selectMedical);
            setMedicalFlows();
        } else if (event.getType() == EventType.UPDATE_GENETIC_PAST) {
            selectGenetic = (String) event.getData();
            archivesBean.setGeneticHistory(selectGenetic);
            setGeneticFlows();
        } else if (event.getType() == EventType.UPDATE_SURGERY_HISTORY) {
            PatientSurgeryHistoryBean historyBean = (PatientSurgeryHistoryBean) event.getData();
            initSurgeryHistoryData(historyBean);
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

    /**
     * 手术史
     */
    private void initSurgeryHistoryData(PatientSurgeryHistoryBean historyBean) {
        historyBean.setPatientId(archivesBean.getPatientId());
        if (curSurgeryPosition != -1) {
            surgeryHistoryBeans.set(curSurgeryPosition, historyBean);
        } else {
            surgeryHistoryBeans.add(historyBean);
        }
        archivesBean.setPatientSurgeryHistories(surgeryHistoryBeans);
        addSurgeryHistoryView();
    }

    @Override
    public void setCurSurgeryPosition(int curSurgeryPosition) {
        this.curSurgeryPosition = curSurgeryPosition;
    }

    @Override
    public void setCurRelationPosition(int curRelationPosition) {
        this.curRelationPosition = curRelationPosition;
    }

    /**
     * 添加紧急联系人布局
     */
    private void addRelationshipView() {
        if (relativesBeans == null) {
            return;
        }
        healthContactPeopleLayout.removeAllViews();
        int showNum = relativesBeans.size();
        if (relativesBeans.size() > 3) {
            healthArchivesLookMoreTv.setVisibility(View.VISIBLE);
            showNum = 3;
        } else {
            healthArchivesLookMoreTv.setVisibility(View.GONE);
        }
        for (int i = 0; i < showNum; i++) {
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

    /**
     * 手术史
     */
    private void addSurgeryHistoryView() {
        if (surgeryHistoryBeans == null) {
            return;
        }
        healthSurgeryHistoryLayout.removeAllViews();
        for (int i = 0; i < surgeryHistoryBeans.size(); i++) {
            PatientSurgeryHistoryBean bean = surgeryHistoryBeans.get(i);
            View view = getLayoutInflater().inflate(R.layout.item_health_add_surgery, null);
            TextView name = view.findViewById(R.id.item_surgery_history_name_tv);
            TextView date = view.findViewById(R.id.item_surgery_history_date_tv);
            TextView status = view.findViewById(R.id.item_surgery_history_status_tv);
            name.setText(bean.getName());
            date.setText(bean.getSurgeryDate());
            status.setText(bean.getSurgeryStatus());
            int finalI = i;
            view.setOnClickListener(v -> {
                curSurgeryPosition = finalI;
                HealthAddSurgeryActivity.start(this, bean);
            });
            healthSurgeryHistoryLayout.addView(view);
        }
    }

    /**
     * 既往病史
     */
    private void setMedicalFlows() {
        List<String> list = Arrays.asList(selectMedical.split(","));
        TagAdapter<String> tagAdapter = new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView tv =
                        (TextView) getLayoutInflater().inflate(R.layout.view_health_manager_unselect_flow,
                                healthArchivesPastFlowLayout, false);
                tv.setText(o);
                return tv;
            }
        };
        healthArchivesPastFlowLayout.setAdapter(tagAdapter);
        healthArchivesSelectPastTv.setText(R.string.txt_edit_past_medical);
    }

    /**
     * 遗传病史
     */
    private void setGeneticFlows() {
        List<String> list = Arrays.asList(selectGenetic.split(","));
        TagAdapter<String> tagAdapter = new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView tv =
                        (TextView) getLayoutInflater().inflate(R.layout.view_health_manager_unselect_flow,
                                healthArchivesGeneticFlowLayout, false);
                tv.setText(o);
                return tv;
            }
        };
        healthArchivesGeneticFlowLayout.setAdapter(tagAdapter);
        healthArchivesGeneticTv.setText(R.string.txt_edit_past_medical);
    }

    @Override
    public HealthArchivesBean getArchivesBean() {
        return archivesBean;
    }

    @Override
    public List<String> getDrinkDegreeData() {
        return drinkDegreeData;
    }

    @Override
    public List<String> getDrinkNumData() {
        return drinkNumData;
    }

    @Override
    public List<String> getSmokeDegreeData() {
        return smokeDegreeData;
    }

    @Override
    public List<String> getSmokeNumData() {
        return smokeNumData;
    }

    @Override
    public List<String> getDrinkOrSmokeYearData() {
        return drinkOrSmokeYearData;
    }


    @Override
    public void setSelectDrink(int type) {
        archivesBean.setIsDrink(type);
        if (type == 0) {
            healthArchivesDrink.setSelected(false);
            healthArchivesNonDrink.setSelected(true);
            healthArchivesDrinkRootLayout.setVisibility(View.GONE);
        } else {
            healthArchivesDrink.setSelected(true);
            healthArchivesNonDrink.setSelected(false);
            healthArchivesDrinkRootLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setDrinkDegree(int position) {
        isUpdate = true;
        healthArchivesDrinkFrequencyTv.setText(drinkDegreeData.get(position));
        archivesBean.setDrinkDegree(drinkDegreeData.get(position));
    }

    @Override
    public void setDrinkNum(int position) {
        isUpdate = true;
        healthArchivesDrinkQuantityTv.setText(drinkNumData.get(position));
        archivesBean.setDrinkMl(drinkNumData.get(position));

    }

    @Override
    public void setDrinkYear(int position) {
        isUpdate = true;
        healthArchivesDrinkYearTv.setText(drinkOrSmokeYearData.get(position));
        archivesBean.setDrinkYear(drinkOrSmokeYearData.get(position));
    }

    @Override
    public void setSelectSmoke(int type) {
        archivesBean.setIsSmoke(type);
        if (type == 0) {
            healthArchivesSmoke.setSelected(false);
            healthArchivesNonSmoke.setSelected(true);
            healthArchivesSmokeRootLayout.setVisibility(View.GONE);
        } else {
            healthArchivesSmoke.setSelected(true);
            healthArchivesNonSmoke.setSelected(false);
            healthArchivesSmokeRootLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setSmokeDegree(int position) {
        isUpdate = true;
        healthArchivesSmokeFrequencyTv.setText(smokeDegreeData.get(position));
        archivesBean.setSmokeDegree(smokeDegreeData.get(position));

    }

    @Override
    public void setSmokeNum(int position) {
        isUpdate = true;
        healthArchivesSmokeQuantityTv.setText(smokeNumData.get(position));
        archivesBean.setSmokeAmount(smokeNumData.get(position));

    }

    @Override
    public void setSmokeYear(int position) {
        isUpdate = true;
        healthArchivesSmokeYearTv.setText(drinkOrSmokeYearData.get(position));
        archivesBean.setSmokeYear(drinkOrSmokeYearData.get(position));
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
        params.put("medicalHistory", archivesBean.getMedicalHistory());
        params.put("geneticHistory", archivesBean.getGeneticHistory());
        params.put("patientSurgeryHistories", archivesBean.getPatientSurgeryHistories());
        params.put("isDrink", archivesBean.getIsDrink());
        params.put("drinkDegree", archivesBean.getDrinkDegree());
        params.put("drinkMl", archivesBean.getDrinkMl());
        params.put("drinkYear", archivesBean.getDrinkYear());
        params.put("isSmoke", archivesBean.getIsSmoke());
        params.put("smokeDegree", archivesBean.getSmokeDegree());
        params.put("smokeAmount", archivesBean.getSmokeAmount());
        params.put("smokeYear", archivesBean.getSmokeYear());
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
        EventBus.getDefault().post(new Event(EventType.UPDATE_HEALTH_MANAGER, null));
        finish();
    }

    private void finishPage() {
        if (isUpdate) {
            new GeneralDialog(getContext(), "您还未保存，是否退出？",
                    () -> finish()).setTitle("提示").setPositiveButton("确认").show();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finishPage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
