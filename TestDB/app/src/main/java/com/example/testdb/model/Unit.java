package com.example.testdb.model;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

public class Unit implements Serializable {
    private String id;
    private String name;
    private String email;
    private String website;
    private String logo;
    private String address;
    private String phone;
    private String parentUnitId;

    public Unit(String id, String name, String email, String website, String logo, String address, String phone, String parentUnitId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.website = website;
        this.logo = logo;
        this.address = address;
        this.phone = phone;
        this.parentUnitId = parentUnitId;
    }

    public Unit() {
        DataSnapshot snapshot = null;
        snapshot.getValue(Unit.class);
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getWebsite() { return website; }

    public void setWebsite(String website) { this.website = website; }

    public String getLogo() { return logo; }

    public void setLogo(String logo) { this.logo = logo; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getParentUnitId() { return parentUnitId; }

    public void setParentUnitId(String parentUnitId) { this.parentUnitId = parentUnitId; }
}
