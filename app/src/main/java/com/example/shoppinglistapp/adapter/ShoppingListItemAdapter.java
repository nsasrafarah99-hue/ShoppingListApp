package com.example.shoppinglistapp.adapter;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglistapp.R;
import com.example.shoppinglistapp.model.ShoppingListItem;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListItemAdapter extends RecyclerView.Adapter<ShoppingListItemAdapter.ViewHolder> {

    private final List<ShoppingListItem> items = new ArrayList<>();

    public void setItems(List<ShoppingListItem> newItems) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingListItem item = items.get(position);
        holder.name.setText(item.getName());
        holder.quantity.setText(holder.itemView.getContext().getString(R.string.quantity) + ": " + item.getQuantity());

        if (item.getPrice() != null) {
            holder.price.setVisibility(View.VISIBLE);
            holder.price.setText(holder.itemView.getContext().getString(R.string.price_optional) + ": " + item.getPrice());
        } else {
            holder.price.setVisibility(View.GONE);
        }

        String path = item.getImagePath();
        if (path != null && !path.isEmpty()) {
            try {
                if (path.startsWith("content://") || path.startsWith("file://")) {
                    Uri uri = Uri.parse(path);
                    InputStream is = holder.itemView.getContext().getContentResolver().openInputStream(uri);
                    if (is != null) {
                        holder.image.setVisibility(View.VISIBLE);
                        holder.image.setImageBitmap(BitmapFactory.decodeStream(is));
                        is.close();
                    } else {
                        holder.image.setVisibility(View.GONE);
                    }
                } else {
                    File f = new File(path);
                    if (f.exists()) {
                        holder.image.setVisibility(View.VISIBLE);
                        holder.image.setImageBitmap(BitmapFactory.decodeFile(path));
                    } else {
                        holder.image.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            holder.image.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView image;
        final TextView name;
        final TextView quantity;
        final TextView price;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            quantity = itemView.findViewById(R.id.item_quantity);
            price = itemView.findViewById(R.id.item_price);
        }
    }
}
