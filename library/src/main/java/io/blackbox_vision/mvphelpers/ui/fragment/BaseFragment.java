package io.blackbox_vision.mvphelpers.ui.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;
import io.blackbox_vision.mvphelpers.ui.loader.PresenterLoader;

import static android.support.v4.app.LoaderManager.LoaderCallbacks;


public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements LoaderCallbacks<P> {
    private static final int LOADER_ID = 201;

    protected Unbinder unbinder;

    @Nullable
    protected P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Loader<P> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getContext(), createPresenterFactory());
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
