package com.keydom.ih_doctor.activity.doctor_cooperation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_common.view.GridViewForScrollView;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.doctor_cooperation.controller.FillOutApplyController;
import com.keydom.ih_doctor.activity.doctor_cooperation.view.FillOutApplyView;
import com.keydom.ih_doctor.adapter.DiagnoseChangePlusImgAdapter;
import com.keydom.ih_doctor.adapter.DiagnoseOrderDetailAdapter;
import com.keydom.ih_doctor.bean.DeptDoctorBean;
import com.keydom.ih_doctor.bean.DiagnoseFillOutResBean;
import com.keydom.ih_doctor.bean.InquiryBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class FillOutApplyActivity extends BaseControllerActivity<FillOutApplyController> implements FillOutApplyView {

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
     * 选中的图片拼接字符串
     */
    private String imgStr = "";
    /**
     * 问诊单
     */
    private InquiryBean orderBean;

    private TextView selectDoctor, selectDiagnoseOrder;
    private EditText explainInputEt;
    private GridViewForScrollView photoGv;
    private LinearLayout diagnoseOrderLl, doctorBox;


    private void initView() {
        selectDoctor = this.findViewById(R.id.select_doctor);
        doctorBox = this.findViewById(R.id.doctor_box);
        selectDiagnoseOrder = this.findViewById(R.id.select_diagnose_order);
        explainInputEt = this.findViewById(R.id.explain_input_et);
        photoGv = this.findViewById(R.id.photo_gv);
        diagnoseOrderLl = this.findViewById(R.id.diagnose_order_ll);
        mAdapter = new DiagnoseChangePlusImgAdapter(this, gridList);
        photoGv.setAdapter(mAdapter);
        photoGv.setOnItemClickListener(getController());
        selectDoctor.setOnClickListener(getController());
        selectDiagnoseOrder.setOnClickListener(getController());
    }

    /**
     * 转诊申请
     *
     * @param context
     */
    public static void startFillOut(Context context) {
        Intent starter = new Intent(context, FillOutApplyActivity.class);
        starter.putExtra(Const.TYPE, DOCTOR_GOURP_FILLOUT_APPLY);
        context.startActivity(starter);
    }


    public static void startFillOut(Context context, InquiryBean bean) {
        Intent starter = new Intent(context, FillOutApplyActivity.class);
        starter.putExtra(Const.TYPE, DIAGNOSE_FILLOUT_APPLY);
        starter.putExtra(Const.DATA, bean);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_fill_out_apply_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(Const.TYPE, 0);
        orderBean = (InquiryBean) getIntent().getSerializableExtra(Const.DATA);
        initView();
        setTitle("填写转诊申请");
        if (orderBean != null) {
            addOrder(orderBean);
        }
        setRightTxt("提交");
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                if (doctorList == null || doctorList.size() <= 0) {
                    ToastUtil.shortToast(FillOutApplyActivity.this, "请选择医生");
                    return;
                }
                if (orderBean == null) {
                    ToastUtil.shortToast(FillOutApplyActivity.this, "请选择问诊订单");
                    return;
                }
                String str = CommonUtils.filterEmoji(explainInputEt.getText().toString().trim());
                if (str == null || str.length() < 20) {
                    ToastUtil.shortToast(FillOutApplyActivity.this, "转诊说明至少20字!");
                    return;
                }
                getController().submit();
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
        ImageView delete = view.findViewById(R.id.delete);
        RecyclerView imgRv = view.findViewById(R.id.img_rv);
        userName.setText(bean.getName());
        userAge.setText(String.valueOf(bean.getAge()));
        userSex.setText(CommonUtils.getSex(bean.getSex()));
        diagnoseDec.setText(bean.getConditionDesc());
        diagnoseTime.setText(bean.getApplyTime());
        selectDiagnoseOrder.setText(bean.getName());
        orderStatus.setVisibility(View.GONE);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GeneralDialog(FillOutApplyActivity.this, "确定删除该问诊单?", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        orderBean = null;
                        diagnoseOrderLl.removeAllViews();
                        selectDiagnoseOrder.setText("请选择问诊单");
                    }
                }).show();
            }
        });
        DiagnoseOrderDetailAdapter adapter = new DiagnoseOrderDetailAdapter(this, CommonUtils.getImgList(orderBean.getConditionData()));
        LinearLayoutManager diagnoseInfoImgRvLm = new LinearLayoutManager(this);
        diagnoseInfoImgRvLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        imgRv.setAdapter(adapter);
        imgRv.setLayoutManager(diagnoseInfoImgRvLm);
        diagnoseOrderLl.addView(view);
        orderStack.push(view);
    }


    /**
     * media.getPath(); 为原图path
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
                    List<DeptDoctorBean> list = (List<DeptDoctorBean>) data.getSerializableExtra(Const.DATA);
                    doctorList.clear();
                    doctorList.addAll(list);
                    removeView();
                    addDoctor();
                    selectDoctor.setText("");
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
            doctorBox.removeView(mStack.get(i));
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
        if(errMsg==null||"".equals(errMsg.trim())){
            ToastUtil.shortToast(this, errMsg);
        }else{
            ToastUtil.shortToast(this, "图片上传失败!");
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
        ToastUtil.shortToast(this, errMsg);
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
     * 按照接口规则拼接上传的图片字符串
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
        map.put("referralExplain", CommonUtils.filterEmoji(explainInputEt.getText().toString().trim()));
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
