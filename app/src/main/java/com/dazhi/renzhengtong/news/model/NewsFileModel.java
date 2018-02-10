package com.dazhi.renzhengtong.news.model;

import org.litepal.crud.DataSupport;

/**
 * Created by mac on 2018/2/6.
 */

public class NewsFileModel extends DataSupport {
    private String url;
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
