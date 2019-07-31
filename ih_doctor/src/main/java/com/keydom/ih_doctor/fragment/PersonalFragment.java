package com.keydom.ih_doctor.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.view.CircleImageView;
import com.keydom.ih_common.view.CountItemView;
import com.keydom.ih_common.view.GeneralItemView;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.bean.PersonalHomeBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.fragment.controller.PersonalFragmentController;
import com.keydom.ih_doctor.fragment.view.PersonalFragmentView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Name：com.kentra.yxyz.fragment
 * @Description：个人中心页面
 * @Author：song
 * @Date：18/11/5 下午5:25
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:25
 */
public class PersonalFragment extends BaseControllerFragment<PersonalFragmentController> implements PersonalFragmentView {
    private GeneralItemView myArticle;
    private GeneralItemView myVisitingCard;
    private GeneralItemView myCollect;
    private GeneralItemView myService;
    private GeneralItemView myAttention;
    private GeneralItemView myEvaluation;
    private GeneralItemView myPurse;
    private GeneralItemView mySetting;
    private GeneralItemView myMessage;
    private GeneralItemView myFeedback;
    private GeneralItemView myInsurance;
    private GeneralItemView mySign;
    private ImageView settingIv, closeSettingTip;
    private CircleImageView personalIconCiv;
    private TextView personalUserNameTv, personalPositionTv, departmentTv, finishSetting, topHospitalName;
    private CountItemView todayOrderRl, allOrderRl, todayEvaluationRl, allEvaluationRl;
    private LinearLayout finishInfoLl;
    private RelativeLayout topTitleLayout;
    private Button searchButton;
    private RefreshLayout refreshLayout;

    /**
     * 初始化页面
     */
    private void initView() {
        searchButton = (Button) getView().findViewById(R.id.search_btn);
        searchButton.setOnClickListener(getController());
        closeSettingTip = (ImageView) getView().findViewById(R.id.close_setting_tip);
        personalIconCiv = (CircleImageView) getView().findViewById(R.id.personal_icon_civ);
        personalUserNameTv = (TextView) getView().findViewById(R.id.personal_user_name_tv);
        personalPositionTv = (TextView) getView().findViewById(R.id.personal_position_tv);
        topTitleLayout = (RelativeLayout) getView().findViewById(R.id.top_title_layout);
        refreshLayout = getView().findViewById(R.id.refreshLayout);
        departmentTv = (TextView) getView().findViewById(R.id.department_tv);
        finishSetting = (TextView) getView().findViewById(R.id.finish_setting);
        todayOrderRl = (CountItemView) getView().findViewById(R.id.today_order_rl);
        allOrderRl = (CountItemView) getView().findViewById(R.id.all_order_rl);
        todayEvaluationRl = (CountItemView) getView().findViewById(R.id.today_evaluation_rl);
        allEvaluationRl = (CountItemView) getView().findViewById(R.id.all_evaluation_rl);
        finishInfoLl = (LinearLayout) getView().findViewById(R.id.finish_info_ll);

        myInsurance = getView().findViewById(R.id.my_insurance);
        myArticle = (GeneralItemView) getView().findViewById(R.id.my_article);
        myCollect = (GeneralItemView) getView().findViewById(R.id.my_collect);
        myVisitingCard = (GeneralItemView) getView().findViewById(R.id.my_visiting_card);
        myService = (GeneralItemView) getView().findViewById(R.id.my_service);
        myAttention = (GeneralItemView) getView().findViewById(R.id.my_attention);
        myEvaluation = (GeneralItemView) getView().findViewById(R.id.my_evaluation);
        myPurse = (GeneralItemView) getView().findViewById(R.id.my_purse);
        mySetting = (GeneralItemView) getView().findViewById(R.id.my_setting);
        myMessage=getView().findViewById(R.id.my_message);
        myFeedback = (GeneralItemView) getView().findViewById(R.id.my_feedback);
        mySign = (GeneralItemView) getView().findViewById(R.id.my_sign);
        settingIv = (ImageView) getView().findViewById(R.id.setting_iv);
        myArticle.setOnClickListener(getController());
        myCollect.setOnClickListener(getController());
        myService.setOnClickListener(getController());
        myAttention.setOnClickListener(getController());
        myEvaluation.setOnClickListener(getController());
        myVisitingCard.setOnClickListener(getController());
        mySign.setOnClickListener(getController());
        settingIv.setOnClickListener(getController());
        myPurse.setOnClickListener(getController());
        mySetting.setOnClickListener(getController());
        myMessage.setOnClickListener(getController());
        myFeedback.setOnClickListener(getController());
        closeSettingTip.setOnClickListener(getController());
        finishSetting.setOnClickListener(getController());
        myInsurance.setOnClickListener(getController());
        todayOrderRl.setOnClickListener(getController());
        allOrderRl.setOnClickListener(getController());
        todayEvaluationRl.setOnClickListener(getController());
        allEvaluationRl.setOnClickListener(getController());
        topHospitalName = (TextView) getView().findViewById(R.id.top_hospital_name);
        topHospitalName.setOnClickListener(getController());
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getPersonalHomeInfo();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getController().getPersonalHomeInfo();
            }
        });

    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initView();
        if (Const.CHECK_AND_ACCEP) {
            mySign.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_personal;
    }


    @Override
    public void getPersonalDataSuccess(PersonalHomeBean bean) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
        if (bean.getIsPerfectInfo() == 1) {
            hideInfoLl();
        } else {
            showInfoLl();
        }
        pageLoadingSuccess();
        personalUserNameTv.setText(bean.getName());
        if (bean.getJobTitle() == null || "".equals(bean.getJobTitle())) {
            personalPositionTv.setVisibility(View.GONE);
        } else {
            personalPositionTv.setVisibility(View.VISIBLE);
            personalPositionTv.setText(bean.getJobTitle());
        }

        departmentTv.setText(bean.getDeptName());
        todayOrderRl.setCount(String.valueOf(bean.getCurrentOrder()));
        allOrderRl.setCount(String.valueOf(bean.getTotalOrder()));
        todayEvaluationRl.setCount(String.valueOf(bean.getCurrentComment()));
        allEvaluationRl.setCount(String.valueOf(bean.getTotalComment()));
        GlideUtils.load(personalIconCiv, Const.IMAGE_HOST + bean.getAvatar(), 0, 0, false, null);
        topHospitalName.setText(MyApplication.userInfo.getHospitalName());
    }

    @Override
    public void getPersonalDataFailed(String errMsg) {
        pageLoadingFail();
    }


    @Override
    public void lazyLoad() {
        pageLoading();
        getController().getPersonalHomeInfo();
    }

    @Override
    public Map<String, Object> getHomeMap() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    @Override
    public void hideInfoLl() {
        finishInfoLl.setVisibility(View.GONE);
    }

    @Override
    public RelativeLayout getTitleLayout() {
        return topTitleLayout;
    }

    /**
     * 显示完善信息按钮
     */
    public void showInfoLl() {
        finishInfoLl.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.UPDATE_USER_INFO) {
            getController().getPersonalHomeInfo();
        }
    }
}
