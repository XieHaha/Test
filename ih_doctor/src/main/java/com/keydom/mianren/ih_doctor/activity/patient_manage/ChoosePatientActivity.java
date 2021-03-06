package com.keydom.mianren.ih_doctor.activity.patient_manage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.activity.patient_manage.controller.ChoosePatientController;
import com.keydom.mianren.ih_doctor.activity.patient_manage.view.ChoosePatientView;
import com.keydom.mianren.ih_doctor.adapter.ChoosePatientRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.ImPatientInfo;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.Const;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.keydom.mianren.ih_doctor.view.TagView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class ChoosePatientActivity extends BaseControllerActivity<ChoosePatientController> implements ChoosePatientView {

    private TextView searchInputEv;
    private RecyclerView patientRv;
    private SmartRefreshLayout mRefreshV;
    private AppCompatImageView appCompatImageView;
    /**
     * ?????????????????????
     */
    private ChoosePatientRecyclrViewAdapter choosePatientRecyclrViewAdapter;
    /**
     * ??????????????????????????????
     */
    private List<ImPatientInfo> mList = new ArrayList<>();
    /**
     * ?????????????????????
     */
    private List<ImPatientInfo> selectList = new ArrayList<>();
    /**
     * ???????????????????????????????????????????????????
     */
    private List<ImPatientInfo> tempList;
    private TagView flowLayout;
    /**
     * ???????????????view??????
     */
    private Stack<View> chooseStack = new Stack<>();
    private SearchView mSearchView;
    /**
     * ??????????????????
     */
    private List<ImPatientInfo> filterList;
    Random random = new Random();
    /**
     * ???????????????
     */
    int[] mArray = {
            R.mipmap.point_blue,
            R.mipmap.point_black,
            R.mipmap.point_green,
            R.mipmap.point_orange,
            R.mipmap.point_purple,
            R.mipmap.point_purple_blue,
            R.mipmap.point_red,
            R.mipmap.point_yellow};

    /**
     * ????????????????????????
     *
     * @param context
     * @param list    ????????????
     */
    public static void start(Context context, List<ImPatientInfo> list) {
        Intent starter = new Intent(context, ChoosePatientActivity.class);
        starter.putExtra(Const.DATA, (Serializable) list);
        ((Activity) context).startActivityForResult(starter, Const.PATIENT_SLEECT_ONLY_RESULT);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_choose_patient_layout;
    }

    private void initView() {
        patientRv = this.findViewById(R.id.patient_rv);
        flowLayout = this.findViewById(R.id.selected_patient_fl);
        mSearchView = this.findViewById(R.id.search);
        searchInputEv = this.findViewById(R.id.search_input_ev);
        mRefreshV = this.findViewById(R.id.choose_patient_refresh_v);
        setIcon(mSearchView, "??????", "");
        choosePatientRecyclrViewAdapter = new ChoosePatientRecyclrViewAdapter(this, mList);
        patientRv.setAdapter(choosePatientRecyclrViewAdapter);
        patientRv.setLayoutManager(new LinearLayoutManager(this));
        patientRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        appCompatImageView = (AppCompatImageView) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        EditText editText = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setFilters(new InputFilter[]{emojiFilter });
        editText.setSingleLine(true);
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchInputEv.setVisibility(View.VISIBLE);
                mSearchView.setVisibility(View.GONE);
                return false;
            }
        });


        searchInputEv.setOnClickListener(new View.OnClickListener() {
            @SingleClick(1000)
            @Override
            public void onClick(View v) {
                searchInputEv.setVisibility(View.GONE);
                mSearchView.setVisibility(View.VISIBLE);
                if (appCompatImageView != null)
                    appCompatImageView.performClick();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterFriend(newText);
                return false;
            }
        });

        mRefreshV.setOnRefreshListener(refreshLayout -> getController().getUserList(TypeEnum.REFRESH));
        mRefreshV.setOnLoadMoreListener(refreshLayout -> getController().getUserList(TypeEnum.LOAD_MORE));
    }

    /**
     * ????????????????????????
     *
     * @param key ?????????
     */
    private void filterFriend(String key) {
        if (filterList == null) {
            return;
        }
        mList.clear();
        for (int i = 0; i < filterList.size(); i++) {
            if (filterList.get(i).getUserName().contains(key)) {
                mList.add(filterList.get(i));
            }
        }
        choosePatientRecyclrViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setTitle("????????????");
        setRightTxt("??????");
        tempList = (List<ImPatientInfo>) getIntent().getSerializableExtra(Const.DATA);
        if (tempList == null) {
            tempList = new ArrayList<>();
        }
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Const.DATA, (Serializable) selectList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        initView();
        pageLoading();
        getController().getUserList(TypeEnum.REFRESH);
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                getController().getUserList(TypeEnum.REFRESH);
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void getUserListSuccess(List<ImPatientInfo> list,TypeEnum typeEnum) {
        mRefreshV.finishLoadMore();
        mRefreshV.finishRefresh();
        pageLoadingSuccess();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < tempList.size(); j++) {
                    if (list.get(i).getId() == tempList.get(j).getId()) {
                        list.get(i).setSelect(true);
                        addTag(list.get(i));
                    }
                }
            }
        }
        if (typeEnum == TypeEnum.REFRESH) {
            mList.clear();
        }
        mList.addAll(list);
        filterList = list;
        choosePatientRecyclrViewAdapter.notifyDataSetChanged();
        getController().currentPagePlus();
    }

    @Override
    public void getUserListFailed(String errMsg) {
        mRefreshV.finishLoadMore();
        mRefreshV.finishRefresh();
        pageLoadingFail();
    }

    /**
     * ???????????????????????????
     *
     * @param tag
     */
    private void addTag(String tag) {

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.choose_patient_tag_tv, null, true);
        TextView tagTv = view.findViewById(R.id.tag_tv);
        tagTv.setText(tag);
        int index = random.nextInt(5);
        Drawable drawable = getContext().getResources().getDrawable(mArray[index]);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tagTv.setCompoundDrawables(drawable, null, null, null);
        flowLayout.addView(view, lp);
        chooseStack.push(view);
    }



    /**
     * ????????????
     *
     * @param info
     */
    private void addTag(ImPatientInfo info) {
        if (info == null) {
            return;
        }
        selectList.add(info);
        addTag(info.getUserName());
    }

    /**
     * ????????????
     *
     * @param info
     */
    private void removeTag(ImPatientInfo info) {
        if (info == null) {
            return;
        }
        for (int i = 0; i < selectList.size(); i++) {
            if (info.getId() == selectList.get(i).getId()) {
                selectList.remove(i);
                flowLayout.removeView(chooseStack.get(i));
                chooseStack.remove(i);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {

        if (messageEvent.getType() == EventType.SELECT_PATIENT_RESULT) {
            ImPatientInfo info = (ImPatientInfo) messageEvent.getData();
            addTag(info);

        } else if (messageEvent.getType() == EventType.UN_SELECT_PATIENT_RESULT) {
            ImPatientInfo info = (ImPatientInfo) messageEvent.getData();
            removeTag(info);

        } else if (messageEvent.getType() == EventType.PATIENT_UPDATE_USER_LIST) {
            getController().getUserList(TypeEnum.REFRESH);
        }
    }


    /**
     * ?????????????????????
     *
     * @param seach ????????????
     * @param hint  ????????????
     * @param text  ???????????????
     */
    public void setIcon(SearchView seach, String hint, String text) {
        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete) seach.findViewById(R.id.search_src_text);
        textView.setTextColor(this.getResources().getColor(R.color.fontColorDirection));
        if (TextUtils.isEmpty(text)) {
            textView.setHint(hint);
        } else {
            textView.setText(text);
        }
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, this.getResources().getDimensionPixelOffset(R.dimen.font_size_primary));
        textView.setTextColor(this.getResources().getColor(R.color.fontColorPrimary));

    }

    InputFilter emojiFilter = new InputFilter() {
        /*Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);*/

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (CommonUtils.containsEmoji(source.toString())) {
                Toast.makeText(getContext(), "?????????????????????????????????", Toast.LENGTH_SHORT).show();
                return "";
            }
            return null;
          /*  Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                Toast.makeText(context, "?????????????????????", Toast.LENGTH_SHORT).show();
                return "";
            }
            return null;*/
        }
    };
}
