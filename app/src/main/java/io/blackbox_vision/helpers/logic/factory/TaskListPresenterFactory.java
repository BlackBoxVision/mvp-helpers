package io.blackbox_vision.helpers.logic.factory;

import io.blackbox_vision.helpers.logic.presenter.TaskListPresenter;
import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;


public final class TaskListPresenterFactory implements PresenterFactory<TaskListPresenter> {
    private static TaskListPresenterFactory taskListPresenterFactory = null;

    private TaskListPresenterFactory() { }

    @Override
    public TaskListPresenter create() {
        return TaskListPresenter.newInstance();
    }

    public static TaskListPresenterFactory newInstance() {
        if (null == taskListPresenterFactory) {
            taskListPresenterFactory = new TaskListPresenterFactory();
        }

        return taskListPresenterFactory;
    }
}
