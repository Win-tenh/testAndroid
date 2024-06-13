package com.example.testdb.activity.add;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testdb.R;
import com.example.testdb.db.UnitDB;
import com.example.testdb.model.Unit;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddUnitActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private CircleImageView iv_logo;
    private Spinner spParent;
    private TextView tvNamePage, tvAddEdit, tvDel, tvParent;
    private EditText etName, etPhone, etEmail, etWebsite, etAddress, etParent;
    private List<Unit> listUnit;
    private ArrayAdapter<Unit> adapter;
    private UnitDB dbUnit;
    private Unit unit;
    private String parent_id = "";
    private ActivityResultLauncher<Intent> pickImageLauncher;
    private Uri imageUri;
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
        iv_logo = findViewById(R.id.iv_logo);
        etName = findViewById(R.id.et_name_unit);
        etPhone = findViewById(R.id.et_phone_unit);
        etEmail = findViewById(R.id.et_email_unit);
        etWebsite = findViewById(R.id.et_website_unit);
        etAddress = findViewById(R.id.et_address_unit);
        etParent = findViewById(R.id.et_parent_unit);
        spParent = findViewById(R.id.sp_parent_unit);

        setLayout();

        // nhận danh sách unit
        setSpinner();

        // Event
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                imageUri = result.getData().getData();
                iv_logo.setImageURI(imageUri);
            }
        });

        btnBack.setOnClickListener(v -> finish());

        spParent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) parent_id="";
                else {
                    Unit unit = (Unit) parent.getItemAtPosition(position);
                    parent_id = unit.getId();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        iv_logo.setOnClickListener(v -> { setLogo(); });

        tvAddEdit.setOnClickListener(v -> { addUnit(); });
    }

    private void onSuccess(Uri uri) {
        // set logo cho unit
        unit.setLogo(uri.toString());
        dbUnit = new UnitDB();
        dbUnit.addUnit(unit);
        
        // thông báo sau khi thêm unit thành công
        Toast.makeText(this, "Thêm đơn vị thành công", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void addUnit() {
        if (!validate()) return;

        // upload ảnh lên storage
        // sau đó lấy url ảnh từ storage và set vào unit logo
        StorageReference ref = FirebaseStorage
                .getInstance()
                .getReference()
                .child("images_contact_book/" + UUID.randomUUID().toString());
        UploadTask uploadTask = ref.putFile(imageUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            ref.getDownloadUrl().addOnSuccessListener(this::onSuccess);
        });
    }

    private void setLogo() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    private void setSpinner() {
        Bundle bundle = getIntent().getExtras();
        listUnit = new ArrayList<>();
        listUnit.add(0,new Unit("-1", "Chọn đơn vị"));
        listUnit.addAll((List<Unit>) bundle.getSerializable("unitList"));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listUnit);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spParent.setAdapter(adapter);
    }

    private void setLayout() {
        tvNamePage.setText("Thêm đơn vị");
        tvAddEdit.setText("Thêm");
        tvDel.setVisibility(TextView.GONE);
        tvParent.setText("Đơn vị trực thuộc (tùy chọn):");
        spParent.setVisibility(Spinner.VISIBLE);
        etParent.setVisibility(EditText.GONE);
    }

    private boolean validate() {
        // get data input
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String website = etWebsite.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String name = etName.getText().toString().trim();

        // validate
        if (name.isEmpty() ||
                phone.isEmpty() ||
                email.isEmpty() ||
                website.isEmpty() ||
                address.isEmpty() ||
                imageUri == null ) {
            Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        // set data to unit
        unit = new Unit();
        unit.setName(name);
        unit.setEmail(email);
        unit.setPhone(phone);
        unit.setWebsite(website);
        unit.setAddress(address);
        unit.setParentUnitId(parent_id);
        return true;
    }
}