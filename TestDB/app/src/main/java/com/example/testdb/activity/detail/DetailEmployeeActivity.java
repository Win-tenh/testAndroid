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

public class DetailEmployeeActivity extends AppCompatActivity {

    private TextView tvNamePage, tvAddEdit, tvDel;
    private EditText etName, etPhone, etEmal, etPosition, etUnit;
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

        etName.setText("Nguyễn Văn A");
        etPhone.setText("0987654321");
        etEmal.setText("john.mckinley@examplepetstore.com");
        etPosition.setText("Nhân viên");
        etUnit.setText("Công ty ABC");

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

        // Event
        backButton.setOnClickListener(v -> finish());
        tvAddEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailEmployeeActivity.class);
            startActivity(intent);
        });
        tvDel.setOnClickListener(v -> {
            finish();
        });
    }
}