package io.blackbox_vision.mvphelpers.ui.loader

import android.content.Context
import android.support.v4.content.Loader

import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter


class PresenterLoader<P : BasePresenter<*>> constructor(context: Context, private val factory: PresenterFactory<P>) : Loader<P>(context) {

    private var presenter: P? = null

    override fun onStartLoading() {
        super.onStartLoading()

        if (null != presenter) {
            deliverResult(presenter)
        }

        forceLoad()
    }

    override fun onForceLoad() {
        super.onForceLoad()
        presenter = factory.create()

        deliverResult(presenter)
    }

    override fun onReset() {
        super.onReset()

        if (null != presenter) {
            presenter!!.detachView()
            presenter = null
        }
    }

    companion object {
        fun <P : BasePresenter<*>> newInstance(context: Context, factory: PresenterFactory<P>): PresenterLoader<P> {
            return PresenterLoader(context, factory)
        }
    }
}
