package com.example.android.quakereport;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by diego.almeida on 11/05/2017.
 */
public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    private EarthquakeFilter filter;

    public EarthquakeLoader(Context context, EarthquakeFilter filter) {
        super(context);
        this.filter = filter;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.i("EarthquakeLoader","onStartLoading()");
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        // Create a fake list of earthquake locations.
        ArrayList<Earthquake> earthquakes = null;
        try {
            earthquakes = QueryUtils.extractEarthquakes(filter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("EarthquakeLoader","loadInBackground()");
        return earthquakes;

    }
}
