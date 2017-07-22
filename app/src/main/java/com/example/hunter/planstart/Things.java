package com.example.hunter.planstart;

/**
 * Created by hunter on 17/7/17.
 */

public class Things {

    public String name;
    public int quantity;
    private int mType;
    public static final int AUTOCOMPLETE_TYPE = 0;
    public static final int BORLIST_TYPE = 1;
    public boolean isAutoComplete;

    public Things(String name,boolean isAutoComplete)
    {
        this.name=name;
        this.isAutoComplete=isAutoComplete;
        quantity=1;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name){ this.name=name;}

    public void setisAutocomplete(boolean isAutoComplete)
    {
        this.isAutoComplete=isAutoComplete;
    }


    //public int getType() {
      //  return mType;
    //}

    //public void setType(int type) {
      //  this.mType = type;
    //}

}
