package io.blackbox_vision.helpers.logic.interactor;

import android.support.annotation.NonNull;

import java.util.List;

import io.blackbox_vision.helpers.logic.error.TaskException;
import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.mvphelpers.logic.interactor.BaseInteractor;
import io.blackbox_vision.mvphelpers.logic.listener.OnErrorListener;
import io.blackbox_vision.mvphelpers.logic.listener.OnSuccessListener;


public final class ListInteractor extends BaseInteractor {
    private static ListInteractor listInteractor;

    private ListInteractor() { }

    public void findTaskList(@NonNull OnErrorListener<Throwable> eListener, @NonNull OnSuccessListener<List<Task>> sListener) {
        runOnBackground(() -> {
            List<Task> tasks = Task.find(Task.class, null, null);

            runOnUiThread(() -> {
                if (!tasks.isEmpty()) {
                    sListener.onSuccess(tasks);
                } else {
                    eListener.onError(new TaskException(TaskException.EMPTY_LIST));
                }
            }, 2000L);
        });
    }

    public static ListInteractor newInstance() {
        if (null == listInteractor) {
            listInteractor = new ListInteractor();
        }

        return listInteractor;
    }
}
