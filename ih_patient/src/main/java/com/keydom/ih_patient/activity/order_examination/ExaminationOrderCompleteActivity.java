package com.keydom.ih_patient.activity.order_examination;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_examination.controller.ExaminationOrderCompleteController;
import com.keydom.ih_patient.activity.order_examination.view.ExaminationOrderCompleteView;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

/**
 * 预约完成页面
 */
public class ExaminationOrderCompleteActivity extends BaseControllerActivity<ExaminationOrderCompleteController> implements ExaminationOrderCompleteView {

    /**
     * 启动
     */
    public static void start(Context context, String projectName, String projectDetail){
        Intent intent=new Intent(context,ExaminationOrderCompleteActivity.class);
        intent.putExtra("projectName",projectName);
        intent.putExtra("projectDetail",projectDetail);
        context.startActivity(intent);
    }
    private TextView exa_name,exa_detail_tv;
    /**
     * 项目名称，项目详情
     */
    private String projectName,projectDetail;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_examination_order_complete_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("检查预约");
        projectName=getIntent().getStringExtra("projectName");
        projectDetail=getIntent().getStringExtra("projectDetail");
        exa_name=this.findViewById(R.id.exa_name);
        exa_detail_tv=this.findViewById(R.id.exa_detail_tv);
        exa_name.setText(projectName);
        exa_detail_tv.setText(projectDetail);
    }

    @Override
    public void finish() {
        EventBus.getDefault().post(new Event(EventType.UPLOADEXAMINATION,null));
        super.finish();
    }
}
