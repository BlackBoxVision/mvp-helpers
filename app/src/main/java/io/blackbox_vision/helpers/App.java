package io.blackbox_vision.helpers;

import android.os.Build;

import com.orm.SugarApp;
import com.squareup.leakcanary.LeakCanary;

import io.blackbox_vision.mvphelpers.utils.bugfix.IMMLeaks;
import io.blackbox_vision.mvphelpers.utils.bugfix.UserManagerLeaks;


public final class App extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            IMMLeaks.fixAllPosiblyFocusedViewLeaks(this);
        }

        UserManagerLeaks.fixLeakInGetMethod(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this);
    }
}
