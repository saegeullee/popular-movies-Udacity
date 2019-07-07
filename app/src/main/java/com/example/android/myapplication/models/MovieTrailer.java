package com.example.android.myapplication.models;

public class MovieTrailer {
    private String key;
    private String name;

    public MovieTrailer() {
    }

    public MovieTrailer(String key, String name) {
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
        return "MovieTrailer{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
