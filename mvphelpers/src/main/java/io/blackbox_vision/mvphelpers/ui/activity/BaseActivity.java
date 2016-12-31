package io.blackbox_vision.mvphelpers.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;
import io.blackbox_vision.mvphelpers.logic.view.BaseView;
import io.blackbox_vision.mvphelpers.ui.loader.PresenterLoader;

import static android.support.v4.app.LoaderManager.LoaderCallbacks;


public abstract class BaseActivity<P extends BasePresenter<V>, V extends BaseView> extends AppCompatActivity implements LoaderCallbacks<P> {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private static final int LOADER_ID = 201;

    protected P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<P> onCreateLoader(int id, Bundle args) {
        return PresenterLoader.newInstance(getApplicationContext(), createPresenterFactory());
    }

    @Override
    public void onLoadFinished(Loader<P> loader, P basePresenter) {
        presenter = basePresenter;

        if (null != getPresenterView()) {
            presenter.attachView(getPresenterView());
        } else {
            Log.d(TAG, "View can't be attached because you don't implement it in your activity.");
        }

        onPresenterCreated(this.presenter);
    }

    @Override
    public void onLoaderReset(Loader<P> loader) {
        if (isPresenterAvailable()) {
            presenter.detachView();
            presenter = null;
        }

        onPresenterDestroyed();
    }

    @NonNull
    protected abstract PresenterFactory<P> createPresenterFactory();

    @LayoutRes
    protected abstract int getLayout();

    protected abstract void onPresenterCreated(@NonNull P presenter);

    protected abstract void onPresenterDestroyed();

    protected P getPresenter() {
        return presenter;
    }

    protected boolean isPresenterAvailable() {
        return presenter != null;
    }

    protected V getPresenterView() {
        V view = null;

        try {
            view = (V) this;
        } catch (final ClassCastException ex) {
            Log.e(TAG, "You should implement your view class in the activity.", ex.getCause());
        }

        return view;
    }
}
