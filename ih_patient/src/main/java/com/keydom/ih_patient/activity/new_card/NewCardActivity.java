package com.keydom.ih_patient.activity.new_card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.PhoneUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.new_card.controller.NewCardController;
import com.keydom.ih_patient.activity.new_card.view.NewCardView;
import com.keydom.ih_patient.bean.IdCardBean;
import com.keydom.ih_patient.bean.IdCardInfo;
import com.keydom.ih_patient.bean.PackageData;
import com.keydom.ih_patient.bean.event.CertificateSuccess;
import com.keydom.ih_patient.constant.Const;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加新卡页面
 */
public class NewCardActivity extends BaseControllerActivity<NewCardController> implements NewCardView {
    private LinearLayout new_card_audit_layout;
    private ScrollView new_card_operate_layout;
    private LinearLayout other_certificate_address_now_layout, other_certificate_address_detail_layout, id_card_region_layout, id_card_address_detail_layout;
    private LinearLayout contactor_name_layout, contactor_phone_layout, contactor_relationship_layout, id_card_validity_period_layout;
    private TextView certificate_type_tv, user_birth_tv, id_card_validity_period_tv;
    private String type, birthDateStr, idCardValidityPeriodDateStr;
    private String idCardValidityStartDateStr;
    private Date birthDate, idCardValidityPeriodDate;
    private TextView new_card_commit, user_sex_tv, user_national_tv, other_certificate_address_now_tv, id_card_region_tv;
    private TextView mCancelCommitTv;
    private InterceptorEditText userName_edt, certificate_id_edt;
    private InterceptorEditText other_certificate_address_detail_edt, di_card_address_detail_edt, contactor_name_edt, contactor_phone_edt, contactor_relationship_edt;
    private ArrayList<String> cardUrlList;
    private String sexStr = "", nationStr = "", regionStr = "";
    private String provinceName, cityName, areaName;
    private String nation, birthTime, validityPeriod;
    private IdCardBean result;
    private boolean isOnlyIdCard;

