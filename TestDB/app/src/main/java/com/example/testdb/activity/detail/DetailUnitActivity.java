package com.example.testdb.activity.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testdb.R;
import com.example.testdb.activity.edit.EditUnitActivity;
import com.example.testdb.db.UnitDB;
import com.example.testdb.model.Unit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailUnitActivity extends AppCompatActivity {

    private TextView tvNamePage, tvAddEdit, tvDel;
    private EditText etName, etPhone, etEmail, etWebsite, etAddress, etParent;
    private ImageView ivLogo;
    private UnitDB dbUnit;
    private String idUnit;

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
        ivLogo = findViewById(R.id.iv_logo);
        etName = findViewById(R.id.et_name_unit);
        etPhone = findViewById(R.id.et_phone_unit);
        etEmail = findViewById(R.id.et_email_unit);
        etWebsite = findViewById(R.id.et_website_unit);
        etAddress = findViewById(R.id.et_address_unit);
        etParent = findViewById(R.id.et_parent_unit);
        ImageButton btn_back = findViewById(R.id.btn_back);

        tvNamePage.setText("Chi tiết đơn vị");

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

        dbUnit = new UnitDB();
        idUnit = getIntent().getStringExtra("id");

        // Event
        btn_back.setOnClickListener(v -> finish());
        tvAddEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditUnitActivity.class);
            startActivity(intent);
        });
        tvDel.setOnClickListener(v -> {
            deleteUnit(idUnit, etName.getText().toString());
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUnitById(idUnit);
    }

    private void getUnitById(String id) {
        dbUnit.getRefUnit().child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) return;
                String name = snapshot.child("name").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                String phone = snapshot.child("phone").getValue(String.class);
                String logo = snapshot.child("logo").getValue(String.class);
                String website = snapshot.child("website").getValue(String.class);
                String address = snapshot.child("address").getValue(String.class);
                String parent_id = snapshot.child("parentUnitId").getValue(String.class);
                Unit unit = new Unit(
                        id,
                        name,
                        email,
                        website,
                        logo,
                        address,
                        phone,
                        parent_id
                );
                setEditText(unit);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                finish();
            }
        });
    }

    private void setEditText(Unit unit) {
        if (!unit.getLogo().isEmpty()) {
            Picasso.get().load(unit.getLogo()).into(ivLogo);
        }
        etName.setText(unit.getName());
        etEmail.setText(unit.getEmail());
        etPhone.setText(unit.getPhone());
        etWebsite.setText(unit.getWebsite());
        etAddress.setText(unit.getAddress());
        if (unit.getParentUnitId().equals("")) {
            etParent.setText("Không có");
        } else {
            etParent.setText(unit.getParentUnitId());
        }
    }

    private void deleteUnit(String id, String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa đơn vị");
        builder.setMessage("Bạn chắc chắn muốn xóa đơn vị "+ name + " không?");

        builder.setPositiveButton("Không", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setNeutralButton("Có", (dialog, which) -> {
            dbUnit.deleteUnit(id);
            this.finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}