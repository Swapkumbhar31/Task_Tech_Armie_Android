package ca.lambton.task_tech_armie_android.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {
    @Query("select * from tasks where parentTaskId is null and isCompleted = :isCompleted")
    List<Task> getAllTasks(boolean isCompleted);

    @Insert
    void addTask(Task task);

    @Update
    void update(Task task);
}
