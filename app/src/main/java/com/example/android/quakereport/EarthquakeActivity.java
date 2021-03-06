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

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquackes>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    private CustomArrayAdapter customArrayAdapter;
    private ArrayList<Earthquackes> quackList;
    private static final int LOADER_ID = 99;
    private TextView emptyView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        quackList = new ArrayList<>();
        emptyView = (TextView) findViewById(R.id.empty);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID,null,this);
            Log.v(LOG_TAG,"am under the loader init");

        }else{

            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_connection);
        }



//        EarthquackAsyncTask task = new EarthquackAsyncTask();
//        task.execute(USGS_URL);


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setEmptyView(emptyView);

        // Create a new {@link ArrayAdapter} of earthquakes
        customArrayAdapter = new CustomArrayAdapter(this, R.layout.earthquake_activity, quackList);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(customArrayAdapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquackes currentQuacke = (Earthquackes) customArrayAdapter.getItem(position);
                Uri earthquackeUri = Uri.parse(currentQuacke.getmUrl());
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, earthquackeUri);
                startActivity(urlIntent);
            }
        });

    }

    @Override
    public Loader<List<Earthquackes>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG,"creating loader");
        return new AsyncTaskLoader(this,USGS_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<Earthquackes>> loader, List<Earthquackes> data) {
       customArrayAdapter.clear();
       if (data!= null && !data.isEmpty()){
        customArrayAdapter.addAll(data);
        emptyView.setText(R.string.no_records);
           progressBar.setVisibility(View.GONE);
           Log.v(LOG_TAG,"loading finished");
    }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquackes>> loader) {
        customArrayAdapter.clear();
        Log.v(LOG_TAG,"Loader reset");
    }

//    private class EarthquackAsyncTask extends AsyncTask<String, Void, List<Earthquackes>> {
//
//        @Override
//        protected List<Earthquackes> doInBackground(String... urls) {
//            if (urls.length < 1 | urls[0] == null) {
//                return null;
//            }
//
//            return QueryUtils.fetchEarthquakeData(urls[0]);
//        }
//
//        @Override
//        protected void onPostExecute(List<Earthquackes> earthquackes) {
//            customArrayAdapter.clear();
//            if (earthquackes != null && !earthquackes.isEmpty()) {
//                quackList.addAll(earthquackes);
//
//            }
//        }
//
//    }
}
