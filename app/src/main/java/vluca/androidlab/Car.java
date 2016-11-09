package vluca.androidlab;

/**
 * Created by lucav on 11/9/2016.
 */

public class Car {
    private int id;
    private String mark;
    private String model;
    private int fileId;

    public Car(int id, String mark, String model, int fileId) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.fileId = fileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

}
