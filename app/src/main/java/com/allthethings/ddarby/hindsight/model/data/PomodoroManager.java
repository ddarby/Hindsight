package com.allthethings.ddarby.hindsight.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.allthethings.ddarby.hindsight.model.Pomodoro;
import com.allthethings.ddarby.hindsight.model.Task;
import com.allthethings.ddarby.hindsight.util.HindsightUtils;

import java.util.ArrayList;

public class PomodoroManager extends SQLiteOpenHelper {

    private final String TABLE_POMODORO = "pomodoro";
    private final String TABLE_TASK = "task";

    /**
     * Shared Keys
     */
    private final String KEY_ID = "_id";
    private final String KEY_TITLE = "title";
    private final String KEY_TIMESTAMP = "timestamp";

    /**
     * Pomodoro Keys
     */

    /**
     * Task Keys
     */
    private final String KEY_POMODORO_ID = "_pomodoroId";
    private final String KEY_TODO = "todo";
    private final String KEY_FINISHED = "finished";

    public final String[] ALL_POMODORO_COLS = new String[] { KEY_ID, KEY_TITLE, KEY_TIMESTAMP };
    public final String[] ALL_TASK_COLS = new String[] { KEY_ID, KEY_POMODORO_ID, KEY_TITLE, KEY_TODO, KEY_FINISHED, KEY_TIMESTAMP };

    private static final String DATABASE_NAME = "pomodoro.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;
    private static PomodoroManager instance;

    // Pomodoro Table creation sql statement
    private final String POMODORO_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_POMODORO + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_TIMESTAMP + " TEXT" + ")";

    // Task Table creation sql statement
    private final String TASK_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_TASK + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_POMODORO_ID + " INTEGER," + KEY_TITLE
            + " TEXT," + KEY_TODO + " TEXT," + KEY_FINISHED + " INTEGER," + KEY_TIMESTAMP + " TEXT," + " FOREIGN KEY (" + KEY_POMODORO_ID + ") REFERENCES "
            + TABLE_POMODORO + "(" + KEY_ID + ") ON DELETE CASCADE )";

    private PomodoroManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Database Calls
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("PRAGMA foreign_keys=ON;");
        database.execSQL(POMODORO_TABLE_CREATE);
        database.execSQL(TASK_TABLE_CREATE);    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(PomodoroManager.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    public static synchronized void initializeInstance(Context context) {
        if (instance == null) {
            instance = new PomodoroManager(context);
        } else {
            throw new IllegalStateException(PomodoroManager.class.getSimpleName() + " was already initialized.");
        }
    }

    public static PomodoroManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(PomodoroManager.class.getSimpleName() + " is not initialized, call initializeInstance(..) method first.");
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

    public synchronized void deleteDatabase() {
        deleteTaskTable();
        deletePomodoroTable();
    }

    private synchronized void deletePomodoroTable() {
        db = getDatabaseInstance();
        try {
            db.delete(TABLE_TASK, null, null);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            closeDatabaseInstance();
        }
    }

    private synchronized void deleteTaskTable() {
        db = getDatabaseInstance();
        try {
            db.delete(TABLE_TASK, null, null);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            closeDatabaseInstance();
        }
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        throw new IllegalStateException(PomodoroManager.class.getSimpleName() + " should not directly return a database. Use getDatabaseInstance instead.");
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        throw new IllegalStateException(PomodoroManager.class.getSimpleName() + " should not directly return a database. Use getDatabaseInstance instead.");
    }

    /**
     * Pomodoro Table Calls
     */
    public synchronized Pomodoro insertOrUpdatePomodoro(Pomodoro pomodoro) {
        if (pomodoro == null) {
            return pomodoro;
        }

        Pomodoro storedPomodoro = getPomodoro(pomodoro);
        db = getDatabaseInstance();
        ContentValues values = new ContentValues();
        if (storedPomodoro != null) {
            values.put(KEY_ID, storedPomodoro.getId());
        }
        values.put(KEY_TITLE, pomodoro.getTitle());
        if (storedPomodoro != null) {
            values.put(KEY_TIMESTAMP, pomodoro.getTimestamp().getTime());
        }
        pomodoro.setId((int) db.insertWithOnConflict(TABLE_POMODORO, null, values, SQLiteDatabase.CONFLICT_REPLACE));
        closeDatabaseInstance();

        return pomodoro;
    }

    public synchronized Pomodoro getPomodoro(Pomodoro pomodoro) {
        if (pomodoro == null) {
            return null;
        }

        return getPomodoro(pomodoro.getId());
    }

    public synchronized Pomodoro getPomodoro(int id) {
        Pomodoro pomodoro = null;
        db = getDatabaseInstance();

        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_POMODORO, ALL_POMODORO_COLS, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                pomodoro = getPomodoroFromCursor(cursor);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            closeDatabaseInstance();
        }

        return pomodoro;
    }

