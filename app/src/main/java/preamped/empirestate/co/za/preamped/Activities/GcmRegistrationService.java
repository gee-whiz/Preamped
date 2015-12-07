package preamped.empirestate.co.za.preamped.Activities;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import preamped.empirestate.co.za.preamped.Login_social;
import preamped.empirestate.co.za.preamped.loadshedding.GcmDevice;
import preamped.empirestate.co.za.preamped.loadshedding.MyDBHandler;

/**
 * Created by users em on 2015-04-22.
 */
public class GcmRegistrationService extends IntentService {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String DISPLAY_MESSAGE_ACTION = "Gcm Message";
    static final String TAG = "GCMDemo";
    private MyDBHandler db;
    String SENDER_ID = "970358825737";
    GoogleCloudMessaging gcm;
    String regid="";


    public GcmRegistrationService() {
        super("GcmRegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        new GcmRegistrationTask().execute();




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO do something useful
      //  Toast.makeText(getApplicationContext(), "gcm reg started",
               /// Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);

    }






    private class GcmRegistrationTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
  db = new MyDBHandler(getApplicationContext());
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
            }
            regid = getRegistrationId(getApplicationContext());
            if(regid.isEmpty()){
                try {
                    regid = gcm.register(SENDER_ID);
                    storeRegistrationId(getApplicationContext(),regid);
                    GcmDevice gcmDevice = new GcmDevice(regid);
                    db.addGcm(gcmDevice);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void params){
            super.onPostExecute(params);
            if(!regid.isEmpty()){

            }
        }
    }
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the registration ID in your app is up to you.
        return getSharedPreferences(Login_social.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }


    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        // Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();

    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            //  Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            // Log.i(TAG, "App version changed.");
            return "";
        }

       // displayMessage(context,regid);
        return registrationId;
    }




}