package io.blackbox_vision.helpers.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import io.blackbox_vision.helpers.R;
import io.blackbox_vision.helpers.helper.DateUtils;
import io.blackbox_vision.helpers.logic.factory.AddTaskPresenterFactory;
import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.helpers.logic.presenter.AddTaskPresenter;
import io.blackbox_vision.helpers.logic.view.AddTaskView;
import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.ui.fragment.BaseFragment;


public final class AddTaskFragment extends BaseFragment<AddTaskPresenter> implements AddTaskView {
    public static final String LAUNCH_MODE = "LAUNCH_MODE";
    public static final String TASK_ID = "TASK_ID";

    private static final String MODE_CREATE = "create";
    private static final String MODE_VIEW = "view";
    private static final String MODE_EDIT = "edit";

    @BindView(R.id.titleEditText)
    EditText titleEditText;

    @BindView(R.id.descriptionEditText)
    EditText descriptionEditText;

    @BindView(R.id.startDateEditText)
    EditText startDateEditText;

    @BindView(R.id.dueDateEditText)
    EditText dueDateEditText;

    @BindView(R.id.taskButton)
    Button taskButton;

    private String launchMode;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskButton.setOnClickListener(this::handleTaskButtonClick);
    }

    private void handleTaskButtonClick(@NonNull View view) {
        switch (launchMode) {
            case MODE_CREATE:
                if (null != getPresenter()) {
                    getPresenter().addNewTask();
                }

                break;

            case MODE_EDIT:
                if (null != getPresenter()) {
                    getPresenter().updateTask();
                }

                break;

            case MODE_VIEW:
                titleEditText.setEnabled(true);
                descriptionEditText.setEnabled(true);
                startDateEditText.setEnabled(true);
                dueDateEditText.setEnabled(true);

                titleEditText.setFocusable(true);
                descriptionEditText.setFocusable(true);
                startDateEditText.setFocusable(true);
                dueDateEditText.setFocusable(true);

                titleEditText.setFocusableInTouchMode(true);
                descriptionEditText.setFocusableInTouchMode(true);
                startDateEditText.setFocusableInTouchMode(true);
                dueDateEditText.setFocusableInTouchMode(true);

                launchMode = MODE_EDIT;
                taskButton.setText(R.string.update_task);
        }
    }

    @NonNull
    @Override
    public PresenterFactory<AddTaskPresenter> createPresenterFactory() {
        return AddTaskPresenterFactory.newInstance();
    }

    @LayoutRes
    @Override
    public int getLayout() {
        return R.layout.fragment_add_task;
    }

    @Override
    public void onPresenterCreated(@NonNull AddTaskPresenter presenter) {
        presenter.attachView(this);

        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        final Intent intent = getActivity().getIntent();

        if (null != intent && null != actionBar) {
            launchMode = intent.getStringExtra(LAUNCH_MODE);
            final Long taskId = intent.getLongExtra(TASK_ID, -1L);

            switch (launchMode) {
                case MODE_VIEW:
                    actionBar.setTitle(R.string.view_task);
                    taskButton.setText(R.string.edit_task);
                    break;

                case MODE_EDIT:
                    actionBar.setTitle(R.string.edit_task);
                    taskButton.setText(R.string.update_task);
                    break;

                case MODE_CREATE:
                default:
                    actionBar.setTitle(R.string.create_task);
                    taskButton.setText(R.string.create_task);
                    break;
            }

            if (taskId != -1L) {
                presenter.findTaskById(taskId);
            }
        }
    }

    @Override
    public Task getTask() {
        final String title = titleEditText.getText().toString().trim();
        final String description = descriptionEditText.getText().toString().trim();
        final String startDate = startDateEditText.getText().toString().trim();
        final String dueDate = dueDateEditText.getText().toString().trim();

        return new Task()
                .setTitle(title)
                .setDescription(description)
                .setStartDate(DateUtils.fromString(startDate, "/"))
                .setDueDate(DateUtils.fromString(dueDate, "/"))
                .setCompleted(false);
    }

    @Override
    public void onTaskCreated(@NonNull Task task) {

    }

    @Override
    public void onTaskUpdated(@NonNull Task task) {

    }

    @Override
    public void onTaskFound(@NonNull Task task) {
        if (launchMode.equals(MODE_VIEW)) {
            String startDate = "";
            String dueDate = "";

            if (null != task.getStartDate()) {
                startDate = DateUtils.formatWithDefaults(task.getStartDate());
            }

            if (null != task.getDueDate()) {
                dueDate = DateUtils.formatWithDefaults(task.getDueDate());
            }

            titleEditText.setText(task.getTitle());
            descriptionEditText.setText(task.getDescription());
            startDateEditText.setText(startDate);
            dueDateEditText.setText(dueDate);

            titleEditText.setEnabled(false);
            descriptionEditText.setEnabled(false);
            startDateEditText.setEnabled(false);
            dueDateEditText.setEnabled(false);

            titleEditText.setFocusable(false);
            descriptionEditText.setFocusable(false);
            startDateEditText.setFocusable(false);
            dueDateEditText.setFocusable(false);

            titleEditText.setFocusableInTouchMode(false);
            descriptionEditText.setFocusableInTouchMode(false);
            startDateEditText.setFocusableInTouchMode(false);
            dueDateEditText.setFocusableInTouchMode(false);
        }
    }

    @Override
    public void onError(@NonNull Throwable error) {

    }
}
