package io.blackbox_vision.mvphelpers.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    protected Unbinder unbinder;
    protected T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);
        presenter = addPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.detachView();
    }

    @NonNull
    public abstract T addPresenter();

    @LayoutRes
    public abstract int getLayout();

    public T getPresenter() {
        return presenter;
    }
}
