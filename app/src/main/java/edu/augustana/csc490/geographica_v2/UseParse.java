package edu.augustana.csc490.geographica_v2;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Owner on 5/10/2015.
 */
public class UseParse extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "dHP0UNu4XCweTe6Df8ISsna6B2tklT7p3R6WrhOr", "Ku4n4ZYUDsdZgzLfKSGZzRBPOlEml4Tb28SoDqPP");
    }
}
