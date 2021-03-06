package ca.lambton.task_tech_armie_android.Adaptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;

import java.util.List;

import ca.lambton.task_tech_armie_android.AddNewTask;
import ca.lambton.task_tech_armie_android.Database.Category;
import ca.lambton.task_tech_armie_android.Database.CategoryDAO;
import ca.lambton.task_tech_armie_android.Database.Task;
import ca.lambton.task_tech_armie_android.MainActivity;
import ca.lambton.task_tech_armie_android.Database.TaskRoomDB;
import ca.lambton.task_tech_armie_android.Helper.ListViewSize;
import ca.lambton.task_tech_armie_android.MainActivity;
import ca.lambton.task_tech_armie_android.R;
import ca.lambton.task_tech_armie_android.ViewTaskActivity;

public class TaskListAdaptor extends BaseAdapter {

    Context context;
    List<Task> tasks;
    LayoutInflater inflater;
    private TaskRoomDB taskRoomDB;
    private Runnable callback;

    public TaskListAdaptor(Context context, List<Task> tasks, Runnable callback) {
        this.context = context;
        this.tasks = tasks;
        this.callback = callback;
        inflater = (LayoutInflater.from(context));
        taskRoomDB = TaskRoomDB.getInstance(context);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return tasks.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        Category category = taskRoomDB.categoryDAO().getCategoryByID(tasks.get(i).getCategoryID());
        if(view==null)
        {
            view=inflater.inflate(R.layout.task_list_adapter,null);
            holder=new ViewHolder();
            holder.lblTaskTitle=view.findViewById(R.id.lblTaskTitle);
            holder.lblTaskCategory=view.findViewById(R.id.lblTaskCategory);
            holder.btnCheck=view.findViewById(R.id.btnCheck);
            holder.btnRemoveTask=view.findViewById(R.id.btnDeleteTask);
            holder.llParentTask=view.findViewById(R.id.llParentTask);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        holder.lblTaskTitle.setText(tasks.get(i).getName());
        holder.lblTaskCategory.setText(category.getName());
        if(tasks.get(i).isCompleted()){
            holder.btnCheck.setChecked(true);
        }else{
            holder.btnCheck.setChecked(false);
        }

        holder.btnCheck.setOnClickListener(v -> {
            if(tasks.get(i).isCompleted()){
                tasks.get(i).setCompleted(false);
            }else{
                tasks.get(i).setCompleted(true);
            }
            taskRoomDB.taskDAO().update(tasks.get(i));
            if (callback != null) {
                callback.run();
            }
        });

        holder.btnRemoveTask.setOnClickListener(v -> {
            taskRoomDB.taskDAO().delete(tasks.get(i));
            if (callback != null) {
                callback.run();
            }
        });

        view.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, ViewTaskActivity.class);
            intent.putExtra("taskId", tasks.get(i).getId());
            context.startActivity(intent);
        });
        return view;
    }

    static class ViewHolder{
        private TextView lblTaskTitle, lblTaskCategory;
        private CheckBox btnCheck;
        private ImageButton btnRemoveTask;
        private LinearLayout llParentTask;
    }
}
