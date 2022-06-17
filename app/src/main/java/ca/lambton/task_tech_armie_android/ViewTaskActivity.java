package ca.lambton.task_tech_armie_android;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    Button go_back ;
    MediaPlayer mediaPlayer;
    LinearLayout audio_layout ;
    ListView lvCompleted, lvIncomplete;
    private TaskRoomDB taskRoomDB;
    List<Task> completedTasks;
    List<Task> inCompleteTasks;
    TextView categories;
    Category category;
    //Boolean isPlaying  = false ;
    Task task;
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
        mediaPlayer.setVolume(5f,5f);
        lvCompleted = findViewById(R.id.listviewCompleted);
        lvIncomplete = findViewById(R.id.listviewIncomplete);
        categories = findViewById(R.id.categories);

        Objects.requireNonNull(getSupportActionBar()).hide();

        taskRoomDB = TaskRoomDB.getInstance(this);
        task = taskRoomDB.taskDAO().getTaskById(1L);

        if (task != null) {
            if (task.getAudioPath() == null) {
                audio_layout.setVisibility(View.GONE);
            } else if (task.getAudioPath().isEmpty()) {
                audio_layout.setVisibility(View.GONE);
            }
            due_date.setText(DateFormat.format("yyyy-MM-dd hh:mm a", task.getEndDate()));

            loadAllTasks();
        }


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

    }

    private void loadAllTasks(){
        completedTasks = taskRoomDB.taskDAO().getSubtasks(getTaskId(),true);
        inCompleteTasks = taskRoomDB.taskDAO().getSubtasks(task.getId(),false);
        category = taskRoomDB.categoryDAO().getCategoryByID(task.getId());
        categories.setText(category.getName());
        lvIncomplete.setAdapter(new TaskListAdaptor(this, inCompleteTasks));
        lvCompleted.setAdapter(new TaskListAdaptor(this, completedTasks));
        ListViewSize.getListViewSize(lvIncomplete);
        ListViewSize.getListViewSize(lvCompleted);
    }

}