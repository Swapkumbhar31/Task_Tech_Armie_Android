package ca.lambton.task_tech_armie_android.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import android.database.Cursor;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Insert
    void addCategory(Category category);

    @Query("select * from categories")
    List<Category> getAllCategories();

    @Delete
    void delete(Category category);

    @Update
    void update(Category category);

    @Query("select * from categories where id = :id")
    Category getCategoryByID(long id);

    @Query("select *, count(tasks.id) as taskCount from categories CROSS join tasks on tasks.categoryID = categories.id group by categories.id;")
    Cursor getCategoriesWithTaskCount();
}
