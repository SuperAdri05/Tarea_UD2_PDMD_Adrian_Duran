package com.example.tarea_ud2.Controllers;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.tarea_ud2.Models.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tarea_ud2.R;
import com.example.tarea_ud2.Resources.DBAccess;

public class RegisterActivity extends AppCompatActivity {

    private EditText edTxtName;
    private EditText edTxtEmail;
    private EditText edTxtAge;
    private Button btnRegister;
    private Button btnBack;
    private ImageView imgUser;
    private int selectedImageResourceId;
    DBAccess mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edTxtName = findViewById(R.id.nameEditText);
        edTxtEmail = findViewById(R.id.emailEditText);
        edTxtAge = findViewById(R.id.ageEditText);
        btnRegister = findViewById(R.id.registerUserButton);
        btnBack = findViewById(R.id.backButton);
        imgUser = findViewById(R.id.logoImageView);

        selectedImageResourceId = R.drawable.image1;
        imgUser.setImageResource(selectedImageResourceId);

        mDB = new DBAccess(this);
        mDB.getVersionDB();

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSelectionDialog();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edTxtName.getText().toString().trim();
                String email = edTxtEmail.getText().toString().trim();
                String ageStr = edTxtAge.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || ageStr.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(RegisterActivity.this, "El correo electrónico no es válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age;
                try {
                    age = Integer.parseInt(ageStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(RegisterActivity.this, "La edad debe ser un número válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (age < 18) {
                    Toast.makeText(RegisterActivity.this, "Debes ser mayor de 18 años para registrarte", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mDB.insert(name, email, age, selectedImageResourceId) != -1){
                    Toast("Usuario registrado exitosamente");
                }else{
                    Toast("Error! Usuario no insertado. Comprueba LogCat");
                    return;
                }

                User newUser = new User(name, email, age, selectedImageResourceId);
                Utils.userList.add(newUser);
            }
        });
    }

    public void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void showImageSelectionDialog() {
        String[] imageOptions = {"Imagen 1", "Imagen 2", "Imagen 3", "Imagen 4"};
        final int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una imagen")
                .setItems(imageOptions, (dialog, which) -> {
                    selectedImageResourceId = images[which];
                    imgUser.setImageResource(selectedImageResourceId);
                })
                .create()
                .show();
    }
}
