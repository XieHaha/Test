package com.keydom.mianren.ih_patient.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.CountItemView;
import com.keydom.ih_common.view.SwitchButton;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_search.DiagnoseSearchActivity;
import com.keydom.mianren.ih_patient.adapter.ChooseHospitalAdapter;
import com.keydom.mianren.ih_patient.adapter.MyDocAndNurAdapter;
import com.keydom.mianren.ih_patient.adapter.RecommendDocAndNurAdapter;
import com.keydom.mianren.ih_patient.bean.BannerBean;
import com.keydom.mianren.ih_patient.bean.DiagnoseIndexBean;
import com.keydom.mianren.ih_patient.bean.DiagnosesAndNurDepart;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.HospitalAreaInfo;
import com.keydom.mianren.ih_patient.bean.RecommendDocAndNurBean;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;
import com.keydom.mianren.ih_patient.callback.SingleClick;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.keydom.mianren.ih_patient.fragment.controller.TabDiagnosesController;
import com.keydom.mianren.ih_patient.fragment.view.TabDiagnosesView;
import com.keydom.mianren.ih_patient.view.MyNestedScollView;
import com.keydom.mianren.ih_patient.view.NestScollViewInterface;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在线问诊页面
 *
 * @Author：song
 * @Date：18/11/5 下午5:27
 */
