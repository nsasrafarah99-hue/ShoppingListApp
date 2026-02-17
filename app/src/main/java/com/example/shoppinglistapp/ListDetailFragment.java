package com.example.shoppinglistapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglistapp.adapter.ShoppingListItemAdapter;
import com.example.shoppinglistapp.data.ShoppingListStore;
import com.example.shoppinglistapp.model.ShoppingListItem;
import com.example.shoppinglistapp.model.ShoppingList;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class ListDetailFragment extends Fragment {

    private static final String ARG_LIST_ID = "list_id";

    private String listId;
    private ShoppingList list;
    private RecyclerView recyclerItems;
    private TextView emptyItems;
    private ShoppingListItemAdapter adapter;
    private String pickedImagePath;

    private final ActivityResultLauncher<String> pickImage = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    pickedImagePath = uri.toString();
                }
            });

    public static ListDetailFragment newInstance(String listId) {
        ListDetailFragment f = new ListDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LIST_ID, listId);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listId = getArguments().getString(ARG_LIST_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = ShoppingListStore.getInstance().getListById(listId);
        if (list == null) {
            requireActivity().getSupportFragmentManager().popBackStack();
            return;
        }
        recyclerItems = view.findViewById(R.id.recycler_items);
        emptyItems = view.findViewById(R.id.empty_items);
        FloatingActionButton fabAddItem = view.findViewById(R.id.fab_add_item);
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(list.getName());
        toolbar.setNavigationOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        recyclerItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ShoppingListItemAdapter();
        recyclerItems.setAdapter(adapter);

        fabAddItem.setOnClickListener(v -> showAddItemDialog());
        refreshItems();
    }

    private void refreshItems() {
        if (list != null) {
            adapter.setItems(list.getItems());
            boolean empty = list.getItems() == null || list.getItems().isEmpty();
            emptyItems.setVisibility(empty ? View.VISIBLE : View.GONE);
        }
    }

    private void showAddItemDialog() {
        pickedImagePath = null;
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_item, null);
        TextInputEditText editName = dialogView.findViewById(R.id.edit_item_name);
        TextInputEditText editQuantity = dialogView.findViewById(R.id.edit_quantity);
        TextInputEditText editPrice = dialogView.findViewById(R.id.edit_price);
        Button btnPickImage = dialogView.findViewById(R.id.btn_pick_image);

        btnPickImage.setOnClickListener(v -> pickImage.launch("image/*"));

        new MaterialAlertDialogBuilder(requireContext())
                .setView(dialogView)
                .setTitle(R.string.add_item)
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    String name = editName.getText() != null ? editName.getText().toString().trim() : "";
                    if (name.isEmpty()) {
                        Toast.makeText(requireContext(), R.string.item_name, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int qty = 1;
                    try {
                        String q = editQuantity.getText() != null ? editQuantity.getText().toString() : "1";
                        if (!q.isEmpty()) qty = Integer.parseInt(q);
                    } catch (NumberFormatException e) {
                        qty = 1;
                    }
                    Double price = null;
                    try {
                        String p = editPrice.getText() != null ? editPrice.getText().toString().trim() : "";
                        if (!p.isEmpty()) price = Double.parseDouble(p);
                    } catch (NumberFormatException ignored) { }

                    ShoppingListItem item = new ShoppingListItem();
                    item.setName(name);
                    item.setQuantity(qty);
                    item.setPrice(price);
                    item.setImagePath(pickedImagePath);

                    if (list != null) {
                        list.addItem(item);
                        refreshItems();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
