package com.syna.updatefirmwaredemo.common;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/10.
 */

public class FileBean implements Serializable {
    private String id;
    private String path;
    private String size;
    private String name;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getSize() {
        return size;
    }


    public void setSize(String size) {
        this.size = size;
    }


    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }
}
