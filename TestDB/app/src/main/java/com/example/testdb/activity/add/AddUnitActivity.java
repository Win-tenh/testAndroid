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
import com.example.testdb.activity.detail.DetailUnitActivity;
import com.example.testdb.db.UnitDB;
import com.example.testdb.model.Unit;

public class AddUnitActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvNamePage, tvAddEdit, tvDel, tvParent;
    private EditText etName, etPhone, etEmail, etWebsite, etAddress, etParent;
    private UnitDB dbUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_unit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.btn_back);
        tvNamePage = findViewById(R.id.tv_name_layout);
        tvAddEdit = findViewById(R.id.btn_add_edit);
        tvDel = findViewById(R.id.btn_delete);
        tvParent = findViewById(R.id.tv_parent_unit);
        // input
        etName = findViewById(R.id.et_name_unit);
        etPhone = findViewById(R.id.et_phone_unit);
        etEmail = findViewById(R.id.et_email_unit);
        etWebsite = findViewById(R.id.et_website_unit);
        etAddress = findViewById(R.id.et_address_unit);
        etParent = findViewById(R.id.et_parent_unit);

        tvNamePage.setText("Thêm đơn vị");
        tvAddEdit.setText("Thêm");
        tvDel.setVisibility(TextView.GONE);
        tvParent.setText("Đơn vị trực thuộc (tùy chọn):");

        dbUnit = new UnitDB();

        // Event
        btnBack.setOnClickListener(v -> finish());
        tvAddEdit.setOnClickListener(v -> {
            // lấy dữ liệu từ input
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String website = etWebsite.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String parent = etParent.getText().toString().trim();
            String logo = "";
            // check dữ liệu
            if (!validate(name, phone, email, website, address)) {
                Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            // thêm dữ liệu vào database
            Unit unit = new Unit("", name, email, website, logo, address, phone, parent);
            dbUnit.addUnit(unit);
            finish();
        });
    }

    private boolean validate(String name, String phone, String email, String website, String address) {
        if (name.isEmpty() ||
                phone.isEmpty() ||
                email.isEmpty() ||
                website.isEmpty() ||
                address.isEmpty()) {
            return false;
        }
        return true;
    }
}