package com.example.pc.evergreen.beans;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by pc on 2018/3/26.
 */

public class Goods extends BmobObject implements Serializable {
    private String goodsName;
    private String goodsImage;
    private Integer goodsIntegral;//商品积分
    private String goodsOrigin;//商品产地
    private String goodsModel;// 商品型号
    private String goodsDate;//生产日期
    private Integer inventory;//库存

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsDate() {
        return goodsDate;
    }

    public void setGoodsDate(String goodsDate) {
        this.goodsDate = goodsDate;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public Integer getGoodsIntegral() {
        return goodsIntegral;
    }

    public void setGoodsIntegral(Integer goodsIntegral) {
        this.goodsIntegral = goodsIntegral;
    }

    public String getGoodsOrigin() {
        return goodsOrigin;
    }

    public void setGoodsOrigin(String goodsOrigin) {
        this.goodsOrigin = goodsOrigin;
    }

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }
}
