package io.blackbox_vision.mvphelpers.logic.interactor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import io.blackbox_vision.mvphelpers.utils.Preconditions;


public class BaseInteractor {
    private static final int NUMBER_OF_THREADS = 5;

    private ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);
    private ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private Handler handler = new Handler(Looper.getMainLooper());

    private ScheduledFuture<?> scheduledFuture;

    protected void runOnUiThread(@NonNull Runnable runnable) {
        Preconditions.checkNotNull(runnable, "runnable shouldn't be null");

        handler.post(runnable);
    }

    protected void runOnBackground(@NonNull Runnable runnable) {
        Preconditions.checkNotNull(runnable, "runnable shouldn't be null");

        if (executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        }

        final Future<?> task = executor.submit(runnable);

        if (task.isDone()) {
            executor.shutdown();
        }
    }

    protected void runScheduledTaskOnBackground(@NonNull Runnable runnable, @NonNull Long time, @NonNull TimeUnit unit) {
        Preconditions.checkNotNull(runnable, "runnable shouldn't be null");
        Preconditions.checkNotNull(time, "time shouldn't be null");
        Preconditions.checkNotNull(unit, "unit shouldn't be null");

        scheduledFuture = scheduledExecutor.schedule(runnable, time, unit);
    }

    protected void cancelScheduledTask() {
        if (null != scheduledFuture) {
            scheduledFuture.cancel(true);
        }
    }
}
