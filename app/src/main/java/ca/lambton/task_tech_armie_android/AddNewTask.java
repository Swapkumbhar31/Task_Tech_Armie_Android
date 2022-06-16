package ca.lambton.task_tech_armie_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddNewTask extends AppCompatActivity {

    ImageButton btnRecorder, btnRecordingPlay;
    Boolean recorder;
    Boolean playRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        getSupportActionBar().hide();

        btnRecorder = findViewById(R.id.btnRecorder);
        btnRecordingPlay = findViewById(R.id.btnPlayRecording);

        init();
    }

    private void init(){
        recorder = false;
        playRecording = false;
    }

    public void goToBack(View view) {
        this.finish();
    }

    public void recorderClicked(View view) {
        if(!playRecording){
            if(recorder){
                recorder = false;
                btnRecorder.setImageResource(R.drawable.recorder);
            }else{
                recorder = true;
                btnRecorder.setImageResource(R.drawable.stop_recorder);
            }
        }else{
            Toast.makeText(this, "Please stop played audio", Toast.LENGTH_LONG).show();
        }
    }

    public void playRecordingPlayed(View view) {
        if(!recorder){
            if(playRecording){
                playRecording = false;
                btnRecordingPlay.setImageResource(R.drawable.play);
            }else{
                playRecording = true;
                btnRecordingPlay.setImageResource(R.drawable.pause);
            }
        }else{
            Toast.makeText(this, "Please stop recording", Toast.LENGTH_LONG).show();
        }
    }
}