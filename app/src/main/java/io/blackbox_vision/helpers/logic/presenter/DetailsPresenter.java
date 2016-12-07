package io.blackbox_vision.helpers.logic.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import io.blackbox_vision.helpers.logic.view.DetailsView;
import io.blackbox_vision.helpers.logic.interactor.DetailsInteractor;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;


public final class DetailsPresenter extends BasePresenter<DetailsView> {
    private static DetailsPresenter detailsPresenter = null;
    private DetailsInteractor detailsInteractor;

    private DetailsPresenter() { }

    @Override
    public void onViewAttached(@NonNull DetailsView view) {
        //Init your interactors here
        detailsInteractor = DetailsInteractor.newInstance();

        //Restore app state
    }

    @Override
    public void onViewDetached() {
        //Dereference interactors
        detailsInteractor = null;

        //Save app state
    }

    public void findRequiredInformation(@NonNull String id) {
        if (isViewAttached()) {
            detailsInteractor.retrieveDetailsFromService(id, this::onSuccess, this::onError);
        }
    }

    private void onSuccess(@NonNull Bundle information) {
        if (isViewAttached()) {
            getView().onInfoReceived(information);
        }
    }

    private void onError(@NonNull String errorMessage) {
        if (isViewAttached()) {
            getView().onInfoError(errorMessage);
        }
    }

    public static DetailsPresenter newInstance() {
        if (null == detailsPresenter) {
            detailsPresenter = new DetailsPresenter();
        }

        return detailsPresenter;
    }
}
