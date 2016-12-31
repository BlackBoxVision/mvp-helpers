package io.blackbox_vision.mvphelpers.logic.factory;

import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;


public interface PresenterFactory<P extends BasePresenter> {

    P create();
}
