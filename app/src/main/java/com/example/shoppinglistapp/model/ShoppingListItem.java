package com.example.shoppinglistapp.model;

/**
 * عنصر في قائمة المشتريات: اسم، كمية، سعر اختياري، صورة اختيارية.
 */
public class ShoppingListItem {

    private String name;
    private int quantity;
    private Double price;
    private String imagePath;

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
