package com.keydom.mianren.ih_patient.activity.health_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.health_manager.controller.HealthContactController;
import com.keydom.mianren.ih_patient.activity.health_manager.view.HealthContactView;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.PatientRelativesBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/4 10:54
 * @des 紧急联系人
 */
public class HealthContactActivity extends BaseControllerActivity<HealthContactController> implements HealthContactView {
    @BindView(R.id.health_contact_relationship_tv)
    TextView healthContactRelationshipTv;
    @BindView(R.id.health_contact_name_et)
    InterceptorEditText healthContactNameEt;
    @BindView(R.id.health_contact_phone_et)
    InterceptorEditText healthContactPhoneEt;

    private PatientRelativesBean relativesBean;

    private List<String> relationshipData;

    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, HealthContactActivity.class));
    }

    /**
     * 更新
     */
    public static void start(Context context, PatientRelativesBean relativesBean) {
        Intent intent = new Intent(context, HealthContactActivity.class);
        intent.putExtra(Const.DATA, relativesBean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_health_contact;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.txt_add_relatives);
        setRightTxt(getString(R.string.save));
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                saveData();
            }
        });
        localData();
        relativesBean = (PatientRelativesBean) getIntent().getSerializableExtra(Const.DATA);
        if (relativesBean != null) {
            healthContactRelationshipTv.setText(relativesBean.getRelation());
            healthContactNameEt.setText(relativesBean.getName());
            healthContactPhoneEt.setText(relativesBean.getPhoneNumber());
        } else {
            relativesBean = new PatientRelativesBean();
        }

        healthContactRelationshipTv.setOnClickListener(getController());
    }

    /**
     * 本地数据获取
     */
    private void localData() {
        String[] jobs = getResources().getStringArray(R.array.contact_people);
        relationshipData = new ArrayList<>(jobs.length);
        Collections.addAll(relationshipData, jobs);
    }


    private void saveData() {
        String relationship = healthContactRelationshipTv.getText().toString();
        String name = healthContactNameEt.getText().toString();
        String phone = healthContactPhoneEt.getText().toString();
        if (TextUtils.isEmpty(relationship) || TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            ToastUtil.showMessage(this, "请完善信息");
            return;
        }
        relativesBean.setName(name);
        relativesBean.setPhoneNumber(phone);
        relativesBean.setRelation(relationship);

        EventBus.getDefault().post(new Event(EventType.UPDATE_RELATIONSHIP, relativesBean));
        finish();
    }

    @Override
    public void setRelationship(int position) {
        healthContactRelationshipTv.setText(relationshipData.get(position));
    }

    @Override
    public List<String> getRelationshipData() {
        return relationshipData;
    }
}
