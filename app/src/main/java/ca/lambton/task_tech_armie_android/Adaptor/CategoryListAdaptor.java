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

import java.util.ArrayList;
import java.util.List;

import ca.lambton.task_tech_armie_android.Database.Category;
import ca.lambton.task_tech_armie_android.Database.Task;
import ca.lambton.task_tech_armie_android.Database.TaskRoomDB;
import ca.lambton.task_tech_armie_android.R;

public class CategoryListAdaptor extends BaseAdapter {

    Context context;
    List<Category> category;
    LayoutInflater inflater;
    TaskRoomDB taskRoomDB;

    public CategoryListAdaptor(Context context, int resource , List<Category> category, TaskRoomDB taskRoomDB) {
        this.context = context;
        this.category = category;
        this.taskRoomDB = taskRoomDB;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return category.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
   }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null)
        {
            view=inflater.inflate(R.layout.category_list_adapter,null);
            holder=new ViewHolder();
            holder.lblCategoryName=view.findViewById(R.id.tv_name);
            holder.btnDelete=view.findViewById(R.id.btn_delete);

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskRoomDB.categoryDAO().delete(category.get(i));
                    loadCategories();
                }
            });

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        holder.lblCategoryName.setText(category.get(i).getName());

        return view;
    }

    private void loadCategories(){
        List<Category> listOfItems = new ArrayList<>();
        for (Category category: taskRoomDB.categoryDAO().getAllCategories()) {
            listOfItems.add(category);
        }
        this.category = listOfItems;
        notifyDataSetChanged();
    }

    static class ViewHolder{
        private TextView lblCategoryName;
        private Button btnDelete;
    }
}
