package io.blackbox_vision.helpers.logic.interactor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import io.blackbox_vision.helpers.helper.DateUtils;
import io.blackbox_vision.helpers.logic.error.TaskException;
import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.mvphelpers.logic.interactor.BaseInteractor;
import io.blackbox_vision.mvphelpers.logic.listener.OnErrorListener;
import io.blackbox_vision.mvphelpers.logic.listener.OnSuccessListener;


@SuppressWarnings("all")
public final class AddTaskInteractor extends BaseInteractor {
    private static AddTaskInteractor addTaskInteractor = null;

    private AddTaskInteractor() { }

    public void addNewTask(@NonNull String title,
                           @NonNull String description,
                           @NonNull String startDate,
                           @NonNull String dueDate,
                           @NonNull OnErrorListener<Throwable> errorListener,
                           @NonNull OnSuccessListener<Task> successListener) {
        runOnBackground(() -> {
            boolean error = validate(title, description, startDate, dueDate, errorListener);

            if (!error) {
                Task task = new Task()
                        .setTitle(title)
                        .setDescription(description)
                        .setStartDate(DateUtils.fromString(startDate, "/"))
                        .setDueDate(DateUtils.fromString(dueDate, "/"))
                        .setCompleted(false);

                Long id = Task.save(task);
                task.setId(id);

                if (null != id) {
                    runOnUiThread(() -> successListener.onSuccess(task));
                }
                else {
                    runOnUiThread(() -> errorListener.onError(new TaskException(TaskException.CANNOT_CREATE_TASK)));
                }
            }
        });
    }

    public void updateTask(@NonNull Long taskId,
                           @NonNull String title,
                           @NonNull String description,
                           @NonNull String startDate,
                           @NonNull String dueDate,
                           @NonNull OnErrorListener<Throwable> errorListener,
                           @NonNull OnSuccessListener<Task> successListener) {
        runOnBackground(() -> {
            boolean error = validate(title, description, startDate, dueDate, errorListener);

            if (!error) {
                Task task = new Task()
                        .setTitle(title)
                        .setDescription(description)
                        .setStartDate(DateUtils.fromString(startDate, "/"))
                        .setDueDate(DateUtils.fromString(dueDate, "/"))
                        .setCompleted(false);

                if (taskId != -1L) {
                    task.setId(taskId);
                }

                Long id = Task.update(task);
                task.setId(id);

                if (null != id) {
                    runOnUiThread(() -> successListener.onSuccess(task));
                }
                else {
                    runOnUiThread(() -> errorListener.onError(new TaskException(TaskException.CANNOT_CREATE_TASK)));
                }
            }
        });
    }

    public void findTaskById(@NonNull Long taskId,
                             @NonNull OnErrorListener<Throwable> errorListener,
                             @NonNull OnSuccessListener<Task> successListener) {
        runOnBackground(() -> {
            Task task = Task.findById(Task.class, taskId);

            if (null != taskId) {
                runOnUiThread(() -> successListener.onSuccess(task));
            }
            else {
                runOnUiThread(() -> errorListener.onError(new TaskException(TaskException.CANNOT_UPDATE_TASK)));
            }
        });
    }

    public void removeTask(@NonNull Task task,
                           @NonNull OnErrorListener<Throwable> errorListener,
                           @NonNull OnSuccessListener<Boolean> successListener) {
        runOnBackground(() -> {
            boolean deleted = Task.delete(task);

            if (deleted) {
                runOnUiThread(() -> successListener.onSuccess(deleted));
            }
            else {
                runOnUiThread(() -> errorListener.onError(new TaskException(TaskException.CANNOT_DELETE_TASK)));
            }
        });
    }

    private boolean validate(@NonNull String title,
                             @NonNull String description,
                             @NonNull String startDate,
                             @NonNull String dueDate,
                             @NonNull OnErrorListener<Throwable> errorListener) {
        boolean error = false;

        if (TextUtils.isEmpty(title)) {
            runOnUiThread(() -> errorListener.onError(new TaskException(TaskException.TITLE_EMPTY_OR_NULL)));
            error = true;
        }
        else if (TextUtils.isEmpty(description)) {
            runOnUiThread(() -> errorListener.onError(new TaskException(TaskException.DESCRIPTION_EMPTY_OR_NULL)));
            error = true;
        }
        else if (TextUtils.isEmpty(startDate)) {
            runOnUiThread(() -> errorListener.onError(new TaskException(TaskException.START_DATE_EMPTY_OR_NULL)));
            error = true;
        }
        else if (TextUtils.isEmpty(dueDate)) {
            runOnUiThread(() -> errorListener.onError(new TaskException(TaskException.DUE_DATE_EMPTY_OR_NULL)));
            error = true;
        }

        return error;
    }

    public static AddTaskInteractor newInstance() {
        if (null == addTaskInteractor) {
            addTaskInteractor = new AddTaskInteractor();
        }

        return addTaskInteractor;
    }
}
