package edu.augustana.csc490.geographica_v2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;
import java.util.Random;
import static edu.augustana.csc490.geographica_v2.PolyUtil.*;

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

        if(gameMode == 1){
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
