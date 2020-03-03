package com.keydom.ih_common.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.ganxin.library.LoadDataLayout
import com.keydom.ih_common.R

abstract class BaseFragment : Fragment(), BaseFragmentInterFace {
    private var loadDataLayout: LoadDataLayout? = null
    protected var isViewInitiated: Boolean = false
    protected var isVisibleToUser: Boolean = false
    protected var isDataInitiated: Boolean = false
    /**
     * 注解
     */
    protected var unbinder: Unbinder? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(layoutRes, container, false)
        getView(inflate)

        unbinder = ButterKnife.bind(this, inflate)
        isViewInitiated = true;
        loadDataLayout = inflate.findViewById<View>(R.id.loadDataLayout) as LoadDataLayout?
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initController()
        initData(savedInstanceState)

        if (isVisibleToUser) {
            if (!isDataInitiated && isViewInitiated) {
                lazyLoad()
                isDataInitiated = true
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            if (!isDataInitiated && isViewInitiated) {
                lazyLoad()
                isDataInitiated = true
            }
        }
    }

    fun pageLoading() {
        loadDataLayout?.let { it.setStatus(LoadDataLayout.LOADING) }
    }

    fun pageLoadingSuccess() {
        loadDataLayout?.let { it.setStatus(LoadDataLayout.SUCCESS) }
    }

    fun pageLoadingFail() {
        loadDataLayout?.let { it.setStatus(LoadDataLayout.ERROR) }

    }

    fun pageEmpty() {
        loadDataLayout?.let { it.setStatus(LoadDataLayout.EMPTY) }
    }

    fun noNetWork() {
        loadDataLayout?.let { it.setStatus(LoadDataLayout.NO_NETWORK) }
    }


    fun setReloadListener(listener: LoadDataLayout.OnReloadListener) {
        loadDataLayout?.let { it.setOnReloadListener(listener) }

    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder?.unbind()
    }

}