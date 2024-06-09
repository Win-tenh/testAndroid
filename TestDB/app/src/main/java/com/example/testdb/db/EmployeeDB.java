package com.example.testdb.db;

import com.example.testdb.model.Employee;
import com.example.testdb.model.Unit;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeeDB {

    private final FirebaseDatabase mDatabase;
    private final DatabaseReference mRefEmployee;

    public EmployeeDB() {
        String databaseUrl = "https://test-firebase-ecfde-default-rtdb.asia-southeast1.firebasedatabase.app/";
        mDatabase = FirebaseDatabase.getInstance(databaseUrl);
        mRefEmployee = mDatabase.getReference("contact_book/employees");
    }

    public void addEmployee(Employee employee) {
        String id = mRefEmployee.push().getKey();
        employee.setId(id);
        mRefEmployee.child(id).setValue(employee);
    }

    public void deleteEmployee(String id) {
        mRefEmployee.child(id).removeValue();
    }

    public void updateEmployee(String id, Employee employee) {
        mRefEmployee.child(id).setValue(employee);
    }

    public EmployeeDB(FirebaseDatabase mDatabase, DatabaseReference mRefStudents) {
        this.mDatabase = mDatabase;
        this.mRefEmployee = mRefStudents;
    }

    public DatabaseReference getRefEmployee() {
        return mRefEmployee;
    }
}
