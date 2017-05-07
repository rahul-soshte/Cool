package com.example.hunter.planstart.User;

/**
 * Created by hunter on 4/5/17.
 */

public class UserOne {

    public String firstname;
    public String lastname;
    public double GpsLat;
    public double GpsLong;
    public int contactno;
    public String email_id;
    public String password;

    public UserOne(String firstname,String lastname,int contactno, String email_id,String password)
    {
        this.firstname=firstname;
        this.lastname=lastname;
        this.contactno=contactno;
        this.email_id=email_id;
        this.password=password;

    }

    public void setGpsLatLong(double latitude,double longitude)
    {
        this.GpsLat=latitude;

        this.GpsLong=longitude;

    }

    public double getGpsLat()
    {
        return GpsLat;
    }

    public double getGpsLong()
    {
        return GpsLong;
    }

    public String FirstName()
    {
        return firstname;
    }

    public String LastName() {
        return lastname;
    }
    public int getContactNo()
    {
    return contactno;
    }
    public String getemailid()
    {
        return email_id;

    }

}
