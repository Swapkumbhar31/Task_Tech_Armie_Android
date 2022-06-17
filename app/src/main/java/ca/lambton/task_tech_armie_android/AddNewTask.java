package ca.lambton.task_tech_armie_android;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import ca.lambton.task_tech_armie_android.Database.Category;
import ca.lambton.task_tech_armie_android.Database.Task;
import ca.lambton.task_tech_armie_android.Database.TaskRoomDB;


public class AddNewTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    ImageButton btnRecorder, btnRecordingPlay;
    Boolean recorder;
    Boolean playRecording;
    TextView txtDueDate;
    Calendar dueDate = Calendar.getInstance();
    Spinner categoriesSpinner;
    private TaskRoomDB taskRoomDB;
    EditText txtNewTask;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        Objects.requireNonNull(getSupportActionBar()).hide();
        taskRoomDB = TaskRoomDB.getInstance(this);

        btnRecorder = findViewById(R.id.btnRecorder);
        btnRecordingPlay = findViewById(R.id.btnPlayRecording);
        txtDueDate = findViewById(R.id.txtDueDate);
        categoriesSpinner = findViewById(R.id.categories);
        submitBtn = findViewById(R.id.submitBtn);
        txtNewTask = findViewById(R.id.txtNewTask);
        txtDueDate.setText(DateFormat.format("yyyy-MM-dd hh:mm a", dueDate));
        List<String> listOfItems = new ArrayList<>();
        for (Category category: taskRoomDB.categoryDAO().getAllCategories()) {
            listOfItems.add(category.getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfItems);

        categoriesSpinner.setAdapter(spinnerAdapter);

        txtDueDate.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewTask.this, AddNewTask.this, year, month, day);
            datePickerDialog.show();
        });

        btnRecordingPlay.setEnabled(false);
        btnRecordingPlay.setImageResource(R.drawable.ic_baseline_play_arrow_48_disabled);

        init();

    }

    private void init() {
        recorder = false;
        playRecording = false;
        submitBtn.setOnClickListener(view -> {
            taskRoomDB.taskDAO().addTask(
                    new Task(
                            txtNewTask.getText().toString(),
                            dueDate.getTime(),
                            false,
                            new ArrayList<>(),
                            null,
                            null,
                            1L,
                            null
                            )
            );
            finish();
        });
    }

    public void goToBack(View view) {
        this.finish();
    }

    public void recorderClicked(View view) {
        if (!playRecording) {
            if (recorder) {
                recorder = false;
                btnRecorder.setImageResource(R.drawable.recorder);
            } else {
                recorder = true;
                btnRecorder.setImageResource(R.drawable.stop_recorder);
            }
        } else {
            Toast.makeText(this, "Please stop played audio", Toast.LENGTH_LONG).show();
        }
    }

    public void playRecordingPlayed(View view) {
        if (!recorder) {
            if (playRecording) {
                playRecording = false;
                btnRecordingPlay.setImageResource(R.drawable.play);
            } else {
                playRecording = true;
                btnRecordingPlay.setImageResource(R.drawable.pause);
            }
        } else {
            Toast.makeText(this, "Please stop recording", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dueDate.set(year, month, dayOfMonth);
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewTask.this, AddNewTask.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DAY_OF_MONTH), i, i1);
        txtDueDate.setText(DateFormat.format("yyyy-MM-dd hh:mm a", dueDate));
    }
}