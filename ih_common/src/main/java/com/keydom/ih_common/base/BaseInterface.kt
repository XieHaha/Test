package com.keydom.ih_common.base

import android.os.Bundle
import android.support.annotation.RestrictTo
import android.view.View

/**
 * Created by Chao  2018/5/8 on 15:53
 * description Activity/Fragment基类接口
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
interface BaseInterface {
    /**
     * 布局
     */
    val layoutRes: Int

    /**
     * 逻辑
     */
    fun initController() {}

}

/**
 * Activity基类接口
 */
interface BaseActivityInterFace : BaseInterface {
    /**
     * setContentView之前调用
     */
    fun beforeInit() {}

    /**
     * 初始化
     */
    fun initData(savedInstanceState: Bundle?) {}

}

/**
 *  Fragment基类接口
 */
interface BaseFragmentInterFace : BaseInterface {
    /**
     * 初始化
     */
    fun initData(savedInstanceState: Bundle?) {}

    fun getView(view: View?){}

    fun lazyLoad(){}
}