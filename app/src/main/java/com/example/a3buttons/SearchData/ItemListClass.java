package com.example.a3buttons.SearchData;

public class ItemListClass {
    private String valueName;
    private int imageId;

    public ItemListClass(String valueName, int imageId) {
        this.valueName = valueName;
        this.imageId = imageId;
    }

    public String getValueName() {
        return valueName;
    }

    public int getImageId() {
        return imageId;
    }
}
