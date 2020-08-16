package com.example.simplelist.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.simplelist.database.TaskManagerDBHelper;
import com.example.simplelist.database.TaskManagerSchema;
import com.example.simplelist.database.cursorwrapper.TaskCursorWrapper;
import com.example.simplelist.database.cursorwrapper.UserCursorWrapper;
import com.example.simplelist.model.Task;
import com.example.simplelist.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDBRepository implements IRepository<User> {
    private static UserDBRepository sUserDBRepository;
    private static Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    public static UserDBRepository getInstance(Context context){
        mContext = context.getApplicationContext();
        if (sUserDBRepository == null)
            sUserDBRepository = new UserDBRepository();
        return sUserDBRepository;
    }

    private UserDBRepository() {
        TaskManagerDBHelper taskManagerDBHelper = new TaskManagerDBHelper(mContext);
        mSQLiteDatabase = taskManagerDBHelper.getWritableDatabase();
    }



    @Override
    public List<User> getList() {
        List<User> users = new ArrayList<>();
        UserCursorWrapper cursorWrapper  = queryUser(null , null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                users.add(cursorWrapper.getUser());
                cursorWrapper.moveToNext();
            }

        }
        finally {
            cursorWrapper.close();
        }
        return users;
    }

    @Override
    public void setList(List<User> list) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void insert(User user) {
        mSQLiteDatabase.insert(TaskManagerSchema.UserTable.NAME , null , getUserContentValues(user));
    }

    @Override
    public User get(UUID i) {
        String where  = TaskManagerSchema.TaskTable.COLS.UUID + "=?";
        String[] whereArgs = {i.toString()};
        UserCursorWrapper userCursorWrapper = queryUser(where,whereArgs);

        try {
            userCursorWrapper.moveToFirst();
            return userCursorWrapper.getUser();
        }
        finally {
            userCursorWrapper.close();
        }
    }

    private ContentValues getUserContentValues(User user){
        ContentValues values = new ContentValues();
        values.put(TaskManagerSchema.UserTable.COLS.USERNAME , user.getUserName());
        values.put(TaskManagerSchema.UserTable.COLS.USER_ID , user.getUser_ID());
        return values;
    }

    public void update(User user){
        ContentValues values = getUserContentValues(user);
        String where= TaskManagerSchema.UserTable.COLS.USER_ID + "=?";
        String[]  whereArgs = {user.getUser_ID()};
        mSQLiteDatabase.update(TaskManagerSchema.UserTable.NAME ,values ,where, whereArgs);
    }

    public void delete(User user){
        String where= TaskManagerSchema.UserTable.COLS.USER_ID + "=?";
        String[]  whereArgs = {user.getUser_ID()};
        mSQLiteDatabase.delete(TaskManagerSchema.UserTable.NAME , where , whereArgs);
    }

    private UserCursorWrapper queryUser(String selection , String[] selectionArgs){
        Cursor cursor = mSQLiteDatabase.query(TaskManagerSchema.UserTable.NAME ,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);

        UserCursorWrapper userCursorWrapper = new UserCursorWrapper(cursor);
        return userCursorWrapper;
    }
}
