package com.example.shoppinglistapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglistapp.R;
import com.example.shoppinglistapp.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private final List<ShoppingList> lists = new ArrayList<>();
    private OnListClickListener listener;

    public interface OnListClickListener {
        void onListClick(ShoppingList list);
    }

    public void setOnListClickListener(OnListClickListener listener) {
        this.listener = listener;
    }

    public void setLists(List<ShoppingList> newLists) {
        lists.clear();
        if (newLists != null) {
            lists.addAll(newLists);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingList list = lists.get(position);
        holder.name.setText(list.getName());
        int count = list.getItems() != null ? list.getItems().size() : 0;
        holder.count.setText(holder.itemView.getContext().getString(R.string.items_count, count));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onListClick(list);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView count;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_list_name);
            count = itemView.findViewById(R.id.item_list_count);
        }
    }
}
