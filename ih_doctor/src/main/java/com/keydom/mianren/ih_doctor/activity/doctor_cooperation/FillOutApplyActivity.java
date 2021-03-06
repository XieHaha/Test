package com.keydom.mianren.ih_doctor.activity.doctor_cooperation;

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
import android.widget.EditText;
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
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.controller.FillOutApplyController;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.view.FillOutApplyView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import io.reactivex.functions.Consumer;

/**
 * @Name???com.keydom.ih_doctor.activity.personal
 * @Description???????????????
 * @Author???song
 * @Date???18/11/14 ??????10:37
 * ????????????xusong
 * ???????????????18/11/14 ??????10:37
 */
public class FillOutApplyActivity extends BaseControllerActivity<FillOutApplyController> implements FillOutApplyView {

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
     * ??????????????????????????????
     */
    private String imgStr = "";
    /**
     * ?????????
     */
    private InquiryBean orderBean;

    private TextView selectDoctor, selectDiagnoseOrder;
    private EditText explainInputEt;
    private GridViewForScrollView photoGv;
    private LinearLayout diagnoseOrderLl, doctorBox;


    private ImageView mVoiceInputIv;

    // ????????????UI
    private CustomRecognizerDialog mIatDialog;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_fill_out_apply_layout;
    }

    /**
     * ?????????????????????
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {

            if (code != ErrorCode.SUCCESS) {
                Log.e("xunfei", "??????????????????????????????" + code + ",???????????????https://www.xfyun" +
                        ".cn/document/error-code??????????????????");
            }
        }
    };

    /**
     * ??????UI?????????
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            if (null != explainInputEt) {
                String text = JsonUtils.handleXunFeiJson(results);
                if (TextUtils.isEmpty(explainInputEt.getText().toString())) {
                    explainInputEt.setText(text);
                    explainInputEt.setSelection(explainInputEt.getText().length());
                } else {
                    explainInputEt.setText(explainInputEt.getText().toString() + text);
                    explainInputEt.setSelection(explainInputEt.getText().length());
                }
            }

        }

        /**
         * ??????????????????.
         */
        public void onError(SpeechError error) {
            ToastUtil.showMessage(FillOutApplyActivity.this, error.getPlainDescription(true));

        }

    };


    private void initView() {
        selectDoctor = this.findViewById(R.id.select_doctor);
        doctorBox = this.findViewById(R.id.doctor_box);
        selectDiagnoseOrder = this.findViewById(R.id.select_diagnose_order);
        explainInputEt = this.findViewById(R.id.explain_input_et);
        photoGv = this.findViewById(R.id.photo_gv);
        diagnoseOrderLl = this.findViewById(R.id.diagnose_order_ll);
        mVoiceInputIv = this.findViewById(R.id.fill_out_apply_layout_voice_input_iv);
        mAdapter = new DiagnoseChangePlusImgAdapter(this, gridList);
        photoGv.setAdapter(mAdapter);
        photoGv.setOnItemClickListener(getController());
        selectDoctor.setOnClickListener(getController());
        selectDiagnoseOrder.setOnClickListener(getController());

        // ???????????????Dialog?????????????????????UI???????????????????????????SpeechRecognizer
        // ??????UI????????????????????????sdk??????????????????notice.txt,?????????????????????????????????
        mIatDialog = new CustomRecognizerDialog(this, mInitListener);
        mIatDialog.setListener(mRecognizerDialogListener);
        mVoiceInputIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPremissions();
            }
        });
    }

    @SuppressLint("CheckResult")
    public void initPremissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            if (mIatDialog.isShowing()) {
                                mIatDialog.dismiss();
                            }
                            mIatDialog.show();
                            ToastUtil.showMessage(FillOutApplyActivity.this, "??????????????????");

                        } else {
                            ToastUtil.showMessage(FillOutApplyActivity.this, "??????????????????????????????");

                        }
                    }
                });

    }

    /**
     * ????????????
     */
    public static void startFillOut(Context context, int type) {
        Intent starter = new Intent(context, FillOutApplyActivity.class);
        starter.putExtra(Const.TYPE, type);
        context.startActivity(starter);
    }


    public static void startFillOut(Context context, InquiryBean bean) {
        Intent starter = new Intent(context, FillOutApplyActivity.class);
        starter.putExtra(Const.TYPE, DIAGNOSE_FILLOUT_APPLY);
        starter.putExtra(Const.DATA, bean);
        context.startActivity(starter);
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(Const.TYPE, 0);
        orderBean = (InquiryBean) getIntent().getSerializableExtra(Const.DATA);
        initView();
        setTitle("??????");
        if (orderBean != null) {
            addOrder(orderBean);
        }
        setRightTxt("??????");
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                if (doctorList == null || doctorList.size() <= 0) {
                    ToastUtil.showMessage(FillOutApplyActivity.this, "???????????????");
                    return;
                }
                if (orderBean == null) {
                    ToastUtil.showMessage(FillOutApplyActivity.this, "?????????????????????");
                    return;
                }
                String str = CommonUtils.filterEmoji(explainInputEt.getText().toString().trim());
                if (str == null || str.length() < 20) {
                    ToastUtil.showMessage(FillOutApplyActivity.this, "??????????????????20???!");
                    return;
                }
                getController().submit();
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
        selectDiagnoseOrder.setText(bean.getName());
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
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GeneralDialog(FillOutApplyActivity.this, "?????????????????????????",
                        new GeneralDialog.OnCloseListener() {
                            @Override
                            public void onCommit() {
                                orderBean = null;
                                diagnoseOrderLl.removeAllViews();
                                selectDiagnoseOrder.setText("??????????????????");
                            }
                        }).show();
            }
        });
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
     * media.getPath(); ?????????path
     *
     * @param requestCode
     * @param resultCode
     * @param data
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
                            selectDoctor.setText("");
                        } else {
                            ToastUtil.showMessage(getContext(), "????????????????????????????????????");
                        }
                    } else if (orderBean != null && orderBean.getInquisitionType() == 1) {
                        if (list.get(0).getProjectStatus() == 2 || list.get(0).getProjectStatus() == 3) {
                            doctorList.clear();
                            doctorList.addAll(list);
                            removeView();
                            addDoctor();
                            selectDoctor.setText("");
                        } else {
                            ToastUtil.showMessage(getContext(), "????????????????????????????????????");
                        }
                    } else {
                        doctorList.clear();
                        doctorList.addAll(list);
                        removeView();
                        addDoctor();
                        selectDoctor.setText("");
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
            doctorBox.removeView(mStack.get(i));
        }
    }

    /**
     * ??????????????????????????????
     *
     * @param name
     */
    private void addDoctorView(String name) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.fill_out_doctor_tag, null, true);
        TextView tagTv = view.findViewById(R.id.doctor_name);
        tagTv.setText(name);
        doctorBox.addView(view);
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
        if (position == gridList.size())
            return true;
        return false;
    }

    @Override
    public List<DeptDoctorBean> getSelectedDoctor() {
        return doctorList;
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @return
     */
    private String getImgStr() {
        imgStr = "";
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
                CommonUtils.filterEmoji(explainInputEt.getText().toString().trim()));
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
