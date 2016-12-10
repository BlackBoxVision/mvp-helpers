package io.blackbox_vision.helpers.logic.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import io.blackbox_vision.helpers.logic.interactor.TaskListInteractor;
import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.helpers.logic.view.TaskListView;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;


public final class TaskListPresenter extends BasePresenter<TaskListView> {
    private static TaskListPresenter taskListPresenter = null;
    private TaskListInteractor taskListInteractor;

    private TaskListPresenter() { }

    @Override
    public void onViewAttached(@NonNull TaskListView view) {
        taskListInteractor = TaskListInteractor.newInstance();
    }

    @Override
    public void onViewDetached() {
        taskListInteractor = null;
    }

    public void getTasks() {
        if (isViewAttached()) {
            getView().hideTaskList();
            getView().showProgress();

            taskListInteractor.getTasks(this::onTaskListError, this::onTaskListFetched);
        }
    }

    public void newTask() {
        if (isViewAttached()) {
            getView().onNewTaskRequest();
        }
    }

    public void showTask(@NonNull Long id) {
        if (isViewAttached()) {
            getView().onTaskDetailRequest(id);
        }
    }

    private void onTaskListFetched(@NonNull List<Task> taskList) {
        if (isViewAttached()) {
            getView().hideProgress();
            getView().showTaskList();
            getView().onTaskListFetched(taskList);
        }
    }

    private void onTaskListError(@NonNull Throwable error) {
        if (isViewAttached()) {
            getView().hideProgress();
            getView().showErrorView();
            getView().onTaskListError(error);
        }
    }

    public static TaskListPresenter newInstance() {
        if (null == taskListPresenter) {
            taskListPresenter = new TaskListPresenter();
        }

        return taskListPresenter;
    }
}
