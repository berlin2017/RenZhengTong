package com.dazhi.renzhengtong.news.model;

/**
 * Created by mac on 2018/1/25.
 */

public class SlideModel {
    private String image;
    private int id;
    private int slide_id;
    private int list_order;
    private String title;
    private String url;
    private String target;
    private String description;
    private String content;

    public int getSlide_id() {
        return slide_id;
    }

    public void setSlide_id(int slide_id) {
        this.slide_id = slide_id;
    }

    public int getList_order() {
        return list_order;
    }

    public void setList_order(int list_order) {
        this.list_order = list_order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
