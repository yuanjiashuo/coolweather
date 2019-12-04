package com.example.coolweather.wechat;

public class contacts {
    private String name;

    private int imageId;

    public contacts(String name, int imageId) {

        this.name = name;
        this.imageId = imageId;

    }

    public String getName() {

        return name;
    }

    public int getImageId() {

        return imageId;

    }

}
