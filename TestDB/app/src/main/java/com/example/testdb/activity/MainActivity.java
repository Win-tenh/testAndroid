package com.example.testdb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdb.R;
import com.example.testdb.activity.add.AddEmployeeActivity;
import com.example.testdb.activity.add.AddUnitActivity;
import com.example.testdb.adapter.ContactAdapter;
import com.example.testdb.db.EmployeeDB;
import com.example.testdb.db.UnitDB;
import com.example.testdb.model.Employee;
import com.example.testdb.model.ItemContact;
import com.example.testdb.model.Unit;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private EditText et_search;
    private TabLayout tabLayout;
    private RecyclerView rcv_contact;
    private ContactAdapter contactAdapter;
    private ArrayList<Unit> unitList;
    private ArrayList<Employee> employeeList;
    private UnitDB dbUnit;
    private EmployeeDB dbEmployee;
    private int selectedTabPosition = 0;
    private int selectedEmployeePosition = -1;
    private int selectedUnitPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_search = findViewById(R.id.et_search);
        tabLayout = findViewById(R.id.tab_contact);
        rcv_contact = findViewById(R.id.rcv_contact);
        contactAdapter = new ContactAdapter(this);
        dbUnit = new UnitDB();
        dbEmployee = new EmployeeDB();

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // hide keyboard
        findViewById(R.id.main).setOnTouchListener(this);

        unitList = new ArrayList<>();
        employeeList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv_contact.setLayoutManager(layoutManager);
        rcv_contact.setAdapter(contactAdapter);
        contactAdapter.setContactList(unitList, null);
        contactAdapter.notifyDataSetChanged();

        et_search.setHint("Tìm kiếm tên đơn vị");
        getDataUnit();

        // chọn vào từng item của tablayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    selectedTabPosition = 0;
                    contactAdapter.setContactList(unitList, null);
                    contactAdapter.notifyDataSetChanged();
                    et_search.setHint("Tìm kiếm tên đơn vị");
                    getDataUnit();
                }
                else if (tab.getPosition() == 1) {
                    selectedTabPosition = 1;
                    contactAdapter.setContactList(null, employeeList);
                    contactAdapter.notifyDataSetChanged();
                    et_search.setHint("Tìm kiếm tên nhân viên");
                    getDataEmployee();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void getDataUnit() {
        dbUnit.getRefUnit().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                unitList.clear();
                for (DataSnapshot unitSnapshot : snapshot.getChildren()) {
                    String id = unitSnapshot.getKey();
                    String name = unitSnapshot.child("name").getValue(String.class);
                    String email = unitSnapshot.child("email").getValue(String.class);
                    String phone = unitSnapshot.child("phone").getValue(String.class);
                    String logo = unitSnapshot.child("logo").getValue(String.class);
                    String website = unitSnapshot.child("website").getValue(String.class);
                    String address = unitSnapshot.child("address").getValue(String.class);
                    String parent_id = unitSnapshot.child("parentUnitId").getValue(String.class);
                    Unit unit = new Unit(
                            id,
                            name,
                            email,
                            website,
                            logo,
                            address,
                            phone,
                            parent_id
                    );
                    unitList.add(unit);
                }
                // sắp xếp các item theo thứ tự tên nếu số lượng đơn vị > 1
                if (unitList.size() > 1) unitList.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                contactAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataEmployee() {
        dbEmployee.getRefEmployee().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                employeeList.clear();
                for (DataSnapshot Snapshot : snapshot.getChildren()) {
                    String id = Snapshot.getKey();
                    String name = Snapshot.child("name").getValue(String.class);
                    String email = Snapshot.child("email").getValue(String.class);
                    String phone = Snapshot.child("phone").getValue(String.class);
                    String avatar = Snapshot.child("avatar").getValue(String.class);
                    String position = Snapshot.child("position").getValue(String.class);
                    String id_unit = Snapshot.child("id_unit").getValue(String.class);
                    Employee employee = new Employee(
                            id,
                            name,
                            phone,
                            email,
                            position,
                            avatar,
                            id_unit
                    );
                    employeeList.add(employee);
                }
                // sắp xếp các item theo thứ tự tên nếu số lượng đơn vị > 1
                if (employeeList.size() > 1) unitList.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
                contactAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_unit) {
            Intent intent = new Intent(this, AddUnitActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_add_employee) {
            Intent intent = new Intent(this, AddEmployeeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        et_search.clearFocus();
        return false;
    }
}