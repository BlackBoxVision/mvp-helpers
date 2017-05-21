package io.blackbox_vision.mvphelpers.ui.view

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter


abstract class BaseFrameLayout<T : BasePresenter<*>> : FrameLayout {

    var presenter: T? = null
        private set

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter = addPresenter()
        onPresenterCreated(presenter!!)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        if (null != presenter) {
            presenter!!.detachView()
        }
    }

    abstract fun addPresenter(): T

    abstract fun onPresenterCreated(presenter: T)
}
