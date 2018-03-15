package no.ntnu.stud.larsjny.lab2.util;

import android.app.Activity;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import no.ntnu.stud.larsjny.lab2.tasks.DownloadImageTask;

/**
 * Created by Lars Johan on 13.03.2018.
 */

public class Article implements Serializable{

    private Activity activity;

    private String imgUrl;

    private String title;

    private String summary;

    private String content;

    private String date;

    private String author;

    private Bitmap image;

    public Article(Activity activity,
                   String imgUrl,
                   String title,
                   String summary,
                   String content,
                   Date date,
                   String author) {
        this.activity = activity;
        this.imgUrl = imgUrl;
        this.title = title;
        this.summary = summary;
        this.content = content;
        try {
            this.date = new SimpleDateFormat("dd.MM.yyyy - HH:mm").parse(date.toString()).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException npe){
            this.date = "";
        }
        this.author = author;
        downloadImage();
    }

    private void downloadImage() {
        if (this.imgUrl != null) {
            DownloadImageTask downloader = new DownloadImageTask(this.activity, this);
            downloader.execute(this.imgUrl);
        }
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
