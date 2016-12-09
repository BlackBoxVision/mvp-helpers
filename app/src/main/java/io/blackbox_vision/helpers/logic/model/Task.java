package io.blackbox_vision.helpers.logic.model;

import java.util.Date;


public final class Task {
    private int id;
    private String title;
    private String description;
    private Date initialDate;
    private Date endDate;

    public int getId() {
        return id;
    }

    public Task setId(int id) {
        this.id = id;
        return this;
    }

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

    public Date getInitialDate() {
        return initialDate;
    }

    public Task setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Task setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", initialDate=" + initialDate +
                ", endDate=" + endDate +
                '}';
    }
}
