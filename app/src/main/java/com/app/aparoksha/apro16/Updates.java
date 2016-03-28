package com.app.aparoksha.apro16;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Updates extends AppCompatActivity {
    Integer temp;
    TextView tv;
    private Toolbar toolbar;
    private ListView mList;
    private View shad;
    String[] events;
    int[] images;
    String[] timing;
    String[] venue;
    String[] intents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature((Window.FEATURE_ACTION_BAR_OVERLAY));
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contacts);

        toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //dont forget to change the list ID
        mList = (ListView) findViewById(R.id.listUpdate);
        //shad = (View) findViewById(R.id.shadow);


        //mList.addHeaderView(Updates.this.getLayoutInflater().inflate(R.layout.listview_header, null));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Updates");
        //query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> event_list, com.parse.ParseException e) {
                if (e == null) {
                    //temp = event_list.get(0).getInt("Temperature");
                    for (int i = 0; i < event_list.size(); i++) {
                        events[i] = event_list.get(i).getString("Events");
                        timing[i] = event_list.get(i).getString("Timing");
                        venue[i] = event_list.get(i).getString("Venue");
                        intents[i] = event_list.get(i).getString("Intent");

                    }


                } else {
                    e.printStackTrace();
                }
            }
        });
        String a = events.length+" ";
        Toast.makeText(this,a,Toast.LENGTH_LONG).show();
        //initList(events, timing, venue, intents);
    }

    public void initList( String[] eventsArray, String[] timingList,String[] venue, String[] intentsList) {
        if (eventsArray.length != 0) {

            ArrayList<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();

            for (int i = 0; i < eventsArray.length; i++) {
                HashMap<String, String> candy = new HashMap<String, String>();
                candy.put("event", eventsArray[i]);
                candy.put("time", timingList[i]);
                candy.put("venue", venue[i]);
                candy.put("intent", intentsList[i].trim());
                eventList.add(candy);
            }
            ListAdapter adapter = new SimpleAdapter(
                    Updates.this ,
                    eventList,
                    R.layout.list_item,
                    new String[] { "event", "image", "time", "venue", "intent" },
                    new int[] { R.id.event_name, R.id.eventImg, R.id.eventTime, R.id.eventVenue, R.id.intent }) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView item_name = (TextView)view.findViewById(R.id.event_name);

                    return view;
                }
            };

            mList.setAdapter(adapter);
        }
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String intentToOpen = "com.app.aparoksha.apro16." + intents[position];


                Intent i = new Intent(intentToOpen);
                i.putExtra("INTENT", intentToOpen);
                startActivity(i);
            }
        });
    }
}

