package com.keydom.mianren.ih_doctor.activity.online_triage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.DiagnoseOrderDetailBean;
import com.keydom.ih_common.bean.TriageBean;
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.im.config.ImConstants;
import com.keydom.ih_common.utils.CostomToastUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_triage.controller.TriageOrderDetailController;
import com.keydom.mianren.ih_doctor.activity.online_triage.view.TriageOrderDetailView;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseOrderDetailAdapter;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.keydom.mianren.ih_doctor.adapter.DiagnoseOrderRecyclrViewAdapter.IS_ORDER;

/**
 * @date 3月20日 17:06
 * 分诊详情
 */
public class TriageOrderDetailActivity extends BaseControllerActivity<TriageOrderDetailController> implements TriageOrderDetailView {
    @BindView(R.id.triage_order_detail_name_tv)
    TextView triageOrderDetailNameTv;
    @BindView(R.id.triage_order_detail_sex_tv)
    TextView triageOrderDetailSexTv;
    @BindView(R.id.triage_order_detail_age_tv)
    TextView triageOrderDetailAgeTv;
    @BindView(R.id.triage_order_detail_diagnose_info_tv)
    TextView triageOrderDetailDiagnoseInfoTv;
    @BindView(R.id.triage_order_detail_diagnose_info_recycler_view)
    RecyclerView triageOrderDetailDiagnoseInfoRecyclerView;
    @BindView(R.id.triage_order_detail_diagnose_explain_tv)
    TextView triageOrderDetailDiagnoseExplainTv;
    @BindView(R.id.triage_order_detail_diagnose_material_recycler)
    RecyclerView triageOrderDetailDiagnoseMaterialRecycler;
    @BindView(R.id.triage_order_detail_doctor_header_iv)
    CircleImageView triageOrderDetailDoctorHeaderIv;
    @BindView(R.id.triage_order_detail_doctor_name_tv)
    TextView triageOrderDetailDoctorNameTv;
    @BindView(R.id.triage_order_detail_doctor_job_iv)
    TextView triageOrderDetailDoctorJobIv;
    @BindView(R.id.triage_order_detail_doctor_depart_tv)
    TextView triageOrderDetailDoctorDepartTv;
    @BindView(R.id.triage_order_detail_apply_time_tv)
    TextView triageOrderDetailApplyTimeTv;
    @BindView(R.id.triage_order_detail_bottom_left)
    Button triageOrderDetailBottomLeft;
    @BindView(R.id.triage_order_detail_bottom_right)
    Button triageOrderDetailBottomRight;
    @BindView(R.id.triage_order_detail_voice_layout)
    LinearLayout triageOrderDetailVoiceLayout;
    @BindView(R.id.triage_order_detail_bottom_layout)
    LinearLayout triageOrderDetailBottomLayout;

    /**
     * 分诊单详情
     */
    private TriageBean triageBean;
    private boolean isChat;

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
    private TypeEnum mType;
    /**
     * 拒绝问诊单弹窗
     */
    private Dialog notReceiveDialog;
    private EditText dialogInput;

    public static final String KEY_TRIAGE_BEAN = "key_triage_bean";
    public static final String KEY_IS_CHAT = "key_is_chat";

    /**
     * 启动
     */
    public static void startWithAction(Context context, TriageBean bean, TypeEnum type) {
        startWithAction(context, bean, type, false);
    }

    /**
     * 启动
     */
    public static void startWithAction(Context context, TriageBean bean, TypeEnum type,
                                       boolean isChat) {
        Intent starter = new Intent(context, TriageOrderDetailActivity.class);
        starter.putExtra(KEY_TRIAGE_BEAN, bean);
        starter.putExtra(KEY_IS_CHAT, isChat);
        starter.putExtra(Const.TYPE, type);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_triage_order_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mType = (TypeEnum) getIntent().getSerializableExtra(Const.TYPE);
        triageBean = (TriageBean) getIntent().getSerializableExtra(KEY_TRIAGE_BEAN);
        isChat = getIntent().getBooleanExtra(KEY_IS_CHAT, false);
        setTitle(getString(R.string.txt_triage_order_detail));
        if (!isChat) {
            setRightTxt("问诊");
        }
        initView();

        getDetailData();
        setReloadListener((v, status) -> getDetailData());
    }

