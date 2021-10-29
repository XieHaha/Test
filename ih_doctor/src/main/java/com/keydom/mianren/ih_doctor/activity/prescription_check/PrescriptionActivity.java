package com.keydom.mianren.ih_doctor.activity.prescription_check;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.prescription_check.controller.PrescriptionController;
import com.keydom.mianren.ih_doctor.activity.prescription_check.view.PrescriptionView;
import com.keydom.mianren.ih_doctor.bean.DoctorPrescriptionDetailBean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.utils.CloneUtil;
import com.keydom.mianren.ih_doctor.view.PrescriptionDetailView;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/19 上午11:34
 * 修改人：xusong
 * 修改时间：18/11/19 上午11:34
 */
public class PrescriptionActivity extends BaseControllerActivity<PrescriptionController> implements PrescriptionView {
    /**
     * 处方－带审核按钮
     */
    public static final int PRESCRIPTION_ACTION = 1100;
    /**
     * 处方－不带审核按钮
     */
    public static final int PRESCRIPTION_NOT_ACTION = 1101;

    private TextView checkYes, checkNo;
    private LinearLayout prescription_detail_layout;

    /**
     * 处方ID
     */
    private long id;
    /**
     * 审核提交状态
     */
    private int state = -1;

    /**
     * 页面类型
     */
    private int type;
    private LinearLayout actionLl;
    private ScrollView scrollView;
    private String auditOpinion;

    /**
     * view 宽高
     */
    private int width;
    private List<Integer> heights;
    //图片地址
    private List<String> imagePaths = new ArrayList<>();

    public static final String PDF_PATH = "/sdcard/screen_pdf.pdf";

    public Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (1 == msg.what) {
                getController().signPdf(heights.size(),msg.getData().getString("pw"));
            }
            return false;
        }
    });


    /**
     * 药师审核的时候启动处方详情页面
     *
     * @param context
     * @param id      处方ID
     */
    public static void startWithAction(Context context, long id) {
        Intent starter = new Intent(context, PrescriptionActivity.class);
        starter.putExtra(Const.PRESCRIPTION_ID, id);
        starter.putExtra(Const.TYPE, PRESCRIPTION_ACTION);
        context.startActivity(starter);
    }

    /**
     * 医生启动处方详情页面
     *
     * @param context
     * @param id      处方ID
     */
    public static void startCommon(Context context, long id) {
        Intent starter = new Intent(context, PrescriptionActivity.class);
        starter.putExtra(Const.PRESCRIPTION_ID, id);
        starter.putExtra(Const.TYPE, PRESCRIPTION_NOT_ACTION);
        context.startActivity(starter);
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_prescription_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("电子处方单");
        id = getIntent().getLongExtra(Const.PRESCRIPTION_ID, 0);
        type = getIntent().getIntExtra(Const.TYPE, 0);
        scrollView = findViewById(R.id.detail_box_sv);
        checkNo =  findViewById(R.id.check_no);
        checkYes =  findViewById(R.id.check_yes);
        prescription_detail_layout=findViewById(R.id.prescription_detail_layout);
        actionLl =  findViewById(R.id.action_ll);
        if (type == PRESCRIPTION_NOT_ACTION || SharePreferenceManager.getRoleId() != Const.ROLE_MEDICINE) {
            actionLl.setVisibility(View.GONE);
        }
        checkNo.setOnClickListener(getController());
        checkYes.setOnClickListener(getController());

        pageLoading();
        getController().getPrescriptionDetail();
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getPrescriptionDetail();
            }
        });
    }

    @Override
    public ScrollView getScrollView() {
        return scrollView;
    }

    @Override
    public void handleImageAndPdf(String password) {
        getController().showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //截屏
                CommonUtils.getBitmapByView(scrollView, new CommonUtils.ViewToImageCallBack() {
                    @Override
                    public void result(List<String> a, int b, List<Integer> c) {
                        imagePaths.clear();
                        imagePaths.addAll(a);
                        width = b;
                        heights = c;
                    }
                });
                //图片转pdf
                CloneUtil.imageToPDF(imagePaths, width, heights, PrescriptionActivity.PDF_PATH);
                Message message = mHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("pw", password);
                message.setData(bundle);
                message.what = 1;
                mHandler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void auditPass() {
        state = 1;
        auditOpinion = "";
        getController().isSign();
    }

    @Override
    public void auditReturn(String auditOpinion) {
        state = 0;
        this.auditOpinion = auditOpinion;
        getController().audit("");
    }

    @Override
    public void getDetailSuccess(DoctorPrescriptionDetailBean bean) {
        pageLoadingSuccess();
        setDetail(bean);
    }

    @Override
    public void getDetailFailed(String msg) {
        pageLoadingFail();
    }

    @Override
    public void auditSuccess(String successMsg) {
        EventBus.getDefault().post(new MessageEvent.Buidler().setType(EventType.PRESCRIPTION_UPDATE).build());
        mHandler.postDelayed(new Thread(new Runnable() {
            @Override
            public void run() {
                PrescriptionActivity.this.finish();
            }
        }), 500);
    }

    @Override
    public void auditFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public Map<String, Object> getDetailMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("hospitalId", MyApplication.userInfo.getHospitalId());
        map.put("id", id);
        return map;
    }

    @Override
    public Map<String, Object> getAuditMap(String fileId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("hospitalId", MyApplication.userInfo.getHospitalId());
        map.put("state", state);
        map.put("auditOpinion", auditOpinion);
        //签章文件id
        if (!TextUtils.isEmpty(fileId)) {
            map.put("auditorSignFileId", fileId);
        }
        return map;
    }

    /**
     * 设置处方信息
     *
     * @param bean 处方数据
     */
    private void setDetail(DoctorPrescriptionDetailBean bean) {
        prescription_detail_layout.removeAllViews();
        for (int i = 0; i <bean.getList().size() ; i++) {
            PrescriptionDetailView prescriptionDetailView=new PrescriptionDetailView(getContext());
            prescriptionDetailView.setData(bean,i);
            prescription_detail_layout.addView(prescriptionDetailView);
        }
    }


}
