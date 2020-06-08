package com.keydom.mianren.ih_doctor.adapter;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
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
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.bean.DrugBean;
import com.keydom.mianren.ih_doctor.bean.DrugUseConfigBean;
import com.keydom.mianren.ih_doctor.bean.FrequencyBean;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DrugUseAdapter extends BaseQuickAdapter<DrugBean, BaseViewHolder> {

    private DecimalFormat df1 = new DecimalFormat("#.#");
    private DrugUseConfigBean configBean;

    public DrugUseAdapter(@Nullable List<DrugBean> data, DrugUseConfigBean bean) {
        super(R.layout.drug_use_item_layout, data);
        this.configBean = bean;
    }

    boolean isChange;

    @Override
    protected void convert(BaseViewHolder helper, DrugBean item) {
        double dose = 0;
        if (item.getSingleDose() != 0) {
            dose = item.getSingleDose();
        } else {
            if (item.getDosage() != null && Double.valueOf(item.getDosage()) != 0) {
                dose = Double.valueOf(item.getDosage());
            } else {
                dose = Double.valueOf(item.getSingleMaximum());
            }
        }
        isChange = true;
        helper.setText(R.id.medical_name_tv, item.getDrugsName())
                .setText(R.id.medical_desc_tv, item.getSpec())
                .setText(R.id.medical_num_scaler_text_layout, String.valueOf(item.getQuantity()))
                .setText(R.id.get_medical_way_tv, item.getWay())
                .setText(R.id.medical_dosage_scaler_text_layout, df1.format(dose))
                .setText(R.id.dosage_unit_tv, item.getDosageUnit())
                .setText(R.id.eat_medical_rate_tv, item.getFrequency())
                .setText(R.id.eat_medical_day_scaler_text_layout, String.valueOf(item.getDays()))
                .setText(R.id.doctor_entrust, item.getDoctorAdvice());
        isChange = false;
        item.setSingleDose(Double.parseDouble(df1.format(dose)));
        EditText doctor_entrust = helper.itemView.findViewById(R.id.doctor_entrust);
        TextView medical_num_scaler_text_layout =
                helper.itemView.findViewById(R.id.medical_num_scaler_text_layout);
        EditText medical_dosage_scaler_text_layout =
                helper.itemView.findViewById(R.id.medical_dosage_scaler_text_layout);
        EditText eat_medical_day_scaler_text_layout =
                helper.itemView.findViewById(R.id.eat_medical_day_scaler_text_layout);
        TextView eat_medical_rate_tv = helper.itemView.findViewById(R.id.eat_medical_rate_tv);
        TextView get_medical_way_tv = helper.itemView.findViewById(R.id.get_medical_way_tv);
        RelativeLayout medical_dosage_scaler_minus_layout =
                helper.itemView.findViewById(R.id.medical_dosage_scaler_minus_layout);
        RelativeLayout medical_dosage_scaler_add_layout =
                helper.itemView.findViewById(R.id.medical_dosage_scaler_add_layout);
        RelativeLayout eat_medical_day_scaler_minus_layout =
                helper.itemView.findViewById(R.id.eat_medical_day_scaler_minus_layout);
        RelativeLayout eat_medical_day_scaler_add_layout =
                helper.itemView.findViewById(R.id.eat_medical_day_scaler_add_layout);
        medical_num_scaler_text_layout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isChange) {
                    if ("".equals(s.toString())) {

                    } else {
                        item.setQuantity(Integer.valueOf(s.toString()));
                       /* s.clear();
                        s.append(Integer.valueOf(s.toString()) + "");*/

                        //                        medical_num_scaler_text_layout.setSelection
                        //                        (String.valueOf(item.getQuantity()).length());
                        //                        notifyDataSetChanged();

                    }

                }

            }
        });
        medical_dosage_scaler_text_layout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isChange) {
                    if ("".equals(s.toString())) {

                    } else {
                        if (s.toString().contains(".")) {
                            if (s.toString().indexOf(".") == s.toString().length() - 1) {

                            } else if (s.toString().indexOf(".") == s.toString().length() - 3) {
                                ToastUtil.showMessage(mContext, "仅支持输入一位小数");
                                String value = s.toString();
                                value = value.substring(0, s.toString().indexOf(".") + 2);

                                s.replace(0, s.length(), value);
                                return;
                                //                                s.replace(0,value.toString()
                                //                                .length(),value,0,value.length());
                                //                                notifyDataSetChanged();


                            } else {
                                if (TextUtils.isEmpty(item.getSingleMaximum())) {
                                    return;
                                }
                                if (Double.parseDouble(item.getSingleMaximum()) > 0 && Double.parseDouble(df1.format(Double.valueOf(s.toString()))) > Double.parseDouble(item.getSingleMaximum())) {
                                    ToastUtil.showMessage(mContext, "输入的值超过最大单次剂量");// + item
                                    // .getSingleMaximum()
                                    s.replace(0, s.length(), item.getSingleMaximum() + "");
                                   /* s.clear();
                                    s.append(item.getSingleMaximum()+"");*/
                                    return;
                                } else {
                                    item.setSingleDose(Double.parseDouble(df1.format(Double.valueOf(s.toString()))));
                                    item.setQuantity(computeDosage(item));

                                }
                            }
                        } else {
                            String s1 = s.toString();
                            if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(item.getSingleMaximum())) {
                                return;
                            }
                            if (Double.parseDouble(item.getSingleMaximum()) > 0 && Double.parseDouble(df1.format(Double.valueOf(s1))) > Double.parseDouble(item.getSingleMaximum())) {
                                ToastUtil.showMessage(mContext, "输入的值超过最大单次剂量");//+ item
                                // .getSingleMaximum()
                                s.replace(0, s.length(), item.getSingleMaximum() + "");
/*
                                s.clear();
                                s.append(item.getSingleMaximum()+"");*/
                            } else {
                                item.setSingleDose(Double.parseDouble(df1.format(Double.valueOf(s.toString()))));
                                item.setQuantity(computeDosage(item));
                                //                                s.replace(0,s.length(),df1
                                //                                .format(Double.valueOf(s
                                //                                .toString())));
/*
                                s.clear();
                                s.append(df1.format(Double.valueOf(s.toString())));*/
                                //                            notifyDataSetChanged();
                                //                                medical_dosage_scaler_text_layout.setSelection(df1.format(Double.valueOf(s.toString())).length());
                            }
                        }

                    }

                }

            }
        });
        eat_medical_day_scaler_text_layout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isChange && !TextUtils.isEmpty(s.toString())) {
                    if (item.getMaximumMedicationDays() != 0 && Integer.valueOf(s.toString()) > item.getMaximumMedicationDays()) {
                        ToastUtil.showMessage(mContext, "输入的值超过最大用药天数");// + item
                        // .getSingleMaximum()
                        s.clear();
                        s.append(item.getMaximumMedicationDays() + "");
                    } else {
                        item.setDays(Integer.valueOf(s.toString()));
                        item.setQuantity(computeDosage(item));
                    }
                }

            }
        });


        doctor_entrust.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isChange) {
                    item.setDoctorAdvice(s.toString());
                }

            }
        });

        medical_dosage_scaler_minus_layout.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (item.getSingleDose() > 0.1) {
                    if (!isChange) {
                        item.setSingleDose(Double.parseDouble(df1.format(item.getSingleDose() - 0.1)));
                        item.setQuantity(computeDosage(item));
                    }
                }
                notifyDataSetChanged();
            }
        });

        medical_dosage_scaler_add_layout.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(item.getSingleMaximum())) {
                    item.setSingleMaximum("0");
                }
                if (Double.valueOf(item.getSingleMaximum()) == 0 || Double.parseDouble(df1.format(item.getSingleDose() + 0.1)) <= Double.valueOf(item.getSingleMaximum())) {
                    if (!isChange) {
                        item.setSingleDose(Double.parseDouble(df1.format(item.getSingleDose() + 0.1)));
                        item.setQuantity(computeDosage(item));
                    }
                    notifyDataSetChanged();
                } else if (Double.parseDouble(df1.format(item.getSingleDose() + 0.1)) > Double.valueOf(item.getSingleMaximum())) {
                    ToastUtil.showMessage(mContext, "已达到最大单次剂量");
                }
            }
        });

        eat_medical_day_scaler_minus_layout.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (item.getDays() > 0) {
                    if (!isChange) {
                        item.setDays(item.getDays() - 1);
                        item.setQuantity(computeDosage(item));
                    }
                    notifyDataSetChanged();
                }
            }
        });

        eat_medical_day_scaler_add_layout.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                if (item.getMaximumMedicationDays() == 0 || item.getDays() < item.getMaximumMedicationDays()) {
                    if (!isChange) {
                        item.setDays(item.getDays() + 1);

                        item.setQuantity(computeDosage(item));
                    }
                    notifyDataSetChanged();
                } else if (item.getDays() >= item.getMaximumMedicationDays()) {
                    ToastUtil.showMessage(mContext, "已达到最大用药天数");
                }
            }
        });
        OptionsPickerView frequencyPickerView = new OptionsPickerBuilder(mContext,
                new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        if (!isChange) {
                            item.setFrequency(configBean.getFrequencyList().get(options1).getRemark());
                            item.setFrequencyEnglish(configBean.getFrequencyList().get(options1).getName());
                            eat_medical_rate_tv.setText(configBean.getFrequencyList().get(options1).getRemark());
                        }

                    }
                }).build();
        ArrayList<String> frequencys = new ArrayList<>();
        for (FrequencyBean bean : configBean.getFrequencyList()) {
            frequencys.add(bean.getName());
        }
        frequencyPickerView.setPicker(frequencys);

        OptionsPickerView unitPickerView = new OptionsPickerBuilder(mContext,
                new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        if (!isChange) {
                            item.setDosageUnit(configBean.getUnitList().get(options1));
                        }
                    }
                }).build();
        unitPickerView.setPicker(configBean.getUnitList());

        OptionsPickerView wayPickerView = new OptionsPickerBuilder(mContext,
                new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        if (!isChange) {
                            item.setWay(configBean.getWayList().get(options1).getCodeValue());
                            item.setWayCode(configBean.getWayList().get(options1).getCode());
                            get_medical_way_tv.setText(configBean.getWayList().get(options1).getCodeValue());
                        }
                    }
                }).build();
        wayPickerView.setPicker(configBean.getWayList());

        eat_medical_rate_tv.setOnClickListener(v -> frequencyPickerView.show());

        get_medical_way_tv.setOnClickListener(v -> wayPickerView.show());
    }

    private int computeDosage(DrugBean item) {
        if (item.getRate() == 0) {
            return item.getQuantity();
        }
        return Double.valueOf(Math.ceil(item.getSingleDose() * item.getTimes() * item.getDays() / item.getRate())).intValue();
    }

}
