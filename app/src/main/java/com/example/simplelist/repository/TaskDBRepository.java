package com.example.simplelist.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Selection;

import com.example.simplelist.database.TaskManagerDBHelper;
import com.example.simplelist.database.TaskManagerSchema;
import com.example.simplelist.database.cursorwrapper.TaskCursorWrapper;
import com.example.simplelist.model.Task;
import com.example.simplelist.database.TaskManagerSchema.TaskTable.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDBRepository implements IRepository<Task>  {

    private static TaskDBRepository sTaskDBRepository;
    private static Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    public static TaskDBRepository getInstance(Context context){
        mContext = context.getApplicationContext();
        if (sTaskDBRepository == null)
            sTaskDBRepository = new TaskDBRepository(mContext);
        return sTaskDBRepository;
    }

    private TaskDBRepository(Context context) {
        TaskManagerDBHelper taskManagerDBHelper = new TaskManagerDBHelper(context);
        mSQLiteDatabase = taskManagerDBHelper.getWritableDatabase();
    }

    @Override
    public List<Task> getList() {
        List<Task> tasks = new ArrayList<>();
        TaskCursorWrapper cursorWrapper = queryTask(null,null);

        try {
            cursorWrapper.moveToFirst();

            while (!cursorWrapper.isAfterLast()){
                tasks.add(cursorWrapper.getTask());
                cursorWrapper.moveToNext();
            }
        }
        finally {
            cursorWrapper.close();
        }
        return tasks;
    }

    @Override
    public void setList(List<Task> list) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void insert(Task task) {

        mSQLiteDatabase.insert(TaskManagerSchema.TaskTable.NAME , null , getTaskContentValues(task));

    }

    @Override
    public Task get(UUID i) {
        String where  = COLS.UUID + "=?";
        String[] whereArgs = {i.toString()};
        TaskCursorWrapper cursorWrapper = queryTask(where,whereArgs);

        try {
            cursorWrapper.moveToFirst();
            return cursorWrapper.getTask();
        }
        finally {
            cursorWrapper.close();
        }
    }

    public void update(Task task){
        ContentValues values = getTaskContentValues(task);
        String where= COLS.UUID + "=?";
        String[]  whereArgs = {task.getTaskID().toString()};
        mSQLiteDatabase.update(TaskManagerSchema.TaskTable.NAME ,values ,where, whereArgs);
    }

    public void delete(Task task){
        String where = COLS.UUID + "=?";
        String[] whereArgs = new String[]{task.getTaskID().toString()};
        mSQLiteDatabase.delete(TaskManagerSchema.TaskTable.NAME , where , whereArgs);
    }

    private ContentValues getTaskContentValues(Task task){
        ContentValues values = new ContentValues();
        values.put(COLS.NAME , task.getName());
        values.put(COLS.UUID , task.getTaskID().toString());
        values.put(COLS.DATE , task.getDate().getTime());
        values.put(COLS.STATE , task.getStats().toString());
        values.put(COLS.DESCRIPTION , task.getDescription());
        values.put(COLS.USER_ID , task.getUserID());
        return values;
    }

    private TaskCursorWrapper queryTask(String selection , String[] selectionArgs){
        Cursor cursor = mSQLiteDatabase.query(TaskManagerSchema.TaskTable.NAME ,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);

        TaskCursorWrapper taskCursorWrapper = new TaskCursorWrapper(cursor);
        return taskCursorWrapper;
    }
}
