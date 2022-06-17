package ca.lambton.task_tech_armie_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.util.Date;
import java.util.List;
import java.util.Objects;

import ca.lambton.task_tech_armie_android.Adaptor.TaskListAdaptor;
import ca.lambton.task_tech_armie_android.Database.Category;
import ca.lambton.task_tech_armie_android.Database.Task;
import ca.lambton.task_tech_armie_android.Database.TaskRoomDB;
import ca.lambton.task_tech_armie_android.Helper.ListViewSize;

public class ViewTaskActivity extends AppCompatActivity {



    TextView due_date ;
    ImageButton audio_play;
    Button add_subtask ;
    Button delete_task ;
    TextView go_back ;

    MediaPlayer mediaPlayer;
    LinearLayout audio_layout ,images_layout ;

    ListView lvCompleted, lvIncomplete;
    private TaskRoomDB taskRoomDB;
    List<Task> completedTasks;
    List<Task> inCompleteTasks;
    TextView categories , name;
    Category category;
    Task task;
    ImageView ivChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        audio_play = findViewById(R.id.btn_play_pause_mp3);
        due_date = findViewById(R.id.tv_due_date);
        add_subtask = findViewById(R.id.btn_add_new_task);
        delete_task = findViewById(R.id.btn_delete_task);
        go_back = findViewById(R.id.bt_go_back);
        mediaPlayer = MediaPlayer.create(this, R.raw.song);
        audio_layout = findViewById(R.id.audio_ll);
        images_layout = findViewById(R.id.images_layout);
        name = findViewById(R.id.tv_name);
        mediaPlayer.setVolume(5f,5f);
        lvCompleted = findViewById(R.id.listviewCompleted);
        lvIncomplete = findViewById(R.id.listviewIncomplete);
        categories = findViewById(R.id.categories);
        ivChecked = findViewById(R.id.iv_checked);

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
            System.out.println("out" + task.getPhotos().size());
            if (task.getPhotos() == null)
            {
                images_layout.setVisibility(View.GONE);
            }
            else if (task.getPhotos().size() == 0)
            {
                images_layout.setVisibility(View.GONE);
                System.out.println("empty");
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

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        audio_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mediaPlayer.isPlaying()) {
                    audio_play.setImageResource(R.drawable.play);
                    mediaPlayer.pause();
                }
                else
                {
                    audio_play.setImageResource(R.drawable.pause);
                    mediaPlayer.start();
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

    private void loadAllTasks(){
        Intent i = getIntent();
        long taskId = i.getLongExtra("taskId", 0L);
        task = taskRoomDB.taskDAO().getTaskById(taskId);
        completedTasks = taskRoomDB.taskDAO().getSubtasks(task.getId(),true);
        inCompleteTasks = taskRoomDB.taskDAO().getSubtasks(task.getId(),false);
        category = taskRoomDB.categoryDAO().getCategoryByID(task.getCategoryID());
        categories.setText(category.getName());
        lvIncomplete.setAdapter(new TaskListAdaptor(this, inCompleteTasks));
        lvCompleted.setAdapter(new TaskListAdaptor(this, completedTasks));
        ListViewSize.getListViewSize(lvIncomplete);
        ListViewSize.getListViewSize(lvCompleted);
        if (task.isCompleted()) {
            ivChecked.setVisibility(View.GONE);
        } else {
            ivChecked.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadAllTasks();
    }
}