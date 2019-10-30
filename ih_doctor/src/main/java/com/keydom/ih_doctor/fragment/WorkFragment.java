package com.keydom.ih_doctor.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.push.PushManager;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.GlideUtils;
import com.keydom.ih_common.utils.SharePreferenceManager;
import com.keydom.ih_common.view.PageIndicatorView;
import com.keydom.ih_common.view.PageRecyclerView;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.consulting_arrange.ConsultingArrangeActivity;
import com.keydom.ih_doctor.activity.doctor_cooperation.DoctorCooperationActivity;
import com.keydom.ih_doctor.activity.issue_information.NotificationListActivity;
import com.keydom.ih_doctor.activity.nurse_service.NurseServiceOrderListActivity;
import com.keydom.ih_doctor.activity.online_diagnose.DiagnoseOrderListActivity;
import com.keydom.ih_doctor.activity.prescription_check.PrescriptionCheckActivity;
import com.keydom.ih_doctor.adapter.WorkFunctionAdapter;
import com.keydom.ih_doctor.bean.HomeBean;
import com.keydom.ih_doctor.bean.HomeMsgBean;
import com.keydom.ih_doctor.bean.IndexMenuBean;
import com.keydom.ih_doctor.bean.MessageEvent;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.EventType;
import com.keydom.ih_doctor.constant.ServiceConst;
import com.keydom.ih_doctor.fragment.controller.WorkFragmentController;
import com.keydom.ih_doctor.fragment.view.WorkFragmentView;
import com.keydom.ih_doctor.m_interface.SingleClick;
import com.keydom.ih_doctor.utils.LocalizationUtils;
import com.keydom.ih_doctor.utils.SpacesItemDecoration;
import com.keydom.ih_doctor.utils.ToastUtil;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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

    private RecyclerView.Adapter rwAdapter = null;
    private PageRecyclerView workFunctionRecyclerView;
    private PageIndicatorView indicatorView;
    private ImageView iconCircleImageView;
    private ImageView editTv;
    private TextView userNameTv;
    private TextView addTv;
    private TextView hospitalTv;
    private TextView departmentTv;
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
    private List<IndexMenuBean> dataList = new ArrayList<>();
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
    private int space = 0;
    private int itemWidth = 0;


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_work;
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
        receiveReferral=homeBean.getReceiveReferral();
        MyApplication.receiveReferral=receiveReferral;
        if(receiveReferral>0)
            cooperate_redpoint_view.setVisibility(View.VISIBLE);
        else
            cooperate_redpoint_view.setVisibility(View.GONE);
        MyApplication.userInfo = bean.getInfo();
        PushManager.setAlias(getContext(), (String) SharePreferenceManager.getPhoneNumber());
        MyApplication.accessInfoBean = bean.getAuth();
        if (MyApplication.deptBeanList.size() > 0) {//如果科室已经拿到，并且存在，则过滤科室，否则不过滤
            MyApplication.filterDept();
        }
        LocalizationUtils.fileSave2Local(getContext(), bean.getInfo(), Const.USER_INFO);
        SharePreferenceManager.setId(bean.getInfo().getId());


