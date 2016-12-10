package io.blackbox_vision.helpers.logic.interactor;

import android.os.Bundle;
import android.support.annotation.NonNull;

import io.blackbox_vision.helpers.util.MockUtils;
import io.blackbox_vision.mvphelpers.logic.interactor.BaseInteractor;
import io.blackbox_vision.mvphelpers.logic.listener.OnErrorListener;
import io.blackbox_vision.mvphelpers.logic.listener.OnSuccessListener;


public final class AddTaskInteractor extends BaseInteractor {
    private static AddTaskInteractor addTaskInteractor = null;

    private AddTaskInteractor() { }

    public void retrieveDetailsFromService(@NonNull String id, @NonNull OnSuccessListener<Bundle> sListener, @NonNull OnErrorListener<String> eListener) {
        runOnBackground(() -> {
            final Bundle data = MockUtils.getData(id);

            runOnUiThread(() -> {
                if (data != null) {
                    sListener.onSuccess(data);
                } else {
                    eListener.onError("Ups, something went wrong");
                }
            });
        });
    }

    public static AddTaskInteractor newInstance() {
        if (null == addTaskInteractor) {
            addTaskInteractor = new AddTaskInteractor();
        }

        return addTaskInteractor;
    }
}
