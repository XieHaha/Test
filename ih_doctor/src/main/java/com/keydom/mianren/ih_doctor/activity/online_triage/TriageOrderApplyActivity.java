package com.keydom.mianren.ih_doctor.activity.online_triage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.DiagnoseCommonActivity;
import com.keydom.mianren.ih_doctor.activity.online_triage.controller.TriageOrderApplyController;
import com.keydom.mianren.ih_doctor.activity.online_triage.view.TriageOrderApplyView;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseChangePlusImgAdapter;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseOrderDetailAdapter;
import com.keydom.mianren.ih_doctor.bean.DeptDoctorBean;
import com.keydom.mianren.ih_doctor.bean.DiagnoseFillOutResBean;
import com.keydom.mianren.ih_doctor.bean.InquiryBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.utils.JsonUtils;
import com.keydom.mianren.ih_doctor.view.CustomRecognizerDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import butterknife.BindView;

/**
 * @date 3月19日
 * 分诊
 */
public class TriageOrderApplyActivity extends BaseControllerActivity<TriageOrderApplyController> implements TriageOrderApplyView {
    @BindView(R.id.triage_apply_doctor_box_layout)
    LinearLayout triageApplyDoctorBoxLayout;
    @BindView(R.id.triage_apply_doctor_tv)
    TextView triageApplyDoctorTv;
    @BindView(R.id.triage_apply_inquiry_order_tv)
    TextView triageApplyInquiryOrderTv;
    @BindView(R.id.diagnose_order_ll)
    LinearLayout diagnoseOrderLl;
    @BindView(R.id.triage_apply_transfer_description_et)
    InterceptorEditText triageApplyTransferDescriptionEt;
    @BindView(R.id.triage_apply_transfer_description_voice)
    ImageView triageApplyTransferDescriptionVoice;
    @BindView(R.id.triage_apply_condition_image_grid)
    GridViewForScrollView triageApplyConditionImageGrid;
    /**
     * 判断是在线问诊发起的转诊还是医生协作发起的转诊（在线问诊发起的转诊自动带了问诊单对象过来）
     */
    private int mType;
    /**
     * 图片适配器
     */
    private DiagnoseChangePlusImgAdapter mAdapter;
    /**
     * 选中的图片地址列表
     */
    private List<String> gridList = new ArrayList<>();
    /**
     * 科室医生列表
     */
    private List<DeptDoctorBean> doctorList = new ArrayList<>();
    /**
     * 存放医生布局列表
     */
    private Stack<View> mStack = new Stack<>();
    /**
     * 存放问诊单布局列表
     */
    private Stack<View> orderStack = new Stack<>();
    /**
     * 问诊单
     */
    private InquiryBean orderBean;

    // 语音听写UI
    private CustomRecognizerDialog mIatDialog;


    /**
     * 病情资料限制上传的最大图片数量
     */
    public static final int MAX_IMAGE = 9;
    /**
     * 在线问诊转诊申请
     */
    public static final int DIAGNOSE_FILLOUT_APPLY = 1200;
    /**
     * 医生协作转诊申请
     */
    public static final int DOCTOR_GOURP_FILLOUT_APPLY = 1201;

    public static void start(Context context,InquiryBean bean) {
        Intent intent = new Intent(context, TriageOrderApplyActivity.class);
        intent.putExtra(Const.DATA, bean);
        context.startActivity(intent);
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = code -> {
        if (code != ErrorCode.SUCCESS) {
            Log.e("xunfei", "初始化失败，错误码：" + code + ",请点击网址https://www.xfyun.cn/document" +
                    "/error-code查询解决方案");
        }
    };

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            if (null != triageApplyTransferDescriptionEt) {
                String text = JsonUtils.handleXunFeiJson(results);
                if (TextUtils.isEmpty(triageApplyTransferDescriptionEt.getText().toString())) {
                    triageApplyTransferDescriptionEt.setText(text);
                    triageApplyTransferDescriptionEt.setSelection(triageApplyTransferDescriptionEt.getText().length());
                } else {
                    triageApplyTransferDescriptionEt.setText(triageApplyTransferDescriptionEt.getText().toString() + text);
                    triageApplyTransferDescriptionEt.setSelection(triageApplyTransferDescriptionEt.getText().length());
                }
            }
        }

