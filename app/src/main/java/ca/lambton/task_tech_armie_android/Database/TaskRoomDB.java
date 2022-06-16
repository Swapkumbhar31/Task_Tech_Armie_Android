package ca.lambton.task_tech_armie_android.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class, Category.class}, version = 1)
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
    public abstract CategoryDAO categoryDAO();
}
