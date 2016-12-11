package io.blackbox_vision.helpers.logic.error;

import android.support.annotation.NonNull;


public final class TaskException extends Exception {
    public static final String EMPTY_LIST = "EMPTY TASK LIST";
    public static final String CANNOT_CREATE_TASK = "CANNOT CREATE TASK";
    public static final String CANNOT_UPDATE_TASK = "CANNOT UPDATE TASK";
    public static final String CANNOT_DELETE_ALL_TASKS = "CANNOT DELETE ALL TASKS";

    public TaskException(@NonNull String message) {
        super(message);
    }
}
