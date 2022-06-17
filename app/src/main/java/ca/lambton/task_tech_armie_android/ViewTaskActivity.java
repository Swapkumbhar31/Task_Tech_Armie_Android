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
import android.widget.RelativeLayout;
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
import ca.lambton.task_tech_armie_android.Helper.ListViewSize;

public class ViewTaskActivity extends AppCompatActivity {


    TextView due_date;
    ImageButton audio_play;
    Button add_subtask;
    Button delete_task;

    MediaPlayer mediaPlayer;
    LinearLayout audio_layout, images_layout;
    Runnable runnable;

    ListView lvCompleted, lvIncomplete;
    List<Task> completedTasks;
    List<Task> inCompleteTasks;
    TextView categories, name;
    RelativeLayout no_photos , no_task_completed , no_task_incompleted;
    Category category;
    Task task;
    ImageView ivChecked;
    private TaskRoomDB taskRoomDB;
    RecyclerView lvImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        audio_play = findViewById(R.id.btn_play_pause_mp3);
        due_date = findViewById(R.id.tv_due_date);
        add_subtask = findViewById(R.id.btn_add_new_task);
        delete_task = findViewById(R.id.btn_delete_task);
        mediaPlayer = new MediaPlayer();
        audio_layout = findViewById(R.id.audio_ll);
        images_layout = findViewById(R.id.images_layout);
        name = findViewById(R.id.tv_name);
        mediaPlayer.setVolume(5f, 5f);
        lvCompleted = findViewById(R.id.listviewCompleted);
        lvIncomplete = findViewById(R.id.listviewIncomplete);
        categories = findViewById(R.id.categories);
        ivChecked = findViewById(R.id.iv_checked);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        lvImageView = findViewById(R.id.lvImagePreview);
        lvImageView.setLayoutManager(layoutManager);
        no_photos = findViewById(R.id.no_photos);
        no_task_incompleted  = findViewById(R.id.no_task_incompleted);
        no_task_completed = findViewById(R.id.no_task_completed);
        mediaPlayer.setLooping(true);
        runnable = new Runnable() {
            @Override
            public void run() {
                loadAllTasks();
            }
        };
        lvImageView.setVisibility(View.GONE);

        Objects.requireNonNull(getSupportActionBar()).hide();

        taskRoomDB = TaskRoomDB.getInstance(this);

        loadAllTasks();
        if (task != null) {
            if (task.getAudioPath() == null) {
                audio_layout.setVisibility(View.GONE);
            } else if (task.getAudioPath().isEmpty()) {
                audio_layout.setVisibility(View.GONE);
            }
            due_date.setText(DateFormat.format("yyyy-MM-dd hh:mm a", task.getEndDate()));
            name.setText(task.getName());
            if (task.getPhotos() == null) {
                images_layout.setVisibility(View.VISIBLE);
                no_photos.setVisibility(View.VISIBLE);
            } else if (task.getPhotos().size() == 0) {
                images_layout.setVisibility(View.VISIBLE);
                no_photos.setVisibility(View.VISIBLE);
            } else {
                lvImageView.setVisibility(View.VISIBLE);
                no_photos.setVisibility(View.GONE);
                lvImageView.setAdapter(new ImageListAdapter(this, task.getPhotos()));
            }
        }
        add_subtask.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddNewTask.class);
            intent.putExtra("parentTaskId", task.getId());
            startActivity(intent);
        });

        delete_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskRoomDB.taskDAO().delete(task);
                finish();

            }
        });

        audio_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    audio_play.setImageResource(R.drawable.play);
                    mediaPlayer.pause();


                } else {
                    try {
                        mediaPlayer.setDataSource(task.getAudioPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    audio_play.setImageResource(R.drawable.pause);
                    try {
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ivChecked.setOnClickListener(view -> {
            task.setCompleted(true);
            task.setCompletedAt(new Date());
            taskRoomDB.taskDAO().update(task);
            loadAllTasks();
        });
    }

    public void goToBack(View view) {
        this.finish();
    }

    private void loadAllTasks() {
        Intent i = getIntent();
        long taskId = i.getLongExtra("taskId", 0L);
        task = taskRoomDB.taskDAO().getTaskById(taskId);
        completedTasks = taskRoomDB.taskDAO().getSubtasks(task.getId(), true);
        inCompleteTasks = taskRoomDB.taskDAO().getSubtasks(task.getId(), false);
        category = taskRoomDB.categoryDAO().getCategoryByID(task.getCategoryID());
        categories.setText(category.getName());
        lvIncomplete.setAdapter(new TaskListAdaptor(this, inCompleteTasks, runnable));
        lvCompleted.setAdapter(new TaskListAdaptor(this, completedTasks, runnable));
        ListViewSize.getListViewSize(lvIncomplete);
        ListViewSize.getListViewSize(lvCompleted);
        if (task.isCompleted()) {
            ivChecked.setVisibility(View.GONE);
            add_subtask.setVisibility(View.GONE);
        } else {
            ivChecked.setVisibility(View.VISIBLE);
            add_subtask.setVisibility(View.VISIBLE);
        }
        if (completedTasks.size() == 0)
        {
            no_task_completed.setVisibility(View.VISIBLE);
        }
        else
        {
            no_task_completed.setVisibility(View.GONE);
        }
        if (inCompleteTasks.size() == 0)
        {
            no_task_incompleted.setVisibility(View.VISIBLE);
        }
        else
        {
            no_task_incompleted.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadAllTasks();
    }
}