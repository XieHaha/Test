package com.keydom.mianren.ih_patient.activity.prescription;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.keydom.ih_common.base.BaseActivity;
import com.keydom.ih_common.net.ApiRequest;
import com.keydom.ih_common.net.exception.ApiException;
import com.keydom.ih_common.net.service.HttpService;
import com.keydom.ih_common.net.subsriber.HttpSubscriber;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.adapter.ChoosePharmacyAdapter;
import com.keydom.mianren.ih_patient.adapter.ChoosePharmacyPupAdapter;
import com.keydom.mianren.ih_patient.bean.Event;
import com.keydom.mianren.ih_patient.bean.PrescriptionDetailBean;
import com.keydom.mianren.ih_patient.bean.PrescriptionDrugBean;
import com.keydom.mianren.ih_patient.bean.entity.LatXyEntity;
import com.keydom.mianren.ih_patient.bean.entity.PharmacyEntity;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.PharmacyBean;
import com.keydom.mianren.ih_patient.bean.entity.pharmacy.PrescriptionItemEntity;
import com.keydom.mianren.ih_patient.constant.EventType;
import com.keydom.mianren.ih_patient.net.PrescriptionService;
import com.keydom.mianren.ih_patient.utils.CommUtil;
import com.keydom.mianren.ih_patient.utils.DataCacheUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.baidu.mapapi.BMapManager.getContext;


/**
 * @author ??????
 */
public class ChoosePharmacyActivity extends BaseActivity {
    private RelativeLayout mReDis;
    private TextView mTvContent;
    private PopupWindow mPopupWindow;
    private ChoosePharmacyPupAdapter mChoosePharmacyPupAdapter;
    private ChoosePharmacyAdapter mChoosePharmacyAdapter;
    String data[] = {"??????", "??????", "??????"};
    private List<String> mDatas;
    private String mValue = "??????";
    private RecyclerView mRecyclerView;

    private PharmacyEntity mPharmacyEntity;
    private String mName = null;
    private String mAddress = null;
    private double latx = 39.9037448095;
    private double laty = 116.3980007172;
    List<PharmacyBean> pharmacyBeans = new ArrayList<>();
    private EditText mEtSearch;
    private double lat;
    private double lng;

    private PharmacyBean mCurrentPharmacyBean;


    /**
     * ??????
     * ??????   ?????????????????????????????????????????????????????????????????????????????????????????????
     */


    String mLatXy = DEFAULT_GPS_LNG + "," + DEFAULT_GPS_LAT;
    /**
     * ??????????????????
     */
    public static final double DEFAULT_GPS_LAT = 30.663919;
    /**
     * ??????????????????
     */
    public static final double DEFAULT_GPS_LNG = 104.071144;

    public static final String ID = "prescriptionId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EventBus.getDefault().register(this);
        List<PrescriptionItemEntity> drugs =
                DataCacheUtil.getInstance().getPrescriptionItemEntity();
        LatXyEntity latXyEntity;
        latXyEntity = DataCacheUtil.getInstance().getlatXy();
        if (latXyEntity != null) {
            lat = latXyEntity.getLat();
            lng = latXyEntity.getLng();
            mLatXy = lng + "," + lat;
        }

        String prescriptionId = getIntent().getStringExtra(ID);
        getPrescriptionDetailDrugs(prescriptionId);
        //todo : ??????????????????
/*        if (!CommUtil.isEmpty(drugs)) {
            Logger.e("?????????=" + mLatXy);
            getHttpFindDrugstores(mLatXy, drugs);
        }*/

        initData();


