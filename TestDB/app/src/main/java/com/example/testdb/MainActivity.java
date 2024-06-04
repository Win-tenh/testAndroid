package com.example.testdb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView unit1_btn = findViewById(R.id.unit1_btn);
        unit1_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UnitDetailActivity.class);
            intent.putExtra("unit_id", 1);
            startActivity(intent);
        });

        LinearLayout employee1 = findViewById(R.id.employee1);
        employee1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EmployeeDetailActivity.class);
            intent.putExtra("employee_id", 1);
            startActivity(intent);
        });

    }
}