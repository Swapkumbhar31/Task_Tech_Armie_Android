package ca.lambton.task_tech_armie_android;

        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;

        import androidx.appcompat.app.AppCompatActivity;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Objects;

        import ca.lambton.task_tech_armie_android.Adaptor.CategoryListAdaptor;
        import ca.lambton.task_tech_armie_android.Database.Category;
        import ca.lambton.task_tech_armie_android.Database.Task;
        import ca.lambton.task_tech_armie_android.Database.TaskRoomDB;


public class AddNewCategory extends AppCompatActivity {

    private TaskRoomDB taskRoomDB;

    ListView categoryListView;
    EditText txtNewCategory;
    Button addCategoryBtn;
    List<Category> listOfItems;
    CategoryListAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        Objects.requireNonNull(getSupportActionBar()).hide();
        taskRoomDB = TaskRoomDB.getInstance(this);

        categoryListView = findViewById(R.id.simpleListVieww);
        addCategoryBtn = findViewById(R.id.addCategoryBtnn);
        txtNewCategory = findViewById(R.id.txtNewCategoryy);

        this.loadCategories();

        CategoryListAdaptor adapter = new CategoryListAdaptor(this,
                R.layout.category_list_adapter,
                listOfItems,
                taskRoomDB);
        categoryListView.setAdapter(adapter);

        init();

    }

    private void init() {
        addCategoryBtn.setOnClickListener(view -> {
            taskRoomDB.categoryDAO().addCategory(
                    new Category(
                            txtNewCategory.getText().toString()
                    )
            );
            loadCategories();
        });
    }

    private void loadCategories(){
        listOfItems = new ArrayList<>();
        List<Category> listOfItems = new ArrayList<>();
        for (Category category: taskRoomDB.categoryDAO().getAllCategories()) {
            listOfItems.add(category);
        }
        this.listOfItems = listOfItems;

        adaptor = new CategoryListAdaptor(this,
                R.layout.category_list_adapter,
                listOfItems,
                taskRoomDB);
        categoryListView.setAdapter(adaptor);
    }

    public void getBack(View view) {
        onBackPressed();
    }

}