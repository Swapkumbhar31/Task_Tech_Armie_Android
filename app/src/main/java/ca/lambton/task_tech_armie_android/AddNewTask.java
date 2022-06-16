package ca.lambton.task_tech_armie_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class AddNewTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        getSupportActionBar().hide();
    }

    public void goToBack(View view) {
        this.finish();
    }
}