    private void initView() {
        setRightBtnListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean(IS_ORDER, true);
            bundle.putBoolean(ImConstants.TEAM, true);
            ImClient.startConversation(TriageOrderDetailActivity.this,
                    triageBean.getGroupTid(), bundle);
        });
        triageOrderDetailBottomLeft.setOnClickListener(getController());
        triageOrderDetailBottomRight.setOnClickListener(getController());
        notReceiveDialog = createDialog();

        if (mType == TypeEnum.TRIAGE_WAIT) {
            triageOrderDetailBottomLayout.setVisibility(View.VISIBLE);
        } else {
            triageOrderDetailBottomLayout.setVisibility(View.GONE);
        }
    }

    private void getDetailData() {
        pageLoading();
        getController().getPatientInquisitionById(triageBean.getOrderId());
    }

    private void bindData(com.keydom.ih_common.bean.DiagnoseOrderDetailBean detailBean) {
        triageOrderDetailNameTv.setText(triageBean.getPatientName());
        triageOrderDetailSexTv.setText(triageBean.getPatientSex());
        triageOrderDetailAgeTv.setText(triageBean.getPatientAge() + "岁");
        triageOrderDetailDiagnoseExplainTv.setText(triageBean.getTriageExplain());
        triageOrderDetailDoctorNameTv.setText(triageBean.getDoctor());
        triageOrderDetailDoctorJobIv.setText(triageBean.getJobTitle());
        triageOrderDetailDoctorDepartTv.setText(triageBean.getDept());
        triageOrderDetailApplyTimeTv.setText(triageBean.getTriageTime());
        if (TextUtils.isEmpty(triageBean.getVoiceUrl())) {
            triageOrderDetailVoiceLayout.setVisibility(View.GONE);
        } else {
            triageOrderDetailVoiceLayout.setVisibility(View.VISIBLE);
        }
        triageOrderDetailDiagnoseInfoTv.setText(detailBean.getConditionDesc());
        GlideUtils.load(triageOrderDetailDoctorHeaderIv,
                Const.IMAGE_HOST + triageBean.getDoctorAvatar(), 0, 0, false, null);
        String[] diagnoseInfo;
        if (!TextUtils.isEmpty(triageBean.getDiseaseData())) {
            diagnoseInfo = triageBean.getDiseaseData().split(",");
            Collections.addAll(diagnoseMaterialList, diagnoseInfo);
        }
        String[] materialInfo;
        if (!TextUtils.isEmpty(detailBean.getConditionData())) {
            materialInfo = detailBean.getConditionData().split(",");
            Collections.addAll(diagnoseInfoImgList, materialInfo);
        }
        //图片适配器，病情资料和问诊说明图片适配器
        DiagnoseOrderDetailAdapter diagnoseInfoImgAdapter = new DiagnoseOrderDetailAdapter(this,
                diagnoseInfoImgList);
        DiagnoseOrderDetailAdapter diagnoseMaterialAdapter = new DiagnoseOrderDetailAdapter(this,
                diagnoseMaterialList);
        //
        LinearLayoutManager diagnoseInfoImgRvLm = new LinearLayoutManager(this);
        diagnoseInfoImgRvLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        triageOrderDetailDiagnoseInfoRecyclerView.setAdapter(diagnoseInfoImgAdapter);
        triageOrderDetailDiagnoseInfoRecyclerView.setLayoutManager(diagnoseInfoImgRvLm);

        LinearLayoutManager diagnoseMaterialRvLm = new LinearLayoutManager(this);
        diagnoseMaterialRvLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        triageOrderDetailDiagnoseMaterialRecycler.setAdapter(diagnoseMaterialAdapter);
        triageOrderDetailDiagnoseMaterialRecycler.setLayoutManager(diagnoseMaterialRvLm);
    }

    @Override
    public void getInquisitionDetailSuccess(DiagnoseOrderDetailBean bean) {
        bindData(bean);
        pageLoadingSuccess();
    }

    @Override
    public void getInquisitionDetailFailed(String msg) {
        ToastUtil.showMessage(this, msg);
        pageLoadingFail();
    }

    @Override
    public Map<String, Object> getOperateMap(long option) {
        Map<String, Object> map = new HashMap<>();
        map.put("triageApplyId", triageBean.getId());
        map.put("state", option);
        return map;
    }

    @Override
    public void operationSuccess(String successMsg) {
        CostomToastUtils.getInstance().ToastMessage(getContext(), successMsg);
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.DIAGNOSE_ORDER_UPDATE).setData(triageBean.getId()).build());
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
        final ImageView cancel = view.findViewById(R.id.check_dialog_close);
        final TextView commit = view.findViewById(R.id.check_dialog_submit);
        dialogInput = view.findViewById(R.id.check_dialog_input);
        cancel.setOnClickListener(getController());
        commit.setOnClickListener(getController());
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }
}
