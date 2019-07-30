package com.keydom.ih_patient.activity.index_main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.index_main.Controller.ChooseCityController;
import com.keydom.ih_patient.activity.index_main.view.ChooseCityView;
import com.keydom.ih_patient.adapter.ChooseCityAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
/**
*@Author: LiuJie
*@Date: 2019/3/4 0004
*@Desc: 切换城市页面
*/
public class ChooseCityActivity extends BaseControllerActivity<ChooseCityController> implements ChooseCityView {

    /**
     * 启动页面
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, ChooseCityActivity.class);//.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(starter);
    }
    private RecyclerView city_rv;
    private ChooseCityAdapter chooseCityAdapter;
    private List<Object> dataList=new ArrayList<>();
    private EditText search_edt;
    private TextView search_tv,now_location_tv;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_city_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("切换城市");
        city_rv=findViewById(R.id.city_rv);
        chooseCityAdapter=new ChooseCityAdapter(dataList,getContext());
        city_rv.setAdapter(chooseCityAdapter);
        search_edt=findViewById(R.id.search_edt);
        search_tv=findViewById(R.id.search_tv);
        now_location_tv=findViewById(R.id.now_location_tv);
        if(Global.getLocationCountry()!=null&&Global.getLocationProvince()!=null&&Global.getLocationCity()!=null){
            now_location_tv.setText(Global.getLocationCountry()+" "+Global.getLocationProvince()+" "+Global.getLocationCity());
            now_location_tv.setOnClickListener(getController());
        }else {
            now_location_tv.setText("定位失败，请从以下城市中选择");
        }

        search_tv.setOnClickListener(getController());
        getController().queryCityListByKeyword("");
    }

    @Override
    public void getCityListSuccess(List<Object> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        chooseCityAdapter.notifyDataSetChanged();
    }

    @Override
    public void getCityListFailed(String errMsg) {

    }

    @Override
    public String getSearchKeyWord() {
        return search_edt.getText().toString().trim();
    }

    @Override
    public void finish() {
        Logger.e("从finish更改城市通知");
        EventBus.getDefault().post(new Event(EventType.UPDATECITY,null));
        super.finish();
    }
}
