package com.keydom.mianren.ih_doctor.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.push.PushManager;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.consulting_arrange.ConsultingArrangeActivity;
import com.keydom.mianren.ih_doctor.activity.doctor_cooperation.DoctorCooperationActivity;
import com.keydom.mianren.ih_doctor.activity.health_manager.HealthManagerActivity;
import com.keydom.mianren.ih_doctor.activity.issue_information.NotificationListActivity;
import com.keydom.mianren.ih_doctor.activity.nurse_service.NurseServiceOrderListActivity;
import com.keydom.mianren.ih_doctor.activity.online_diagnose.DiagnoseOrderListActivity;
import com.keydom.mianren.ih_doctor.activity.online_triage.TriageOrderListActivity;
import com.keydom.mianren.ih_doctor.activity.prescription_check.PrescriptionCheckActivity;
import com.keydom.mianren.ih_doctor.adapter.WorkFunctionAdapter;
import com.keydom.mianren.ih_doctor.bean.HomeBean;
import com.keydom.mianren.ih_doctor.bean.HomeMsgBean;
import com.keydom.mianren.ih_doctor.bean.IndexMenuBean;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.ServiceConst;
import com.keydom.mianren.ih_doctor.fragment.controller.WorkFragmentController;
import com.keydom.mianren.ih_doctor.fragment.view.WorkFragmentView;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.utils.LocalizationUtils;
import com.keydom.mianren.ih_doctor.utils.SpacesItemDecoration;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.kentra.yxyz.fragment
 * @Description：工作台页面
 * @Author：song
 * @Date：18/11/5 下午5:25
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:25
 */
public class WorkFragment extends BaseControllerFragment<WorkFragmentController> implements WorkFragmentView {
    private ImageView iconCircleImageView;
    private ImageView editTv;
    private TextView userNameTv;
    //    private TextView addTv;
    //    private TextView hospitalTv;
    private TextView departmentTv;
    private TextView doctorTitle;
    private TextView topHospitalName;
    private TextView receiveOnlineName;
    private RelativeLayout receiveOnlineRe;
    private RelativeLayout cooperateOnlineRe;
    private RelativeLayout calculatorRe;
    private RelativeLayout dianoseToolRe;
    private RelativeLayout medicalScienceRe;
    private RelativeLayout guideRe;
    private ScrollView scrollView;
    private RelativeLayout titleBarLayout;
    private Button searchButton;
    private List<IndexMenuBean> dataList;
    private HomeBean homeBean;
    private RefreshLayout refreshLayout;
    private RecyclerView work_function_rv;
    private WorkFunctionAdapter workFunctionAdapter;
    private View receive_redpoint_view, cooperate_redpoint_view;

    private int auditNoPass = 0;
    private int noAudit = 0;
    private int receiveNurse = 0;
    private int receiveInquiry = 0;
    private int visitNurse = 0;
    private int receiveReferral = 0;

