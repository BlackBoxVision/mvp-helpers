package io.blackbox_vision.mvphelpers.logic.interactor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.blackbox_vision.mvphelpers.utils.Preconditions;

public class BaseInteractor {
    private Executor executor = Executors.newFixedThreadPool(5);
    private Handler handler = new Handler(Looper.getMainLooper());

    protected void runOnBackground(@NonNull Runnable runnable) {
        Preconditions.checkNotNull(runnable, "runnable shouldn't be null");
        executor.execute(runnable);
    }

    protected void runOnUiThread(@NonNull Runnable runnable) {
        Preconditions.checkNotNull(runnable, "runnable shouldn't be null");
        handler.post(runnable);
    }
}