public class TabDiagnoseFragment extends BaseControllerFragment<TabDiagnosesController> implements TabDiagnosesView {
    private RelativeLayout emptyLayout;
    private TextView emptyTv;
    //科室ID
    private long deptId = -1;
    private long areaId = -1;
    private CountItemView await_pay_cv, await_diagnose_cv, diagnose_cv, finished_cv,
            await_evaluation_cv;
    private MyDocAndNurAdapter myDocAndNurAdapter;
    private RecommendDocAndNurAdapter recommendDocAndNurAdapter;
    private RecyclerView my_doctor_rv, recommend_doctor_rv;
    private TextView diagnoses_online_search_tv, choose_depart_tv, choose_area_tv, search_tv;
    private List<DiagnoseIndexBean.AttentionListBean> dataList = new ArrayList<>();
    private List<RecommendDocAndNurBean> recommendList = new ArrayList<>();
    private SwitchButton online_doctor_sw;
    private boolean isOnline = false;
    private LinearLayout qr_code_layout, titleLayout;
    private List<HospitalAreaInfo> hospitalListFromService = new ArrayList<>();
    private List<HospitalAreaInfo> hospitalList = new ArrayList<>();
    private ChooseHospitalAdapter chooseHospitalAdapter;
    private OptionsPickerView areaPickerView, deptPickerView;
    //选中的医院ID
    private Long selectHospitalId;
    //选中的医院名字
    private String selectHospitalName;
    private PopupWindow hospitalPopupWindow;
    private final int REQUEST_CODE = 100;
    private XBanner diagnoses_banner;
    private SmartRefreshLayout diagnose_index_refresh;
    private MyNestedScollView diagnose_sv;
    private List<HospitalAreaInfo> data = new ArrayList<>();
    public List<DiagnosesAndNurDepart> departmentList = new ArrayList<>();
    private int unpayTag = 0;
    private boolean isInited = false;

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        diagnose_sv = getView().findViewById(R.id.diagnose_sv);
        emptyLayout = getView().findViewById(R.id.state_retry2);
        diagnoses_banner = getView().findViewById(R.id.diagnoses_banner);
        emptyTv = getView().findViewById(R.id.empty_text);
        titleLayout = getView().findViewById(R.id.title_layout);
        await_pay_cv = getView().findViewById(R.id.await_pay_cv);
        await_pay_cv.setOnClickListener(getController());
        await_diagnose_cv = getView().findViewById(R.id.await_diagnose_cv);
        await_diagnose_cv.setOnClickListener(getController());
        diagnose_cv = getView().findViewById(R.id.diagnose_cv);
        diagnose_cv.setOnClickListener(getController());
        finished_cv = getView().findViewById(R.id.finished_cv);
        finished_cv.setOnClickListener(getController());
        await_evaluation_cv = getView().findViewById(R.id.await_evaluation_cv);
        await_evaluation_cv.setOnClickListener(getController());
        my_doctor_rv = getView().findViewById(R.id.my_doctor_rv);
        choose_depart_tv = getView().findViewById(R.id.choose_depart_tv);
        choose_depart_tv.setOnClickListener(getController());
        choose_area_tv = getView().findViewById(R.id.choose_area_tv);
        choose_area_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                showChooseAreaDialog(data);
            }
        });
        search_tv = getView().findViewById(R.id.search_tv);
        search_tv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                DiagnoseSearchActivity.start(getContext(), 0, isOnline ? 1 : 0, 1);
            }
        });
        my_doctor_rv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        myDocAndNurAdapter = new MyDocAndNurAdapter(dataList);
        myDocAndNurAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick(1000)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.e(myDocAndNurAdapter.getData().get(position).getUuid());
                Intent intent = new Intent(getContext(), DoctorOrNurseDetailActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("doctorCode", myDocAndNurAdapter.getData().get(position).getUuid());
                startActivity(intent);
            }
        });
        my_doctor_rv.setNestedScrollingEnabled(false);
        my_doctor_rv.setAdapter(myDocAndNurAdapter);
        diagnose_index_refresh = getView().findViewById(R.id.diagnose_index_refresh);
        diagnose_index_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshUi();
            }

        });
        diagnose_index_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getController().getRecommendNurse(getHospitslRecommendNurseQueryMap(isOnline),
                        TypeEnum.LOAD_MORE);
            }
        });
        recommend_doctor_rv = getView().findViewById(R.id.recommend_doctor_rv);
        recommendDocAndNurAdapter = new RecommendDocAndNurAdapter(recommendList);
        recommendDocAndNurAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @SingleClick(1000)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (recommendDocAndNurAdapter.getData().get(position).getIsInquiry() == 0) {
                    ToastUtil.showMessage(getContext(), "该医生暂未开通问诊服务");
                } else {
                    Logger.e(recommendDocAndNurAdapter.getData().get(position).getUuid());
                    Intent intent = new Intent(getContext(), DoctorOrNurseDetailActivity.class);
                    int type = 0;

                    intent.putExtra("type", 0);
                    intent.putExtra("doctorCode",
                            recommendDocAndNurAdapter.getData().get(position).getUuid());
                    startActivity(intent);
                }

            }
        });
        recommend_doctor_rv.setNestedScrollingEnabled(false);
        recommend_doctor_rv.setAdapter(recommendDocAndNurAdapter);
        diagnoses_online_search_tv = getView().findViewById(R.id.diagnoses_online_search_tv);

        diagnoses_online_search_tv.setOnClickListener(getController());
        qr_code_layout = getView().findViewById(R.id.qr_code_layout);
        qr_code_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.getUserId() != -1) {
                    EventBus.getDefault().post(new Event(EventType.STARTTOQR, null));
                } else {
                    ToastUtil.showMessage(getContext(), "你未登录,请登录后尝试");
                }
            }
        });
        online_doctor_sw = getView().findViewById(R.id.online_doctor_sw);
        chooseHospitalAdapter = new ChooseHospitalAdapter(getContext(), hospitalList,
                new GeneralCallback.SelectHospitalListener() {
                    @Override
                    public void getSelectedHospital(HospitalAreaInfo hospitalAreaInfo) {
                        Logger.e("getHospitalSuccess-->HospitalId==" + hospitalAreaInfo.getId() + "   " +
                                "HospitalName==" + hospitalAreaInfo.getName());
                        selectHospitalId = hospitalAreaInfo.getId();
                        selectHospitalName = hospitalAreaInfo.getName();
                    }
                });
        online_doctor_sw.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton buttonView, boolean isChecked) {
                isOnline = isChecked;
                getController().setCurrentPage(1);
                if (deptId == -1) {
                    getController().getRecommendNurse(getHospitslRecommendNurseQueryMap(isOnline)
                            , TypeEnum.REFRESH);
                } else {
                    getController().getRecommendNurse(getDepartmentRecommendNurseQueryMap(isOnline), TypeEnum.REFRESH);
                }
            }
        });
        diagnose_sv.setScrollViewListener(new NestScollViewInterface() {
            @Override
            public void onScrollChanged(NestedScrollView scrollView, int x, int y, int oldx,
                                        int oldy) {
                if (y > 0) {
                    titleLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    titleLayout.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                }
            }
        });
        /*getController().getHomeData(getHomeQueryMap());
        getController().getRecommendNurse(getHospitslRecommendNurseQueryMap(isOnline));*/
        EventBus.getDefault().register(this);

        diagnoses_online_search_tv.setText(App.hospitalName);
        hospitalListFromService.clear();
        hospitalList.clear();
        hospitalListFromService.addAll(Global.getHospitalList());
        hospitalList.addAll(Global.getHospitalList());
        chooseHospitalAdapter.notifyDataSetChanged();
        selectHospitalId = App.hospitalId;
        selectHospitalName = App.hospitalName;
    }

    @Override
    public void lazyLoad() {
        //refreshData();
    }

    /**
     * 刷新页面
     */
    private void refreshUi() {
        if (Global.getUserId() == -1) {
           /* emptyLayout.setVisibility(View.VISIBLE);
            emptyLayout.setClickable(false);
            emptyTv.setText("请前往个人中心登录后再尝试");*/
        } else {
           /* if(emptyLayout.getVisibility()==View.VISIBLE){
                emptyLayout.setVisibility(View.GONE);
            }*/
            diagnoses_online_search_tv.setText(App.hospitalName);
        }
        getController().setCurrentPage(1);
        getController().getHomeData(getHomeQueryMap());
        Logger.e("refreshUi---getHomeData");
        getController().getRecommendNurse(getHospitslRecommendNurseQueryMap(isOnline),
                TypeEnum.REFRESH);
        getController().getBannerPicByHospitalId(getRequestMap());
        getController().queryHospitalAreaList();
    }

    /**
     * 返回页面请求数据
     */
    private Map<String, Object> getRequestMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("state", 2);
        map.put("position", 1);
        map.put("hospitalId", App.hospitalId);
        return map;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateLoginState(Event event) {
        if (event.getType() == EventType.UPDATELOGINSTATE) {
            refreshUi();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateHospitalChange(Event event) {
        if (event.getType() == EventType.UPDATEHOSPITAL) {
            refreshUi();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateHospitalList(Event event) {
        if (event.getType() == EventType.UPDATELOCALHOSPITALLIST) {
            hospitalListFromService.clear();
            hospitalListFromService.addAll(Global.getHospitalList());
            hospitalList.clear();
            hospitalList.addAll(Global.getHospitalList());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshUi();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_tab_diagnose;
    }

    private Map<String, Object> getHomeQueryMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitalId", App.hospitalId);
        map.put("userId", Global.getUserId());
        map.put("type", 0);
        return map;
    }

    /**
     * 获取护理记录请求map
     */
    private Map<String, Object> getHospitslRecommendNurseQueryMap(boolean isOnline) {
        Map<String, Object> map = new HashMap<>();
        if (isOnline) {
            map.put("isOnline", 1);
        }
        if (areaId != -1) {
            map.put("hospitalAreaId", areaId);
        }
        map.put("isRecommend", 1);
        map.put("hospitalId", App.hospitalId);
        map.put("deptId", deptId);
        map.put("type", 0);
        return map;
    }

    /**
     * 获取科室护理记录请求map
     */
    private Map<String, Object> getDepartmentRecommendNurseQueryMap(boolean isOnline) {
        Map<String, Object> map = new HashMap<>();
        if (isOnline) {
            map.put("isOnline", 1);
        }
        map.put("deptId", deptId);
        map.put("type", 0);
        return map;
    }

    @Override
    public void getHomeDataSuccess(DiagnoseIndexBean data) {
        diagnose_index_refresh.finishRefresh();
        await_pay_cv.setCount("" + data.getPay());
        await_diagnose_cv.setCount("" + data.getAccept());
        diagnose_cv.setCount("" + data.getProceeding());
        finished_cv.setCount("" + data.getFinish());
        await_evaluation_cv.setCount("" + data.getComment());
        myDocAndNurAdapter.setNewData(data.getAttentionList());
        unpayTag = data.getUnpayTag();

    }

    @Override
    public void getHomeDataFailed(String errMsg) {
        diagnose_index_refresh.finishRefresh();
    }

    @Override
    public void getRecommendSuccess(List<RecommendDocAndNurBean> dataList, TypeEnum type) {
        if (type == TypeEnum.REFRESH) {
            recommendList.clear();
        }
        pageLoadingSuccess();
        diagnose_index_refresh.finishRefresh();

        if (dataList.size() < com.keydom.mianren.ih_patient.constant.Const.PAGE_SIZE) {
            diagnose_index_refresh.finishLoadMoreWithNoMoreData();
        } else {
            diagnose_index_refresh.finishLoadMore();
        }
        recommendList.addAll(dataList);
        getController().currentPagePlus();
        recommendDocAndNurAdapter.notifyDataSetChanged();
    }

    @Override
    public void getRecommendFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "查询失败:" + errMsg);
        diagnose_index_refresh.finishLoadMore();
        diagnose_index_refresh.finishRefresh();
        pageLoadingFail();
    }

    @Override
    public void getDepartListSuccess(List<DiagnosesAndNurDepart> data) {
        departmentList.clear();
        departmentList.addAll(data);
        showChooseDepartmentDialog(departmentList);
    }

    @Override
    public void getDepartListFailed(String errMsg) {

    }

    /**
     * 显示科室选择弹框
     */
    private void showChooseDepartmentDialog(List<DiagnosesAndNurDepart> departmentList) {
        if (departmentList != null && departmentList.size() != 0) {
            List<String> parentDepList = new ArrayList<>();
            final List<List<String>> childDepList = new ArrayList<>();
            for (int i = 0; i < departmentList.size(); i++) {
                parentDepList.add(departmentList.get(i).getName());
                List<String> parendChildList = new ArrayList<>();
                for (int j = 0; j < departmentList.get(i).getItems().size(); j++) {
                    parendChildList.add(departmentList.get(i).getItems().get(j).getName());
                }
                childDepList.add(parendChildList);
            }
            if (deptPickerView == null) {
                deptPickerView = new OptionsPickerBuilder(getContext(),
                        new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3,
                                                        View v) {
                                setDept(options1, option2);
                            }
                        }).build();
            }
            deptPickerView.setPicker(parentDepList, childDepList);
            deptPickerView.show();
        } else {
            ToastUtil.showMessage(getContext(), "没有查询到科室信息");
        }

    }

    public void setDept(int option1, int option2) {
        getController().setCurrentPage(1);
        deptId = departmentList.get(option1).getItems().get(option2).getId();
        choose_depart_tv.setText(departmentList.get(option1).getItems().get(option2).getName());
        getController().getRecommendNurse(getDepartmentRecommendNurseQueryMap(isOnline),
                TypeEnum.REFRESH);
    }

    /**
     * 显示院区选择弹框
     */
    private void showChooseAreaDialog(List<HospitalAreaInfo> data) {
        if (data != null && data.size() != 0) {
            List<String> areaList = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                areaList.add(data.get(i).getName());
            }
            if (areaPickerView == null) {
                areaPickerView = new OptionsPickerBuilder(getContext(),
                        new OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3,
                                                        View v) {
                                areaId = data.get(options1).getId();
                                choose_area_tv.setText(data.get(options1).getName());
                                choose_depart_tv.setText("全部科室");
                                deptId = -1;
                                getController().setCurrentPage(1);
                                getController().getRecommendNurse(getHospitslRecommendNurseQueryMap(isOnline), TypeEnum.REFRESH);
                            }
                        }).build();
            }
            areaPickerView.setPicker(areaList);
            areaPickerView.show();
        } else {
            ToastUtil.showMessage(getContext(), "该医院没有下属院区");
        }

    }

    @Override
    public void showHospitalPopupWindow() {
        Logger.e("执行打开popupwindow");
        for (int i = 0; i < hospitalList.size(); i++) {
            hospitalList.get(i).setSelected(false);
            if (hospitalList.get(i).getName().equals(App.hospitalName)) {
                hospitalList.get(i).setSelected(true);
            }
        }
        chooseHospitalAdapter.notifyDataSetChanged();
        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.index_choose_hospital_popup_layout, titleLayout, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.hospital_list_rv);
        TextView cancelTv = view.findViewById(R.id.cancel_tv);
        View backgroudView = view.findViewById(R.id.backgroud_view);
        EditText hospitalSearchEdt = view.findViewById(R.id.hospital_search_edt);
        hospitalSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    hospitalList.clear();
                    hospitalList.addAll(hospitalListFromService);
                    for (int position = 0; position < hospitalList.size(); position++) {
                        hospitalList.get(position).setSelected(false);
                        if (hospitalList.get(position).getName().equals(App.hospitalName)) {
                            hospitalList.get(position).setSelected(true);
                        }
                    }
                    chooseHospitalAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        TextView hospitalSearchTv = view.findViewById(R.id.hospital_search_tv);
        hospitalSearchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hospitalSearchEdt.getText().toString().trim().equals("")) {
                } else {
                    hospitalList.clear();
                    for (HospitalAreaInfo info : hospitalListFromService) {
                        if (info.getName().contains(hospitalSearchEdt.getText().toString().trim())) {
                            hospitalList.add(info);
                        }
                    }
                    for (int i = 0; i < hospitalList.size(); i++) {
                        hospitalList.get(i).setSelected(false);
                        if (hospitalList.get(i).getName().equals(App.hospitalName)) {
                            hospitalList.get(i).setSelected(true);
                        }
                    }
                    chooseHospitalAdapter.notifyDataSetChanged();
                }
            }
        });
        backgroudView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hospitalPopupWindow.dismiss();
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hospitalPopupWindow.dismiss();
            }
        });
        TextView commitTv = view.findViewById(R.id.commit_tv);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getController().setCurrentPage(1);
                diagnoses_online_search_tv.setText(selectHospitalName);
                App.hospitalId = selectHospitalId;
                App.hospitalName = selectHospitalName;
                EventBus.getDefault().post(new Event(EventType.UPDATEHOSPITAL, null));
                deptId = -1;
                choose_depart_tv.setText("全部科室");
                choose_area_tv.setText("院区");
                areaId = -1;
                getController().getHomeData(getHomeQueryMap());
                Logger.e("onClick---getHomeData");
                getController().getRecommendNurse(getHospitslRecommendNurseQueryMap(isOnline),
                        TypeEnum.REFRESH);
                hospitalPopupWindow.dismiss();
            }
        });
        recyclerView.setAdapter(chooseHospitalAdapter);
        hospitalPopupWindow = new PopupWindow(getContext(), null,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hospitalPopupWindow.setContentView(view);
        hospitalPopupWindow.setFocusable(true);
        hospitalPopupWindow.setWidth(titleLayout.getWidth());
        hospitalPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                backgroudView.setVisibility(View.INVISIBLE);
                hospitalList.clear();
                hospitalList.addAll(hospitalListFromService);
            }
        });

        hospitalPopupWindow.showAsDropDown(titleLayout);
        backgroudView.setVisibility(View.VISIBLE);
    }

    @Override
    public void getPicListSuccess(List<BannerBean> dataList) {
        if (dataList != null && dataList.size() != 0) {
            diagnoses_banner.setData(dataList, null);
            diagnoses_banner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(getContext()).load(Const.IMAGE_HOST + dataList.get(position).getPicturePath()).into((ImageView) view);
                }
            });
            diagnoses_banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, Object model, View view, int position) {
                    if (dataList.get(position).getPictureUrl() != null && !"".equals(dataList.get(position).getPictureUrl())) {
                        Logger.e(dataList.get(position).getPictureUrl().indexOf("https:") + "--" + dataList.get(position).getPictureUrl().indexOf("http:"));
                        if (dataList.get(position).getPictureUrl().indexOf("https:") == 0 || dataList.get(position).getPictureUrl().indexOf("http:") == 0) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(dataList.get(position).getPictureUrl());
                            intent.setData(content_url);
                            startActivity(intent);
                        } else {
                            Logger.e("地址非法");
                        }


                    }
                }
            });
        } else {
            final List<Integer> imgesUrl = new ArrayList<>();
            imgesUrl.add(R.mipmap.physical_exa_head_icon);
            diagnoses_banner.setData(imgesUrl, null);
            diagnoses_banner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(getContext()).load(imgesUrl.get(position)).into((ImageView) view);
                }
            });
        }
    }

    @Override
    public void getPicListFailed(String errMsg) {

    }

    @Override
    public void getAreaList(List<HospitalAreaInfo> data) {
        this.data.clear();
        this.data.addAll(data);
        if (data != null && data.size() == 1) {
            if (data.get(0).getId() == App.hospitalId) {
                choose_area_tv.setVisibility(View.GONE);
            } else {
                choose_area_tv.setVisibility(View.VISIBLE);
            }
        } else {
            choose_area_tv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getAreaListFailed(String msg) {
        ToastUtil.showMessage(getContext(), "接口异常" + msg);
    }

    @Override
    public Map<String, Object> getQueryDeptMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitalId", App.hospitalId);
        if (areaId != -1) {
            map.put("hospitalAreaId", areaId);
        }
        return map;
    }

    @Override
    public int getUnpaytag() {
        return unpayTag;
    }
}
