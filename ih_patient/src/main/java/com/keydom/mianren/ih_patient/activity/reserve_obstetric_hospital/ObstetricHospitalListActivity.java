package com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.diagnose_user_manager.ManageUserSelectActivity;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.controller.ObstetricHospitalController;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.fragment.ObstetricHospitalFragment;
import com.keydom.mianren.ih_patient.activity.reserve_obstetric_hospital.view.ObstetricHospitalView;
import com.keydom.mianren.ih_patient.adapter.ViewPagerAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.ManagerUserBean;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.constant.Type;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 产科住院记录
 *
 * @author 顿顿
 */
public class ObstetricHospitalListActivity extends BaseControllerActivity<ObstetricHospitalController> implements ObstetricHospitalView {
    /**
     * 启动
     */
    public static void start(Context context) {
        context.startActivity(new Intent(context, ObstetricHospitalListActivity.class));
    }

    private ObstetricHospitalFragment notHospitalized, hospitalized;

    private ManagerUserBean curUserBean;

    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> list = new ArrayList<>();

    private String idCard;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_registration_record_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setTitle(getString(R.string.txt_obstetric_hospital_reserve));
        idCard = App.userInfo.getIdCard();
        setRightTxt(App.userInfo.getUserName());
        setRightBtnListener(v -> ManageUserSelectActivity.start(this, idCard));

        TabLayout registrationRecordTab = this.findViewById(R.id.registration_record_tab);
        ViewPager registrationRecordVp = this.findViewById(R.id.registration_record_vp);
        //        list.add("未住院");
        //        list.add("已住院");
        FragmentManager fm = getSupportFragmentManager();
        notHospitalized = new ObstetricHospitalFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", Type.NOTHOSPITALIZED);
        bundle.putString(Const.CARD_ID_CARD, idCard);
        notHospitalized.setArguments(bundle);
        fragmentList.add(notHospitalized);

        hospitalized = new ObstetricHospitalFragment();
        Bundle bundle_f = new Bundle();
        bundle_f.putInt("type", Type.HOSPITALIZED);
        bundle_f.putString(Const.CARD_ID_CARD, idCard);
        hospitalized.setArguments(bundle_f);
        //        fragmentList.add(hospitalized);
        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter(fm, fragmentList);
        }
        registrationRecordVp.setAdapter(viewPagerAdapter);
        registrationRecordTab.setupWithViewPager(registrationRecordVp);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVisitPeopleSelect(Event event) {
        if (event.getType() == EventType.SENDPATIENTINFO) {
            curUserBean = (ManagerUserBean) event.getData();
            setRightTxt(curUserBean.getName());
            idCard = curUserBean.getCardId();
            if (notHospitalized != null) {
                notHospitalized.setIdCard(idCard);
            }
            if (hospitalized != null) {
                hospitalized.setIdCard(idCard);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
