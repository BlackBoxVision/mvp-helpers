package io.blackbox_vision.helpers.logic.view;

import android.support.annotation.NonNull;

import java.util.List;

import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.mvphelpers.logic.listener.OnErrorListener;
import io.blackbox_vision.mvphelpers.logic.view.BaseView;


public interface TaskListView extends BaseView, OnErrorListener<Throwable> {

    void onTaskListFetched(@NonNull List<Task> tasks);

    void onTasksRemoved();

    void onNewTaskRequest();

    void onTaskDetailRequest(@NonNull Long id);

    void showProgress();

    void hideProgress();

    void showTaskList();

    void hideTaskList();

    void showErrorView();

    void showEmptyView();
}
