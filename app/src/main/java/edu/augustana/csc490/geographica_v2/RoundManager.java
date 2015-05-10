package edu.augustana.csc490.geographica_v2;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;
import java.util.Random;

/**
 * Created by Ethan on 5/9/2015.
 */
public class RoundManager {

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

        if(!hasCountryCode(new LatLng(latitude,longitude))){
            getRandomLocation();
        }
        return new LatLng(latitude,longitude);

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

        if(gameMode == 1){
            topTextView.setText("Round: " + roundNum);
        }else{
            topTextView.setText("Round: " + roundNum + " Player " + currentPlayer);
        }

    }

}
