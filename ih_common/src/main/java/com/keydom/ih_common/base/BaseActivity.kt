package com.keydom.ih_common.base

import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.ButterKnife
import com.ganxin.library.LoadDataLayout
import com.keydom.ih_common.R
import com.keydom.ih_common.utils.StatusBarUtils
import com.keydom.ih_common.view.IhTitleLayout
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Chao  2018/5/8 on 15:51
 * description Activity基类
 */
abstract class BaseActivity : AppCompatActivity(), BaseActivityInterFace {
    private var titleLayout: IhTitleLayout? = null
    private var loadDataLayout: LoadDataLayout? = null
    private val disposable: CompositeDisposable = CompositeDisposable()


    public fun getDisposable(): CompositeDisposable = disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        beforeInit()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        ButterKnife.bind(this)
        initTitle()
        loadDataLayout = this.findViewById<View>(R.id.loadDataLayout) as LoadDataLayout?
        initController()
        initData(savedInstanceState)

    }

    private fun initTitle() {
        titleLayout = this.findViewById<View>(R.id.toolBar) as IhTitleLayout?
        StatusBarUtils.setWindowStatusBarColor(this, R.color.fontColorForeground)
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        titleLayout?.let { it ->
            it.initViewsVisible(true, true, false)
            it.setOnLeftButtonClickListener {
                finish()
            }
        }
    }

    /**
     * 加载中
     */
    fun pageLoading() {
        loadDataLayout?.status = LoadDataLayout.LOADING
    }

    /**
     * 加载成功
     */
    fun pageLoadingSuccess() {
        loadDataLayout?.status = LoadDataLayout.SUCCESS
    }

    /**
     * 加载失败
     */
    fun pageLoadingFail() {
        loadDataLayout?.status = LoadDataLayout.ERROR

    }

    /**
     * 加载数据为空
     */
    fun pageEmpty() {
        loadDataLayout?.status = LoadDataLayout.EMPTY
    }

    fun searchEmpty() {
        loadDataLayout?.status = LoadDataLayout.EMPTY
        loadDataLayout?.setEmptyText("无搜索结果")
        loadDataLayout?.setReloadBtnVisible(false)
    }

    /**
     * 无网络
     */
    fun noNetWork() {
        loadDataLayout?.status = LoadDataLayout.NO_NETWORK
    }


    /**
     * 设置重新加载事件监听
     */
    fun setReloadListener(listener: LoadDataLayout.OnReloadListener) {
        loadDataLayout?.setOnReloadListener(listener)

    }


    /**
     * 设置标题栏右侧图片（如果需要）
     */
    fun setRightImg(drawable: Drawable) {
        titleLayout?.setRightImg(drawable)
    }

    /**
     * 设置标题栏右侧按钮文字
     */
    fun setRightTxt(title: String) {
        titleLayout?.setRightTitle(title)
    }

    fun setRightColor(color: Int) {
        titleLayout?.setRightTitleColor(color)
    }

    /**
     * 设置标题栏右侧监听
     */
    fun setRightBtnListener(listener: IhTitleLayout.OnRightTextClickListener) {
        titleLayout?.setOnRightTextClickListener(listener)
    }

    /**
     * 设置标题栏右侧图片监听
     */
    fun setRightImgListener(listener: IhTitleLayout.OnRightImgClickListener) {
        titleLayout?.setOnRightImgClickListener(listener)
    }

    /**
     * 设置是否显示标题栏右侧按钮
     */
    fun setRightImgVisibility(visibility: Boolean) {
        titleLayout?.hideRightLl(visibility)
    }

    /**
     * 设置是否显示左侧
     */
    fun setLeftBtnVisibility(visibility: Boolean) {
        titleLayout?.hideLeftLl(visibility)
    }

    /**
     * 设置标题栏左侧按钮监听
     */
    fun setLeftBtnListener(listener: IhTitleLayout.OnLeftButtonClickListener) {
        titleLayout?.setOnLeftButtonClickListener(listener)
    }

    /**
     * 设置标题
     */
    override fun setTitle(title: CharSequence) {
        titleLayout?.setAppTitle(title.toString())
    }


    /**
     * 设置标白色
     */
    fun setWhiteBar() {
        titleLayout?.setWhiteBar()
    }

    /**
     * 获取标题栏对象
     */
    fun getTitleLayout(): IhTitleLayout? {
        return titleLayout
    }


}