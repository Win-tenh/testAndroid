package com.example.testdb.activity.edit;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testdb.R;
import com.example.testdb.db.EmployeeDB;
import com.example.testdb.model.Employee;
import com.example.testdb.model.Unit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditEmployeeActivity extends AppCompatActivity {

    private Spinner spParent;
    private ImageButton btnBack;
    private TextView tvNamePage, tvAddEdit, tvDel;
    private EditText etName, etPhone, etEmal, etPosition, etUnit;
    private CircleImageView iv_avatar;
    private Employee employee;
    private ArrayList<Unit> listUnit;
    private String parent_id;
    private EmployeeDB dbEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_employee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.btn_back);
        tvNamePage = findViewById(R.id.tv_name_layout);
        tvAddEdit = findViewById(R.id.btn_add_edit);
        tvDel = findViewById(R.id.btn_delete);
        // Edit
        iv_avatar = findViewById(R.id.iv_avatar);
        etName = findViewById(R.id.et_name_employee);
        etPhone = findViewById(R.id.et_phone_employee);
        etEmal = findViewById(R.id.et_email_employee);
        etPosition = findViewById(R.id.et_position_employee);
        spParent = findViewById(R.id.sp_parent_unit);
        etUnit = findViewById(R.id.et_unit_employee);

        tvNamePage.setText("Sửa nhân viên");
        tvAddEdit.setText("Lưu");
        tvDel.setVisibility(TextView.GONE);
        etUnit.setVisibility(TextView.GONE);
        spParent.setVisibility(TextView.VISIBLE);
        listUnit = new ArrayList<>();
        listUnit.add(new Unit("-1", "Mặc định: không chọn"));

        dbEmployee = new EmployeeDB();

        // Receive data
        Bundle bundle = getIntent().getExtras();
        listUnit.addAll((ArrayList<Unit>) bundle.getSerializable("unitList"));
        employee = (Employee) bundle.getSerializable("obj_employee");

        if (!employee.getAvatar().isEmpty()) {
            Picasso.get().load(employee.getAvatar()).into(iv_avatar);
        }
        etName.setText(employee.getName());
        etPhone.setText(employee.getPhone());
        etEmal.setText(employee.getEmail());
        etPosition.setText(employee.getPosition());

        ArrayAdapter<Unit> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listUnit);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spParent.setAdapter(adapter);
        // set mặc định là cha của đơn vị, nếu không có cha thì mặc định là 0
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).getId().equals(employee.getId_unit())) {
                spParent.setSelection(i);
                break;
            }
        }
        parent_id = employee.getId_unit();

        // Event
        btnBack.setOnClickListener(v -> finish());
        tvAddEdit.setOnClickListener(v -> {
            if (updateEmployee()) {
                Toast.makeText(this, "Cập nhật nhân viên thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        spParent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Unit unit_parent = (Unit) parent.getItemAtPosition(position);
                if (position == 0) parent_id="";
                else {
                    parent_id = unit_parent.getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean updateEmployee() {
        if (!validate()) return false;
        employee.setName(etName.getText().toString());
        employee.setPhone(etPhone.getText().toString());
        employee.setEmail(etEmal.getText().toString());
        employee.setPosition(etPosition.getText().toString());
        employee.setId_unit(parent_id);
        dbEmployee.updateEmployee(employee.getId(), employee);
        return true;
    }

    private boolean validate() {
        if (etName.getText().toString().isEmpty() ||
                etPhone.getText().toString().isEmpty() ||
                etEmal.getText().toString().isEmpty() ||
                etPosition.getText().toString().isEmpty() ||
                parent_id.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}