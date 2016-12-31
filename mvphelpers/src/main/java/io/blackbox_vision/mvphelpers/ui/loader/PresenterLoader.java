package io.blackbox_vision.mvphelpers.ui.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;

import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;


public final class PresenterLoader<P extends BasePresenter> extends Loader<P> {

    @NonNull
    private final PresenterFactory<P> factory;

    @Nullable
    private P presenter;

    protected PresenterLoader(@NonNull Context context, @NonNull PresenterFactory<P> factory) {
        super(context);
        this.factory = factory;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (null != presenter) {
            deliverResult(presenter);
        }

        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        presenter = factory.create();

        deliverResult(presenter);
    }

    @Override
    protected void onReset() {
        super.onReset();

        if (null != presenter) {
            presenter.detachView();
            presenter = null;
        }
    }

    public static <P extends BasePresenter> PresenterLoader<P> newInstance(@NonNull Context context, @NonNull PresenterFactory<P> factory) {
        return new PresenterLoader<>(context, factory);
    }
}
