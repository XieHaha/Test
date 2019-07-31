package com.keydom.ih_patient.activity.order_hospital_cure.controller;

import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.AgreementActivity;
import com.keydom.ih_patient.activity.common_document.CommonDocumentActivity;
import com.keydom.ih_patient.activity.order_hospital_cure.view.OrderHospitalCureView;
import com.keydom.ih_patient.bean.CommonDocumentBean;
import com.keydom.ih_patient.bean.HospitalCureInfo;
import com.keydom.ih_patient.bean.PackageData;
import com.keydom.ih_patient.callback.OnCheckDialogListener;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.net.OrderService;
import com.keydom.ih_patient.net.UserService;
import com.keydom.ih_patient.utils.SelectDialogUtils;
import com.keydom.ih_patient.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入院服务订单控制器
 */
public class OrderHospitalCureController extends ControllerImpl<OrderHospitalCureView> implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.order_region_tv:
                showOrderRegion();
                break;

            case R.id.order_health_care_region_tv:
                showHealthCareRegion();
                break;
            case R.id.order_health_care_type_tv:
                showHealthType();
                break;

            case R.id.order_settlement_type_tv:
                showSettlementType();
                break;
            case R.id.commit_order_tv:
                applyHospitalCure(getView().getApplyMap());
                break;
            case R.id.unconfirm_tv:
                SelectDialogUtils.createCheckDialog(getContext(), new OnCheckDialogListener() {
                    @Override
                    public void commit(View v, String value) {
                        updateStatus(getView().getAdmissionNumber(),0,value);
                    }
                }).show();
                break;
            case R.id.confirm_tv:
                updateStatus(getView().getAdmissionNumber(),1,null);
                break;
            case R.id.remove_order_tv:
                cancellationApply(getView().getAdmissionNumber());
                break;
            case R.id.cure_attention_tv:
                //AgreementActivity.startBeInHospitalAgreement(getContext());
                CommonDocumentActivity.start(getContext(),CommonDocumentBean.CODE_3);
                break;
        }
    }

    /**
     * 查询医保来源地
     */
    private void showHealthCareRegion() {
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getRegionList(), new HttpSubscriber<List<PackageData.ProvinceBean>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable final List<PackageData.ProvinceBean> data) {
                List<String> options1Items = new ArrayList<>();
                List<List<String>> options2Items = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    options1Items.add( data.get(i).getName());
                    List<String> CityList=new ArrayList<>();
                    CityList.add("");
                    for (int j = 0; j <  data.get(i).getCity().size(); j++) {
                        CityList.add(data.get(i).getCity().get(j).getName());
                    }
                    options2Items.add(CityList);
                }
                hideLoading();
                OptionsPickerView optionsPickerView=new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        String regionName="";
                        String regionCode="";
                        if(data.get(options1).getCity().size()==0){
                            regionName=data.get(options1).getName();
                            regionCode=data.get(options1).getCode();
                        }else{
                            if(options2!=0){
                                regionName=data.get(options1).getName()+"-"+data.get(options1).getCity().get(options2-1).getName();
                                regionCode=data.get(options1).getCode()+"-"+data.get(options1).getCity().get(options2-1).getCode();
                            }else {
                                regionName=data.get(options1).getName();
                                regionCode=data.get(options1).getCode();
                            }

                        }

                        getView().getHealthCareRegion(regionName,regionCode);
                    }
                }).build();
                optionsPickerView.setPicker(options1Items,options2Items);
                optionsPickerView.show();
    }
            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtil.shortToast(getContext(),"获取地区列表失败："+msg+"，请稍后重试");
                return super.requestError(exception, code, msg);
            }
        });
    }
    /**
     * 查询居住地区并打开选择器
     */
    public void showOrderRegion(){
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(UserService.class).getRegionList(), new HttpSubscriber<List<PackageData.ProvinceBean>>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable final List<PackageData.ProvinceBean> data) {

                List<String> options1Items=new ArrayList<>();
                List<List<String>> options2Items=new ArrayList<>();
                List<List<List<String>>> options3Items=new ArrayList<>();

                for (int i = 0; i < data.size(); i++) {
                    options1Items.add( data.get(i).getName());
                    List<String> CityList=new ArrayList<>();
                    List<List<String>> Province_AreaList=new ArrayList<>();
                    for (int j = 0; j <  data.get(i).getCity().size(); j++) {
                        CityList.add(data.get(i).getCity().get(j).getName());
                        List<String> City_AreaList=new ArrayList<>();
                        if(data.get(i).getCity().get(j).getArea().size()==0){
                            City_AreaList.add("");
                        }else {
                            for (int k = 0; k <data.get(i).getCity().get(j).getArea().size() ; k++) {
                                City_AreaList.add(data.get(i).getCity().get(j).getArea().get(k).getName());
                            }
                        }
                        Province_AreaList.add(City_AreaList);

                    }
                    options2Items.add(CityList);
                    options3Items.add(Province_AreaList);
                }
                hideLoading();
                OptionsPickerView optionsPickerView=new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        String regionName="";
                        String regionCode="";
                        if(data.get(options1).getCity().size()==0){
                            regionName=data.get(options1).getName();
                            regionCode=data.get(options1).getCode();
                        }else if(data.get(options1).getCity().get(options2).getArea().size()==0){
                            regionName=data.get(options1).getName()+"-"+data.get(options1).getCity().get(options2).getName();
                            regionCode=data.get(options1).getCode()+"-"+data.get(options1).getCity().get(options2).getCode();
                        }else {
                            regionName=data.get(options1).getName()+"-"+data.get(options1).getCity().get(options2).getName()+"-"+data.get(options1).getCity().get(options2).getArea().get(options3).getName();
                            regionCode=data.get(options1).getCode()+"-"+data.get(options1).getCity().get(options2).getCode()+"-"+data.get(options1).getCity().get(options2).getArea().get(options3).getCode();
                        }

                        getView().getOrderRegion(regionName,regionCode);
                    }
                }).build();
                optionsPickerView.setPicker(options1Items,options2Items,options3Items);
                optionsPickerView.show();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                ToastUtil.shortToast(getContext(),"获取地区列表失败："+msg+"，请稍后重试");
                return super.requestError(exception, code, msg);
            }
        });
    }


    /**
     * 展示健康类型
     */
    private void showHealthType() {
        final List<String> Items = new ArrayList<>();
        Items.add("城镇职工基本医疗保险");
        Items.add("新型农村合作医疗");
        Items.add("城镇居民基本医疗保险");
        final List<Integer> options1Items = new ArrayList<>();
        options1Items.add(0);
        options1Items.add(1);
        options1Items.add(2);
        OptionsPickerView optionsPickerView=new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                getView().getHealthType(options1Items.get(options1));
            }
        }).build();
        optionsPickerView.setPicker(Items);
        optionsPickerView.show();

    }

    /**
     * 显示异地类型
     */
    private void showSettlementType() {
        final List<String> Items = new ArrayList<>();
        Items.add("异地结算");
        Items.add("回属地报销");
        Items.add("现金");
        final List<Integer> options1Items = new ArrayList<>();
        options1Items.add(0);
        options1Items.add(1);
        options1Items.add(2);
        OptionsPickerView optionsPickerView=new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                getView().getSettlementType(options1Items.get(options1));
            }
        }).build();
        optionsPickerView.setPicker(Items);
        optionsPickerView.show();

    }

    /**
     * 显示名称编辑弹框
     */
    private void showNameEditDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        View view=LayoutInflater.from(getContext()).inflate(R.layout.reback_edit_dialog_layout,null,false);
        bottomSheetDialog.setContentView(view);
        final InterceptorEditText nameEdt=view.findViewById(R.id.name_edt);
        nameEdt.setHint("输入不能准时报到理由，医生近期会线下联系你");
        TextView commitTv=view.findViewById(R.id.edt_commit_tv);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus(getView().getAdmissionNumber(),0,nameEdt.getText().toString().trim());
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();

    }

    /**
     * 申请入院
     */
    public void applyHospitalCure(Map<String,Object> map){
        if(map!=null){
            showLoading();
            ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).applyHospitalCure(HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<Object>(getContext(),getDisposable(),false) {
                @Override
                public void requestComplete(@Nullable Object data) {
                    hideLoading();
                    getView().applyHealthCureSuccess();
                }

                @Override
                public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                    hideLoading();
                    getView().applyHealthCureFailed(msg);
                    return super.requestError(exception, code, msg);
                }
            });
        }
    }

    /**
     * 查询入院证详情
     */
    public void queryHospitalCure(String admissionNumber){
        Map<String,Object> map=new HashMap<>();
        map.put("id",Global.getUserId());
        map.put("admissionNumber",admissionNumber);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).queryHospitalCure(map), new HttpSubscriber<HospitalCureInfo>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable HospitalCureInfo data) {
                hideLoading();
                getView().getHealthCureSuccess(data);
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().getHealthCureFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * 更新状态
     */
    public void updateStatus(String admissionNumber,int isPunctual ,String noPunctualReason){
        Map<String,Object> map=new HashMap<>();
        map.put("id",Global.getUserId());
        map.put("admissionNumber",admissionNumber);
        map.put("isPunctual",isPunctual);
        if(noPunctualReason!=null){
            map.put("noPunctualReason",noPunctualReason);
        }
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).updateStatus(map), new HttpSubscriber<Object>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().updateStatusSuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().updateStatusFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }

    /**
     * 取消申请
     */
    public void cancellationApply(String admissionNumber){
        Map<String,Object> map=new HashMap<>();
        map.put("id",Global.getUserId());
        map.put("admissionNumber",admissionNumber);
        showLoading();
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(OrderService.class).cancellationApply(map), new HttpSubscriber<Object>(getContext(),getDisposable(),false) {
            @Override
            public void requestComplete(@Nullable Object data) {
                hideLoading();
                getView().cancellationApplySuccess();
            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code, @NotNull String msg) {
                hideLoading();
                getView().cancellationApplyFailed(msg);
                return super.requestError(exception, code, msg);
            }
        });

    }
}