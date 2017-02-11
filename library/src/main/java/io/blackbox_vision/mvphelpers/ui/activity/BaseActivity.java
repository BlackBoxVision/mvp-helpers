package io.blackbox_vision.mvphelpers.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.blackbox_vision.mvphelpers.logic.factory.PresenterFactory;
import io.blackbox_vision.mvphelpers.logic.presenter.BasePresenter;
import io.blackbox_vision.mvphelpers.logic.view.BaseView;
import io.blackbox_vision.mvphelpers.ui.loader.PresenterLoader;

import static android.support.v4.app.LoaderManager.LoaderCallbacks;


public abstract class BaseActivity<P extends BasePresenter<V>, V extends BaseView> extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private static final int LOADER_ID = 201;

    protected P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        getSupportLoaderManager().initLoader(LOADER_ID, null, new LoaderCallbacks<P>() {
            @Override
            public Loader<P> onCreateLoader(int id, Bundle args) {
                return PresenterLoader.newInstance(getApplicationContext(), createPresenterFactory());
            }

            @Override
            public void onLoadFinished(Loader<P> loader, P basePresenter) {
                final V presenterView = getPresenterView();
                presenter = basePresenter;

                if (null != presenterView) {
                    presenter.attachView(presenterView);
                } else {
                    Log.d(TAG, "View can't be attached because you don't implement it in your activity.");
                }

                onPresenterCreated(presenter);
            }

            @Override
            public void onLoaderReset(Loader<P> loader) {
                if (isPresenterAvailable()) {
                    presenter.detachView();
                    presenter = null;
                }

                onPresenterDestroyed();
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        onPresenterStateRestore(presenter, savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        onPresenterStateSave(presenter, outState);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    protected abstract PresenterFactory<P> createPresenterFactory();

    @LayoutRes
    protected abstract int getLayout();

    protected abstract void onPresenterCreated(@NonNull P presenter);

    protected abstract void onPresenterStateRestore(@NonNull final P presenter, @Nullable final Bundle savedInstanceState);

    protected abstract void onPresenterStateSave(@NonNull final P presenter, @NonNull final Bundle outState);

    protected abstract void onPresenterDestroyed();

    protected P getPresenter() {
        return presenter;
    }

    protected boolean isPresenterAvailable() {
        return presenter != null;
    }

    @SuppressWarnings("all")
    protected V getPresenterView() {
        V view = null;

        try {
            view = (V) this;
        } catch (final ClassCastException ex) {
            Log.e(TAG, "You should implement your view class in the activity.", ex.getCause());
        }

        return view;
    }
}
