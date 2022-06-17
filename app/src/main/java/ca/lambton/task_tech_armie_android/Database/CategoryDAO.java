package ca.lambton.task_tech_armie_android.Database;

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

    @Delete
    void delete(Category category);

    @Update
    void update(Category category);

    @Query("select * from categories where id = :id")
    Category getCategoryByID(long id);
}
