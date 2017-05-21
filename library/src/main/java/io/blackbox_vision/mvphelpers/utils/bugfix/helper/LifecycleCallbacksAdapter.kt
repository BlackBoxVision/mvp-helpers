package io.blackbox_vision.mvphelpers.utils.bugfix.helper

import android.annotation.TargetApi
import android.app.Activity
import android.app.Application
import android.os.Bundle


/** Helper to avoid implementing all lifecycle callback methods.  */
@TargetApi(14)
open class LifecycleCallbacksAdapter : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}