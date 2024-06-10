package com.example.testdb.model;

import java.io.Serializable;

public class Employee implements Serializable {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String position;
    private String avatar;
    private String id_unit;

    public Employee(String id, String name, String phone, String email, String position, String avatar, String id_unit) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.avatar = avatar;
        this.id_unit = id_unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId_unit() {
        return id_unit;
    }

    public void setId_unit(String id_unit) {
        this.id_unit = id_unit;
    }
}
