/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Omar El-Sayed
 */
public class Item {
    private String item_name;
    private int quantity;
    private long timestamp;

    public Item(String item_name, int quantity, long timestamp) {
        this.item_name = item_name;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public String getItem_name() {
        return item_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    
}
