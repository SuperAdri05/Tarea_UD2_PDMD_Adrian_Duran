package com.example.tarea_ud2.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea_ud2.Models.User;
import com.example.tarea_ud2.Models.UserAdapter;
import com.example.tarea_ud2.Models.Utils;
import com.example.tarea_ud2.R;
import com.example.tarea_ud2.Resources.DBAccess;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private ArrayList<User> userList;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        userList = Utils.userList;

        adapter = new UserAdapter(userList, this::showDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        adapter.setOnSelectionChangeListener(selectedCount -> {
            if (selectedCount > 0) {
                if (actionMode == null) {
                    actionMode = startSupportActionMode(actionModeCallback);
                }
                actionMode.setTitle(selectedCount + " seleccionados");
            } else if (actionMode != null) {
                actionMode.finish();
            }
        });
    }

    private void showDetail(User user) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("name", user.getName());
        intent.putExtra("email", user.getEmail());
        intent.putExtra("age", user.getAge());
        intent.putExtra("imageResourceId", user.getImageResourceId());
        startActivity(intent);
    }

    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_action, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.action_delete) {
                new AlertDialog.Builder(ListActivity.this)
                        .setTitle("Confirmar eliminación")
                        .setMessage("¿Estás seguro de que quieres eliminar este usuario?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            adapter.deleteSelectedUsers();
                            mode.finish();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            adapter.clearSelection();
        }
    };
}
