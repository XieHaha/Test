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
import com.keydom.ih_common.utils.BaseFileUtils;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_triage.controller.TriageOrderApplyController;
import com.keydom.mianren.ih_doctor.activity.online_triage.view.TriageOrderApplyView;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseChangePlusImgAdapter;
import com.keydom.mianren.ih_doctor.adapter.DiagnoseOrderDetailAdapter;
import com.keydom.mianren.ih_doctor.bean.DeptDoctorBean;
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
 * @date 3???19???
 * ??????
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
     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    private int mType;
    /**
     * ???????????????
     */
    private DiagnoseChangePlusImgAdapter mAdapter;
    /**
     * ???????????????????????????
     */
    private List<String> gridList = new ArrayList<>();
    /**
     * ??????????????????
     */
    private List<DeptDoctorBean> doctorList = new ArrayList<>();
    /**
     * ????????????????????????
     */
    private Stack<View> mStack = new Stack<>();
    /**
     * ???????????????????????????
     */
    private Stack<View> orderStack = new Stack<>();
    /**
     * ?????????
     */
    private InquiryBean orderBean;

    // ????????????UI
    private CustomRecognizerDialog mIatDialog;


    /**
     * ?????????????????????????????????????????????
     */
    public static final int MAX_IMAGE = 9;
    /**
     * ????????????????????????
     */
    public static final int DIAGNOSE_FILLOUT_APPLY = 1200;
    /**
     * ????????????????????????
     */
    public static final int DOCTOR_GOURP_FILLOUT_APPLY = 1201;

    public static void start(Context context, InquiryBean bean) {
        Intent intent = new Intent(context, TriageOrderApplyActivity.class);
        intent.putExtra(Const.DATA, bean);
        context.startActivity(intent);
    }

    /**
     * ?????????????????????
     */
    private InitListener mInitListener = code -> {
        if (code != ErrorCode.SUCCESS) {
            Log.e("xunfei", "??????????????????????????????" + code + ",???????????????https://www.xfyun.cn/document" +
                    "/error-code??????????????????");
        }
    };

    /**
     * ??????UI?????????
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
        setTitle("??????");
        if (orderBean != null) {
            addOrder(orderBean);
        }
        setRightTxt("??????");
        setRightBtnListener(v -> {
            if (doctorList == null || doctorList.size() <= 0) {
                ToastUtil.showMessage(this, "???????????????");
                return;
            }
            if (orderBean == null) {
                ToastUtil.showMessage(this, "?????????????????????");
                return;
            }
            String str =
                    CommonUtils.filterEmoji(triageApplyTransferDescriptionEt.getText().toString().trim());
            if (str == null || str.length() < 20) {
                ToastUtil.showMessage(this, "??????????????????20???!");
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

        // ???????????????Dialog?????????????????????UI???????????????????????????SpeechRecognizer
        // ??????UI????????????????????????sdk??????????????????notice.txt,?????????????????????????????????
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
                        ToastUtil.showMessage(this, "??????????????????");

                    } else {
                        ToastUtil.showMessage(this, "??????????????????????????????");

                    }
                });

    }

    /**
     * ????????????????????????
     *
     * @param bean ??????????????????
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
        userAge.setText(bean.getAge());
        userSex.setText(CommonUtils.getSex(bean.getSex()));
        diagnoseDec.setText(bean.getConditionDesc());
        diagnoseTime.setText(bean.getApplyTime());
        triageApplyInquiryOrderTv.setText(bean.getName());
        if (bean.getInquisitionType() == 0) {
            diagnoseType.setText("????????????");
            Drawable rightDrawable =
                    getContext().getResources().getDrawable(R.mipmap.diagnose_illustration);
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(),
                    rightDrawable.getMinimumHeight());
            diagnoseType.setCompoundDrawables(rightDrawable, null, null, null);
        } else {
            diagnoseType.setText("????????????");
            Drawable rightDrawable =
                    getContext().getResources().getDrawable(R.mipmap.video_diagnoses_icon);
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(),
                    rightDrawable.getMinimumHeight());
            diagnoseType.setCompoundDrawables(rightDrawable, null, null, null);

        }
        orderStatus.setVisibility(View.GONE);
        delete.setOnClickListener(v -> new GeneralDialog(this, "?????????????????????????",
                () -> {
                    orderBean = null;
                    diagnoseOrderLl.removeAllViews();
                    triageApplyInquiryOrderTv.setText("");
                }).show());
        GlideUtils.load(userIcon, BaseFileUtils.getHeaderUrl(orderBean.getUserAvatar()), 0,
                R.mipmap.user_icon, false, null);
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
     * media.getPath(); ?????????path
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
                            ToastUtil.showMessage(getContext(), "????????????????????????????????????");
                        }
                    } else if (orderBean != null && orderBean.getInquisitionType() == 1) {
                        if (list.get(0).getProjectStatus() == 2 || list.get(0).getProjectStatus() == 3) {
                            doctorList.clear();
                            doctorList.addAll(list);
                            removeView();
                            addDoctor();
                            triageApplyDoctorTv.setText("");
                        } else {
                            ToastUtil.showMessage(getContext(), "????????????????????????????????????");
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
     * ??????????????????????????????
     */
    private void addDoctor() {
        for (int i = 0; i < doctorList.size(); i++) {
            addDoctorView(doctorList.get(i).getName());
        }
    }

    /**
     * ????????????????????????????????????
     */
    private void removeView() {
        for (int i = 0; i < mStack.size(); i++) {
            triageApplyDoctorBoxLayout.removeView(mStack.get(i));
        }
    }

    /**
     * ??????????????????????????????
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
            ToastUtil.showMessage(this, "??????????????????!");
        }
    }

    @Override
    public void saveSuccess(String msg) {
        ToastUtil.showMessage(this, "????????????????????????");
        finish();
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
     * ????????????????????????????????????????????????
     */
    private String getImgStr() {
        //??????????????????????????????
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
        map.put("changeDoctorId", doctorList.get(0).getId());
        map.put("orderId", orderBean.getId());
        map.put("conditionData", getImgStr());
        map.put("conditionDesc", triageApplyTransferDescriptionEt.getText().toString().trim());
        //        map.put("deptId", doctorList.get(0).get);
        //        map.put("voiceUrl", orderBean.getSex());
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
