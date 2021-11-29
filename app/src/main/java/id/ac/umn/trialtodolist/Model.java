package id.ac.umn.trialtodolist;

public class Model {

    private String task, description, id, date, imageURL;

    public Model() {
    }

    public Model(String task, String description, String id, String date, String imageURL) {
        this.task = task;
        this.description = description;
        this.id = id;
        this.date = date;
        this.imageURL= imageURL;

    }

    public Model(String task, String description, String id, String date) {
        this.task = task;
        this.description = description;
        this.id = id;
        this.date = date;

    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }
}
