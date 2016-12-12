package io.blackbox_vision.helpers.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import io.blackbox_vision.helpers.R;
import io.blackbox_vision.helpers.helper.DateUtils;
import io.blackbox_vision.helpers.helper.DrawableUtils;
import io.blackbox_vision.helpers.logic.factory.AddTaskPresenterFactory;
import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.helpers.logic.presenter.AddTaskPresenter;
import io.blackbox_vision.helpers.logic.view.AddTaskView;
import io.blackbox_vision.helpers.ui.activity.TaskListActivity;
import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.ui.fragment.BaseFragment;


public final class AddTaskFragment extends BaseFragment<AddTaskPresenter> implements AddTaskView {
    public static final String LAUNCH_MODE = "LAUNCH_MODE";
    public static final String TASK_ID = "TASK_ID";

    private static final String MODE_CREATE = "create";
    private static final String MODE_EDIT = "edit";

    @BindView(R.id.titleEditText)
    TextInputEditText titleEditText;

    @BindView(R.id.descriptionEditText)
    TextInputEditText descriptionEditText;

    @BindView(R.id.startDateEditText)
    TextInputEditText startDateEditText;

    @BindView(R.id.dueDateEditText)
    TextInputEditText dueDateEditText;

    @BindView(R.id.taskButton)
    Button taskButton;

    private String launchMode;
    private Long taskId;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        taskButton.setOnClickListener(this::handleTaskButtonClick);

        Drawable titleDrawable = DrawableUtils.applyColorFilter(getApplicationContext(), R.drawable.ic_title_black_24dp);
        Drawable descriptionDrawable = DrawableUtils.applyColorFilter(getApplicationContext(), R.drawable.ic_description_black_24dp);
        Drawable dateDrawable = DrawableUtils.applyColorFilter(getApplicationContext(), R.drawable.ic_date_range_black_24dp);

        titleEditText.setCompoundDrawablesWithIntrinsicBounds(titleDrawable, null, null, null);
        descriptionEditText.setCompoundDrawablesWithIntrinsicBounds(descriptionDrawable, null, null, null);
        startDateEditText.setCompoundDrawablesWithIntrinsicBounds(dateDrawable, null, null, null);
        dueDateEditText.setCompoundDrawablesWithIntrinsicBounds(dateDrawable, null, null, null);

        titleEditText.setCompoundDrawablePadding(8);
        descriptionEditText.setCompoundDrawablePadding(8);
        startDateEditText.setCompoundDrawablePadding(8);
        dueDateEditText.setCompoundDrawablePadding(8);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.task_menu, menu);

        if (launchMode.equals(MODE_CREATE)) {
            MenuItem item = menu.getItem(0);
            item.setVisible(false);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                if (null != getPresenter()) {
                    getPresenter().removeTask();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
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
        presenter.updateViewByMode();
    }

    @Override
    public Task getTask() {
        final String title = titleEditText.getText().toString().trim();
        final String description = descriptionEditText.getText().toString().trim();
        final String startDate = startDateEditText.getText().toString().trim();
        final String dueDate = dueDateEditText.getText().toString().trim();

        Task task = new Task()
                .setTitle(title)
                .setDescription(description)
                .setStartDate(DateUtils.fromString(startDate, "/"))
                .setDueDate(DateUtils.fromString(dueDate, "/"))
                .setCompleted(false);

        if (taskId != -1L) {
            task.setId(taskId);
        }

        return task;
    }

    @Override
    public void updateViewByLaunchMode() {
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        final Intent intent = getActivity().getIntent();

        if (null != intent && null != actionBar) {
            launchMode = intent.getStringExtra(LAUNCH_MODE);
            taskId = intent.getLongExtra(TASK_ID, -1L);

            switch (launchMode) {
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

            if (taskId != -1L && null != getPresenter()) {
                getPresenter().findTaskById(taskId);
            }
        }
    }

    @Override
    public void onTaskFound(@NonNull Task task) {
        String startDate = "";
        String dueDate = "";

        if (null != task.getStartDate()) {
            startDate = DateUtils.formatDate(task.getStartDate());
        }

        if (null != task.getDueDate()) {
            dueDate = DateUtils.formatDate(task.getDueDate());
        }

        titleEditText.setText(task.getTitle());
        descriptionEditText.setText(task.getDescription());
        startDateEditText.setText(startDate);
        dueDateEditText.setText(dueDate);
    }

    @Override
    public void goBack() {
        Intent intent = new Intent(getApplicationContext(), TaskListActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onError(@NonNull Throwable error) {

    }
}
