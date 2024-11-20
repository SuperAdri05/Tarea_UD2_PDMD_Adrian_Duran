package com.example.tarea_ud2.Model;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea_ud2.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final ArrayList<User> userList;
    private OnUserClickListener listener;
    private int visibleTrashPosition = -1;
    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public UserAdapter(ArrayList<User> userList, OnUserClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = userList.get(position);

        holder.txtName.setText(user.getName());
        holder.imgUser.setImageResource(user.getImageResourceId());

        holder.imgDelete.setVisibility(position == visibleTrashPosition ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            if (visibleTrashPosition != -1) {
                visibleTrashPosition = -1;
                notifyDataSetChanged();
            } else {
                listener.onUserClick(user);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            visibleTrashPosition = position;
            notifyDataSetChanged();
            return true;
        });

        holder.imgDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage("¿Seguro que quieres eliminar a este usuario?")
                    .setPositiveButton("Sí", (dialog, id) -> {
                        userList.remove(position);
                        notifyItemRemoved(position);
                        visibleTrashPosition = -1;
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (dialog, id) -> {
                        dialog.dismiss();
                    });
            builder.create().show();
        });
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void clearVisibleTrash() {
        if (visibleTrashPosition != -1) {
            visibleTrashPosition = -1;
            notifyDataSetChanged();
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgUser, imgDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgUser = itemView.findViewById(R.id.imgUser);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
