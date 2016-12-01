package io.blackbox_vision.mvphelpers.logic.presenter

import io.blackbox_vision.mvphelpers.logic.view.BaseView


open class BasePresenter<T : BaseView> {
    var view: T? = null
        private set

    protected val isViewAttached: Boolean
        get() = this.view != null

    fun attachView(view: T) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}
