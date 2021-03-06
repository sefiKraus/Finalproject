package com.sefy.finalproject.Model;

/**
 * Created by sefy1 on 04/08/2017.
 */

public class CartItem {
    private ItemModel item;
    private int quantity;
    private int totalPrice;


    public CartItem(ItemModel item, int quantity, int totalPrice) {
        this.item = item;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public ItemModel getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "item=" + item +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public void setItem(ItemModel item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        totalPrice=item.getPrice()*quantity;
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
