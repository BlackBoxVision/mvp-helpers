package io.blackbox_vision.mvphelpers.logic.presenter;

import android.support.annotation.NonNull;

import io.blackbox_vision.mvphelpers.logic.view.BaseView;


public abstract class BasePresenter<T extends BaseView> {
    private T view;

    public abstract void onViewAttached(@NonNull T view);

    public abstract void onViewDetached(@NonNull T view);

    protected boolean isViewAttached() {
        return this.view != null;
    }

    public void attachView(@NonNull T view) {
        this.view = view;
        onViewAttached(view);
    }

    public void detachView() {
        onViewDetached(this.view);
        this.view = null;
    }

    public T getView() {
        return view;
    }
}
