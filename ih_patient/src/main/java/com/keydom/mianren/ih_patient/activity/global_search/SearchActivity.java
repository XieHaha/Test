package com.keydom.mianren.ih_patient.activity.global_search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.keydom.ih_common.activity.ArticleDetailActivity;
import com.keydom.ih_common.adapter.SearchResultAdapter;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.bean.SearchResultBean;
import com.keydom.ih_common.constant.SearchConst;
import com.keydom.ih_common.minterface.OnSearchListItemClickListener;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.mianren.ih_patient.App;
import com.keydom.mianren.ih_patient.R;
import com.keydom.mianren.ih_patient.activity.global_search.controller.SearchController;
import com.keydom.mianren.ih_patient.activity.global_search.view.SearchView;
import com.keydom.mianren.ih_patient.activity.my_doctor_or_nurse.DoctorOrNurseDetailActivity;
import com.keydom.mianren.ih_patient.activity.order_doctor_register.ChooseDoctorActivity;
import com.keydom.mianren.ih_patient.constant.Const;
import com.keydom.mianren.ih_patient.constant.Global;
import com.keydom.mianren.ih_patient.constant.TypeEnum;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.kentra.yxyz.activity
 * @Description：公用搜索页面
 * @Author：song
 * @Date：18/11/2 下午4:37
 * 修改人：xusong
 * 修改时间：18/11/2 下午4:37
 */
public class SearchActivity extends BaseControllerActivity<SearchController> implements SearchView {


    private EditText searchEt;
    private TextView searchCloseTv;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private SearchResultAdapter searchResultAdapter;
    private List<MultiItemEntity> result = new ArrayList<>();
    private int mType;
    private String keyword;

