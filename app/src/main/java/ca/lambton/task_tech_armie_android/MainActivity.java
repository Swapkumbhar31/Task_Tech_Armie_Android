package ca.lambton.task_tech_armie_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import ca.lambton.task_tech_armie_android.Adaptor.TaskListAdaptor;
import ca.lambton.task_tech_armie_android.Database.Category;
import ca.lambton.task_tech_armie_android.Database.Task;
import ca.lambton.task_tech_armie_android.Database.TaskRoomDB;
import ca.lambton.task_tech_armie_android.Helper.DateConverter;
import ca.lambton.task_tech_armie_android.SharedPreferences.UserSettings;

public class MainActivity extends AppCompatActivity {

    private TaskRoomDB taskRoomDB;

    TextView lblCurrentDate, lblTaskInfo;
    ListView lvCompleted, lvIncomplete;

    List<Task> completedTasks;
    List<Task> inCompleteTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        taskRoomDB = TaskRoomDB.getInstance(this);

        // UIObject initialization
        lblCurrentDate = findViewById(R.id.lblCurrentDate);
        lblTaskInfo = findViewById(R.id.lblTaskCompletionInfo);
        lvCompleted = findViewById(R.id.listviewCompleted);
        lvIncomplete = findViewById(R.id.listviewIncomplete);

        // Insert Dummy data

        UserSettings userSettings = new UserSettings().getInstance(getApplicationContext());
        boolean firstTimeOpen = new UserSettings().getInstance(getApplicationContext()).isFirstTimeOpen();

        if (firstTimeOpen) {
            insertCategories();
            insertTasks();
            userSettings.setIsFirstTimeOpen(false);
        }
        init();

        lvIncomplete.setAdapter(new TaskListAdaptor(this, inCompleteTasks));
        lvCompleted.setAdapter(new TaskListAdaptor(this, completedTasks));

    }

    private void loadAllTasks(){
        completedTasks = taskRoomDB.taskDAO().getAllTasks(true);
        inCompleteTasks = taskRoomDB.taskDAO().getAllTasks(false);
    }

    private void init(){
        lblCurrentDate.setText(DateConverter.getFullDate(new Date()));
        loadAllTasks();
    }

    private void insertCategories() {
        taskRoomDB.categoryDAO().addCategory(new Category("Finance"));
        taskRoomDB.categoryDAO().addCategory(new Category("Shopping"));
        taskRoomDB.categoryDAO().addCategory(new Category("Freelance"));
        taskRoomDB.categoryDAO().addCategory(new Category("Personal"));
    }

    private void insertTasks() {
        taskRoomDB.taskDAO().addTask(new Task(
                "Visit Montreal",
                new Date(1655413575),
                new Date(1655499975),
                false,
                new ArrayList<>(),
                null,
                null,
                1L,
                null
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Invest Today",
                new Date(1655413575),
                new Date(1655420775),
                false,
                new ArrayList<>(),
                null,
                null,
                1L,
                null
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Book a room",
                new Date(1655424375),
                new Date(1655510355),
                false,
                new ArrayList<>(),
                null,
                1L,
                1L,
                null
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Say hello to new friend",
                new Date(1655683200),
                new Date(1655769600),
                false,
                new ArrayList<>(),
                null,
                null,
                3L,
                null
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Soaps",
                new Date(1655856000),
                new Date(1656028800),
                false,
                new ArrayList<>(),
                null,
                null,
                2L,
                null
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Hello World Program",
                new Date(1655029800),
                new Date(1655202600),
                true,
                new ArrayList<>(),
                null,
                null,
                3L,
                new Date(1655116200)
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Attend Class",
                new Date(1655238600),
                new Date(1655245800),
                true,
                new ArrayList<>(),
                null,
                null,
                4L,
                new Date(1655238600)
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Laundry",
                new Date(1655220600),
                new Date(1655227800),
                true,
                new ArrayList<>(),
                null,
                null,
                4L,
                new Date(1655220600)
        ));
    }

    public void openSortPopupView(View view){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialogView = layoutInflater.inflate(R.layout.sorting_popup, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setLayout(width,height);

        final Button btnTitleSort = dialogView.findViewById(R.id.btnTitleSort);
        final Button btnDateSort = dialogView.findViewById(R.id.btnDateSort);
        final RelativeLayout close = dialogView.findViewById(R.id.closePopup);

        close.setOnClickListener(v -> {
            alertDialog.cancel();
        });

        btnTitleSort.setOnClickListener(v -> {

        });

        btnDateSort.setOnClickListener(v -> {

        });
    }

    public void addNewTask(View view) {
        startActivity(new Intent(this, AddNewTask.class));
    }
}