package ca.lambton.task_tech_armie_android.Database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(foreignKeys = {@ForeignKey(entity = Task.class,
        parentColumns = "parentTaskId",
        childColumns = "id",
        onDelete = ForeignKey.CASCADE)
})
public class Task {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private Date startDate;

    @NonNull
    private Date endDate;

    @ColumnInfo(defaultValue = "false")
    @NonNull
    private boolean isCompleted;

    @Nullable
    private List<String> photos;

    @Nullable
    private String audioPath;

    @Nullable
    private long parentTaskId;

}
