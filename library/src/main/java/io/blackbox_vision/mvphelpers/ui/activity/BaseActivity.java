package io.blackbox_vision.mvphelpers.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;
import io.blackbox_vision.mvphelpers.ui.loader.PresenterLoader;


import static android.support.v4.app.LoaderManager.LoaderCallbacks;


public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements LoaderCallbacks<P> {
    private static final int LOADER_ID = 201;

    protected Unbinder unbinder;

    @Nullable
    protected P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public Loader<P> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getApplicationContext(), createPresenterFactory());
    }

    @Override
    public void onLoadFinished(Loader<P> loader, P presenter) {
        this.presenter = presenter;
        onPresenterCreated(this.presenter);
    }

    @Override
    public void onLoaderReset(Loader<P> loader) {
        if (null != presenter) {
            presenter.detachView();
            presenter = null;
        }
    }

    @NonNull
    public abstract PresenterFactory<P> createPresenterFactory();

    @LayoutRes
    public abstract int getLayout();

    public abstract void onPresenterCreated(@NonNull P presenter);

    @Nullable
    public P getPresenter() {
        return presenter;
    }
}
