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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.quakereport.utils.QuakeUtils;

import java.util.List;

public class EarthquakeActivity extends AppCompatActivity
implements LoaderManager.LoaderCallbacks<List<TonicQuake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String TAG = EarthquakeActivity.class.getName();
    private RecyclerView mRecyclerView;
    private MainRecyclerAdapter mMainRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView emptyView;
    private ImageView earth;
    private ProgressBar progress;
    private boolean mIsConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mRecyclerView = findViewById(R.id.main_recycler);

        mLayoutManager = new LinearLayoutManager(this);

        emptyView = findViewById(R.id.empty_state);
        earth = findViewById(R.id.earth);
        progress = findViewById(R.id.progress_circular);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        mIsConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        if(mIsConnected) {
            getSupportLoaderManager().initLoader(0, null, this);
        } else {
            hideViews(earth, emptyView);
            Toast toast = Toast.makeText(EarthquakeActivity.this, R.string.no_internet, Toast.LENGTH_LONG);
            toast.show();
        }


    }

    private void updateUI(List<TonicQuake> tonicQuakes) {

            hideViews(earth, emptyView);
            showViews(mRecyclerView);
            mMainRecyclerAdapter = new MainRecyclerAdapter(tonicQuakes, EarthquakeActivity.this);
            mRecyclerView.setAdapter(mMainRecyclerAdapter);
            mRecyclerView.setLayoutManager(mLayoutManager);

    }

    private void hideViews(View... views){
        for(View view: views){
            view.setVisibility(View.GONE);
        }
    }

    private void showViews(View... views){
        for(View view: views){
            view.setVisibility(View.VISIBLE);
        }
    }


    @NonNull
    @Override
    public Loader<List<TonicQuake>> onCreateLoader(int id, @Nullable Bundle args) {
        showViews(progress);
        hideViews(emptyView, earth);

        return new QuakeDataLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<TonicQuake>> loader, List<TonicQuake> data) {
        hideViews(progress);
        if(data.size() > 0){
            updateUI(data);
        } else if(data != null){
            hideViews(mRecyclerView);
            showViews(earth, emptyView);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<TonicQuake>> loader) {

    }

    public static class QuakeDataLoader extends AsyncTaskLoader<List<TonicQuake>> {
        private List<TonicQuake> mQuakeData;

        public QuakeDataLoader(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if(mQuakeData == null){
                forceLoad();
            } else{
                deliverResult(mQuakeData);
            }
        }

        @Nullable
        @Override
        public List<TonicQuake> loadInBackground() {
            return QuakeUtils.extractEarthquakes();
        }

        @Override
        public void deliverResult(@Nullable List<TonicQuake> data) {
            mQuakeData = data;
            super.deliverResult(data);
        }
    }
}
