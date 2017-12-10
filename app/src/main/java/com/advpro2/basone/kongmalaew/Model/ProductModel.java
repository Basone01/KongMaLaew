package com.advpro2.basone.kongmalaew.Model;

/**
 * Created by kanazang on 11/14/2017.
 */

public class ProductModel {
    String product_name;
    String product_id;
    double product_price;
    String product_detail;
    String product_img;
    String product_brand;

    public String getProduct_brand() {
        return product_brand;
    }

    public void setProduct_brand(String product_brand) {
        this.product_brand = product_brand;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getProduct_uploadby() {
        return product_uploadby;
    }

    public void setProduct_uploadby(String product_uploadby) {
        this.product_uploadby = product_uploadby;
    }

    String product_uploadby;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Float product_price) {
        this.product_price = product_price;
    }

    public String getProduct_detail() {
        return product_detail;
    }

    public void setProduct_detail(String product_detail) {
        this.product_detail = product_detail;
    }

    public ProductModel (String product_id ,String product_name,String product_detail , double product_price ,String product_img,String product_uploadby,String product_brand){
        this.product_id=product_id;
        this.product_name=product_name;
        this.product_detail=product_detail;
        this.product_price=product_price;
        this.product_img=product_img;
        this.product_uploadby=product_uploadby;
        this.product_brand=product_brand;
    }

}