package ca.lambton.task_tech_armie_android;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static ca.lambton.task_tech_armie_android.Helper.ImageHelper.getBitmapFormUri;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import ca.lambton.task_tech_armie_android.Adaptor.ImageListAdapter;
import ca.lambton.task_tech_armie_android.Adaptor.TaskListAdaptor;
import ca.lambton.task_tech_armie_android.Database.Category;
import ca.lambton.task_tech_armie_android.Database.Task;
import ca.lambton.task_tech_armie_android.Database.TaskRoomDB;
import ca.lambton.task_tech_armie_android.Helper.ImageHelper;


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
    long parentTaskId;
    RecyclerView lvImageView;

    List<String> imageList = new ArrayList<>();

    // creating a variable for medi recorder object class.
    private MediaRecorder mRecorder;
    // creating a variable for mediaplayer class
    private MediaPlayer mPlayer;
    // string variable is created for storing a file name
    private static String mFileName = null;
    // constant for storing audio permission
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        Objects.requireNonNull(getSupportActionBar()).hide();
        taskRoomDB = TaskRoomDB.getInstance(this);

        Intent intent = this.getIntent();
        parentTaskId = intent.getLongExtra("parentTaskId", 0L);

        btnRecorder = findViewById(R.id.btnRecorder);
        btnRecordingPlay = findViewById(R.id.btnPlayRecording);
        txtDueDate = findViewById(R.id.txtDueDate);
        categoriesSpinner = findViewById(R.id.categories);
        submitBtn = findViewById(R.id.submitBtn);
        txtNewTask = findViewById(R.id.txtNewTask);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        lvImageView = findViewById(R.id.lvImagePreview);
        lvImageView.setLayoutManager(layoutManager);

        lvImageView.setVisibility(View.GONE);

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

        mFileName = getExternalCacheDir().getAbsolutePath();
        mFileName += ImageHelper.generateUniqueFileName();

        init();

        submitBtn.setOnClickListener(view -> {

            String taskName = txtNewTask.getText().toString().trim();
            if(taskName.isEmpty()){
                txtNewTask.setError("Task can't be empty");
                txtNewTask.requestFocus();
                return;
            }
            taskRoomDB.taskDAO().addTask(
                    new Task(
                            taskName,
                            dueDate.getTime(),
                            false,
                            imageList,
                            (!btnRecordingPlay.isEnabled()) ? null : mFileName,
                            parentTaskId > 0L ? parentTaskId : null,
                            1L,
                            null
                    )
            );
            finish();
        });

    }

    private void refreshImageView(){
        lvImageView.setAdapter(new ImageListAdapter(this, imageList));
    }

    private void init() {
        recorder = false;
        playRecording = false;
    }

    public void goToBack(View view) {
        this.finish();
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

    public void uploadImageClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    final Uri imageUri = data.getData();
                    Bitmap selectedImage =  getBitmapFormUri(this, imageUri);
                    String imagePath = MediaStore.Images.Media.insertImage(
                            getContentResolver(),
                            selectedImage,
                            new Date().toString(),
                            new Date().toString()
                    );
                    imageList.add(imagePath);
                    lvImageView.setVisibility(View.VISIBLE);
                    refreshImageView();
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_AUDIO_PERMISSION_CODE) {
            if (grantResults.length > 0) {
                boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (permissionToRecord && permissionToStore) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public boolean CheckPermissions() {
        // this method is used to check permission
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestPermissions() {
        // this method is used to request the
        // permission for audio recording and storage.
        ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    public void recorderClicked(View view) {
        if(CheckPermissions()){
            if (!playRecording) {
                if (recorder) {
                    recorder = false;
                    btnRecorder.setImageResource(R.drawable.recorder);

                    mRecorder.stop();

                    mRecorder.release();
                    mRecorder = null;
                    Toast.makeText(this, "Recording stopped", Toast.LENGTH_LONG).show();
                    btnRecordingPlay.setEnabled(true);
                } else {
                    recorder = true;
                    btnRecorder.setImageResource(R.drawable.stop_recorder);

                    btnRecordingPlay.setEnabled(false);

                    mRecorder = new MediaRecorder();
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mRecorder.setOutputFile(mFileName);
                    try {
                        mRecorder.prepare();
                    } catch (IOException e) {
                        Log.e("TAG", "prepare() failed" + e);
                    }
                    mRecorder.start();
                    Toast.makeText(this, "Recording started", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Please stop played audio", Toast.LENGTH_LONG).show();
            }
        }else{
            RequestPermissions();
        }
    }

    public void playRecordingPlayed(View view) {
        if (!recorder) {
            if (playRecording) {
                playRecording = false;
                btnRecordingPlay.setImageResource(R.drawable.play);
                btnRecorder.setEnabled(true);

                mPlayer.release();
                mPlayer = null;

                Toast.makeText(this, "Audio player stopped", Toast.LENGTH_LONG).show();
            } else {
                playRecording = true;
                btnRecordingPlay.setImageResource(R.drawable.pause);
                btnRecorder.setEnabled(false);

                mPlayer = new MediaPlayer();

                try {
                    mPlayer.setDataSource(mFileName);
                    mPlayer.prepare();
                    mPlayer.start();
                    Toast.makeText(this, "Audio player started", Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    Log.e("TAG", "prepare() failed");
                }
            }
        } else {
            Toast.makeText(this, "Please stop recording", Toast.LENGTH_LONG).show();
        }
    }


}