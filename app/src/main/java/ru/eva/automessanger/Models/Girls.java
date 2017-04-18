package ru.eva.automessanger.Models;

import io.realm.RealmObject;

public class Girls extends RealmObject {
    private String photo_max;
    private String first_name;
    private String last_name;
    private int id;

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhoto() {
        return photo_max;
    }

    public void setPhoto(String photo_max) {
        this.photo_max = photo_max;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
