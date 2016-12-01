package io.blackbox_vision.helpers.logic.presenter

import android.os.Bundle

import io.blackbox_vision.helpers.logic.view.DetailsView
import io.blackbox_vision.helpers.logic.interactor.DetailsInteractor
import io.blackbox_vision.mvphelpers.logic.listener.OnErrorListener
import io.blackbox_vision.mvphelpers.logic.listener.OnSuccessListener
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter

class DetailsPresenter : BasePresenter<DetailsView>(), OnSuccessListener<Bundle>, OnErrorListener<String> {
    private val interactor: DetailsInteractor

    init {
        interactor = DetailsInteractor()
    }

    fun findRequiredInformation(id: String) {
        if (isViewAttached) {
            interactor.retrieveDetailsFromService(id, this, this)
        }
    }

    override fun onSuccess(data: Bundle) {
        if (isViewAttached) {
            view!!.onInfoReceived(data)
        }
    }

    override fun onError(error: String) {
        if (isViewAttached) {
            view!!.onInfoError(error)
        }
    }
}
