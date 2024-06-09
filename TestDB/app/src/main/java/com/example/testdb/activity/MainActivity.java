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
import com.example.testdb.model.ItemContact;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private EditText et_search;
    private TabLayout tabLayout;
    private RecyclerView rcv_contact;
    private ContactAdapter contactAdapter;
    private List<ItemContact> unitList, employeeList;
    private int selectedTabPosition = 0;
    private int selectedEmployeePosition = 0;
    private int selectedUnitPosition = 0;


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

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // hide keyboard
        findViewById(R.id.main).setOnTouchListener(this);

        unitList = new ArrayList<>();
        employeeList = new ArrayList<>();
        getData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv_contact.setLayoutManager(layoutManager);
        rcv_contact.setAdapter(contactAdapter);
        contactAdapter.setContactList(unitList);
        contactAdapter.notifyDataSetChanged();

        et_search.setHint("Tìm kiếm tên đơn vị");

        // chọn vào từng item của tablayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    selectedTabPosition = 0;
                    contactAdapter.setContactList(unitList);
                    contactAdapter.notifyDataSetChanged();
                    et_search.setHint("Tìm kiếm tên đơn vị");
                }
                else if (tab.getPosition() == 1) {
                    selectedTabPosition = 1;
                    contactAdapter.setContactList(employeeList);
                    contactAdapter.notifyDataSetChanged();
                    et_search.setHint("Tìm kiếm tên nhân viên");
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

//        TextView unit1_btn = findViewById(R.id.unit1_btn);
//        unit1_btn.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, UnitDetailActivity.class);
//            intent.putExtra("unit_id", 1);
//            startActivity(intent);
//        });
//
//        LinearLayout employee1 = findViewById(R.id.employee1);
//        employee1.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, EmployeeDetailActivity.class);
//            intent.putExtra("employee_id", 1);
//            startActivity(intent);
//        });

    }

    private void getData() {
        unitList.add(new ItemContact(R.drawable.avatar1, "Đơn vị 4"));
        unitList.add(new ItemContact(R.drawable.avatar1, "Đơn vị 2"));
        unitList.add(new ItemContact(R.drawable.avatar1, "Đơn vị 3"));

        employeeList.add(new ItemContact(R.drawable.avatar1, "James Harrison 1"));
        employeeList.add(new ItemContact(R.drawable.avatar1, "Aberta Tyler 2"));
        employeeList.add(new ItemContact(R.drawable.avatar1, "John Johnson 3"));

        // sắp xếp các item theo thứ tự tên
        unitList.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        employeeList.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
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