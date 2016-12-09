package io.blackbox_vision.helpers.logic.factory;

import io.blackbox_vision.helpers.logic.presenter.ListPresenter;
import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;


public final class ListPresenterFactory implements PresenterFactory<ListPresenter> {
    private static ListPresenterFactory listPresenterFactory = null;

    private ListPresenterFactory() { }

    @Override
    public ListPresenter create() {
        return ListPresenter.newInstance();
    }

    public static ListPresenterFactory newInstance() {
        if (null == listPresenterFactory) {
            listPresenterFactory = new ListPresenterFactory();
        }

        return listPresenterFactory;
    }
}
