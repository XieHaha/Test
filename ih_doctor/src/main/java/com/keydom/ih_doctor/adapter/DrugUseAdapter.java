package com.keydom.ih_doctor.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.bean.DrugBean;
import com.keydom.ih_doctor.bean.DrugUseConfigBean;
import com.keydom.ih_doctor.utils.ToastUtil;

import java.text.DecimalFormat;
import java.util.List;

public class DrugUseAdapter extends BaseQuickAdapter<DrugBean, BaseViewHolder> {

    private DecimalFormat df1 = new DecimalFormat("#.#");
    private DrugUseConfigBean configBean;

    public DrugUseAdapter(@Nullable List<DrugBean> data, DrugUseConfigBean bean) {
        super(R.layout.drug_use_item_layout, data);
        this.configBean = bean;
    }

    boolean ischange;

    @Override
    protected void convert(BaseViewHolder helper, DrugBean item) {
        double dose=0;
        if(item.getSingleDose()!=0){
            dose=item.getSingleDose();
        }else {
            if(item.getDosage()!=0){
                dose=item.getDosage();
            }else {
                dose=item.getSingleMaximum();
            }
        }
        ischange = true;
        helper.setText(R.id.medical_name_tv, item.getDrugsName())
                .setText(R.id.medical_desc_tv, item.getSpec())
                .setText(R.id.medical_num_scaler_text_layout, String.valueOf(item.getQuantity()))
                .setText(R.id.get_medical_way_tv, item.getWay())
                .setText(R.id.medical_dosage_scaler_text_layout, df1.format(dose))
                .setText(R.id.dosage_unit_tv, item.getDosageUnit())
                .setText(R.id.eat_medical_rate_tv, String.valueOf(item.getFrequency()))
                .setText(R.id.eat_medical_day_scaler_text_layout, String.valueOf(item.getDays()))
                .setText(R.id.doctor_entrust, item.getDoctorAdvice());
        ischange = false;
        item.setSingleDose(Double.parseDouble(df1.format(dose)));
        EditText doctor_entrust = helper.itemView.findViewById(R.id.doctor_entrust);
        TextView eat_medical_rate_tv = helper.itemView.findViewById(R.id.eat_medical_rate_tv);
        TextView dosage_unit_tv = helper.itemView.findViewById(R.id.dosage_unit_tv);
        TextView get_medical_way_tv = helper.itemView.findViewById(R.id.get_medical_way_tv);
        RelativeLayout medical_num_scaler_minus_layout = helper.itemView.findViewById(R.id.medical_num_scaler_minus_layout);
        RelativeLayout medical_num_scaler_add_layout = helper.itemView.findViewById(R.id.medical_num_scaler_add_layout);
        RelativeLayout medical_dosage_scaler_minus_layout = helper.itemView.findViewById(R.id.medical_dosage_scaler_minus_layout);
        RelativeLayout medical_dosage_scaler_add_layout = helper.itemView.findViewById(R.id.medical_dosage_scaler_add_layout);
        RelativeLayout eat_medical_day_scaler_minus_layout = helper.itemView.findViewById(R.id.eat_medical_day_scaler_minus_layout);
        RelativeLayout eat_medical_day_scaler_add_layout = helper.itemView.findViewById(R.id.eat_medical_day_scaler_add_layout);
        doctor_entrust.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!ischange) {
                    item.setDoctorAdvice(s.toString());
                }

            }
        });
        medical_num_scaler_minus_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getQuantity() > 0) {
                    if (!ischange) {
                        item.setQuantity(item.getQuantity() - 1);
                    }
                    notifyDataSetChanged();
                }
            }
        });
        medical_num_scaler_add_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ischange) {
                    item.setQuantity(item.getQuantity() + 1);
                }
                notifyDataSetChanged();
            }
        });

        medical_dosage_scaler_minus_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getSingleDose() > 0.1) {
                    if (!ischange) {
                        item.setSingleDose(Double.parseDouble(df1.format(item.getSingleDose() - 0.1)));
                        item.setQuantity(computeDosage(item.getSingleDose(),item.getTimes(),item.getDays(),item.getRate()));
                    }
                }
                notifyDataSetChanged();
            }
        });

        medical_dosage_scaler_add_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getSingleMaximum() == 0 || Double.parseDouble(df1.format(item.getSingleDose() + 0.1)) <= item.getSingleMaximum()) {
                    if (!ischange) {
                        item.setSingleDose(Double.parseDouble(df1.format(item.getSingleDose() + 0.1)));
                        item.setQuantity(computeDosage(item.getSingleDose(),item.getTimes(),item.getDays(),item.getRate()));
                    }
                    notifyDataSetChanged();
                } else if (Double.parseDouble(df1.format(item.getSingleDose() + 0.1)) > item.getSingleMaximum()) {
                    ToastUtil.shortToast(mContext, "已达到最大单次剂量");
                }
            }
        });

        eat_medical_day_scaler_minus_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getDays() > 0) {
                    if (!ischange) {
                        item.setDays(item.getDays() - 1);
                        item.setQuantity(computeDosage(item.getSingleDose(),item.getTimes(),item.getDays(),item.getRate()));
                    }
                    notifyDataSetChanged();
                }
            }
        });

        eat_medical_day_scaler_add_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getMaximumMedicationDays() == 0 || item.getDays() < item.getMaximumMedicationDays()) {
                    if (!ischange) {
                        item.setDays(item.getDays() + 1);
                        item.setQuantity(computeDosage(item.getSingleDose(),item.getTimes(),item.getDays(),item.getRate()));
                    }
                    notifyDataSetChanged();
                } else if (item.getDays() >= item.getMaximumMedicationDays()) {
                    ToastUtil.shortToast(mContext, "已达到最大用药天数");
                }
            }
        });
        OptionsPickerView frequencyPickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                if (!ischange) {
                    item.setFrequency(configBean.getFrequencyList().get(options1));
                    eat_medical_rate_tv.setText(configBean.getFrequencyList().get(options1));
                }

            }
        }).build();
        frequencyPickerView.setPicker(configBean.getFrequencyList());

        OptionsPickerView unitPickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                if (!ischange) {
                    item.setDosageUnit(configBean.getUnitList().get(options1));
                    dosage_unit_tv.setText(configBean.getUnitList().get(options1));
                }
            }
        }).build();
        unitPickerView.setPicker(configBean.getUnitList());

        OptionsPickerView wayPickerView = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                if (!ischange) {
                    item.setWay(configBean.getWayList().get(options1));
                    get_medical_way_tv.setText(configBean.getWayList().get(options1));
                }
            }
        }).build();
        wayPickerView.setPicker(configBean.getWayList());
       /* eat_medical_rate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequencyPickerView.show();
            }
        });*/

       /* get_medical_way_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wayPickerView.show();
            }
        });*/

       /* dosage_unit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitPickerView.show();
            }
        });*/

    }

    public int computeDosage(double singleDose,float times, int day,float rate){
        int result=new Double(Math.ceil(singleDose*times*day/rate)).intValue();

        return result;
    }

}
