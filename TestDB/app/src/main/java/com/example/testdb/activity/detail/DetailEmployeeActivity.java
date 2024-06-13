package com.example.testdb.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testdb.R;
import com.example.testdb.activity.edit.EditEmployeeActivity;
import com.example.testdb.db.EmployeeDB;
import com.example.testdb.db.UnitDB;
import com.example.testdb.model.Employee;
import com.example.testdb.model.Unit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailEmployeeActivity extends AppCompatActivity {

    private TextView tvNamePage, tvAddEdit, tvDel;
    private EditText etName, etPhone, etEmal, etPosition, etUnit;
    private CircleImageView iv_avatar;
    private EmployeeDB dbEmployee;
    private Employee currentEmployee;
    private String idEmployee;
    private ArrayList<Unit> listUnit;
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
        iv_avatar = findViewById(R.id.iv_avatar);
        ImageButton backButton = findViewById(R.id.btn_back);

        setLayout();

        dbEmployee = new EmployeeDB();
        idEmployee = getIntent().getStringExtra("id");
        Bundle getBundle = getIntent().getExtras();
        listUnit = (ArrayList<Unit>) getBundle.getSerializable("unitList");

        // Event
        backButton.setOnClickListener(v -> finish());
        tvAddEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditEmployeeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("obj_employee", currentEmployee);
            bundle.putSerializable("unitList", listUnit);
            intent.putExtras(bundle);
            this.startActivity(intent);
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
                Employee employee = snapshot.getValue(Employee.class);
                currentEmployee = employee;
                setEditText(currentEmployee);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void setEditText(Employee employee) {
        if (!employee.getAvatar().isEmpty()) {
            Picasso.get().load(employee.getAvatar()).into(iv_avatar);
        }
        etName.setText(employee.getName());
        etPhone.setText(employee.getPhone());
        etEmal.setText(employee.getEmail());
        etPosition.setText(employee.getPosition());

        etUnit.setText("Không có");
        for (Unit u : listUnit)
            if (u.getId().equals(employee.getId_unit())) {
                etUnit.setText(u.getName());
                break;
            }
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
            Toast.makeText(this, "Đã xóa nhân viên " + name, Toast.LENGTH_SHORT).show();
            this.finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setLayout() {
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
    }
}