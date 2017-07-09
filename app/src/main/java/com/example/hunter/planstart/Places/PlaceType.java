package com.example.hunter.planstart.Places;

/**
 * Created by hunter on 9/7/17.
 */

public class PlaceType {
    boolean check_filter=false;
    String code = null;
    String name = null;
    //boolean selected = false;
   /*
    public Country(String code, String name, boolean selected) {
        super();
        this.code = code;
        this.name = name;
        this.selected = selected;
    }
*/

    public PlaceType(String code,String name,boolean check_filter)
    {

        this.code=code;
        this.check_filter=check_filter;
        this.name=name;

    }
    public PlaceType(String name,boolean check_filter)
    {
        this.check_filter=check_filter;
        this.name=name;

    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public void setCheck_filter(boolean check_filter)
    {
        this.check_filter=check_filter;
    }
    public boolean isCheck_filter()
    {
        return check_filter;
    }

}
