/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    private CustomArrayAdapter customArrayAdapter;
    private ArrayList<Earthquackes> quackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        quackList = new ArrayList<>();

        EarthquackAsyncTask task = new EarthquackAsyncTask();
        task.execute(USGS_URL);


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        customArrayAdapter = new CustomArrayAdapter(this,R.layout.earthquake_activity,quackList);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(customArrayAdapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquackes currentQuacke = (Earthquackes) customArrayAdapter.getItem(position);
                Uri earthquackeUri = Uri.parse(currentQuacke.getmUrl());
                Intent urlIntent = new Intent(Intent.ACTION_VIEW,earthquackeUri);
                startActivity(urlIntent);
            }
        });

    }
    private class EarthquackAsyncTask extends AsyncTask<String, Void,List<Earthquackes>>{

        @Override
        protected List<Earthquackes> doInBackground(String... urls) {
            if (urls.length <1 | urls[0] == null){
                return null;
            }

            return QueryUtils.fetchEarthquakeData(USGS_URL);
        }

        @Override
        protected void onPostExecute(List<Earthquackes> earthquackes) {
            customArrayAdapter.clear();
            if (earthquackes !=null && !earthquackes.isEmpty()){
                quackList.addAll(earthquackes);

            }
        }
    }
}
