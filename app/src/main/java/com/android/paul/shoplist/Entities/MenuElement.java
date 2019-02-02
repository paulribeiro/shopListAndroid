package com.android.paul.shoplist.Entities;

import java.util.List;

public class MenuElement {
    private List<ShopElement> shopElementList;
    private String title;

    public MenuElement(List<ShopElement> shopElements, String title) {
        this.shopElementList = shopElements;
        this.title = title;
    }

    public List<ShopElement> getShopElementList() {
        return shopElementList;
    }

    public void setShopElementList(List<ShopElement> shopElements) {
        this.shopElementList = shopElements;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
