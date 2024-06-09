package com.example.testdb.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testdb.R;
import com.example.testdb.db.EmployeeDB;
import com.example.testdb.db.UnitDB;
import com.example.testdb.model.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class DetailEmployeeActivity extends AppCompatActivity {

    private TextView tvNamePage, tvAddEdit, tvDel;
    private EditText etName, etPhone, etEmal, etPosition, etUnit;
    private EmployeeDB dbEmployee;
    private String idEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_employee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvNamePage = findViewById(R.id.tv_name_layout);
        tvAddEdit = findViewById(R.id.btn_add_edit);
        tvDel = findViewById(R.id.btn_delete);
        etName = findViewById(R.id.et_name_employee);
        etPhone = findViewById(R.id.et_phone_employee);
        etEmal = findViewById(R.id.et_email_employee);
        etPosition = findViewById(R.id.et_position_employee);
        etUnit = findViewById(R.id.et_unit_employee);
        ImageButton backButton = findViewById(R.id.btn_back);

        tvNamePage.setText("Chi tiết nhân viên");

        etName.setKeyListener(null);
        etName.setTextIsSelectable(true);
        etPhone.setKeyListener(null);
        etPhone.setTextIsSelectable(true);
        etEmal.setKeyListener(null);
        etEmal.setTextIsSelectable(true);
        etPosition.setKeyListener(null);
        etPosition.setTextIsSelectable(true);
        etUnit.setKeyListener(null);
        etUnit.setTextIsSelectable(true);

        dbEmployee = new EmployeeDB();
        idEmployee = getIntent().getStringExtra("id");

        // Event
        backButton.setOnClickListener(v -> finish());
        tvAddEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailEmployeeActivity.class);
            startActivity(intent);
        });
        tvDel.setOnClickListener(v -> {
            deleteEmployee(idEmployee, etName.getText().toString());
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getEmployeeById(idEmployee);
    }

    private void getEmployeeById(String id) {
        dbEmployee.getRefEmployee().child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) return;
                String name = snapshot.child("name").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                String phone = snapshot.child("phone").getValue(String.class);
                String avatar = snapshot.child("avatar").getValue(String.class);
                String position = snapshot.child("position").getValue(String.class);
                String id_unit = snapshot.child("id_unit").getValue(String.class);
                Employee employee = new Employee(
                        id,
                        name,
                        phone,
                        email,
                        position,
                        avatar,
                        id_unit
                );
                setEditText(employee);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void setEditText(Employee employee) {
        etName.setText(employee.getName());
        etPhone.setText(employee.getPhone());
        etEmal.setText(employee.getEmail());
        etPosition.setText(employee.getPosition());
        if (employee.getId_unit().equals(""))
            etUnit.setText("Không có");
        else etUnit.setText(employee.getId_unit());
    }

    private void deleteEmployee(String id, String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa nhân viên");
        builder.setMessage("Bạn chắc chắn muốn xóa nhân viên "+ name + " không?");

        builder.setPositiveButton("Không", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setNeutralButton("Có", (dialog, which) -> {
            dbEmployee.deleteEmployee(id);
            this.finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}