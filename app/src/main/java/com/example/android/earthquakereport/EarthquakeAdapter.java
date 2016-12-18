package com.example.android.earthquakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Tapan on 11/17/2016.
 */

public class EarthquakeAdapter extends ArrayAdapter{

    public EarthquakeAdapter(Activity context,ArrayList<Earthquake> earthquakeList){
        super(context,0,earthquakeList);
    }

    public View getView(int position,View converView,ViewGroup parent){

        View listItemView = converView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_view,parent,false);
        }
        Earthquake currentEarthquake= (Earthquake) getItem(position);


        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag_text_view);
        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
        magTextView.setText(formattedMagnitude);


        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);


        String quakeLocation = currentEarthquake.getLocation();
        String locationOffset;
        String primaryLocation;

        if (quakeLocation.contains("of")) {
            String[] parts = quakeLocation.split("(?<=of)");
            locationOffset = parts[0];
            primaryLocation = parts[1];
        } else {
            locationOffset = "Near the";
            primaryLocation = quakeLocation;
        }
        TextView locationOffsetTextView = (TextView) listItemView.findViewById(R.id.location_offset_text_view);
        locationOffsetTextView.setText(locationOffset);

        TextView primaryLocationTextView = (TextView) listItemView.findViewById(R.id.primary_location_text_view);
        primaryLocationTextView.setText(primaryLocation);

        Date dateObject = new Date(currentEarthquake.getTimeInMilliSec());
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);

        String formattedDate = formatDate(dateObject);
        dateTextView.setText(formattedDate);

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_text_view);
        String formattedTime = formatTime(dateObject);
        timeTextView.setText(formattedTime);


        return listItemView;
    }
    private int getMagnitudeColor(Double magObject){
        int magnitudeColorResId;
        int magFloor = (int) Math.floor(magObject);
        switch (magFloor){
            case 0:
            case 1:
                magnitudeColorResId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResId);
    }
    public String formatMagnitude(double mMagnitude){
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(mMagnitude);
    }
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormatter.format(dateObject);
    }
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        return timeFormatter.format(dateObject);
    }

}
