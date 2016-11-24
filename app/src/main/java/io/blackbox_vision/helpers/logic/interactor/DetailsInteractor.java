package io.blackbox_vision.helpers.logic.interactor;

import android.os.Bundle;
import android.support.annotation.NonNull;

import io.blackbox_vision.helpers.util.MockUtils;
import io.blackbox_vision.mvphelpers.logic.interactor.BaseInteractor;
import io.blackbox_vision.mvphelpers.logic.listener.OnErrorListener;
import io.blackbox_vision.mvphelpers.logic.listener.OnSuccessListener;

public final class DetailsInteractor extends BaseInteractor {

    public void retrieveDetailsFromService(@NonNull final String id, @NonNull final OnSuccessListener<Bundle> successListener, @NonNull final OnErrorListener<String> errorListener) {
        runOnBackground(() -> {
            //Getting data from somewhere
            final Bundle data = MockUtils.getMockedData(id);

            if (data != null) {
                runOnUiThread(() -> successListener.onSuccess(data));
            } else {
                runOnUiThread(() -> errorListener.onError("Ups, something went wrong"));
            }
        });
    }
}
