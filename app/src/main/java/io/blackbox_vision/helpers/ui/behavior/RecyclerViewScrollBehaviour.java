package io.blackbox_vision.helpers.ui.behavior;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

public final class RecyclerViewScrollBehaviour extends RecyclerView.OnScrollListener {
    private final FloatingActionButton fab;

    public RecyclerViewScrollBehaviour(@NonNull FloatingActionButton fab) {
        this.fab = fab;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_SETTLING:
            case RecyclerView.SCROLL_STATE_DRAGGING:
                fab.hide();
                break;
            case RecyclerView.SCROLL_STATE_IDLE:
            default:
                fab.show();
                break;
        }

        super.onScrollStateChanged(recyclerView, newState);
    }
}
