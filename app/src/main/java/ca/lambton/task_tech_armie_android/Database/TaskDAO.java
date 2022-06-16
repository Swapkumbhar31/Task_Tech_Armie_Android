package ca.lambton.task_tech_armie_android.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDAO {
    @Query("select * from tasks where parentTaskId = null")
    List<Task> getAllTasks();

    @Insert
    void addTask(Task task);

}
