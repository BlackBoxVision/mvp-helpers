package io.blackbox_vision.helpers.logic.view;

import android.support.annotation.NonNull;

import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.mvphelpers.logic.listener.OnErrorListener;
import io.blackbox_vision.mvphelpers.logic.view.BaseView;


public interface AddTaskView extends BaseView, OnErrorListener<Throwable> {

    Task getTask();

    void updateViewByLaunchMode();

    void onTaskFound(@NonNull Task task);

    void onError(@NonNull Throwable error);

    void goBack();
}
