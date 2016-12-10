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
import android.widget.Toast;

import butterknife.BindView;

import io.blackbox_vision.helpers.R;
import io.blackbox_vision.helpers.logic.factory.AddTaskPresenterFactory;
import io.blackbox_vision.helpers.util.MockUtils;
import io.blackbox_vision.helpers.logic.presenter.AddTaskPresenter;
import io.blackbox_vision.helpers.logic.view.AddTaskView;
import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.ui.fragment.BaseFragment;


public final class AddTaskFragment extends BaseFragment<AddTaskPresenter> implements AddTaskView {
    public static final String LAUNCH_MODE = "LAUNCH_MODE";
    //public static final String TASK_ID = "TASK_ID";

    private static final String MODE_CREATE = "create";
    private static final String MODE_EDIT = "edit";

    @BindView(R.id.sample_button)
    Button button;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button.setOnClickListener(v -> {
            if (null != getPresenter()) {
                getPresenter().findRequiredInformation(MockUtils.SAMPLE);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        final Intent intent = getActivity().getIntent();

        if (null != intent && null != actionBar) {
            final String mode = intent.getStringExtra(LAUNCH_MODE);

            switch (mode) {
                case MODE_EDIT:
                    actionBar.setTitle(R.string.edit_task);
                    break;

                case MODE_CREATE:
                default:
                    actionBar.setTitle(R.string.create_task);
                    break;
            }
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
    }

    @Override
    public void onInfoReceived(@NonNull Bundle information) {
        final String message = "This is the information ---> " + information.get(MockUtils.SAMPLE);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInfoError(@NonNull String errorMessage) {
        final String message = "This is the error ---> " + errorMessage;
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
