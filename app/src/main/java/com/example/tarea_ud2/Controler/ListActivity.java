package com.example.tarea_ud2.Controler;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea_ud2.Model.User;
import com.example.tarea_ud2.Model.UserAdapter;
import com.example.tarea_ud2.Model.Utils;
import com.example.tarea_ud2.R;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private ArrayList<User> userList;
    private ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        btnBack = findViewById(R.id.buttonBack);
        userList = Utils.userList;

        adapter = new UserAdapter(userList, user -> {
            Intent intent = new Intent(ListActivity.this, DetailActivity.class);
            intent.putExtra("name", user.getName());
            intent.putExtra("email", user.getEmail());
            intent.putExtra("age", user.getAge());
            intent.putExtra("imageResourceId", user.getImageResourceId());
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (adapter != null) {
            adapter.clearVisibleTrash();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (adapter != null && adapter.getItemCount() > 0) {
            adapter.clearVisibleTrash();
        } else {
            super.onBackPressed();
        }
    }
}
