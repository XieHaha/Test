package com.keydom.ih_doctor.activity.nurse_service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.nurse_service.controller.MaterialChooseController;
import com.keydom.ih_doctor.activity.nurse_service.view.MaterialChooseView;
import com.keydom.ih_doctor.adapter.MaterialChooseAdapter;
import com.keydom.ih_doctor.bean.MaterialBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.utils.JsonUtils;
import com.keydom.ih_doctor.view.CustomRecognizerDialog;
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
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class MaterialChooseActivity extends BaseControllerActivity<MaterialChooseController> implements MaterialChooseView {

    private RecyclerView recyclerView;
    private MaterialChooseAdapter materialChooseAdapter;
    private EditText searchEt;
    private TextView searchTv;
    private RefreshLayout refreshLayout;
    /**
     * 耗材列表
     */
    private List<MaterialBean> mList = new ArrayList<>();
    /**
     * 选中的耗材列表
     */
    private List<MaterialBean> selectList;

    private ImageView mVoiceInputIv;

    // 语音听写UI
    private CustomRecognizerDialog mIatDialog;

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {

            if (code != ErrorCode.SUCCESS) {
                Log.e("xunfei","初始化失败，错误码：" + code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
            }
        }
    };

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            if(null != searchEt){
                String text = JsonUtils.handleXunFeiJson(results);
                if(TextUtils.isEmpty(searchEt.getText().toString())){
                    searchEt.setText(text);
                    searchEt.setSelection(searchEt.getText().length());
                }else{
                    searchEt.setText(searchEt.getText().toString() + text);
                    searchEt.setSelection(searchEt.getText().length());
                }
            }

        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            ToastUtil.showMessage(MaterialChooseActivity.this,error.getPlainDescription(true));

        }

    };

    /**
     * 启动添加耗材页面
     *
     * @param context
     * @param list    已经添加了的耗材列表
     */
    public static void start(Context context, List<MaterialBean> list) {
        Intent starter = new Intent(context, MaterialChooseActivity.class);
        starter.putExtra(Const.DATA, (Serializable) list);
        ((Activity) context).startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_medical_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        selectList = (List<MaterialBean>) getIntent().getSerializableExtra(Const.DATA);
        setTitle("添加耗材、器材");
        setRightTxt("确定");
        initView();
        initList();
        getController().materialList(TypeEnum.REFRESH);
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @SingleClick(1000)
            @Override
            public void OnRightTextClick(View v) {
                if (materialChooseAdapter.getSelectList() == null || materialChooseAdapter.getSelectList().size() == 0) {
                    ToastUtil.showMessage(MaterialChooseActivity.this, "请选择耗材、器材后再提交");
                    return;
                } else {
                    MaterialUseActivity.start(MaterialChooseActivity.this, materialChooseAdapter.getSelectList());
                }

            }
        });
    }

    /**
     * 设置列表适配器
     */
    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        materialChooseAdapter = new MaterialChooseAdapter(mList, selectList);
        recyclerView.setAdapter(materialChooseAdapter);
    }


    /**
     * 初始化页面
     */
    private void initView() {
        recyclerView = this.findViewById(R.id.medical_rv);
        searchEt = this.findViewById(R.id.search_et);
        searchEt.setHint("请搜索耗材、器材关键字");
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
        rxPermissions.request(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            if(mIatDialog.isShowing()){
                                mIatDialog.dismiss();
                            }
                            mIatDialog.show();
                            ToastUtil.showMessage(MyApplication.mApplication,"请开始说话…");

                        } else {
                            ToastUtil.showMessage(MyApplication.mApplication,"请开启录音需要的权限");

                        }
                    }
                });

    }

    @Override
    public Map<String, Object> getMaterialListMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("keyword", searchEt.getText().toString().trim());
        map.put("hospitalId", MyApplication.userInfo.getHospitalId());
        return map;
    }

    @Override
    public void getMaterialListSuccess(List<MaterialBean> list, TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            mList.clear();
        }
        mList.addAll(list);
        materialChooseAdapter.notifyDataSetChanged();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void getMaterialListFailed(String errMsg) {
        pageLoadingFail();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }
}
