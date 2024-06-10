package com.example.testdb.activity.edit;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testdb.R;
import com.example.testdb.db.UnitDB;
import com.example.testdb.model.Unit;
import com.squareup.picasso.Picasso;

public class EditUnitActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvNamePage, tvAddEdit, tvDel;
    private EditText etName, etPhone, etEmail, etWebsite, etAddress, etParent;
    private ImageView ivLogo;
    private Unit unit;
    private UnitDB dbUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_unit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack = findViewById(R.id.btn_back);
        tvNamePage = findViewById(R.id.tv_name_layout);
        tvAddEdit = findViewById(R.id.btn_add_edit);
        tvDel = findViewById(R.id.btn_delete);

        ivLogo = findViewById(R.id.iv_logo);
        etName = findViewById(R.id.et_name_unit);
        etPhone = findViewById(R.id.et_phone_unit);
        etEmail = findViewById(R.id.et_email_unit);
        etWebsite = findViewById(R.id.et_website_unit);
        etAddress = findViewById(R.id.et_address_unit);
        etParent = findViewById(R.id.et_parent_unit);

        tvNamePage.setText("Sửa đơn vị");
        tvAddEdit.setText("Lưu");
        tvDel.setVisibility(TextView.GONE);
        dbUnit = new UnitDB();

        // Receive data
        Bundle bundle = getIntent().getExtras();
        unit = (Unit) bundle.getSerializable("obj_unit");
        if (!unit.getLogo().isEmpty()) {
            Picasso.get().load(unit.getLogo()).into(ivLogo);
        }
        etName.setText(unit.getName());
        etEmail.setText(unit.getEmail());
        etPhone.setText(unit.getPhone());
        etWebsite.setText(unit.getWebsite());
        etAddress.setText(unit.getAddress());
        etParent.setText(unit.getParentUnitId());

        // Event
        btnBack.setOnClickListener(v -> finish());
        tvAddEdit.setOnClickListener(v -> {
            if (updateUnit()) {
                Toast.makeText(this, "Cập nhật đơn vị thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private boolean updateUnit() {
        if (!validate()) return false;
        unit.setName(etName.getText().toString());
        unit.setPhone(etPhone.getText().toString());
        unit.setEmail(etEmail.getText().toString());
        unit.setWebsite(etWebsite.getText().toString());
        unit.setAddress(etAddress.getText().toString());
        unit.setParentUnitId(etParent.getText().toString());
        dbUnit.updateUnit(unit.getId(), unit);
        return true;
    }

    private boolean validate() {
        if (etName.getText().toString().isEmpty() ||
                etPhone.getText().toString().isEmpty() ||
                etEmail.getText().toString().isEmpty() ||
                etWebsite.getText().toString().isEmpty() ||
                etAddress.getText().toString().isEmpty() ) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}