    /**
     * 打开公用搜索页面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, SearchActivity.class);
        context.startActivity(starter);
    }

    public static void startWithType(Context context, int type, String keyword) {
        Intent starter = new Intent(context, SearchActivity.class);
        starter.putExtra(Const.TYPE, type);
        starter.putExtra(Const.DATA, keyword);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_search_layout;
    }

    public void initSearchWithType() {
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableRefresh(true);
        if (keyword != null) {
            searchEt.setText(keyword);
            searchEt.setSelection(searchEt.getText().toString().length());
        }
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getController().pageSearch(TypeEnum.LOAD_MORE);
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getController().setCurrentPage(1);
                getController().pageSearch(TypeEnum.REFRESH);
            }
        });
        getController().pageSearch(TypeEnum.REFRESH);
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(Const.TYPE, -1);
        keyword = getIntent().getStringExtra(Const.DATA);
        searchEt = this.findViewById(R.id.search_edt);
        refreshLayout = this.findViewById(R.id.refreshLayout);
        searchCloseTv = this.findViewById(R.id.search_close_tv);
        searchCloseTv.setOnClickListener(getController());
        recyclerView = (RecyclerView) this.findViewById(R.id.doctor_or_department_rv);
        searchResultAdapter = new SearchResultAdapter(result);
        recyclerView.setAdapter(searchResultAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setSmoothScrollbarEnabled(true);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        if (mType != -1) {
            initSearchWithType();
        }
        searchResultAdapter.setOnItemClickListener(new OnSearchListItemClickListener() {
            @Override
            public void click(MultiItemEntity item) {
                if (item instanceof SearchResultBean.UserBean) {
//                    ToastUtil.showMessage(SearchActivity.this, "医生详情");
                    DoctorOrNurseDetailActivity.startDoctorPage(getContext(), 1, ((SearchResultBean.UserBean) item).getUserCode());
                }
                if (item instanceof SearchResultBean.DeptBean) {
//                    ToastUtil.showMessage(SearchActivity.this, "科室详情");
                    ChooseDoctorActivity.start(getContext(), ((SearchResultBean.DeptBean) item).getHospitalAreaId(), ((SearchResultBean.DeptBean) item).getHospitalAreaName(), ((SearchResultBean.DeptBean) item).getId(), ((SearchResultBean.DeptBean) item).getName(), null);
                }
                if (item instanceof SearchResultBean.ArticleBean) {
//                    ArticleDetailActivity.startArticle(SearchActivity.this, ((SearchResultBean.ArticleBean) item).getId(), MyApplication.userInfo.getId(), MyApplication.userInfo.getName(), MyApplication.userInfo.getAvatar(), true);
                    ArticleDetailActivity.startHealth(getContext(), ((SearchResultBean.ArticleBean) item).getId(), Global.getUserId(), "", "");
                }
                if (item instanceof SearchResultBean.BottomItemBean) {
                    SearchActivity.startWithType(SearchActivity.this, ((SearchResultBean.BottomItemBean) item).getType(), searchEt.getText().toString().trim());
                }
            }
        });
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            CommonUtils.hideSoftKeyboard(SearchActivity.this);
                            getController().setCurrentPage(1);
                            if (mType != -1) {
                                getController().pageSearch(TypeEnum.REFRESH);
                            } else {
                                getController().search();
                            }
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getController().setCurrentPage(1);
                if (mType != -1) {
                    getController().pageSearch(TypeEnum.REFRESH);
                } else {
                    getController().search();
                }
            }
        });
    }


    @Override
    public Map<String, Object> getSearchMap() {
        Map map = new HashMap();
        map.put("hospitalId", App.hospitalId);
        map.put("keyword", searchEt.getText().toString().trim());
        return map;
    }

    @Override
    public Map<String, Object> getPageSearchMap() {
        Map map = new HashMap();
        map.put("hospitalId", App.hospitalId);
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        map.put("type", mType);
        map.put("keyword", searchEt.getText().toString().trim());
        return map;
    }


    private MultiItemEntity getTitleBean(String name) {
        MultiItemEntity titleBean = new SearchResultBean.TitleItemBean();
        ((SearchResultBean.TitleItemBean) titleBean).setName(name);
        return titleBean;
    }

    private MultiItemEntity getBottomBean(String name, int type) {
        MultiItemEntity bottomBean = new SearchResultBean.BottomItemBean();
        ((SearchResultBean.BottomItemBean) bottomBean).setName(name);
        ((SearchResultBean.BottomItemBean) bottomBean).setType(type);
        return bottomBean;
    }

    @Override
    public void searchSuccess(SearchResultBean bean) {
        result.clear();
        pageLoadingSuccess();
        if (bean == null) {
            searchEmpty();
            return;
        }
        if (bean.getUser() != null && bean.getUser().getList() != null && bean.getUser().getList().size() > 0) {
            result.add(getTitleBean(bean.getUser().getName()));
            List<JSONObject> userBeans = (List<JSONObject>) bean.getUser().getList();
            int size = userBeans.size() > 3 ? 3 : userBeans.size();
            for (int i = 0; i < size; i++) {
                result.add(JSON.toJavaObject(userBeans.get(i), SearchResultBean.UserBean.class));
            }
            if (userBeans.size() > 3)
                result.add(getBottomBean("查看更多", bean.getUser().getType()));
        }
        if (bean.getDept() != null && bean.getDept().getList() != null && bean.getDept().getList().size() > 0) {
            result.add(getTitleBean(bean.getDept().getName()));
            List<JSONObject> deptBeans = (List<JSONObject>) bean.getDept().getList();
            int size = deptBeans.size() > 3 ? 3 : deptBeans.size();
            for (int i = 0; i < size; i++) {
                result.add(JSON.toJavaObject(deptBeans.get(i), SearchResultBean.DeptBean.class));
            }
            if (deptBeans.size() > 3)
                result.add(getBottomBean("查看更多", bean.getDept().getType()));
        }
        if (bean.getArticle() != null && bean.getArticle().getList() != null && bean.getArticle().getList().size() > 0) {
            result.add(getTitleBean(bean.getArticle().getName()));
            List<JSONObject> articleBeans = (List<JSONObject>) bean.getArticle().getList();
            int size = articleBeans.size() > 3 ? 3 : articleBeans.size();

            for (int i = 0; i < size; i++) {
                result.add(JSON.toJavaObject(articleBeans.get(i), SearchResultBean.ArticleBean.class));
            }
            if (articleBeans.size() > 3)
                result.add(getBottomBean("查看更多", bean.getArticle().getType()));
        }
        if (result != null && result.size() != 0) {
            result.add(new SearchResultBean.NomoreData());
        } else {
            searchEmpty();
        }
        searchResultAdapter.setKeyWord(searchEt.getText().toString().trim());
        searchResultAdapter.notifyDataSetChanged();
    }

    @Override
    public void searchFailed(String msg) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
        if (refreshLayout.isLoading()) {
            refreshLayout.finishLoadMore();
        }
        pageLoadingFail();
        result.clear();
        searchResultAdapter.notifyDataSetChanged();
    }

    @Override
    public void pageSearchSuccess(TypeEnum typeEnum, List<JSONObject> list) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
        if (refreshLayout.isLoading()) {
            refreshLayout.finishLoadMore();
        }
        if (typeEnum == TypeEnum.REFRESH) {
            result.clear();
            if (list == null || list.size() == 0) {
                searchEmpty();
                return;
            }
        }
        getController().currentPagePlus();
        pageLoadingSuccess();

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (mType == SearchConst.SEARCH_USER)
                    result.add(JSON.toJavaObject(list.get(i), SearchResultBean.UserBean.class));
                else if (mType == SearchConst.SEARCH_DEPT)
                    result.add(JSON.toJavaObject(list.get(i), SearchResultBean.DeptBean.class));
                else if (mType == SearchConst.SEARCH_HEALTH)
                    result.add(JSON.toJavaObject(list.get(i), SearchResultBean.ArticleBean.class));
            }
        }
        searchResultAdapter.setKeyWord(searchEt.getText().toString().trim());
        searchResultAdapter.notifyDataSetChanged();
    }

    @Override
    public String getSearchKeyWord() {
        return searchEt.getText().toString().trim();
    }
}
