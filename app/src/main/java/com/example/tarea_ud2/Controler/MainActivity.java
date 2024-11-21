package com.example.tarea_ud2.Controler;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.tarea_ud2.Model.User;
import com.example.tarea_ud2.Model.Utils;
import com.example.tarea_ud2.R;

public class MainActivity extends AppCompatActivity{
    private Button btnRegister;
    private Button btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnRegister = findViewById(R.id.registerButton);
        this.btnList = findViewById(R.id.listButton);

        User newUser = new User("Adrian", "adrianduran@gmail.com", 19, R.drawable.image1);
        User newUser2 = new User("Jose Carlos", "josecarlos@gmail.com", 30, R.drawable.image2);
        Utils.userList.add(newUser);
        Utils.userList.add(newUser2);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ListActivity.class);
                startActivity(i);
            }
        });


    }

}

