package com.keydom.ih_patient.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keydom.ih_common.activity.ArticleDetailActivity;
import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.bean.MainLoadingEvent;
import com.keydom.ih_common.constant.Const;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.ih_common.view.GeneralDialog;
import com.keydom.ih_patient.App;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.diagnose_main.DiagnoseMainActivity;
import com.keydom.ih_patient.activity.index_main.ChooseCityActivity;
import com.keydom.ih_patient.activity.my_message.NoticeDeatailActivity;
import com.keydom.ih_patient.activity.nurse_main.NurseMainActivity;
import com.keydom.ih_patient.adapter.ChooseHospitalAdapter;
import com.keydom.ih_patient.adapter.IndexFunctionAdapter;
import com.keydom.ih_patient.bean.CityBean;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.HealthKnowledgeBean;
import com.keydom.ih_patient.bean.HospitalAreaInfo;
import com.keydom.ih_patient.bean.IndexData;
import com.keydom.ih_patient.bean.IndexFunction;
import com.keydom.ih_patient.callback.GeneralCallback;
import com.keydom.ih_patient.callback.SingleClick;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.fragment.controller.TabIndexController;
import com.keydom.ih_patient.fragment.view.TabIndexView;
import com.keydom.ih_patient.utils.DepartmentDataHelper;
import com.keydom.ih_patient.view.FunctionRvItemDecoration;
import com.keydom.ih_patient.view.GeneralArticleItem;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页页面
 *
 * @Author：song
 * @Date：18/11/5 下午5:25
 */
public class TabIndexFragment extends BaseControllerFragment<TabIndexController> implements TabIndexView {

    private LayoutInflater mLayountInflater;
    private LinearLayout mMemberRootLl;
    private RelativeLayout mTopRightRootRl;
    private RelativeLayout mTopLeftRootRl;
    private TextView mTopRightTitleTv;
    private XBanner indexFirstBanner, indexSecondBanner, indexNoticeBanner, indexNewArticleBanner;
    private RecyclerView indexFunctionRv;
    private RecyclerView mFirstVIPRv;
    private RecyclerView mSecondVIPRv;
    private IndexFunctionAdapter indexFunctionAdapter;
    private IndexFunctionAdapter mFirstVIPFunctionAdapter;
    private IndexFunctionAdapter mSecondVIPFunctionAdapter;
    private LinearLayout locationLayout, searchLayout, qrLayout, healthZoneLayout, titleLayout;
    private TextView locationTv, new_article_title_tv, new_article_readernum_tv;
    private TextView searchEdt, empty_text, more_tv;
    private List<IndexFunction> datalist = new ArrayList<>();
    private List<IndexFunction> mFirstVIPDatas = new ArrayList<>();
    private List<IndexFunction> mSecondVIPDatas = new ArrayList<>();
    private RelativeLayout topHealthRl, empty_layout;

    private List<HospitalAreaInfo> hospitalListFromService = new ArrayList<>();
    private List<HospitalAreaInfo> hospitalList = new ArrayList<>();
    private PopupWindow hospitalPopupWindow;
    private ChooseHospitalAdapter chooseHospitalAdapter;
    private Long selectHospitalId;
    private String selectHospitalName;
    private SmartRefreshLayout indexRefresh;
    private List<IndexData.NotificationsBean> noticeList = new ArrayList<>();
    private int unFinishInquiry = 0;
    private int unFinishNurse = 0;
    private int clinic = 0;
    private int unFinishAdmission = 0;
    private int unFinishInspect = 0;
    private int page=1;
    private TextView index_footer;

    private LinearLayout mFirstVIPRootLl;
    private LinearLayout mSecondVIPRootLl;
    private LinearLayout mIndexFunctionRootLl;


