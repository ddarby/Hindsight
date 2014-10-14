package com.allthethings.ddarby.hindsight;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ddarby on 10/13/14.
 */
public class Pomodoro {
    private ArrayList<Task> pomodoroTasks;
    private Date dateCreated;
    private String title;

    public Pomodoro(ArrayList<Task> pomodoroTasks, Date dateCreated, String title) {
        this.pomodoroTasks = pomodoroTasks;
        this.dateCreated = dateCreated;
        this.title = title;
    }

    public ArrayList<Task> getPomodoroTasks() {
        return pomodoroTasks;
    }

    public void setPomodoroTasks(ArrayList<Task> pomodoroTasks) {
        this.pomodoroTasks = pomodoroTasks;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Pomodoro{" +
                "pomodoroTasks=" + pomodoroTasks +
                ", dateCreated=" + dateCreated +
                ", title='" + title + '\'' +
                '}';
    }
}
