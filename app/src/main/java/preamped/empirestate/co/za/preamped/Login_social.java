package preamped.empirestate.co.za.preamped;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import preamped.empirestate.co.za.preamped.Activities.RandomPics;
import preamped.empirestate.co.za.preamped.Activities.TimerUtil;
import preamped.empirestate.co.za.preamped.loadshedding.AffectedMarkers;
import preamped.empirestate.co.za.preamped.loadshedding.AppConfig;
import preamped.empirestate.co.za.preamped.loadshedding.AppController;
import preamped.empirestate.co.za.preamped.loadshedding.GcmDevice;
import preamped.empirestate.co.za.preamped.loadshedding.MyDBHandler;
import preamped.empirestate.co.za.preamped.loadshedding.PreampedPager;


import preamped.empirestate.co.za.preamped.loadshedding.Suburbs;
import preamped.empirestate.co.za.preamped.loadshedding.Users;



public class Login_social extends FragmentActivity implements

   GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String LOG = "";

    LoginButton loginButton;
    ImageView imageView;
    GcmDevice gcmDevice;
    String gcm;
    private MyDBHandler db;
    private ProgressDialog pDialog;
    TextView txtPower;
    Button register, regExit, login;
    private static String STOREUSER = "storeUser.txt";
    ProgressBar progressBar;
    Button gregister, glogin;
    View logloadView, regView, LogView;
    EditText firstname, lastname, phone, password;
    EditText email;
    String fName, fSurname, fEmail;
    EditText femail, fPassword;
    private TextView mTextDetails;
    Context ctx;
    String emails;
    View regMain;
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplication());
        mCallbackManager = CallbackManager.Factory.create();
        flashImages();
        pDialog = new ProgressDialog(Login_social.this);
        db = new MyDBHandler(getApplicationContext());
