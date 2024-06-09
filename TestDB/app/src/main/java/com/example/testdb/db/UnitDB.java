package com.example.testdb.db;

import com.example.testdb.model.Unit;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UnitDB {

    private final FirebaseDatabase mDatabase;
    private final DatabaseReference mRefUnit;

    public UnitDB() {
        String databaseUrl = "https://test-firebase-ecfde-default-rtdb.asia-southeast1.firebasedatabase.app/";
        mDatabase = FirebaseDatabase.getInstance(databaseUrl);
        mRefUnit = mDatabase.getReference("contact_book/units");
    }

    public void addUnit(Unit unit) {
        String id = mRefUnit.push().getKey();
        unit.setId(id);
        mRefUnit.child(id).setValue(unit);
    }

    public void deleteUnit(String id) {
        mRefUnit.child(id).removeValue();
    }

    public void updateUnit(String id, Unit unit) {
        mRefUnit.child(id).setValue(unit);
    }


    public UnitDB(FirebaseDatabase mDatabase, DatabaseReference mRefStudents) {
        this.mDatabase = mDatabase;
        this.mRefUnit = mRefStudents;
    }

    public DatabaseReference getRefUnit() {
        return mRefUnit;
    }
}
