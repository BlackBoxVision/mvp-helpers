package io.blackbox_vision.mvphelpers.ui.activity

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

import butterknife.ButterKnife
import butterknife.Unbinder
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter

abstract class BaseActivity<T : BasePresenter<*>> : AppCompatActivity() {
    protected lateinit var unbinder: Unbinder

    var presenter: T? = null
        protected set

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        unbinder = ButterKnife.bind(this)
        presenter = addPresenter()
        onPresenterCreated(presenter!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder.unbind()

        if (null != presenter) {
            presenter!!.detachView()
        }
    }

    abstract fun addPresenter(): T

    abstract val layout: Int

    abstract fun onPresenterCreated(presenter: T)
}
