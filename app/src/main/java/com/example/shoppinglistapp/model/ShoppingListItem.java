package com.example.shoppinglistapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * عنصر في قائمة المشتريات: اسم، كمية، سعر اختياري، صورة اختيارية.
 * متوافق مع Appwrite: الحقل id يقرأ من $id، و listId يربط بالقائمة.
 */
public class ShoppingListItem {

    @SerializedName("$id")
    private String id;
    private String listId;
    private String name;
    private int quantity;
    private Double price;
    private String imagePath;
    /** يستخدم من قبل DAL كـ documentId عند الحفظ في Appwrite */
    private String documentId;

    public ShoppingListItem() {
        this.name = "";
        this.quantity = 1;
        this.price = null;
        this.imagePath = null;
    }

    public ShoppingListItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.price = null;
        this.imagePath = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
