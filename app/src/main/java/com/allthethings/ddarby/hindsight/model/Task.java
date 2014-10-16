package com.allthethings.ddarby.hindsight.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ddarby on 10/13/14.
 */
public class Task implements Parcelable {

    private int id;
    private int pomodoroId;
    private String title;
    private String todo;
    private boolean finished;
    private Date timestamp;

    public Task() {
        this("", "", false);
    }

    public Task(Parcel in) {
        readFromParcel(in);
    }

    public Task(String title, String todo, boolean finished) {
        this(title, todo, finished, new Date());
    }

    public Task(String title, String todo, boolean finished, Date timestamp) {
        this(-1, -1, title, todo, finished, timestamp);
    }

    public Task(int id, int pomodoroId, String title, String todo, boolean finished, Date timestamp) {
        this.id = id;
        this.pomodoroId = pomodoroId;
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

    public int getPomodoroId() {
        return pomodoroId;
    }

    public void setPomodoroId(int pomodoroId) {
        this.pomodoroId = pomodoroId;
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
    public int describeContents() {
        return id;
    }

    public void readFromParcel(Parcel in) {
        id = in.readInt();
        pomodoroId = in.readInt();
        title = in.readString();
        todo = in.readString();
        finished = in.readInt() == 1;
        timestamp = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeInt(pomodoroId);
        out.writeString(title);
        out.writeString(todo);
        out.writeInt(finished ? 1 : 0);
        out.writeLong(timestamp.getTime());
    }

    @Override
    public boolean equals(Object task1) {
        if (task1 instanceof Task) {
            return id == ((Task) task1).getId();
        }

        return false;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", pomodoroId=" + pomodoroId +
                ", title='" + title + '\'' +
                ", todo='" + todo + '\'' +
                ", finished=" + finished +
                ", timestamp=" + timestamp +
                '}';
    }
}
