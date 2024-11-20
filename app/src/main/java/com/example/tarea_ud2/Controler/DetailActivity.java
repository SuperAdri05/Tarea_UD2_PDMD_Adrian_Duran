package com.example.tarea_ud2.Controler;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tarea_ud2.R;

public class DetailActivity extends AppCompatActivity {
    private TextView txtName, txtEmail, txtAge;
    private ImageView imgUser;
    private ImageButton btnBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtAge = findViewById(R.id.txtAge);
        imgUser = findViewById(R.id.imgUser);
        btnBack = findViewById(R.id.btnVolver);

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        int age = getIntent().getIntExtra("age", 0);
        int imageResourceId = getIntent().getIntExtra("imageResourceId", R.drawable.image1);

        txtName.setText(name);
        txtEmail.setText(email);
        txtAge.setText(String.valueOf(age));
        imgUser.setImageResource(imageResourceId);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