//        GlideUtils.load(iconCircleImageView, Const.IMAGE_HOST + bean.getInfo().getAvatar(), 0, 0, false, null);
        GlideUtils.loadWithBorder(iconCircleImageView, Const.IMAGE_HOST + bean.getInfo().getAvatar());
        userNameTv.setText(bean.getInfo().getName());
        if (bean.getInfo().getCityName() == null || "".equals(bean.getInfo().getCityName())) {
            addTv.setVisibility(View.GONE);
        } else {
            addTv.setText(bean.getInfo().getCityName());
        }
        hospitalTv.setText(bean.getInfo().getHospitalName());
        departmentTv.setText(bean.getInfo().getDeptName());
        dataList.clear();
        dataList.addAll(bean.getList());
        for (int i = 0; i < dataList.size(); i++) {
            if ("在线接诊".equals(dataList.get(i).getName()) || "在线咨询".equals(dataList.get(i).getName())) {
                if (receiveInquiry > 0) {
                    dataList.get(i).setRedPointShow(true);
                } else {
                    dataList.get(i).setRedPointShow(false);
                }
            }
            if ("处方审核".equals(dataList.get(i).getName())) {
                if (noAudit > 0) {
                    dataList.get(i).setRedPointShow(true);
                } else {
                    dataList.get(i).setRedPointShow(false);
                }
            }
            if ("处方查询".equals(dataList.get(i).getName())) {
                if (auditNoPass > 0) {
                    dataList.get(i).setRedPointShow(true);
                } else {
                    dataList.get(i).setRedPointShow(false);
                }
            }
            if ("护理接单".equals(dataList.get(i).getName())) {
                if (receiveNurse > 0) {
                    dataList.get(i).setRedPointShow(true);
                } else {
                    dataList.get(i).setRedPointShow(false);
                }
            }
            if ("上门护理".equals(dataList.get(i).getName())) {
                if (visitNurse > 0) {
                    dataList.get(i).setRedPointShow(true);
                } else {
                    dataList.get(i).setRedPointShow(false);
                }
            }
        }
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
     /*   workFunctionRecyclerView.setPageMargin(0);
        workFunctionRecyclerView.setPageSize(1, 4);
        workFunctionRecyclerView.setIndicator(indicatorView);*/
       /* if (rwAdapter == null) {
            rwAdapter = workFunctionRecyclerView.new PageAdapter(R.dimen.dp_20, dataList, new PageRecyclerView.CallBack() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.work_function_item, parent, false);
                    return new MyHolder(view);
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                    ((MyHolder) holder).username.setText(dataList.get(position).getName());
                    if (dataList.get(position).isRedPointShow())
                        ((MyHolder) holder).redPointView.setVisibility(View.VISIBLE);
                    else
                        ((MyHolder) holder).redPointView.setVisibility(View.GONE);
                    ((MyHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                        @SingleClick(1000)
                        @Override
                        public void onClick(View v) {
                            switch (dataList.get(position).getName()) {
                                case "门诊排班":
                                    ConsultingArrangeActivity.start(getContext());
                                    break;
                                case "处方审核":
                                case "处方查询":
                                    if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE, ServiceConst.MEDICINE_PRESCRIPTION_SERVICE_CODE})) {
                                        PrescriptionCheckActivity.start(getContext());
                                    } else {
                                        getController().showNotAccessDialog();
                                    }
                                    break;
                                case "发布信息":
                                    NotificationListActivity.start(getContext());
                                    break;
                                case "医生协作":
                                    if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_COOPERATE_SERVICE_CODE, ServiceConst.NURSE_COOPERATE_SERVICE_CODE, ServiceConst.MEDICINE_COOPERATE_SERVICE_CODE})) {
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
                                    if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_ONLINE_DIAGNOSE_SERVICE_CODE})) {
                                        DiagnoseOrderListActivity.startDiagnose(getContext());
                                    } else {
                                        getController().showNotAccessDialog();
                                    }
                                    break;
                                case "在线咨询":
                                    if (MyApplication.serviceEnable(new String[]{ServiceConst.NURSE_CONSULT_SERVICE_CODE, ServiceConst.MEDICINE_CONSULT_SERVICE_CODE})) {
                                        DiagnoseOrderListActivity.startConsult(getContext());
                                    } else {
                                        getController().showNotAccessDialog();
                                    }

                                default:
                            }
                        }
                    });
                    int resId = GetMenuIconResId.getInstance().getId(dataList.get(position).getName());
                    if (resId == -1) {
                        ((MyHolder) holder).usericon.setImageDrawable(getResources().getDrawable(R.mipmap.nurse_visit));
                    } else {
                        ((MyHolder) holder).usericon.setImageDrawable(getResources().getDrawable(resId));
                    }
                }
            });
            workFunctionRecyclerView.setAdapter(rwAdapter);
        }else
            rwAdapter.notifyDataSetChanged();*/

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


    @SuppressLint("NewApi")
    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        receive_redpoint_view = getView().findViewById(R.id.receive_redpoint_view);
        cooperate_redpoint_view = getView().findViewById(R.id.cooperate_redpoint_view);
        indicatorView = (PageIndicatorView) getView().findViewById(R.id.indicator);
        iconCircleImageView = (ImageView) getView().findViewById(R.id.user_icon);
        work_function_rv = getView().findViewById(R.id.work_function_rv);
        RvWidth = getRvWidth();
        space = dip2px(getContext(), 34);
        itemWidth = (RvWidth - space * 3) / 4;
        work_function_rv.addItemDecoration(new SpacesItemDecoration(space));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        work_function_rv.setLayoutManager(layoutManager);
        workFunctionAdapter = new WorkFunctionAdapter(getContext(), dataList, itemWidth);
        workFunctionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick(1000)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(SharePreferenceManager.isAutony()){
                    switch (dataList.get(position).getName()) {
                        case "门诊排班":
                            ConsultingArrangeActivity.start(getContext());
                            break;
                        case "处方审核":
                            if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE, ServiceConst.MEDICINE_PRESCRIPTION_SERVICE_CODE})) {
                                PrescriptionCheckActivity.start(getContext(), ServiceConst.MEDICINE_PRESCRIPTION_SERVICE_CODE);
                            } else {
                                getController().showNotAccessDialog();
                            }
                            break;
                        case "处方查询":
                            if (MyApplication.serviceEnable(new String[]{ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE, ServiceConst.MEDICINE_PRESCRIPTION_SERVICE_CODE})) {
                                PrescriptionCheckActivity.start(getContext(), ServiceConst.DOCTOR_PRESCRIPTION_SERVICE_CODE);
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

                        default:
                    }
                }else{
                    ToastUtil.shortToast(getActivity(),"还未实名认证，请实名认证再开通相关服务");
                }
            }
        });
        work_function_rv.setAdapter(workFunctionAdapter);
        workFunctionRecyclerView = (PageRecyclerView) getView().findViewById(R.id.work_function_rw);
        editTv = (ImageView) getView().findViewById(R.id.edit);
        userNameTv = (TextView) getView().findViewById(R.id.user_name);
        refreshLayout = (RefreshLayout) getView().findViewById(R.id.refreshLayout);
        receiveOnlineName = (TextView) getView().findViewById(R.id.receive_online_name);
        addTv = (TextView) getView().findViewById(R.id.address);
        departmentTv = (TextView) getView().findViewById(R.id.department);
        hospitalTv = (TextView) getView().findViewById(R.id.hospital);
        receiveOnlineRe = (RelativeLayout) getView().findViewById(R.id.receive_online_re);
        cooperateOnlineRe = (RelativeLayout) getView().findViewById(R.id.cooperate_online_re);
        calculatorRe = (RelativeLayout) getView().findViewById(R.id.calculator_re);
        dianoseToolRe = (RelativeLayout) getView().findViewById(R.id.dianose_tool_re);
        medicalScienceRe = (RelativeLayout) getView().findViewById(R.id.medical_science_re);
        guideRe = (RelativeLayout) getView().findViewById(R.id.guide_re);
        scrollView = (ScrollView) getView().findViewById(R.id.work_scrollview);
        titleBarLayout = (RelativeLayout) getView().findViewById(R.id.title_bar_rl);
        searchButton = (Button) getView().findViewById(R.id.search_btn);
        searchButton.setOnClickListener(getController());
        scrollView.setOnScrollChangeListener(getController());
        receiveOnlineRe.setOnClickListener(getController());
        cooperateOnlineRe.setOnClickListener(getController());
        receiveOnlineRe.setOnClickListener(getController());
        calculatorRe.setOnClickListener(getController());
        dianoseToolRe.setOnClickListener(getController());
        medicalScienceRe.setOnClickListener(getController());
        editTv.setOnClickListener(getController());
        guideRe.setOnClickListener(getController());
        topHospitalName = (TextView) getView().findViewById(R.id.top_hospital_name);
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
            receiveOnlineName.setText("在线接诊");
            if (receiveInquiry > 0)
                receive_redpoint_view.setVisibility(View.VISIBLE);
            else
                receive_redpoint_view.setVisibility(View.GONE);
        }
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                lazyLoad();
            }
        });
        refreshLayout.setPrimaryColorsId(R.color.transparent, R.color.fontClickEnable);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getController().getHome();
            }
        });

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
        getController().getHome();
    }

    /**
     * 常用功能holder
     */
    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView username = null;
        public ImageView usericon = null;
        public View redPointView = null;

        public MyHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.work_function_name);
            usericon = (ImageView) itemView.findViewById(R.id.work_function_icon);
            redPointView = itemView.findViewById(R.id.item_redpoint_view);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 刷新首页
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.UPDATE_USER_INFO) {
            getController().getHome();
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
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}

