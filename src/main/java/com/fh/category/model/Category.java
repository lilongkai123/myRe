package com.fh.category.model;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_category")
public class Category {

    private int id;

    private String categoryName;

    private int pid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
