package io.blackbox_vision.mvphelpers.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.content.Loader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter
import io.blackbox_vision.mvphelpers.logic.view.BaseView
import io.blackbox_vision.mvphelpers.ui.loader.PresenterLoader

import android.support.v4.app.LoaderManager.LoaderCallbacks


abstract class BaseFragment<P : BasePresenter<V>, V : BaseView> : Fragment() {

    protected var presenter: P? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loaderManager.initLoader(LOADER_ID, Bundle(), object : LoaderCallbacks<P> {
            override fun onCreateLoader(id: Int, args: Bundle): Loader<P> {
                return PresenterLoader.newInstance(context, createPresenterFactory())
            }

            override fun onLoadFinished(loader: Loader<P>, basePresenter: P) {
                val presenterView = presenterView
                presenter = basePresenter

                if (null != presenterView) {
                    presenter!!.attachView(presenterView)
                } else {
                    Log.d(TAG, "View can't be attached because you don't implement it in your fragment.")
                }

                onPresenterCreated(presenter!!, savedInstanceState)
            }

            override fun onLoaderReset(loader: Loader<P>) {
                if (isPresenterAvailable) {
                    presenter!!.detachView()
                    presenter = null
                }

                onPresenterDestroyed()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        onPresenterStateSave(presenter!!, outState!!)
        super.onSaveInstanceState(outState)
    }

    protected abstract fun createPresenterFactory(): PresenterFactory<P>

    @get:LayoutRes
    protected abstract val layout: Int

    protected abstract fun onPresenterCreated(presenter: P, savedInstanceState: Bundle?)

    protected abstract fun onPresenterStateSave(presenter: P, outState: Bundle)

    protected abstract fun onPresenterDestroyed()

    protected val isPresenterAvailable: Boolean
        get() = presenter != null

    protected val presenterView: V?
        get() {
            var view: V? = null

            try {
                view = this as V
            } catch (ex: ClassCastException) {
                Log.e(TAG, "You should implement your view class in the fragment.", ex.cause)
            }

            return view
        }

    protected val applicationContext: Context
        get() = context.applicationContext

    companion object {
        private val TAG = "BaseFragment"
        private val LOADER_ID = 201
    }
}
