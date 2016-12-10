package io.blackbox_vision.helpers.logic.presenter;

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

    public static AddTaskPresenter newInstance() {
        if (null == addTaskPresenter) {
            addTaskPresenter = new AddTaskPresenter();
        }

        return addTaskPresenter;
    }
}
