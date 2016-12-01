package io.blackbox_vision.mvphelpers.logic.listener


interface OnErrorListener<in T> {

    fun onError(error: T)
}
