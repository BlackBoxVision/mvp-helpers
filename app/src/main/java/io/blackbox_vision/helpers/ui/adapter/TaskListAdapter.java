package io.blackbox_vision.helpers.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.blackbox_vision.helpers.R;
import io.blackbox_vision.helpers.logic.model.Task;


public final class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private OnItemSelectedListener<Task> onItemSelectedListener;
    private Context context;
    private List<Task> items;

    public TaskListAdapter(@NonNull Context context, @NonNull List<Task> taskList) {
        this.context = context;
        this.items = taskList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Task task = items.get(position);

        String title = "";
        String description = "";

        if (null != task.getTitle()) {
            title += task.getTitle();
        }

        if (null != task.getDescription()) {
            description += task.getDescription();
        }

        holder.itemView.setOnClickListener((v) -> onItemSelectedListener.onItemSelected(v, task, position));

        holder.titleTextView.setCompoundDrawablePadding(8);
        holder.descriptionTextView.setCompoundDrawablePadding(8);

        holder.titleTextView.setText(title);
        holder.titleTextView.setTextColor(ContextCompat.getColor(context, android.R.color.primary_text_light));
        holder.titleTextView.setTextSize(16f);

        holder.descriptionTextView.setText(description);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Task> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener<Task> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titleTextView)
        TextView titleTextView;

        @BindView(R.id.descriptionTextView)
        TextView descriptionTextView;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemSelectedListener<T> {

        void onItemSelected(@NonNull View view, @NonNull T data, int position);
    }
}
