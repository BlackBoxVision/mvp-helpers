package io.blackbox_vision.helpers.ui.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import io.blackbox_vision.helpers.R;
import io.blackbox_vision.helpers.util.MockUtils;
import io.blackbox_vision.helpers.logic.presenter.DetailsPresenter;
import io.blackbox_vision.helpers.logic.view.DetailsView;
import io.blackbox_vision.mvphelpers.ui.BaseFragment;


public final class DetailsFragment extends BaseFragment<DetailsPresenter> implements DetailsView {

    @NonNull
    @Override
    public DetailsPresenter addPresenter() {
        return new DetailsPresenter();
    }

    @LayoutRes
    @Override
    public int getLayout() {
        return R.layout.fragment_details;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPresenter().registerView(this);
        getPresenter().findRequiredInformation(MockUtils.SAMPLE);
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
