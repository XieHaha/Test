package com.keydom.ih_patient.activity.nursing_service;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.view.CountItemView;
import com.keydom.ih_common.view.SwitchButton;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.ih_patient.activity.nursing_service.controller.NursingOnlineConsultController;
import com.keydom.ih_patient.activity.nursing_service.view.NursingOnlineConsultView;
import com.keydom.ih_patient.activity.online_diagnoses_search.DiagnoseSearchActivity;
import com.keydom.ih_patient.adapter.ChooseHospitalAdapter;
import com.keydom.ih_patient.adapter.MyDocAndNurAdapter;
import com.keydom.ih_patient.adapter.RecommendDocAndNurAdapter;
import com.keydom.ih_patient.bean.DiagnoseIndexBean;
import com.keydom.ih_patient.bean.DiagnosesAndNurDepart;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.RecommendDocAndNurBean;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.utils.ToastUtil;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.decoding.Intents;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * 护理服务在线问诊页面
 */
public class NursingOnlineConsultActivity extends BaseControllerActivity<NursingOnlineConsultController> implements NursingOnlineConsultView {
    public static void start(Context context) {
        context.startActivity(new Intent(context, NursingOnlineConsultActivity.class));
    }

    private long deptId = -1;
    private long areaId = -1;
    private CountItemView await_pay_cv, await_diagnose_cv, diagnose_cv, finished_cv, await_evaluation_cv;
    private MyDocAndNurAdapter myDocAndNurAdapter;
    private RecommendDocAndNurAdapter recommendDocAndNurAdapter;
    private RecyclerView my_nurse_rv, recommend_nurse_rv;
    private TextView nursing_online_search_tv, choose_depart_tv, choose_area_tv, search_tv;
    private List<DiagnoseIndexBean.AttentionListBean> dataList = new ArrayList<>();
    private List<RecommendDocAndNurBean> recommendList = new ArrayList<>();
    private SwitchButton online_doctor_sw;
    private boolean isOnline = false;
    private LinearLayout qr_code_layout, titleLayout;
    private List<HospitalAreaInfo> hospitalListFromService = new ArrayList<>();
    private List<HospitalAreaInfo> hospitalList = new ArrayList<>();
    private ChooseHospitalAdapter chooseHospitalAdapter;
    private Long selectHospitalId;
    private String selectHospitalName;
    private PopupWindow hospitalPopupWindow;
    private final int REQUEST_CODE = 100;
    private OptionsPickerView areaPickerView,deptPickerView;
    private int unpayTag=0;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_nursing_online_consult_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        titleLayout = findViewById(R.id.title_layout);
        await_pay_cv = findViewById(R.id.await_pay_cv);
        await_pay_cv.setOnClickListener(getController());
        await_diagnose_cv = findViewById(R.id.await_diagnose_cv);
        await_diagnose_cv.setOnClickListener(getController());
        diagnose_cv = findViewById(R.id.diagnose_cv);
        diagnose_cv.setOnClickListener(getController());
        finished_cv = findViewById(R.id.finished_cv);
        finished_cv.setOnClickListener(getController());
        await_evaluation_cv = findViewById(R.id.await_evaluation_cv);
        await_evaluation_cv.setOnClickListener(getController());
        my_nurse_rv = findViewById(R.id.my_nurse_rv);
        choose_depart_tv = findViewById(R.id.choose_depart_tv);
        choose_depart_tv.setOnClickListener(getController());
        choose_area_tv = findViewById(R.id.choose_area_tv);
        choose_area_tv.setOnClickListener(getController());
        my_nurse_rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        myDocAndNurAdapter = new MyDocAndNurAdapter(dataList);
        myDocAndNurAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.e(myDocAndNurAdapter.getData().get(position).getUuid());
                Intent intent = new Intent(NursingOnlineConsultActivity.this, DoctorOrNurseDetailActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("doctorCode", myDocAndNurAdapter.getData().get(position).getUuid());
                startActivity(intent);
            }
        });
        search_tv = findViewById(R.id.search_tv);
        search_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiagnoseSearchActivity.start(getContext(), 1, isOnline ? 1 : 0, 1);
            }
        });
        my_nurse_rv.setAdapter(myDocAndNurAdapter);
        recommend_nurse_rv = findViewById(R.id.recommend_nurse_rv);
        recommendDocAndNurAdapter = new RecommendDocAndNurAdapter(recommendList);
        recommendDocAndNurAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (recommendDocAndNurAdapter.getData().get(position).getIsInquiry() == 0)
                    ToastUtil.shortToast(getContext(), "该护士暂未开通咨询服务");
                else {
                    Logger.e(recommendDocAndNurAdapter.getData().get(position).getUuid());
                    Intent intent = new Intent(NursingOnlineConsultActivity.this, DoctorOrNurseDetailActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("doctorCode", recommendDocAndNurAdapter.getData().get(position).getUuid());
                    startActivity(intent);
                }

            }
        });
        recommend_nurse_rv.setAdapter(recommendDocAndNurAdapter);
        nursing_online_search_tv = findViewById(R.id.nursing_online_search_tv);
        nursing_online_search_tv.setText(App.hospitalName);
        nursing_online_search_tv.setOnClickListener(getController());
        qr_code_layout = findViewById(R.id.qr_code_layout);
        qr_code_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxPermissions rxPermissions = new RxPermissions((FragmentActivity) getContext());
                rxPermissions.request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Intent intent = new Intent(getContext(), CaptureActivity.class);
                            intent.setAction(Intents.Scan.ACTION);
                            intent.putExtra(Intents.Scan.SCAN_FORMATS, "QR_CODE");
                            startActivityForResult(intent, REQUEST_CODE);
                        } else
                            ToastUtil.shortToast(getContext(), "未获取摄像头使用权限，无法使用二维码功能");
                    }
                });
            }
        });
        online_doctor_sw = findViewById(R.id.online_doctor_sw);
        hospitalListFromService.addAll(Global.getHospitalList());
        hospitalList.addAll(Global.getHospitalList());
        selectHospitalId=App.hospitalId;
        selectHospitalName=App.hospitalName;
        chooseHospitalAdapter = new ChooseHospitalAdapter(getContext(), hospitalList, new GeneralCallback.SelectHospitalListener() {
            @Override
            public void getSelectedHospital(HospitalAreaInfo hospitalAreaInfo) {
                Logger.e("getHospitalSuccess-->HospitalId==" + hospitalAreaInfo.getId() + "   HospitalName==" + hospitalAreaInfo.getName());
                selectHospitalId = hospitalAreaInfo.getId();
                selectHospitalName = hospitalAreaInfo.getName();
            }
        });
        online_doctor_sw.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton buttonView, boolean isChecked) {
                isOnline = isChecked;
                if (deptId == -1) {
                    getController().getRecommendNurse(getHospitslRecommendNurseQueryMap(isOnline));
                } else {
                    getController().getRecommendNurse(getDepartmentRecommendNurseQueryMap(isOnline));
                }
            }
        });
        getController().getHomeData(getHomeQueryMap());
        getController().getRecommendNurse(getHospitslRecommendNurseQueryMap(isOnline));
    }

    /**
     * 获取首页请求map
     */
    private Map<String, Object> getHomeQueryMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitalId", App.hospitalId);
        map.put("userId", Global.getUserId());
        map.put("type", 1);
        return map;
    }

    /**
     * 获取推荐医院请求map
     */
    private Map<String, Object> getHospitslRecommendNurseQueryMap(boolean isOnline) {
        Map<String, Object> map = new HashMap<>();
        if (isOnline) {
            map.put("isOnline", 1);

        }
        map.put("isRecommend", 1);
        map.put("hospitalId", App.hospitalId);
        map.put("type", 1);
        return map;
    }

    /**
     * 获取科室请求map
     */
    private Map<String, Object> getDepartmentRecommendNurseQueryMap(boolean isOnline) {
        Map<String, Object> map = new HashMap<>();
        if (isOnline) {
            map.put("isOnline", 1);

        }
        map.put("deptId", deptId);
        map.put("type", 1);
        return map;
    }

    @Override
    public void getHomeDataSuccess(DiagnoseIndexBean data) {
        await_pay_cv.setCount("" + data.getPay());
        await_diagnose_cv.setCount("" + data.getAccept());
        diagnose_cv.setCount("" + data.getProceeding());
        finished_cv.setCount("" + data.getFinish());
        await_evaluation_cv.setCount("" + data.getComment());
        myDocAndNurAdapter.setNewData(data.getAttentionList());
        unpayTag=data.getUnpayTag();
    }

    @Override
    public void getHomeDataFailed(String errMsg) {

    }

    @Override
    public void getRecommendSuccess(List<RecommendDocAndNurBean> recommendList) {
        recommendDocAndNurAdapter.setNewData(recommendList);
    }

    @Override
    public void getRecommendFailed(String errMsg) {

    }

    @Override
    public void getDepartListSuccess(List<DiagnosesAndNurDepart> data) {
        showChooseDepartmentDialog(data);
    }

    @Override
    public void getDepartListFailed(String errMsg) {

    }

    /**
     * 显示选择科室弹框
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
                deptPickerView = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    deptId = departmentList.get(options1).getItems().get(option2).getId();
                    choose_depart_tv.setText(departmentList.get(options1).getItems().get(option2).getName());
                    getController().getRecommendNurse(getDepartmentRecommendNurseQueryMap(isOnline));
                }
            }).build();
            deptPickerView.setPicker(parentDepList, childDepList);
            deptPickerView.show();
        } else {
            ToastUtil.shortToast(getContext(), "没有查询到科室信息");
        }

    }

    @Override
    public void showHospitalPopupWindow() {
        Logger.e("执行打开popupwindow");
        for (int position = 0; position <hospitalList.size() ; position++) {
            hospitalList.get(position).setSelected(false);
            if( hospitalList.get(position).getName().equals(App.hospitalName))
                hospitalList.get(position).setSelected(true);
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
                        if( hospitalList.get(position).getName().equals(App.hospitalName))
                            hospitalList.get(position).setSelected(true);
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
                    for (int position = 0; position <hospitalList.size() ; position++) {
                        hospitalList.get(position).setSelected(false);
                        if( hospitalList.get(position).getName().equals(App.hospitalName))
                            hospitalList.get(position).setSelected(true);
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
                nursing_online_search_tv.setText(selectHospitalName);
                App.hospitalId = selectHospitalId;
                App.hospitalName = selectHospitalName;
                EventBus.getDefault().post(new Event(EventType.UPDATEHOSPITAL, null));
                deptId = -1;
                choose_depart_tv.setText("全部科室");
                choose_area_tv.setText("院区");
                areaId = -1;
                getController().getHomeData(getHomeQueryMap());
                getController().getRecommendNurse(getHospitslRecommendNurseQueryMap(isOnline));
                hospitalPopupWindow.dismiss();
            }
        });
        recyclerView.setAdapter(chooseHospitalAdapter);
        hospitalPopupWindow = new PopupWindow(getContext(), null, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

    /**
     * 显示院区选择弹框
     */
    private void showChooseAreaDialog(List<HospitalAreaInfo> data) {
        if (data != null && data.size() != 0) {
            List<String> areaList = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                areaList.add(data.get(i).getName());
            }
            if (areaPickerView == null)
                areaPickerView = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        areaId = data.get(options1).getId();
                        choose_area_tv.setText(data.get(options1).getName());

                    }
                }).build();
            areaPickerView.setPicker(areaList);
            areaPickerView.show();
        } else {
            ToastUtil.shortToast(getContext(), "该医院没有下属院区");
        }

    }

    @Override
    public void getAreaList(List<HospitalAreaInfo> data) {
        showChooseAreaDialog(data);
    }

    @Override
    public void getAreaListFailed(String msg) {
        ToastUtil.shortToast(getContext(), "接口异常" + msg);
    }

    @Override
    public Map<String, Object> getQueryDeptMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitalId", App.hospitalId);
        if (areaId != -1)
            map.put("hospitalAreaId", areaId);
        return map;
    }
    @Override
    public int getUnpaytag() {
        return unpayTag;
    }
}
