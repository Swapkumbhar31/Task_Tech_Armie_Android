package ca.lambton.task_tech_armie_android.Database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.List;

import ca.lambton.task_tech_armie_android.Helper.DateConverter;
import ca.lambton.task_tech_armie_android.Helper.ListConvertor;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Task.class,
                parentColumns = "id",
                childColumns = "parentTaskId",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Category.class,
                parentColumns = "id",
                childColumns = "categoryID"
        )
}, tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @TypeConverters(DateConverter.class)
    private Date startDate;

    @NonNull
    @TypeConverters(DateConverter.class)
    private Date endDate;

    @ColumnInfo(defaultValue = "false")
    private boolean isCompleted;

    @Nullable
    @TypeConverters(ListConvertor.class)
    private List<String> photos;

    @Nullable
    private String audioPath;

    @Nullable
    private Long parentTaskId;

    @NonNull
    private Long categoryID;

    @NonNull
    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(@NonNull Long categoryID) {
        this.categoryID = categoryID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(@NonNull Date startDate) {
        this.startDate = startDate;
    }

    @NonNull
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@NonNull Date endDate) {
        this.endDate = endDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Nullable
    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(@Nullable List<String> photos) {
        this.photos = photos;
    }

    @Nullable
    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(@Nullable String audioPath) {
        this.audioPath = audioPath;
    }

    @Nullable
    public Long getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(@Nullable Long parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public Task(@NonNull String name, @NonNull Date startDate, @NonNull Date endDate, boolean isCompleted, @Nullable List<String> photos, @Nullable String audioPath, @Nullable Long parentTaskId, @NonNull Long categoryID) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isCompleted = isCompleted;
        this.photos = photos;
        this.audioPath = audioPath;
        this.parentTaskId = parentTaskId;
        this.categoryID = categoryID;
    }
}
