package io.blackbox_vision.mvphelpers.utils.bugfix;

import android.app.Application;
import android.content.Context;
import android.os.UserManager;
import android.util.Log;

import java.lang.reflect.Method;


public final class UserManagerLeaks {
    private static final String TAG = UserManagerLeaks.class.getSimpleName();
    private static final String GET_METHOD = "get";

    private UserManagerLeaks() {}

    /**
     * Dirty fix, since the UserManager.get() method is marked as hidden and can't be
     * accessed directly, the current solution it's to use reflection to invoke the get method.
     *
     * @param app - the Application instance where UserManager.get() will be triggered
     */
    public static void fixLeakInGetMethod(Application app) {
        try {
            final Method m = UserManager.class.getMethod(GET_METHOD, Context.class);
            m.setAccessible(true);
            m.invoke(null, app);

            //above is reflection for below...
            //UserManager.get();
        } catch (Throwable e) {
            Log.e(TAG, e.getLocalizedMessage(), e.getCause());
        }
    }
}
