package io.blackbox_vision.mvphelpers.logic.factory

import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter


interface PresenterFactory<P : BasePresenter<*>> {

    fun create(): P
}
