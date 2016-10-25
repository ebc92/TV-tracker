package local.ebc.tvtracker.model;

import java.io.Serializable;

/**
 * Created by ebc on 02.10.2016.
 */

public class Tvshow implements Serializable{
    private long id;
    private String title;
    private String description;
    private String imgPath;
    private int rating;
    private String dateadded;
    private String status;


    public Tvshow(long id, String title, String description, String imgPath, int rating, String dateadded, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imgPath = imgPath;
        this.rating = rating;
        this.dateadded = dateadded;
        this.status = status;
    }

    public Tvshow(Tvshow tvshow){
        this.id = tvshow.getId();
        this.title = tvshow.getTitle();
        this.description = tvshow.getDescription();
        this.imgPath = tvshow.getImgPath();
        this.rating = tvshow.getRating();
        this.dateadded = tvshow.getDateadded();
        this.status = tvshow.getStatus();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDateadded() {
        return dateadded;
    }

    public void setDateadded(String dateadded) {
        this.dateadded = dateadded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
