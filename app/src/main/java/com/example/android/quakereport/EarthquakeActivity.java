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

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.quakereport.utils.NetworkUtils;
import com.example.android.quakereport.utils.QuakeUtils;

import java.net.URL;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private RecyclerView mRecyclerView;
    private MainRecyclerAdapter mMainRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mRecyclerView = findViewById(R.id.main_recycler);

        mLayoutManager = new LinearLayoutManager(this);

        new QuakeAsynkTask().execute();


    }

    private class QuakeAsynkTask extends AsyncTask<URL, Void, List<TonicQuake>> {

        @Override
        protected List<TonicQuake> doInBackground(URL... urls) {
            List<TonicQuake> tonicQuakes = QuakeUtils.extractEarthquakes();
            return tonicQuakes;
        }


        @Override
        protected void onPostExecute(List<TonicQuake> tonicQuakes) {
            super.onPostExecute(tonicQuakes);
            updateUI(tonicQuakes);
        }

        private void updateUI(List<TonicQuake> tonicQuakes) {
            mMainRecyclerAdapter = new MainRecyclerAdapter(tonicQuakes, EarthquakeActivity.this);
            mRecyclerView.setAdapter(mMainRecyclerAdapter);
            mRecyclerView.setLayoutManager(mLayoutManager);
        }

    }
}
