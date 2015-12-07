package preamped.empirestate.co.za.preamped.loadshedding;

import android.app.Application;
import android.content.Context;

/**
 * @author George Kapoya on 2015/03/04.
 */
public class MyApp extends Application{

    protected static MyApp myApp = null;
    public static String regId = "";

    @Override
    public void onCreate() {
        super.onCreate();
    }
    public  MyApp(){
        myApp = this;
    }
    public static Context getContext() {
        return myApp.getApplicationContext();
    }
}
