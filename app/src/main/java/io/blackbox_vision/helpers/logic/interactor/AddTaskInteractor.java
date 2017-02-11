package io.blackbox_vision.helpers.logic.interactor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import io.blackbox_vision.helpers.logic.error.TaskException;
import io.blackbox_vision.helpers.data.Task;
import io.blackbox_vision.mvphelpers.logic.interactor.BaseInteractor;
import io.blackbox_vision.mvphelpers.logic.listener.OnErrorListener;
import io.blackbox_vision.mvphelpers.logic.listener.OnSuccessListener;


public final class AddTaskInteractor extends BaseInteractor {
    private static AddTaskInteractor addTaskInteractor = null;

    private AddTaskInteractor() { }

    public void addNewTask(@NonNull Task task,
                           @NonNull OnErrorListener<Throwable> error,
                           @NonNull OnSuccessListener<Task> success) {
        runOnBackground(() -> {
            boolean hasError = validate(task, error);

            if (!hasError) {
                Long id = Task.save(task);
                task.setId(id);

                if (-1L != id) {
                    runOnUiThread(() -> success.onSuccess(task));
                }
                else {
                    runOnUiThread(() -> error.onError(new TaskException(TaskException.CANNOT_CREATE_TASK)));
                }
            }
        });
    }

    public void updateTask(@NonNull Task task,
                           @NonNull OnErrorListener<Throwable> error,
                           @NonNull OnSuccessListener<Task> success) {
        runOnBackground(() -> {
            boolean hasError = validate(task, error);

            if (!hasError) {
                Long id = Task.update(task);
                task.setId(id);

                if (-1L != id) {
                    runOnUiThread(() -> success.onSuccess(task));
                }
                else {
                    runOnUiThread(() -> error.onError(new TaskException(TaskException.CANNOT_CREATE_TASK)));
                }
            }
        });
    }

    public void findTaskById(@NonNull Long taskId,
                             @NonNull OnErrorListener<Throwable> error,
                             @NonNull OnSuccessListener<Task> success) {
        runOnBackground(() -> {
            Task task = Task.findById(Task.class, taskId);

            if (-1L != taskId) {
                runOnUiThread(() -> success.onSuccess(task));
            }
            else {
                runOnUiThread(() -> error.onError(new TaskException(TaskException.CANNOT_UPDATE_TASK)));
            }
        });
    }

    public void removeTask(@NonNull Task task,
                           @NonNull OnErrorListener<Throwable> error,
                           @NonNull OnSuccessListener<Boolean> success) {
        runOnBackground(() -> {
            boolean isDeleted = Task.delete(task);

            if (isDeleted) {
                runOnUiThread(() -> success.onSuccess(true));
            }
            else {
                runOnUiThread(() -> error.onError(new TaskException(TaskException.CANNOT_DELETE_TASK)));
            }
        });
    }

    private boolean validate(@NonNull Task task, @NonNull OnErrorListener<Throwable> error) {
        if (TextUtils.isEmpty(task.getTitle())) {
            runOnUiThread(() -> error.onError(new TaskException(TaskException.TITLE_EMPTY_OR_NULL)));
            return true;
        }
        else if (TextUtils.isEmpty(task.getDescription())) {
            runOnUiThread(() -> error.onError(new TaskException(TaskException.DESCRIPTION_EMPTY_OR_NULL)));
            return true;
        }
        else if (TextUtils.isEmpty(task.getStartDate())) {
            runOnUiThread(() -> error.onError(new TaskException(TaskException.START_DATE_EMPTY_OR_NULL)));
            return true;
        }
        else if (TextUtils.isEmpty(task.getDueDate())) {
            runOnUiThread(() -> error.onError(new TaskException(TaskException.DUE_DATE_EMPTY_OR_NULL)));
            return true;
        }

        return false;
    }

    public static AddTaskInteractor newInstance() {
        if (null == addTaskInteractor) {
            addTaskInteractor = new AddTaskInteractor();
        }

        return addTaskInteractor;
    }
}
