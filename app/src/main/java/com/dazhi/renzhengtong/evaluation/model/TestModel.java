package com.dazhi.renzhengtong.evaluation.model;

import java.util.List;

/**
 * Created by mac on 2018/2/1.
 */

public class TestModel {
    private List<OptionItem>list;
    private String title;
    private int selectPosition = -1;

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public List<OptionItem> getList() {
        return list;
    }

    public void setList(List<OptionItem> list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
