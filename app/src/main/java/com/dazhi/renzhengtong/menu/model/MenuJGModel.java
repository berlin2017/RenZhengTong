package com.dazhi.renzhengtong.menu.model;

import java.util.List;

/**
 * Created by mac on 2018/2/6.
 */

public class MenuJGModel {

    private int id;
    private int uid;
    private int status;
    private int delete_time;
    private String remark;
    private String jgname;
    private String jgxm;
    private String jgtel;
    private String jglogo;
    private int createtime;
    private String address;
    private String services;
    private String intro;
    private List<String>images;
    private int gzrs;

    public int getGzrs() {
        return gzrs;
    }

    public void setGzrs(int gzrs) {
        this.gzrs = gzrs;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getJgname() {
        return jgname;
    }

    public void setJgname(String jgname) {
        this.jgname = jgname;
    }

    public String getJgxm() {
        return jgxm;
    }

    public void setJgxm(String jgxm) {
        this.jgxm = jgxm;
    }

    public String getJgtel() {
        return jgtel;
    }

    public void setJgtel(String jgtel) {
        this.jgtel = jgtel;
    }

    public String getJglogo() {
        return jglogo;
    }

    public void setJglogo(String jglogo) {
        this.jglogo = jglogo;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }
}
