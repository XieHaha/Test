package com.keydom.mianren.ih_patient.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralItemView;
import com.keydom.ih_common.view.MyScrollView;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.inspection_report.InspectionReportActivity;
import com.keydom.mianren.ih_patient.activity.medical_record.MedicalRecordActivity;
import com.keydom.mianren.ih_patient.activity.nursing_order.NursingOrderActivity;
import com.keydom.mianren.ih_patient.activity.online_diagnoses_order.OnlineDiagnonsesOrderActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.RegistrationRecordActivity;
import com.keydom.mianren.ih_patient.activity.prescription_check.PrescriptionListActivity;
import com.keydom.mianren.ih_patient.activity.subscribe_examination_order.SubscribeExaminationActivity;
import com.keydom.mianren.ih_patient.adapter.ChooseHospitalAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.HospitalAreaInfo;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.bean.UserInfo;
import com.keydom.mianren.ih_patient.callback.GeneralCallback;
import com.keydom.mianren.ih_patient.callback.SingleClick;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.fragment.controller.TabMineController;
import com.keydom.mianren.ih_patient.fragment.view.TabMineView;
import com.keydom.mianren.ih_patient.utils.LocalizationUtils;
import com.keydom.mianren.ih_patient.view.MineFunctionItemView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * 个人中心页面
 *
 * @Author：song
 * @Date：18/11/5 下午5:25
 */
public class TabMineFragment extends BaseControllerFragment<TabMineController> implements TabMineView {
    private MineFunctionItemView mineLocationControl, mineRegisteredMailOrder,
            mineMedicalMailOrder, mineReportOrder, mineBookingOrder, minePeopleOrder,
            minePaymentRecords,
            minePhysicianOrders, mineNursingOrders;
    private MineFunctionItemView itemRegister, itemCheck, itemObstetric, itemPainlessDelivery;
    private RelativeLayout layoutTopBg, layoutBase;
    private LinearLayout layoutMy;
    private RelativeLayout layoutTopBgVip, layoutBaseVip, layoutBase2Vip;
    private TextView mineFunctionCard, jumpToLoginTv, mineUserLocationTv, searchEdt,
            mineUserPhoneTv, mineUserName, mineUserNameVip, mineUserSexTv, mineLastClinicalTime,
            locationTv;
    private TextView tvCurrentVisitName, tvCurrentVisitSex, tvCurrentVisitChange,
            tvCurrentVisitCardNumber;
    private ImageView ivCurrentVisitQr;
    private ImageView mineSettingImg, mineUserHeadImg;
    private ImageView mineSettingImgVip, mineUserHeadImgVip;
    private MyScrollView mineBoxSv;
    private LinearLayout titleBarLayout, qrCodeLayout, searchLayout, locationLayout;
    private RelativeLayout unLoginLayout;
    private GeneralItemView mineItemSetting, mineItemMyMessage, mineItemMyChatGroup;

    private SmartRefreshLayout mineIndexRefresh;
    private List<HospitalAreaInfo> hospitalListFromService = new ArrayList<>();
    private List<HospitalAreaInfo> hospitalList = new ArrayList<>();
    private ChooseHospitalAdapter chooseHospitalAdapter;
    private Long selectHospitalId;
    private String selectHospitalName;
    private PopupWindow hospitalPopupWindow;
    private String imgStr = "";