        public void onError(SpeechError error) {
            ToastUtil.showMessage(TriageOrderApplyActivity.this, error.getPlainDescription(true));
        }

    };

    @Override
    public int getLayoutRes() {
        return R.layout.activity_triage_apply;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(Const.TYPE, 0);
        orderBean = (InquiryBean) getIntent().getSerializableExtra(Const.DATA);
        initView();
        setTitle("分诊");
        if (orderBean != null) {
            addOrder(orderBean);
        }
        setRightTxt("提交");
        setRightBtnListener(v -> {
            if (doctorList == null || doctorList.size() <= 0) {
                ToastUtil.showMessage(this, "请选择医生");
                return;
            }
            if (orderBean == null) {
                ToastUtil.showMessage(this, "请选择问诊订单");
                return;
            }
            String str =
                    CommonUtils.filterEmoji(triageApplyTransferDescriptionEt.getText().toString().trim());
            if (str == null || str.length() < 20) {
                ToastUtil.showMessage(this, "转诊说明至少20字!");
                return;
            }
            getController().submit();
        });
    }

    private void initView() {
        mAdapter = new DiagnoseChangePlusImgAdapter(this, gridList);
        triageApplyConditionImageGrid.setAdapter(mAdapter);
        triageApplyConditionImageGrid.setOnItemClickListener(getController());
        triageApplyDoctorTv.setOnClickListener(getController());
        triageApplyInquiryOrderTv.setOnClickListener(getController());

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new CustomRecognizerDialog(this, mInitListener);
        mIatDialog.setListener(mRecognizerDialogListener);
        triageApplyTransferDescriptionVoice.setOnClickListener(v -> initPremissions());
    }

    @SuppressLint("CheckResult")
    public void initPremissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        if (mIatDialog.isShowing()) {
                            mIatDialog.dismiss();
                        }
                        mIatDialog.show();
                        ToastUtil.showMessage(this, "请开始说话…");

                    } else {
                        ToastUtil.showMessage(this, "请开启录音需要的权限");

                    }
                });

    }

    /**
     * 添加问诊单到界面
     *
     * @param bean 选择的问诊单
     */
    private void addOrder(InquiryBean bean) {
        diagnoseOrderLl.removeAllViews();
        orderStack.clear();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.diagnose_order_item, null, true);
        CircleImageView userIcon = view.findViewById(R.id.user_icon);
        TextView userName = view.findViewById(R.id.user_name);
        TextView orderStatus = view.findViewById(R.id.order_status);
        TextView userAge = view.findViewById(R.id.user_age);
        TextView userSex = view.findViewById(R.id.user_sex);
        TextView diagnoseDec = view.findViewById(R.id.diagnose_dec);
        TextView diagnoseTime = view.findViewById(R.id.diagnose_time);
        TextView diagnoseType = view.findViewById(R.id.order_type_tv);
        ImageView delete = view.findViewById(R.id.delete);
        RecyclerView imgRv = view.findViewById(R.id.img_rv);
        userName.setText(bean.getName());
        userAge.setText(String.valueOf(bean.getAge()));
        userSex.setText(CommonUtils.getSex(bean.getSex()));
        diagnoseDec.setText(bean.getConditionDesc());
        diagnoseTime.setText(bean.getApplyTime());
        triageApplyInquiryOrderTv.setText(bean.getName());
        if (bean.getInquisitionType() == 0) {
            diagnoseType.setText("图文问诊");
            Drawable rightDrawable =
                    getContext().getResources().getDrawable(R.mipmap.diagnose_illustration);
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(),
                    rightDrawable.getMinimumHeight());
            diagnoseType.setCompoundDrawables(rightDrawable, null, null, null);
        } else {
            diagnoseType.setText("视频问诊");
            Drawable rightDrawable =
                    getContext().getResources().getDrawable(R.mipmap.video_diagnoses_icon);
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(),
                    rightDrawable.getMinimumHeight());
            diagnoseType.setCompoundDrawables(rightDrawable, null, null, null);

        }
        orderStatus.setVisibility(View.GONE);
        delete.setOnClickListener(v -> new GeneralDialog(this, "确定删除该问诊单?",
                () -> {
                    orderBean = null;
                    diagnoseOrderLl.removeAllViews();
                    triageApplyInquiryOrderTv.setText("");
                }).show());
        GlideUtils.load(userIcon, Const.IMAGE_HOST + bean.getUserAvatar(), 0, R.mipmap.user_icon,
                false, null);
        DiagnoseOrderDetailAdapter adapter = new DiagnoseOrderDetailAdapter(this,
                CommonUtils.getImgList(orderBean.getConditionData()));
        LinearLayoutManager diagnoseInfoImgRvLm = new LinearLayoutManager(this);
        diagnoseInfoImgRvLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        imgRv.setAdapter(adapter);
        imgRv.setLayoutManager(diagnoseInfoImgRvLm);
        diagnoseOrderLl.addView(view);
        orderStack.push(view);
    }


    /**
     * media.getPath(); 为原图path
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < selectList.size(); i++) {
                        getController().uploadFile(selectList.get(i).getPath());
                    }
                    break;
                case Const.DOCTOR_SLEECT_ONLY_RESULT:
                    List<DeptDoctorBean> list =
                            (List<DeptDoctorBean>) data.getSerializableExtra(Const.DATA);
                    if (orderBean != null && orderBean.getInquisitionType() == 0) {
                        if (list.get(0).getProjectStatus() == 1 || list.get(0).getProjectStatus() == 3) {
                            doctorList.clear();
                            doctorList.addAll(list);
                            removeView();
                            addDoctor();
                            triageApplyDoctorTv.setText("");
                        } else {
                            ToastUtil.showMessage(getContext(), "该医生未开通图文问诊服务");
                        }
                    } else if (orderBean != null && orderBean.getInquisitionType() == 1) {
                        if (list.get(0).getProjectStatus() == 2 || list.get(0).getProjectStatus() == 3) {
                            doctorList.clear();
                            doctorList.addAll(list);
                            removeView();
                            addDoctor();
                            triageApplyDoctorTv.setText("");
                        } else {
                            ToastUtil.showMessage(getContext(), "该医生未开通视频问诊服务");
                        }
                    } else {
                        doctorList.clear();
                        doctorList.addAll(list);
                        removeView();
                        addDoctor();
                        triageApplyDoctorTv.setText("");
                    }

                    break;
                case Const.DIAGNOSE_ORDER_SELECT:
                    orderBean = (InquiryBean) data.getSerializableExtra(Const.DATA);
                    addOrder(orderBean);
                    break;

            }
        }


    }


    /**
     * 添加选择的医生到页面
     */
    private void addDoctor() {
        for (int i = 0; i < doctorList.size(); i++) {
            addDoctorView(doctorList.get(i).getName());
        }
    }

    /**
     * 界面上移除所有选择的医生
     */
    private void removeView() {
        for (int i = 0; i < mStack.size(); i++) {
            triageApplyDoctorBoxLayout.removeView(mStack.get(i));
        }
    }

    /**
     * 界面上添加选中的医生
     *
     * @param name
     */
    private void addDoctorView(String name) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.fill_out_doctor_tag, null, true);
        TextView tagTv = view.findViewById(R.id.doctor_name);
        tagTv.setText(name);
        triageApplyDoctorBoxLayout.addView(view);
        mStack.push(view);
    }


    @Override
    public void uploadSuccess(String msg) {
        gridList.add(msg);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void uploadFailed(String errMsg) {
        if (errMsg == null || "".equals(errMsg.trim())) {
            ToastUtil.showMessage(this, errMsg);
        } else {
            ToastUtil.showMessage(this, "图片上传失败!");
        }
    }

    @Override
    public void saveSuccess(DiagnoseFillOutResBean bean) {
        if (mType == DIAGNOSE_FILLOUT_APPLY) {

            finish();
        } else {
            DiagnoseCommonActivity.startDiagnoseChangeRecoder(getContext());
            finish();
        }
    }

    @Override
    public void saveFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public boolean getLastItemClick(int position) {
        return position == gridList.size();
    }

    @Override
    public List<DeptDoctorBean> getSelectedDoctor() {
        return doctorList;
    }

    /**
     * 按照接口规则拼接上传的图片字符串
     */
    private String getImgStr() {
        //选中的图片拼接字符串
        String imgStr = "";
        for (String str : gridList) {
            if ("".equals(imgStr)) {
                imgStr = str;
            } else {
                imgStr = imgStr + "," + str;
            }
        }
        return imgStr;
    }

    @Override
    public Map<String, Object> getOperateMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("referralExplain",
                CommonUtils.filterEmoji(triageApplyTransferDescriptionEt.getText().toString().trim()));
        map.put("orderId", orderBean.getId());
        map.put("patientAge", orderBean.getAge());
        map.put("patientName", orderBean.getName());
        map.put("patientPhone", orderBean.getPhoneNumber());
        map.put("patientSex", orderBean.getSex());
        map.put("diseaseData", getImgStr());
        map.put("changeInfoDoctorCode", doctorList.get(0).getUuid());
        return map;
    }

    @Override
    public int getImgSize() {
        return gridList.size();
    }

    @Override
    public void deleteImg(int position) {
        gridList.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public List<String> getImgList() {
        return gridList;
    }

    @Override
    public int getOrderType() {
        if (orderBean != null)
            return orderBean.getType() + 1;
        return 0;
    }

    @Override
    public int getDoctorType() {
        if (doctorList != null && doctorList.size() == 1) {
            return doctorList.get(0).getProjectStatus();
        }
        return 3;
    }
}
