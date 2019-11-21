package com.keydom.ih_doctor.activity.doctor_cooperation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ganxin.library.LoadDataLayout;
import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_common.utils.CommonUtils;
import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_doctor.MyApplication;
import com.keydom.ih_doctor.R;
import com.keydom.ih_doctor.activity.doctor_cooperation.controller.SelectDoctorController;
import com.keydom.ih_doctor.activity.doctor_cooperation.view.SelectDoctorView;
import com.keydom.ih_doctor.adapter.DoctorSelectRecyclrViewAdapter;
import com.keydom.ih_doctor.bean.DeptDoctorBean;
import com.keydom.ih_doctor.constant.Const;
import com.keydom.ih_doctor.constant.TypeEnum;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.angmarch.views.NiceSpinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Name：com.keydom.ih_doctor.activity.personal
 * @Author：song
 * @Date：18/11/14 上午10:37
 * 修改人：xusong
 * 修改时间：18/11/14 上午10:37
 */
public class SelectDoctorActivity extends BaseControllerActivity<SelectDoctorController> implements SelectDoctorView {
    /**
     * 选择医生
     */
    private static final int DOCTOR = 0;
    /**
     * 选择护士
     */
    private static final int NURSE = 1;
    /**
     * 选择所有医护人员
     */
    private static final int ALL_USER = 2;
    /**
     * 选择其他科室的医生，只返回结果,单选
     */
    public static final int DOCTOR_SELECT_OTHER_DEPT_ONLY_RESULT = 319;
    /**
     * 选择转诊医生，单选
     */
    public static final int DOCTOR_SELECT_OTHER_DEPT_WITH_DIAGNOSE_ONLY_RESULT = 320;
    /**
     * 获取本科室下的医生
     */
    public static final int DOCTOR_SLEECT_SELF_DEPT = 312;
    /**
     * 获取本科室医生
     */
    public static final int DOCTOR_SLEECT_SELF_DEPT_ONLY_RESULT = 313;
    /**
     * 获取本科室下面的一个医生
     */
    public static final int DOCTOR_SLEECT_ONE_SELF_DEPT_ONLY_RESULT = 317;
    /**
     * 选择医生
     */
    public static final int DOCTOR_SLEECT = 307;
    /**
     * 选择医生，直接返回
     */
    public static final int DOCTOR_SLEECT_ONLY_RESULT = 308;
    /**
     * 选择添加医生团队成员，调用接口直接添加，单选
     */
    public static final int DOCTOR_SLEECT_GROUP_MEMBER_ONLY_RESULT = 309;
    private RecyclerView recyclerView;
    /**
     * 医生列表适配器
     */
    private DoctorSelectRecyclrViewAdapter doctorSelectRecyclrViewAdapter;
    /**
     * 是否在线 默认未开启在线过滤
     */
    private boolean isOnline = false;
    /**
     * 选择科室控件
     */
    private NiceSpinner niceSpinner;
    /**
     * 查询出来的医生列表
     */
    private List<DeptDoctorBean> mList = new ArrayList<>();
    /**
     * 查询出来的医生临时列表（搜索用）
     */
    private List<DeptDoctorBean> mTempList = new ArrayList<>();
    /**
     * 当前医生所在的科室（用于排除当前科室）
     */
    private long deptId = MyApplication.userInfo.getDeptId();
    /**
     * 其它页面带过来的已经存在的医生列表（用于设置列表的已选中状态）
     */
    private List<DeptDoctorBean> selectList;
    /**
     * 其它页面带过来的已经存在的临时医生列表
     */
    private List<DeptDoctorBean> tempList = new ArrayList<>();
    /**
     * 选中的医生列表（包含其它页面带过来的已存在的医生和本页面选中的医生）
     */
    private List<DeptDoctorBean> selectedList = new ArrayList<>();
    /**
     * 选择医生的页面类型
     */
    private int mType;
    private boolean isCancel = false;
    /**
     * 团队ID，用于医生协作添加成员（需要在本页面调用添加接口）
     */
    private long groupId;
    private TextView searchTv;
    private EditText searchInputEv;
    private LinearLayout filterDoctorRl;
    private ImageView consultingOpen, consultingClose, consultingActionBg;
    private RelativeLayout actionRl;
    private SmartRefreshLayout mRefreshLayout;
    /**
     * orderType 单子类型    0不需要校验   1图文问诊、2视频问诊
     * @param context
     * @param orderType
     */
    private int orderType = 0;

    /**
     * @param context 转诊选医生
     */
    public static void start(Context context, List<DeptDoctorBean> mList) {
        Intent starter = new Intent(context, SelectDoctorActivity.class);
        starter.putExtra(Const.DATA, (Serializable) mList);
        starter.putExtra(Const.TYPE, DOCTOR_SLEECT);
        ((Activity) context).startActivityForResult(starter, DOCTOR_SLEECT);
    }

