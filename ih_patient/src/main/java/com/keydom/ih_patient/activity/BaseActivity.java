package com.keydom.ih_patient.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.keydom.ih_common.view.IhTitleLayout;
import com.keydom.ih_patient.R;
import com.keydom.ih_patient.utils.StatusBarUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * @Name：yxyz
 * @Description：Activity基础类，这里负责初始化、权限申请、验证、提示等操作
 * @Author：song
 * @Date：18/11/2 上午11:47
 * 修改人：song
 * 修改时间：18/11/2 上午11:47
 * 修改备注：
 */
abstract public class BaseActivity extends AppCompatActivity {

    /**
     * 加载布局ID
     * @return layoutId
     */
    protected abstract int getLayoutRes();
    private IhTitleLayout titleLayout;
    private Map<Integer,View> ViewMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        ViewMap=new HashMap<>();
        initView();
    }

    /**
     * 初始化控件
     */
    protected void initView(){

        titleLayout=(IhTitleLayout)this.findViewById(R.id.toolBar);
        StatusBarUtils.setWindowStatusBarColor(this,R.color.primary_color);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initTitle();
    }

    /**
     * 绑定控件
     */
    public View BindView(int ViewID, View view){
        view=findViewById(ViewID);
        ViewMap.put(ViewID,view);
        return view;
    }

    /**
     * 初始化标题
     */
    public void initTitle(){
        titleLayout.initViewsVisible(true,true,false);
        titleLayout.setRightTitle("跳过");
        titleLayout.setOnLeftButtonClickListener(new IhTitleLayout.OnLeftButtonClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                BaseActivity.this.finish();
            }
        });
    }

    public IhTitleLayout getTitleLayout() {
        return titleLayout;
    }
    public Map<Integer,View> getViewMap(){return ViewMap;}
    @Override
    public void setTitle(CharSequence title) {
        titleLayout.setAppTitle(title.toString());
    }

}
