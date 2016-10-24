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
    private int episodes;
    private int seasons;
    private int duration;


    public Tvshow(long id, String title, String description, String imgPath, int episodes, int seasons, int duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imgPath = imgPath;
        this.episodes = episodes;
        this.seasons = seasons;
        this.duration = duration;
    }

    public Tvshow(Tvshow tvshow){
        this.id = tvshow.getId();
        this.title = tvshow.getTitle();
        this.description = tvshow.getDescription();
        this.imgPath = tvshow.getImgPath();
        this.episodes = tvshow.getEpisodes();
        this.seasons = tvshow.getSeasons();
        this.duration = tvshow.getDuration();
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

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public int getSeasons() {
        return seasons;
    }

    public void setSeasons(int seasons) {
        this.seasons = seasons;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


}
