package com.allthethings.ddarby.hindsight.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ddarby on 10/13/14.
 */
public class Pomodoro implements Parcelable {

    private int id;
    private String title;
    private ArrayList<Task> pomodoroTasks;
    private Date timestamp;

    public Pomodoro(Parcel in) {
        readFromParcel(in);
    }

    public Pomodoro(String title) {
        this(title, new Date());
    }

    public Pomodoro(String title, Date timestamp) {
        this(title, new ArrayList<Task>(), timestamp);
    }

    public Pomodoro(String title, ArrayList<Task> pomodoroTasks) {
        this(title, pomodoroTasks, new Date());
    }

    public Pomodoro(String title, ArrayList<Task> pomodoroTasks, Date timestamp) {
        this(-1, title, pomodoroTasks, timestamp);
    }

    public Pomodoro(int id, String title, ArrayList<Task> pomodoroTasks, Date timestamp) {
        this.id = id;
        this.title = title;
        this.pomodoroTasks = pomodoroTasks;
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

    public ArrayList<Task> getPomodoroTasks() {
        return pomodoroTasks;
    }

    public void setPomodoroTasks(ArrayList<Task> pomodoroTasks) {
        this.pomodoroTasks = pomodoroTasks;
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
    public int describeContents() {
        return id;
    }

    public void readFromParcel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        pomodoroTasks = in.readArrayList(Task.class.getClassLoader());
        timestamp = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeString(title);
        out.writeList(pomodoroTasks);
        out.writeLong(timestamp.getTime());
    }

    @Override
    public boolean equals(Object pomodoro1) {
        if (pomodoro1 instanceof Pomodoro) {
            return id == ((Pomodoro) pomodoro1).getId();
        }

        return false;
    }

    @Override
    public String toString() {
        return "Pomodoro{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pomodoroTasks=" + pomodoroTasks +
                ", timestamp=" + timestamp +
                '}';
    }
}
