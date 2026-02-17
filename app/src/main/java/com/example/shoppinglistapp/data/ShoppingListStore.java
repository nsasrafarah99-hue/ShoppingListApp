package com.example.shoppinglistapp.data;

import com.example.shoppinglistapp.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;

/**
 * مخزن قوائم المشتريات (في الذاكرة).
 */
public class ShoppingListStore {

    private static final ShoppingListStore INSTANCE = new ShoppingListStore();
    private final List<ShoppingList> lists;

    private ShoppingListStore() {
        this.lists = new ArrayList<>();
    }

    public static ShoppingListStore getInstance() {
        return INSTANCE;
    }

    public List<ShoppingList> getLists() {
        return lists;
    }

    public void addList(ShoppingList list) {
        if (list != null) {
            lists.add(list);
        }
    }

    public ShoppingList getListById(String id) {
        for (ShoppingList list : lists) {
            if (list.getId() != null && list.getId().equals(id)) {
                return list;
            }
        }
        return null;
    }

    public void removeList(ShoppingList list) {
        lists.remove(list);
    }

    public int getListCount() {
        return lists.size();
    }

    /** استبدال كل القوائم (بعد التحميل من Appwrite) */
    public void replaceAll(List<ShoppingList> newLists) {
        lists.clear();
        if (newLists != null) {
            lists.addAll(newLists);
        }
    }
}
