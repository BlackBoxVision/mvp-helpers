package io.blackbox_vision.helpers.logic.model;

import com.orm.SugarRecord;

import java.util.Date;


public final class Task extends SugarRecord {
    private String title;
    private String description;
    private Date startDate;
    private Date dueDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public Task setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Task setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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
