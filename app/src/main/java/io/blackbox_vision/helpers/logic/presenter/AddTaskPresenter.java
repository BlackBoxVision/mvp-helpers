package io.blackbox_vision.helpers.logic.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import io.blackbox_vision.helpers.logic.view.AddTaskView;
import io.blackbox_vision.helpers.logic.interactor.AddTaskInteractor;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;


public final class AddTaskPresenter extends BasePresenter<AddTaskView> {
    private static AddTaskPresenter addTaskPresenter = null;
    private AddTaskInteractor addTaskInteractor;

    private AddTaskPresenter() { }

    @Override
    public void onViewAttached(@NonNull AddTaskView view) {
        addTaskInteractor = AddTaskInteractor.newInstance();
    }

    @Override
    public void onViewDetached() {
        addTaskInteractor = null;
    }

    public void findRequiredInformation(@NonNull String id) {
        if (isViewAttached()) {
            addTaskInteractor.retrieveDetailsFromService(id, this::onSuccess, this::onError);
        }
    }

    private void onSuccess(@NonNull Bundle information) {
        if (isViewAttached()) {
            getView().onInfoReceived(information);
        }
    }

    private void onError(@NonNull String errorMessage) {
        if (isViewAttached()) {
            getView().onInfoError(errorMessage);
        }
    }

    public static AddTaskPresenter newInstance() {
        if (null == addTaskPresenter) {
            addTaskPresenter = new AddTaskPresenter();
        }

        return addTaskPresenter;
    }
}
