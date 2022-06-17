package ca.lambton.task_tech_armie_android.Adaptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.lambton.task_tech_armie_android.Database.Task;
import ca.lambton.task_tech_armie_android.R;

public class TaskListAdaptor extends BaseAdapter {

    Context context;
    List<Task> tasks;
    LayoutInflater inflater;

    public TaskListAdaptor(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
        inflater = (LayoutInflater.from(context));
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
        if(view==null)
        {
            view=inflater.inflate(R.layout.task_list_adapter,null);
            holder=new ViewHolder();
            holder.lblTaskTitle=view.findViewById(R.id.lblTaskTitle);
            holder.lblTaskCategory=view.findViewById(R.id.lblTaskCategory);
            holder.btnCheck=view.findViewById(R.id.btnCheck);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        holder.lblTaskTitle.setText(tasks.get(i).getName());
        holder.lblTaskCategory.setText("Business");
        if(tasks.get(i).isCompleted()){
            holder.btnCheck.setChecked(true);
        }else{
            holder.btnCheck.setChecked(false);
        }


        return view;
    }

    static class ViewHolder{
        private TextView lblTaskTitle, lblTaskCategory;
        private CheckBox btnCheck;
    }
}
