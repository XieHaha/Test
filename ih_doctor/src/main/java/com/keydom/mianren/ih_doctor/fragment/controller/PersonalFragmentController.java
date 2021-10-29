package com.keydom.mianren.ih_doctor.fragment.controller;

import android.view.View;

import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.SearchActivity;
import com.keydom.mianren.ih_doctor.activity.electronic_signature.SiChuanCAActivity;
import com.keydom.mianren.ih_doctor.activity.my_message.MyMessageActivity;
import com.keydom.mianren.ih_doctor.activity.personal.ArticleListActivity;
import com.keydom.mianren.ih_doctor.activity.personal.FeedBackActivity;
import com.keydom.mianren.ih_doctor.activity.personal.MyAttentionActivity;
import com.keydom.mianren.ih_doctor.activity.personal.MyEvaluationActivity;
import com.keydom.mianren.ih_doctor.activity.personal.MyIncomeActivity;
import com.keydom.mianren.ih_doctor.activity.personal.MyServiceActivity;
import com.keydom.mianren.ih_doctor.activity.personal.MyVisitingCardActivity;
import com.keydom.mianren.ih_doctor.activity.personal.PersonalInfoActivity;
import com.keydom.mianren.ih_doctor.activity.personal.SettingActivity;
import com.keydom.mianren.ih_doctor.bean.PersonalHomeBean;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.view.PersonalFragmentView;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.net.MessageService;
import com.keydom.mianren.ih_doctor.net.PersonalApiService;
import com.keydom.mianren.ih_doctor.utils.SelectHospitalPopUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Name：com.keydom.ih_doctor.fragment.controller
 * @Description：描述信息
 * @Author：song
 * @Date：18/11/14 上午10:56
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:56
 */
public class PersonalFragmentController extends ControllerImpl<PersonalFragmentView> implements View.OnClickListener {
    @SingleClick(1000)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_iv:
                PersonalInfoActivity.start(mContext);
                break;
            case R.id.my_article:
                ArticleListActivity.start(mContext, TypeEnum.MYARTICLE,null,null);
                break;
            case R.id.my_collect:
                ArticleListActivity.start(mContext, TypeEnum.MYCOLLECT,null,null);
                break;
            case R.id.my_evaluation:
                MyEvaluationActivity.start(mContext, MyEvaluationActivity.DEFAULT_EVALUATION);
                break;
            case R.id.my_feedback:
                FeedBackActivity.start(mContext);
                break;
            case R.id.my_insurance:
                break;
            case R.id.my_purse:
                MyIncomeActivity.start(mContext);
                break;
            case R.id.my_service:
                if(SharePreferenceManager.isAutony()){
                    MyServiceActivity.start(mContext,false);
                }else{
                    ToastUtil.showMessage(mContext,"还未实名认证，请实名认证再开通相关服务");
                }
                break;
            case R.id.my_setting:
                SettingActivity.start(mContext);
                break;
            case R.id.my_visiting_card:
                MyVisitingCardActivity.start(mContext);
                break;
            case R.id.my_attention:
                MyAttentionActivity.start(mContext);
                break;
            case R.id.finish_setting:
                PersonalInfoActivity.start(mContext);
                getView().hideInfoLl();
                break;
            case R.id.close_setting_tip:
                getView().hideInfoLl();
                break;
            case R.id.today_order_rl:
                break;
            case R.id.all_order_rl:
                break;
            case R.id.today_evaluation_rl:
                MyEvaluationActivity.start(getContext(), MyEvaluationActivity.TODAY_EVALUATION);
                break;
            case R.id.all_evaluation_rl:
                MyEvaluationActivity.start(getContext(), MyEvaluationActivity.TOTAL_EVALUATION);
                break;
            case R.id.search_btn:
                SearchActivity.start(getContext());
                break;
            case R.id.top_hospital_name:
                SelectHospitalPopUtil.getInstance().initPopWindow(getContext()).showHospitalPopupWindow(getView().getTitleLayout());
                break;
            case R.id.my_sign:
                //没有注册、跳转到注册页面
                //                SignUtils.toGetSignature(getContext());
                SiChuanCAActivity.start(getContext());
                break;
            case R.id.my_message:
                MyMessageActivity.start(getContext(),null);
                break;
            default:
        }
    }


    public void getPersonalHomeInfo() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PersonalApiService.class).home(getView().getHomeMap()), new HttpSubscriber<PersonalHomeBean>() {
            @Override
            public void requestComplete(@Nullable PersonalHomeBean data) {
                getView().getPersonalDataSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getPersonalDataFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    /**
     * 获取我的消息条数
     */
    public void getMyUnreadMessageNum() {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(MessageService.class).countMessage(SharePreferenceManager.getPhoneNumber()), new HttpSubscriber<Integer>(getContext(),getDisposable(),false,false) {
            @Override
            public void requestComplete(@Nullable Integer data) {
                getView().getUnreadMessagaCountSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                getView().getUnreadMessageCountFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

}
