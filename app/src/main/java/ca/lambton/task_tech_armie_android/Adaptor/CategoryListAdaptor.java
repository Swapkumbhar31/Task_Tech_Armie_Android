package ca.lambton.task_tech_armie_android.Adaptor;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.lambton.task_tech_armie_android.Database.TaskRoomDB;
import ca.lambton.task_tech_armie_android.Model.CategoryModel;
import ca.lambton.task_tech_armie_android.R;

public class CategoryListAdaptor extends BaseAdapter {

    Context context;
    List<CategoryModel> categories;
    LayoutInflater inflater;
    TaskRoomDB taskRoomDB;

    public CategoryListAdaptor(Context context, int resource, List<CategoryModel> categories, TaskRoomDB taskRoomDB) {
        this.context = context;
        this.categories = categories;
        this.taskRoomDB = taskRoomDB;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return categories.size();
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
        if (view == null) {
            view = inflater.inflate(R.layout.category_list_adapter, null);
            holder = new ViewHolder();
            holder.btnDelete = view.findViewById(R.id.btn_delete);
            holder.taskCountView = view.findViewById(R.id.taskCountView);
            holder.lblCategoryName = view.findViewById(R.id.tv_name);

            CategoryModel categoryModel = categories.get(i);
            if (categoryModel.getTaskCount() > 0) {
                holder.btnDelete.setVisibility(View.GONE);
                holder.taskCountView.setText(categoryModel.getTaskCount() + "");
                holder.taskCountView.setVisibility(View.VISIBLE);
            } else {
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.taskCountView.setVisibility(View.GONE);
            }

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskRoomDB.categoryDAO().delete(categoryModel.getId());
                    categories = new ArrayList<>();
                    notifyDataSetChanged();
                    loadCategories();
                }
            });

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.lblCategoryName.setText(categories.get(i).getName());

        return view;
    }

    private void loadCategories(){
        List<CategoryModel> listOfItems = new ArrayList<>();
        Cursor cursor = taskRoomDB.categoryDAO().getCategoriesWithTaskCount();
        while (cursor.moveToNext()) {
            listOfItems.add(new CategoryModel(cursor.getString(1), cursor.getLong(0), cursor.getInt(11)));
        }
        this.categories = listOfItems;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        private TextView lblCategoryName;
        private TextView taskCountView;
        private Button btnDelete;
    }
}
