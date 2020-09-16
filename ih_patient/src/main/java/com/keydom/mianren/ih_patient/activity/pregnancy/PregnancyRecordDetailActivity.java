package com.keydom.mianren.ih_patient.activity.pregnancy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.pregnancy.controller.PregnancyRecordDetailController;
import com.keydom.mianren.ih_patient.activity.pregnancy.view.PregnancyRecordDetailView;
import com.keydom.mianren.ih_patient.adapter.PregnancyDetailItemAdapter;
import com.keydom.mianren.ih_patient.bean.PregnancyItemBean;
import com.keydom.mianren.ih_patient.bean.PregnancyRecordBean;
import com.keydom.mianren.ih_patient.constant.Const;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * created date: 2019/1/4 on 13:00
 * des:产检记录页面
 *
 * @author 顿顿
 */
public class PregnancyRecordDetailActivity extends BaseControllerActivity<PregnancyRecordDetailController> implements PregnancyRecordDetailView {
    @BindView(R.id.pregnancy_record_detail_name)
    TextView pregnancyRecordDetailName;
    @BindView(R.id.pregnancy_record_detail_due_date)
    TextView pregnancyRecordDetailDueDate;
    @BindView(R.id.pregnancy_record_detail_date)
    TextView pregnancyRecordDetailDate;
    @BindView(R.id.pregnancy_record_detail_recycler)
    RecyclerView recyclerView;

    private PregnancyDetailItemAdapter itemAdapter;

    private PregnancyRecordBean recordBean;

    private List<PregnancyItemBean> itemBeans;

    private String[] keys = {"孕周", "体重", "增加体重", "心率", "血压", "宫高", "腹围", "胎心", "胎动", "妊娠分级", "水肿"
            , "尿蛋白", "其他"};
    private String[] units = {"周", "kg", "kg", "次/分钟", "mmhg", "cm", "cm", "次/分钟", "", "", "", ""
            , ""};

    public static void start(Context context, PregnancyRecordBean bean) {
        Intent intent = new Intent(context, PregnancyRecordDetailActivity.class);
        intent.putExtra(Const.DATA, bean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_pregnancy_record_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("产检病历详情");
        recordBean = (PregnancyRecordBean) getIntent().getSerializableExtra(Const.DATA);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        if (recordBean != null) {
            pregnancyRecordDetailName.setText(recordBean.getName());
            pregnancyRecordDetailDueDate.setText(recordBean.getDueDate());
            pregnancyRecordDetailDate.setText(recordBean.getCheckDate());
            itemBeans = new ArrayList<>();

            String[] values = {recordBean.getPregnancyWeek(), recordBean.getWeight(),
                    recordBean.getAddWeight(), recordBean.getHeartRate(),
                    recordBean.getBloodPressure(), recordBean.getHighPalace(),
                    recordBean.getAbdominalGirth(), recordBean.getFetalHeart(),
                    recordBean.getFetalMovement(), String.valueOf(recordBean.getLevel()),
                    recordBean.getEdema(), recordBean.getUrineProtein(), recordBean.getOther()};
            for (int i = 0; i < keys.length; i++) {
                PregnancyItemBean bean = new PregnancyItemBean();
                bean.setName(keys[i]);
                bean.setUnit(units[i]);
                bean.setValue(values[i]);
                itemBeans.add(bean);
            }
            itemAdapter = new PregnancyDetailItemAdapter(itemBeans);
            recyclerView.setAdapter(itemAdapter);
        }
    }

}
