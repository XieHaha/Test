package com.keydom.ih_doctor.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.adapter.ChooseHospitalAdapter;
import com.keydom.ih_doctor.bean.HospitalAreaInfo;
import com.keydom.ih_doctor.m_interface.GeneralCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：com.keydom.ih_doctor.utils
 * @Description：描述信息
 * @Author：song
 * @Date：19/1/15 上午11:17
 * 修改人：xusong
 * 修改时间：19/1/15 上午11:17
 */
public class SelectHospitalPopUtil {
    private PopupWindow hospitalPopupWindow;
    private ChooseHospitalAdapter chooseHospitalAdapter;
    private Context context;
    HospitalAreaInfo areaInfo = new HospitalAreaInfo();
    List<HospitalAreaInfo> list = new ArrayList<>();
    List<HospitalAreaInfo> tempList = new ArrayList<>();
    public static SelectHospitalPopUtil instance;

    public static SelectHospitalPopUtil getInstance() {
        if (instance == null) {
            instance = new SelectHospitalPopUtil();
        }
        return instance;
    }


    public SelectHospitalPopUtil initPopWindow(Context context) {
        this.context = context;
        areaInfo.setName(MyApplication.userInfo.getHospitalName());
        areaInfo.setId(MyApplication.userInfo.getHospitalId());
        list.clear();
        tempList.clear();
        list.add(areaInfo);
        tempList.add(areaInfo);
        chooseHospitalAdapter = new ChooseHospitalAdapter(context, list, new GeneralCallback.SelectHospitalListener() {
            @Override
            public void getSelectedHospital(HospitalAreaInfo hospitalAreaInfo) {

            }
        });
        return this;
    }


    public void showHospitalPopupWindow(RelativeLayout parentView) {
        final View mView = LayoutInflater.from(context).inflate(R.layout.index_choose_hospital_popup_layout, parentView, false);
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.hospital_list_rv);
        TextView cancelTv = mView.findViewById(R.id.cancel_tv);
        View backgroudView = mView.findViewById(R.id.backgroud_view);
        EditText hospitalSearchEdt = mView.findViewById(R.id.hospital_search_edt);
        hospitalSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterHospital(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        TextView hospitalSearchTv = mView.findViewById(R.id.hospital_search_tv);
        hospitalSearchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterHospital(hospitalSearchEdt.getText().toString().trim());
            }
        });
        backgroudView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(mView, (Activity) context);
                hospitalPopupWindow.dismiss();
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(mView, (Activity) context);
                hospitalPopupWindow.dismiss();
            }
        });
        TextView commitTv = mView.findViewById(R.id.commit_tv);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(mView, (Activity) context);
                hospitalPopupWindow.dismiss();
            }
        });
        recyclerView.setAdapter(chooseHospitalAdapter);
        hospitalPopupWindow = new PopupWindow(context, null, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hospitalPopupWindow.setContentView(mView);
        hospitalPopupWindow.setFocusable(true);
        hospitalPopupWindow.setWidth(parentView.getWidth());
        hospitalPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                hideSoftKeyboard(mView, (Activity) context);
                backgroudView.setVisibility(View.INVISIBLE);
            }
        });
        int[] location = new int[2];
        parentView.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        hospitalPopupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY, 0, y + parentView.getHeight());
        backgroudView.setVisibility(View.VISIBLE);
    }

    private void filterHospital(String key) {
        list.clear();
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getName() != null && tempList.get(i).getName().contains(key)) {
                list.add(tempList.get(i));
            }

        }
        chooseHospitalAdapter.notifyDataSetChanged();
    }


    private void hideSoftKeyboard(View view, Activity activity) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) (activity).getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
