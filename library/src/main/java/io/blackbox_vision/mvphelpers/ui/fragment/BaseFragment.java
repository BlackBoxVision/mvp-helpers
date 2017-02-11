package io.blackbox_vision.mvphelpers.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;
import io.blackbox_vision.mvphelpers.logic.view.BaseView;
import io.blackbox_vision.mvphelpers.ui.loader.PresenterLoader;

import static android.support.v4.app.LoaderManager.LoaderCallbacks;


public abstract class BaseFragment<P extends BasePresenter<V>, V extends BaseView> extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    private static final int LOADER_ID = 201;

    protected P presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID, null, new LoaderCallbacks<P>() {
            @Override
            public Loader<P> onCreateLoader(int id, Bundle args) {
                return PresenterLoader.newInstance(getContext(), createPresenterFactory());
            }

            @Override
            public void onLoadFinished(Loader<P> loader, P basePresenter) {
                final V presenterView = getPresenterView();
                presenter = basePresenter;

                if (null != presenterView) {
                    presenter.attachView(presenterView);
                } else {
                    Log.d(TAG, "View can't be attached because you don't implement it in your fragment.");
                }

                onPresenterCreated(presenter, savedInstanceState);
            }

            @Override
            public void onLoaderReset(Loader<P> loader) {
                if (isPresenterAvailable()) {
                    presenter.detachView();
                    presenter = null;
                }

                onPresenterDestroyed();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        onPresenterStateSave(presenter, outState);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    protected abstract PresenterFactory<P> createPresenterFactory();

    @LayoutRes
    protected abstract int getLayout();

    protected abstract void onPresenterCreated(@NonNull final P presenter, @Nullable final Bundle savedInstanceState);

    protected abstract void onPresenterStateSave(@NonNull final P presenter, @NonNull final Bundle outState);

    protected abstract void onPresenterDestroyed();

    protected P getPresenter() {
        return presenter;
    }

    protected boolean isPresenterAvailable() {
        return presenter != null;
    }

    @SuppressWarnings("all")
    protected V getPresenterView() {
        V view = null;

        try {
            view = (V) this;
        } catch (final ClassCastException ex) {
            Log.e(TAG, "You should implement your view class in the fragment.", ex.getCause());
        }

        return view;
    }

    @NonNull
    protected Context getApplicationContext() {
        return getContext().getApplicationContext();
    }
}
