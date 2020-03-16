package com.keydom.mianren.ih_doctor.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.keydom.ih_common.base.BaseControllerFragment;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.utils.ToastUtil;
import com.keydom.mianren.ih_doctor.MyApplication;
import com.keydom.mianren.ih_doctor.R;
import com.keydom.mianren.ih_doctor.adapter.ContactRecyclrViewAdapter;
import com.keydom.mianren.ih_doctor.bean.ImPatientInfo;
import com.keydom.mianren.ih_doctor.bean.MessageEvent;
import com.keydom.mianren.ih_doctor.constant.EventType;
import com.keydom.mianren.ih_doctor.constant.TypeEnum;
import com.keydom.mianren.ih_doctor.fragment.controller.PatientContactFragmentController;
import com.keydom.mianren.ih_doctor.fragment.view.PatientContactFragmentView;
import com.keydom.mianren.ih_doctor.m_interface.SingleClick;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.kentra.yxyz.fragment
 * @Description：患者管理联系人页面
 * @Author：song
 * @Date：18/11/5 下午5:27
 * 修改人：xusong
 * 修改时间：18/11/5 下午5:27
 */
public class PatientContactFragment extends BaseControllerFragment<PatientContactFragmentController> implements PatientContactFragmentView {

    private RecyclerView contactRv;
    /**
     * 所有人列表
     */
    private List<ImPatientInfo> mList = new ArrayList<>();
    /**
     * 所有人备份列表（搜索用）
     */
    private List<ImPatientInfo> filterList;
    private ContactRecyclrViewAdapter contactRecyclrViewAdapter;
    private SearchView mSearchView;
    private TextView searchInputEv;
    private AppCompatImageView appCompatImageView;
    private SmartRefreshLayout mRefreshV;

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initView();
    }

    private DividerItemDecoration dividerItemDecoration;

    /**
     * 初始化页面
     */
    private void initView() {
        contactRecyclrViewAdapter = new ContactRecyclrViewAdapter(getContext(), mList);
        contactRv = (RecyclerView) getView().findViewById(R.id.all_contact_rv);
        mSearchView = (SearchView) getView().findViewById(R.id.search);
        searchInputEv = (TextView) getView().findViewById(R.id.search_input_ev);
        mRefreshV = (SmartRefreshLayout) getView().findViewById(R.id.refreshLayout);
        contactRv.setNestedScrollingEnabled(false);
        contactRv.setAdapter(contactRecyclrViewAdapter);
        contactRv.setLayoutManager(new LinearLayoutManager(getContext()));
        dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
//        contactRv.addItemDecoration(dividerItemDecoration);
        mSearchView.setQueryHint("搜索");
        mSearchView.setIconified(true);
        appCompatImageView = (AppCompatImageView) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        EditText editText = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        editText.setFilters(new InputFilter[]{emojiFilter });
        editText.setSingleLine(true);
        editText.setHintTextColor(ContextCompat.getColor(getContext(), R.color.click_txt));
        editText.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_font_color));
        try {
            java.lang.reflect.Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(editText, R.drawable.cursor_bg);
        } catch (Exception e) {
        }
        editText.setTextSize(CommonUtils.px2dip(getContext(), getResources().getDimension(R.dimen.font_size_primary)));
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(mSearchView, R.color.click_txt);
        } catch (Exception e) {

        }
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    filterFriend(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null) {
                    filterFriend(newText);
                }
                return false;
            }
        });

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

        mRefreshV.setOnRefreshListener(refreshLayout -> getController().getUserList(TypeEnum.REFRESH));
        mRefreshV.setOnLoadMoreListener(refreshLayout -> getController().getUserList(TypeEnum.LOAD_MORE));
    }

    /**
     * 关键字搜索联系人
     *
     * @param key 关键字
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
        contactRecyclrViewAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_patient_contact;
    }

    @Override
    public void getUserListSuccess(List<ImPatientInfo> list, TypeEnum typeEnum) {
        mRefreshV.finishLoadMore();
        mRefreshV.finishRefresh();
        pageLoadingSuccess();

        if (typeEnum == TypeEnum.REFRESH) {
            mList.clear();
        }
        mList.addAll(list);
        filterList = list;
        contactRecyclrViewAdapter.notifyDataSetChanged();
        getController().currentPagePlus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void getUserListFailed(String errMsg) {
        ToastUtil.showMessage(getContext(), errMsg);
    }

    @Override
    public void lazyLoad() {
        getController().getUserList(TypeEnum.REFRESH);
    }

    @Override
    public Map<String, Object> getListMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("doctorId", MyApplication.userInfo.getId());
        return map;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getType() == EventType.PATIENT_UPDATE_USER_LIST) {
            getController().getUserList(TypeEnum.REFRESH);
        }
    }

    InputFilter emojiFilter = new InputFilter() {
        /*Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);*/

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (CommonUtils.containsEmoji(source.toString())) {
                Toast.makeText(getContext(), "不支持输入颜文字或表情", Toast.LENGTH_SHORT).show();
                return "";
            }
            return null;
          /*  Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                Toast.makeText(context, "不支持输入表情", Toast.LENGTH_SHORT).show();
                return "";
            }
            return null;*/
        }
    };
}
