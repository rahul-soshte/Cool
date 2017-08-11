package com.example.hunter.planstart.GClasses;

import java.io.Serializable;

/**
 * Created by hunter on 27/7/17.
 */

public class RentedThings implements Serializable {
     int user_id_who_putup;
     String productimageurl;
     Double rentperday;
     String prodname;
     String username;
    String prodesc;
    String contactno;


    public RentedThings(String prodname, String productimageurl, Double rentperday, int user_id_who_putup,String username) {
        this.prodname=prodname;
        this.productimageurl = productimageurl;
        this.rentperday = rentperday;
        this.user_id_who_putup = user_id_who_putup;
        this.username=username;
    }
    public RentedThings(String prodname, String productimageurl, Double rentperday, int user_id_who_putup,String username,String prodesc,String contactno) {
        this.prodname=prodname;
        this.productimageurl = productimageurl;
        this.rentperday = rentperday;
        this.user_id_who_putup = user_id_who_putup;
        this.username=username;
        this.contactno=contactno;
        this.prodesc=prodesc;
    }
    public String getProdname()
    {
        return prodname;
    }
    public String getProductimageurl()
    {
        return productimageurl;
    }
    public Double getRentperday()
    {
        return rentperday;
    }

    public String getuser()
    {
        return Integer.toString(user_id_who_putup);
    }
    public String getusername()
    {
        return username;
    }
public String getProdesc()
{
    return prodesc;
}
public String getContactno()
{
    return contactno;
}

}