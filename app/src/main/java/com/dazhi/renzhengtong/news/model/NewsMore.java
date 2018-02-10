package com.dazhi.renzhengtong.news.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 2018/1/30.
 */

public class NewsMore extends DataSupport implements Serializable {
    private String thumbnail;
    private String template;
    private List<NewsFileModel>files;

    public List<NewsFileModel> getFiles() {
        return files;
    }

    public void setFiles(List<NewsFileModel> files) {
        this.files = files;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }


}
