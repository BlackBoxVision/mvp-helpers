package io.blackbox_vision.mvphelpers.ui.activity

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.util.Log

import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter
import io.blackbox_vision.mvphelpers.logic.view.BaseView
import io.blackbox_vision.mvphelpers.ui.loader.PresenterLoader

import android.support.v4.app.LoaderManager.LoaderCallbacks


abstract class BaseActivity<P : BasePresenter<V>, V : BaseView> : AppCompatActivity() {

    protected var presenter: P? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)

        supportLoaderManager.initLoader(LOADER_ID, null, object : LoaderCallbacks<P> {
            override fun onCreateLoader(id: Int, args: Bundle): Loader<P> {
                return PresenterLoader.newInstance(applicationContext, createPresenterFactory())
            }

            override fun onLoadFinished(loader: Loader<P>, basePresenter: P) {
                val presenterView = presenterView
                presenter = basePresenter

                if (null != presenterView) {
                    presenter!!.attachView(presenterView)
                } else {
                    Log.d(TAG, "View can't be attached because you don't implement it in your activity.")
                }

                onPresenterCreated(presenter!!)
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

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        onPresenterStateRestore(presenter!!, savedInstanceState)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        onPresenterStateSave(presenter!!, outState)
        super.onSaveInstanceState(outState)
    }

    protected abstract fun createPresenterFactory(): PresenterFactory<P>

    @get:LayoutRes
    protected abstract val layout: Int

    protected abstract fun onPresenterCreated(presenter: P)

    protected abstract fun onPresenterStateRestore(presenter: P, savedInstanceState: Bundle?)

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
                Log.e(TAG, "You should implement your view class in the activity.", ex.cause)
            }

            return view
        }

    companion object {
        private val TAG = "BaseActivity"
        private val LOADER_ID = 201
    }
}
