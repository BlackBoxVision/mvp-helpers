package io.blackbox_vision.mvphelpers.logic.presenter

import io.blackbox_vision.mvphelpers.logic.view.BaseView


abstract class BasePresenter<V : BaseView> {
    var view: V? = null
        private set

    protected abstract fun onViewAttached(view: V)

    protected abstract fun onViewDetached()

    val isViewAttached: Boolean
        get() = this.view != null

    fun attachView(view: V) {
        this.view = view
        onViewAttached(view)
    }

    fun detachView() {
        this.view = null
        onViewDetached()
    }
}
