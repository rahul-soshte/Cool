package com.example.hunter.planstart.Places;

/**
 * Created by hunter on 21/2/17.
 */

public class PlacesOne {
     String name;
     double longitude;
     double latitude;


public PlacesOne(String name, double latitude, double longitude)
{
    this.name=name;
    this.longitude=longitude;
    this.latitude=latitude;

}
public String getPlaceName() {
    return name;
}
public void setLongitude(double longitude)
{this.longitude=longitude;
}
    public void setLatitude(double latitude)
    {
        this.latitude=latitude;
    }
public double getLatitude()
{
    return latitude;
}
    public double getLongitude()
    {
        return longitude;

    }


}
