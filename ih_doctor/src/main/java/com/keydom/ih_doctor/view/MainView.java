package com.keydom.ih_doctor.view;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.keydom.ih_common.view.ScrollControlViewPager;
import com.keydom.ih_doctor.R;

/**
 * @link Author: song
 * Create: 18/11/5 下午5:14
 * Changes (from 18/11/5)
 * 18/11/5 : Create MainView.java (song);
 */
public class MainView extends RelativeLayout {


    private Button[] mBtnList;
    private int[] mBtnListID;
    private ScrollControlViewPager mViewContainer;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 初始化底部tab
     */
    public void initModule() {
        mBtnListID = new int[]{
                R.id.tab_work, R.id.tab_patient_manage, R.id.tab_community, R.id.tab_personal_center
        };
        mBtnList = new Button[4];
        for (int i = 0; i < 4; i++) {
            mBtnList[i] = (Button) findViewById(mBtnListID[i]);
        }
        mViewContainer = (ScrollControlViewPager) findViewById(R.id.viewpager);
        mBtnList[0].setTextColor(getResources().getColor(R.color.login_btn_color));
        mBtnList[0].setSelected(true);
    }

    /**
     * 设置底部tab事件监听
     *
     * @param onclickListener
     */
    public void setOnClickListener(OnClickListener onclickListener) {
        for (int i = 0; i < mBtnListID.length; i++) {
            mBtnList[i].setOnClickListener(onclickListener);
        }
    }

    /**
     * 设置页面切换监听器
     *
     * @param onPageChangeListener
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mViewContainer.addOnPageChangeListener(onPageChangeListener);
    }

    /**
     * 设置viewPager适配器
     *
     * @param adapter
     */
    public void setViewPagerAdapter(FragmentPagerAdapter adapter) {
        mViewContainer.setAdapter(adapter);
    }

    /**
     * 切换fragment页面
     *
     * @param index
     * @param scroll
     */
    public void setCurrentItem(int index, boolean scroll) {
        mViewContainer.setCurrentItem(index, scroll);
    }


    /**
     * 设置底部tab的按钮状态<br/>
     * 更换颜色<br/>
     *
     * @param index
     */
    public void setButtonColor(int index) {
        for (int i = 0; i < 4; i++) {
            if (index == i) {
                mBtnList[i].setSelected(true);
                mBtnList[i].setTextColor(getResources().getColor(R.color.login_btn_color));
            } else {
                mBtnList[i].setSelected(false);
                mBtnList[i].setTextColor(getResources().getColor(R.color.action_bar_txt_color));
            }
        }
    }

}
