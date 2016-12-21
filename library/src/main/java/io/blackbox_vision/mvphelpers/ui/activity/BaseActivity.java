package io.blackbox_vision.mvphelpers.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;
import io.blackbox_vision.mvphelpers.logic.view.BaseView;
import io.blackbox_vision.mvphelpers.ui.loader.PresenterLoader;

import static android.support.v4.app.LoaderManager.LoaderCallbacks;


public abstract class BaseActivity<P extends BasePresenter<V>, V extends BaseView> extends AppCompatActivity
        implements LoaderCallbacks<P> {

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
        return new PresenterLoader<>(getApplicationContext(), createPresenterFactory());
    }

    @Override
    public void onLoadFinished(Loader<P> loader, P basePresenter) {
        presenter = basePresenter;
        presenter.attachView(getPresenterView());
        onPresenterCreated(this.presenter);
    }

    @Override
    public void onLoaderReset(Loader<P> loader) {
        if (null != presenter) {
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
        return getPresenter() != null;
    }

    protected V getPresenterView() {
        return (V) this;
    }
}
