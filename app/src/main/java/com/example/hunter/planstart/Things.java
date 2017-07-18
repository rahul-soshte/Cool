package com.example.hunter.planstart;

/**
 * Created by hunter on 17/7/17.
 */

public class Things {
    public String name;
    public int quantity;

    public Things(String name)
    {
        this.name=name;
        quantity=1;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName()
    {
        return name;
    }

}
