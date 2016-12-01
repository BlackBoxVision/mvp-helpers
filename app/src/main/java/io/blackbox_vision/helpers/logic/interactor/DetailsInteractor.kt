package io.blackbox_vision.helpers.logic.interactor

import android.os.Bundle

import io.blackbox_vision.helpers.util.MockUtils
import io.blackbox_vision.mvphelpers.logic.interactor.BaseInteractor
import io.blackbox_vision.mvphelpers.logic.listener.OnErrorListener
import io.blackbox_vision.mvphelpers.logic.listener.OnSuccessListener

class DetailsInteractor : BaseInteractor() {

    fun retrieveDetailsFromService(id: String, successListener: OnSuccessListener<Bundle>, errorListener: OnErrorListener<String>) {
        runOnBackground(Runnable {
            //Getting data from somewhere
            val data = MockUtils.getMockedData(id)

            runOnBackground(Runnable {
                if (data != null) {
                    successListener.onSuccess(data)
                } else {
                    errorListener.onError("Ups, something went wrong")
                }
            })
        })
    }
}
