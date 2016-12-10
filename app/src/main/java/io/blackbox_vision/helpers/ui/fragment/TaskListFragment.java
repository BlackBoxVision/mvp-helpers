package io.blackbox_vision.helpers.ui.fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import io.blackbox_vision.helpers.R;
import io.blackbox_vision.helpers.logic.error.TaskException;
import io.blackbox_vision.helpers.logic.factory.TaskListPresenterFactory;
import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.helpers.logic.presenter.TaskListPresenter;
import io.blackbox_vision.helpers.logic.view.TaskListView;
import io.blackbox_vision.helpers.ui.activity.AddTaskActivity;
import io.blackbox_vision.helpers.ui.adapter.TaskListAdapter;
import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.ui.fragment.BaseFragment;


public final class TaskListFragment extends BaseFragment<TaskListPresenter> implements TaskListView {
    public static final String LAUNCH_MODE = "LAUNCH_MODE";
    public static final String TASK_ID = "TASK_ID";

    @NonNull
    TaskListAdapter taskListAdapter;

    @BindView(R.id.taskListView)
    RecyclerView taskListView;

    @BindView(R.id.newTaskButton)
    FloatingActionButton newTaskButton;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.errorTextView)
    TextView errorTextView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskListAdapter = new TaskListAdapter(getApplicationContext(), new ArrayList<>());
        taskListAdapter.setOnItemSelectedListener(this::handleItemSelected);

        taskListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        taskListView.setItemViewCacheSize(1024 * 24);
        taskListView.setAdapter(taskListAdapter);
        taskListView.setHasFixedSize(true);

        taskListAdapter.notifyDataSetChanged();

        newTaskButton.setOnClickListener(this::handleNewTaskButtonClick);
    }

    @NonNull
    @Override
    public PresenterFactory<TaskListPresenter> createPresenterFactory() {
        return TaskListPresenterFactory.newInstance();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_task_list;
    }

    @Override
    public void onPresenterCreated(@NonNull TaskListPresenter presenter) {
        presenter.attachView(this);
        presenter.requestTaskList();
    }

    public void handleNewTaskButtonClick(@NonNull View v) {
        if (null != getPresenter()) {
            getPresenter().createNewTask();
        }
    }

    public void handleItemSelected(@NonNull View v, @NonNull Task task, int position) {
        if (null != getPresenter()) {
            getPresenter().showTask(task.getId());
        }
    }

    @Override
    public void onNewTaskRequest() {
        final Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);

        intent.putExtra(LAUNCH_MODE, "create");

        startActivity(intent);
    }

    @Override
    public void onTaskDetailRequest(@NonNull Long id) {
        final Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);

        intent.putExtra(TASK_ID, id);
        intent.putExtra(LAUNCH_MODE, "edit");

        startActivity(intent);
    }

    @Override
    public void onTaskListFetched(@NonNull List<Task> tasks) {
        taskListAdapter.setItems(tasks);
    }

    @Override
    public void onTaskListError(@NonNull Throwable error) {
        final String msg = error.getMessage();

        switch (msg) {
            case TaskException.EMPTY_LIST:
                int drawableColor = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);

                final Drawable d = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_assignment_turned_in_black_48dp);
                d.setColorFilter(drawableColor, PorterDuff.Mode.SRC_ATOP);

                errorTextView.setCompoundDrawablesWithIntrinsicBounds(null, d, null, null);
                errorTextView.setText(getString(R.string.error_empty_list));
                errorTextView.setTextSize(16F);

                break;
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showTaskList() {
        taskListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTaskList() {
        taskListView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorView() {
        errorTextView.setVisibility(View.VISIBLE);
    }
}
