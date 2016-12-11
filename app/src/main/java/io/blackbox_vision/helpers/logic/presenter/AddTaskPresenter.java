package io.blackbox_vision.helpers.logic.presenter;

import android.support.annotation.NonNull;

import io.blackbox_vision.helpers.logic.model.Task;
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

    public void findTaskById(@NonNull Long taskId) {
        if (isViewAttached()) {
            addTaskInteractor.findTaskById(taskId, this::onError, this::onTaskFound);
        }
    }

    public void addNewTask() {
        if (isViewAttached()) {
            final Task task = getView().getTask();

            addTaskInteractor.addNewTask(task, this::onError, this::onTaskCreated);
        }
    }

    public void updateTask() {
        if (isViewAttached()) {
            final Task task = getView().getTask();

            addTaskInteractor.updateTask(task, this::onError, this::onTaskUpdated);
        }
    }

    private void onTaskUpdated(@NonNull Task task) {
        if (isViewAttached()) {
            getView().onTaskUpdated(task);
        }
    }

    private void onTaskCreated(@NonNull Task task) {
        if (isViewAttached()) {
            getView().onTaskCreated(task);
        }
    }

    private void onTaskFound(@NonNull Task task) {
        if (isViewAttached()) {
            getView().onTaskFound(task);
        }
    }

    private void onError(@NonNull Throwable error) {
        if (isViewAttached()) {
            getView().onError(error);
        }
    }

    public static AddTaskPresenter newInstance() {
        if (null == addTaskPresenter) {
            addTaskPresenter = new AddTaskPresenter();
        }

        return addTaskPresenter;
    }
}
