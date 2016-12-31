package io.blackbox_vision.helpers.logic.view;

import android.support.annotation.NonNull;

import java.util.Calendar;

import io.blackbox_vision.mvphelpers.logic.listener.OnErrorListener;
import io.blackbox_vision.mvphelpers.logic.view.BaseView;


public interface AddTaskView extends BaseView, OnErrorListener<Throwable> {

    void onLaunchMode();

    void onError(@NonNull Throwable error);

    void goBack();

    Long getTaskId();

    String getTitle();

    void setTitle(@NonNull String title);

    String getDescription();

    void setDescription(@NonNull String description);

    String getStartDate();

    void setStartDate(@NonNull String formattedDate, @NonNull Calendar date);

    String getDueDate();

    void setDueDate(@NonNull String formattedDate, @NonNull Calendar dueDate);
}
