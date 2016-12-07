package io.blackbox_vision.helpers.logic.factory;

import io.blackbox_vision.helpers.logic.presenter.DetailsPresenter;
import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;


public final class DetailsPresenterFactory implements PresenterFactory<DetailsPresenter> {
    private static DetailsPresenterFactory detailsPresenterFactory = null;

    private DetailsPresenterFactory() { }

    @Override
    public DetailsPresenter create() {
        return DetailsPresenter.newInstance();
    }

    public static DetailsPresenterFactory newInstance() {
        if (null == detailsPresenterFactory) {
            detailsPresenterFactory = new DetailsPresenterFactory();
        }

        return detailsPresenterFactory;
    }
}
