package com.example.testdb.activity.add;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testdb.R;
import com.example.testdb.activity.detail.DetailUnitActivity;

public class AddUnitActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvNamePage, tvAddEdit, tvDel;

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

        tvNamePage.setText("Thêm đơn vị");
        tvAddEdit.setText("Thêm");
        tvDel.setVisibility(TextView.GONE);

        // Event
        btnBack.setOnClickListener(v -> finish());
        tvAddEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailUnitActivity.class);
            startActivity(intent);
        });
    }
}