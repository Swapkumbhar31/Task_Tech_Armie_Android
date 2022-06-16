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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.lambton.task_tech_armie_android.Database.Category;
import ca.lambton.task_tech_armie_android.Database.Task;
import ca.lambton.task_tech_armie_android.Database.TaskRoomDB;
import ca.lambton.task_tech_armie_android.Helper.DateConverter;
import ca.lambton.task_tech_armie_android.SharedPreferences.UserSettings;

public class MainActivity extends AppCompatActivity {

    private TaskRoomDB taskRoomDB;

    TextView lblCurrentDate, lblTaskInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        taskRoomDB = TaskRoomDB.getInstance(this);

        // UIObject initialization
        lblCurrentDate = findViewById(R.id.lblCurrentDate);
        lblTaskInfo = findViewById(R.id.lblTaskCompletionInfo);

        // Insert Dummy data

        UserSettings userSettings = new UserSettings().getInstance(getApplicationContext());
        boolean firstTimeOpen = new UserSettings().getInstance(getApplicationContext()).isFirstTimeOpen();

        if (firstTimeOpen) {
            insertCategories();
            insertTasks();
            userSettings.setIsFirstTimeOpen(false);
        }
        taskRoomDB.taskDAO().getAllTasks(true);
        taskRoomDB.taskDAO().getAllTasks(false);

        init();
    }

    private void init(){
        lblCurrentDate.setText(DateConverter.getFullDate(new Date()));
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
                new Date(),
                new Date(),
                false,
                new ArrayList<>(),
                null,
                null,
                1L
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Invest Today",
                new Date(),
                new Date(),
                false,
                new ArrayList<>(),
                null,
                null,
                1L
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Book a room",
                new Date(),
                new Date(),
                false,
                new ArrayList<>(),
                null,
                1L,
                1L
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Say hello to new friend",
                new Date(),
                new Date(),
                false,
                new ArrayList<>(),
                null,
                null,
                3L
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Soaps",
                new Date(),
                new Date(),
                false,
                new ArrayList<>(),
                null,
                null,
                2L
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Hello World Program",
                new Date(),
                new Date(),
                true,
                new ArrayList<>(),
                null,
                null,
                3L
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Attend Class",
                new Date(),
                new Date(),
                true,
                new ArrayList<>(),
                null,
                null,
                4L
        ));
        taskRoomDB.taskDAO().addTask(new Task(
                "Laundry",
                new Date(),
                new Date(),
                true,
                new ArrayList<>(),
                null,
                null,
                4L
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