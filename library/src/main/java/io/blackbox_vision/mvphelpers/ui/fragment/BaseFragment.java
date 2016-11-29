package io.blackbox_vision.mvphelpers.ui.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;


public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    protected Unbinder unbinder;

    @Nullable
    protected T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = addPresenter();
        onPresenterCreated(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        if (null != presenter) {
            presenter.detachView();
        }
    }

    @NonNull
    public abstract T addPresenter();

    @LayoutRes
    public abstract int getLayout();

    public abstract void onPresenterCreated(@NonNull T presenter);

    public T getPresenter() {
        return presenter;
    }
}
