package com.fh.product.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("t_product")
public class Product {

    private int id;

    private String name;

    private double price;

    private Date createDate;

    private int brandId;

    private String filePath;

    private int category1;

    private int category2;

    private int category3;

    private int status;

    private int isHot;

    private int stock;


    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getCategory1() {
        return category1;
    }

    public void setCategory1(int category1) {
        this.category1 = category1;
    }

    public int getCategory2() {
        return category2;
    }

    public void setCategory2(int category2) {
        this.category2 = category2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public int getCategory3() {
        return category3;
    }

    public void setCategory3(int category3) {
        this.category3 = category3;
    }
}
