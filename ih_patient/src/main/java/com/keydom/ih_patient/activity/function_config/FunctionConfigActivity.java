package com.keydom.ih_patient.activity.function_config;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.keydom.ih_common.base.BaseControllerActivity;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.activity.function_config.controller.FunctionConfigController;
import com.keydom.ih_patient.activity.function_config.view.FunctionConfigView;
import com.keydom.ih_patient.adapter.FunctionConfigAdapter;
import com.keydom.ih_patient.bean.Event;
import com.keydom.ih_patient.bean.IndexFunction;
import com.keydom.ih_patient.callback.ItemDragCallback;
import com.keydom.ih_patient.constant.EventType;
import com.keydom.ih_patient.constant.Global;
import com.keydom.ih_patient.constant.Type;
import com.keydom.ih_patient.utils.LocalizationUtils;
import com.keydom.ih_patient.view.FunctionConfigRvDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
/**
*@Author: LiuJie
*@Date: 2019/3/4 0004
*@Desc: 首页菜单配置页面
*/
public class FunctionConfigActivity extends BaseControllerActivity<FunctionConfigController> implements FunctionConfigView   {
    private RecyclerView allFunctionRv,selectedFunctionRv;
    private FunctionConfigRvDecoration functionConfigRvDecoration;
    private FunctionConfigAdapter allFunctionAdapter,selectedFunctionAdapter;
    private ItemDragCallback callback;
    private TextView editorTv,topLabelTv,hintTv;
    private List<IndexFunction>selectedList=new ArrayList<>(), indexFunctionList=new ArrayList<>();
    private boolean isEditing=false;
    @Override
    public int getLayoutRes() {
        return R.layout.activity_function_config_layout;
    }

    /**
     * 菜单配置修改监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecieve(IndexFunction data) {
        if (data.isSelected()) {
            selectedList.add(data);
        } else {
            for (int i = 0; i < selectedList.size(); i++) {
                if (selectedList.get(i).getCode().equals(data.getCode())) {
                    selectedList.remove(i);
                }
            }
            for (int i = 0; i < indexFunctionList.size(); i++) {
                if (indexFunctionList.get(i).getCode().equals(data.getCode())) {
                    indexFunctionList.get(i).setSelected(false);
                    allFunctionAdapter.refreshSelectedCount();
                }
            }
        }
        selectedFunctionAdapter.notifyDataSetChanged();
        allFunctionAdapter.notifyDataSetChanged();
    }
    @Override
    public void initData(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.function_config_title));
        getTitleLayout().setRightTitle(getString(R.string.function_config_complete));
        getTitleLayout().initViewsVisible(true,true,true);
        EventBus.getDefault().register(this);
        hintTv=this.findViewById(R.id.my_func_hint_tv);
        topLabelTv=this.findViewById(R.id.function_confing_label);
        allFunctionRv=this.findViewById(R.id.all_function_rv);
        selectedFunctionRv=this.findViewById(R.id.selected_function_rv);
        functionConfigRvDecoration=new FunctionConfigRvDecoration(3,6);
        allFunctionRv.setLayoutManager(new GridLayoutManager(this,4));
        allFunctionRv.addItemDecoration(functionConfigRvDecoration);
        allFunctionAdapter=new FunctionConfigAdapter(this,indexFunctionList,Type.ALLFUNCTIONTYPE);
        allFunctionRv.setAdapter(allFunctionAdapter);
        selectedFunctionRv.setLayoutManager(new GridLayoutManager(this,4));
        selectedFunctionRv.addItemDecoration(functionConfigRvDecoration);
        selectedFunctionAdapter=new FunctionConfigAdapter(this,selectedList,Type.SELECTEDFUNCTIONTYPE);
        selectedFunctionRv.setAdapter(selectedFunctionAdapter);
        callback= new ItemDragCallback(selectedFunctionAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(selectedFunctionRv);
        editorTv=this.findViewById(R.id.function_config_editor_tv);
        editorTv.setOnClickListener(getController());
        getTitleLayout().setOnRightTextClickListener(getController());
        getController().initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       EventBus.getDefault().unregister(this);
    }

    @Override
    public void changEditStatus() {
        if(isEditing){
            isEditing=false;
            topLabelTv.setText(this.getString(R.string.function_config_label_top_base));
            hintTv.setVisibility(View.GONE);
            editorTv.setVisibility(View.VISIBLE);
            callback.changeState(false);
            allFunctionAdapter.ChangeState(false);
            selectedFunctionAdapter.ChangeState(false);
            EventBus.getDefault().post(new Event(EventType.UPDATEFUCTION,selectedList));
        }else {
            isEditing=true;
            callback.changeState(true);
            allFunctionAdapter.ChangeState(true);
            selectedFunctionAdapter.ChangeState(true);
            topLabelTv.setText(this.getString(R.string.function_config_label_top_editing));
            hintTv.setVisibility(View.VISIBLE);
            editorTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void fillFunctionData(List<IndexFunction> list,List<IndexFunction> selectedFunctionlist) {
        indexFunctionList.addAll(list);
        allFunctionAdapter.notifyDataSetChanged();
        selectedList.addAll(selectedFunctionlist);
        selectedFunctionAdapter.notifyDataSetChanged();
    }

    @Override
    public void localizationConfig() {
        String filename="index_function_"+Global.getUserId();
        String allFunctionFilename = "all_function_" + Global.getUserId();
        LocalizationUtils.fileSave2Local(getContext(),selectedList,filename);
        LocalizationUtils.fileSave2Local(getContext(),indexFunctionList,allFunctionFilename);
//        Global.setFuncitonList(indexFunctionList);
    }

    @Override
    public boolean isEditing() {
        return isEditing;
    }


}
