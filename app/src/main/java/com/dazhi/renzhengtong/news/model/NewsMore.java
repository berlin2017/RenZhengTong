package com.dazhi.renzhengtong.news.model;

import java.io.Serializable;

/**
 * Created by mac on 2018/1/30.
 */

public class NewsMore implements Serializable {
    private String thumbnail;
//    private String template;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

//    public String getTemplate() {
//        return template;
//    }
//
//    public void setTemplate(String template) {
//        this.template = template;
//    }
}