    /**
     * 首页当前显示的就诊人
     */
    private ManagerUserBean curUserBean;
    //病例记录标识
    public static final String MEDICALRECORD = "medical_record";
    //检验检查标识
    public static final String INSPECTIONORDER = "inspection_order";
    //电子处方标识
    public static final String ELECTRONICPRESCRIBING = "electronic_prescribing";
    //问诊订单标识
    public static final String DIAGNOSEORDER = "diagnose_order";
    //护理订单标识
    public static final String NURSINGORDER = "nursing_order";
    //体检预约订单标识
    public static final String BOOKINGORDER = "booking_order";
    //挂号订单标识
    public static final String REGISTRATIONRECORD = "registration_order";

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_tab_mine;
    }

    @Override
    public void onViewCreated(@NotNull View view,
                              @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        qrCodeLayout = view.findViewById(R.id.qr_code_layout);
        locationTv = view.findViewById(R.id.location_tv);
        qrCodeLayout.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (Global.getUserId() != -1) {
                    EventBus.getDefault().post(new Event(EventType.STARTTOQR, null));
                } else {
                    ToastUtil.showMessage(getContext(), "你未登录,请登录后尝试");
                }
            }
        });

        locationLayout = view.findViewById(R.id.location_layout);
        locationLayout.setOnClickListener(getController());
        searchLayout = view.findViewById(R.id.search_layout);
        searchLayout.setOnClickListener(getController());
        mineLocationControl = view.findViewById(R.id.mine_location_control);
        mineBoxSv = view.findViewById(R.id.mine_box_sv);
        unLoginLayout = view.findViewById(R.id.un_login_layout);
        jumpToLoginTv = view.findViewById(R.id.jump_to_login_tv);
        jumpToLoginTv.setOnClickListener(getController());
        mineUserLocationTv = view.findViewById(R.id.mine_user_location_tv);
        mineUserPhoneTv = view.findViewById(R.id.mine_user_phone_tv);
        searchEdt = view.findViewById(R.id.search_edt);
        searchEdt.setOnClickListener(getController());
        mineUserName = view.findViewById(R.id.tv_user_name);
        mineUserNameVip = view.findViewById(R.id.tv_user_name_vip);
        mineUserSexTv = view.findViewById(R.id.mine_user_sex_tv);
        mineLastClinicalTime = view.findViewById(R.id.mine_last_clinical_time);
        mineUserHeadImg = view.findViewById(R.id.iv_user_header);
        mineUserHeadImg.setOnClickListener(getController());
        mineUserHeadImgVip = view.findViewById(R.id.iv_user_header_vip);
        mineUserHeadImgVip.setOnClickListener(getController());
        mineItemSetting = view.findViewById(R.id.mine_item_setting);
        mineItemSetting.setOnClickListener(getController());
        mineLocationControl = view.findViewById(R.id.mine_location_control);
        mineBoxSv = view.findViewById(R.id.mine_box_sv);
        mineLocationControl.setOnClickListener(getController());
        mineFunctionCard = view.findViewById(R.id.mine_function_card);
        mineFunctionCard.setOnClickListener(getController());
        mineSettingImg = view.findViewById(R.id.iv_setting);
        mineSettingImg.setOnClickListener(getController());
        mineSettingImgVip = view.findViewById(R.id.iv_setting_vip);
        mineSettingImgVip.setOnClickListener(getController());
        mineBoxSv.setOnScrollListener(getController());
        titleBarLayout = view.findViewById(R.id.mine_top);
        minePaymentRecords = view.findViewById(R.id.mine_payment_records);
        minePaymentRecords.setOnClickListener(getController());
        mineRegisteredMailOrder = view.findViewById(R.id.mine_registered_mail_order);
        mineRegisteredMailOrder.setOnClickListener(getController());
        mineMedicalMailOrder = view.findViewById(R.id.mine_medical_mail_order);
        mineMedicalMailOrder.setOnClickListener(getController());
        mineReportOrder = view.findViewById(R.id.mine_report_order);
        mineReportOrder.setOnClickListener(getController());
        mineBookingOrder = view.findViewById(R.id.mine_booking_order);
        mineBookingOrder.setOnClickListener(getController());
        minePeopleOrder = view.findViewById(R.id.mine_people_control);
        minePeopleOrder.setOnClickListener(getController());
        minePhysicianOrders = view.findViewById(R.id.mine_physician_orders);
        minePhysicianOrders.setOnClickListener(getController());
        mineItemMyMessage = view.findViewById(R.id.mine_item_my_message);
        mineItemMyMessage.setOnClickListener(getController());
        mineItemMyChatGroup = view.findViewById(R.id.mine_item_my_chatgroup);
        mineItemMyChatGroup.setOnClickListener(getController());

        layoutTopBg = view.findViewById(R.id.layout_top_bg);
        layoutBase = view.findViewById(R.id.layout_base);
        layoutMy = view.findViewById(R.id.layout_my);
        layoutTopBgVip = view.findViewById(R.id.layout_top_bg_vip);
        layoutBaseVip = view.findViewById(R.id.layout_base_vip);
        layoutBase2Vip = view.findViewById(R.id.layout_base2_vip);

        tvCurrentVisitName = view.findViewById(R.id.mine_current_visit_name_tv);
        tvCurrentVisitSex = view.findViewById(R.id.mine_current_visit_sex_tv);
        tvCurrentVisitCardNumber = view.findViewById(R.id.mine_current_visit_card_number_tv);
        tvCurrentVisitChange = view.findViewById(R.id.mine_current_visit_change);
        ivCurrentVisitQr = view.findViewById(R.id.mine_current_visit_qr_iv);

        itemRegister = view.findViewById(R.id.item_register);
        itemCheck = view.findViewById(R.id.item_check);
        itemObstetric = view.findViewById(R.id.item_obstetric);
        itemPainlessDelivery = view.findViewById(R.id.item_painless_delivery);
        itemRegister.setOnClickListener(getController());
        itemCheck.setOnClickListener(getController());
        itemObstetric.setOnClickListener(getController());
        itemPainlessDelivery.setOnClickListener(getController());
        mineNursingOrders = view.findViewById(R.id.mine_nursing_orders);
        mineNursingOrders.setOnClickListener(getController());
        view.findViewById(R.id.mine_function_doctor).setOnClickListener(getController());
        view.findViewById(R.id.mine_function_nurse).setOnClickListener(getController());
        //此处暂用来跳转会话界面
        view.findViewById(R.id.mine_item_feedback).setOnClickListener(getController());
        mineIndexRefresh = getView().findViewById(R.id.mine_index_refresh);
        mineIndexRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
            }
        });
        if (Global.getSelectedCityCode() != null && !"".equals(Global.getSelectedCityCode())) {
            locationTv.setText(Global.getSelectedCityName());
        } else {
            if (Global.getLocationCity() != null && !"".equals(Global.getLocationCity())) {
                locationTv.setText(Global.getLocationCity());
            } else {
                locationTv.setText("选择城市");
            }
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Global.getUserId() != -1) {
            getController().getMyUnreadMessageNum();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onResume();
        }
    }

    @Override
    public void lazyLoad() {
        hospitalListFromService.clear();
        hospitalList.clear();
        hospitalListFromService.addAll(Global.getHospitalList());

        hospitalList.addAll(Global.getHospitalList());
        chooseHospitalAdapter.notifyDataSetChanged();
        selectHospitalId = App.hospitalId;
        selectHospitalName = App.hospitalName;
        initData();
    }

    /**
     * 登录更新事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateLoginState(Event event) {
        if (event.getType() == EventType.UPDATELOGINSTATE) {
            initData();
        }
    }

    /**
     * 更新用户数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserInfo(Event event) {
        if (event.getType() == EventType.UPDATEUSERINFO) {
            initData();
        }
    }

    /**
     * 更换医院
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateHospitalChange(Event event) {
        if (event.getType() == EventType.UPDATEHOSPITAL) {
            searchEdt.setText(App.hospitalName);
        }
    }

    /**
     * 更换城市
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCityChange(Event event) {
        if (event.getType() == EventType.UPDATECITY) {
            locationTv.setText(Global.getSelectedCityName());
            lazyLoad();
        }
    }

    /**
     * 更换首页显示的就诊人
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserBean(Event event) {
        if (event.getType() == EventType.SENDPATIENTINFO) {
            curUserBean = (ManagerUserBean) event.getData();
            initCurrentUserBean();
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
            String recordType = (String) event.getData();
            switch (recordType) {
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
                    OnlineDiagnonsesOrderActivity.start(getContext(),
                            OnlineDiagnonsesOrderActivity.WAITEDIAGNOSES);
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
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission
                            .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(getContext(), "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        } else {
            UserInfo userInfo = (UserInfo) LocalizationUtils.readFileFromLocal(getContext(),
                    "userInfo");
            if (Global.getUserId() != -1 && userInfo != null) {
                if (userInfo.getMember() != 1) {
                    notLoginUiInit();
                    unLoginLayout.setVisibility(View.GONE);
                    if (userInfo.getUserImage() != null && !"".equals(userInfo.getUserImage())) {
                        mineUserHeadImg.setPadding(0, 0, 0, 0);
                        Glide.with(getContext()).load(Const.IMAGE_HOST + userInfo.getUserImage()).into(mineUserHeadImg);
                        imgStr = Const.IMAGE_HOST + userInfo.getUserImage();
                    }
                    mineUserLocationTv.setText(userInfo.getAddress() != null && !"".equals(userInfo.getAddress()) ? userInfo.getAddress() : "");
                    if (userInfo.getProvinceName() != null && !"".equals(userInfo.getProvinceName())) {
                        if (userInfo.getCityName() != null && !"".equals(userInfo.getCityName())) {
                            mineUserLocationTv.setText(userInfo.getProvinceName() + " " + userInfo.getCityName());
                        } else {
                            mineUserLocationTv.setText(userInfo.getProvinceName());
                        }
                    } else {
                        mineUserLocationTv.setText("还未完善地址信息");
                    }
                    mineUserLocationTv.setTextColor(ContextCompat.getColor(getContext(),
                            R.color.color_333333));
                    mineUserPhoneTv.setText(userInfo.getPhoneNumber() !=
                            null && !"".equals(userInfo.getPhoneNumber()) ?
                            userInfo.getPhoneNumber() : "");
                    mineUserPhoneTv.setTextColor(ContextCompat.getColor(getContext(),
                            R.color.color_333333));
                    mineUserName.setText(userInfo.getUserName() != null && !"".equals(userInfo.getUserName()) ? userInfo.getUserName() : "");
                    if (userInfo.getSex() != null && !"".equals(userInfo.getSex())) {
                        mineUserSexTv.setText("0".equals(userInfo.getSex()) ? "男" : "女");
                    } else {
                        mineUserSexTv.setText("");
                    }
                    mineLastClinicalTime.setText(userInfo.getConsultTime() != null ?
                            "最近就诊时间：" + userInfo.getConsultTime() : "最近就诊时间：");
                } else {
                    vipUiInit();
                    if (userInfo.getUserImage() != null && !"".equals(userInfo.getUserImage())) {
                        mineUserHeadImgVip.setPadding(0, 0, 0, 0);
                        Glide.with(getContext()).load(Const.IMAGE_HOST + userInfo.getUserImage()).into(mineUserHeadImgVip);
                        imgStr = Const.IMAGE_HOST + userInfo.getUserImage();
                    }
                    mineUserNameVip.setText(userInfo.getUserName() != null && !"".equals(userInfo.getUserName()) ? userInfo.getUserName() : "");
                }
            } else {
                notLogin();
            }

        }
        tvCurrentVisitChange.setOnClickListener(getController());
        initCurrentUserBean();
        mineIndexRefresh.finishRefresh();
    }

    /**
     * 当前就诊人
     */
    private void initCurrentUserBean() {
        if (curUserBean != null) {
            Bitmap image = CodeUtils.createImage(String.valueOf(curUserBean.getId()), 400, 400,
                    null);
            ivCurrentVisitQr.setImageBitmap(image);
            tvCurrentVisitName.setText(curUserBean.getName());
            tvCurrentVisitSex.setText(curUserBean.getSex());
            tvCurrentVisitCardNumber.setText("就诊卡号");
        } else {
            Bitmap image = CodeUtils.createImage("111", 400, 400, null);
            ivCurrentVisitQr.setImageBitmap(image);
            tvCurrentVisitName.setText("测试");
            tvCurrentVisitSex.setText("男");
            tvCurrentVisitCardNumber.setText("就诊卡号");
        }
    }


    /**
     * 未登录
     */
    private void notLogin() {
        notLoginUiInit();
        unLoginLayout.setVisibility(View.VISIBLE);
        mineUserHeadImg.setPadding((int) getContext().getResources().getDimension(R.dimen.dp_21),
                (int) getContext().getResources().getDimension(R.dimen.dp_21),
                (int) getContext().getResources().getDimension(R.dimen.dp_21),
                (int) getContext().getResources().getDimension(R.dimen.dp_21));
        Glide.with(getContext()).load(R.mipmap.unlogin_uer_head).into(mineUserHeadImg);
        mineUserLocationTv.setText("暂无法获得信息");
        mineUserLocationTv.setTextColor(Color.parseColor("#BBBBBB"));
        mineUserPhoneTv.setTextColor(Color.parseColor("#BBBBBB"));
        mineUserPhoneTv.setText("暂无法获得信息");
    }

    /**
     * 未登录或非会员 ui处理
     */
    private void notLoginUiInit() {
        //非会员
        layoutTopBg.setVisibility(View.VISIBLE);
        layoutBase.setVisibility(View.VISIBLE);
        layoutMy.setVisibility(View.VISIBLE);
        mineNursingOrders.setVisibility(View.VISIBLE);
        mineRegisteredMailOrder.setVisibility(View.VISIBLE);
        mineItemMyChatGroup.setVisibility(View.VISIBLE);
        mineMedicalMailOrder.setVisibility(View.GONE);
        mineReportOrder.setVisibility(View.GONE);
        layoutTopBgVip.setVisibility(View.GONE);
        layoutBaseVip.setVisibility(View.GONE);
        layoutBase2Vip.setVisibility(View.GONE);
        mineBookingOrder.setIconImg(R.mipmap.mine_booking_order);
    }

    /**
     * 预付费会员ui处理
     */
    private void vipUiInit() {
        //会员
        layoutTopBg.setVisibility(View.GONE);
        layoutBase.setVisibility(View.GONE);
        layoutMy.setVisibility(View.GONE);
        mineNursingOrders.setVisibility(View.GONE);
        mineRegisteredMailOrder.setVisibility(View.GONE);
        mineItemMyChatGroup.setVisibility(View.GONE);
        mineMedicalMailOrder.setVisibility(View.VISIBLE);
        mineReportOrder.setVisibility(View.VISIBLE);
        layoutTopBgVip.setVisibility(View.VISIBLE);
        layoutBaseVip.setVisibility(View.VISIBLE);
        layoutBase2Vip.setVisibility(View.VISIBLE);
        mineBookingOrder.setIconImg(R.mipmap.mine_nursing_orders);
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
        for (int i = 0; i < hospitalList.size(); i++) {
            hospitalList.get(i).setSelected(false);
            if (hospitalList.get(i).getName().equals(App.hospitalName)) {
                hospitalList.get(i).setSelected(true);
            }
        }
        chooseHospitalAdapter.notifyDataSetChanged();
        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.index_choose_hospital_popup_layout, titleBarLayout, false);
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
                    for (int position = 0; position < hospitalList.size(); position++) {
                        hospitalList.get(position).setSelected(false);
                        if (hospitalList.get(position).getName().equals(App.hospitalName)) {
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
                App.hospitalId = selectHospitalId;
                App.hospitalName = selectHospitalName;
                EventBus.getDefault().post(new Event(EventType.UPDATEHOSPITAL, null));
                hospitalPopupWindow.dismiss();
            }
        });
        recyclerView.setAdapter(chooseHospitalAdapter);
        hospitalPopupWindow = new PopupWindow(getContext(), null,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hospitalPopupWindow.setContentView(view);
        hospitalPopupWindow.setFocusable(true);
        hospitalPopupWindow.setWidth(titleBarLayout.getWidth());
        hospitalPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
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
        if (count > 0) {
            mineItemMyMessage.setRedpointVisiable(true);
        } else {
            mineItemMyMessage.setRedpointVisiable(false);
        }
    }

    @Override
    public void getUnreadMessageCountFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), "拉取未读信息失败");
    }

    @Override
    public String getImgStr() {
        return imgStr;
    }

    @Override
    public long getCurUserId() {
        return curUserBean == null ? -1 : curUserBean.getId();
    }
}