        //   String aa = getJson("pharmcyData.json", this);
        //    Logger.e("aaa=" + aa);
        //??????????????????????????????JSONobject
        JSONObject jsonObject = new JSONObject();
        //??????JSONObject??????????????????
        //   List<PharmacyEntity> data = JSONObject.parseArray(aa, PharmacyEntity.class);
        //   Logger.e("bbbb=" + data);
        mReDis = findViewById(R.id.re_dis);
        mTvContent = findViewById(R.id.tv_content);
        mTvContent.setText(mValue);
        mEtSearch = findViewById(R.id.search_edt);
        mReDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
        mRecyclerView = findViewById(R.id.recycler_pharmacy);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        //??????RecyclerView ??????
        mRecyclerView.setLayoutManager(layoutmanager);
        mChoosePharmacyAdapter = new ChoosePharmacyAdapter(this);
        mRecyclerView.setAdapter(mChoosePharmacyAdapter);
        mChoosePharmacyAdapter.setOnItemClickListener(new ChoosePharmacyAdapter.OnItemClickListener() {
            @Override
            public void onClick(PharmacyBean pharmacyBean) {
                if (null != pharmacyBean) {
                    String mPharmacyName = pharmacyBean.getDrugstore();
                    String mDetailAddress = pharmacyBean.getDrugstoreAddress();

                    if (!CommUtil.isEmpty(mPharmacyName)) {
                        mName = mPharmacyName;
                    }
                    if (!CommUtil.isEmpty(mDetailAddress)) {
                        mAddress = mDetailAddress;
                    }
                    mCurrentPharmacyBean = pharmacyBean;
                }
            }
        });
        mEtSearch.addTextChangedListener(new MyEditTextChangeListener());


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        //    EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_pharmacy;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("????????????");
        setRightTxt("??????");
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                if (!CommUtil.isEmpty(mName) && !CommUtil.isEmpty(mAddress)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("mName", mName);
                    map.put("mAddress", mAddress);
                    map.put("pharmacy", mCurrentPharmacyBean);
                    EventBus.getDefault().post(new Event(EventType.SELECTPHARMACY, map));
                    finish();
                } else {
                    ToastUtil.showMessage(ChoosePharmacyActivity.this, "???????????????");
                }

            }
        });
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int j = 0; j < data.length; j++) {
            mDatas.add(data[j]);
        }
    }

    public void showPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.index_choose_pharmacy_popup_layout
                , mReDis, false);
        RecyclerView recyclerView = view.findViewById(R.id.list_rv);

        mChoosePharmacyPupAdapter = new ChoosePharmacyPupAdapter(this, mDatas, mValue);
        mChoosePharmacyPupAdapter.setOnItemClickListener(new ChoosePharmacyPupAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                mValue = mDatas.get(pos);
                mTvContent.setText(mValue);
                mPopupWindow.dismiss();
                if (mValue.equals("??????")) {
                    sortMethodDistance(pharmacyBeans);
                    refreshView(sortMethodDistance(pharmacyBeans));
                    Logger.e("mValue=" + mValue);
                    Logger.e("pharmacyBeans=" + pharmacyBeans);
                } else if (mValue.equals("??????")) {
                    sortMethodPrice(pharmacyBeans);
                    refreshView(pharmacyBeans);
                    Logger.e("mValue=" + mValue);
                    Logger.e("pharmacyBeans=" + pharmacyBeans);
                }

            }
        });
        recyclerView.setAdapter(mChoosePharmacyPupAdapter);
        mPopupWindow = new PopupWindow(this, null, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setContentView(view);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setWidth(mReDis.getWidth());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        mPopupWindow.showAsDropDown(mReDis);

    }


    //    public static String getJson(String fileName, Context context) {
    //        //???json?????????????????????
    //        StringBuilder stringBuilder = new StringBuilder();
    //        try {
    //            //??????assets???????????????
    //            AssetManager assetManager = context.getAssets();
    //            //????????????????????????????????????
    //            BufferedReader bf = new BufferedReader(new InputStreamReader(
    //                    assetManager.open(fileName)));
    //            String line;
    //            while ((line = bf.readLine()) != null) {
    //                stringBuilder.append(line);
    //            }
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //        return stringBuilder.toString();
    //    }


    /**
     * ????????????
     */
    public void getPrescriptionDetailDrugs(String prescriptionId) {
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getDetailById(prescriptionId), new HttpSubscriber<PrescriptionDetailBean>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable PrescriptionDetailBean data) {
                if (null != data && !CommUtil.isEmpty(data.getList())) {
                    data.getList().get(0).get(0).setPrescriptionId(prescriptionId);
                    getHttpFindDrugstores(mLatXy, data.getList());
                }

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                ToastUtils.showShort(msg);
                return super.requestError(exception, code, msg);
            }
        });
    }

    /**
     * ??????????????????
     */
    private void getHttpFindDrugstores(String mLatXy, List<List<PrescriptionDrugBean>> drugs) {
        Map<String, Object> map = new HashMap<>();
        map.put("startLonLat", mLatXy);
        map.put("drugs", drugs);
        ApiRequest.INSTANCE.request(HttpService.INSTANCE.createService(PrescriptionService.class).getFindDrugstores("application/json", HttpService.INSTANCE.object2Body(map)), new HttpSubscriber<List<PharmacyBean>>(getContext(), getDisposable(), true, true) {
            @Override
            public void requestComplete(@Nullable List<PharmacyBean> data) {
                if (!CommUtil.isEmpty(data)) {
                    pharmacyBeans = data;
                    refreshView(sortMethodDistance(data));
                }

            }

            @Override
            public boolean requestError(@NotNull ApiException exception, int code,
                                        @NotNull String msg) {
                return super.requestError(exception, code, msg);
            }
        });

    }

    /**
     * ??????Adapter?????????
     */
    public void refreshView(List<PharmacyBean> data) {
        if (!CommUtil.isEmpty(data)) {

            mChoosePharmacyAdapter.setList(data);

        }

    }

    /**
     * ??????????????????????????????
     */
    @SuppressWarnings("unchecked")
    public static List<PharmacyBean> sortMethodDistance(List<PharmacyBean> list) {

        List<PharmacyBean> collect = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            collect =
                    list.stream().sorted(Comparator.comparingLong(PharmacyBean::getDistance)).collect(Collectors.toList());

            return collect;
        } else {
            Collections.sort(list, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    PharmacyBean stu1 = (PharmacyBean) o1;
                    PharmacyBean stu2 = (PharmacyBean) o2;
                    return stu1.getDistance().compareTo(stu2.getDistance());
                }
            });

            return list;
        }

    }

    /**
     * ??????????????????????????????
     */
    @SuppressWarnings("unchecked")
    public static void sortMethodPrice(List list) {
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                PharmacyBean stu1 = (PharmacyBean) o1;
                PharmacyBean stu2 = (PharmacyBean) o2;
                return stu1.getSumFee().compareTo(stu2.getSumFee());
            }
        });
    }

    public class MyEditTextChangeListener implements TextWatcher {
        private String TAG = this.getClass().getName();

        /**
         * ???????????????????????????????????????????????????
         */
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        /**
         * ?????????????????????????????????????????????????????? >>??????????????????
         * ?????????????????????????????? ?????????????????????????????????
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            List<PharmacyBean> pharmacyBeanList = new ArrayList<>();
            if (!CommUtil.isEmpty(pharmacyBeans)) {
                for (int j = 0; j < pharmacyBeans.size(); j++) {
                    if (pharmacyBeans.get(j).getDrugstore().contains(charSequence.toString())) {
                        pharmacyBeanList.add(pharmacyBeans.get(j));
                        Logger.e("pharmacyBeans=" + pharmacyBeans.get(j));
                    }
                }
            }
            Logger.e("pharmacyBeanList=" + pharmacyBeanList);
            Logger.e("charSequence=" + charSequence.toString());

            refreshView(pharmacyBeanList);

        }


        /**
         * ??????????????????????????????,??????????????????????????? ???????????????
         */
        @Override
        public void afterTextChanged(Editable editable) {

        }
    }


}
