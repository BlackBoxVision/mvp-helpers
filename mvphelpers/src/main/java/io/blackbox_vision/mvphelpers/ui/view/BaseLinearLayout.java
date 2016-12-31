package io.blackbox_vision.mvphelpers.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;


public abstract class BaseLinearLayout<T extends BasePresenter> extends LinearLayout {

    @Nullable
    private T presenter;

    public BaseLinearLayout(Context context) {
        super(context);
    }

    public BaseLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(11)
    public BaseLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BaseLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter = addPresenter();
        onPresenterCreated(presenter);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (null != presenter) {
            presenter.detachView();
        }
    }

    @NonNull
    public abstract T addPresenter();

    public abstract void onPresenterCreated(@NonNull T presenter);

    @Nullable
    public T getPresenter() {
        return presenter;
    }
}