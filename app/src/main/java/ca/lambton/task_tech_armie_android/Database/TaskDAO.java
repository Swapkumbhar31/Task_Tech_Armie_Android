package ca.lambton.task_tech_armie_android.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {
    @Query("select * from tasks where parentTaskId is null and isCompleted = :isCompleted order by completedAt, endDate desc")
    List<Task> getAllTasks(boolean isCompleted);

    @Insert
    void addTask(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("select * from tasks where parentTaskId is null and isCompleted = :isCompleted order by name desc")
    List<Task> getAllTasksSortByTitle(boolean isCompleted);

    @Query("select * from tasks where parentTaskId is null and isCompleted = :isCompleted order by endDate desc")
    List<Task> getAllTasksSortByEndDate(boolean isCompleted);

    @Query("select * from tasks where id = :id")
    Task getTaskById(long id);

    @Query("select * from tasks where name like '%' || :name || '%' and parentTaskId is null and isCompleted = :isCompleted order by completedAt, endDate desc")
    List<Task> searchTaskByName(String name, boolean isCompleted);

    @Query("select * from tasks where parentTaskId = :parentTaskId and isCompleted = :isCompleted order by completedAt, endDate desc")
    List<Task> getSubtasks(long parentTaskId, boolean isCompleted);
}
