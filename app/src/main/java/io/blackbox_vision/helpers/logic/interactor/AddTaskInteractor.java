package io.blackbox_vision.helpers.logic.interactor;

import android.support.annotation.NonNull;

import io.blackbox_vision.helpers.logic.error.TaskException;
import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.mvphelpers.logic.interactor.BaseInteractor;
import io.blackbox_vision.mvphelpers.logic.listener.OnErrorListener;
import io.blackbox_vision.mvphelpers.logic.listener.OnSuccessListener;


@SuppressWarnings("all")
public final class AddTaskInteractor extends BaseInteractor {
    private static AddTaskInteractor addTaskInteractor = null;

    private AddTaskInteractor() { }

    public void addNewTask(@NonNull Task task,
                           @NonNull OnErrorListener<Throwable> errorListener,
                           @NonNull OnSuccessListener<Task> successListener) {
        runOnBackground(() -> {
            Long id = Task.save(task);
            task.setId(id);

            if (null != id) {
                runOnUiThread(() -> successListener.onSuccess(task));
            } else {
                runOnUiThread(() -> errorListener.onError(new TaskException(TaskException.CANNOT_CREATE_TASK)));
            }
        });
    }

    public void updateTask(@NonNull Task task,
                           @NonNull OnErrorListener<Throwable> errorListener,
                           @NonNull OnSuccessListener<Task> successListener) {
        runOnBackground(() -> {
            Long id = Task.update(task);
            task.setId(id);

            if (null != id) {
                runOnUiThread(() -> successListener.onSuccess(task));
            } else {
                runOnUiThread(() -> errorListener.onError(new TaskException(TaskException.CANNOT_CREATE_TASK)));
            }
        });
    }

    public void findTaskById(@NonNull Long id,
                             @NonNull OnErrorListener<Throwable> errorListener,
                             @NonNull OnSuccessListener<Task> successListener) {
        runOnBackground(() -> {
            Task task = Task.findById(Task.class, id);

            if (null == task.getTitle()) {
                task.setTitle("");
            }

            if (null == task.getDescription()) {
                task.setDescription("");
            }

            if (null != id) {
                runOnUiThread(() -> successListener.onSuccess(task));
            } else {
                runOnUiThread(() -> errorListener.onError(new TaskException(TaskException.CANNOT_UPDATE_TASK)));
            }
        });
    }

    public static AddTaskInteractor newInstance() {
        if (null == addTaskInteractor) {
            addTaskInteractor = new AddTaskInteractor();
        }

        return addTaskInteractor;
    }
}