    public void initVipFunction(){
        mMemberRootLl.setVisibility(View.GONE);
        mIndexFunctionRootLl.setVisibility(View.GONE);
        mFirstVIPRootLl.setVisibility(View.VISIBLE);
        mSecondVIPRootLl.setVisibility(View.VISIBLE);

        mTopRightTitleTv.setText("产检预约");

        mTopRightRootRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mTopLeftRootRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiagnoseMainActivity.start(getContext());
            }
        });
    }

    public void initNormal(){
        mMemberRootLl.setVisibility(View.VISIBLE);
        mIndexFunctionRootLl.setVisibility(View.VISIBLE);
        mFirstVIPRootLl.setVisibility(View.GONE);
        mSecondVIPRootLl.setVisibility(View.GONE);

        mTopRightTitleTv.setText("护理服务");

        mTopRightRootRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NurseMainActivity.start(getContext());
            }
        });
        mTopLeftRootRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiagnoseMainActivity.start(getContext());
            }
        });
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_tab_index;
    }

    @Override
    public void onViewCreated(@NotNull View view, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mFirstVIPRootLl = view.findViewById(R.id.fragment_tab_index_first_vip_root_ll);
        mSecondVIPRootLl = view.findViewById(R.id.fragment_tab_index_second_vip_root_ll);
        mIndexFunctionRootLl = view.findViewById(R.id.fragment_tab_index_function_root_ll);
        indexRefresh = view.findViewById(R.id.index_refresh);
        mMemberRootLl = view.findViewById(R.id.fragment_tab_index_open_vip_ll);
        mMemberRootLl.setOnClickListener(getController());
        titleLayout = view.findViewById(R.id.title_layout);
        locationLayout = view.findViewById(R.id.location_layout);
        searchLayout = view.findViewById(R.id.search_layout);
        qrLayout = view.findViewById(R.id.qr_code_layout);
        locationTv = view.findViewById(R.id.location_tv);
        searchEdt = view.findViewById(R.id.search_edt);
        searchEdt.setOnClickListener(getController());
        indexFirstBanner = view.findViewById(R.id.index_first_banner);
        indexFunctionRv = view.findViewById(R.id.index_function_rv);
        mFirstVIPRv = view.findViewById(R.id.fragment_tab_index_first_vip_rv);
        mSecondVIPRv = view.findViewById(R.id.fragment_tab_index_second_vip_rv);
        new_article_title_tv = view.findViewById(R.id.new_article_title_tv);
        new_article_readernum_tv = view.findViewById(R.id.new_article_readernum_tv);
        indexSecondBanner = view.findViewById(R.id.index_second_banner);
        indexNoticeBanner = view.findViewById(R.id.index_notice_xbanner);
        indexNewArticleBanner = view.findViewById(R.id.new_article_banner);
        healthZoneLayout = view.findViewById(R.id.health_zone_layout);
        topHealthRl = view.findViewById(R.id.top_health_rl);
        empty_layout = view.findViewById(R.id.empty_layout);
        empty_text = view.findViewById(R.id.empty_text);
        more_tv = view.findViewById(R.id.more_tv);
        index_footer=view.findViewById(R.id.index_footer);
        mTopLeftRootRl=view.findViewById(R.id.fragment_tab_index_top_left_rl);
        mTopRightRootRl=view.findViewById(R.id.fragment_tab_index_top_right_rl);
        mTopRightTitleTv=view.findViewById(R.id.fragment_tab_index_top_right_title_tv);


        indexRefresh.setEnableLoadMore(false);
        indexRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page=1;
                getController().fillViewData();
                getController().fillHealthKnowledges(page);

            }
        });
       /* indexRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getController().fillHealthKnowledges(page);
            }
        });*/


        if (Global.getSelectedCityCode() != null && !"".equals(Global.getSelectedCityCode())) {
            locationTv.setText(Global.getSelectedCityName());
        } else {
            if (Global.getLocationCity() != null && !"".equals(Global.getLocationCity())) {
                locationTv.setText(Global.getLocationCity());
            } else {
                locationTv.setText("选择城市");
            }
        }

        more_tv.setOnClickListener(getController());
        chooseHospitalAdapter = new ChooseHospitalAdapter(getContext(), hospitalList, new GeneralCallback.SelectHospitalListener() {
            @Override
            public void getSelectedHospital(HospitalAreaInfo hospitalAreaInfo) {
                Logger.e("getHospitalSuccess-->HospitalId==" + hospitalAreaInfo.getId() + "   HospitalName==" + hospitalAreaInfo.getName());
                selectHospitalId = hospitalAreaInfo.getId();
                selectHospitalName = hospitalAreaInfo.getName();
            }
        });

        index_footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getController().fillHealthKnowledges(page);
            }
        });

        indexFunctionRv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        indexFunctionRv.addItemDecoration(new FunctionRvItemDecoration(70, 30));
        indexFunctionAdapter = new IndexFunctionAdapter(getContext(), datalist);
        indexFunctionRv.setAdapter(indexFunctionAdapter);
        indexFunctionRv.setNestedScrollingEnabled(false);

        mFirstVIPDatas.add(new IndexFunction(2131493053,"产后康复"));
        mFirstVIPDatas.add(new IndexFunction(2131493053,"羊水穿刺预约"));
        mFirstVIPDatas.add(new IndexFunction(2131493053,"孕妇学校"));
        mFirstVIPDatas.add(new IndexFunction(2131493053,"无痛分娩预约"));
        mFirstVIPDatas.add(new IndexFunction(2131493053,"产科住院预约"));
        mFirstVIPDatas.add(new IndexFunction(2131493053,"儿童保健"));

        mSecondVIPDatas.add(new IndexFunction(2131493053,"健康管理"));
        mSecondVIPDatas.add(new IndexFunction(2131493053,"健康档案"));
        mSecondVIPDatas.add(new IndexFunction(2131493053,"病案邮寄"));

        mFirstVIPRv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mFirstVIPRv.addItemDecoration(new FunctionRvItemDecoration(70, 30));
        mFirstVIPFunctionAdapter = new IndexFunctionAdapter(getContext(), mFirstVIPDatas);
        mFirstVIPRv.setAdapter(mFirstVIPFunctionAdapter);
        mFirstVIPRv.setNestedScrollingEnabled(false);


        mSecondVIPRv.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mSecondVIPRv.addItemDecoration(new FunctionRvItemDecoration(70, 30));
        mSecondVIPFunctionAdapter = new IndexFunctionAdapter(getContext(), mSecondVIPDatas);
        mSecondVIPRv.setAdapter(mSecondVIPFunctionAdapter);
        mSecondVIPRv.setNestedScrollingEnabled(false);

        memberLayoutShow();

        bindInteractionEvents();
        EventBus.getDefault().register(this);

        super.onViewCreated(view, savedInstanceState);



    }

    private void memberLayoutShow() {
        if (Global.isMember()) {
            initVipFunction();
        } else {
            initNormal();
        }
    }

    @Override
    public void lazyLoad() {
        if (Global.getSelectedCityCode() != null && !"".equals(Global.getSelectedCityCode())) {
            getController().initHospitalList(Global.getSelectedCityCode());
            locationTv.setText(Global.getSelectedCityName());
        } else {
            if (Global.getLocationCityCode() != null && !"".equals(Global.getLocationCityCode())) {
                Global.setSelectedCityName(Global.getLocationCity());
                Global.setSelectedCityCode(Global.getLocationCityCode());
                locationTv.setText(Global.getLocationCity());
                getController().initHospitalList(Global.getLocationCityCode());
            } else {
                getController().queryCityListByKeyword("");
                /*locationTv.setText("选择城市");
                new GeneralDialog(getContext(), "获取当前位置失败，请选择所在城市", new GeneralDialog.OnCloseListener() {
                    @Override
                    public void onCommit() {
                        ChooseCityActivity.start(getContext());
                    }
                }).setTitle("提示").setCancel(false).setNegativeButtonIsGone(true).setPositiveButton("确认").show();*/
            }

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecieve(Event event) {//List<IndexFunction> dataList
        if (event.getType() == EventType.UPDATEFUCTION) {
            List<IndexFunction> dataList = (List<IndexFunction>) event.getData();
            Log.d("TAG", "收到返回列表数据,列表size为" + dataList.size());
            getController().upDateData(dataList);
        }
    }

    /**
     * 成功登录事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateLoginState(Event event) {
        if (event.getType() == EventType.UPDATELOGINSTATE) {
            getController().fillViewData();
            getController().fillFunction();
            getController().fillHealthKnowledges(page);
            memberLayoutShow();
        }
    }

    /**
     * 更改城市事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCityChange(Event event) {
        if (event.getType() == EventType.UPDATECITY) {
            Logger.e("收到更改城市通知");
            locationTv.setText(Global.getSelectedCityName());
            getController().initHospitalList(Global.getSelectedCityCode());
        }
    }

    /**
     * 医院更新事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateHospitalChange(Event event) {
        if (event.getType() == EventType.UPDATEHOSPITAL) {
            searchEdt.setText(App.hospitalName);
            page=1;
            getController().fillViewData();
            getController().fillFunction();
            getController().fillHealthKnowledges(page);
        }
    }

    /**
     * 绑定事件
     */
    private void bindInteractionEvents() {
        locationLayout.setOnClickListener(getController());
        searchLayout.setOnClickListener(getController());
        qrLayout.setOnClickListener(getController());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
//        getController().fillViewData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void dataRequestFailed(String msg) {
        EventBus.getDefault().post(new MainLoadingEvent());
        indexRefresh.finishRefresh();
        ToastUtil.showMessage(getContext(), "首页数据加载失败：" + msg);
        final List<Integer> imgesUrlTop = new ArrayList<>();
        imgesUrlTop.add(R.drawable.ad_pic_1);
        indexFirstBanner.setData(imgesUrlTop, null);
        indexFirstBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getContext()).load(imgesUrlTop.get(position)).into((ImageView) view);
            }
        });
        final List<Integer> imgesUrl = new ArrayList<>();
        imgesUrl.add(R.drawable.ad_pic_1);
        indexSecondBanner.setData(imgesUrl, null);
        indexSecondBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getContext()).load(imgesUrl.get(position)).into((ImageView) view);
            }
        });
        if (empty_layout.getVisibility() == View.GONE) {
            empty_layout.setVisibility(View.VISIBLE);
            empty_layout.setOnClickListener(getController());
            empty_text.setText("首页数据加载失败，点击刷新");
        }

    }

    @Override
    public void setPicBannerData(final List<IndexData.HeaderbannerBean> List) {
        indexRefresh.finishRefresh();
        if (List != null && List.size() != 0) {
            indexFirstBanner.setData(List, null);
            indexFirstBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(getContext()).load(Const.IMAGE_HOST + List.get(position).getPicturePath()).into((ImageView) view);
                }
            });
        } else {
            List<String> list = new ArrayList<>();
            indexFirstBanner.setData(list, null);
            indexFirstBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(getContext()).load(list.get(position)).into((ImageView) view);
                }
            });
        }
        indexFirstBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                if (List.get(position).getPictureUrl() != null && !"".equals(List.get(position).getPictureUrl())) {
                    Logger.e(List.get(position).getPictureUrl().indexOf("https:") + "--" + List.get(position).getPictureUrl().indexOf("http:"));
                    if (List.get(position).getPictureUrl().indexOf("https:") == 0 || List.get(position).getPictureUrl().indexOf("http:") == 0) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(List.get(position).getPictureUrl());
                        intent.setData(content_url);
                        startActivity(intent);
                    } else Logger.e("地址非法");


                }
            }
        });
    }

    @Override
    public void setFunctionRvData(List<IndexFunction> dataList) {
        for (int i = 0; i <dataList.size() ; i++) {
            if(dataList.get(i).getId()==23){
                //诊间缴费
                if(clinic>0){
                    dataList.get(i).setRedPointShow(true);
                }else {
                    dataList.get(i).setRedPointShow(false);
                }
            }else if(dataList.get(i).getId()==25){
                //在线问诊
                if(unFinishInquiry>0){
                    dataList.get(i).setRedPointShow(true);
                }else {
                    dataList.get(i).setRedPointShow(false);
                }
            }else if(dataList.get(i).getId()==28){
                //检查项目
                if(unFinishInspect>0){
                    dataList.get(i).setRedPointShow(true);
                }else {
                    dataList.get(i).setRedPointShow(false);
                }
            }else if(dataList.get(i).getId()==26){
                //护理订单
                if(unFinishNurse>0){
                    dataList.get(i).setRedPointShow(true);
                }else {
                    dataList.get(i).setRedPointShow(false);
                }
            }else if(dataList.get(i).getId()==29){
                //住院预约
                if(unFinishAdmission>0){
                    dataList.get(i).setRedPointShow(true);
                }else {
                    dataList.get(i).setRedPointShow(false);
                }
            }
        }
        datalist.clear();
        datalist.addAll(dataList);
        indexFunctionAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAdBannerData(final List<IndexData.AdvertisementBean> List) {
        if (List != null && List.size() != 0) {
            indexSecondBanner.setData(List, null);
            indexSecondBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(getContext()).load(Const.IMAGE_HOST + List.get(position).getPicturePath()).into((ImageView) view);
                }
            });
            indexSecondBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, Object model, View view, int position) {
                    if (List.get(position).getPictureUrl() != null && !"".equals(List.get(position).getPictureUrl())) {
                        Logger.e(List.get(position).getPictureUrl().indexOf("https:") + "--" + List.get(position).getPictureUrl().indexOf("http:"));
                        if (List.get(position).getPictureUrl().indexOf("https:") == 0 || List.get(position).getPictureUrl().indexOf("http:") == 0) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(List.get(position).getPictureUrl());
                            intent.setData(content_url);
                            startActivity(intent);
                        } else Logger.e("地址非法");


                    }
                }
            });
        } /*else {
            final List<Integer> imgesUrl = new ArrayList<>();
            imgesUrl.add(R.drawable.ad_pic_1);
            indexSecondBanner.setData(imgesUrl, null);
            indexSecondBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(getContext()).load(imgesUrl.get(position)).into((ImageView) view);
                }
            });
        }
*/
    }

    @Override
    public void setNoticeData(final List<IndexData.NotificationsBean> List) {

        if (List == null || List.size() == 0) {
            noticeList.clear();
            List<String> list = new ArrayList<>();
            list.add("暂无通知");
            indexNoticeBanner.setData(R.layout.notice_xbanner_item, list, null);
            indexNoticeBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    TextView indexNewsTv = view.findViewById(R.id.index_news_tv);
                    indexNewsTv.setText(list.get(position));
                }
            });
            indexNoticeBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, Object model, View view, int position) {
                    ToastUtil.showMessage(getContext(), "暂无通知");
                }
            });

        } else {
            noticeList.clear();
            noticeList.addAll(List);
            indexNoticeBanner.setData(R.layout.notice_xbanner_item, List, null);
            indexNoticeBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    TextView indexNewsTv = view.findViewById(R.id.index_news_tv);
                    indexNewsTv.setText(List.get(position).getTitle());
                }
            });
            indexNoticeBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, Object model, View view, int position) {
                    NoticeDeatailActivity.start(getContext(), (IndexData.NotificationsBean) DepartmentDataHelper.getNotificationsBeanAfterHandle(List).get(position));
                }
            });
        }

    }


    @Override
    public void setArticleData(final List<HealthKnowledgeBean.KnowledgeBean> dataList) {
        if(page>1){
            indexRefresh.finishLoadMore();
        }
        if (dataList == null || dataList.size() == 0) {
            if(page==1){
                if (empty_layout.getVisibility() == View.GONE)
                    empty_layout.setVisibility(View.VISIBLE);
                empty_text.setText("暂无文章，我们正努力更新中");
                empty_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                topHealthRl.setOnClickListener(null);
                healthZoneLayout.removeAllViews();
            }
            index_footer.setText("我也是有底线的");
            indexRefresh.setEnableLoadMore(false);
            index_footer.setClickable(false);
        } else {
            if(page==1){
                if (empty_layout.getVisibility() == View.VISIBLE) {
                    empty_layout.setVisibility(View.GONE);
                }
                final int[] topReadNum = new int[1];
                topReadNum[0] = dataList.get(0).getPageView();
                indexNewArticleBanner.setData(dataList.get(0).getImageList(), null);
                new_article_title_tv.setText(dataList.get(0).getTitle());
                new_article_readernum_tv.setText("阅读人数：" + dataList.get(0).getPageView() + "  " + dataList.get(0).getCreateTime());
                indexNewArticleBanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(getContext()).load(Const.IMAGE_HOST + dataList.get(0).getImageList().get(position)).into((ImageView) view);
                    }
                });

                indexNewArticleBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                    @SingleClick(1000)
                    @Override
                    public void onItemClick(XBanner banner, Object model, View view, int position) {
                        ArticleDetailActivity.startHealth(getContext(), dataList.get(0).getId(), Global.getUserId(), "", "");
                    }
                });
                topHealthRl.setOnClickListener(new View.OnClickListener() {
                    @SingleClick(1000)
                    @Override
                    public void onClick(View v) {
                        ArticleDetailActivity.startHealth(getContext(), dataList.get(0).getId(), Global.getUserId(), "", "");
                    }
                });
                healthZoneLayout.removeAllViews();
                for (int i = 1; i < dataList.size(); i++) {
                    final GeneralArticleItem generalArticleItem = new GeneralArticleItem(getContext());
                    generalArticleItem.setArticleId(dataList.get(i).getId());
                    generalArticleItem.setTitle(dataList.get(i).getTitle());
                    generalArticleItem.setLabel(dataList.get(i).getLablelist());
                    if (dataList.get(i).getImageList() != null) {
                        generalArticleItem.setIcon(dataList.get(i).getImageList().get(0));
                    }
                    generalArticleItem.setReaderNum(dataList.get(i).getPageView(), dataList.get(i).getCreateTime());
                    generalArticleItem.setOnClickListener(new View.OnClickListener() {
                        @SingleClick(1000)
                        @Override
                        public void onClick(View v) {
                            ArticleDetailActivity.startHealth(getContext(), generalArticleItem.getArticleId(), Global.getUserId(), "", "");
                            //generalArticleItem.addReadNum();
                        }
                    });
                    healthZoneLayout.addView(generalArticleItem);
                }
            }else {
                for (int i = 0; i < dataList.size(); i++) {
                    final GeneralArticleItem generalArticleItem = new GeneralArticleItem(getContext());
                    generalArticleItem.setArticleId(dataList.get(i).getId());
                    generalArticleItem.setTitle(dataList.get(i).getTitle());
                    if (dataList.get(i).getImageList() != null) {
                        generalArticleItem.setIcon(dataList.get(i).getImageList().get(0));
                    }
                    generalArticleItem.setReaderNum(dataList.get(i).getPageView(), dataList.get(i).getCreateTime());
                    generalArticleItem.setOnClickListener(new View.OnClickListener() {
                        @SingleClick(1000)
                        @Override
                        public void onClick(View v) {
                            ArticleDetailActivity.startHealth(getContext(), generalArticleItem.getArticleId(), Global.getUserId(), "", "");
                            //generalArticleItem.addReadNum();
                        }
                    });
                    healthZoneLayout.addView(generalArticleItem);
                }
            }
            page++;
            if(dataList.size()<10){
                index_footer.setText("我也是有底线的");
                index_footer.setClickable(false);
            }else {
                index_footer.setText("点击加载更多");
                index_footer.setClickable(true);
            }

        }

    }

    @Override
    public void setRedPointView(IndexData indexData) {
        unFinishInquiry = indexData.getUnFinishInquiry();
        unFinishNurse = indexData.getUnFinishNurse();
        clinic = indexData.getClinic();
        unFinishAdmission = indexData.getUnFinishAdmission();
        unFinishInspect = indexData.getUnFinishInspect();
        for (int i = 0; i <datalist.size() ; i++) {
            if(datalist.get(i).getId()==23){
                //诊间缴费
                if(clinic>0){
                    datalist.get(i).setRedPointShow(true);
                }else {
                    datalist.get(i).setRedPointShow(false);
                }
            }else if(datalist.get(i).getId()==25){
                //在线问诊
                if(unFinishInquiry>0){
                    datalist.get(i).setRedPointShow(true);
                }else {
                    datalist.get(i).setRedPointShow(false);
                }
            }else if(datalist.get(i).getId()==28){
                //检查项目
                if(unFinishInspect>0){
                    datalist.get(i).setRedPointShow(true);
                }else {
                    datalist.get(i).setRedPointShow(false);
                }
            }else if(datalist.get(i).getId()==26){
                //护理订单
                if(unFinishNurse>0){
                    datalist.get(i).setRedPointShow(true);
                }else {
                    datalist.get(i).setRedPointShow(false);
                }
            }else if(datalist.get(i).getId()==29){
                //住院预约
                if(unFinishAdmission>0){
                    datalist.get(i).setRedPointShow(true);
                }else {
                    datalist.get(i).setRedPointShow(false);
                }
            }
        }
        indexFunctionAdapter.notifyDataSetChanged();


    }

    @Override
    public String getUserId() {
        return "";
    }

    @Override
    public String getHospitalCode() {
        return "";
    }

    @Override
    public void getHospitalListSuccess(List<HospitalAreaInfo> dataList) {
        if (dataList != null && dataList.size() != 0) {
            hospitalList.clear();
            hospitalList.addAll(dataList);
            hospitalListFromService.clear();
            hospitalListFromService.addAll(dataList);
            Global.setHospitalList(dataList);
            boolean isHaveConsult = false;
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).getIsConsult() == 1) {
                    App.hospitalId = hospitalList.get(i).getId();
                    App.hospitalName = hospitalList.get(i).getName();
                    selectHospitalId = hospitalList.get(i).getId();
                    selectHospitalName = hospitalList.get(i).getName();
                    Global.getHospitalList().get(i).setSelected(true);
                    searchEdt.setText(hospitalList.get(i).getName());
                    isHaveConsult = true;
                    break;
                }
            }
            if (!isHaveConsult) {
                App.hospitalId = hospitalList.get(0).getId();
                App.hospitalName = hospitalList.get(0).getName();
                selectHospitalId = hospitalList.get(0).getId();
                selectHospitalName = hospitalList.get(0).getName();
                Global.getHospitalList().get(0).setSelected(true);
                searchEdt.setText(hospitalList.get(0).getName());
            }
            EventBus.getDefault().post(new Event(EventType.UPDATELOCALHOSPITALLIST,null));
            EventBus.getDefault().post(new Event(EventType.UPDATEHOSPITAL, null));
            chooseHospitalAdapter.notifyDataSetChanged();
