package com.keydom.ih_patient.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.InterceptorEditText;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.fragment.controller.CardBindController;
import com.keydom.ih_patient.fragment.view.CardBindView;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 绑卡页面
 */
public class CardBindFragment extends BaseControllerFragment<CardBindController> implements CardBindView {
    private TextView bind_card_tv, card_type_label,name_label_tv;
    private InterceptorEditText cardUserNameEdt,cardNumEdt;
    private RadioGroup radioGroup;
    //卡类型 EntityCard 实体卡  ElectronicCard 电子卡
    private int cardType = 1;
    private final int EntityCard=1;
    private final int ElectronicCard=2;
    private String nameStr = "";
    private String eleCardNumber = "";

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_bind_card;
    }

    @Override
    public void onViewCreated(@NotNull View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind_card_tv = view.findViewById(R.id.bind_card_tv);
        card_type_label = view.findViewById(R.id.card_type_label);
        name_label_tv = view.findViewById(R.id.name_label_tv);
        cardUserNameEdt = view.findViewById(R.id.card_user_name_edt);
        cardUserNameEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    name_label_tv.setTextColor(Color.parseColor("#3F98F7"));
                }else {
                    name_label_tv.setTextColor(Color.parseColor("#666666"));

                }
            }
        });
        cardNumEdt = view.findViewById(R.id.card_num_edt);
        cardNumEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    card_type_label.setTextColor(Color.parseColor("#3F98F7"));
                }else {
                    card_type_label.setTextColor(Color.parseColor("#666666"));

                }
            }
        });
        radioGroup = (RadioGroup) view.findViewById(R.id.card_type_rg);
        bind_card_tv.setOnClickListener(getController());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.card_phusical_rb) {
                    cardType = EntityCard;
                    card_type_label.setText("就诊卡号");
                    cardNumEdt.setHint("请输入就诊卡号");
                } else {
                    cardType = ElectronicCard;
                    card_type_label.setText("身份证号");
                    cardNumEdt.setHint("请输入身份证号");
                }
            }
        });
    }


    @Override
    public void bindSuccess() {
        Event event = new Event(EventType.CARDBINDSUCCESS, null);
        EventBus.getDefault().post(event);
    }

    @Override
    public void bindFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), errMsg);
    }

    @Override
    public Map<String, Object> getBindMap() {
        Map<String, Object> map = new HashMap<>();
        nameStr = cardUserNameEdt.getText().toString().trim();
        if (cardType == EntityCard) {
            eleCardNumber = cardNumEdt.getText().toString();
            map.put("eleCardNumber", eleCardNumber);
        } else {
            String idcard = cardNumEdt.getText().toString();
            if (!RegexUtils.isIDCard15(idcard) && !RegexUtils.isIDCard18(idcard)) {
                ToastUtils.showShort("请输入正确的身份证号");
                return null;
            } else
                map.put("idCard", idcard);
        }
        if (nameStr == null || "".equals(nameStr) || cardNumEdt.getText().toString() == null || "".equals(cardNumEdt.getText().toString())) {
            ToastUtil.showMessage(getContext(), "请完善绑卡信息");
            return null;
        }
        map.put("name", nameStr);
        map.put("registerUserId", App.userInfo.getId());
        map.put("hospitalId", App.hospitalId);
        map.put("cardType", String.valueOf(cardType));
        return map;
    }
}
