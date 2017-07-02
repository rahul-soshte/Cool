package com.example.hunter.planstart.User;

/**
 * Created by hunter on 4/5/17.
 */

public class UserOne {

    public int user_id;

    public String firstname;
    public String lastname;

    public double GpsLat;
    public double GpsLong;
    //public int contactno;

    public String email_id;
    public String password;
    String username;

    public UserOne(int user_id,String firstname,String lastname, String email_id,String username,String password)
    {
        this.user_id=user_id;
        this.firstname=firstname;
        this.lastname=lastname;
        this.username=username;
        this.email_id=email_id;
        this.password=password;
    }
    public UserOne(int user_id)
    {
        this.user_id=user_id;
    }

    public void setGpsLatLong(double latitude,double longitude)
    {
        this.GpsLat=latitude;

        this.GpsLong=longitude;

    }
    /*
     public int getContactNo()
    {
        return contactno;
    }
  */

    public double getGpsLat()
    {
        return GpsLat;
    }

    public double getGpsLong()
    {
        return GpsLong;
    }

    public String getFirstName()
    {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getemailid()
    {
        return email_id;

    }
    public  String getUsername()
    {
        return username;

    }
    public String getPassword()
    {
        return password;
    }

    public int getUser_id()
    {
        return user_id;
    }
}
