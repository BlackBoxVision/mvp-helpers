package io.blackbox_vision.helpers.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import io.blackbox_vision.helpers.R;
import io.blackbox_vision.helpers.helper.AppConstants;
import io.blackbox_vision.helpers.helper.DrawableUtils;
import io.blackbox_vision.helpers.logic.error.TaskException;
import io.blackbox_vision.helpers.logic.factory.AddTaskPresenterFactory;
import io.blackbox_vision.helpers.logic.presenter.AddTaskPresenter;
import io.blackbox_vision.helpers.logic.view.AddTaskView;
import io.blackbox_vision.helpers.ui.activity.TaskListActivity;
import io.blackbox_vision.helpers.ui.custom.DatePickerEditText;
import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.ui.fragment.BaseFragment;


public final class AddTaskFragment extends BaseFragment<AddTaskPresenter, AddTaskView> implements AddTaskView {
    private String launchMode;
    private Long taskId;

    @BindView(R.id.titleInputLayout)
    TextInputLayout titleInputLayout;

    @BindView(R.id.titleEditText)
    TextInputEditText titleEditText;

    @BindView(R.id.descriptionInputLayout)
    TextInputLayout descriptionInputLayout;

    @BindView(R.id.descriptionEditText)
    TextInputEditText descriptionEditText;

    @BindView(R.id.startDateInputLayout)
    TextInputLayout startDateInputLayout;

    @BindView(R.id.startDateEditText)
    DatePickerEditText startDateEditText;

    @BindView(R.id.dueDateInputLayout)
    TextInputLayout dueDateInputLayout;

    @BindView(R.id.dueDateEditText)
    DatePickerEditText dueDateEditText;

    @BindView(R.id.taskButton)
    Button taskButton;

    public AddTaskFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        final Drawable titleDrawable = DrawableUtils.applyColorFilter(getApplicationContext(), R.drawable.ic_title_black_24dp);
        final Drawable descriptionDrawable = DrawableUtils.applyColorFilter(getApplicationContext(), R.drawable.ic_description_black_24dp);
        final Drawable dateDrawable = DrawableUtils.applyColorFilter(getApplicationContext(), R.drawable.ic_date_range_black_24dp);

        titleEditText.setCompoundDrawablesWithIntrinsicBounds(titleDrawable, null, null, null);
        descriptionEditText.setCompoundDrawablesWithIntrinsicBounds(descriptionDrawable, null, null, null);
        startDateEditText.setCompoundDrawablesWithIntrinsicBounds(dateDrawable, null, null, null);
        dueDateEditText.setCompoundDrawablesWithIntrinsicBounds(dateDrawable, null, null, null);

        titleEditText.setCompoundDrawablePadding(8);
        descriptionEditText.setCompoundDrawablePadding(8);
        startDateEditText.setCompoundDrawablePadding(8);
        dueDateEditText.setCompoundDrawablePadding(8);

        startDateEditText.setManager(getFragmentManager());
        dueDateEditText.setManager(getFragmentManager());

        startDateEditText.setFocusChangeListener(this::onFocusChange);
        dueDateEditText.setFocusChangeListener(this::onFocusChange);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.task_menu, menu);

        switch (launchMode) {
            case AppConstants.MODE_CREATE:
                final MenuItem item = menu.getItem(0);
                item.setVisible(false);

                break;
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                if (isPresenterAvailable()) {
                    final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.dialog_delete_task)
                            .setPositiveButton(android.R.string.ok, this::onClick)
                            .setNegativeButton(android.R.string.cancel, this::onClick)
                            .create();

                    dialog.show();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(@NonNull DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                if (isPresenterAvailable()) {
                    getPresenter().removeTask();
                }

                dialog.dismiss();

                break;

            case DialogInterface.BUTTON_NEGATIVE:
                dialog.cancel();

                break;
        }
    }

    public void onFocusChange(View view, boolean isFocused) {
        switch (view.getId()) {
            case R.id.startDateEditText:
            case R.id.dueDateEditText:

                if (isFocused) {
                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                break;
        }
    }

    @OnClick(R.id.taskButton)
    public void onClick() {
        switch (launchMode) {
            case AppConstants.MODE_CREATE:
                if (isPresenterAvailable()) {
                    getPresenter().addNewTask();
                }

                break;

            case AppConstants.MODE_EDIT:
                if (isPresenterAvailable()) {
                    getPresenter().updateTask();
                }

                break;
        }
    }

    @NonNull
    @Override
    protected PresenterFactory<AddTaskPresenter> createPresenterFactory() {
        return AddTaskPresenterFactory.newInstance();
    }

    @LayoutRes
    @Override
    protected int getLayout() {
        return R.layout.fragment_add_task;
    }

    @Override
    protected void onPresenterCreated(@NonNull AddTaskPresenter presenter) {
        presenter.findLaunchMode();

        if (taskId != -1L) {
            presenter.findTaskById(taskId);
        }
    }

    @Override
    protected void onPresenterDestroyed() {

    }

    @Override
    public Long getTaskId() {
        return taskId;
    }

    @Override
    public String getTitle() {
        return titleEditText.getText().toString().trim();
    }

    @Override
    public void setTitle(@NonNull String title) {
        titleEditText.setText(title);
    }

    @Override
    public String getDescription() {
        return descriptionEditText.getText().toString().trim();
    }

    @Override
    public void setDescription(@NonNull String description) {
        descriptionEditText.setText(description);
    }

    @Override
    public String getStartDate() {
        return startDateEditText.getText().toString().trim();
    }

    @Override
    public void setStartDate(@NonNull String formattedDate, @NonNull Calendar date) {
        startDateEditText.setText(formattedDate);
        startDateEditText.setDate(date);
    }

    @Override
    public String getDueDate() {
        return dueDateEditText.getText().toString().trim();
    }

    @Override
    public void setDueDate(@NonNull String formattedDate, @NonNull Calendar date) {
        dueDateEditText.setText(formattedDate);
        dueDateEditText.setDate(date);
    }

    @Override
    public void onLaunchMode() {
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        final Intent intent = getActivity().getIntent();

        if (null != intent && null != actionBar) {
            launchMode = intent.getStringExtra(AppConstants.LAUNCH_MODE);
            taskId = intent.getLongExtra(AppConstants.TASK_ID, -1L);

            switch (launchMode) {
                case AppConstants.MODE_EDIT:
                    actionBar.setTitle(R.string.edit_task);
                    taskButton.setText(R.string.update_task);
                    break;

                case AppConstants.MODE_CREATE:
                default:
                    actionBar.setTitle(R.string.create_task);
                    taskButton.setText(R.string.create_task);
                    break;
            }
        }
    }

    @Override
    public void goBack() {
        final Intent intent = new Intent(getApplicationContext(), TaskListActivity.class);

        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onError(@NonNull Throwable error) {
        int messageId;

        switch (error.getMessage()) {
            case TaskException.TITLE_EMPTY_OR_NULL:
                messageId = R.string.empty_title;
                break;
            case TaskException.DESCRIPTION_EMPTY_OR_NULL:
                messageId = R.string.empty_description;
                break;
            case TaskException.START_DATE_EMPTY_OR_NULL:
                messageId = R.string.empty_start_date;
                break;
            case TaskException.DUE_DATE_EMPTY_OR_NULL:
                messageId = R.string.empty_due_date;
                break;
            default:
                messageId = R.string.generic_error;
                break;
        }

        Snackbar.make(taskButton, messageId, Snackbar.LENGTH_SHORT).show();
    }
}
