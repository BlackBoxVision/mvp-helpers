package io.blackbox_vision.mvphelpers.logic.listener.listener;

import android.support.annotation.NonNull;

public interface OnErrorListener<T> {

    void onError(@NonNull T error);
}
