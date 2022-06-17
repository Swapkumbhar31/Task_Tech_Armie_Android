package ca.lambton.task_tech_armie_android;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Objects;

public class AddNewTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    ImageButton btnRecorder, btnRecordingPlay;
    Boolean recorder;
    Boolean playRecording;
    TextView txtDueDate;
    Calendar dueDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btnRecorder = findViewById(R.id.btnRecorder);
        btnRecordingPlay = findViewById(R.id.btnPlayRecording);
        txtDueDate = findViewById(R.id.txtDueDate);
        txtDueDate.setText(DateFormat.format("yyyy-MM-dd hh:mm a", dueDate));

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