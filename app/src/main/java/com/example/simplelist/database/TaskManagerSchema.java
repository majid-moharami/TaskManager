package com.example.simplelist.database;

public class TaskManagerSchema {
    public static final String NAME = "TaskManager.db";
    public static final int VERSION = 1;

    public static class TaskTable{
        public static final String NAME = "TaskTable";

        public static class COLS{
            public static final String ID = "id";
            public static final String NAME = "TaskName";
            public static final String STATE = "TaskState";
            public static final String DATE = "TaskDate";
            public static final String DESCRIPTION = "TaskDescription";
            public static final String UUID= "TaskUUID";
            public static final String USER_ID = "TaskUserId";
        }
    }

    public static class UserTable{
        public static final String NAME = "UserTable";

        public static class COLS{
            public static final String ID = "id";
            public static final String USERNAME = "UserName";
            public static final String USER_ID = "UserID";

        }
    }
}
