package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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
import java.util.List;

/**
 * Created by sherif on 16/10/17.
 */

public class CustomArrayAdapter extends ArrayAdapter {

    Context context;
    private ArrayList objects;
    final static String LOCATION_SEPARATOR = " of ";
    public CustomArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);

    }





    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mArrayList = convertView;
        String primaryLocation;
        String locationOffset;


        if (mArrayList == null){
            mArrayList = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout,parent,false);
            //et the current position of the listView
            Earthquackes earthquackes = (Earthquackes) getItem(position);


            //Sets the views of the list Item
            TextView mag = (TextView) mArrayList.findViewById(R.id.magnitude);
            TextView locationOffsetView  = (TextView) mArrayList.findViewById(R.id.location_offset);
            TextView primaryLocationView = (TextView) mArrayList.findViewById(R.id.primary_location);
            TextView date = (TextView)mArrayList.findViewById(R.id.date);
            TextView time = (TextView) mArrayList.findViewById(R.id.time);
            //Storing the location in a string variable
            String originalLocation = earthquackes.getmLocation();

            //Check if the returned location has "of" word to separate offset form the orig location
            if (originalLocation.contains(LOCATION_SEPARATOR)){

                //creating an arraylist from the for the splitted parts
                String[] parts = originalLocation.split(LOCATION_SEPARATOR);

                /*Storing the offset value to the location offset variable in the first location
                 in the array
                  */
                locationOffset = parts[0] + LOCATION_SEPARATOR;

                /*
                storing the primary location to the second index of the array
                 */
                primaryLocation = parts[1];
                //if the original location doesn't contain "of"
            }else {
                //set a fixed text to the offset location
                locationOffset = getContext().getString(R.string.near_the);

                //then store the original location to the primary location variable
                primaryLocation = originalLocation;
            }



            //Creating a date object from the retrieved date in milliseconds
            Date dateObject = new Date(earthquackes.getmTimeInMilliseconds());

            //Stores the formatted date object in a string varible
            String formattedDate = formatDate(dateObject);

            //Store the formatted time object in a string variable
            String formattedTime = formatTime(dateObject);


            //Setting the magnitude text in its textview
            mag.setText(formatMagnitude(earthquackes.getmMag()));


            // Set the proper background color on the magnitude circle.
            // Fetch the background from the TextView, which is a GradientDrawable.
            GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

            // Get the appropriate background color based on the current earthquake magnitude
            int magnitudeColor = getMagnitudeColor(earthquackes.getmMag());

            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor);

            //setting the offset text to the offset textview
            locationOffsetView.setText(locationOffset);

            //setting the primary location text to the primary location textview
            primaryLocationView.setText(primaryLocation);

            //Setting the Date text to the date textView
            date.setText(formattedDate);

            //setting the time text to itstextView
            time.setText(formattedTime);


        }
return mArrayList;




    }

    private int getMagnitudeColor(double mMag) {
       int magnitudeColorResourceId;
       int magnitudeFloor = (int) Math.floor(mMag);
        switch (magnitudeFloor){
           case 0:
           case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
               magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;

       }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }


    //A method to format the time returning a String
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return timeFormat.format(dateObject);

    }
    //A method to format the date returning a String
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyy");
        return  dateFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }


}
