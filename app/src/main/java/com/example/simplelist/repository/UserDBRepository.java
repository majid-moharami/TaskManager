package com.example.simplelist.repository;

import android.content.ContentValues;
import android.content.Context;

import androidx.room.Room;

import com.example.simplelist.database.TaskManagerDatabase;
import com.example.simplelist.model.User;

import java.util.List;
import java.util.UUID;

public class UserDBRepository implements IRepository<User> {
    private static UserDBRepository sUserDBRepository;
    private static Context mContext;
    private TaskManagerDatabase mDatabase;

    public static UserDBRepository getInstance(Context context){
        mContext = context.getApplicationContext();
        if (sUserDBRepository == null)
            sUserDBRepository = new UserDBRepository();
        return sUserDBRepository;
    }

    private UserDBRepository() {
       mDatabase = Room.databaseBuilder(mContext,TaskManagerDatabase.class,"taskManager.db").allowMainThreadQueries().build();
    }



    @Override
    public List<User> getList() {
        return mDatabase.userDao().getList();
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
        mDatabase.userDao().insert(user);
    }

    @Override
    public User get(UUID i) {
//        String where  = TaskManagerSchema.TaskTable.COLS.UUID + "=?";
//        String[] whereArgs = {i.toString()};
//        //UserCursorWrapper userCursorWrapper = queryUser(where,whereArgs);
//
//        try {
//            userCursorWrapper.moveToFirst();
//            return userCursorWrapper.getUser();
//        }
//        finally {
//            userCursorWrapper.close();
//        }
        return new User();
    }

//    private ContentValues getUserContentValues(User user){
//        ContentValues values = new ContentValues();
//        values.put(TaskManagerSchema.UserTable.COLS.USERNAME , user.getUserName());
//        values.put(TaskManagerSchema.UserTable.COLS.USER_ID , user.getUser_ID());
//        return values;
//    }

    public void update(User user){
       mDatabase.userDao().update(user);
    }

    public void delete(User user){
       mDatabase.userDao().delete(user);
    }

//    private UserCursorWrapper queryUser(String selection , String[] selectionArgs){
//        Cursor cursor = mDatabase.query(TaskManagerSchema.UserTable.NAME ,
//                null,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                null);
//
//        UserCursorWrapper userCursorWrapper = new UserCursorWrapper(cursor);
//        return userCursorWrapper;
//    }
}
