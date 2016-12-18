package com.example.android.earthquakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EathquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private static final String USGS_EARTHQUAKE="http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=3&limit=500";
    private EarthquakeAdapter madapter;
    private TextView mEmptyStateTextView;
    private ProgressBar mLoadingSpinnerView;
    private static final int EARTHQUAKE_LOADER_ID= 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eathquake);


        mLoadingSpinnerView = (ProgressBar) findViewById(R.id.loading_spinner);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_state_Text_View);


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            mLoadingSpinnerView.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }


        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();
        ListView listView = (ListView) findViewById(R.id.list);
        madapter =new EarthquakeAdapter(this,earthquakes);
        listView.setAdapter(madapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = (Earthquake) madapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Set the empty view on to the screen if earthquake info list is empty.
        listView.setEmptyView(mEmptyStateTextView);
    }


    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        return new EarthquakeLoader(this,USGS_EARTHQUAKE);
    }


    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        madapter.clear();

        if (earthquakes != null && !earthquakes.isEmpty()) {
            madapter.addAll(earthquakes);
        }
        mEmptyStateTextView.setText(R.string.no_earthquakes);

        mLoadingSpinnerView.setVisibility(View.GONE);
    }


    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        madapter.clear();
    }
}
