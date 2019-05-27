package com.example.android.myapplication.models;

public class VideoTrailer {
    private String key;
    private String name;

    public VideoTrailer() {
    }

    public VideoTrailer(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VideoTrailer{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
