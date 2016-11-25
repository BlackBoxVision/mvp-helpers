package io.blackbox_vision.mvphelpers.logic.presenter;

import android.support.annotation.NonNull;

import io.blackbox_vision.mvphelpers.logic.view.BaseView;


public class BasePresenter<T extends BaseView> {
    private T view;

    protected boolean isViewAttached() {
        return this.view != null;
    }

    public void registerView(@NonNull T view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }

    public T getView() {
        return view;
    }
}