    public synchronized ArrayList<Pomodoro> getAllPomodoros() {
        ArrayList<Pomodoro> pomodoros = new ArrayList<Pomodoro>();

        db = getDatabaseInstance();
        Cursor cursor = db.query(true, TABLE_POMODORO, ALL_POMODORO_COLS, null, null, null, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Pomodoro pomodoro = getPomodoroFromCursor(cursor);
                    if (pomodoro != null) {
                        pomodoros.add(pomodoro);
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

        return pomodoros;
    }

    public synchronized void deletePomorodo(Pomodoro pomodoro) {
        if (pomodoro == null) {
            return;
        }

        deletePomorodo(pomodoro.getId());
    }

    public synchronized void deletePomorodo(int id) {
        db = getDatabaseInstance();
        try {
            db.delete(TABLE_POMODORO, KEY_ID + "=?", new String[]{ String.valueOf(id) });
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            closeDatabaseInstance();
        }
    }

    public synchronized Pomodoro getPomodoroFromCursor(Cursor cursor) {
        Pomodoro pomodoro= null;
        try {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int titleIndex = cursor.getColumnIndex(KEY_TITLE);
            int timestampIndex = cursor.getColumnIndex(KEY_TIMESTAMP);
            int id = cursor.getInt(idIndex);
            String title = cursor.getString(titleIndex);
            pomodoro = new Pomodoro(title, getAllTasks(id));
            pomodoro.setId(id);
            try {
                pomodoro.setTimestamp(Long.parseLong(cursor.getString(timestampIndex)));
            } catch (Throwable t) {
                // do nothing. no timestamp recorded for this listing
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return pomodoro;
    }

    /**
     * Task Table Calls
     */
    public synchronized Task insertOrUpdateTask(Task task) {
        if (task == null) {
            return task;
        }

        Task storedTask = getTask(task);
        db = getDatabaseInstance();
        ContentValues values = new ContentValues();
        if (storedTask != null) {
            values.put(KEY_ID, storedTask.getId());
        }
        values.put(KEY_POMODORO_ID, task.getPomodoroId());
        values.put(KEY_TITLE, task.getTitle());
        values.put(KEY_TODO, task.getTodo());
        values.put(KEY_FINISHED, task.isFinished() ? 1 : 0);
        if (storedTask != null) {
            values.put(KEY_TIMESTAMP, task.getTimestamp().getTime());
        }
        task.setId((int) db.insertWithOnConflict(TABLE_TASK, null, values, SQLiteDatabase.CONFLICT_REPLACE));
        closeDatabaseInstance();

        return task;
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
            cursor = db.query(TABLE_TASK, ALL_TASK_COLS, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
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

    public synchronized ArrayList<Task> getAllTasks(int pomodoroId) {
        ArrayList<Task> tasks = new ArrayList<Task>();

        db = getDatabaseInstance();
        Cursor cursor = db.query(TABLE_TASK, ALL_TASK_COLS, KEY_POMODORO_ID + "=?", new String[] { String.valueOf(pomodoroId) }, null, null, null, null);
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

    public synchronized ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();

        db = getDatabaseInstance();
        Cursor cursor = db.query(true, TABLE_TASK, ALL_TASK_COLS, null, null, null, null, null, null);

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

        deleteTask(task.getId());
    }

    public synchronized void deleteTask(int id) {
        db = getDatabaseInstance();
        try {
            db.delete(TABLE_TASK, KEY_ID + "=?", new String[]{ String.valueOf(id) });
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            closeDatabaseInstance();
        }
    }

    public synchronized void deleteTasks(int[] taskIds) {
        String args = HindsightUtils.joinTasks(", ", taskIds);
        db = getDatabaseInstance();
        try {
            db.execSQL("DELETE FROM " + TABLE_TASK + " WHERE " + KEY_ID + " IN (" + args + ");");
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
            int pomodoroIdIndex = cursor.getColumnIndex(KEY_POMODORO_ID);
            int titleIndex = cursor.getColumnIndex(KEY_TITLE);
            int todoIndex = cursor.getColumnIndex(KEY_TODO);
            int finishedIndex = cursor.getColumnIndex(KEY_FINISHED);
            int timestampIndex = cursor.getColumnIndex(KEY_TIMESTAMP);
            int id = cursor.getInt(idIndex);
            int pomodoroId = cursor.getInt(pomodoroIdIndex);
            String title = cursor.getString(titleIndex);
            String todo = cursor.getString(todoIndex);
            boolean finished = cursor.getInt(finishedIndex) == 1;
            task = new Task(title, todo, finished);
            task.setId(id);
            task.setPomodoroId(pomodoroId);
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
}