package com.keydom.ih_doctor.fragment.view;

import android.widget.RelativeLayout;

import com.keydom.ih_common.base.BaseView;
import com.keydom.ih_doctor.bean.HomeBean;
import com.keydom.ih_doctor.bean.HomeMsgBean;

/**
 * @Name：com.keydom.ih_doctor.fragment.view
 * @Description：
 * @Author：song
 * @Date：18/11/16 下午2:25
 * 修改人：xusong
 * 修改时间：18/11/16 下午2:25
 */
public interface WorkFragmentView extends BaseView {

    /**
     * 获取首页数据
     *
     * @param bean 首页对象
     */
    void getHomeDataSuccess(HomeBean bean);

    /**
     * 获取首页数据失败
     *
     * @param errMsg 失败提示信息
     */
    void getHomeDataFailed(String errMsg);

    /**
     * 获取标题栏布局
     *
     * @return
     */
    RelativeLayout getTitleLayout();

    String getUserIconStr();
    void getHomeCountMsgSuccess(HomeMsgBean bean);

}
