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

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private RecyclerView mRecyclerView;
    private MainRecyclerAdapter mMainRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mRecyclerView = findViewById(R.id.main_recycler);

        // Create a fake list of earthquake locations.
        List<TonicQuake> quakes = new ArrayList<TonicQuake>(10);
        String[] places = new String[]{"San Francisco", "London", "Tokyo", "Mexico City"};
        String[] dates = new String[]{"Feb, 2021", "March, 2020", "April, 2010", "January, 2013"};
        String[] magnitudes = new String[]{"3.2", "4.5", "9.0", "1.4"};
        for(int i = 0; i < places.length; i++){
            TonicQuake quake = new TonicQuake();
            quake.setPlace(places[i]);
            quake.setDate(dates[i]);
            quake.setMagnitude(magnitudes[i]);

            quakes.add(quake);
        }


        mMainRecyclerAdapter = new MainRecyclerAdapter(quakes, this);
        mRecyclerView.setAdapter(mMainRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Find a reference to the {@link ListView} in the layout
//        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
//        earthquakeListView.setAdapter(adapter);
    }
}
