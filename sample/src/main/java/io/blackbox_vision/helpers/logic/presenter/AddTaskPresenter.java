package io.blackbox_vision.helpers.logic.presenter;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

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
    protected void onViewAttached(@NonNull AddTaskView view) {
        addTaskInteractor = AddTaskInteractor.newInstance();
    }

    @Override
    protected void onViewDetached() {
        addTaskInteractor = null;
    }

    public void findTaskById(@NonNull Long taskId) {
        if (isViewAttached()) {
            addTaskInteractor.findTaskById(taskId, this::onError, this::onTaskFound);
        }
    }

    public void addNewTask() {
        if (isViewAttached()) {
            final Task task = new Task()
                    .setTitle(getView().getTitle())
                    .setDescription(getView().getDescription())
                    .setStartDate(getView().getStartDate())
                    .setDueDate(getView().getDueDate())
                    .setCreatedAt(new Date(System.currentTimeMillis()))
                    .setCompleted(false);

            addTaskInteractor.addNewTask(task, this::onError, this::onTaskCreated);
        }
    }

    public void updateTask() {
        if (isViewAttached()) {
            final Task task = new Task()
                    .setTitle(getView().getTitle())
                    .setDescription(getView().getDescription())
                    .setStartDate(getView().getStartDate())
                    .setDueDate(getView().getDueDate())
                    .setCreatedAt(new Date(System.currentTimeMillis()))
                    .setCompleted(false);

            final Long taskId = getView().getTaskId();

            if (taskId != -1L) {
                task.setId(taskId);
            }

            addTaskInteractor.updateTask(task, this::onError, this::onTaskUpdated);
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
                    .setStartDate(getView().getStartDate())
                    .setDueDate(getView().getDueDate())
                    .setCompleted(false);

            final Long taskId = getView().getTaskId();

            if (taskId != -1L) {
                task.setId(taskId);
            }

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

            final Date start = DateUtils.fromString(task.getStartDate(), "/");
            final Calendar startDate = DateUtils.fromDate(start);

            getView().setStartDate(task.getStartDate(), startDate);

            final Date due = DateUtils.fromString(task.getStartDate(), "/");
            final Calendar dueDate = DateUtils.fromDate(due);

            getView().setDueDate(task.getDueDate(), dueDate);
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
