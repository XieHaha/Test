package com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_common.view.MButton;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.controller.AmniocentesisApplyController;
import com.keydom.mianren.ih_patient.activity.reserve_amniocentesis.view.AmniocentesisApplyView;
import com.keydom.mianren.ih_patient.adapter.AmniocentesisReasonAdapter;
import com.keydom.mianren.ih_patient.bean.AmniocentesisReserveBean;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.keydom.mianren.ih_patient.utils.DateUtils;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author 顿顿
 * @date 20/3/9 16:07
 * @des 羊水穿刺预约申请
 */
public class AmniocentesisApplyFragment extends BaseControllerFragment<AmniocentesisApplyController> implements AmniocentesisApplyView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.amniocentesis_apply_surgery_time_tv)
    TextView amniocentesisApplySurgeryTimeTv;
    @BindView(R.id.amniocentesis_apply_surgery_time_layout)
    LinearLayout amniocentesisApplySurgeryTimeLayout;
    @BindView(R.id.amniocentesis_apply_name_et)
    InterceptorEditText amniocentesisApplyNameEt;
    @BindView(R.id.amniocentesis_apply_id_card_et)
    InterceptorEditText amniocentesisApplyIdCardEt;
    @BindView(R.id.amniocentesis_apply_birth_tv)
    TextView amniocentesisApplyBirthTv;
    @BindView(R.id.amniocentesis_apply_birth_layout)
    LinearLayout amniocentesisApplyBirthLayout;
    @BindView(R.id.amniocentesis_apply_phone_et)
    InterceptorEditText amniocentesisApplyPhoneEt;
    @BindView(R.id.amniocentesis_apply_get_verify_tv)
    MButton amniocentesisApplyGetVerifyTv;
    @BindView(R.id.amniocentesis_apply_verify_code_et)
    InterceptorEditText amniocentesisApplyVerifyCodeEt;
    @BindView(R.id.amniocentesis_apply_last_menstruation_tv)
    TextView amniocentesisApplyLastMenstruationTv;
    @BindView(R.id.amniocentesis_apply_last_menstruation_layout)
    LinearLayout amniocentesisApplyLastMenstruationLayout;
    @BindView(R.id.amniocentesis_apply_due_date_tv)
    TextView amniocentesisApplyDueDateTv;
    @BindView(R.id.amniocentesis_apply_due_date_layout)
    LinearLayout amniocentesisApplyDueDateLayout;
    @BindView(R.id.amniocentesis_apply_family_name_et)
    InterceptorEditText amniocentesisApplyFamilyNameEt;
    @BindView(R.id.amniocentesis_apply_family_phone_et)
    InterceptorEditText amniocentesisApplyFamilyPhoneEt;
    @BindView(R.id.amniocentesis_apply_family_address_et)
    InterceptorEditText amniocentesisApplyFamilyAddressEt;
    @BindView(R.id.amniocentesis_apply_transfer_hospital_et)
    InterceptorEditText amniocentesisApplyTransferHospitalEt;
    @BindView(R.id.amniocentesis_apply_recycler_view)
    RecyclerView amniocentesisApplyRecyclerView;
    @BindView(R.id.amniocentesis_apply_next_tv)
    TextView amniocentesisApplyNextTv;

    private AmniocentesisReasonAdapter reasonAdapter;

    private AmniocentesisReserveBean reserveBean;
    /**
     * 羊水穿刺原因
     */
    private String curSelectReason;
    /**
     * 羊水穿刺原因数据
     */
    private List<String> reasonData;
    private Date applySurgeryDate, lastMenstruationDate;
    /**
     * 28周
     */
    public static final int MAX_WEEKS = 28 * 7;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_amniocentesis_apply;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //        amniocentesisApplySurgeryTimeLayout.setOnClickListener(getController());
        amniocentesisApplyBirthLayout.setOnClickListener(getController());
        amniocentesisApplyLastMenstruationLayout.setOnClickListener(getController());
        amniocentesisApplyDueDateLayout.setOnClickListener(getController());
        amniocentesisApplyGetVerifyTv.setOnClickListener(getController());
        amniocentesisApplyNextTv.setOnClickListener(getController());

        //羊水穿刺原因
        reasonData = Arrays.asList(getResources().getStringArray(R.array.amniocentesisReason));
        reasonAdapter = new AmniocentesisReasonAdapter(reasonData);
        reasonAdapter.setOnItemClickListener(this);
        amniocentesisApplyRecyclerView.setNestedScrollingEnabled(false);
        amniocentesisApplyRecyclerView.setAdapter(reasonAdapter);

        amniocentesisApplyIdCardEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() == 18) {
                    Map<String, String> map = CommUtil.getBirAgeSex(s.toString());
                    if (map != null) {
                        amniocentesisApplyBirthTv.setText(map.get("birthday"));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public AmniocentesisReserveBean getReserveBean() {
        return reserveBean;
    }

    @Override
    public boolean reserveInfoComplete() {
        if (calcDateLimit()) {
            return false;
        }
        //        String surgeryTime = amniocentesisApplySurgeryTimeTv.getText().toString();
        String name = amniocentesisApplyNameEt.getText().toString();
        String idCard = amniocentesisApplyIdCardEt.getText().toString().trim();
        String birthday = amniocentesisApplyBirthTv.getText().toString().trim();
        String smsCode = amniocentesisApplyVerifyCodeEt.getText().toString().trim();
        String telephone = amniocentesisApplyPhoneEt.getText().toString().trim();
        String lastDate = amniocentesisApplyLastMenstruationTv.getText().toString().trim();
        //        String dueDate = amniocentesisApplyDueDateTv.getText().toString().trim();
        String familyName = amniocentesisApplyFamilyNameEt.getText().toString().trim();
        String familyPhone = amniocentesisApplyFamilyPhoneEt.getText().toString().trim();
        String familyAddress = amniocentesisApplyFamilyAddressEt.getText().toString().trim();
        String hospital = amniocentesisApplyTransferHospitalEt.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(idCard) || TextUtils.isEmpty(birthday)
                || TextUtils.isEmpty(telephone) || TextUtils.isEmpty(lastDate)
                || TextUtils.isEmpty(familyName) || TextUtils.isEmpty(familyPhone)
                || TextUtils.isEmpty(familyAddress) || TextUtils.isEmpty(curSelectReason)
                || TextUtils.isEmpty(smsCode)) {
            ToastUtil.showMessage(getContext(), "请完善羊膜腔穿刺手术网上预约申请单");
            return false;
        }

        if (reserveBean == null) {
            reserveBean = new AmniocentesisReserveBean();
        }
        //        reserveBean.setSurgeryTime(surgeryTime);
        reserveBean.setBirthday(birthday);
        reserveBean.setIdCard(idCard);
        reserveBean.setName(name);
        reserveBean.setTelephone(telephone);
        reserveBean.setSmsCode(smsCode);
        //        reserveBean.setExpectedBirthTime(dueDate);
        reserveBean.setEndMensesTime(lastDate);
        reserveBean.setFamilyMemberName(familyName);
        reserveBean.setFamilyMemberPhone(familyPhone);
        reserveBean.setFamilyAddress(familyAddress);
        reserveBean.setReferralHospital(hospital);
        //羊水穿刺原因
        reserveBean.setReason(curSelectReason);
        return true;
    }

    /**
     * 计算申请时间和末次月经时间差（大于28周不能预约）
     */
    private boolean calcDateLimit() {
        if (applySurgeryDate != null && lastMenstruationDate != null) {
            if (DateUtils.dateDifferent(lastMenstruationDate, applySurgeryDate) > MAX_WEEKS) {
                ToastUtil.showMessage(getContext(), "申请时间与末次月经时间超过28周，不能预约！");
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDateSelect(View view, Date date) {
        switch (view.getId()) {
            case R.id.amniocentesis_apply_surgery_time_layout:
                amniocentesisApplySurgeryTimeTv.setText(DateUtils.dateToString(date));
                applySurgeryDate = date;
                calcDateLimit();
                break;
            case R.id.amniocentesis_apply_last_menstruation_layout:
                amniocentesisApplyLastMenstruationTv.setText(DateUtils.dateToString(date));
                lastMenstruationDate = date;
                calcDateLimit();
                break;
            case R.id.amniocentesis_apply_due_date_layout:
                amniocentesisApplyDueDateTv.setText(DateUtils.dateToString(date));
                break;
            case R.id.amniocentesis_apply_birth_layout:
                amniocentesisApplyBirthTv.setText(DateUtils.dateToString(date));
                break;
            default:
                break;
        }
    }

    @Override
    public void getMsgCodeSuccess() {
        ToastUtil.showMessage(getContext(), "验证码已发送，请注意查看");
        amniocentesisApplyGetVerifyTv.startTimer();
    }

    @Override
    public void getMsgCodeFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), errMsg);
    }

    @Override
    public String getPhone() {
        return amniocentesisApplyPhoneEt.getText().toString().trim();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        curSelectReason = reasonData.get(position);
        reasonAdapter.setCurPosition(position);
    }
}
