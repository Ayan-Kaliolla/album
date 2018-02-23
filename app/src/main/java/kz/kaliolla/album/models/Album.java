package kz.kaliolla.album.models;

import java.io.Serializable;

/**
 * Created by akaliolla on 22.02.2018.
 */

public class Album implements Serializable{

    private long id;
    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
