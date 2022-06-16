package ca.lambton.task_tech_armie_android.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        view = inflater.inflate(R.layout.task_list_adapter, null);
        return view;
    }
}
