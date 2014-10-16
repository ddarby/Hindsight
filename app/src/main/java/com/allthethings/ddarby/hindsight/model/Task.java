package com.allthethings.ddarby.hindsight.model;

import java.util.Date;

/**
 * Created by ddarby on 10/13/14.
 */
public class Task {

    private int id;
    private String title;
    private String todo;
    private boolean finished;
    private Date timestamp;

    public Task() {
        this.id = -1;
        this.title = "unknown";
        this.todo = "unknown";
        this.finished = true;
        this.timestamp = new Date();
    }

    public Task(String title, String todo, boolean finished) {
        this.title = title;
        this.todo = todo;
        this.finished = finished;
        this.timestamp = new Date();
    }

    public Task(String title, String todo, boolean finished, Date timestamp) {
        this.title = title;
        this.todo = todo;
        this.finished = finished;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = new Date(timestamp);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", todo='" + todo + '\'' +
                ", finished=" + finished +
                '}';
    }
}
