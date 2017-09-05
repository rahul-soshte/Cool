package com.example.hunter.planstart.User;

/**
 * Created by hunter on 4/5/17.
 */
import com.google.gson.annotations.SerializedName;

public class UserOne {


    @SerializedName("user_id")
    public int user_id;

    @SerializedName("fname")
    public String firstname;
    
    @SerializedName("lname")
    public String lastname;

    @SerializedName("GpsLat")
    public double GpsLat;
    @SerializedName("GpsLong")
    public double GpsLong;
    //public int contactno;
    @SerializedName("email_id")
    public String email_id;
    @SerializedName("password")
    public String password;

    @SerializedName("username")
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
/*
setter methods
 */
    public void setGpsLatLong(double latitude,double longitude)
    {
        this.GpsLat=latitude;

        this.GpsLong=longitude;

    }
public void setUsername(String username)
{
    this.username=username;

}
    public void setEmail_id(String email_id)
    {
        this.email_id=email_id;

    }
    /*
     public int getContactNo()
    {
        return contactno;
    }
  */
/*
Getter Methods
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
