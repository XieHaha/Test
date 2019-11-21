package com.keydom.ih_doctor.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.bean.HeadNurseServiceOrderDetailBean;
import com.keydom.ih_doctor.bean.NursingProjectInfo;
import com.keydom.ih_doctor.m_interface.OnAddServiceItemDialogListener;
import com.keydom.ih_doctor.m_interface.OnNurseServiceItemResultListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * [底部弹出dialog]
 **/
public class AddNurseServiceDialog extends Dialog implements View.OnClickListener, OnNurseServiceItemResultListener {

    private ImageView cancelBtn;
    private RelativeLayout addRl, minusRl;
    private TextView serviceTimes, serviceItemTv, serviceFee, serviceUserInfo;
    private SelectFlowLayout serviceBox;
    private Context mContext;
    private Button submitBt;
    private int serviceAmount = 1;
    private double totalFee = 0;
    private OnAddServiceItemDialogListener mListener;
    private HeadNurseServiceOrderDetailBean baseInfo;
    private List<NursingProjectInfo> selectList=new ArrayList<>();
    private int limit;
    private boolean isUpdate=false;


    /**
     * @param context
     * @param frequency
     */
    public AddNurseServiceDialog(Context context, HeadNurseServiceOrderDetailBean info, int limit, int frequency, List<NursingProjectInfo> dataList, boolean isUpdate, OnAddServiceItemDialogListener listener) {
        super(context, R.style.dialogFullscreen);
        mContext = context;
        this.mListener = listener;
        this.baseInfo = info;
        this.limit = limit;
        serviceAmount=frequency;
        selectList.clear();
        selectList.addAll(dataList);
        this.isUpdate=isUpdate;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_nurse_service_layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(layoutParams);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        cancelBtn = (ImageView) findViewById(R.id.dialog_close);
        addRl = (RelativeLayout) findViewById(R.id.inquiry_scaler_add_layout);
        minusRl = (RelativeLayout) findViewById(R.id.inquiry_scaler_minus_layout);
        serviceTimes = (TextView) findViewById(R.id.inquiry_scaler_text_layout);
        serviceItemTv = (TextView) findViewById(R.id.service_item_tv);
        serviceUserInfo = (TextView) findViewById(R.id.service_user_info);
        serviceFee = (TextView) findViewById(R.id.service_fee);
        serviceBox = (SelectFlowLayout) findViewById(R.id.service_box);
        submitBt = (Button) findViewById(R.id.add_btn);
        if(isUpdate)
            submitBt.setText("修改");
        else
            submitBt.setText("添加");

        cancelBtn.setOnClickListener(this);
        submitBt.setOnClickListener(this);
        minusRl.setOnClickListener(this);
        addRl.setOnClickListener(this);
        serviceItemTv.setOnClickListener(this);
        setInfo();
        nurseServiceItemResult(selectList);
        serviceTimes.setText(String.valueOf(serviceAmount));
    }


    private void setInfo() {
        String baseInfoStr = "";
        for (int i = 0; i < baseInfo.getServiceName().size(); i++) {
            baseInfoStr = (i == 0) ? baseInfoStr + baseInfo.getServiceName().get(i) : baseInfoStr + "、" + baseInfo.getServiceName().get(i);
        }
        baseInfoStr = baseInfoStr + "(" + baseInfo.getServiceObject() + "  " + CommonUtils.getSex(baseInfo.getPatientSex()) + "  " + baseInfo.getPatientAge() + "岁)";
        serviceUserInfo.setText(baseInfoStr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:
                dismiss();
                break;
            case R.id.inquiry_scaler_minus_layout:
                if (serviceAmount > 1) {
                    serviceAmount--;
                }
                serviceTimes.setText(String.valueOf(serviceAmount));
                serviceFee.setText("¥" + totalFee * serviceAmount + "元");
                break;
            case R.id.inquiry_scaler_add_layout:
                if (serviceAmount == limit + 1) {
                    ToastUtil.showMessage(getContext(), "次数已经达到上限！");
                } else {
                    serviceAmount++;
                    serviceTimes.setText(String.valueOf(serviceAmount));
                    serviceFee.setText("¥" + totalFee * serviceAmount + "元");
                }
                break;
            case R.id.service_item_tv:
                mListener.dialogClick(v, null);
                break;
            case R.id.add_btn:
                if (serviceAmount == 0 || selectList == null || selectList.size() == 0) {
                    ToastUtil.showMessage(getContext(), "请选择正确的项目和服务次数后再提交！");
                    return;
                }
                setAddBtnEnable(false);
                mListener.dialogClick(v, getAddServiceItemMap());
                break;
        }
    }


    public void setAddBtnEnable(boolean enable) {
        submitBt.setEnabled(enable);
    }

    @Override
    public void nurseServiceItemResult(List<NursingProjectInfo> list) {
        selectList = list;
        if (serviceBox != null) {
            serviceBox.removeAllViews();
        }
        totalFee = 0;
        if (list != null && list.size() > 0) {

            serviceItemTv.setHint("");
            for (int i = 0; i < list.size(); i++) {
                addItemView(list.get(i).getName());
                totalFee += Double.parseDouble(list.get(i).getFee());
            }

        } else {
            serviceItemTv.setHint("请选择服务项目");
        }

        serviceFee.setText("¥" + totalFee * serviceAmount + "元");
    }

    public Map<String, Object> getAddServiceItemMap() {
        Map<String, Object> map = new HashMap<>();
        String serviceId = "";
        if (selectList != null) {
            for (int i = 0; i < selectList.size(); i++) {
                if (i == 0) {
                    serviceId += selectList.get(i).getId();
                } else {
                    serviceId += "," + selectList.get(i).getId();
                }
            }
        }
        if (baseInfo != null) {
            map.put("serviceId", serviceId);
            map.put("frequency", serviceAmount);
            map.put("userId", MyApplication.userInfo.getId());
            map.put("fee", totalFee * serviceAmount);
            map.put("orderNumber", baseInfo.getOrderNumber());
        }
        return map;
    }

    private void addItemView(String name) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fill_out_doctor_tag, null, true);
        TextView tagTv = view.findViewById(R.id.doctor_name);
        tagTv.setText(name);
        serviceBox.addView(view);
    }


}
