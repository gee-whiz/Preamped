package preamped.empirestate.co.za.preamped.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import preamped.empirestate.co.za.preamped.Login_social;
import preamped.empirestate.co.za.preamped.R;
import preamped.empirestate.co.za.preamped.Schedules;
import preamped.empirestate.co.za.preamped.loadshedding.AppConfig;
import preamped.empirestate.co.za.preamped.loadshedding.AppController;
import preamped.empirestate.co.za.preamped.loadshedding.MyDBHandler;
import preamped.empirestate.co.za.preamped.loadshedding.Suburbs;


public class SplashScreen extends Activity {
    private static final String LOG = "" ;

    private static final String TAG = "Attempting to register device";
    Context ctx;
    GcmRegistrationService gcmRegistrationService;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    String SENDER_ID = "970358825737";
    GoogleCloudMessaging gcm;
    String regid="";
    private MyDBHandler db;

    private ProgressDialog pDialog;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_splash_screen);
        pDialog = new ProgressDialog(SplashScreen.this);
        ctx = getApplicationContext();
        db = new MyDBHandler(getApplicationContext());

        startService(new Intent(this, GcmRegistrationService.class));



   new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        SplashScreen.this.startActivity(new Intent(SplashScreen.this, Login_social.class));
          overridePendingTransition(R.anim.from, R.anim.to);
        SplashScreen.this.finish();
      }
    }
    , 3000);
  }



    }

