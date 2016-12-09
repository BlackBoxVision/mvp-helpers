package io.blackbox_vision.helpers.logic.view;

import android.support.annotation.NonNull;

import java.util.List;

import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.mvphelpers.logic.view.BaseView;


public interface ListView extends BaseView {

    void onTaskListFetched(@NonNull List<Task> tasks);

    void onTaskListError(@NonNull Throwable error);

    void onNewTaskRequest();

    void showProgress();

    void hideProgress();

    void showTaskList();

    void hideTaskList();

    void showErrorView();

    void hideErrorView();
}
