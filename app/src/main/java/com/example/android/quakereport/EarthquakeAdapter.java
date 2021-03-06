package com.example.android.quakereport;

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
 * Created by vraun on 23-01-2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param earthquakes A List of earthquakes to display in a list
     */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquakes);
    }
    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        // Get the {@link Earthquake} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        // Find the TextView in the earthquake_list_iteme_list_item.xml layout with the ID magnitude .
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
        // Format the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
        // set this text on the TextView
        magnitudeView.setText(formattedMagnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);



        /**
         * Splitting Strings .
         */
        //GEt the complete Location String for current EarthQuake
        String Location  = currentEarthquake.getCity();
        //This location string contains the full string of the object.
        //which is ( "5km N of Cairo, Egypt") then we have to split the strings
        //so that we can save them in two different text views .
        String primaryLocation ;
        String offset_location;
        //Now , we have to check wheather the orignal string which is LOCATION contains ("of") of the text ,
        //if it contains ("of") , then we split the LOCATION string

        if(Location.contains("of")){
            //Split the string as an array of the strings
            String[] splittedStrings = Location.split("of");
            //Then the strings which contains (EX: 5km North of Cario Egypt ) will become (5 Km North) + of ,
            //then we store this into offset location .
            offset_location = splittedStrings[0] + "of";
            //Then primary location will be (Cario , Egypt)
            primaryLocation= splittedStrings[1];

        }else {
            // Otherwise, there is no " of " text in the originalLocation string.
            // Hence, set the default location offset to say "Near the".
            offset_location = getContext().getString(R.string.near_the);
            // The primary location will be the full location string "Pacific-Antarctic Ridge".
            primaryLocation = Location;
        }



        // Find the reference to the primaryLocationTextView
        TextView primaryLocationTextView = (TextView) listItemView.findViewById(R.id.primary_location);
        //set the value on those textViews
        primaryLocationTextView.setText(primaryLocation);

        // Find the reference to the offsetLocationTextView
        TextView offsetLocationTextView = (TextView) listItemView.findViewById(R.id.location_offset);
        //set the value on those textViews
        offsetLocationTextView.setText(offset_location);


        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentEarthquake.getTime());


        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude){
        //convert the (double magnitude into int)
        //convert that into int using method like: Math.floor() and then use the Switch on that int value
        switch ((int) Math.floor(magnitude)){
            case 0:
            case 1:
                magnitude =  ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2 :
                magnitude =  ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3 :
                magnitude =  ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4 :
                magnitude =  ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5 :
                magnitude =  ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6 :
                magnitude =  ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7 :
                magnitude =  ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8 :
                magnitude =  ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9 :
                magnitude =  ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;

        }
        return ContextCompat.getColor(getContext(), (int) Math.floor(magnitude));
    }
}
