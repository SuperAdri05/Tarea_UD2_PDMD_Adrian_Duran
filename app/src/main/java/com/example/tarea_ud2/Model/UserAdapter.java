package com.example.tarea_ud2.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea_ud2.R;

import java.util.ArrayList;
import java.util.HashSet;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final ArrayList<User> userList;
    private final HashSet<Integer> selectedPositions = new HashSet<>();
    private OnSelectionChangeListener selectionChangeListener;
    private OnUserClickListener listener;

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public interface OnSelectionChangeListener {
        void onSelectionChanged(int selectedCount);
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
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.txtName.setText(user.getName());
        holder.imgUser.setImageResource(user.getImageResourceId());
        holder.itemView.setSelected(selectedPositions.contains(position));

        holder.itemView.setOnClickListener(v -> {
            if (selectedPositions.isEmpty()) {
                listener.onUserClick(user);
            } else {
                toggleSelection(position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            toggleSelection(position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setOnSelectionChangeListener(OnSelectionChangeListener listener) {
        this.selectionChangeListener = listener;
    }

    public void deleteSelectedUsers() {
        ArrayList<User> usersToRemove = new ArrayList<>();
        for (int position : selectedPositions) {
            usersToRemove.add(userList.get(position));
        }
        userList.removeAll(usersToRemove);
        clearSelection();
        notifyDataSetChanged();
    }

    public void clearSelection() {
        selectedPositions.clear();
        notifyDataSetChanged();
        if (selectionChangeListener != null) {
            selectionChangeListener.onSelectionChanged(0);
        }
    }

    private void toggleSelection(int position) {
        if (selectedPositions.contains(position)) {
            selectedPositions.remove(position);
        } else {
            selectedPositions.add(position);
        }
        notifyItemChanged(position);
        if (selectionChangeListener != null) {
            selectionChangeListener.onSelectionChanged(selectedPositions.size());
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgUser = itemView.findViewById(R.id.imgUser);
        }
    }
}