//            getController().fillViewData();
        } else {
            new GeneralDialog(getContext(), "该城市暂无服务医院，请切换城市", new GeneralDialog.OnCloseListener() {
                @Override
                public void onCommit() {
                    ChooseCityActivity.start(getContext());
                }
            }).setTitle("提示").setCancel(false).setNegativeButtonIsGone(true).setPositiveButton("确认").show();
        }
    }


    @Override
    public void getHospitalListFailed(String errMsg) {

    }


    @Override
    public void showHospitalPopupWindow() {
        for (int i = 0; i < hospitalList.size(); i++) {
            hospitalList.get(i).setSelected(false);
            if (hospitalList.get(i).getName().equals(App.hospitalName))
                hospitalList.get(i).setSelected(true);
        }
        chooseHospitalAdapter.notifyDataSetChanged();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.index_choose_hospital_popup_layout, titleLayout, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.hospital_list_rv);
        TextView cancelTv = view.findViewById(R.id.cancel_tv);
        View backgroudView = view.findViewById(R.id.backgroud_view);
        EditText hospitalSearchEdt = view.findViewById(R.id.hospital_search_edt);
        hospitalSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    hospitalList.clear();
                    hospitalList.addAll(hospitalListFromService);
                    for (int position = 0; position < hospitalList.size(); position++) {
                        hospitalList.get(position).setSelected(false);
                        if (hospitalList.get(position).getName().equals(App.hospitalName))
                            hospitalList.get(position).setSelected(true);
                    }
                    chooseHospitalAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        TextView hospitalSearchTv = view.findViewById(R.id.hospital_search_tv);
        hospitalSearchTv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                if (hospitalSearchEdt.getText().toString().trim().equals("")) {
                } else {
                    hospitalList.clear();
                    for (HospitalAreaInfo info : hospitalListFromService) {
                        if (info.getName().contains(hospitalSearchEdt.getText().toString().trim())) {
                            hospitalList.add(info);
                        }
                    }
                    for (int i = 0; i < hospitalList.size(); i++) {
                        hospitalList.get(i).setSelected(false);
                        if (hospitalList.get(i).getName().equals(App.hospitalName))
                            hospitalList.get(i).setSelected(true);
                    }
                    chooseHospitalAdapter.notifyDataSetChanged();
                }
            }
        });
        backgroudView.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                hospitalPopupWindow.dismiss();
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                hospitalPopupWindow.dismiss();
            }
        });
        TextView commitTv = view.findViewById(R.id.commit_tv);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View view) {
                App.hospitalId = selectHospitalId;
                App.hospitalName = selectHospitalName;
                for (int i = 0; i < Global.getHospitalList().size(); i++) {
                    if (Global.getHospitalList().get(i).getId() == selectHospitalId) {
                        Global.getHospitalList().get(i).setSelected(true);
                    } else
                        Global.getHospitalList().get(i).setSelected(false);

                }
                EventBus.getDefault().post(new Event(EventType.UPDATEHOSPITAL, null));
                hospitalPopupWindow.dismiss();
            }
        });
        recyclerView.setAdapter(chooseHospitalAdapter);
        hospitalPopupWindow = new PopupWindow(getContext(), null, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hospitalPopupWindow.setContentView(view);
        hospitalPopupWindow.setFocusable(true);
        hospitalPopupWindow.setWidth(titleLayout.getWidth());
        hospitalPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                backgroudView.setVisibility(View.INVISIBLE);
                hospitalList.clear();
                hospitalList.addAll(hospitalListFromService);
            }
        });

        hospitalPopupWindow.showAsDropDown(titleLayout);
        backgroudView.setVisibility(View.VISIBLE);
    }

    @Override
    public List<IndexData.NotificationsBean> getNoticeList() {
        return noticeList;
    }

    @Override
    public void getCityListSuccess(List<Object> data) {
        if(null != data && data.size() > 0){
            CityBean.itemBean city = (CityBean.itemBean) data.get(1);
            Global.setSelectedCityName(city.getName());
            Global.setSelectedCityCode(city.getCode());
            locationTv.setText(city.getName());
            getController().initHospitalList(city.getCode());
        }

    }

    @Override
    public void getCityListFailed(String msg) {
        locationTv.setText("选择城市");
        new GeneralDialog(getContext(), "自动获取城市失败，请选择所在城市", new GeneralDialog.OnCloseListener() {
            @Override
            public void onCommit() {
                ChooseCityActivity.start(getContext());
            }
        }).setTitle("提示").setCancel(false).setNegativeButtonIsGone(true).setPositiveButton("确认").show();
    }
}

