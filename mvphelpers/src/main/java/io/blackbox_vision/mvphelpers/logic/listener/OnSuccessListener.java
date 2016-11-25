package io.blackbox_vision.mvphelpers.logic.listener;

import android.support.annotation.NonNull;


public interface OnSuccessListener<T> {

    void onSuccess(@NonNull T data);
}
