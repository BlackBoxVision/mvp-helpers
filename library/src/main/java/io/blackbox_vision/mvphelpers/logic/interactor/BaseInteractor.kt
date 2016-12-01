package io.blackbox_vision.mvphelpers.logic.interactor

import android.os.Handler
import android.os.Looper

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

import io.blackbox_vision.mvphelpers.utils.Preconditions


open class BaseInteractor {
    private val scheduledExecutor = Executors.newScheduledThreadPool(5)
    private var scheduledFuture: ScheduledFuture<*>? = null

    private val executor = Executors.newFixedThreadPool(5)
    private val handler = Handler(Looper.getMainLooper())

    protected fun runOnBackground(runnable: Runnable, time: Long, unit: TimeUnit) {
        Preconditions.checkNotNull(runnable, "runnable shouldn't be null")
        Preconditions.checkNotNull(time, "time shouldn't be null")
        Preconditions.checkNotNull(unit, "unit shouldn't be null")

        scheduledFuture = scheduledExecutor.schedule(runnable, time, unit)
    }

    protected fun runOnBackground(runnable: Runnable) {
        Preconditions.checkNotNull(runnable, "runnable shouldn't be null")

        executor.execute(runnable)
    }

    protected fun runOnUiThread(runnable: Runnable) {
        Preconditions.checkNotNull(runnable, "runnable shouldn't be null")

        handler.post(runnable)
    }

    protected fun cancelTask() {
        if (null != scheduledFuture) {
            scheduledFuture!!.cancel(true)
        }
    }
}
