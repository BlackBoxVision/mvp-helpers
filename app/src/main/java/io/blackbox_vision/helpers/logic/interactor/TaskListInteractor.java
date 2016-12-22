package io.blackbox_vision.helpers.logic.interactor;

import android.support.annotation.NonNull;

import java.util.List;

import io.blackbox_vision.helpers.logic.error.TaskException;
import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.mvphelpers.logic.interactor.BaseInteractor;
import io.blackbox_vision.mvphelpers.logic.listener.OnErrorListener;
import io.blackbox_vision.mvphelpers.logic.listener.OnSuccessListener;


public final class TaskListInteractor extends BaseInteractor {
    private static final Long DELAY_IN_MILLIS = 750L;
    private static TaskListInteractor taskListInteractor;

    private TaskListInteractor() { }

    public Long getCount() {
        return Task.count(Task.class);
    }

    public void getTasks(@NonNull OnErrorListener<Throwable> errorListener,
                         @NonNull OnSuccessListener<List<Task>> successListener) {
        runOnBackground(() -> {
            List<Task> tasks = Task.find(Task.class, null);

            if (!tasks.isEmpty()) {
                runOnUiThread(() -> successListener.onSuccess(tasks), DELAY_IN_MILLIS);
            } else {
                runOnUiThread(() -> errorListener.onError(new TaskException(TaskException.EMPTY_LIST)), DELAY_IN_MILLIS);
            }
        });
    }

    public void removeTasks(@NonNull OnErrorListener<Throwable> errorListener, @NonNull OnSuccessListener<Integer> successListener) {
        runOnBackground(() -> {
            int count = Task.deleteAll(Task.class);

            if (count > 0) {
                runOnUiThread(() -> successListener.onSuccess(count));
            } else {
                runOnUiThread(() -> errorListener.onError(new TaskException(TaskException.CANNOT_DELETE_ALL_TASKS)));
            }
        });
    }

    public static TaskListInteractor newInstance() {
        if (null == taskListInteractor) {
            taskListInteractor = new TaskListInteractor();
        }

        return taskListInteractor;
    }
}
