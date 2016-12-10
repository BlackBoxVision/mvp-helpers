package io.blackbox_vision.helpers.logic.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import io.blackbox_vision.helpers.logic.interactor.ListInteractor;
import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.helpers.logic.view.ListView;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;


public final class ListPresenter extends BasePresenter<ListView> {
    private static ListPresenter listPresenter = null;
    private ListInteractor listInteractor;

    private ListPresenter() { }

    @Override
    public void onViewAttached(@NonNull ListView view) {
        listInteractor = ListInteractor.newInstance();
        view.hideProgress();
    }

    @Override
    public void onViewDetached() {
        listInteractor = null;
    }

    public void requestTaskList() {
        if (isViewAttached()) {
            getView().hideTaskList();
            getView().showProgress();

            getListInteractor().findTaskList(this::onTaskListError, this::onTaskListFetched);
        }
    }

    public void createNewTask() {
        if (isViewAttached()) {
            getView().onNewTaskRequest();
        }
    }

    private void onTaskListFetched(@NonNull List<Task> taskList) {
        if (isViewAttached()) {
            getView().hideProgress();
            getView().showTaskList();
            getView().onTaskListFetched(taskList);
        }
    }

    private void onTaskListError(@NonNull Throwable error) {
        if (isViewAttached()) {
            getView().hideProgress();
            getView().showErrorView();
            getView().onTaskListError(error);
        }
    }

    private ListInteractor getListInteractor() {
        return listInteractor;
    }

    public static ListPresenter newInstance() {
        if (null == listPresenter) {
            listPresenter = new ListPresenter();
        }

        return listPresenter;
    }
}
