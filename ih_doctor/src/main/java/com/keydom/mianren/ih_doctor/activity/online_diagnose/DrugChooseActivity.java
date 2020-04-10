package com.keydom.mianren.ih_doctor.activity.online_diagnose;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.controller.DrugChooseController;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.DrugChooseView;
import com.keydom.mianren.ih_doctor.adapter.DrugChooseAdapter;
import com.keydom.mianren.ih_doctor.bean.DrugBean;
import com.keydom.mianren.ih_doctor.bean.DrugListBean;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.utils.JsonUtils;
import com.keydom.mianren.ih_doctor.view.CustomRecognizerDialog;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Description：药品选择页面
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class DrugChooseActivity extends BaseControllerActivity<DrugChooseController> implements DrugChooseView {

    private RecyclerView recyclerView;
    /**
     * 药品适配器
     */
    private DrugChooseAdapter drugChooseAdapter;
    private EditText searchEt;
    private TextView searchTv;
    private RefreshLayout refreshLayout;
    /**
     * 药品列表
     */
    private List<DrugBean> mList = new ArrayList<>();
    /**
     * 已经选中的药品列表
     */
    private List<DrugBean> selectList;
    private int position;

    private String IsPrescriptionStyle = null;//院内处方标识

    private ImageView mVoiceInputIv;

    // 语音听写UI
    private CustomRecognizerDialog mIatDialog;

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = code -> {
        if (code != ErrorCode.SUCCESS) {
            Log.e("xunfei", "初始化失败，错误码：" + code + ",请点击网址https://www.xfyun" +
                    ".cn/document/error-code查询解决方案");
        }
    };

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            if (null != searchEt) {
                String text = JsonUtils.handleXunFeiJson(results);
                if (TextUtils.isEmpty(searchEt.getText().toString())) {
                    searchEt.setText(text);
                    searchEt.setSelection(searchEt.getText().length());
                } else {
                    searchEt.setText(searchEt.getText().toString() + text);
                    searchEt.setSelection(searchEt.getText().length());
                }
            }

        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            ToastUtil.showMessage(DrugChooseActivity.this, error.getPlainDescription(true));

        }

    };

    /**
     * 启动药品选择页面
     *
     * @param context
     * @param list    已经选择了的药品
     */
    public static void start(Context context, List<DrugBean> list, int position,
                             String isPrescriptionStyle) {
        Intent starter = new Intent(context, DrugChooseActivity.class);
        starter.putExtra(Const.DATA, (Serializable) list);
        starter.putExtra("position", position);
        starter.putExtra(Const.IsPrescriptionStyle, isPrescriptionStyle);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_medical_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        selectList = (List<DrugBean>) getIntent().getSerializableExtra(Const.DATA);
        position = getIntent().getIntExtra("position", 0);
        IsPrescriptionStyle = getIntent().getStringExtra(Const.IsPrescriptionStyle);
        getController().getIsPrescriptionType(IsPrescriptionStyle);
        Logger.e("IsPrescriptionStyle=" + IsPrescriptionStyle);
        setTitle("选择药品");
        setRightTxt("确定");
        initView();
        initList();
        if (IsPrescriptionStyle.equals("0")) {
            getController().drugsList(TypeEnum.REFRESH);
        } else if (IsPrescriptionStyle.equals("1")) {
            getController().drugsListWaiYan(TypeEnum.REFRESH);
        }


        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @SingleClick(1000)
            @Override
            public void OnRightTextClick(View v) {
                if (drugChooseAdapter.getSelectList() == null || drugChooseAdapter.getSelectList().size() == 0) {
                    ToastUtil.showMessage(DrugChooseActivity.this, "请选择药品后再提交");
                    return;
                } else {
                    DrugListBean drugListBean = new DrugListBean();
                    drugListBean.setPosition(position);
                    drugListBean.setDrugList(drugChooseAdapter.getSelectList());
                    DrugUseActivity.start(DrugChooseActivity.this, drugListBean);
                }

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
                            ToastUtil.showMessage(MyApplication.mApplication, "请开始说话…");

                        } else {
                            ToastUtil.showMessage(MyApplication.mApplication, "请开启录音需要的权限");

                        }
                    }
                });

    }

    /**
     * 设置药品列表
     */
    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        drugChooseAdapter = new DrugChooseAdapter(getListWithCompare(mList, selectList));
        recyclerView.setAdapter(drugChooseAdapter);
    }

    private List<DrugBean> getListWithCompare(List<DrugBean> dataList, List<DrugBean> seletedList) {
        if (seletedList != null && seletedList.size() > 0) {
            for (int i = 0; i < seletedList.size(); i++) {
                for (int j = 0; j < dataList.size(); j++) {
                    if (dataList.get(j).getDrugsId() == seletedList.get(i).getDrugsId())
                        dataList.get(j).setSelecte(true);
                }
            }
            List<DrugBean> finalList = new ArrayList<>();
            for (int i = 0; i < dataList.size(); i++) {
                if (!dataList.get(i).isSelecte())
                    finalList.add(dataList.get(i));
            }
            return finalList;
        } else
            return dataList;

    }


    /**
     * 初始化页面
     */
    private void initView() {
        recyclerView = this.findViewById(R.id.medical_rv);
        searchEt = this.findViewById(R.id.search_et);
        searchTv = this.findViewById(R.id.search_tv);
        refreshLayout = this.findViewById(R.id.refresh_layout);
        mVoiceInputIv = this.findViewById(R.id.choose_medical_layout_voice_input_iv);
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        searchTv.setOnClickListener(getController());

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new CustomRecognizerDialog(this, mInitListener);
        mIatDialog.setListener(mRecognizerDialogListener);
        mVoiceInputIv.setOnClickListener(v -> initPremissions());
    }

    @Override
    public Map<String, Object> getDrugListMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("keyword", searchEt.getText().toString().trim());
        return map;
    }

    @Override
    public Map<String, Object> getDrugListWaiYanMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("param", searchEt.getText().toString().trim());
        return map;
    }

    @Override
    public void getDrugListSuccess(List<DrugBean> list, TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            mList.clear();
        }
        mList.addAll(getListWithCompare(list, selectList));
        drugChooseAdapter.setNewData(mList);
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void getDrugListFailed(String errMsg) {
        pageLoadingFail();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }
}
