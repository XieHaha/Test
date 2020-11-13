package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller.AmniocentesisReserveController;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.fragment.AmniocentesisApplyFragment;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.fragment.AmniocentesisEvaluateFragment;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.fragment.AmniocentesisResultFragment;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.fragment.AmniocentesisWebFragment;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisReserveView;
import com.keydom.mianren.ih_patient.bean.AmniocentesisReserveBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.constant.AmniocentesisProtocol;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * @author 顿顿
 * @date 20/3/10 13:52
 * @des 羊水穿刺预约
 */
public class AmniocentesisReserveActivity extends BaseControllerActivity<AmniocentesisReserveController> implements AmniocentesisReserveView {
    public static final String PROTOCOL_TYPE = "protocol_type";
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private AmniocentesisWebFragment webFragment;
    private AmniocentesisApplyFragment authFragment;
    private AmniocentesisEvaluateFragment evaluateFragment;
    private AmniocentesisResultFragment resultFragment;

    private AmniocentesisReserveBean reserveBean;

    /**
     * 羊水穿刺预约评估参数
     */
    private Map<String, Object> evaluateParamsMap;

    private AmniocentesisProtocol protocol;

    /**
     * 启动
     */
    public static void start(Context context, AmniocentesisProtocol protocol) {
        Intent intent = new Intent(context, AmniocentesisReserveActivity.class);
        intent.putExtra(PROTOCOL_TYPE, protocol);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_amniocentesis_reserve;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        if (getIntent() != null) {
            protocol = (AmniocentesisProtocol) getIntent().getSerializableExtra(PROTOCOL_TYPE);
        }

        setTitle(getString(R.string.txt_amniocentesis_reserve));
        setRightTxt(getString(R.string.txt_inquire_and_cancel_reserve));
        setRightColor(R.color.edit_text_color);
        getTitleLayout().setOnRightTextClickListener(getController());
        showVirusTips(getString(R.string.txt_virus_tips_title),
                getString(R.string.txt_virus_tips));

        setLeftBtnListener(v -> {
            if (finishPage()) {
                finish();
            }
        });
        manager = getSupportFragmentManager();
        tabWebView();
    }

    private void showVirusTips(String title, String content) {
        new GeneralDialog(this, content)
                .setTitle(title)
                .setContentGravity(Gravity.LEFT)
                .setNegativeButtonIsGone(true)
                .show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStepConfirm(Event event) {
        switch (event.getType()) {
            case AMNIOCENTESIS_WEB_PROTOCOL:
                curPage = 0;
                break;
            case AMNIOCENTESIS_EVALUATE:
                curPage = 1;
                tabEvaluateView();
                break;
            case AMNIOCENTESIS_WEB_AGREE:
                evaluateParamsMap = (Map<String, Object>) event.getData();
                curPage = 2;
                protocol = AmniocentesisProtocol.AMNIOCENTESIS_AGREE_PROTOCOL;
                tabWebView();
                break;
            case AMNIOCENTESIS_APPLY:
                curPage = 3;
                tabApplyView();
                break;
            case AMNIOCENTESIS_WEB_NOTICE:
                reserveBean = (AmniocentesisReserveBean) event.getData();
                curPage = 4;
                protocol = AmniocentesisProtocol.AMNIOCENTESIS_NOTICE;
                tabWebView();
                break;
            case AMNIOCENTESIS_RESULT:
                curPage = 5;
                tabResultView();
                break;
            default:
                break;
        }
    }

    private void tabWebView() {
        transaction = manager.beginTransaction();
        hideAll(transaction);
        if (webFragment == null) {
            webFragment = new AmniocentesisWebFragment();
            webFragment.setProtocol(protocol);
            webFragment.setReserveBean(reserveBean);
            webFragment.setEvaluateParamsMap(evaluateParamsMap);
            transaction.add(R.id.layout_frame_root, webFragment);
        } else {
            transaction.show(webFragment);
            webFragment.setProtocol(protocol);
            webFragment.setReserveBean(reserveBean);
            webFragment.setEvaluateParamsMap(evaluateParamsMap);
            webFragment.onResume();
        }
        transaction.commitAllowingStateLoss();
    }

    private void tabEvaluateView() {
        transaction = manager.beginTransaction();
        hideAll(transaction);
        if (evaluateFragment == null) {
            evaluateFragment = new AmniocentesisEvaluateFragment();
            transaction.add(R.id.layout_frame_root, evaluateFragment);
        } else {
            transaction.show(evaluateFragment);
            evaluateFragment.onResume();
        }
        transaction.commitAllowingStateLoss();
    }

    private void tabApplyView() {
        transaction = manager.beginTransaction();
        hideAll(transaction);
        if (authFragment == null) {
            authFragment = new AmniocentesisApplyFragment();
            transaction.add(R.id.layout_frame_root, authFragment);
        } else {
            transaction.show(authFragment);
            authFragment.onResume();
        }
        transaction.commitAllowingStateLoss();
    }

    private void tabResultView() {
        transaction = manager.beginTransaction();
        hideAll(transaction);
        if (resultFragment == null) {
            resultFragment = new AmniocentesisResultFragment();
            transaction.add(R.id.layout_frame_root, resultFragment);
        } else {
            transaction.show(resultFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏所有碎片
     */
    private void hideAll(FragmentTransaction transaction) {
        if (authFragment != null) {
            transaction.hide(authFragment);
        }
        if (evaluateFragment != null) {
            transaction.hide(evaluateFragment);
        }
        if (resultFragment != null) {
            transaction.hide(resultFragment);
        }
        if (webFragment != null) {
            transaction.hide(webFragment);
        }
    }


    int curPage;

    /**
     * 页面逻辑处理
     */
    private boolean finishPage() {
        if (curPage == 4) {
            curPage = 3;
            tabApplyView();
            return false;
        } else if (curPage == 3) {
            curPage = 2;
            protocol = AmniocentesisProtocol.AMNIOCENTESIS_AGREE_PROTOCOL;
            tabWebView();
            return false;
        } else if (curPage == 1) {
            curPage = 0;
            protocol = AmniocentesisProtocol.AMNIOCENTESIS_WEB_RESERVE;
            tabWebView();
            return false;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!finishPage()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
