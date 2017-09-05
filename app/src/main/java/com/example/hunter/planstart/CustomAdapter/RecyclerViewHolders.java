package com.example.hunter.planstart.CustomAdapter;

/**
 * Created by hunter on 5/9/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hunter.planstart.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView fname;
    public TextView lname;
    public TextView username;
    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        fname = (TextView)itemView.findViewById(R.id.fname);
        lname = (TextView)itemView.findViewById(R.id.lname);
        username = (TextView)itemView.findViewById(R.id.username);

    }
    @Override
    public void onClick(View view) {
    }
}
