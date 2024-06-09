package com.example.testdb.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testdb.R;
import com.example.testdb.activity.edit.EditUnitActivity;

public class DetailUnitActivity extends AppCompatActivity {

    private TextView tvNamePage, tvAddEdit, tvDel;
    private EditText etName, etPhone, etEmail, etWebsite, etAddress, etParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_unit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvNamePage = findViewById(R.id.tv_name_layout);
        tvAddEdit = findViewById(R.id.btn_add_edit);
        tvDel = findViewById(R.id.btn_delete);
        etName = findViewById(R.id.et_name_unit);
        etPhone = findViewById(R.id.et_phone_unit);
        etEmail = findViewById(R.id.et_email_unit);
        etWebsite = findViewById(R.id.et_website_unit);
        etAddress = findViewById(R.id.et_address_unit);
        etParent = findViewById(R.id.et_parent_unit);
        ImageButton btn_back = findViewById(R.id.btn_back);

        tvNamePage.setText("Chi tiết đơn vị");

        etName.setText("Đơn vị 1");
        etPhone.setText("0123456789");
        etEmail.setText("unit1@unit1.com");
        etWebsite.setText("www.unit1.com");
        etAddress.setText("123 Đường ABC, Quận XYZ");
        etParent.setText("Đơn vị 1");

        etName.setKeyListener(null);
        etName.setTextIsSelectable(true);
        etPhone.setKeyListener(null);
        etPhone.setTextIsSelectable(true);
        etEmail.setKeyListener(null);
        etEmail.setTextIsSelectable(true);
        etWebsite.setKeyListener(null);
        etWebsite.setTextIsSelectable(true);
        etAddress.setKeyListener(null);
        etAddress.setTextIsSelectable(true);
        etParent.setKeyListener(null);
        etParent.setTextIsSelectable(true);

        // Event
        btn_back.setOnClickListener(v -> finish());
        tvAddEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditUnitActivity.class);
            startActivity(intent);
        });
        tvDel.setOnClickListener(v -> {
            finish();
        });

    }
}