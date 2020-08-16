package com.example.simplelist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.simplelist.database.TaskManagerSchema.*;
import androidx.annotation.Nullable;

import com.example.simplelist.model.User;

public class TaskManagerDBHelper extends SQLiteOpenHelper {
    public TaskManagerDBHelper(@Nullable Context context) {
        super(context, TaskManagerSchema.NAME, null, TaskManagerSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TaskManagerSchema.TaskTable.NAME + "(" +
                TaskTable.COLS.NAME + " text," +
                TaskTable.COLS.UUID + " text," +
                TaskTable.COLS.STATE + " text," +
                TaskTable.COLS.DATE + " text," +
                TaskTable.COLS.DESCRIPTION + " test," +
                TaskTable.COLS.ID + "  integer primary key autoincrement," +
                TaskTable.COLS.USER_ID + " text" +
                ");"
                );

        db.execSQL(" CREATE TABLE " + TaskManagerSchema.UserTable.NAME + "(" +
                UserTable.COLS.USERNAME + " text," +
                UserTable.COLS.ID + "   integer primary key autoincrement," +
                UserTable.COLS.USER_ID + " text" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
