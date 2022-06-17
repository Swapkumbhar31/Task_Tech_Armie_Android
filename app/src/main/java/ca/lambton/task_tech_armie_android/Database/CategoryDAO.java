package ca.lambton.task_tech_armie_android.Database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Insert
    void addCategory(Category category);

    @Query("select * from categories")
    List<Category> getAllCategories();

    @Query("delete from categories where id = :id")
    void delete(long id);

    @Update
    void update(Category category);

    @Query("select * from categories where id = :id")
    Category getCategoryByID(long id);

    @Query("select *, count(tasks.id) as taskCount from categories left join tasks on tasks.categoryID = categories.id group by categories.id;")
    Cursor getCategoriesWithTaskCount();
}
