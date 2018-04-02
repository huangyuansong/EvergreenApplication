package com.example.pc.evergreen.beans;

import cn.bmob.v3.BmobObject;

/**
 * Created by pc on 2018/3/29.
 * 兑换申请表
 */

public class Apply extends BmobObject{
    private String applyUserDataId;
    private String applyGoodsId;

    public String getApplyUserDataId() {
        return applyUserDataId;
    }

    public void setApplyUserDataId(String applyUserDataId) {
        this.applyUserDataId = applyUserDataId;
    }

    public String getApplyGoodsId() {
        return applyGoodsId;
    }

    public void setApplyGoodsId(String applyGoodsId) {
        this.applyGoodsId = applyGoodsId;
    }
}
