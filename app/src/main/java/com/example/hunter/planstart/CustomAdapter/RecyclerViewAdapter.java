package com.example.hunter.planstart.CustomAdapter;

/**
 * Created by hunter on 5/9/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hunter.planstart.R;
import com.example.hunter.planstart.User.UserOne;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<UserOne> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<UserOne> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_add_peep, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.fname.setText(itemList.get(position).getFirstName()+" ");
        holder.lname.setText(itemList.get(position).getLastName() + " ");
        holder.username.setText(itemList.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
