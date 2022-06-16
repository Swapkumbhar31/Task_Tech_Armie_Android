package ca.lambton.task_tech_armie_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ca.lambton.task_tech_armie_android.Database.TaskRoomDB;

public class MainActivity extends AppCompatActivity {

    private TaskRoomDB taskRoomDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskRoomDB = TaskRoomDB.getInstance(this);
        taskRoomDB.taskDAO().getAllTasks();
    }
}