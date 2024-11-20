package com.example.tarea_ud2.Model;

public class User {
    private final String name;
    private final String email;
    private final int age;
    private int imageResourceId;

    public User(String name, String email, int age, int imageResourceId) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
