package ru.eva.automessanger.Models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Girl extends RealmObject {
    @SerializedName("photo_max")
    private String photoMax;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("photo_id")
    private String photoId;
    @SerializedName("id")
    @PrimaryKey
    private long id;

    public String toString() {
        return photoMax +"   "+ firstName +"   "+ lastName +"    "+ photoId +"     "+id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photoMax;
    }

    public void setPhoto(String photo_max) {
        this.photoMax = photo_max;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

}
