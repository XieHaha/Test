package com.keydom.ih_patient.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.view.GeneralItemView;
import com.keydom.ih_common.view.MyScrollView;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.inspection_report.InspectionReportActivity;
import com.keydom.ih_patient.activity.medical_record.MedicalRecordActivity;
import com.keydom.ih_patient.activity.nursing_order.NursingOrderActivity;
import com.keydom.ih_patient.activity.online_diagnoses_order.OnlineDiagnonsesOrderActivity;
import com.keydom.ih_patient.activity.order_doctor_register.RegistrationRecordActivity;
import com.keydom.ih_patient.activity.prescription_check.PrescriptionListActivity;
import com.keydom.ih_patient.activity.subscribe_examination_order.SubscribeExaminationActivity;
import com.keydom.ih_patient.adapter.ChooseHospitalAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.UserInfo;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.callback.SingleClick;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.fragment.controller.TabMineController;
import com.keydom.ih_patient.fragment.view.TabMineView;
import com.keydom.ih_patient.utils.LocalizationUtils;
import com.keydom.ih_patient.utils.ToastUtil;
import com.keydom.ih_patient.view.MineFunctionItemView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * 个人中心页面
 * @Author：song
 * @Date：18/11/5 下午5:25
 */
public class TabMineFragment extends BaseControllerFragment<TabMineController> implements TabMineView {
    private MineFunctionItemView mineLocationControl, mine_registered_mail_order, mine_inspection_report, mine_booking_order,mine_people_order,mine_payment_records,mine_physician_orders,mine_electronic_prescription;
    private TextView mineFunctionCard,jump_to_login_tv,mine_user_location_tv,search_edt,mine_user_phone_tv,mine_user_name,mine_user_sex_tv,mine_last_clinical_time,location_tv;
    private ImageView mine_setting_img,mine_user_head_img;
    private MyScrollView mineBoxSv;
    private LinearLayout titleBarLayout,qr_code_layout,search_layout,location_layout;
    private RelativeLayout un_login_layout;
    private GeneralItemView mine_item_setting,mine_item_my_message,mine_item_my_chatgroup;

