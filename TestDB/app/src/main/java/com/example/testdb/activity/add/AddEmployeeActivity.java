package com.example.testdb.activity.add;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class AddEmployeeActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Spinner spParent;
    private TextView tvNamePage, tvAddEdit, tvDel, tvUnit;
    private EditText etName, etPhone, etEmail, etPosition, etUnit;
    private EmployeeDB dbEmployee;
    private List<Unit> listUnit;
    private String parent_id = "";

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
        tvUnit = findViewById(R.id.tv_unit_employee);

        etName = findViewById(R.id.et_name_employee);
        etPhone = findViewById(R.id.et_phone_employee);
        etEmail = findViewById(R.id.et_email_employee);
        etPosition = findViewById(R.id.et_position_employee);
        spParent = findViewById(R.id.sp_parent_unit);
        etUnit = findViewById(R.id.et_unit_employee);

        tvNamePage.setText("Thêm nhân viên");
        tvAddEdit.setText("Thêm");
        tvDel.setVisibility(TextView.GONE);
        tvUnit.setText("Đơn vị trực thuộc (tùy chọn):");
        spParent.setVisibility(Spinner.VISIBLE);
        etUnit.setVisibility(EditText.GONE);

        dbEmployee = new EmployeeDB();

        // nhận danh sách unit
        Bundle bundle = getIntent().getExtras();
        listUnit = new ArrayList<>();
        listUnit.add(0,new Unit("-1", "Chọn đơn vị"));
        listUnit.addAll((List<Unit>) bundle.getSerializable("unitList"));

        ArrayAdapter<Unit> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listUnit);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spParent.setAdapter(adapter);

        // Event
        btnBack.setOnClickListener(v -> finish());
        spParent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // chọn vào đơn vị nào thì log id đơn vị đó
                if (position == 0) parent_id="";
                else {
                    Unit unit = (Unit) parent.getItemAtPosition(position);
                    parent_id = unit.getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tvAddEdit.setOnClickListener(v -> {
            // lấy dữ liệu từ input
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String position = etPosition.getText().toString().trim();
            String avatar = "";
            // check dữ liệu
            if (!validate(name, phone, email, position)) {
                Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            // thêm dữ liệu vào database
            Employee employee = new Employee();
            employee.setName(name);
            employee.setPhone(phone);
            employee.setEmail(email);
            employee.setPosition(position);
            employee.setAvatar(avatar);
            employee.setId_unit(parent_id);
            dbEmployee.addEmployee(employee);
            Toast.makeText(this, "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private boolean validate(String name, String phone, String email, String position) {
        if (name.isEmpty() ||
                phone.isEmpty() ||
                email.isEmpty() ||
                position.isEmpty() ||
                parent_id.isEmpty() ) {
            return false;
        }
        return true;
    }
}