    /**
     * 团队选医生
     *
     * @param context
     * @param mList
     * @param groupId
     */
    public static void startSelfDeptDoctor(Context context, List<DeptDoctorBean> mList, long groupId) {
        Intent starter = new Intent(context, SelectDoctorActivity.class);
        starter.putExtra(Const.DATA, (Serializable) mList);
        starter.putExtra(Const.GROUP_ID, groupId);
        starter.putExtra(Const.TYPE, DOCTOR_SLEECT_SELF_DEPT);
        ((Activity) context).startActivityForResult(starter, DOCTOR_SLEECT);
    }

    /**
     * 直接返回选择结果
     *
     * @param context
     */
    public static void startActivityOnlyResult(Context context) {
        Intent starter = new Intent(context, SelectDoctorActivity.class);
        starter.putExtra(Const.TYPE, DOCTOR_SLEECT_ONLY_RESULT);
        ((Activity) context).startActivityForResult(starter, DOCTOR_SLEECT_ONLY_RESULT);
    }

    public static void startActivitySelectOneOnlyResult(Context context) {
        Intent starter = new Intent(context, SelectDoctorActivity.class);
        starter.putExtra(Const.TYPE, DOCTOR_SELECT_OTHER_DEPT_ONLY_RESULT);
        ((Activity) context).startActivityForResult(starter, DOCTOR_SLEECT_ONLY_RESULT);
    }


    public static void startActivityForDiagnoseDoctor(Context context) {
        Intent starter = new Intent(context, SelectDoctorActivity.class);
        starter.putExtra(Const.TYPE, DOCTOR_SELECT_OTHER_DEPT_WITH_DIAGNOSE_ONLY_RESULT);
        ((Activity) context).startActivityForResult(starter, DOCTOR_SLEECT_ONLY_RESULT);
    }

    /**
     * orderType 单子类型    0不需要校验   1图文问诊、2视频问诊
     * @param context
     * @param orderType
     */
    public static void startActivityForDiagnoseDoctor(Context context, int orderType) {
        Intent starter = new Intent(context, SelectDoctorActivity.class);
        starter.putExtra(Const.TYPE, DOCTOR_SELECT_OTHER_DEPT_WITH_DIAGNOSE_ONLY_RESULT);
        starter.putExtra("orderType",orderType);
        ((Activity) context).startActivityForResult(starter, DOCTOR_SLEECT_ONLY_RESULT);
    }

    /**
     * 本科室直接返回选择结果
     *
     * @param context
     */
    public static void startActivitySelfDeptOnlyResult(Context context, List<DeptDoctorBean> mList) {
        Intent starter = new Intent(context, SelectDoctorActivity.class);
        starter.putExtra(Const.TYPE, DOCTOR_SLEECT_SELF_DEPT_ONLY_RESULT);
        starter.putExtra(Const.DATA, (Serializable) mList);
        ((Activity) context).startActivityForResult(starter, DOCTOR_SLEECT_SELF_DEPT_ONLY_RESULT);
    }

    /**
     * 本科室直接返回选择结果
     *
     * @param context
     */
    public static void startActivitySelfDeptOnlyResult(Context context, List<DeptDoctorBean> mList, boolean isCancel) {
        Intent starter = new Intent(context, SelectDoctorActivity.class);
        starter.putExtra(Const.TYPE, DOCTOR_SLEECT_SELF_DEPT_ONLY_RESULT);
        starter.putExtra("isCancel", isCancel);
        starter.putExtra(Const.DATA, (Serializable) mList);
        ((Activity) context).startActivityForResult(starter, DOCTOR_SLEECT_SELF_DEPT_ONLY_RESULT);
    }

    /**
     * 本科室单选直接返回结果
     *
     * @return
     */
    public static void startActivitySelfDeptOnlyResult(Context context) {
        Intent starter = new Intent(context, SelectDoctorActivity.class);
        starter.putExtra(Const.TYPE, DOCTOR_SLEECT_ONE_SELF_DEPT_ONLY_RESULT);
        ((Activity) context).startActivityForResult(starter, DOCTOR_SLEECT_ONE_SELF_DEPT_ONLY_RESULT);
    }


