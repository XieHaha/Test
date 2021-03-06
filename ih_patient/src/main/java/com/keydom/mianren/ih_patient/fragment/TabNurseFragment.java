package com.keydom.mianren.ih_patient.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.CountItemView;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.login.LoginActivity;
import com.keydom.mianren.ih_patient.activity.nursing_service.NursingChooseHospitalActivity;
import com.keydom.mianren.ih_patient.adapter.ChooseHospitalAdapter;
import com.keydom.mianren.ih_patient.bean.BannerBean;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.HospitalAreaInfo;
import com.keydom.mianren.ih_patient.bean.NursingIndexInfo;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;
import com.keydom.mianren.ih_patient.callback.SingleClick;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.fragment.controller.TabNurseController;
import com.keydom.mianren.ih_patient.fragment.view.TabNurseView;
import com.keydom.mianren.ih_patient.view.MyScollerView;
import com.keydom.mianren.ih_patient.view.ScollerViewInterface;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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
 * ??????????????????
 *
 * @Author???song
 * @Date???18/11/5 ??????5:27
 */
public class TabNurseFragment extends BaseControllerFragment<TabNurseController> implements TabNurseView {


    private RelativeLayout emptyLayout;
    private TextView emptyTv, nursing_search_tv;
    private ImageView nurseServiceImg;
    private CountItemView await_pay_cv, await_diagnose_cv, diagnose_cv, finished_cv, await_evaluation_cv;
    private TextView nurse_function_consulting_online, nurse_function_base_nursing, nurse_function_after_pregnancy, nurse_function_professional_nursing;
    private RelativeLayout hot_service_one_layout, hot_service_two_layout, hot_service_three_layout, hot_service_four_layout, hot_service_five_layout, hot_service_six_layout;
    private TextView hot_service_one_name_tv, hot_service_one_dsc_tv, hot_service_one_price_tv, hot_service_two_name_tv, hot_service_two_dsc_tv, hot_service_two_price_tv, hot_service_three_name_tv, hot_service_three_dsc_tv, hot_service_three_price_tv;
    private TextView hot_service_four_name_tv, hot_service_four_dsc_tv, hot_service_four_price_tv, hot_service_five_name_tv, hot_service_five_dsc_tv, hot_service_five_price_tv, hot_service_six_name_tv, hot_service_six_dsc_tv, hot_service_six_price_tv;
    private ImageView hot_service_one_img, hot_service_two_img, hot_service_three_img,hot_service_four_img,hot_service_five_img, hot_service_six_img;
    private Map<String, Long> projectTypeMap = new HashMap<>();
    private long first_hot_project_id, second_hot_project_id, third_hot_project_id, fourth_hot_project_id, fifth_hot_project_id, sixth_hot_project_id;
    private String first_hot_project_name, second_hot_project_name, third_hot_project_name, fourth_hot_project_name, fifth_hot_project_name, sixth_hot_project_name;
    private SmartRefreshLayout nurse_index_refresh;
    private List<HospitalAreaInfo> hospitalListFromService = new ArrayList<>();
    private List<HospitalAreaInfo> hospitalList = new ArrayList<>();
    private ChooseHospitalAdapter chooseHospitalAdapter;
    private Long selectHospitalId;
    private String selectHospitalName;
    private PopupWindow hospitalPopupWindow;
    private LinearLayout titleLayout, qr_code_layout;
    private XBanner nurse_banner;
    private MyScollerView nurse_sv;
    private int unpayTag=0;
    private boolean isInited=false;
    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        nurse_sv=getView().findViewById(R.id.nurse_sv);
        nurse_banner = getView().findViewById(R.id.nurse_banner);
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
        nursing_search_tv = getView().findViewById(R.id.nursing_search_tv);
        nursing_search_tv.setOnClickListener(getController());
        nurse_function_consulting_online = getView().findViewById(R.id.nurse_function_consulting_online);
        nurse_function_consulting_online.setOnClickListener(getController());
        nurse_function_base_nursing = getView().findViewById(R.id.nurse_function_base_nursing);
        nurse_function_base_nursing.setOnClickListener(getController());
        nurse_function_after_pregnancy = getView().findViewById(R.id.nurse_function_after_pregnancy);
        nurse_function_after_pregnancy.setOnClickListener(getController());
        nurse_function_professional_nursing = getView().findViewById(R.id.nurse_function_professional_nursing);
        nurse_function_professional_nursing.setOnClickListener(getController());
        qr_code_layout = getView().findViewById(R.id.qr_code_layout);
        qr_code_layout.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if(Global.getUserId()!=-1){
                    EventBus.getDefault().post(new Event(EventType.STARTTOQR, null));
                }else {
                    ToastUtil.showMessage(getContext(),"????????????,??????????????????");
                }
            }
        });
        hot_service_one_layout = getView().findViewById(R.id.hot_service_one_layout);
        hot_service_two_layout = getView().findViewById(R.id.hot_service_two_layout);
        hot_service_three_layout = getView().findViewById(R.id.hot_service_three_layout);
        hot_service_four_layout = getView().findViewById(R.id.hot_service_four_layout);
        hot_service_five_layout = getView().findViewById(R.id.hot_service_five_layout);
        hot_service_six_layout = getView().findViewById(R.id.hot_service_six_layout);
        chooseHospitalAdapter = new ChooseHospitalAdapter(getContext(), hospitalList, new GeneralCallback.SelectHospitalListener() {
            @Override
            public void getSelectedHospital(HospitalAreaInfo hospitalAreaInfo) {
                Logger.e("getHospitalSuccess-->HospitalId==" + hospitalAreaInfo.getId() + "   HospitalName==" + hospitalAreaInfo.getName());
                selectHospitalId = hospitalAreaInfo.getId();
                selectHospitalName = hospitalAreaInfo.getName();
            }
        });

        hot_service_one_name_tv = getView().findViewById(R.id.hot_service_one_name_tv);
        hot_service_two_name_tv = getView().findViewById(R.id.hot_service_two_name_tv);
        hot_service_three_name_tv = getView().findViewById(R.id.hot_service_three_name_tv);
        hot_service_four_name_tv = getView().findViewById(R.id.hot_service_four_name_tv);
        hot_service_five_name_tv = getView().findViewById(R.id.hot_service_five_name_tv);
        hot_service_six_name_tv = getView().findViewById(R.id.hot_service_six_name_tv);

        hot_service_one_dsc_tv = getView().findViewById(R.id.hot_service_one_dsc_tv);
        hot_service_two_dsc_tv = getView().findViewById(R.id.hot_service_two_dsc_tv);
        hot_service_three_dsc_tv = getView().findViewById(R.id.hot_service_three_dsc_tv);
        hot_service_four_dsc_tv = getView().findViewById(R.id.hot_service_four_dsc_tv);
        hot_service_five_dsc_tv = getView().findViewById(R.id.hot_service_five_dsc_tv);
        hot_service_six_dsc_tv = getView().findViewById(R.id.hot_service_six_dsc_tv);

        hot_service_one_price_tv = getView().findViewById(R.id.hot_service_one_price_tv);
        hot_service_two_price_tv = getView().findViewById(R.id.hot_service_two_price_tv);
        hot_service_three_price_tv = getView().findViewById(R.id.hot_service_three_price_tv);
        hot_service_four_price_tv = getView().findViewById(R.id.hot_service_four_price_tv);
        hot_service_five_price_tv = getView().findViewById(R.id.hot_service_five_price_tv);
        hot_service_six_price_tv = getView().findViewById(R.id.hot_service_six_price_tv);

        hot_service_one_img = getView().findViewById(R.id.hot_service_one_img);
        hot_service_two_img = getView().findViewById(R.id.hot_service_two_img);
        hot_service_three_img = getView().findViewById(R.id.hot_service_three_img);
        hot_service_four_img = getView().findViewById(R.id.hot_service_four_img);
        hot_service_five_img = getView().findViewById(R.id.hot_service_five_img);
        hot_service_six_img = getView().findViewById(R.id.hot_service_six_img);

        emptyLayout = getView().findViewById(R.id.state_retry2);
        emptyLayout.setOnClickListener(getController());
        emptyTv = getView().findViewById(R.id.empty_text);
        nurse_index_refresh = getView().findViewById(R.id.nurse_index_refresh);
        nurse_index_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshUi();
            }
        });
        nurseServiceImg = getView().findViewById(R.id.nurse_service_img);
        nurseServiceImg.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                if (Global.getUserId() == -1) {
                    new GeneralDialog(getContext(), "????????????????????????????????????", new GeneralDialog.OnCloseListener() {
                        @Override
                        public void onCommit() {
                            LoginActivity.start(getContext());
                        }
                    }).setTitle("??????").setPositiveButton("??????").show();
                } else {
                    if (App.userInfo.getIdCard() != null && !"".equals(App.userInfo.getIdCard())) {
                        NursingChooseHospitalActivity.start(getContext(), null);
                    } else {
                        ToastUtil.showMessage(getContext(), "?????????????????????????????????????????????????????????????????????????????????");
                    }
                }
            }
        });
        nurse_sv.setScrollViewListener(new ScollerViewInterface() {
            @Override
            public void onScrollChanged(MyScollerView scrollView, int x, int y, int oldx, int oldy) {
                if(y>0){
                    titleLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }else {
                    titleLayout.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                }

            }
        });
        EventBus.getDefault().register(this);
        refreshData();
    }

    @Override
    public void lazyLoad() {
        //refreshData();
    }

    private void refreshData() {
        refreshUi();
        hospitalListFromService.clear();
        hospitalList.clear();
        hospitalListFromService.addAll(Global.getHospitalList());
        hospitalList.addAll(Global.getHospitalList());
        chooseHospitalAdapter.notifyDataSetChanged();
        selectHospitalId = App.hospitalId;
        selectHospitalName = App.hospitalName;
        isInited=true;
    }

    /**
     * ????????????map
     */
    private Map<String, Object> getRequestMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("state", 3);
        map.put("position", 1);
        map.put("hospitalId", App.hospitalId);
        return map;
    }

    /**
     * ????????????
     */
    private void refreshUi() {
        getController().getPatientHomePageByUserId();
        getController().getBannerPicByHospitalId(getRequestMap());
        /*if (Global.getUserId() == -1) {
            nurse_index_refresh.setEnableRefresh(false);
            emptyLayout.setVisibility(View.VISIBLE);
            emptyLayout.setClickable(false);
            emptyTv.setText("???????????????????????????????????????");
        }else {
            nurse_index_refresh.setEnableRefresh(true);
            if(emptyLayout.getVisibility()==View.VISIBLE){
                emptyLayout.setVisibility(View.GONE);
            }
            getController().getPatientHomePageByUserId();
            getController().getBannerPicByHospitalId(getRequestMap());
        }*/
    }

    /**
     * ??????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateLoginState(Event event) {
        if (event.getType() == EventType.UPDATELOGINSTATE) {
            refreshUi();
        }
    }

    /**
     * ????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateHospitalChange(Event event) {
        if (event.getType() == EventType.UPDATEHOSPITAL) {
            nursing_search_tv.setText(App.hospitalName);
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
        if(isInited){
            Logger.e("----------------------------------------------------------------??????UI??????----------------------------------------------------------");
            refreshUi();
        }

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
        return R.layout.fragment_tab_nurse;
    }

    @Override
    public void getIndexSuccess(NursingIndexInfo data) {
        nurse_index_refresh.finishRefresh();
        if (emptyLayout.getVisibility() == View.VISIBLE) {
            emptyLayout.setVisibility(View.GONE);
        }
        for (NursingIndexInfo.NurseCategoryIdAndNameDtosBean nurseCategoryIdAndNameDtosBean : data.getNurseCategoryIdAndNameDtos()) {

            projectTypeMap.put(nurseCategoryIdAndNameDtosBean.getName(), nurseCategoryIdAndNameDtosBean.getId());
            if ("????????????".equals(nurseCategoryIdAndNameDtosBean.getName())) {
                Global.setProfessionalProjectTypeId(nurseCategoryIdAndNameDtosBean.getId());
            }
            if ("????????????".equals(nurseCategoryIdAndNameDtosBean.getName())) {
                nurse_function_base_nursing.setVisibility(View.VISIBLE);
            } else if ("????????????".equals(nurseCategoryIdAndNameDtosBean.getName())) {
                nurse_function_professional_nursing.setVisibility(View.VISIBLE);
            } else if ("????????????".equals(nurseCategoryIdAndNameDtosBean.getName())) {
                nurse_function_after_pregnancy.setVisibility(View.VISIBLE);
            }
        }
        Global.setProjectTypeMap(projectTypeMap);
        unpayTag=data.getPatientHomePageWaitDealServiceDto().getUnpayTag();
        await_pay_cv.setCount("" + data.getPatientHomePageWaitDealServiceDto().getNonPayment());
        await_diagnose_cv.setCount("" + data.getPatientHomePageWaitDealServiceDto().getWaitingService());
        diagnose_cv.setCount("" + data.getPatientHomePageWaitDealServiceDto().getOnService());
        finished_cv.setCount("" + data.getPatientHomePageWaitDealServiceDto().getFinish());
        await_evaluation_cv.setCount("" + data.getPatientHomePageWaitDealServiceDto().getMark());
        cleanHotProject();
        if (data.getNurseProjectDetailDtos().size() >= 1) {
            hot_service_one_name_tv.setText(data.getNurseProjectDetailDtos().get(0).getName());
            hot_service_one_dsc_tv.setText(data.getNurseProjectDetailDtos().get(0).getSummary());
            hot_service_one_price_tv.setText("??" + data.getNurseProjectDetailDtos().get(0).getFee() + "???");
            first_hot_project_id = data.getNurseProjectDetailDtos().get(0).getId();
            first_hot_project_name = data.getNurseProjectDetailDtos().get(0).getName();
            hot_service_one_layout.setOnClickListener(getController());

            if(null != data.getNurseProjectDetailDtos().get(0).getHotImg()) {
                Glide.with(getContext()).load(Const.IMAGE_HOST + data.getNurseProjectDetailDtos().get(0).getHotImg()).into(hot_service_one_img);
            }
        }


        if (data.getNurseProjectDetailDtos().size() >= 2) {
            hot_service_two_name_tv.setText(data.getNurseProjectDetailDtos().get(1).getName());
            hot_service_two_dsc_tv.setText(data.getNurseProjectDetailDtos().get(1).getSummary());
            hot_service_two_price_tv.setText("??" + data.getNurseProjectDetailDtos().get(1).getFee() + "???");
            second_hot_project_id = data.getNurseProjectDetailDtos().get(1).getId();
            second_hot_project_name = data.getNurseProjectDetailDtos().get(1).getName();
            hot_service_two_layout.setOnClickListener(getController());
            if(null != data.getNurseProjectDetailDtos().get(1).getHotImg()) {
                Glide.with(getContext()).load(Const.IMAGE_HOST + data.getNurseProjectDetailDtos().get(1).getHotImg()).into(hot_service_two_img);
            }

        }


        if (data.getNurseProjectDetailDtos().size() >= 3) {
            hot_service_three_name_tv.setText(data.getNurseProjectDetailDtos().get(2).getName());
            hot_service_three_dsc_tv.setText(data.getNurseProjectDetailDtos().get(2).getSummary());
            hot_service_three_price_tv.setText("??" + data.getNurseProjectDetailDtos().get(2).getFee() + "???");
            third_hot_project_id = data.getNurseProjectDetailDtos().get(2).getId();
            third_hot_project_name = data.getNurseProjectDetailDtos().get(2).getName();
            hot_service_three_layout.setOnClickListener(getController());
            if(null != data.getNurseProjectDetailDtos().get(2).getHotImg()) {
                Glide.with(getContext()).load(Const.IMAGE_HOST + data.getNurseProjectDetailDtos().get(2).getHotImg()).into(hot_service_three_img);
            }

        }


        if (data.getNurseProjectDetailDtos().size() >= 4) {
            hot_service_four_name_tv.setText(data.getNurseProjectDetailDtos().get(3).getName());
            hot_service_four_dsc_tv.setText(data.getNurseProjectDetailDtos().get(3).getSummary());
            hot_service_four_price_tv.setText("??" + data.getNurseProjectDetailDtos().get(3).getFee() + "???");
            fourth_hot_project_id = data.getNurseProjectDetailDtos().get(3).getId();
            fourth_hot_project_name = data.getNurseProjectDetailDtos().get(3).getName();
            hot_service_four_layout.setOnClickListener(getController());
            if(null != data.getNurseProjectDetailDtos().get(3).getHotImg()) {
                Glide.with(getContext()).load(Const.IMAGE_HOST + data.getNurseProjectDetailDtos().get(3).getHotImg()).into(hot_service_four_img);
            }

        }


        if (data.getNurseProjectDetailDtos().size() >= 5) {
            hot_service_five_name_tv.setText(data.getNurseProjectDetailDtos().get(4).getName());
            hot_service_five_dsc_tv.setText(data.getNurseProjectDetailDtos().get(4).getSummary());
            hot_service_five_price_tv.setText("??" + data.getNurseProjectDetailDtos().get(4).getFee() + "???");
            fifth_hot_project_id = data.getNurseProjectDetailDtos().get(4).getId();
            fifth_hot_project_name = data.getNurseProjectDetailDtos().get(4).getName();
            hot_service_five_layout.setOnClickListener(getController());
            if(null != data.getNurseProjectDetailDtos().get(4).getHotImg()) {
                Glide.with(getContext()).load(Const.IMAGE_HOST + data.getNurseProjectDetailDtos().get(4).getHotImg()).into(hot_service_five_img);
            }
        }


        if (data.getNurseProjectDetailDtos().size() >= 6) {
            hot_service_six_name_tv.setText(data.getNurseProjectDetailDtos().get(5).getName());
            hot_service_six_dsc_tv.setText(data.getNurseProjectDetailDtos().get(5).getSummary());
            hot_service_six_price_tv.setText("??" + data.getNurseProjectDetailDtos().get(5).getFee() + "???");
            sixth_hot_project_id = data.getNurseProjectDetailDtos().get(5).getId();
            sixth_hot_project_name = data.getNurseProjectDetailDtos().get(5).getName();
            hot_service_six_layout.setOnClickListener(getController());
            if(null != data.getNurseProjectDetailDtos().get(5).getHotImg()) {
                Glide.with(getContext()).load(Const.IMAGE_HOST + data.getNurseProjectDetailDtos().get(5).getHotImg()).into(hot_service_six_img);
            }
        }

    }

    /**
     * ??????????????????
     */
    private void cleanHotProject() {

        hot_service_one_name_tv.setText("");
        hot_service_one_dsc_tv.setText("");
        hot_service_one_price_tv.setText("");
        hot_service_one_img.setImageResource(0);
        hot_service_one_layout.setOnClickListener(null);

        hot_service_two_name_tv.setText("");
        hot_service_two_dsc_tv.setText("");
        hot_service_two_price_tv.setText("");
        hot_service_two_img.setImageResource(0);
        hot_service_two_layout.setOnClickListener(null);

        hot_service_three_name_tv.setText("");
        hot_service_three_dsc_tv.setText("");
        hot_service_three_price_tv.setText("");
        hot_service_three_img.setImageResource(0);
        hot_service_three_layout.setOnClickListener(null);

        hot_service_four_name_tv.setText("");
        hot_service_four_dsc_tv.setText("");
        hot_service_four_price_tv.setText("");
        hot_service_four_layout.setOnClickListener(null);

        hot_service_five_name_tv.setText("");
        hot_service_five_dsc_tv.setText("");
        hot_service_five_price_tv.setText("");
        hot_service_five_layout.setOnClickListener(null);


        hot_service_six_name_tv.setText("");
        hot_service_six_dsc_tv.setText("");
        hot_service_six_price_tv.setText("");
        hot_service_six_img.setImageResource(0);
        hot_service_six_layout.setOnClickListener(null);

    }

    @Override
    public void getIndexFailed(String errMsg) {
        nurse_index_refresh.finishRefresh();
        emptyLayout.setVisibility(View.VISIBLE);
        emptyTv.setText("?????????????????????????????????");
        emptyLayout.setClickable(true);

    }

    @Override
    public long getProjectTypeId(String keyName) {
        if (projectTypeMap.containsKey(keyName)) {
            return projectTypeMap.get(keyName);
        } else {
            return -1;
        }

    }

    @Override
    public long getProjectId(int option) {
        switch (option) {
            case 0:
                return first_hot_project_id;
            case 1:
                return second_hot_project_id;
            case 2:
                return third_hot_project_id;
            case 3:
                return fourth_hot_project_id;
            case 4:
                return fifth_hot_project_id;
            case 5:
                return sixth_hot_project_id;
            default:
                return -1;
        }
    }

    @Override
    public String getProjectName(int option) {
        switch (option) {
            case 0:
                return first_hot_project_name;
            case 1:
                return second_hot_project_name;
            case 2:
                return third_hot_project_name;
            case 3:
                return fourth_hot_project_name;
            case 4:
                return fifth_hot_project_name;
            case 5:
                return sixth_hot_project_name;
            default:
                return "";
        }
    }

    @Override
    public void showHospitalPopupWindow() {
        Logger.e("????????????popupwindow");
        for (int position = 0; position <hospitalList.size() ; position++) {
            hospitalList.get(position).setSelected(false);
            if( hospitalList.get(position).getName().equals(App.hospitalName)) {
                hospitalList.get(position).setSelected(true);
            }
        }
        chooseHospitalAdapter.notifyDataSetChanged();

        View view = LayoutInflater.from(getContext()).inflate(R.layout.index_choose_hospital_popup_layout, titleLayout, false);
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
                    for (int position = 0; position <hospitalList.size() ; position++) {
                        hospitalList.get(position).setSelected(false);
                        if( hospitalList.get(position).getName().equals(App.hospitalName)) {
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
            @SingleClick(1000)
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
                    for (int position = 0; position <hospitalList.size() ; position++) {
                        hospitalList.get(position).setSelected(false);
                        if( hospitalList.get(position).getName().equals(App.hospitalName)) {
                            hospitalList.get(position).setSelected(true);
                        }
                    }
                    chooseHospitalAdapter.notifyDataSetChanged();
                }
            }
        });
        backgroudView.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                hospitalPopupWindow.dismiss();
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                hospitalPopupWindow.dismiss();
            }
        });
        TextView commitTv = view.findViewById(R.id.commit_tv);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                nursing_search_tv.setText(selectHospitalName);
                App.hospitalId = selectHospitalId;
                App.hospitalName = selectHospitalName;
                EventBus.getDefault().post(new Event(EventType.UPDATEHOSPITAL, null));
                hospitalPopupWindow.dismiss();
            }
        });
        recyclerView.setAdapter(chooseHospitalAdapter);
        hospitalPopupWindow = new PopupWindow(getContext(), null, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hospitalPopupWindow.setContentView(view);
        hospitalPopupWindow.setFocusable(true);
        hospitalPopupWindow.setWidth(titleLayout.getWidth());
        hospitalPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
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
            nurse_banner.setData(dataList, null);
            nurse_banner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(getContext()).load(Const.IMAGE_HOST + dataList.get(position).getPicturePath()).into((ImageView) view);
                }
            });
            nurse_banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
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
                            Logger.e("????????????");
                        }


                    }
                }
            });
        } else {
            final List<Integer> imgesUrl = new ArrayList<>();
            imgesUrl.add(R.mipmap.physical_exa_detail_head_icon);
            nurse_banner.setData(imgesUrl, null);
            nurse_banner.loadImage(new XBanner.XBannerAdapter() {
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
    public int getUnpaytag() {
        return unpayTag;
    }
}
