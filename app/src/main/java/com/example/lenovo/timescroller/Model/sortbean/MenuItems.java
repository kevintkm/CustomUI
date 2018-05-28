package com.example.lenovo.timescroller.Model.sortbean;

/**
 * menu item 对象
 * 高佣金、发洋财分类条目bean对象
 * Created by East.K on 2016/3/10.
 */
public class MenuItems {
    /**
     * 菜单条目对象
     */
    private Object object;
    /**
     * 菜单项名称
     */
    private String itemName;
    /**
     *图标url
     */
    private String itemIconUrl;

    public String getItemIconUrl() {
        return itemIconUrl;
    }

    public void setItemIconUrl(String itemIconUrl) {
        this.itemIconUrl = itemIconUrl;
    }

    public Object getObject() {
        return object;
    }

    public MenuItems setObject(Object object) {
        this.object = object;
        return this;
    }

    public String getItemName() {
        return itemName;
    }

    public MenuItems setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }
}
