package io.blackbox_vision.mvphelpers.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;


public abstract class BaseFrameLayout<T extends BasePresenter> extends FrameLayout {

    @Nullable
    private T presenter;

    public BaseFrameLayout(Context context) {
        super(context);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
