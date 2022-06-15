package ca.lambton.task_tech_armie_android.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public abstract class TaskRoomDB extends RoomDatabase {

    public static final String DATABASE_NAME = "tasks";

    public static volatile TaskRoomDB taskRoomDB;

    public static TaskRoomDB getInstance(Context context) {
        if (taskRoomDB == null) {
            taskRoomDB = Room
                    .databaseBuilder(context, TaskRoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return taskRoomDB;
    }

    public abstract TaskDAO taskDAO();
}
