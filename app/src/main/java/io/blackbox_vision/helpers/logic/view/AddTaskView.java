package io.blackbox_vision.helpers.logic.view;

import android.support.annotation.NonNull;

import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.mvphelpers.logic.view.BaseView;


public interface AddTaskView extends BaseView {

    Task getTask();

    void onTaskCreated(@NonNull Task task);

    void onTaskUpdated(@NonNull Task task);

    void onTaskFound(@NonNull Task task);

    void onError(@NonNull Throwable error);
}
