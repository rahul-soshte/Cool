package com.example.hunter.planstart.CoreFunctionality;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunter.planstart.Places.PlaceType;
import com.example.hunter.planstart.R;

import java.util.ArrayList;

public class Filters extends AppCompatActivity {
    ListView lv;
    String[] Types = {"ATM", "Restaurants", "Theatres","Parks","Zoos","Malls"};

private PlaceTypesAdapter adapter;
private ArrayList<PlaceType> place_choosen_type_list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        lv=(ListView)findViewById(R.id.FilterList);
        //Generate list View from ArrayList
        displayListView();

        checkButtonClick();
    }
    private void displayListView()
    {
        for(int i=0;i<Types.length;i++) {
            PlaceType placeType;
            if(Types[i].equals("Restaurants") || Types[i].equals("Restaurants")) {
                placeType = new PlaceType(Types[i], true);
            }
            else{
                placeType = new PlaceType(Types[i], false);
            }
            place_choosen_type_list.add(placeType);
            adapter=new PlaceTypesAdapter (this, R.layout.place_type, place_choosen_type_list);
            lv.setAdapter(adapter);
        }
    }
    private class PlaceTypesAdapter extends ArrayAdapter<PlaceType> {


        private ArrayList<PlaceType> place_type_choosen_list;

        public PlaceTypesAdapter(Context context, int textViewResourceId, ArrayList<PlaceType> place_type_choosen_list)
        {
            super(context,textViewResourceId,place_type_choosen_list);

            this.place_type_choosen_list = new ArrayList<PlaceType>();
            this.place_type_choosen_list.addAll(place_type_choosen_list);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            //  Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.place_type, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.PlaceTypeName);
                holder.name = (CheckBox) convertView.findViewById(R.id.check);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        PlaceType placeType=(PlaceType)cb.getTag();
                        //UserOne userOne=(UserOne)cb.getTag();
                        //Country country = (Country) cb.getTag();
                        /*Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                                */
                        placeType.setCheck_filter(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            PlaceType placeType = place_type_choosen_list.get(position);
            holder.name.setText(placeType.getName());
            holder.name.setChecked(placeType.isCheck_filter());
            holder.name.setTag(placeType);

            return convertView;

        }

    }
    private void checkButtonClick() {

        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                for(int i=0;i<place_choosen_type_list.size();i++){
                    PlaceType placeType = place_choosen_type_list.get(i);
                    if(placeType.isCheck_filter()){
                        responseText.append("\n" + placeType.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }

}
