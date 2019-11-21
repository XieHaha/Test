package com.keydom.ih_patient.activity.order_doctor_register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.order_doctor_register.controller.RegistrationRecordDetailController;
import com.keydom.ih_patient.activity.order_doctor_register.view.RegistrationRecordDetailView;
import com.keydom.ih_patient.bean.RegistrationRecordInfo;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;

import org.jetbrains.annotations.Nullable;

/**
 * 预约详情页面
 */
public class RegistrationRecordDetailActivity extends BaseControllerActivity<RegistrationRecordDetailController> implements RegistrationRecordDetailView {

    private TextView register_record_num_tv, register_name_tv, register_time_tv, register_department_tv, register_doctor_tv, register_address_tv, register_serial_number_tv, register_remark_tv,hint_tv;
    private RegistrationRecordInfo registrationRecordInfo;

    /**
     * 启动
     */
    public static void start(Context context, RegistrationRecordInfo registrationRecordInfo) {
        Intent intent = new Intent(context, RegistrationRecordDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("recordInfo", registrationRecordInfo);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_registration_record_detail_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("挂号记录");
        RichText.initCacheDir(this);
        registrationRecordInfo = (RegistrationRecordInfo) getIntent().getSerializableExtra("recordInfo");
        register_record_num_tv = this.findViewById(R.id.register_record_num_tv);
        register_name_tv = this.findViewById(R.id.register_name_tv);
        register_time_tv = this.findViewById(R.id.register_time_tv);
        register_department_tv = this.findViewById(R.id.register_department_tv);
        register_doctor_tv = this.findViewById(R.id.register_doctor_tv);
        register_address_tv = this.findViewById(R.id.register_address_tv);
        register_serial_number_tv = this.findViewById(R.id.register_serial_number_tv);
        register_remark_tv = this.findViewById(R.id.register_remark_tv);
        hint_tv=this.findViewById(R.id.hint_tv);
        if (registrationRecordInfo != null)
            getRegistrationRecordDetailSuccess(registrationRecordInfo);
    }

    @Override
    public void getRegistrationRecordDetailSuccess(RegistrationRecordInfo registrationRecordInfo) {
        register_record_num_tv.setText(registrationRecordInfo.getOrderNo());
        register_name_tv.setText(registrationRecordInfo.getName());
        register_time_tv.setText(registrationRecordInfo.getRegistrationDate()+"("+registrationRecordInfo.getTimeDesc()+")");
        register_department_tv.setText(registrationRecordInfo.getDept());
        register_doctor_tv.setText(registrationRecordInfo.getDoctor());
        register_address_tv.setText(registrationRecordInfo.getAddress());
        register_serial_number_tv.setText(registrationRecordInfo.getNumber());
        register_remark_tv.setText(registrationRecordInfo.getRemark());
        String hint_content="1、预约号最迟应于就诊日期"+registrationRecordInfo.getRegisterBackNo()+"小时前提交退号申请<br> 2、晚间22:30-02:00系统结算时间，不能进行退号操作<br> 3、当日号不能退号退费<br> 4、一个账号7天内只能退"+registrationRecordInfo.getWeekBackNo()+"个号，30天内只能退"+registrationRecordInfo.getMonthBackNo()+"个号";
        RichText.from(hint_content).bind(this)
                .showBorder(false)
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT)
                .into(hint_tv);
    }

    @Override
    public void getRegistrationRecordDetailFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "订单详情获取失败:" + errMsg);
    }
}
