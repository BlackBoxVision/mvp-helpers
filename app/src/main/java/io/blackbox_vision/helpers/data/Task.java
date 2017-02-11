package io.blackbox_vision.helpers.data;

import com.orm.SugarRecord;

import java.util.Date;


public final class Task extends SugarRecord {
    private String title;
    private String description;
    private String startDate;
    private String dueDate;

    private Date createdAt;

    private boolean isCompleted;

    public String getTitle() {
        return title;
    }

    public Task setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public Task setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getDueDate() {
        return dueDate;
    }

    public Task setDueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Task setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public Task setCompleted(boolean completed) {
        isCompleted = completed;
        return this;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
