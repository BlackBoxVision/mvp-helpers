package io.blackbox_vision.helpers.logic.interactor;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.mvphelpers.logic.interactor.BaseInteractor;
import io.blackbox_vision.mvphelpers.logic.listener.OnErrorListener;
import io.blackbox_vision.mvphelpers.logic.listener.OnSuccessListener;


public final class ListInteractor extends BaseInteractor {
    private static final String ERROR_MESSAGE = "EMPTY LIST";
    private static ListInteractor listInteractor;

    private ListInteractor() { }

    public void findTaskList(@NonNull OnErrorListener<Throwable> eListener, @NonNull OnSuccessListener<List<Task>> sListener) {
        runOnBackground(() -> {
            List<Task> tasks = new ArrayList<>();

            tasks.add(new Task().setTitle("Armar los bolsos"));
            tasks.add(new Task().setTitle("Preparar la comida"));
            tasks.add(new Task().setTitle("Ir de compras"));
            tasks.add(new Task().setTitle("Buscar a los chicos"));

            runOnUiThread(() -> {
                if (tasks.size() > 0) {
                    sListener.onSuccess(tasks);
                } else {
                    eListener.onError(new Throwable(ERROR_MESSAGE));
                }
            });
        });
    }

    public static ListInteractor newInstance() {
        if (null == listInteractor) {
            listInteractor = new ListInteractor();
        }

        return listInteractor;
    }
}
