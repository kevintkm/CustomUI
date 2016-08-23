package com.example.lenovo.timescroller.Model;


/**
 * Created by kevin.tian on 2016/5/3.
 */
public class GetJoinDetailRequest extends BaseModel{
    private String goodsId;
    private int findType;
    private String shopType;


    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getFindType() {
        return findType;
    }

    public void setFindType(int findType) {
        this.findType = findType;
    }

}
