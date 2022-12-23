
import java.util.ArrayList;
import java.util.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Omar El-Sayed
 */
public class Items {
    private final ArrayList<Item> items;
    private final static Items INSTANCE = new Items();

    private  Items() {
        this.items = new ArrayList();
    }

    public static Items getINSTANCE() {
        return INSTANCE;
    }
    public void addItem (Item item) {
        Date date = new Date(System.currentTimeMillis());
        System.out.println("Added "  + item.getQuantity()+" "+ item.getItem_name()+" "+ date.toString()  );
        items.add(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    
    
}
