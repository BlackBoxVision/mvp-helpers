package io.blackbox_vision.helpers.logic.presenter;

import android.support.annotation.NonNull;

import java.util.Calendar;

import io.blackbox_vision.helpers.helper.DateUtils;
import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.helpers.logic.view.AddTaskView;
import io.blackbox_vision.helpers.logic.interactor.AddTaskInteractor;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;


public final class AddTaskPresenter extends BasePresenter<AddTaskView> {
    private static AddTaskPresenter addTaskPresenter = null;
    private AddTaskInteractor addTaskInteractor;

    private AddTaskPresenter() { }

    @Override
    public void onViewAttached(@NonNull AddTaskView view) {
        addTaskInteractor = AddTaskInteractor.newInstance();
    }

    @Override
    public void onViewDetached() {
        addTaskInteractor = null;
    }

    public void findTaskById(@NonNull Long taskId) {
        if (isViewAttached()) {
            addTaskInteractor.findTaskById(taskId, this::onError, this::onTaskFound);
        }
    }

    public void addNewTask() {
        if (isViewAttached()) {
            final String title = getView().getTitle();
            final String description = getView().getDescription();
            final String startDate = getView().getStartDate();
            final String dueDate = getView().getDueDate();

            addTaskInteractor.addNewTask(title, description, startDate, dueDate, this::onError, this::onTaskCreated);
        }
    }

    public void updateTask() {
        if (isViewAttached()) {
            final Long taskId = getView().getTaskId();
            final String title = getView().getTitle();
            final String description = getView().getDescription();
            final String startDate = getView().getStartDate();
            final String dueDate = getView().getDueDate();

            addTaskInteractor.updateTask(taskId, title, description, startDate, dueDate, this::onError, this::onTaskUpdated);
        }
    }

    public void findLaunchMode() {
        if (isViewAttached()) {
            getView().onLaunchMode();
        }
    }

    public void removeTask() {
        if (isViewAttached()) {
            final Task task = new Task()
                    .setTitle(getView().getTitle())
                    .setDescription(getView().getDescription())
                    .setStartDate(DateUtils.fromString(getView().getStartDate(), "/"))
                    .setDueDate(DateUtils.fromString(getView().getDueDate(), "/"))
                    .setCompleted(false);

            task.setId(getView().getTaskId());

            addTaskInteractor.removeTask(task, this::onError, this::onTaskDeleted);
        }
    }

    private void onTaskDeleted(@NonNull Boolean deleted) {
        if (isViewAttached()) {
            getView().goBack();
        }
    }

    private void onTaskUpdated(@NonNull Task task) {
        if (isViewAttached()) {
            getView().goBack();
        }
    }

    private void onTaskCreated(@NonNull Task task) {
        if (isViewAttached()) {
            getView().goBack();
        }
    }

    private void onTaskFound(@NonNull Task task) {
        if (isViewAttached()) {
            getView().setTitle(task.getTitle());
            getView().setDescription(task.getDescription());

            final String formattedStartDate = DateUtils.formatDate(task.getStartDate());
            final Calendar startDate = DateUtils.fromDate(task.getStartDate());

            getView().setStartDate(formattedStartDate, startDate);

            final String formattedDueDate = DateUtils.formatDate(task.getStartDate());
            final Calendar dueDate = DateUtils.fromDate(task.getStartDate());

            getView().setDueDate(formattedDueDate, dueDate);
        }
    }

    private void onError(@NonNull Throwable error) {
        if (isViewAttached()) {
            getView().onError(error);
        }
    }

    public static AddTaskPresenter newInstance() {
        if (null == addTaskPresenter) {
            addTaskPresenter = new AddTaskPresenter();
        }

        return addTaskPresenter;
    }
}
