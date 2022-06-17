package ca.lambton.task_tech_armie_android.Model;

public class CategoryModel {
    private String name;
    private long id;
    private int taskCount;

    public CategoryModel(String name, long id, int taskCount) {
        this.name = name;
        this.id = id;
        this.taskCount = taskCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
}
