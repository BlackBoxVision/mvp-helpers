package io.blackbox_vision.helpers.logic.view

import android.os.Bundle

import io.blackbox_vision.mvphelpers.logic.view.BaseView

interface DetailsView : BaseView {

    fun onInfoReceived(information: Bundle)

    fun onInfoError(errorMessage: String)
}
