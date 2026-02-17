package com.example.shoppinglistapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * قائمة مشتريات لها اسم وقائمة من الأغراض.
 * متوافق مع Appwrite: الحقل id يقرأ من $id في الاستجابة.
 */
public class ShoppingList {

    @SerializedName("$id")
    private String id;
    private String name;
    private List<ShoppingListItem> items;
    /** يستخدم من قبل DAL كـ documentId عند الحفظ في Appwrite */
    private String documentId;

    public ShoppingList() {
        this.id = String.valueOf(System.currentTimeMillis());
        this.name = "";
        this.items = new ArrayList<>();
    }

    public ShoppingList(String name) {
        this.id = String.valueOf(System.currentTimeMillis());
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId != null ? documentId : id;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShoppingListItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingListItem> items) {
        this.items = items != null ? items : new ArrayList<>();
    }

    public void addItem(ShoppingListItem item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
    }

    public void removeItem(int index) {
        if (this.items != null && index >= 0 && index < this.items.size()) {
            this.items.remove(index);
        }
    }
}
