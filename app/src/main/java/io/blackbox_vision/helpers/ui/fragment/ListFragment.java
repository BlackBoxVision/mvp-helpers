package io.blackbox_vision.helpers.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import butterknife.BindView;

import io.blackbox_vision.helpers.R;
import io.blackbox_vision.helpers.logic.factory.ListPresenterFactory;
import io.blackbox_vision.helpers.logic.model.Task;
import io.blackbox_vision.helpers.logic.presenter.ListPresenter;
import io.blackbox_vision.helpers.logic.view.ListView;
import io.blackbox_vision.helpers.ui.activity.DetailsActivity;
import io.blackbox_vision.helpers.ui.adapter.TaskListAdapter;
import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.ui.fragment.BaseFragment;


public final class ListFragment extends BaseFragment<ListPresenter> implements ListView {

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
        taskListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        taskListView.setItemViewCacheSize(1024 * 24);
        taskListView.setHasFixedSize(true);

        newTaskButton.setOnClickListener(this::handleNewTaskButtonClick);
    }

    @NonNull
    @Override
    public PresenterFactory<ListPresenter> createPresenterFactory() {
        return ListPresenterFactory.newInstance();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_list;
    }

    @Override
    public void onPresenterCreated(@NonNull ListPresenter presenter) {
        presenter.attachView(this);
        presenter.findTaskList();
    }

    public void handleNewTaskButtonClick(@NonNull View v) {
        if (null != getPresenter()) {
            getPresenter().createNewTask();
        }
    }

    @Override
    public void onNewTaskRequest() {
        final Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onTaskListFetched(@NonNull java.util.List<Task> tasks) {
        taskListView.setAdapter(new TaskListAdapter(getApplicationContext(), tasks));
    }

    @Override
    public void onTaskListError(@NonNull Throwable error) {
        errorTextView.setText(error.getMessage());
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

    @Override
    public void hideErrorView() {
        errorTextView.setVisibility(View.INVISIBLE);
    }
}
