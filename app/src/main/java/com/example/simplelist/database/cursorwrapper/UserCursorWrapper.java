package com.example.simplelist.database.cursorwrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.simplelist.database.TaskManagerSchema;
import com.example.simplelist.model.User;

public class UserCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser(){
        String name = getString(getColumnIndex((TaskManagerSchema.UserTable.COLS.USERNAME)));
        String userId = getString(getColumnIndex((TaskManagerSchema.UserTable.COLS.USER_ID)));
        User user = new User(name ,userId);
        return user;
    }
}
