package com.keydom.ih_patient.activity.pregnancy.controller;

import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.keydom.ih_common.base.ControllerImpl;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.pregnancy.view.PregnancyDetailView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PregnancyDetailController extends ControllerImpl<PregnancyDetailView> implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pregnancy_order_date_root_rl:
                showDateDialog(getView().getSelectedDate());
                break;
        }
    }



    private void showDateDialog(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            if (dateStr != null) {
                date = formatter.parse(dateStr);
            } else
                date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    getView().setOrderDate(date);
                }
            }).setDate(calendar).build();
            pvTime.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
