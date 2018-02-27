package com.dazhi.renzhengtong.evaluation.model;

import java.io.Serializable;

/**
 * Created by mac on 2018/2/8.
 */

public class EvaluationItem  implements Serializable {

    private int id;
    private int aid;
    private int status;
    private int delete_time;
    private String name;
    private String remark;
    private String options;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(int delete_time) {
        this.delete_time = delete_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
