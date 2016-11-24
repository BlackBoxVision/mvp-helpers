package io.blackbox_vision.helpers.logic.view;

import android.os.Bundle;
import android.support.annotation.NonNull;

import io.blackbox_vision.mvphelpers.logic.view.BaseView;

public interface DetailsView extends BaseView {

    void onInfoReceived(@NonNull Bundle information);

    void onInfoError(@NonNull String errorMessage);
}
