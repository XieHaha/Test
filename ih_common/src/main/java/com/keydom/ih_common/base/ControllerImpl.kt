package com.keydom.ih_common.base

import android.app.Dialog
import android.content.Context
import com.keydom.ih_common.utils.DialogCreator
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

/**
 * Created by Chao  2018/5/8 on 15:50
 * description Presenter基类
 */
@Suppress("UNCHECKED_CAST")
open class ControllerImpl<V : BaseView> : BaseController<V> {
    val DEFAULT_PAGE = 1
    lateinit var mContext: Context
    private lateinit var disposable: CompositeDisposable
    private lateinit var mView: WeakReference<BaseView>
    private var loading: Dialog? = null
    private var mCurrentPage = DEFAULT_PAGE

    fun currentPagePlus() {
        mCurrentPage++
    }

    fun setCurrentPage(currentPage: Int) {
        this.mCurrentPage = currentPage
    }

    fun setDefaultPage() {
        this.mCurrentPage = DEFAULT_PAGE;
    }

    fun getCurrentPage(): Int {
        return mCurrentPage
    }

    fun initLoading() {
        if (loading == null) {
            loading = DialogCreator.createLoadingDialog(getContext(), "请稍等")
        }

    }

    fun showLoading() {
        loading?.show()

    }

    fun hideLoading() {
        loading?.hide()
    }

    override fun getView(): V? = mView.get() as V?
    override fun getContext(): Context = mContext
    override fun getDisposable(): CompositeDisposable = disposable

    /**
     * 获取对象到Controller
     */
    override fun attachView(view: BaseView) {
        this.mView = WeakReference(view)
        this.mContext = view.getContext()
        this.disposable = view.getDisposables()
        initLoading()
    }

    /**
     * 销毁资源
     */
    override fun onDestroy() {
        disposable.dispose()
        disposable.clear()
        mView.clear()
        loading?.dismiss()
    }
}