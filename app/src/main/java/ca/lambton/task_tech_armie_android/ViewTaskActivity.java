package ca.lambton.task_tech_armie_android;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import ca.lambton.task_tech_armie_android.Adaptor.ImageListAdapter;
import ca.lambton.task_tech_armie_android.Adaptor.TaskListAdaptor;
import ca.lambton.task_tech_armie_android.Database.Category;
import ca.lambton.task_tech_armie_android.Database.Task;
import ca.lambton.task_tech_armie_android.Database.TaskRoomDB;
import ca.lambton.task_tech_armie_android.Helper.DateConverter;
import ca.lambton.task_tech_armie_android.Helper.ListViewSize;

public class ViewTaskActivity extends AppCompatActivity {

    private MediaPlayer mPlayer;
    private static String mFileName = null;
    private Boolean playRecording = false;

    List<Task> completedTasks;
    List<Task> inCompleteTasks;

    TextView dueDate, taskName, taskCategory, lblIncomplete, lblComplete;
    RecyclerView lvImageView;
    ImageButton btnMarkAsCompleted, btnAudioPlay;
    ListView lvInComplete, lvComplete;
    Button btnAddSubTask, btnDeleteTask;

    private TaskRoomDB taskRoomDB;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        Objects.requireNonNull(getSupportActionBar()).hide();

        taskRoomDB = TaskRoomDB.getInstance(this);

        dueDate = findViewById(R.id.lblDueDate);
        taskName = findViewById(R.id.lblTaskTitle);
        taskCategory = findViewById(R.id.lblCategory);
        lblIncomplete = findViewById(R.id.lblIncomplete);
        lblComplete = findViewById(R.id.lblCompleted);
        lvImageView = findViewById(R.id.lvPhotos);
        btnMarkAsCompleted = findViewById(R.id.btnMarkAsComplete);
        btnAudioPlay = findViewById(R.id.btnDetailsPlayRecording);
        lvInComplete = findViewById(R.id.subListviewIncomplete);
        lvComplete = findViewById(R.id.subListviewCompleted);
        btnAddSubTask = findViewById(R.id.btn_add_sub_task);
        btnDeleteTask = findViewById(R.id.btn_delete_parent_task);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Long value = extras.getLong("taskId");
            task = taskRoomDB.taskDAO().getTaskById(value);
        }
        init();

        btnMarkAsCompleted.setOnClickListener(v -> {
            task.setCompleted(true);
            taskRoomDB.taskDAO().update(task);
            Toast.makeText(this, "Task is added to completed category", Toast.LENGTH_LONG).show();
        });

        btnAddSubTask.setOnClickListener(v -> {

        });

        btnDeleteTask.setOnClickListener(v -> {
            taskRoomDB.taskDAO().delete(task);
            Intent intent=new Intent(this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            this.startActivity(intent);
        });
    }

    public void goToBack(View view) {
        this.finish();
    }

    private void init(){
        taskName.setText(task.getName());
        dueDate.setText(DateConverter.getFullDate(task.getEndDate()));
        taskCategory.setText(taskRoomDB.categoryDAO().getCategoryByID(task.getCategoryID()).getName());

        if(task.getPhotos().size() > 0){
            lvImageView.setAdapter(new ImageListAdapter(this, task.getPhotos()));
            lvImageView.setVisibility(View.VISIBLE);
        }

        if(task.getAudioPath()!=null){
            mFileName = task.getAudioPath();
            btnAudioPlay.setImageResource(R.drawable.play);
        }else{
            btnAudioPlay.setEnabled(false);
        }
    }

//    private void loadAllTasks() {
//        Intent i = getIntent();
//        long taskId = i.getLongExtra("taskId", 0L);
//        task = taskRoomDB.taskDAO().getTaskById(taskId);
//        completedTasks = taskRoomDB.taskDAO().getSubtasks(task.getId(), true);
//        inCompleteTasks = taskRoomDB.taskDAO().getSubtasks(task.getId(), false);
//        category = taskRoomDB.categoryDAO().getCategoryByID(task.getCategoryID());
//        categories.setText(category.getName());
//        lvIncomplete.setAdapter(new TaskListAdaptor(this, inCompleteTasks));
//        lvCompleted.setAdapter(new TaskListAdaptor(this, completedTasks));
//        ListViewSize.getListViewSize(lvIncomplete);
//        ListViewSize.getListViewSize(lvCompleted);
//        if (task.isCompleted()) {
//            ivChecked.setVisibility(View.GONE);
//        } else {
//            ivChecked.setVisibility(View.VISIBLE);
//        }
//    }

    public void playRecordingPlayed(View view) {
        if (playRecording) {
            playRecording = false;
            btnAudioPlay.setImageResource(R.drawable.play);
            mPlayer.release();
            mPlayer = null;

            Toast.makeText(this, "Audio player stopped", Toast.LENGTH_LONG).show();
        } else {
            playRecording = true;
            btnAudioPlay.setImageResource(R.drawable.pause);

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
    }
}