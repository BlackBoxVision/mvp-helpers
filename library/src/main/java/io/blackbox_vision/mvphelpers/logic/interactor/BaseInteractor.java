package io.blackbox_vision.mvphelpers.logic.interactor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import io.blackbox_vision.mvphelpers.utils.Preconditions;


public class BaseInteractor {
    private ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(5);
    private ScheduledFuture<?> scheduledFuture;

    private Executor executor = Executors.newFixedThreadPool(5);
    private Handler handler = new Handler(Looper.getMainLooper());

    protected void runOnBackground(@NonNull Runnable runnable, @NonNull Long time, @NonNull TimeUnit unit) {
        Preconditions.checkNotNull(runnable, "runnable shouldn't be null");
        Preconditions.checkNotNull(time, "time shouldn't be null");
        Preconditions.checkNotNull(unit, "unit shouldn't be null");

        scheduledFuture = scheduledExecutor.schedule(runnable, time, unit);
    }

    protected void runOnBackground(@NonNull Runnable runnable) {
        Preconditions.checkNotNull(runnable, "runnable shouldn't be null");

        executor.execute(runnable);
    }

    protected void runOnUiThread(@NonNull Runnable runnable) {
        Preconditions.checkNotNull(runnable, "runnable shouldn't be null");

        handler.post(runnable);
    }

    protected void cancelTask() {
        if (null != scheduledFuture) {
            scheduledFuture.cancel(true);
        }
    }
}
