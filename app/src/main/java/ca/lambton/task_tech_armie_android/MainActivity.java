package ca.lambton.task_tech_armie_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ca.lambton.task_tech_armie_android.Database.TaskRoomDB;

public class MainActivity extends AppCompatActivity {

    private TaskRoomDB taskRoomDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        taskRoomDB = TaskRoomDB.getInstance(this);
        taskRoomDB.taskDAO().getAllTasks();
    }

    public void openSortPopupView(View view){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialogView = layoutInflater.inflate(R.layout.sorting_popup, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setLayout(width,height);

        final Button btnTitleSort = dialogView.findViewById(R.id.btnTitleSort);
        final Button btnDateSort = dialogView.findViewById(R.id.btnDateSort);
        final RelativeLayout close = dialogView.findViewById(R.id.closePopup);

        close.setOnClickListener(v -> {
            alertDialog.cancel();
        });

        btnTitleSort.setOnClickListener(v -> {

        });

        btnDateSort.setOnClickListener(v -> {

        });
    }
}