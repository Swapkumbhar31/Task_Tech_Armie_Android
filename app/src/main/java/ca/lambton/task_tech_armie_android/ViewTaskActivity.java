package ca.lambton.task_tech_armie_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ViewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        getSupportActionBar().hide();
    }
}