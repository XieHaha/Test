package com.keydom.ih_doctor.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.keydom.ih_common.im.ImClient;
import com.keydom.ih_common.minterface.OnSearchListItemClickListener;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.controller.SearchController;
import com.keydom.ih_doctor.activity.patient_manage.PatientDatumActivity;
import com.keydom.ih_doctor.activity.view.SearchView;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
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
                if (item instanceof SearchResultBean.ArticleBean) {
                    ArticleDetailActivity.startArticle(SearchActivity.this, ((SearchResultBean.ArticleBean) item).getId(), MyApplication.userInfo.getId(), MyApplication.userInfo.getName(), MyApplication.userInfo.getAvatar(), true);
                }
                if (item instanceof SearchResultBean.BottomItemBean) {
                    SearchActivity.startWithType(SearchActivity.this, ((SearchResultBean.BottomItemBean) item).getType(), searchEt.getText().toString().trim());
                }
                if (item instanceof SearchResultBean.PatientBean) {
                    String patientCode = String.valueOf(((SearchResultBean.PatientBean) item).getPatientCode());
                    if (patientCode != null && !"".equals(patientCode)) {
                        PatientDatumActivity.start(SearchActivity.this, String.valueOf(((SearchResultBean.PatientBean) item).getPatientCode()));
                    } else {
                        ToastUtil.showMessage(SearchActivity.this, "无法查看，患者数据错误，请联系管理员");
                    }
                }
                if (item instanceof SearchResultBean.OrderBean) {
                    if (ImClient.getUserInfoProvider().getUserInfo(((SearchResultBean.OrderBean) item).getPatientCode()) != null) {
                        ImClient.startConversation(SearchActivity.this, ((SearchResultBean.OrderBean) item).getPatientCode(), null);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                        builder.setTitle("未获取到该用户信息");
                        builder.setMessage("请检查后重新尝试！");
                        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.create().show();
                    }
                }
            }
        });
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener()

        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            CommonUtils.hideSoftKeyboard(SearchActivity.this);
                            if (mType != -1) {
                                getController().setCurrentPage(1);
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
        searchEt.addTextChangedListener(new

                                                TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                    }

                                                    @Override
                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                    }

                                                    @Override
                                                    public void afterTextChanged(Editable s) {
                                                        if (mType != -1) {
                                                            getController().setCurrentPage(1);
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
        map.put("hospitalId", MyApplication.userInfo.getHospitalId());
        map.put("doctorCode", MyApplication.userInfo.getUserCode());
        map.put("keyword", searchEt.getText().toString().trim());
        return map;
    }

    @Override
    public Map<String, Object> getPageSearchMap() {
        Map map = new HashMap();
        map.put("hospitalId", MyApplication.userInfo.getHospitalId());
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
        //添加患者列表
        if (bean.getUser() != null && bean.getUser().getList() != null && bean.getUser().getList().size() > 0) {
            result.add(getTitleBean(bean.getUser().getName()));
            List<JSONObject> patientBeans = (List<JSONObject>) bean.getUser().getList();
            int size = patientBeans.size() > 3 ? 3 : patientBeans.size();
            for (int i = 0; i < size; i++) {
                result.add(JSON.toJavaObject(patientBeans.get(i), SearchResultBean.PatientBean.class));
            }
            if (patientBeans.size() > 3)
                result.add(getBottomBean("查看更多", bean.getUser().getType()));
        }

        //添加订单列表
        if (bean.getOrder() != null && bean.getOrder().getList() != null && bean.getOrder().getList().size() > 0) {
            result.add(getTitleBean(bean.getOrder().getName()));
            List<JSONObject> orderBeans = (List<JSONObject>) bean.getOrder().getList();
            int size = orderBeans.size() > 3 ? 3 : orderBeans.size();

            for (int i = 0; i < size; i++) {
                result.add(JSON.toJavaObject(orderBeans.get(i), SearchResultBean.OrderBean.class));
            }
            if (orderBeans.size() > 3)
                result.add(getBottomBean("查看更多", bean.getOrder().getType()));
        }

        /**
         * 添加文章列表
         */
        if (bean.getArticle() != null && bean.getArticle().getList() != null && bean.getArticle().getList().size() > 0) {
            result.add(getTitleBean(bean.getArticle().getName()));
            List<JSONObject> articleBeans = (List<JSONObject>) bean.getArticle().getList();
            int size = articleBeans.size() > 3 ? 3 : articleBeans.size();
            for (int i = 0; i <size; i++) {
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
                else if (mType == SearchConst.SEARCH_ARTICLE)
                    result.add(JSON.toJavaObject(list.get(i), SearchResultBean.ArticleBean.class));
                else if (mType == SearchConst.SEARCH_ORDER)
                    result.add(JSON.toJavaObject(list.get(i), SearchResultBean.OrderBean.class));
                else if (mType == SearchConst.SEARCH_PATIENT)
                    result.add(JSON.toJavaObject(list.get(i), SearchResultBean.PatientBean.class));
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
