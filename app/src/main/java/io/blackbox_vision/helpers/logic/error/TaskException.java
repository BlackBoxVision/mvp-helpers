package io.blackbox_vision.helpers.logic.error;

import android.support.annotation.NonNull;


public final class TaskException extends Exception {
    public static final String EMPTY_LIST = "EMPTY TASK LIST";

    public TaskException(@NonNull String message) {
        super(message);
    }
}
