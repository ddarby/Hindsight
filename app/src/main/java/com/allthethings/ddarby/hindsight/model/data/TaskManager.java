package com.allthethings.ddarby.hindsight.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.allthethings.ddarby.hindsight.model.Task;

import java.util.ArrayList;

public class TaskManager extends SQLiteOpenHelper {

    private static final String TABLE_TASK = "task";
    public static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TODO = "todo";
    private static final String KEY_FINISHED = "finished";
    private static final String KEY_TIMESTAMP = "timestamp";

    public static final String[] ALL_COLS = new String[] { KEY_ID, KEY_TITLE, KEY_TODO, KEY_FINISHED, KEY_TIMESTAMP };

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;
    private static TaskManager instance;

    // Database creation sql statement
    private final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_TASK + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_TODO + " TEXT,"
            + KEY_FINISHED + " INTEGER," + KEY_TIMESTAMP + " TEXT" + ")";

    private TaskManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TaskManager.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    public static synchronized void initializeInstance(Context context) {
        if (instance == null) {
            instance = new TaskManager(context);
        } else {
            throw new IllegalStateException(TaskManager.class.getSimpleName() + " was already initialized.");
        }
    }

    public static TaskManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(TaskManager.class.getSimpleName() + " is not initialized, call initializeInstance(..) method first.");
        }
        return instance;
    }

    public synchronized SQLiteDatabase getDatabaseInstance() {
        if (db == null || !db.isOpen()) {
            db = super.getWritableDatabase();
        }
        return db;
    }

    public synchronized void closeDatabaseInstance() {
        try {
            if (db != null) {
                db.close();
                db = null;
            }
        } catch (Throwable t) {db = null;}
    }

    public synchronized void insertOrUpdateTask(Task task) {
        if (task == null) {
            return;
        }

        Task storedTask = getTask(task);
        db = getDatabaseInstance();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, task.getId());
        values.put(KEY_TITLE, task.getTitle());
        values.put(KEY_TODO, task.getTodo());
        values.put(KEY_FINISHED, task.isFinished() ? 1 : 0);
        if (storedTask != null) {
            values.put(KEY_TIMESTAMP, task.getTimestamp().getTime());
        }
        db.insertWithOnConflict(TABLE_TASK, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        closeDatabaseInstance();
    }

    public synchronized Task getTask(Task task) {
        if (task == null) {
            return null;
        }

        return getTask(task.getId());
    }

    public synchronized Task getTask(int id) {
        Task task = null;
        db = getDatabaseInstance();

        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_TASK, ALL_COLS,
                    KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                task = getTaskFromCursor(cursor);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            closeDatabaseInstance();
        }

        return task;
    }

    public synchronized ArrayList<Task> getAll() {
        ArrayList<Task> tasks = new ArrayList<Task>();

        db = getDatabaseInstance();
        Cursor cursor = db.query(true, TABLE_TASK, ALL_COLS, null, null, null, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Task task = getTaskFromCursor(cursor);
                    if (task != null) {
                        tasks.add(task);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            closeDatabaseInstance();
        }

        return tasks;
    }

    public synchronized void deleteTask(Task task) {
        if (task == null) {
            return;
        }

        db = getDatabaseInstance();
        try {
            db.delete(TABLE_TASK, KEY_ID + "=?",
                    new String[]{ String.valueOf(task.getId()) });
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            closeDatabaseInstance();
        }
    }

    public synchronized void deleteDatabase() {
        db = getDatabaseInstance();
        try {
            db.delete(TABLE_TASK, null, null);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            closeDatabaseInstance();
        }
    }

    public synchronized Task getTaskFromCursor(Cursor cursor) {
        Task task= null;
        try {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int titleIndex = cursor.getColumnIndex(KEY_TITLE);
            int todoIndex = cursor.getColumnIndex(KEY_TODO);
            int finishedIndex = cursor.getColumnIndex(KEY_FINISHED);
            int timestampIndex = cursor.getColumnIndex(KEY_TIMESTAMP);
            int id = cursor.getInt(idIndex);
            String title = cursor.getString(titleIndex);
            String todo = cursor.getString(todoIndex);
            boolean finished = cursor.getInt(finishedIndex) == 1;
            task = new Task(title, todo, finished);
            task.setId(id);
            try {
                task.setTimestamp(Long.parseLong(cursor.getString(timestampIndex)));
            } catch (Throwable t) {
                // do nothing. no timestamp recorded for this listing
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return task;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        throw new IllegalStateException(TaskManager.class.getSimpleName() + " should not directly return a database. Use getDatabaseInstance instead.");
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        throw new IllegalStateException(TaskManager.class.getSimpleName() + " should not directly return a database. Use getDatabaseInstance instead.");
    }
}