    public static void startActivityOfGroupMemberOnlyResult(Context context) {
        Intent starter = new Intent(context, SelectDoctorActivity.class);
        starter.putExtra(Const.TYPE, DOCTOR_SLEECT_GROUP_MEMBER_ONLY_RESULT);
        ((Activity) context).startActivityForResult(starter, DOCTOR_SLEECT_GROUP_MEMBER_ONLY_RESULT);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_select_doctor_layout;
    }

    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(Const.TYPE, -1);
        orderType = getIntent().getIntExtra("orderType", 0);
        if (mType != DOCTOR_SLEECT_ONLY_RESULT) {
            selectList = (List<DeptDoctorBean>) getIntent().getSerializableExtra(Const.DATA);
            if (selectList != null) {
                tempList.addAll(selectList);
            }
        }
        isCancel = getIntent().getBooleanExtra("isCancel", false);
        groupId = getIntent().getLongExtra(Const.GROUP_ID, 0);
        setTitle("选择医生");
        setRightTxt("确定");
        setRightBtnListener(new IhTitleLayout.OnRightTextClickListener() {
            @Override
            public void OnRightTextClick(View v) {
                if (groupId != 0) {
                    if (getAddList().size() > 0) {
                        getController().addDoctorList();
                    } else {
                        ToastUtil.showMessage(SelectDoctorActivity.this, "请选择要添加的医生");
                    }

                } else {
                    Intent intent = new Intent();
                    intent.putExtra(Const.DATA, (Serializable) tempList);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
        niceSpinner = this.findViewById(R.id.dept_spinner);
        consultingOpen = this.findViewById(R.id.consulting_open);
        consultingClose = this.findViewById(R.id.consulting_close);
        consultingActionBg = this.findViewById(R.id.consulting_action_bg);
        searchTv = this.findViewById(R.id.search_tv);
        searchInputEv = this.findViewById(R.id.search_input_ev);
        actionRl = this.findViewById(R.id.action_rl);
        recyclerView = this.findViewById(R.id.select_doctor_rv);
        filterDoctorRl = this.findViewById(R.id.filter_doctor_rl);
        mRefreshLayout = this.findViewById(R.id.refreshLayout);
        actionRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline) {
                    close();
                } else {
                    open();
                }
                getController().getDoctorList(TypeEnum.REFRESH);
            }
        });
        if (mType == DOCTOR_SELECT_OTHER_DEPT_ONLY_RESULT || mType == DOCTOR_SELECT_OTHER_DEPT_WITH_DIAGNOSE_ONLY_RESULT || mType == DOCTOR_SLEECT_GROUP_MEMBER_ONLY_RESULT) {
            doctorSelectRecyclrViewAdapter = new DoctorSelectRecyclrViewAdapter(this, mList, tempList, true, isCancel);
            setRightImgVisibility(false);
        } else {
            doctorSelectRecyclrViewAdapter = new DoctorSelectRecyclrViewAdapter(this, mList, tempList, false, isCancel);
        }

        if( mType == DOCTOR_SELECT_OTHER_DEPT_WITH_DIAGNOSE_ONLY_RESULT && (MyApplication.deptBeanList == null || MyApplication.deptBeanList.size() == 0)){
            List<String> mDeptList = new ArrayList<>();
            mDeptList.add("请选择科室");
            niceSpinner.attachDataSource(mDeptList);
            niceSpinner.setEnabled(false);
            deptId = -1;
        }else if (mType == DOCTOR_SLEECT_GROUP_MEMBER_ONLY_RESULT || mType == DOCTOR_SLEECT_ONE_SELF_DEPT_ONLY_RESULT || mType == DOCTOR_SLEECT_SELF_DEPT_ONLY_RESULT || mType == DOCTOR_SLEECT_SELF_DEPT || MyApplication.deptBeanList == null || MyApplication.deptBeanList.size() == 0) {
            List<String> mDeptList = new ArrayList<>();
            if (MyApplication.userInfo.getDeptName() != null) {
                mDeptList.add(MyApplication.userInfo.getDeptName());
                niceSpinner.attachDataSource(mDeptList);
                niceSpinner.setEnabled(false);
                deptId = MyApplication.userInfo.getDeptId();
            }
        } else {
            niceSpinner.attachDataSource(MyApplication.deptSpannerList);
            niceSpinner.setOnItemSelectedListener(getController());
            if (MyApplication.deptBeanList != null && MyApplication.deptBeanList.size() != 0) {
                deptId = MyApplication.deptBeanList.get(0).getId();
            }

        }

