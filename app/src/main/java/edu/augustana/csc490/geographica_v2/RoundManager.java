package edu.augustana.csc490.geographica_v2;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;
import java.util.Random;
import static edu.augustana.csc490.geographica_v2.PolyUtil.*;

/**
 * Created by Ethan on 5/9/2015.
 * Updated by Keegan on 51/12/2015.
 */
public class RoundManager {
    LatLng challengeLocation;
    Context parentContext;

    public RoundManager(Context parentContext){
        this.parentContext = parentContext;
    }

    public LatLng getRandomLocation(){
        Random r = new Random();

        double latitude = (double) r.nextInt(90);

        if(r.nextInt(100) < 50){
            latitude *= -1;
        }

        //Eliminate antarctica

        while(latitude <-60){
            latitude = (double) r.nextInt(90);
            if(r.nextInt(100) <= 50){
                latitude *=-1;
            }
        }

        latitude += ((double) r.nextInt(9)) * .1;
        latitude += ((double) r.nextInt(9)) * .01;
        latitude += ((double) r.nextInt(9)) * .001;
        latitude += ((double) r.nextInt(9)) * .0001;
        latitude += ((double) r.nextInt(9)) * .00001;
        latitude += ((double) r.nextInt(9)) * .000001;

        double longitude = (double) r.nextInt(180);

        if(r.nextInt(100) < 50){
            longitude *= -1;
        }

        longitude += ((double) r.nextInt(9)) * .1;
        longitude += ((double) r.nextInt(9)) * .01;
        longitude += ((double) r.nextInt(9)) * .001;
        longitude += ((double) r.nextInt(9)) * .0001;
        longitude += ((double) r.nextInt(9)) * .00001;
        longitude += ((double) r.nextInt(9)) * .000001;

        Log.w("random Latitude:", Double.toString(latitude));
        Log.w("random Longitude:", Double.toString(longitude));

        return new LatLng(latitude,longitude);

    }

    public LatLng getLatLngSpecial(){

        LatLng location = getRandomLocation();

        GetPolygons getPolygons = new GetPolygons();

        List<LatLng> americasPoly = getPolygons.getAmericas();
        List<LatLng> afroEurAsiaPoly = getPolygons.getAfroEurAsia();
        List<LatLng> australiaPoly = getPolygons.getAustralia();
        List<LatLng> japanPoly = getPolygons.getJapan();

        while(!containsLocation(location,americasPoly,false) && !containsLocation(location,afroEurAsiaPoly,false) && !containsLocation(location,australiaPoly,false) && !containsLocation(location,japanPoly,false)){
            location = getRandomLocation();
        }
        return location;
    }
    public LatLng getLatLngChallenge(final int roundNum) {
        //Gets the challenge location for the given round from parse and returns it.
        Log.d("INLATLNGCHALLENGE","Entering the getLatLngChallenge method");

        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge_Locations");


        double latitude = 0;
        double longitude=0;
        try {
            latitude = (double) query.find().get(roundNum - 1).get("latitude");
            longitude = (double) query.find().get(roundNum - 1).get("longitude");
            Log.d("LOCATION", "Setting location to (" + latitude + "," + longitude + ")");
            challengeLocation = new LatLng(latitude, longitude);
            Log.d("NEWLOCATION", "Current location is (" + latitude + "," + longitude + ")");
        } catch (ParseException e) {
            Log.e("ParseException",e.toString());
        }







        /* Trying to use findInBackground at this point in the process doesn't give it enough time to finish
           before the game starts up.

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> locationList, ParseException e) {
                if (e == null) {
                    double latitude = (double) locationList.get(roundNum - 1).get("latitude");
                    double longitude = (double) locationList.get(roundNum - 1).get("longitude");
                    Log.d("LOCATION", "Setting location to (" + latitude + "," + longitude + ")");
                    challengeLocation = new LatLng(latitude, longitude);
                    Log.d("NEWLOCATION", "Current location is (" + latitude + "," + longitude + ")");

                } else {
                    Log.d("QUERY", "Error in query for Challenge_Locations: " + e.getMessage());
                    challengeLocation = new LatLng(0, 0);
                }
            }
        });
        */
        if(challengeLocation==null){
            Log.d("fail","ChallengeLocation was null?"+challengeLocation.toString());
            return new LatLng(0,0);
        }
        Log.d("success","ChallengeLocation wasn't null?"+challengeLocation.toString());
        return challengeLocation;
    }
    private boolean hasCountryCode(LatLng latLng){

        Geocoder geoCoder = new Geocoder(parentContext);
            try {
                List<Address> matches = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                String countryCode = bestMatch.getCountryCode();
                Log.w("CountryCode", countryCode);
                return true;
            }catch (Exception e){
                return false;
            }
    }

    public int calculateScore(Marker myMarker, LatLng latLngOfPanoID){

        LatLng guessPosition = myMarker.getPosition();

        Log.w("Guess", guessPosition.toString());
        Log.w("Actual", latLngOfPanoID.toString());



        //////////////////////// http://stackoverflow.com/questions/14394366/find-distance-between-two-points-on-map-using-google-map-api-v2

        float[] results = new float[1];
        Location.distanceBetween(guessPosition.latitude, guessPosition.longitude,
                latLngOfPanoID.latitude, latLngOfPanoID.longitude, results);

        /////////////////////// END http://stackoverflow.com/questions/14394366/find-distance-between-two-points-on-map-using-google-map-api-v2

        int newScore = Math.round(results[0]/1000);

        return newScore;

    }

    public void setTopTextView(TextView topTextView, int gameMode, int roundNum, int currentPlayer){

        final Typeface font = Typeface.createFromAsset(parentContext.getAssets(), "fonts/moon_light.otf");

        if(gameMode == 1 || gameMode==3){
            topTextView.setText("Round: " + roundNum);
        }else{
            topTextView.setText("Round: " + roundNum + "  Player: " + currentPlayer);
        }
        topTextView.setTypeface(font);
        topTextView.setTextSize(24);
        topTextView.setTextColor(Color.BLACK);
    }

    public void styleTextView(TextView textView, String text, int size){

        final Typeface font = Typeface.createFromAsset(parentContext.getAssets(), "fonts/moon_light.otf");
        textView.setText(text);
        textView.setTypeface(font);
        textView.setTextSize(size);
        textView.setTextColor(Color.BLACK);
    }

    public void styleTextView(TextView textView, String text, int size, int color){

        final Typeface font = Typeface.createFromAsset(parentContext.getAssets(), "fonts/moon_light.otf");
        textView.setText(text);
        textView.setTypeface(font);
        textView.setTextSize(size);
        textView.setTextColor(color);
    }

    public void styleButton(Button button, int size){

        final Typeface font = Typeface.createFromAsset(parentContext.getAssets(), "fonts/moon_light.otf");

        button.setTypeface(font);
        button.setTextSize(size);
        button.setTextColor(Color.BLACK);

    }

}
