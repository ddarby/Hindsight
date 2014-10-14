package com.allthethings.ddarby.hindsight;

/**
 * Created by ddarby on 10/13/14.
 */
public class Task {
    private String title;
    private String todo;
    private boolean finished;

    public Task(String title, String todo, boolean finished) {
        this.title = title;
        this.todo = todo;
        this.finished = finished;
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

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", todo='" + todo + '\'' +
                ", finished=" + finished +
                '}';
    }
}
