package com.example.simplelist.database.cursorwrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.simplelist.Stats;
import com.example.simplelist.database.TaskManagerSchema;
import com.example.simplelist.model.Task;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask(){
        String name = getString(getColumnIndex(TaskManagerSchema.TaskTable.COLS.NAME));
        String uuid = getString(getColumnIndex(TaskManagerSchema.TaskTable.COLS.UUID));
        String User_id = getString(getColumnIndex(TaskManagerSchema.TaskTable.COLS.USER_ID));
        String state = getString(getColumnIndex(TaskManagerSchema.TaskTable.COLS.STATE));
        Date date =new Date( getLong(getColumnIndex(TaskManagerSchema.TaskTable.COLS.DATE)));
        String description = getString(getColumnIndex(TaskManagerSchema.TaskTable.COLS.DESCRIPTION));
        Stats stats=Stats.TODO;
        if (state.equals("DOING"))
            stats = Stats.DOING;
        if (state.equals("DONE"))
            stats = Stats.DONE;
        Task task = new Task(name, stats,date,description, UUID.fromString(uuid) ,User_id);
        return task;
    }
}
