package io.blackbox_vision.mvphelpers.ui.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import butterknife.ButterKnife
import butterknife.Unbinder
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter


abstract class BaseFragment<T : BasePresenter<*>> : Fragment() {
    protected lateinit var unbinder: Unbinder

    var presenter: T? = null
        protected set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = addPresenter()
        onPresenterCreated(presenter!!)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(layout, container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()

        if (null != presenter) {
            presenter!!.detachView()
        }
    }

    abstract fun addPresenter(): T

    abstract val layout: Int

    abstract fun onPresenterCreated(presenter: T)
}
