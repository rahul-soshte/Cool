package com.example.hunter.planstart.GClasses;

/**
 * Created by hunter on 27/7/17.
 */

public class RentedThings{


     int user_id_who_putup;
     String productimageurl;
    Double rentperday;
String prodname;

    public RentedThings(String prodname, String productimageurl, Double rentperday, int user_id_who_putup) {
        this.prodname=prodname;
        this.productimageurl = productimageurl;
        this.rentperday = rentperday;
        this.user_id_who_putup = user_id_who_putup;
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

    public int getuser()
    {
        return user_id_who_putup;
    }


}