    private int RvWidth = 0;
    private int space = 0, bottom = 0;
    private int itemWidth = 0;


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_work;
    }

    @SuppressLint("NewApi")
    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        receive_redpoint_view = getView().findViewById(R.id.receive_redpoint_view);
        cooperate_redpoint_view = getView().findViewById(R.id.cooperate_redpoint_view);
        iconCircleImageView = getView().findViewById(R.id.user_icon);
        work_function_rv = getView().findViewById(R.id.work_function_rv);
        RvWidth = getRvWidth();
        space = dip2px(getContext(), 34);
        bottom = dip2px(getContext(), 10);
        itemWidth = (RvWidth - space * 3) / 4;
        work_function_rv.addItemDecoration(new SpacesItemDecoration(space, bottom));
        work_function_rv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        workFunctionAdapter = new WorkFunctionAdapter(getContext(), dataList, itemWidth);
        workFunctionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick(1000)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (SharePreferenceManager.isAutony()) {
                    switch (dataList.get(position).getName()) {
                        case "门诊排班":
                            ConsultingArrangeActivity.start(getContext());
                            break;
                        case "处方审核":
                            if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE, ServiceConst.MEDICINE_PRESCRIPTION_SERVICE_CODE})) {
                                PrescriptionCheckActivity.start(getContext(),
                                        ServiceConst.MEDICINE_PRESCRIPTION_SERVICE_CODE);
                            } else {
                                getController().showNotAccessDialog();
                            }
                            break;
                        case "处方查询":
                            if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE, ServiceConst.MEDICINE_PRESCRIPTION_SERVICE_CODE})) {
                                PrescriptionCheckActivity.start(getContext(),
                                        ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE);
                            } else {
                                getController().showNotAccessDialog();
                            }
                            break;
                        case "发布信息":
                            NotificationListActivity.start(getContext());
                            break;
                        case "医生协作":
                            if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_COOPERATE_SERVICE_CODE_Z, ServiceConst.DOCTOR_COOPERATE_SERVICE_CODE_H, ServiceConst.NURSE_COOPERATE_SERVICE_CODE, ServiceConst.MEDICINE_COOPERATE_SERVICE_CODE})) {
                                DoctorCooperationActivity.start(getContext());
                            } else {
                                getController().showNotAccessDialog();
                            }
                            break;
                        case "护理接单":
                            if (MyApplication.serviceEnable(new String[]{ServiceConst.NURSE_SERVICE_CODE})) {
                                NurseServiceOrderListActivity.headNurseStart(getContext());
                            } else {
                                getController().showNotAccessDialog();
                            }
                            break;
                        case "上门护理":
                            if (MyApplication.serviceEnable(new String[]{ServiceConst.NURSE_SERVICE_CODE})) {
                                NurseServiceOrderListActivity.commonNurseStart(getContext());
                            } else {
                                getController().showNotAccessDialog();
                            }
                            break;
                        case "在线接诊":
                            if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_ONLINE_DIAGNOSE_SERVICE_IMG_CODE, ServiceConst.DOCTOR_ONLINE_DIAGNOSE_SERVICE_VIDEO_CODE, ServiceConst.NURSE_IMG_CONSULT_SERVICE_CODE, ServiceConst.NURSE_VIDEO_CONSULT_SERVICE_CODE, ServiceConst.MEDICINE_CONSULT_SERVICE_CODE_IMG, ServiceConst.MEDICINE_CONSULT_SERVICE_CODE_VIDEO})) {
                                DiagnoseOrderListActivity.startDiagnose(getContext());
                            } else {
                                getController().showNotAccessDialog();
                            }
                            break;
                        case "在线咨询":
                            if (MyApplication.serviceEnable(new String[]{ServiceConst.NURSE_IMG_CONSULT_SERVICE_CODE, ServiceConst.NURSE_VIDEO_CONSULT_SERVICE_CODE, ServiceConst.MEDICINE_CONSULT_SERVICE_CODE_IMG, ServiceConst.MEDICINE_CONSULT_SERVICE_CODE_VIDEO})) {
                                DiagnoseOrderListActivity.startConsult(getContext());
                            } else {
                                getController().showNotAccessDialog();
                            }
                            break;
                        case "健康管理":
                            if (true) {
                                HealthManagerActivity.startConsult(getContext());
                            } else {
                                getController().showNotAccessDialog();
                            }
                            break;
                        case "分诊接收":
                            if (true) {
                                TriageOrderListActivity.start(getContext());
                            } else {
                                getController().showNotAccessDialog();
                            }
                            break;
                        default:
                    }
                } else {
                    ToastUtil.showMessage(getActivity(), "还未实名认证，请实名认证再开通相关服务");
                }
            }
        });
        work_function_rv.setAdapter(workFunctionAdapter);
        editTv = getView().findViewById(R.id.edit);
        userNameTv = getView().findViewById(R.id.user_name);
        refreshLayout = getView().findViewById(R.id.refreshLayout);
        receiveOnlineName = getView().findViewById(R.id.receive_online_name);
        //        addTv = getView().findViewById(R.id.address);
        departmentTv = getView().findViewById(R.id.department);
        doctorTitle = getView().findViewById(R.id.work_title);
        //        hospitalTv = getView().findViewById(R.id.hospital);
        receiveOnlineRe = getView().findViewById(R.id.receive_online_re);
        cooperateOnlineRe = getView().findViewById(R.id.cooperate_online_re);
        calculatorRe = getView().findViewById(R.id.calculator_re);
        dianoseToolRe = getView().findViewById(R.id.dianose_tool_re);
        medicalScienceRe = getView().findViewById(R.id.medical_science_re);
        guideRe = getView().findViewById(R.id.guide_re);
        scrollView = getView().findViewById(R.id.work_scrollview);
        titleBarLayout = getView().findViewById(R.id.title_bar_rl);
        searchButton = getView().findViewById(R.id.search_btn);
        searchButton.setOnClickListener(getController());
        scrollView.setOnScrollChangeListener(getController());
        cooperateOnlineRe.setOnClickListener(getController());
        receiveOnlineRe.setOnClickListener(getController());
        calculatorRe.setOnClickListener(getController());
        dianoseToolRe.setOnClickListener(getController());
        medicalScienceRe.setOnClickListener(getController());
        editTv.setOnClickListener(getController());
        guideRe.setOnClickListener(getController());
        topHospitalName = getView().findViewById(R.id.top_hospital_name);
        topHospitalName.setOnClickListener(getController());
        iconCircleImageView.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                CommonUtils.previewImage(getContext(), getUserIconStr());
            }
        });
        if (SharePreferenceManager.getRoleId() == Const.ROLE_NURSE) {
            receiveOnlineName.setText("护理服务");
            if (receiveNurse > 0 || visitNurse > 0)
                receive_redpoint_view.setVisibility(View.VISIBLE);
            else
                receive_redpoint_view.setVisibility(View.GONE);
        } else if (SharePreferenceManager.getRoleId() == Const.ROLE_MEDICINE) {
            receiveOnlineName.setText("在线咨询");
            if (receiveInquiry > 0)
                receive_redpoint_view.setVisibility(View.VISIBLE);
            else
                receive_redpoint_view.setVisibility(View.GONE);

        } else {
            receiveOnlineName.setText("网络门诊 （会员）");
            if (receiveInquiry > 0)
                receive_redpoint_view.setVisibility(View.VISIBLE);
            else
                receive_redpoint_view.setVisibility(View.GONE);
        }
        setReloadListener((v, status) -> lazyLoad());
        refreshLayout.setPrimaryColorsId(R.color.transparent, R.color.fontClickEnable);
        refreshLayout.setOnRefreshListener(refreshLayout -> getController().getHome(false));

    }

    @Override
    public void getHomeDataSuccess(HomeBean bean) {
        //        getController().getHomeCountMsg();
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
        if (bean == null) {
            pageEmpty();
            return;
        }
        homeBean = bean;
        pageLoadingSuccess();
        auditNoPass = homeBean.getAuditNoPass();
        noAudit = homeBean.getNoAudit();
        receiveNurse = homeBean.getReceiveNurse();
        receiveInquiry = homeBean.getReceiveInquiry();
        visitNurse = homeBean.getVisitNurse();
        receiveReferral = homeBean.getReceiveReferral();
        MyApplication.receiveReferral = receiveReferral;
        if (receiveReferral > 0)
            cooperate_redpoint_view.setVisibility(View.VISIBLE);
        else
            cooperate_redpoint_view.setVisibility(View.GONE);
        MyApplication.userInfo = bean.getInfo();
        PushManager.setAlias(getContext(), SharePreferenceManager.getPhoneNumber());
        MyApplication.accessInfoBean = bean.getAuth();
        if (MyApplication.deptBeanList.size() > 0) {//如果科室已经拿到，并且存在，则过滤科室，否则不过滤
            MyApplication.filterDept();
        }
        LocalizationUtils.fileSave2Local(getContext(), bean.getInfo(), Const.USER_INFO);
        SharePreferenceManager.setId(bean.getInfo().getId());


        //        GlideUtils.load(iconCircleImageView, Const.IMAGE_HOST + bean.getInfo()
        //        .getAvatar(), 0, 0, false, null);
        GlideUtils.loadWithBorder(iconCircleImageView,
                Const.IMAGE_HOST + bean.getInfo().getAvatar());
        userNameTv.setText(bean.getInfo().getName());
        //        if (bean.getInfo().getCityName() == null || "".equals(bean.getInfo()
        //        .getCityName())) {
        //            addTv.setVisibility(View.GONE);
        //        } else {
        //            addTv.setText(bean.getInfo().getCityName());
        //        }
        //        hospitalTv.setText(bean.getInfo().getHospitalName());
        departmentTv.setText(bean.getInfo().getDeptName());
        doctorTitle.setText(bean.getInfo().getJobTitle());
        dataList = new ArrayList<>();
        dataList.addAll(bean.getList());
        for (int i = 0; i < dataList.size(); i++) {
            IndexMenuBean menuBean = dataList.get(i);
            if ("在线接诊".equals(menuBean.getName()) || "在线咨询".equals(menuBean.getName())) {
                if (receiveInquiry > 0) {
                    menuBean.setRedPointShow(true);
                } else {
                    menuBean.setRedPointShow(false);
                }
            }
            if ("处方审核".equals(menuBean.getName())) {
                if (noAudit > 0) {
                    menuBean.setRedPointShow(true);
                } else {
                    menuBean.setRedPointShow(false);
                }
            }
            if ("处方查询".equals(menuBean.getName())) {
                if (auditNoPass > 0) {
                    menuBean.setRedPointShow(true);
                } else {
                    menuBean.setRedPointShow(false);
                }
            }
            if ("护理接单".equals(menuBean.getName())) {
                if (receiveNurse > 0) {
                    menuBean.setRedPointShow(true);
                } else {
                    menuBean.setRedPointShow(false);
                }
            }
            if ("上门护理".equals(menuBean.getName())) {
                if (visitNurse > 0) {
                    menuBean.setRedPointShow(true);
                } else {
                    menuBean.setRedPointShow(false);
                }
            }
        }

        //TODO 模拟健康管理数据
        //        IndexMenuBean menuBean = new IndexMenuBean();
        //        menuBean.setName("健康管理");
        IndexMenuBean menuBean1 = new IndexMenuBean();
        menuBean1.setName("分诊接收");
        //        dataList.add(menuBean);
        dataList.add(menuBean1);

        workFunctionAdapter.setNewData(dataList);
        if (SharePreferenceManager.getRoleId() == Const.ROLE_NURSE) {
            if (receiveNurse > 0 || visitNurse > 0)
                receive_redpoint_view.setVisibility(View.VISIBLE);
            else
                receive_redpoint_view.setVisibility(View.GONE);
        } else if (SharePreferenceManager.getRoleId() == Const.ROLE_MEDICINE) {
            if (receiveInquiry > 0)
                receive_redpoint_view.setVisibility(View.VISIBLE);
            else
                receive_redpoint_view.setVisibility(View.GONE);

        } else {
            if (receiveInquiry > 0)
                receive_redpoint_view.setVisibility(View.VISIBLE);
            else
                receive_redpoint_view.setVisibility(View.GONE);
        }
        topHospitalName.setText(MyApplication.userInfo.getHospitalName());
    }

    @Override
    public void getHomeDataFailed(String errMsg) {
        pageLoadingFail();
    }

    @Override
    public RelativeLayout getTitleLayout() {
        return titleBarLayout;
    }

    @Override
    public String getUserIconStr() {
        return (homeBean != null && homeBean.getInfo() != null && homeBean.getInfo().getAvatar() != null && !"".equals(homeBean.getInfo().getAvatar())) ? Const.IMAGE_HOST + homeBean.getInfo().getAvatar() : "";
    }


    @Override
    public void getHomeCountMsgSuccess(HomeMsgBean bean) {
        if (bean != null) {

        }
    }

    @Override
    public void lazyLoad() {
        pageLoading();
        //        getController().getHome();
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.e("刷新工作台数据");
        getController().getHome(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 刷新首页
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.UPDATE_USER_INFO) {
            getController().getHome(false);
        }
    }

    private int getRvWidth() {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        work_function_rv.measure(w, h);
        int width = work_function_rv.getMeasuredWidth();
        return width;
    }

    /**
     * 将dp转换成px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}

