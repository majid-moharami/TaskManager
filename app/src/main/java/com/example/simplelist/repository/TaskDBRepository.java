package com.example.simplelist.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.simplelist.database.TaskManagerDatabase;
import com.example.simplelist.model.Task;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class TaskDBRepository implements IRepository<Task>  {

    private static TaskDBRepository sTaskDBRepository;
    private static Context mContext;
    private TaskManagerDatabase mDatabase;

    public static TaskDBRepository getInstance(Context context){
        mContext = context.getApplicationContext();
        if (sTaskDBRepository == null)
            sTaskDBRepository = new TaskDBRepository(mContext);
        return sTaskDBRepository;
    }

    private TaskDBRepository(Context context) {
        mDatabase = Room.databaseBuilder(mContext,TaskManagerDatabase.class,"taskManager.db").allowMainThreadQueries().build();
    }

    @Override
    public List<Task> getList() {
       return mDatabase.taskDao().getList();
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

        mDatabase.taskDao().insert(task);

    }

    @Override
    public Task get(UUID i) {
       return mDatabase.taskDao().get(i.toString());
    }

    public void update(Task task){
        mDatabase.taskDao().update(task);
    }

    public void delete(Task task){
        mDatabase.taskDao().delete(task);
    }

    public File getPhotoFile(Context context  , Task task){
        return new File(context.getFilesDir(),task.getPhotoFileName());
    }
//
//    private ContentValues getTaskContentValues(Task task){
//        ContentValues values = new ContentValues();
//        values.put(COLS.NAME , task.getTitle());
//        values.put(COLS.UUID , task.getTaskID().toString());
//        values.put(COLS.DATE , task.getDate().getTime());
//        values.put(COLS.STATE , task.getStats().toString());
//        values.put(COLS.DESCRIPTION , task.getDescription());
//        values.put(COLS.USER_ID , task.getUserID());
//        return values;
//    }
//
//    private TaskCursorWrapper queryTask(String selection , String[] selectionArgs){
//        Cursor cursor = mDatabase.query(TaskManagerSchema.TaskTable.NAME ,
//                null,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                null);
//
//        TaskCursorWrapper taskCursorWrapper = new TaskCursorWrapper(cursor);
//        return taskCursorWrapper;
//    }
}
