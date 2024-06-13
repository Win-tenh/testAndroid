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

    public Unit(
            String address,
            String email,
            String id,
            String logo,
            String name,
            String phone,
            String parentUnitId,
            String website
            ) {
        this.address = address;
        this.email = email;
        this.id = id;
        this.logo = logo;
        this.name = name;
        this.parentUnitId = parentUnitId;
        this.phone = phone;
        this.website = website;
    }

    public Unit(String id, String name) {
        this.id = id;
        this.name = name;
        this.parentUnitId = "";
        this.address = "";
        this.email = "";
        this.logo = "";
        this.phone = "";
        this.website = "";
    }

    public Unit() {
        // Default constructor required for calls to DataSnapshot.getValue(Unit.class)
        this("", "", "", "", "", "", "", "");
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

    @Override
    public String toString() {
        return name;
    }
}
