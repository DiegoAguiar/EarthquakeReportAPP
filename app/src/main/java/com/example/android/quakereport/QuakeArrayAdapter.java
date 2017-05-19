package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * Created by diego.almeida on 04/05/2017.
 */
public class QuakeArrayAdapter extends ArrayAdapter {

    private static final String LOCATION_SEPARATOR = " of ";
    private static final String NEAR_THE = "Near the";

    public QuakeArrayAdapter(Context context, List<Earthquake> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listEarthquakeView = convertView;
        if(listEarthquakeView == null){
            listEarthquakeView = LayoutInflater.from(getContext()).inflate(R.layout.list_iten_quake,parent,false);
        }
        final Earthquake mEvent = (Earthquake) getItem(position);

        TextView magTextView = (TextView) listEarthquakeView.findViewById(R.id.mag);
        Double mMag = mEvent.getMagitude();
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        magTextView.setText(decimalFormatter.format(mMag));

        TextView locationOffsetTextView = (TextView) listEarthquakeView.findViewById(R.id.location_offset);
        TextView locationPrimaryTextView = (TextView) listEarthquakeView.findViewById(R.id.location_primary);
        String locationComplete = mEvent.getLocation();
        if(locationComplete.contains(LOCATION_SEPARATOR)){
            String[] mLocation = locationComplete.split(LOCATION_SEPARATOR);
            locationOffsetTextView.setText(mLocation[0] + LOCATION_SEPARATOR);
            locationPrimaryTextView.setText(mLocation[1]);
        }else{
            locationOffsetTextView.setText(NEAR_THE);
            locationPrimaryTextView.setText(locationComplete);
        }


        TextView dateTextView = (TextView) listEarthquakeView.findViewById(R.id.date);
        Date mDate = mEvent.getEventDate();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        dateTextView.setText(dateFormatter.format(mDate));

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss a");
        TextView timeTextView = (TextView) listEarthquakeView.findViewById(R.id.time);
        timeTextView.setText(timeFormatter.format(mDate));

        GradientDrawable magCircle = (GradientDrawable) magTextView.getBackground();
        magCircle.setColor(ContextCompat.getColor(getContext(),mEvent.getIdMagColor()));

        listEarthquakeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Abrindo...",Toast.LENGTH_SHORT).show();
                Intent intent = null;
                Uri urlParser = Uri.parse(mEvent.getUrl());
                intent = new Intent(Intent.ACTION_VIEW,urlParser);
                Intent chooserIntent = Intent.createChooser(intent,"Abrir em: ");

                if(intent.resolveActivity(getContext().getPackageManager()) != null){
                    getContext().startActivity(chooserIntent);
                }
            }
        });

        return listEarthquakeView;
    }
}
