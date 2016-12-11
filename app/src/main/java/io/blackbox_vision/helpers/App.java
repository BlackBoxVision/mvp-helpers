package io.blackbox_vision.helpers;

import android.os.Build;

import com.orm.SugarApp;
import com.squareup.leakcanary.LeakCanary;

import java.util.Date;

import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.mvphelpers.utils.bugfix.IMMLeaks;
import io.blackbox_vision.mvphelpers.utils.bugfix.UserManagerLeaks;


public final class App extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Task task = new Task()
                    .setTitle("Go to the shopping")
                    .setDescription("Buy some thing useful")
                    .setStartDate(new Date())
                    .setDueDate(new Date());


            Task task1 = new Task()
                    .setTitle("Go to the shopping")
                    .setDescription("Buy some thing useful")
                    .setStartDate(new Date())
                    .setDueDate(new Date());


            Task task2 = new Task()
                    .setTitle("Go to the shopping")
                    .setDescription("Buy some thing useful")
                    .setStartDate(new Date())
                    .setDueDate(new Date());


            Task task3 = new Task()
                    .setTitle("Go to the shopping")
                    .setDescription("Buy some thing useful")
                    .setStartDate(new Date())
                    .setDueDate(new Date());

            Task.save(task);
            Task.save(task1);
            Task.save(task2);
            Task.save(task3);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            IMMLeaks.fixAllPosiblyFocusedViewLeaks(this);
        }

        UserManagerLeaks.fixLeakInGetMethod(this);

        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }

            LeakCanary.install(this);
        }
    }
}