//        Profile prof = Profile.getCurrentProfile();
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };
        setContentView(R.layout.loginload_hading);
        ctx = getApplicationContext();
        setupTokenTracker();
        setupProfileTracker();
        checkPlayServices();
        readUser();
        setFields();
        swapViews();


        regExit = (Button) findViewById(R.id.reg_cancel);
        regExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramAnonymousView) {
                System.exit(1);
            }
        });
        if (checkFacebookAppInstalled("com.facebook.katana")) {//Displays button if facebook app exists.
            setupLoginButton();
        }
        mTokenTracker.startTracking();
        //  mProfileTracker.startTracking();
        if (isOnline()) {
            glogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = femail.getText().toString();
                    String password = fPassword.getText().toString();
                    Boolean validEmail = validateEmail(email);
                    // Check for empty data in the form
                    if (email.isEmpty() || !validEmail) {
                        femail.setError("Please Enter a Valid Email Address");
                        return;
                    }

                    if (password.isEmpty()) {
                        fPassword.setError("Please Enter Valid Password");
                        return;
                    } else {
                        // login user
                        pDialog.setMessage("Welcome...please wait..");
                        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pDialog.show();
                        fetchAllSuburbsGroupIDs();
                        checkLogin(email, password);
                        storeUser(password);

                    }
                }
            });
        }

        if (isOnline()) {
            gregister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean validEmail = validateEmail(email.getText().toString());
                    final MyDBHandler dbHandler = new MyDBHandler(getApplicationContext());
                    final List<GcmDevice> gcmDevices = dbHandler.getGcm();
                    for (GcmDevice g : gcmDevices) {
                        gcm = g.deviceID.toString();
                    }

                    String name = firstname.getText().toString();
                    String emaill = email.getText().toString();
                    String passwords = password.getText().toString();
                    String phn = phone.getText().toString();
                    String surname = lastname.getText().toString();
                    if (firstname.getText().toString().isEmpty()) {
                        firstname.setError("Please Enter FIRST NAME");
                        return;
                    }

                    if (email.getText().toString().isEmpty() ) {
                        email.setError("Please Enter a Valid Email Address");
                        return;
                    }


                    if (password.getText().toString().isEmpty()) {
                        password.setError("Please Enter Valid Password");
                        return;
                    } else {
                        setFields();
                        fetchAllSuburbsGroupIDs();
                        pDialog.setMessage("Welcome " + firstname.getText().toString()+" Please wait,Loading...");
                        pDialog.setProgressStyle(ProgressDialog.BUTTON_POSITIVE);
                        pDialog.show();
                        registerUser(name, surname, emaill, phn, passwords, gcm);
                        storeUser(name);
                    }

                }
            });
        } else {
            ShowNetworkDialog();
        }
    }

    private boolean validateEmail(String email) {

        String email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern p = Pattern.compile(email_pattern);
        Matcher m = p.matcher(email);

        if (m.matches()) {
            return true;
        }

        return false;
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        if ((currentAccessToken != null)) {
            Intent intent = new Intent(Login_social.this, PreampedPager.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean checkPlayServices() {
        Log.w(LOG, "checking GooglePlayServices .................");
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(ctx);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                //         PLAY_SERVICES_RESOLUTION_REQUEST).show();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.gms")));
                return false;
            } else {
                Log.i(LOG, "This device is not supported.");
                throw new UnsupportedOperationException("GooglePlayServicesUtil resultCode: " + resultCode);
            }
        }
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }

    private boolean checkFacebookAppInstalled(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void setupTokenTracker() {
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("VIVZ", "" + currentAccessToken);
                if ((currentAccessToken != null)) {
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    Intent intent = new Intent(Login_social.this, PreampedPager.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    private void setupProfileTracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.d("gee", "" + currentProfile);


                fName = currentProfile.getFirstName().toString();
                fSurname = currentProfile.getLastName().toString();

                final MyDBHandler dbHandler = new MyDBHandler(getApplicationContext());
                final List<GcmDevice> gcmDevices = dbHandler.getGcm();
                for (GcmDevice g : gcmDevices) {
                    gcm = g.deviceID.toString();
                }

                registerUser(fName, fSurname, "", "", "", gcm);
                storeUser(fName);
                if (oldProfile != null)
                    if (currentProfile != null) {
                        Toast.makeText(getApplicationContext(), "Welcome " + fName.toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login_social.this, PreampedPager.class);
                        startActivity(intent);

                        finish();
                    }


            }
        };
    }


    private void setupLoginButton() {
        LoginButton mButtonLogin = (LoginButton) findViewById(R.id.login_button);
        mButtonLogin.setReadPermissions("public_profile");
        mButtonLogin.setReadPermissions("email");
        mButtonLogin.registerCallback(mCallbackManager, mFacebookCallback);
        mButtonLogin.setLoginBehavior(LoginBehavior.SSO_ONLY);
       mButtonLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

       Toast.makeText(ctx,"Please wait..",Toast.LENGTH_LONG).show();
        fetchAllSuburbsGroupIDs();
    }
});

    }

    public void setFields() {
        regMain = (View) findViewById(R.id.Logs);
        firstname = (EditText) findViewById(R.id.edtFirstName);
        lastname = (EditText) findViewById(R.id.edtLastName);
        email = (EditText) findViewById(R.id.edtEmail);
        phone = (EditText) findViewById(R.id.edtPhone);
        password = (EditText) findViewById(R.id.edtPassword);
        register = (Button) findViewById(R.id.reg_btn);
        login = (Button) findViewById(R.id.log_btn);
        femail = (EditText) findViewById(R.id.email_address);
        fPassword = (EditText) findViewById(R.id.password);
        logloadView = (View) findViewById(R.id.loadlogLay);
        LogView = (View) findViewById(R.id.logLayout);
        gregister = (Button) findViewById(R.id.btnRegister);
        glogin = (Button) findViewById(R.id.logs_btn);
        regView = (View) findViewById(R.id.regLayout);
        regView.isInEditMode();
        imageView = (ImageView) findViewById(R.id.imgSplash);
        txtPower = (TextView) findViewById(R.id.txtPower);
        logloadView.isInEditMode();
        LogView.isInEditMode();

    }

    public void swapViews() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regMain.setVisibility(View.GONE);
                logloadView.setVisibility(View.GONE);
                regView.setVisibility(View.VISIBLE);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regMain.setVisibility(View.GONE);
                logloadView.setVisibility(View.GONE);
                regView.setVisibility(View.GONE);
                LogView.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onPause() {
        super.onPause();

        AppEventsLogger.activateApp(this);
        // AppEventsLogger.deactivateApp(this);
    }

    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d("VIVZ", "onSuccess");
            AccessToken accessToken = loginResult.getAccessToken();
        }

        @Override
        public void onCancel() {
            Log.d("George ", "onCancel");
        }

        @Override
        public void onError(FacebookException e) {
            Log.d("VIVZ", "onError " + e);
        }
    };

    public void storeUser(String name) {
        String filename = "myfile";
        File file = new File(ctx.getFilesDir(), filename);
            try {
                String string = (name);
                FileOutputStream outputStream;
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(string.getBytes());
                outputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public void readUser() {

        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    openFileInput("myfile")));
            String inputString;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString + "\n");
            }
            if (isOnline()) {
                if (stringBuffer.toString() != null) {
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    Intent intent = new Intent(Login_social.this, PreampedPager.class);
                    startActivity(intent);
                    finish();
                } else {
                    ShowNetworkDialog();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_social, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (regMain.isShown()) {
            System.exit(1);
        } else {
            regMain.setVisibility(View.VISIBLE);
            logloadView.setVisibility(View.GONE);
            regView.setVisibility(View.GONE);
            LogView.setVisibility(View.GONE);
        }

    }

    private void flashImages() {
        TimerUtil.startFlashTime(new TimerUtil.TimerFlashListener() {
            @Override
            public void onStartFlash() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RandomPics.getImage(ctx, imageView, txtPower, new RandomPics.RandomPicsListener() {
                            @Override
                            public void onCompleteFlash() {

                            }
                        });
                    }
                });
            }
        });
    }


    public void fetchAllSuburbsGroupIDs() {
        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST,
                AppConfig.URL_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(LOG, "All SuburbsGroupIDs  " + response.toString());

                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String sname = jsonObject.getString("suburbName");
                        String gname = jsonObject.getString("groupName");
                        String gid = jsonObject.getString("groupID");
                        Suburbs suburbs = new Suburbs(sname, gname, gid);
                        db.addSuburbs(suburbs);
                    }

                } catch (JSONException e) {
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG, "Error: Getting All Suburbs " + error.getMessage());
            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("function", "fetchAllSuburbsGroupIDs");
                Log.e(LOG, "PHP function used " + params);
                return params;
            }

        };


        strReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void registerUser(final String name, final String lastName, final String email, final String phone,
                              final String password, final String gcm) {

        final StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(LOG, "Register Response: " + response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    int uid = jsonObject.getInt("userID");
                    Log.i("tagconvertstr", "[" + jsonArray + "]");
                    Users users = new Users(Integer.toString(uid));
                    db.addUser(users);
                    if (!response.isEmpty()) {
                        Runnable progressRunnable = new Runnable() {
                            @Override
                            public void run() {


                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                                Intent intent = new Intent(
                                        Login_social.this,
                                        PreampedPager.class);
                                startActivity(intent);
                                finish();
                                pDialog.cancel();
                            }
                        };
                        Handler pdCancelor = new Handler();
                        pdCancelor.postDelayed(progressRunnable,3000);
                    } else {

                        pDialog.setMessage("Registering....");
                        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pDialog.show();
                        Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    // String errorMsg = jsonArray.getString(Integer.parseInt("error_msg"));
                    Toast.makeText(getApplicationContext(),
                            "Error getting Posting", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("function", "userIns");
                params.put("firstName", name);
                params.put("lastName", lastName);
                params.put("email", email);
                params.put("phone", phone);
                params.put("password", password);
                params.put("GCMID", gcm);
                Log.e(LOG, "Values from the device " + params);
                return params;
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "userLogin";

        final String[] res = new String[1];

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(LOG, "Login Response: " + response.toString());

                try {

                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);


                    String res = jsonObject.getString("response");
                    String success = "00";
                    if (res.equals(success)) {
                        pDialog.hide();
                        int uid = jsonObject.getInt("userID");
                        Users users = new Users(Integer.toString(uid));
                        db.addUser(users);

                        // Launch main activity
                        Intent intent = new Intent(Login_social.this,
                                PreampedPager.class);
                        startActivity(intent);

                        finish();
                    } else {
                        pDialog.dismiss();

                        Toast.makeText(getApplicationContext(), "Email or Password Incorrect", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("function", "userLogin");
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void ShowNetworkDialog() {
        AlertDialog.Builder d = new AlertDialog.Builder(this);
        d.setTitle(getString(R.string.no_network))
                .setMessage(getString(R.string.internet_message))
                .setPositiveButton(getApplicationContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        Login_social.this.startActivity(intent);
                    }
                })

                .show();
    }

}
   /* class PhotoTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            Log.w(LOG, "");

}
*/