    /**
     * 启动， 为他人办卡
     */
    public static void start(Context context, String type, List<String> urlList, IdCardBean result) {
        Intent intent = new Intent(context, NewCardActivity.class);
        intent.putExtra(Const.CERTIFICATE_TYPE, type);
        intent.putStringArrayListExtra("urlList", (ArrayList<String>) urlList);
        Bundle bundle = new Bundle();
        bundle.putSerializable("result", result);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 启动 ， 实名认证
     */
    public static void start(Context context, String type, List<String> urlList, IdCardBean result,boolean isOnlyIdCard) {
        Intent intent = new Intent(context, NewCardActivity.class);
        intent.putExtra(Const.CERTIFICATE_TYPE, type);
        intent.putExtra("isOnlyIdCard", isOnlyIdCard);
        intent.putStringArrayListExtra("urlList", (ArrayList<String>) urlList);
        Bundle bundle = new Bundle();
        bundle.putSerializable("result", result);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    /**
     * 启动 ， 为本人办卡
     */
    public static void start(Context context, String type) {
        Intent intent = new Intent(context, NewCardActivity.class);
        intent.putExtra(Const.CERTIFICATE_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_new_card_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getIntent().getStringExtra(Const.CERTIFICATE_TYPE);
        cardUrlList = getIntent().getStringArrayListExtra("urlList");
        isOnlyIdCard = getIntent().getBooleanExtra("isOnlyIdCard",false);

        if(App.userInfo.isCertification() && isOnlyIdCard){
            setTitle("实名认证");
        }else{
            setTitle("新增就诊卡");
        }
        getController().setOnlyIdCard(isOnlyIdCard);
        getController().setType(type);
        new_card_operate_layout = this.findViewById(R.id.new_card_operate_layout);
        new_card_audit_layout = this.findViewById(R.id.new_card_audit_layout);
        certificate_type_tv = this.findViewById(R.id.certificate_type_tv);
        mCancelCommitTv = this.findViewById(R.id.new_card_layout_cancel_commit_tv);
        mCancelCommitTv.setOnClickListener(getController());

        other_certificate_address_now_layout = this.findViewById(R.id.other_certificate_address_now_layout);
        other_certificate_address_detail_layout = this.findViewById(R.id.other_certificate_address_detail_layout);
        contactor_name_layout = this.findViewById(R.id.contactor_name_layout);
        contactor_phone_layout = this.findViewById(R.id.contactor_phone_layout);
        contactor_relationship_layout = this.findViewById(R.id.contactor_relationship_layout);

        id_card_region_layout = this.findViewById(R.id.id_card_region_layout);
        id_card_address_detail_layout = this.findViewById(R.id.id_card_address_detail_layout);
        id_card_validity_period_layout = this.findViewById(R.id.id_card_validity_period_layout);

        new_card_commit = this.findViewById(R.id.new_card_commit);
        new_card_commit.setOnClickListener(getController());



        userName_edt = this.findViewById(R.id.userName_edt);
        user_sex_tv = this.findViewById(R.id.user_sex_tv);
        user_sex_tv.setOnClickListener(getController());
        user_national_tv = this.findViewById(R.id.user_national_tv);
        user_national_tv.setOnClickListener(getController());
        user_birth_tv = this.findViewById(R.id.user_birth_tv);
        user_birth_tv.setOnClickListener(getController());
        certificate_id_edt = this.findViewById(R.id.certificate_id_edt);

        other_certificate_address_now_tv = this.findViewById(R.id.other_certificate_address_now_tv);
        other_certificate_address_now_tv.setOnClickListener(getController());
        other_certificate_address_detail_edt = this.findViewById(R.id.other_certificate_address_detail_edt);
        contactor_name_edt = this.findViewById(R.id.contactor_name_edt);
        contactor_phone_edt = this.findViewById(R.id.contactor_phone_edt);
        contactor_relationship_edt = this.findViewById(R.id.contactor_relationship_edt);

        id_card_region_tv = this.findViewById(R.id.id_card_region_tv);
        id_card_region_tv.setOnClickListener(getController());
        di_card_address_detail_edt = this.findViewById(R.id.di_card_address_detail_edt);
        id_card_validity_period_tv = this.findViewById(R.id.id_card_validity_period_tv);
        id_card_validity_period_tv.setOnClickListener(getController());
        if (type.equals(Const.CARD_ID_CARD)) {
            certificate_type_tv.setText("身份证");
            if(isOnlyIdCard){
                contactor_phone_layout.setVisibility(View.GONE);
                contactor_name_layout.setVisibility(View.GONE);
                contactor_relationship_layout.setVisibility(View.GONE);
            }else{
                contactor_phone_layout.setVisibility(View.VISIBLE);
                contactor_name_layout.setVisibility(View.VISIBLE);
                contactor_relationship_layout.setVisibility(View.VISIBLE);
            }
            id_card_region_layout.setVisibility(View.GONE);
            id_card_address_detail_layout.setVisibility(View.VISIBLE);
            id_card_validity_period_layout.setVisibility(View.VISIBLE);
            other_certificate_address_now_layout.setVisibility(View.GONE);
            other_certificate_address_detail_layout.setVisibility(View.GONE);
            result = (IdCardBean) getIntent().getSerializableExtra("result");
            if (result.getName() != null && !"".equals(result.getName()))
                userName_edt.setText(result.getName());
            if (result.getGender() != null && !"".equals(result.getGender())){
                user_sex_tv.setText(result.getGender());
                if("男".equals(result.getGender())){
                    sexStr="0";
                }else {
                    sexStr="1";
                }
            }

            if (result.getEthnic() != null && !"".equals(result.getEthnic())) {
                getController().matchNation(result.getEthnic());
            }
            if (result.getBirthday() != null && !"".equals(result.getBirthday())) {
                birthDateStr=result.getBirthday();
                user_birth_tv.setText(result.getBirthday().substring(0,4)+"-"+result.getBirthday().substring(4,6)+"-"+result.getBirthday().substring(6));
            }
            if (result.getIdNumber() != null && !"".equals(result.getIdNumber()))
                certificate_id_edt.setText(result.getIdNumber());
            if (result.getAddress() != null && !"".equals(result.getAddress()))
                di_card_address_detail_edt.setText(result.getAddress());
            if (result.getExpiryDate() != null && !"".equals(result.getExpiryDate())) {
                idCardValidityPeriodDateStr=result.getExpiryDate();
                id_card_validity_period_tv.setText(result.getExpiryDate().substring(0,4)+"-"+result.getExpiryDate().substring(4,6)+"-"+result.getExpiryDate().substring(6));
            }
            if(result.getSignDate() != null && !"".equals(result.getSignDate())){
                idCardValidityStartDateStr= result.getSignDate();
            }

        }else if(type.equals(Const.CARD_GET_INFO)){
            certificate_type_tv.setText("身份证");
            contactor_phone_layout.setVisibility(View.VISIBLE);
            contactor_name_layout.setVisibility(View.VISIBLE);
            contactor_relationship_layout.setVisibility(View.VISIBLE);
            id_card_region_layout.setVisibility(View.GONE);
            id_card_address_detail_layout.setVisibility(View.VISIBLE);
            id_card_validity_period_layout.setVisibility(View.VISIBLE);
            other_certificate_address_now_layout.setVisibility(View.GONE);
            other_certificate_address_detail_layout.setVisibility(View.GONE);

            getController().getIdCardInfo();
        } else {
            certificate_type_tv.setText("户口簿");
            id_card_region_layout.setVisibility(View.GONE);
            id_card_address_detail_layout.setVisibility(View.GONE);
            id_card_validity_period_layout.setVisibility(View.GONE);
            other_certificate_address_now_layout.setVisibility(View.VISIBLE);
            other_certificate_address_detail_layout.setVisibility(View.VISIBLE);
            contactor_name_layout.setVisibility(View.VISIBLE);
            contactor_phone_layout.setVisibility(View.VISIBLE);
            contactor_relationship_layout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void commitSuccess() {
        if (type.equals(Const.CARD_ID_CARD) && isOnlyIdCard) {
            App.userInfo.setCertification(true);
            EventBus.getDefault().post(new CertificateSuccess());
            finish();
        }else{
            new_card_operate_layout.setVisibility(View.GONE);
            new_card_audit_layout.setVisibility(View.VISIBLE);
            EventBus.getDefault().post(new CertificateSuccess());
        }
    }

    @Override
    public void commitFailed(String msg) {
        ToastUtil.showMessage(getContext(), "提交失败:" + msg);
    }

    @Override
    public Map<String, Object> getParams() {
        if (type.equals(Const.CARD_ID_CARD)) {
            return isOnlyIdCard ? getCertificateParams():sendTypeCardParams();
        } else if(type.equals(Const.CARD_GET_INFO)){
            return sendTypeCardParams();
        } else {
            return sendTypeOtherCerTificateParams();
        }

    }

    @Override
    public Map<String, Object> sendTypeCardParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("registerUserId", Global.getUserId());
        map.put("hospitalId", App.hospitalId);
        map.put("cardImage", cardUrlList.get(0) + "," + cardUrlList.get(1));
        if (userName_edt.getText().toString().trim() != null && !"".equals(userName_edt.getText().toString().trim())) {
            map.put("name", userName_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请输入姓名");
            return null;
        }

        if (!"".equals(sexStr)) {
            map.put("sex", sexStr);
        } else {
            ToastUtil.showMessage(getContext(), "请填入性别");
            return null;
        }
        if (!"".equals(nationStr)) {
            map.put("nation", nationStr);
        } else {
            ToastUtil.showMessage(getContext(), "请输入民族");
            return null;
        }
        if (birthDateStr != null && !"".equals(birthDateStr)) {
            map.put("birthDate", birthDateStr);
        } else {
            ToastUtil.showMessage(getContext(), "请选择出生日期");
            return null;
        }


        if (type.equals(Const.CARD_ID_CARD) || type.equals(Const.CARD_GET_INFO)) {
            map.put("cardType", "card_type_01");
        } else {
            map.put("cardType", "card_type_02");
        }
        if (certificate_id_edt.getText().toString().trim() != null && !"".equals(certificate_id_edt.getText().toString().trim())) {
            if (!RegexUtils.isIDCard15(certificate_id_edt.getText().toString().trim()) && !RegexUtils.isIDCard18(certificate_id_edt.getText().toString().trim())) {
                ToastUtils.showShort("请输入正确的身份证号");
                return null;
            } else
                map.put("cardNumber", certificate_id_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请输入证件号码");
            return null;
        }
        //TODO ： 暂时屏蔽
/*        if (!"".equals(regionStr)) {
            map.put("addressCode", regionStr);
            map.put("idCardAddress", provinceName + "-" + cityName + "-" + areaName);
        } else {
            ToastUtil.showMessage(getContext(), "请输入身份证所在地");
            return null;
        }*/
        if (di_card_address_detail_edt.getText().toString().trim() != null && !"".equals(di_card_address_detail_edt.getText().toString().trim())) {
            map.put("detailAddress", di_card_address_detail_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请输入身份证详细地址");
            return null;
        }
        if (idCardValidityPeriodDateStr != null && !"".equals(idCardValidityPeriodDateStr)) {
            map.put("cardValidEnd", idCardValidityPeriodDateStr);
        } else {
            ToastUtil.showMessage(getContext(), "请选择身份证有效截止日期");
            return null;
        }
        if (DateUtils.compareDate(birthDateStr, idCardValidityPeriodDateStr)) {
            ToastUtil.showMessage(getContext(), "身份证有效截止日期应该晚于出生日期，请核实");
            return null;
        }
        if (contactor_name_edt.getText().toString().trim() != null && !"".equals(contactor_name_edt.getText().toString().trim())) {
            map.put("contact", contactor_name_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请输入联系人姓名");
            return null;
        }
        if (contactor_phone_edt.getText().toString().trim() != null && !"".equals(contactor_phone_edt.getText().toString().trim())) {
            if (PhoneUtils.isMobileEnable(contactor_phone_edt.getText().toString().trim())) {
                map.put("contactPhone", contactor_phone_edt.getText().toString().trim());
            } else {
                ToastUtil.showMessage(getContext(), "请填写正确的手机格式");
                return null;
            }
        } else {
            ToastUtil.showMessage(getContext(), "请输入联系人联系电话");
            return null;
        }
        if (contactor_relationship_edt.getText().toString().trim() != null && !"".equals(contactor_relationship_edt.getText().toString().trim())) {
            map.put("relationship", contactor_relationship_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请输入与患者的关系");
            return null;
        }
        if (DateUtils.compareDateWithNow(birthDateStr)) {
            ToastUtil.showMessage(getContext(), "出生日期填不该晚于当天，请核实");
            return null;
        }
        return map;
    }

    @Override
    public Map<String, Object> getCertificateParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Global.getUserId());
        map.put("cardImage", cardUrlList.get(0));
        map.put("cardImageBack",cardUrlList.get(1));
        if (userName_edt.getText().toString().trim() != null && !"".equals(userName_edt.getText().toString().trim())) {
            map.put("name", userName_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请输入姓名");
            return null;
        }

        if (!"".equals(sexStr)) {
            map.put("sex", sexStr);
        } else {
            ToastUtil.showMessage(getContext(), "请填入性别");
            return null;
        }
        if (!"".equals(nationStr)) {
            map.put("nation", nationStr);
        } else {
            ToastUtil.showMessage(getContext(), "请输入民族");
            return null;
        }
        if (birthDateStr != null && !"".equals(birthDateStr)) {
            map.put("birthDate", birthDateStr);
        } else {
            ToastUtil.showMessage(getContext(), "请选择出生日期");
            return null;
        }


        if (type.equals(Const.CARD_ID_CARD)) {
            map.put("cardType", "card_type_01");
        } else {
            map.put("cardType", "card_type_02");
        }
        if (certificate_id_edt.getText().toString().trim() != null && !"".equals(certificate_id_edt.getText().toString().trim())) {
            if (!RegexUtils.isIDCard15(certificate_id_edt.getText().toString().trim()) && !RegexUtils.isIDCard18(certificate_id_edt.getText().toString().trim())) {
                ToastUtils.showShort("请输入正确的身份证号");
                return null;
            } else
                map.put("cardNumber", certificate_id_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请输入证件号码");
            return null;
        }

        if (di_card_address_detail_edt.getText().toString().trim() != null && !"".equals(di_card_address_detail_edt.getText().toString().trim())) {
            map.put("detailAddress", di_card_address_detail_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请输入身份证详细地址");
            return null;
        }
        if (idCardValidityPeriodDateStr != null && !"".equals(idCardValidityPeriodDateStr)) {
            map.put("cardValidEnd", idCardValidityPeriodDateStr);
        } else {
            ToastUtil.showMessage(getContext(), "请选择身份证有效截止日期");
            return null;
        }

        if (idCardValidityStartDateStr != null && !"".equals(idCardValidityStartDateStr)) {
            map.put("cardValidBegin", idCardValidityStartDateStr);
        }

        if (DateUtils.compareDate(birthDateStr, idCardValidityPeriodDateStr)) {
            ToastUtil.showMessage(getContext(), "身份证有效截止日期应该晚于出生日期，请核实");
            return null;
        }

        if (DateUtils.compareDateWithNow(birthDateStr)) {
            ToastUtil.showMessage(getContext(), "出生日期填不该晚于当天，请核实");
            return null;
        }
        return map;
    }

    @Override
    public Map<String, Object> sendTypeOtherCerTificateParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("registerUserId", Global.getUserId());
        map.put("hospitalId", App.hospitalId);
        map.put("cardImage", cardUrlList.get(0) + "," + cardUrlList.get(1));
        if (userName_edt.getText().toString().trim() != null && !"".equals(userName_edt.getText().toString().trim())) {
            map.put("name", userName_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请输入姓名");
            return null;
        }

        if (!"".equals(sexStr)) {
            map.put("sex", sexStr);
        } else {
            ToastUtil.showMessage(getContext(), "请填入性别");
            return null;
        }
        if (!"".equals(nationStr)) {
            map.put("nation", nationStr);
        } else {
            ToastUtil.showMessage(getContext(), "请输入民族");
            return null;
        }
        if (birthDateStr != null && !"".equals(birthDateStr)) {
            map.put("birthDate", birthDateStr);
        } else {
            ToastUtil.showMessage(getContext(), "请选择出生日期");
            return null;
        }
        if (type.equals(Const.CARD_ID_CARD)) {
            map.put("cardType", "card_type_01");
        } else {
            map.put("cardType", "card_type_02");
        }
        if (certificate_id_edt.getText().toString().trim() != null && !"".equals(certificate_id_edt.getText().toString().trim())) {
            map.put("cardNumber", certificate_id_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请输入证件号码");
            return null;
        }
        if (!"".equals(regionStr)) {
            map.put("addressCode", regionStr);
            map.put("address", provinceName + "-" + cityName + "-" + areaName);
        } else {
            ToastUtil.showMessage(getContext(), "请输入现住址");
            return null;
        }
        if (other_certificate_address_detail_edt.getText().toString().trim() != null && !"".equals(other_certificate_address_detail_edt.getText().toString().trim())) {
            map.put("detailAddress", other_certificate_address_detail_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请输入详细地址");
            return null;
        }
        if (contactor_name_edt.getText().toString().trim() != null && !"".equals(contactor_name_edt.getText().toString().trim())) {
            map.put("contact", contactor_name_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请输入联系人姓名");
            return null;
        }
        if (contactor_phone_edt.getText().toString().trim() != null && !"".equals(contactor_phone_edt.getText().toString().trim())) {
            if (PhoneUtils.isMobileEnable(contactor_phone_edt.getText().toString().trim())) {
                map.put("contactPhone", contactor_phone_edt.getText().toString().trim());
            } else {
                ToastUtil.showMessage(getContext(), "请填写正确的手机格式");
                return null;
            }
        } else {
            ToastUtil.showMessage(getContext(), "请输入联系人联系电话");
            return null;
        }
        if (contactor_relationship_edt.getText().toString().trim() != null && !"".equals(contactor_relationship_edt.getText().toString().trim())) {
            map.put("relationship", contactor_relationship_edt.getText().toString().trim());
        } else {
            ToastUtil.showMessage(getContext(), "请输入与患者的关系");
            return null;
        }
        if (DateUtils.compareDateWithNow(birthDateStr)) {
            ToastUtil.showMessage(getContext(), "出生日期填不该晚于当天，请核实");
            return null;
        }
        return map;
    }

    @Override
    public void setBirthDate(Date date) {
        birthDate = date;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        birthDateStr = formatter.format(birthDate);
        user_birth_tv.setText(birthDateStr);
        user_birth_tv.setTextColor(getResources().getColor(R.color.primary_font_color));
        birthTime = birthDateStr;
    }

    @Override
    public void setIdCardValidityPeriodDate(Date date) {
        idCardValidityPeriodDate = date;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        idCardValidityPeriodDateStr = formatter.format(idCardValidityPeriodDate);
        id_card_validity_period_tv.setText(idCardValidityPeriodDateStr);
        id_card_validity_period_tv.setTextColor(getResources().getColor(R.color.primary_font_color));
        validityPeriod = idCardValidityPeriodDateStr;
    }

    @Override
    public void saveRegion(List<PackageData.ProvinceBean> data, int options1, int option2, int options3) {
        if (data.get(options1).getCity().size() == 0) {
            if (type.equals(Const.CARD_ID_CARD)) {
                id_card_region_tv.setTextColor(getResources().getColor(R.color.primary_font_color));
                id_card_region_tv.setText(data.get(options1).getName());
            } else {
                other_certificate_address_now_tv.setText(data.get(options1).getName());
                other_certificate_address_now_tv.setTextColor(getResources().getColor(R.color.primary_font_color));
            }
            regionStr = data.get(options1).getCode();
            provinceName = data.get(options1).getName();
            cityName = "";
            areaName = "";

        } else if (data.get(options1).getCity().get(option2).getArea().size() == 0) {
            if (type.equals(Const.CARD_ID_CARD)) {
                id_card_region_tv.setTextColor(getResources().getColor(R.color.primary_font_color));
                id_card_region_tv.setText(data.get(options1).getName() + "-" + data.get(options1).getCity().get(option2).getName());
            } else {
                other_certificate_address_now_tv.setText(data.get(options1).getName() + "-" + data.get(options1).getCity().get(option2).getName());
                other_certificate_address_now_tv.setTextColor(getResources().getColor(R.color.primary_font_color));
            }
            regionStr = data.get(options1).getCode() + "-" + data.get(options1).getCity().get(option2).getCode();
            provinceName = data.get(options1).getName();
            cityName = data.get(options1).getCity().get(option2).getName();
            areaName = "";
        } else {

            if (type.equals(Const.CARD_ID_CARD)) {
                id_card_region_tv.setTextColor(getResources().getColor(R.color.primary_font_color));
                id_card_region_tv.setText(data.get(options1).getName() + "-" + data.get(options1).getCity().get(option2).getName() + "-" + data.get(options1).getCity().get(option2).getArea().get(options3).getName());
            } else {
                other_certificate_address_now_tv.setText(data.get(options1).getName() + "-" + data.get(options1).getCity().get(option2).getName() + "-" + data.get(options1).getCity().get(option2).getArea().get(options3).getName());
                other_certificate_address_now_tv.setTextColor(getResources().getColor(R.color.primary_font_color));
            }
            regionStr = data.get(options1).getCode() + "-" + data.get(options1).getCity().get(option2).getCode() + "-" + data.get(options1).getCity().get(option2).getArea().get(options3).getCode();
            provinceName = data.get(options1).getName();
            cityName = data.get(options1).getCity().get(option2).getName();
            areaName = data.get(options1).getCity().get(option2).getArea().get(options3).getName();
        }
    }

    @Override
    public void saveNation(PackageData.NationBean nationBean) {
        nationStr = nationBean.getNotionCode();
        user_national_tv.setText(nationBean.getNationName());
        user_national_tv.setTextColor(getResources().getColor(R.color.primary_font_color));
        nation = nationBean.getNationName();
    }

    @Override
    public void setSex(String sexName, String sexCode) {
        sexStr = sexCode;
        user_sex_tv.setText(sexName);
        user_sex_tv.setTextColor(getResources().getColor(R.color.primary_font_color));
    }

    @Override
    public String getProvinceName() {
        return provinceName;
    }

    @Override
    public String getCityName() {
        return cityName;
    }

    @Override
    public String getAreaName() {
        return areaName;
    }

    @Override
    public String getNation() {
        return nation;
    }

    @Override
    public String getBirthTime() {
        return birthTime;
    }

    @Override
    public String getValidityPeriod() {
        return validityPeriod;
    }

    @Override
    public void matchNation(PackageData.NationBean nationBean) {
        user_national_tv.setText(nationBean.getNationName());
        nationStr=nationBean.getNotionCode();
    }

    @Override
    public void getIdCardSuccess(IdCardInfo idCardInfo) {

        if(null != idCardInfo){

            if(!TextUtils.isEmpty(idCardInfo.getName())){
                userName_edt.setText(idCardInfo.getName());
            }

            if(!TextUtils.isEmpty(idCardInfo.getSex())){
                sexStr = idCardInfo.getSex();
                user_sex_tv.setText("0".equals(sexStr)? "男" : "女");
            }


            if(!TextUtils.isEmpty(idCardInfo.getNation())){
                nationStr = idCardInfo.getNation();
            }

            if(!TextUtils.isEmpty(idCardInfo.getNationName())){
                user_national_tv.setText(idCardInfo.getNationName());
            }

            if(!TextUtils.isEmpty(idCardInfo.getCardNumber())){
                certificate_id_edt.setText(idCardInfo.getCardNumber());
            }

            if(!TextUtils.isEmpty(idCardInfo.getDetailAddress())){
                di_card_address_detail_edt.setText(idCardInfo.getDetailAddress());
            }

            if(!TextUtils.isEmpty(idCardInfo.getBirthDate())){
                birthDateStr = DateUtils.getYMDfromYMDHMSNoFormat(idCardInfo.getBirthDate());
                user_birth_tv.setText(DateUtils.getYMDfromYMDHMS(idCardInfo.getBirthDate()));
            }


            cardUrlList = new ArrayList<>();
            if(!TextUtils.isEmpty(idCardInfo.getCardImage()) && !TextUtils.isEmpty(idCardInfo.getCardImageBack())){
                cardUrlList.add(idCardInfo.getCardImage());
                cardUrlList.add(idCardInfo.getCardImageBack());
            }

            if(!TextUtils.isEmpty(idCardInfo.getCardValidEnd())){
                idCardValidityPeriodDateStr = idCardInfo.getCardValidEnd();
                id_card_validity_period_tv.setText(idCardInfo.getCardValidEnd());
            }

        }

    }

    @Override
    public void getIdCardFailed(String msg) {
        ToastUtil.showMessage(this,msg);
    }

    @Override
    public void finishMySelf() {
        finish();
    }
}
