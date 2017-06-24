package com.example.hunter.planstart.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hunter.planstart.R;
import com.example.hunter.planstart.User.UserOne;

import java.util.ArrayList;

/**
 * Created by hunter on 24/6/17.
 */

public class UserAdapter extends ArrayAdapter {
    private ArrayList<UserOne> users;

    public UserAdapter(Context context, int textViewResourceId, ArrayList<UserOne> users)
    {
        super(context,textViewResourceId,users);

        this.users=users;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v=convertView;

        if(v==null)
        {
            LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.user_add_peep,null);
        }

        UserOne i=users.get(position);
        if(i!=null)
        {
            TextView tfname=(TextView)v.findViewById(R.id.fname);
            TextView tlname=(TextView)v.findViewById(R.id.lname);
            TextView tusername=(TextView)v.findViewById(R.id.username);


            if(tfname!=null)
            {
                tfname.setText(i.getFirstName());

            }

            if(tlname!=null)
            {
                tlname.setText(i.getLastName());

            }

            if(tusername!=null)
            {
                tusername.setText(i.getUsername());
            }

        }
        return v;
    }

}
