package io.blackbox_vision.helpers.util;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.HashMap;

public final class MockUtils {
    public static final String SAMPLE = "SAMPLE";
    private static final HashMap<String, Bundle> map = new HashMap<>();
    private static final Bundle bundle = new Bundle();

    //This doesn't make sense at all, it's for educational purpose
    public static Bundle getData(@NonNull String id) {
        bundle.putString(SAMPLE, "HELLO MVP!!");
        map.put(SAMPLE, bundle);
        return map.get(id);
    }
}
