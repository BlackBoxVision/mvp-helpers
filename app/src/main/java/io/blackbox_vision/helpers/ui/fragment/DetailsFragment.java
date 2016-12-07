package io.blackbox_vision.helpers.ui.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;

import io.blackbox_vision.helpers.R;
import io.blackbox_vision.helpers.logic.factory.DetailsPresenterFactory;
import io.blackbox_vision.helpers.util.MockUtils;
import io.blackbox_vision.helpers.logic.presenter.DetailsPresenter;
import io.blackbox_vision.helpers.logic.view.DetailsView;
import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.ui.fragment.BaseFragment;


public final class DetailsFragment extends BaseFragment<DetailsPresenter> implements DetailsView {

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

    @NonNull
    @Override
    public PresenterFactory<DetailsPresenter> createPresenterFactory() {
        return DetailsPresenterFactory.newInstance();
    }

    @LayoutRes
    @Override
    public int getLayout() {
        return R.layout.fragment_details;
    }

    @Override
    public void onPresenterCreated(@NonNull DetailsPresenter presenter) {
        presenter.attachView(this);
    }

    @Override
    public void onInfoReceived(@NonNull Bundle information) {
        Toast.makeText(getContext(), "This is the information ---> " + information.get(MockUtils.SAMPLE), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInfoError(@NonNull String errorMessage) {
        Toast.makeText(getContext(), "This is the error ---> " + errorMessage, Toast.LENGTH_SHORT).show();
    }
}
