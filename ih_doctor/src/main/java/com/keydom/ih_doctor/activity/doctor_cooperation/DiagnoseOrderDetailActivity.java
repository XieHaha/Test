package com.keydom.ih_doctor.activity.doctor_cooperation;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.CostomToastUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.doctor_cooperation.controller.DiagnoseOrderDetailController;
import com.keydom.ih_doctor.activity.doctor_cooperation.view.DiagnoseOrderDetailView;
import com.keydom.ih_doctor.adapter.DiagnoseOrderDetailAdapter;
import com.keydom.ih_doctor.bean.DiagnoseOrderDetailBean;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：转诊单详情公用页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DiagnoseOrderDetailActivity extends BaseControllerActivity<DiagnoseOrderDetailController> implements DiagnoseOrderDetailView {

    /**
     * 转诊单公用页面－带接收操作
     */
    public static final int ORDER_ACTION = 1300;
    /**
     * 转诊单公用页面－无接收操作
     */
    public static final int ORDER_WITHOUT_ACTION = 1301;
    /**
     * 转诊单ID
     */
    private long id;

    /**
     * 图片适配器，病情资料和问诊说明图片适配器
     */
    private DiagnoseOrderDetailAdapter diagnoseInfoImgAdapter, diagnoseMaterialAdapter;

    /**
     * 存放问诊信息图片地址的列表
     */
    private List<String> diagnoseInfoImgList = new ArrayList<>();
    /**
     * 存放病情资料的图片地址列表
     */
    private List<String> diagnoseMaterialList = new ArrayList<>();
    /**
     * 页面类型TYPE
     */
    private int mType;
    /**
     * 拒绝问诊单弹窗
     */
    private Dialog notReceiveDialog;

    private TextView userName, userSex, userAge, diagnoseInfoTv, diagnoseExplainTv, doctorName, doctorJob, doctorDept, doctorBeGood, applyTime;

    private RecyclerView diagnoseInfoImgRv, diagnoseMaterialRv;

    private CircleImageView doctorIcon;

    private Button returnBt, receiveBt;

    private LinearLayout buttonLl;

    private EditText dialogInput;


    /**
     * @param context
     */
    public static void startCommon(Context context, long id) {
        Intent starter = new Intent(context, DiagnoseOrderDetailActivity.class);
        starter.putExtra(Const.DATA, id);
        starter.putExtra(Const.TYPE, ORDER_WITHOUT_ACTION);
        context.startActivity(starter);
    }


    /**
     * @param context
     */
    public static void startWithAction(Context context, long id) {
        Intent starter = new Intent(context, DiagnoseOrderDetailActivity.class);
        starter.putExtra(Const.DATA, id);
        starter.putExtra(Const.TYPE, ORDER_ACTION);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.diagnose_order_detail_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        id = getIntent().getLongExtra(Const.DATA, Const.INT_DEFAULT);
        mType = getIntent().getIntExtra(Const.TYPE, Const.INT_DEFAULT);
        setTitle("转诊详情");
        initView();
        pageLoading();
        getController().getDetail();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getDetail();
            }
        });
    }

    private void initView() {
        doctorIcon = this.findViewById(R.id.doctor_icon);
        userName = this.findViewById(R.id.user_name);
        userSex = this.findViewById(R.id.user_sex);
        userAge = this.findViewById(R.id.user_age);
        diagnoseInfoTv = this.findViewById(R.id.diagnose_info_tv);
        diagnoseExplainTv = this.findViewById(R.id.diagnose_explain_tv);
        doctorName = this.findViewById(R.id.doctor_name);
        doctorJob = this.findViewById(R.id.doctor_job);
        doctorDept = this.findViewById(R.id.doctor_dept);
        doctorBeGood = this.findViewById(R.id.doctor_be_good);
        applyTime = this.findViewById(R.id.apply_time);
        buttonLl = this.findViewById(R.id.button_ll);
        diagnoseInfoImgRv = this.findViewById(R.id.diagnose_info_img_rv);
        diagnoseMaterialRv = this.findViewById(R.id.diagnose_material_rv);
        returnBt = this.findViewById(R.id.return_bt);
        receiveBt = this.findViewById(R.id.receive_bt);
        returnBt.setOnClickListener(getController());
        receiveBt.setOnClickListener(getController());
        notReceiveDialog = createDialog();

        if (mType == ORDER_ACTION) {
            buttonLl.setVisibility(View.VISIBLE);
        } else {
            buttonLl.setVisibility(View.GONE);
        }
    }


    private void setInfo(DiagnoseOrderDetailBean bean) {
        userName.setText(bean.getPatientName());
        userSex.setText(CommonUtils.getSex(bean.getPatientSex()));
        userAge.setText(String.valueOf(bean.getPatientAge()));
        diagnoseInfoTv.setText(bean.getContent());
        diagnoseExplainTv.setText(bean.getReferralExplain());
        doctorName.setText(bean.getDoctor());
        doctorJob.setText(bean.getJobTitle());
        doctorDept.setText(bean.getDept());
        doctorBeGood.setText(bean.getAdept());
        applyTime.setText(bean.getReferralTime());
        GlideUtils.load(doctorIcon, Const.IMAGE_HOST + bean.getDoctorAvatar(), 0, 0, false, null);
        String[] diagnoseInfo;
        if (bean.getDiseaseData() != null && !"".equals(bean.getDiseaseData().trim())) {
            diagnoseInfo = bean.getDiseaseData().split(",");
            for (int i = 0; i < diagnoseInfo.length; i++) {
                diagnoseMaterialList.add(diagnoseInfo[i]);
            }
        }
        String[] materialInfo;
        if (bean.getPatientImages() != null && !"".equals(bean.getPatientImages().trim())) {
            materialInfo = bean.getPatientImages().split(",");
            for (int i = 0; i < materialInfo.length; i++) {
                diagnoseInfoImgList.add(materialInfo[i]);
            }
        }
        diagnoseInfoImgAdapter = new DiagnoseOrderDetailAdapter(this, diagnoseInfoImgList);
        diagnoseMaterialAdapter = new DiagnoseOrderDetailAdapter(this, diagnoseMaterialList);

        LinearLayoutManager diagnoseInfoImgRvLm = new LinearLayoutManager(this);
        diagnoseInfoImgRvLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        diagnoseInfoImgRv.setAdapter(diagnoseInfoImgAdapter);
        diagnoseInfoImgRv.setLayoutManager(diagnoseInfoImgRvLm);

        LinearLayoutManager diagnoseMaterialRvLm = new LinearLayoutManager(this);
        diagnoseMaterialRvLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        diagnoseMaterialRv.setAdapter(diagnoseMaterialAdapter);
        diagnoseMaterialRv.setLayoutManager(diagnoseMaterialRvLm);
    }


    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return map;
    }

    @Override
    public void getDetailSuccess(DiagnoseOrderDetailBean bean) {
        if (bean != null) {
            setInfo(bean);
            pageLoadingSuccess();
        } else {
            pageEmpty();
        }

    }

    @Override
    public void getDetailFailed(String errMsg) {
        pageLoadingFail();
    }

    @Override
    public Map<String, Object> getoperateMap(long option) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("state", option);
        map.put("rejectionReason", getDialogValue());
        return map;
    }

    @Override
    public void operationSuccess(String successMsg) {
        CostomToastUtils.getInstance().ToastMessage(getContext(), "操作成功");
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.DIAGNOSE_ORDER_UPDATE).setData(id).build());
        finish();
    }

    @Override
    public void operationFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public String getDialogValue() {
        return (dialogInput == null) ? "" : dialogInput.getText().toString();
    }

    @Override
    public void showDialog() {
        notReceiveDialog.show();
    }

    @Override
    public void hideDialog() {
        notReceiveDialog.hide();
    }

    private Dialog createDialog() {
        Dialog dialog = new Dialog(this, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.prescription_dialog_layout, null);
        dialog.setContentView(view);
        final ImageView cancel = (ImageView) view.findViewById(R.id.check_dialog_close);
        final TextView commit = (TextView) view.findViewById(R.id.check_dialog_submit);
        dialogInput = (EditText) view.findViewById(R.id.check_dialog_input);
        cancel.setOnClickListener(getController());
        commit.setOnClickListener(getController());
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }
}
