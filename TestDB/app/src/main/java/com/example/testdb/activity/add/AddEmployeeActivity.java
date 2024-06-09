package com.example.testdb.activity.add;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testdb.R;
import com.example.testdb.activity.detail.DetailEmployeeActivity;
import com.example.testdb.db.EmployeeDB;
import com.example.testdb.model.Employee;
import com.example.testdb.model.Unit;

public class AddEmployeeActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvNamePage, tvAddEdit, tvDel;
    private EditText etName, etPhone, etEmail, etPosition, etUnit;
    private EmployeeDB dbEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_employee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.btn_back);
        tvNamePage = findViewById(R.id.tv_name_layout);
        tvAddEdit = findViewById(R.id.btn_add_edit);
        tvDel = findViewById(R.id.btn_delete);

        etName = findViewById(R.id.et_name_employee);
        etPhone = findViewById(R.id.et_phone_employee);
        etEmail = findViewById(R.id.et_email_employee);
        etPosition = findViewById(R.id.et_position_employee);
        etUnit = findViewById(R.id.et_unit_employee);

        tvNamePage.setText("Thêm nhân viên");
        tvAddEdit.setText("Thêm");
        tvDel.setVisibility(TextView.GONE);

        dbEmployee = new EmployeeDB();

        // Event
        btnBack.setOnClickListener(v -> finish());
        tvAddEdit.setOnClickListener(v -> {
            // lấy dữ liệu từ input
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String position = etPosition.getText().toString().trim();
            String unit = etUnit.getText().toString().trim();
            String avatar = "";
            // check dữ liệu
            if (!validate(name, phone, email, position, unit)) {
                Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            // thêm dữ liệu vào database
            Employee employee = new Employee("", name, phone, email, position, avatar, unit);
            dbEmployee.addEmployee(employee);
            finish();
        });
    }

    private boolean validate(String name, String phone, String email, String position, String unit) {
        if (name.isEmpty() ||
                phone.isEmpty() ||
                email.isEmpty() ||
                position.isEmpty() ||
                unit.isEmpty()) {
            return false;
        }
        return true;
    }
}