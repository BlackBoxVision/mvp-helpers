package io.blackbox_vision.helpers.logic.factory;

import io.blackbox_vision.helpers.logic.presenter.AddTaskPresenter;
import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;


public final class AddTaskPresenterFactory implements PresenterFactory<AddTaskPresenter> {
    private static AddTaskPresenterFactory addTaskPresenterFactory = null;

    private AddTaskPresenterFactory() { }

    @Override
    public AddTaskPresenter create() {
        return AddTaskPresenter.newInstance();
    }

    public static AddTaskPresenterFactory newInstance() {
        if (null == addTaskPresenterFactory) {
            addTaskPresenterFactory = new AddTaskPresenterFactory();
        }

        return addTaskPresenterFactory;
    }
}
