package io.blackbox_vision.helpers.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.blackbox_vision.helpers.R;
import io.blackbox_vision.helpers.helper.DrawableUtils;
import io.blackbox_vision.helpers.logic.model.Task;


public final class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private static final String DELIMITER = ": ";

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

        String title = context.getText(R.string.title) + DELIMITER;
        String description = context.getText(R.string.description) + DELIMITER;
        String startDate = context.getText(R.string.start_date) + DELIMITER;
        String dueDate = context.getText(R.string.due_date) + DELIMITER;

        if (null != task.getTitle()) {
            title += task.getTitle();
        }

        if (null != task.getDescription()) {
            description += task.getDescription();
        }

        if (null != task.getStartDate()) {
            startDate += task.getStartDate();
        }

        if (null != task.getDueDate()) {
            dueDate += task.getDueDate();
        }

        Drawable titleDrawable = DrawableUtils.applyColorFilter(context, R.drawable.ic_title_black_24dp);
        Drawable descriptionDrawable = DrawableUtils.applyColorFilter(context, R.drawable.ic_description_black_24dp);
        Drawable dateDrawable = DrawableUtils.applyColorFilter(context, R.drawable.ic_date_range_black_24dp);

        holder.itemView.setOnClickListener((v) -> onItemSelectedListener.onItemSelected(v, task, position));

        holder.titleTextView.setCompoundDrawablesWithIntrinsicBounds(titleDrawable, null, null, null);
        holder.descriptionTextView.setCompoundDrawablesWithIntrinsicBounds(descriptionDrawable, null, null, null);
        holder.startDateTextView.setCompoundDrawablesWithIntrinsicBounds(dateDrawable, null, null, null);
        holder.dueDateTextView.setCompoundDrawablesWithIntrinsicBounds(dateDrawable, null, null, null);

        holder.titleTextView.setCompoundDrawablePadding(8);
        holder.descriptionTextView.setCompoundDrawablePadding(8);
        holder.startDateTextView.setCompoundDrawablePadding(8);
        holder.dueDateTextView.setCompoundDrawablePadding(8);

        holder.titleTextView.setText(title);
        holder.descriptionTextView.setText(description);
        holder.startDateTextView.setText(startDate);
        holder.dueDateTextView.setText(dueDate);
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

        @BindView(R.id.startDateTextView)
        TextView startDateTextView;

        @BindView(R.id.dueDateTextView)
        TextView dueDateTextView;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemSelectedListener<T> {

        void onItemSelected(@NonNull View view, @NonNull T data, int position);
    }
}
