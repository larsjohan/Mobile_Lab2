package no.ntnu.stud.larsjny.lab2;

/**
 * Created by Lars Johan on 13.03.2018.
 */

public class Article {

    private String imgUrl;

    private String title;

    private String summary;

    private String content;

    public Article(String imgUrl, String title, String summary, String content) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.summary = summary;
        this.content = content;
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
}