    private SmartRefreshLayout mine_index_refresh;
    private List<HospitalAreaInfo> hospitalListFromService=new ArrayList<>();
    private List<HospitalAreaInfo> hospitalList=new ArrayList<>();
    private ChooseHospitalAdapter chooseHospitalAdapter;
    private Long selectHospitalId;
    private String selectHospitalName;
    private PopupWindow hospitalPopupWindow;
    private String imgStr="";
    //病例记录标识
    public static final String MEDICALRECORD="medical_record";
    //检验检查标识
    public static final String INSPECTIONORDER="inspection_order";
    //电子处方标识
    public static final String ELECTRONICPRESCRIBING="electronic_prescribing";
    //问诊订单标识
    public static final String DIAGNOSEORDER="diagnose_order";
    //护理订单标识
    public static final String NURSINGORDER="nursing_order";
    //体检预约订单标识
    public static final String BOOKINGORDER="booking_order";
    //挂号订单标识
    public static final String REGISTRATIONRECORD="registration_order";
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_tab_mine;
    }

    @Override
    public void onViewCreated(@NotNull View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chooseHospitalAdapter= new ChooseHospitalAdapter(getContext(), hospitalList, new GeneralCallback.SelectHospitalListener() {
            @Override
            public void getSelectedHospital(HospitalAreaInfo hospitalAreaInfo) {
                Logger.e("getHospitalSuccess-->HospitalId=="+hospitalAreaInfo.getId()+"   HospitalName=="+hospitalAreaInfo.getName());
                selectHospitalId=hospitalAreaInfo.getId();
                selectHospitalName=hospitalAreaInfo.getName();
            }
        });
        qr_code_layout=view.findViewById(R.id.qr_code_layout);
        location_tv=view.findViewById(R.id.location_tv);
        qr_code_layout.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if(Global.getUserId()!=-1){
                    EventBus.getDefault().post(new Event(EventType.STARTTOQR, null));
                }else {
                    ToastUtil.shortToast(getContext(),"你未登录,请登录后尝试");
                }
            }
        });
        location_layout=view.findViewById(R.id.location_layout);
        location_layout.setOnClickListener(getController());
        search_layout=view.findViewById(R.id.search_layout);
        search_layout.setOnClickListener(getController());
        mineLocationControl = view.findViewById(R.id.mine_location_control);
        mineBoxSv = view.findViewById(R.id.mine_box_sv);
        un_login_layout=view.findViewById(R.id.un_login_layout);
        jump_to_login_tv=view.findViewById(R.id.jump_to_login_tv);
        jump_to_login_tv.setOnClickListener(getController());
        mine_user_location_tv=view.findViewById(R.id.mine_user_location_tv);
        mine_user_phone_tv=view.findViewById(R.id.mine_user_phone_tv);
        search_edt=view.findViewById(R.id.search_edt);
        search_edt.setOnClickListener(getController());
        mine_user_name=view.findViewById(R.id.mine_user_name);
        mine_user_sex_tv=view.findViewById(R.id.mine_user_sex_tv);
        mine_last_clinical_time=view.findViewById(R.id.mine_last_clinical_time);
        mine_user_head_img=view.findViewById(R.id.mine_user_head_img);
        mine_user_head_img.setOnClickListener(getController());
        mine_item_setting=view.findViewById(R.id.mine_item_setting);
        mine_item_setting.setOnClickListener(getController());
        mineLocationControl=view.findViewById(R.id.mine_location_control);
        mine_electronic_prescription=view.findViewById(R.id.mine_electronic_prescription);
        mine_electronic_prescription.setOnClickListener(getController());
        mineBoxSv=view.findViewById(R.id.mine_box_sv);
        mineLocationControl.setOnClickListener(getController());
        mineFunctionCard = view.findViewById(R.id.mine_function_card);
        mineFunctionCard.setOnClickListener(getController());
        mine_setting_img = view.findViewById(R.id.mine_setting_img);
        mine_setting_img.setOnClickListener(getController());
        mineBoxSv.setOnScrollListener(getController());
        titleBarLayout = (LinearLayout) view.findViewById(R.id.mine_top);
        mine_payment_records=view.findViewById(R.id.mine_payment_records);
        mine_payment_records.setOnClickListener(getController());
        mine_registered_mail_order = view.findViewById(R.id.mine_registered_mail_order);
        mine_registered_mail_order.setOnClickListener(getController());
        mine_inspection_report = view.findViewById(R.id.mine_inspection_report);
        mine_inspection_report.setOnClickListener(getController());
        mine_booking_order = view.findViewById(R.id.mine_booking_order);
        mine_booking_order.setOnClickListener(getController());
        mine_people_order = view.findViewById(R.id.mine_people_control);
        mine_people_order.setOnClickListener(getController());
        mine_physician_orders=view.findViewById(R.id.mine_physician_orders);
        mine_physician_orders.setOnClickListener(getController());
        mine_item_my_message=view.findViewById(R.id.mine_item_my_message);
        mine_item_my_message.setOnClickListener(getController());
        mine_item_my_chatgroup=view.findViewById(R.id.mine_item_my_chatgroup);
        mine_item_my_chatgroup.setOnClickListener(getController());
        view.findViewById(R.id.mine_nursing_orders).setOnClickListener(getController());
        view.findViewById(R.id.mine_case_record).setOnClickListener(getController());
        view.findViewById(R.id.mine_function_doctor).setOnClickListener(getController());
        view.findViewById(R.id.mine_function_nurse).setOnClickListener(getController());
        //此处暂用来跳转会话界面
        view.findViewById(R.id.mine_item_feedback).setOnClickListener(getController());
        mine_index_refresh=getView().findViewById(R.id.mine_index_refresh);
        mine_index_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
        if(Global.getSelectedCityCode()!=null&&!"".equals(Global.getSelectedCityCode())){
            location_tv.setText(Global.getSelectedCityName());
        }else {
            if(Global.getLocationCity()!=null&&!"".equals(Global.getLocationCity())){
                location_tv.setText(Global.getLocationCity());
            }else {
                location_tv.setText("选择城市");
            }
        }
        EventBus.getDefault().register(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        if(Global.getUserId()!=-1)
            getController().getMyUnreadMessageNum();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
            onResume();
    }

    @Override
    public void lazyLoad() {
        hospitalListFromService.clear();
        hospitalList.clear();
        hospitalListFromService.addAll(Global.getHospitalList());

        hospitalList.addAll(Global.getHospitalList());
        chooseHospitalAdapter.notifyDataSetChanged();
        selectHospitalId=App.hospitalId;
        selectHospitalName=App.hospitalName;
        initData();
    }

    /**
     * 登录更新事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateLoginState(Event event){
        if(event.getType()==EventType.UPDATELOGINSTATE){
            initData();
        }
    }

    /**
     * 更新用户数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserInfo(Event event){
        if(event.getType()==EventType.UPDATEUSERINFO){
            initData();
        }
    }

    /**
     * 更换医院
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateHospitalChange(Event event) {
        if (event.getType() == EventType.UPDATEHOSPITAL) {
            search_edt.setText(App.hospitalName);
        }
    }

    /**
     * 更换城市
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCityChange(Event event) {
        if (event.getType() == EventType.UPDATECITY) {
            location_tv.setText(Global.getSelectedCityName());
            lazyLoad();
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

    /**
     * 跳转到相应订单页面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void turnToRecord(Event event) {
        if (event.getType() == EventType.TURNTORECORD) {
            String recordType= (String) event.getData();
            switch (recordType){
                case MEDICALRECORD:
                    ActivityUtils.startActivity(MedicalRecordActivity.class);
                    break;
                case INSPECTIONORDER:
                    InspectionReportActivity.start(getContext());
                    break;
                case ELECTRONICPRESCRIBING:
                    ActivityUtils.startActivity(PrescriptionListActivity.class);
                    break;
                case NURSINGORDER:
                    ActivityUtils.startActivity(NursingOrderActivity.class);
                    break;
                case DIAGNOSEORDER:
                    OnlineDiagnonsesOrderActivity.start(getContext(),OnlineDiagnonsesOrderActivity.WAITEDIAGNOSES);
                    break;
                case BOOKINGORDER:
                    SubscribeExaminationActivity.start(getContext());
                    break;
                case REGISTRATIONRECORD:
                    RegistrationRecordActivity.start(getContext());
                    break;
            }
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(getContext(), "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        } else {
            if(Global.getUserId()!=-1){
                UserInfo userInfo= (UserInfo) LocalizationUtils.readFileFromLocal(getContext(),"userInfo");
                if(userInfo!=null){
                    un_login_layout.setVisibility(View.GONE);
                    if(userInfo.getUserImage()!=null&&!"".equals(userInfo.getUserImage())){
                        mine_user_head_img.setPadding(0,0,0,0);
                        Glide.with(getContext()).load(Const.IMAGE_HOST+userInfo.getUserImage()).into(mine_user_head_img);
                        imgStr=Const.IMAGE_HOST+userInfo.getUserImage();
                    }
                    mine_user_location_tv.setText(userInfo.getAddress()!=null&&!"".equals(userInfo.getAddress())?userInfo.getAddress():"");
                    if(userInfo.getProvinceName()!=null&&!"".equals(userInfo.getProvinceName())){
                       if (userInfo.getCityName()!=null&&!"".equals(userInfo.getCityName())){
                           mine_user_location_tv.setText(userInfo.getProvinceName()+" "+userInfo.getCityName());
                        }else {
                           mine_user_location_tv.setText(userInfo.getProvinceName());
                       }
                    }else {
                        mine_user_location_tv.setText("还未完善地址信息");
                    }
                    mine_user_location_tv.setTextColor(Color.parseColor("#333333"));
                    mine_user_phone_tv.setText(userInfo.getPhoneNumber()!=null&&!"".equals(userInfo.getPhoneNumber())?userInfo.getPhoneNumber():"");
                    mine_user_phone_tv.setTextColor(Color.parseColor("#333333"));
                    mine_user_name.setText(userInfo.getUserName()!=null&&!"".equals(userInfo.getUserName())?userInfo.getUserName():"");
                    if(userInfo.getSex()!=null&&!"".equals(userInfo.getSex())){
                        mine_user_sex_tv.setText("0".equals(userInfo.getSex())?"男":"女");
                    }else
                        mine_user_sex_tv.setText("");
                    mine_last_clinical_time.setText(userInfo.getConsultTime()!=null?"最近就诊时间："+userInfo.getConsultTime():"最近就诊时间：");
                }else {
                    un_login_layout.setVisibility(View.VISIBLE);
                    mine_user_head_img.setPadding((int) getContext().getResources().getDimension(R.dimen.dp_21),(int) getContext().getResources().getDimension(R.dimen.dp_21),(int) getContext().getResources().getDimension(R.dimen.dp_21),(int) getContext().getResources().getDimension(R.dimen.dp_21));
                    Glide.with(getContext()).load(R.mipmap.unlogin_uer_head).into(mine_user_head_img);
                    mine_user_location_tv.setText("暂无法获得信息");
                    mine_user_location_tv.setTextColor(Color.parseColor("#BBBBBB"));
                    mine_user_phone_tv.setTextColor(Color.parseColor("#BBBBBB"));
                    mine_user_phone_tv.setText("暂无法获得信息");
                }
            }else {
                un_login_layout.setVisibility(View.VISIBLE);
                mine_user_head_img.setPadding((int) getContext().getResources().getDimension(R.dimen.dp_21),(int) getContext().getResources().getDimension(R.dimen.dp_21),(int) getContext().getResources().getDimension(R.dimen.dp_21),(int) getContext().getResources().getDimension(R.dimen.dp_21));
                Glide.with(getContext()).load(R.mipmap.unlogin_uer_head).into(mine_user_head_img);
                mine_user_location_tv.setText("暂无法获得信息");
                mine_user_location_tv.setTextColor(Color.parseColor("#BBBBBB"));
                mine_user_phone_tv.setTextColor(Color.parseColor("#BBBBBB"));
                mine_user_phone_tv.setText("暂无法获得信息");
            }

        }
        mine_index_refresh.finishRefresh();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public LinearLayout getTitleLayout() {
        return titleBarLayout;
    }
    @Override
    public void showHospitalPopupWindow() {
        Logger.e("执行打开popupwindow");
        for (int i = 0; i <hospitalList.size() ; i++) {
            hospitalList.get(i).setSelected(false);
            if( hospitalList.get(i).getName().equals(App.hospitalName))
                hospitalList.get(i).setSelected(true);
        }
        chooseHospitalAdapter.notifyDataSetChanged();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.index_choose_hospital_popup_layout,titleBarLayout,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.hospital_list_rv);
        TextView cancelTv=view.findViewById(R.id.cancel_tv);
        View backgroudView=view.findViewById(R.id.backgroud_view);
        EditText hospitalSearchEdt=view.findViewById(R.id.hospital_search_edt);
        hospitalSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
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
        TextView hospitalSearchTv=view.findViewById(R.id.hospital_search_tv);
        hospitalSearchTv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                if(hospitalSearchEdt.getText().toString().trim().equals("")){
                }else {
                    hospitalList.clear();
                    for (HospitalAreaInfo info:hospitalListFromService) {
                        if(info.getName().contains(hospitalSearchEdt.getText().toString().trim())){
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
        TextView commitTv=view.findViewById(R.id.commit_tv);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                App.hospitalId=selectHospitalId;
                App.hospitalName=selectHospitalName;
                EventBus.getDefault().post(new Event(EventType.UPDATEHOSPITAL,null));
                hospitalPopupWindow.dismiss();
            }
        });
        recyclerView.setAdapter(chooseHospitalAdapter);
        hospitalPopupWindow = new PopupWindow(getContext(), null,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hospitalPopupWindow.setContentView(view);
        hospitalPopupWindow.setFocusable(true);
        hospitalPopupWindow.setWidth(titleBarLayout.getWidth());
        hospitalPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                backgroudView.setVisibility(View.INVISIBLE);
                hospitalList.clear();
                hospitalList.addAll(hospitalListFromService);
            }
        });

        hospitalPopupWindow.showAsDropDown(titleBarLayout);
        backgroudView.setVisibility(View.VISIBLE);
    }

    @Override
    public void getUnreadMessagaCountSuccess(Integer count) {
        if(count>0)
            mine_item_my_message.setRedpointVisiable(true);
        else
            mine_item_my_message.setRedpointVisiable(false);
    }

    @Override
    public void getUnreadMessageCountFailed(String errMsg) {
        ToastUtil.shortToast(getContext(),"拉取未读信息失败");
    }

    @Override
    public String getImgStr() {
        return imgStr;
    }
}