        if (mType == DOCTOR_SLEECT_SELF_DEPT_ONLY_RESULT || mType == DOCTOR_SLEECT_SELF_DEPT || mType == DOCTOR_SLEECT_ONE_SELF_DEPT_ONLY_RESULT || mType == DOCTOR_SLEECT_GROUP_MEMBER_ONLY_RESULT) {
            filterDoctorRl.setVisibility(View.GONE);
        } else {
            if (isOnline) {
                close();
            } else {
                open();
            }
        }

        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.hideSoftKeyboard(SelectDoctorActivity.this);
                searchMember(searchInputEv.getText().toString().trim());
            }
        });

        searchInputEv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                searchMember(searchInputEv.getText().toString().trim());
            }
        });
        recyclerView.setAdapter(doctorSelectRecyclrViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        pageLoading();
        if (mType == DOCTOR_SLEECT_GROUP_MEMBER_ONLY_RESULT) {
            getController().ihGroupQueryDoctorTeamAllUser();
        } else {
            getController().getDoctorList(TypeEnum.REFRESH);

        }
        setReloadListener(new LoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                pageLoading();
                if (mType == DOCTOR_SLEECT_GROUP_MEMBER_ONLY_RESULT) {
                    getController().ihGroupQueryDoctorTeamAllUser();
                } else {
                    getController().getDoctorList(TypeEnum.REFRESH);

                }
            }
        });

        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            getController().getDoctorList(TypeEnum.REFRESH);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> getController().getDoctorList(TypeEnum.LOAD_MORE));
    }


    /**
     * 获取添加的医生列表
     *
     * @return
     */
    private List<String> getAddList() {
        selectedList.clear();
        List<String> addDoctorList = new ArrayList<>();
        boolean isExist;
        for (int i = 0; i < tempList.size(); i++) {
            isExist = false;
            for (int j = 0; j < selectList.size(); j++) {
                if (tempList.get(i).getUuid() == selectList.get(j).getUuid()) {
                    isExist = true;
                    continue;
                }
            }
            if (!isExist) {
                addDoctorList.add(tempList.get(i).getUuid());
                selectedList.add(tempList.get(i));
            }
        }
        return addDoctorList;
    }

    /**
     * 筛选在线医生
     */
    public void open() {
        isOnline = true;
        consultingOpen.setVisibility(View.VISIBLE);
        consultingClose.setVisibility(View.GONE);
        consultingActionBg.setImageDrawable(getResources().getDrawable(R.mipmap.consulting_open_bg));
    }

    /**
     * 关闭筛选在线医生
     */
    public void close() {
        isOnline = false;
        consultingOpen.setVisibility(View.GONE);
        consultingClose.setVisibility(View.VISIBLE);
        consultingActionBg.setImageDrawable(getResources().getDrawable(R.mipmap.consulting_close_bg));
    }


    @Override
    public Map<String, Object> getQueryMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("deptId", deptId);
        map.put("isOnline", isOnline ? 1 : 0);
        if (mType == DOCTOR_SELECT_OTHER_DEPT_WITH_DIAGNOSE_ONLY_RESULT) {
            map.put("isFilterUnInquiry", 1);
        }
        map.put("type", (mType == DOCTOR_SLEECT_SELF_DEPT ? ALL_USER : DOCTOR));
        map.put("currentPage", getController().getCurrentPage());
        map.put("pageSize", Const.PAGE_SIZE);
        return map;
    }

    @Override
    public Map<String, Object> getAddMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        map.put("hospitalUsers", getAddList());
        return map;
    }

    @Override
    public void getDoctorListSuccess(List<DeptDoctorBean> list, TypeEnum typeEnum) {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
        pageLoadingSuccess();

        if (typeEnum == TypeEnum.REFRESH) {
            mList.clear();
            mTempList.clear();
        }
        mList.addAll(list);
        mTempList.addAll(list);
        doctorSelectRecyclrViewAdapter.notifyDataSetChanged();
        getController().currentPagePlus();
    }

    /**
     * 关键字搜索医生，只能搜索本地医生
     *
     * @param key 搜索关键字
     */
    private void searchMember(String key) {
        mList.clear();
        mList.addAll(mTempList);
        Iterator<DeptDoctorBean> it = mList.iterator();
        while (it.hasNext()) {
            if (!it.next().getName().contains(key)) {
                it.remove();
            }
        }
        doctorSelectRecyclrViewAdapter.notifyDataSetChanged();
    }

    /**
     * 去掉本人
     *
     * @param list
     * @return
     */
    public List<DeptDoctorBean> getWithOutMeList(List<DeptDoctorBean> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(MyApplication.userInfo.getUserCode())) {
                list.remove(i);
            }
        }
        return list;
    }

    @Override
    public void getDoctorListFailed(String errMsg) {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
        pageLoadingFail();
    }

    @Override
    public void addDoctorSuccess(String msg) {
        Intent intent = new Intent();
        intent.putExtra(Const.DATA, (Serializable) selectedList);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void addDoctorFailed(String errMsg) {
        ToastUtil.showMessage(this, errMsg);
    }

    @Override
    public void selectDept(int position) {
        deptId = MyApplication.deptBeanList.get(position).getId();
    }
}
