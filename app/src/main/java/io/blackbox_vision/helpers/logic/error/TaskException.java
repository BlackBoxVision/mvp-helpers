package io.blackbox_vision.helpers.logic.error;

import android.support.annotation.NonNull;


public final class TaskException extends Exception {
    public static final String EMPTY_LIST = "EMPTY TASK LIST";
    public static final String CANNOT_CREATE_TASK = "CANNOT CREATE TASK";
    public static final String CANNOT_UPDATE_TASK = "CANNOT UPDATE TASK";
    public static final String CANNOT_DELETE_ALL_TASKS = "CANNOT DELETE ALL TASKS";
    public static final String CANNOT_DELETE_TASK = "CANNOT DELETE TASK";
    public static final String TITLE_EMPTY_OR_NULL = "TITLE EMPTY OR NULL";
    public static final String DESCRIPTION_EMPTY_OR_NULL = "DESCRIPTION EMPTY OR NULL";
    public static final String START_DATE_EMPTY_OR_NULL = "START DATE EMPTY OR NULL";
    public static final String DUE_DATE_EMPTY_OR_NULL = "DUE DATE EMPTY OR NULL";

    public TaskException(@NonNull String message) {
        super(message);
    }
}
