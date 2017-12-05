package com.example.kevin.mygooglemapsapp1204174;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;
    
    // Google Maps API Key
    // https://developers.google.com/maps/documentation/javascript/get-api-key
    public String API_KEY = "YOUR_API_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new FetchJsonData().execute();
            }
        });

    }

    private class FetchJsonData extends AsyncTask<Void, Void, JsonObject> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonStr = null;

        @Override
        protected JsonObject doInBackground(Void... params) {
            try {
                URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=40.6655101,-73.89188969999998&destinations=40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.6905615%2C-73.9976592%7C40.659569%2C-73.933783%7C40.729029%2C-73.851524%7C40.6860072%2C-73.6334271%7C40.598566%2C-73.7527626%7C40.659569%2C-73.933783%7C40.729029%2C-73.851524%7C40.6860072%2C-73.6334271%7C40.598566%2C-73.7527626&key="+API_KEY);

                // Create connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the inputStream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null){
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0){
                    return null;
                }
                jsonStr = buffer.toString();

                JsonParser parser = new JsonParser();
                JsonObject o = parser.parse(jsonStr).getAsJsonObject();

                return o;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JsonObject o){
            super.onPostExecute(o);
            JsonParser parser = new JsonParser();

            // Destination, Origin
            JsonArray o_dest =  o.getAsJsonArray("destination_addresses");
            JsonArray o_ori = o.getAsJsonArray("origin_addresses");
            String dest;
            String ori = o_dest.get(0).toString();

            // Trimming
            JsonArray rows = o.getAsJsonArray("rows");
            JsonElement elements = rows.get(0);
            JsonObject o1 = parser.parse(elements.toString()).getAsJsonObject();
            JsonArray a1 = o1.getAsJsonArray("elements");

            JsonElement e1;
            JsonObject o2;
            JsonObject o3;
            JsonObject o4;

            // Distance, Duration
            String dist;
            String duration;

            double dist_double;
            for (int i =0; i < a1.size(); i++){
                dest = o_dest.get(i).toString();

                e1 = a1.get(i);
                o2 = parser.parse(e1.toString()).getAsJsonObject();
                o3 = o2.getAsJsonObject("distance");
                o4 = o2.getAsJsonObject("duration");

                dist = o3.get("text").getAsString();
                dist = dist.substring(0,dist.indexOf("mi")).trim();
                dist_double = Double.parseDouble(dist);

                duration = o4.get("text").getAsString();

                ListItem item = new ListItem(dest, dist_double, duration);
                listItems.add(item);
            }
            Collections.sort(listItems);
            adapter = new MyAdapter(listItems, getApplication());
            recyclerView.setAdapter(adapter);
        }
    }
}
