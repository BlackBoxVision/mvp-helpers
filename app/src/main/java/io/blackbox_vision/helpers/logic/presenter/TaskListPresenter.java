package io.blackbox_vision.helpers.logic.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import io.blackbox_vision.helpers.logic.interactor.TaskListInteractor;
import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.helpers.logic.presenter_view.TaskListView;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;


public final class TaskListPresenter extends BasePresenter<TaskListView> {
    private static TaskListPresenter taskListPresenter = null;
    private TaskListInteractor taskListInteractor;

    private TaskListPresenter() { }

    @Override
    protected void onViewAttached(@NonNull TaskListView view) {
        taskListInteractor = TaskListInteractor.newInstance();
    }

    @Override
    protected void onViewDetached() {
        taskListInteractor = null;
    }

    public Long getTasksCount() {
        return isViewAttached()? taskListInteractor.getCount() : 0L;
    }

    public void getTasks() {
        if (isViewAttached()) {
            getView().hideTaskList();
            getView().hideErrorView();
            getView().showProgress();

            taskListInteractor.getTasks(this::onTaskListError, this::onTaskListFetched);
        }
    }

    public void removeAllTasks() {
        if (isViewAttached()) {
            getView().hideTaskList();
            getView().showProgress();

            taskListInteractor.removeTasks(this::onTasksNotRemoved, this::onTasksRemoved);
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

    private void onTasksRemoved(@NonNull Integer count) {
        if (isViewAttached()) {
            getView().hideProgress();
            getView().showErrorView();
            getView().onTasksRemoved();
        }
    }

    private void onTasksNotRemoved(@NonNull Throwable error) {
        if (isViewAttached()) {
            getView().hideProgress();
            getView().onError(error);
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
            getView().onError(error);
        }
    }

    public void showEmptyView() {
        if (isViewAttached()) {
            getView().showEmptyView();
        }
    }

    public static TaskListPresenter newInstance() {
        if (null == taskListPresenter) {
            taskListPresenter = new TaskListPresenter();
        }

        return taskListPresenter;
    }
}
