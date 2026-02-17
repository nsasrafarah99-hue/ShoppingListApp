package com.example.shoppinglistapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglistapp.adapter.ShoppingListAdapter;
import com.example.shoppinglistapp.data.ShoppingListStore;
import com.example.shoppinglistapp.model.ShoppingList;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerLists;
    private TextView emptyLists;
    private ShoppingListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerLists = view.findViewById(R.id.recycler_lists);
        emptyLists = view.findViewById(R.id.empty_lists);
        FloatingActionButton fabAddList = view.findViewById(R.id.fab_add_list);

        recyclerLists.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ShoppingListAdapter();
        adapter.setOnListClickListener(list -> openListDetail(list));
        recyclerLists.setAdapter(adapter);

        fabAddList.setOnClickListener(v -> showAddListDialog());
        refreshLists();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLists();
    }

    private void refreshLists() {
        adapter.setLists(ShoppingListStore.getInstance().getLists());
        boolean empty = adapter.getItemCount() == 0;
        emptyLists.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    private void showAddListDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_list, null);
        TextInputEditText editName = dialogView.findViewById(R.id.edit_list_name);

        new MaterialAlertDialogBuilder(requireContext())
                .setView(dialogView)
                .setTitle(R.string.add_list)
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    String name = editName.getText() != null ? editName.getText().toString().trim() : "";
                    if (name.isEmpty()) {
                        Toast.makeText(requireContext(), R.string.list_name, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ShoppingList list = new ShoppingList(name);
                    ShoppingListStore.getInstance().addList(list);
                    refreshLists();
                    openListDetail(list);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void openListDetail(ShoppingList list) {
        ListDetailFragment detail = ListDetailFragment.newInstance(list.getId());
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detail)
                .addToBackStack(null)
                .commit();
    }
}
