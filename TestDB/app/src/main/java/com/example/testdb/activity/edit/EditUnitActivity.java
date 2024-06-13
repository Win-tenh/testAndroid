package com.example.testdb.activity.edit;

import android.os.Bundle;
import android.util.Log;
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
import com.example.testdb.db.UnitDB;
import com.example.testdb.model.Unit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditUnitActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvNamePage, tvAddEdit, tvDel;
    private EditText etName, etPhone, etEmail, etWebsite, etAddress, etParent;
    private CircleImageView ivLogo;
    private Spinner spParent;
    private Unit unit;
    private UnitDB dbUnit;
    private ArrayList<Unit> listUnit;
    private String parent_id;

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
        spParent = findViewById(R.id.sp_parent_unit);

        tvNamePage.setText("Sửa đơn vị");
        tvAddEdit.setText("Lưu");
        tvDel.setVisibility(TextView.GONE);
        etParent.setVisibility(TextView.GONE);
        spParent.setVisibility(TextView.VISIBLE);
        dbUnit = new UnitDB();

        // Receive data
        listUnit = new ArrayList<>();
        listUnit.add(new Unit("-1", "Mặc định: không chọn"));
        Bundle bundle = getIntent().getExtras();
        unit = (Unit) bundle.getSerializable("obj_unit");
        listUnit.addAll((ArrayList<Unit>) bundle.getSerializable("unitList"));

        for (Unit u : listUnit)
            if (unit.getId().equals(u.getId())) {
                listUnit.remove(u);
                break;
            }
        List<Unit> descendants = getAllDescendants(unit, listUnit);
        listUnit.removeAll(descendants); // Remove all descendants

        if (!unit.getLogo().isEmpty()) {
            Picasso.get().load(unit.getLogo()).into(ivLogo);
        }
        etName.setText(unit.getName());
        etEmail.setText(unit.getEmail());
        etPhone.setText(unit.getPhone());
        etWebsite.setText(unit.getWebsite());
        etAddress.setText(unit.getAddress());

        ArrayAdapter<Unit> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listUnit);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spParent.setAdapter(adapter);
        // set mặc định là cha của đơn vị, nếu không có cha thì mặc định là 0
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).getId().equals(unit.getParentUnitId())) {
                spParent.setSelection(i);
                break;
            }
        }
        parent_id = unit.getParentUnitId();

        // Event
        btnBack.setOnClickListener(v -> finish());
        tvAddEdit.setOnClickListener(v -> {
            if (updateUnit()) {
                Toast.makeText(this, "Cập nhật đơn vị thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        spParent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Unit unit_parent = (Unit) parent.getItemAtPosition(position);
                if (unit_parent.getId().equals("-1")) parent_id="";
                else {
                    parent_id = unit_parent.getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean updateUnit() {
        if (!validate()) return false;
        unit.setName(etName.getText().toString().trim());
        unit.setPhone(etPhone.getText().toString().trim());
        unit.setEmail(etEmail.getText().toString().trim());
        unit.setWebsite(etWebsite.getText().toString().trim());
        unit.setAddress(etAddress.getText().toString().trim());
        unit.setParentUnitId(parent_id);
        dbUnit.updateUnit(unit.getId(), unit);
        return true;
    }

    private List<Unit> getAllDescendants(Unit parent, List<Unit> allUnits) {
        List<Unit> descendants = new ArrayList<>();
        for (Unit unit : allUnits) {
            if (parent.getId().equals(unit.getParentUnitId())) {
                descendants.add(unit);
                descendants.addAll(getAllDescendants(unit, allUnits));
            }
        }
        return descendants;
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