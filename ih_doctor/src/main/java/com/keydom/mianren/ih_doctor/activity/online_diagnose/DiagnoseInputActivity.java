package com.keydom.mianren.ih_doctor.activity.online_diagnose;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.adapter.DiagnoseSelectAdapter;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.controller.DiagnoseInputController;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.view.DiagnoseInputView;
import com.keydom.mianren.ih_doctor.adapter.ICD10ListAdapter;
import com.keydom.mianren.ih_doctor.bean.ICD10Bean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.utils.JsonUtils;
import com.keydom.mianren.ih_doctor.view.CustomRecognizerDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @Name???com.keydom.ih_doctor.activity.personal
 * @Description?????????????????????????????????
 * @Author???song
 * @Date???18/11/14 ??????10:37
 * ????????????xusong
 * ???????????????18/11/14 ??????10:37
 */
public class DiagnoseInputActivity extends BaseControllerActivity<DiagnoseInputController> implements DiagnoseInputView {

    /**
     * ????????????code
     */
    public static final int DIAGNOSE_RES_INPUT = 901;
    private EditText diagnoseInputEt, searchInputEv;
    private TextView searchTv;
    private RecyclerView recyclerView;
    private RecyclerView selectRecyclerView;
    private LinearLayout selectLayout;
    /**
     * ???????????????
     */
    private ICD10ListAdapter icd10ListAdapter;
    /**
     * ?????????????????????????????????
     */
    private DiagnoseSelectAdapter diagnoseSelectAdapter;
    /**
     * ??????????????????
     */
    private String inputStr = "";
    private RefreshLayout refreshLayout;
    /**
     * ??????????????????
     */
    private List<ICD10Bean> mList = new ArrayList<>();
    /**
     * ??????
     */
    private ArrayList<ICD10Bean> selectData = new ArrayList<>();

    private ImageView mVoiceInputIv;

    // ????????????UI
    private CustomRecognizerDialog mIatDialog;

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
            if (null != diagnoseInputEt) {
                String text = JsonUtils.handleXunFeiJson(results);
                if (TextUtils.isEmpty(diagnoseInputEt.getText().toString())) {
                    diagnoseInputEt.setText(text);
                    diagnoseInputEt.setSelection(diagnoseInputEt.getText().length());
                } else {
                    diagnoseInputEt.setText(diagnoseInputEt.getText().toString() + text);
                    diagnoseInputEt.setSelection(diagnoseInputEt.getText().length());
                }
            }

        }

        /**
         * ??????????????????.
         */
        public void onError(SpeechError error) {
            ToastUtil.showMessage(DiagnoseInputActivity.this, error.getPlainDescription(true));

        }

    };

    public static void start(Context context, String content) {
        Intent starter = new Intent(context, DiagnoseInputActivity.class);
        starter.putExtra(Const.DATA, content);
        ((Activity) context).startActivityForResult(starter, DIAGNOSE_RES_INPUT);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_diagnose_input_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        initView();
        setTitle("????????????");
        setRightTxt("??????");
        EventBus.getDefault().register(this);
        inputStr = getIntent().getStringExtra(Const.DATA);
        diagnoseInputEt.setText(inputStr);
        diagnoseInputEt.setSelection(inputStr.length());
        getController().icdCateList();
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @SingleClick(1000)
            @Override
            public void OnRightTextClick(View v) {
                if (selectData != null && selectData.size() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra(Const.DATA, diagnoseInputEt.getText().toString());
                    intent.putExtra("listData", selectData);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtil.showMessage(DiagnoseInputActivity.this, "?????????????????????");
                }
            }
        });

        diagnoseInputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputStr = s.toString();
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.GET_ICD_10_VALUE) {
            ICD10Bean bean = (ICD10Bean) messageEvent.getData();
            if (!selectData.contains(bean)) {
                selectData.add(bean);
            }
            diagnoseSelectAdapter.setNewData(selectData);
            appendInputStr();
        }
    }

    private void appendInputStr() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < selectData.size(); i++) {
            builder.append(selectData.get(i).getName());
            if (i != selectData.size() - 1) {
                builder.append(",");
            }
        }
        inputStr = builder.toString();
        diagnoseInputEt.setText(inputStr);
        com.keydom.ih_common.utils.CommonUtils.hideSoftKeyboard(this);
    }

    /**
     * ?????????????????????
     */
    private void initSelectedData() {
        if (!TextUtils.isEmpty(inputStr)) {
            String[] strings = inputStr.split(",");
            List<String> list = Arrays.asList(strings);
            selectData.clear();
            for (ICD10Bean bean : mList) {
                if (list.contains(bean.getName())) {
                    selectData.add(bean);
                }
            }
            diagnoseSelectAdapter.setNewData(selectData);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * ???????????????
     */
    private void initView() {
        searchInputEv = findViewById(R.id.search_input_ev);
        searchTv = findViewById(R.id.search_tv);
        refreshLayout = findViewById(R.id.refreshLayout);
        diagnoseInputEt = this.findViewById(R.id.diagnose_input_et);
        recyclerView = this.findViewById(R.id.icd_rv);
        mVoiceInputIv = this.findViewById(R.id.diagnose_input_layout_voice_input_iv);
        selectRecyclerView = findViewById(R.id.recycler_view);
        selectLayout = findViewById(R.id.select_layout);
        selectRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        diagnoseSelectAdapter = new DiagnoseSelectAdapter(selectData);
        selectRecyclerView.setAdapter(diagnoseSelectAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        refreshLayout.setOnRefreshListener(getController());
        refreshLayout.setOnLoadMoreListener(getController());
        searchTv.setOnClickListener(getController());
        showICD10List();

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

        //??????????????????
        diagnoseSelectAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                selectData.remove(position);
                diagnoseSelectAdapter.setNewData(selectData);
                appendInputStr();
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
                            ToastUtil.showMessage(DiagnoseInputActivity.this, "??????????????????");

                        } else {
                            ToastUtil.showMessage(DiagnoseInputActivity.this, "??????????????????????????????");

                        }
                    }
                });

    }

    /**
     * ??????ICD-10??????
     */
    private void showICD10List() {
        if (icd10ListAdapter == null) {
            icd10ListAdapter = new ICD10ListAdapter(DiagnoseInputActivity.this, mList);
        }
        recyclerView.setAdapter(icd10ListAdapter);
    }

    @Override
    public void getICDListSuccess(List<ICD10Bean> list) {
        mList.addAll(list);
        initSelectedData();
        icd10ListAdapter.notifyDataSetChanged();
        getController().currentPagePlus();
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public void getICDListFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
    }

    @Override
    public String getKeyWord() {
        return searchInputEv.getText().toString();
    }

    @Override
    public void clearList() {
        mList.clear();
    }
}
