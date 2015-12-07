package preamped.empirestate.co.za.preamped;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonArray;
import com.melnykov.fab.ScrollDirectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import preamped.empirestate.co.za.preamped.Adapters.ScheduleAdpter;
import preamped.empirestate.co.za.preamped.loadshedding.AppConfig;
import preamped.empirestate.co.za.preamped.loadshedding.AppController;
import preamped.empirestate.co.za.preamped.loadshedding.MapsFragment;
import preamped.empirestate.co.za.preamped.loadshedding.MyDBHandler;
import preamped.empirestate.co.za.preamped.loadshedding.PreampedPager;
import preamped.empirestate.co.za.preamped.loadshedding.SavedLocations;
import preamped.empirestate.co.za.preamped.loadshedding.Scheduless;
import preamped.empirestate.co.za.preamped.loadshedding.Suburbs;
import preamped.empirestate.co.za.preamped.loadshedding.Users;
import preamped.empirestate.co.za.preamped.loadshedding.Util;


public class Schedules extends ActionBarActivity {
    private MyDBHandler db;
    private static final String LOG = "" ;
    private ProgressDialog pDialog;
    String user,latitude,longitde,marker;
    Context context;
    View sview,viewList;
    View noSch;
    ProgressBar pda;
    MapsFragment mapsFragment;
    TextView surb;
    ImageView auto,backs;
    Button clickck;
   private List<Scheduless> schedulessList = new ArrayList<Scheduless>();
    private ScheduleAdpter scheduleAdpter;
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedules);
        sview = (View)findViewById(R.id.ssl);
        pDialog = new ProgressDialog(Schedules.this);
        db = new MyDBHandler(getApplicationContext());
        context = getApplicationContext();
        viewList = (View)findViewById(R.id.viewlist);
        surb = (TextView)findViewById(R.id.txtSurbub);
        mapsFragment = new MapsFragment();
        pDialog.setMessage("Loading...");
        pda = (ProgressBar)findViewById(R.id.pD);
       pda.setVisibility(View.VISIBLE);

        noSch = (View)findViewById(R.id.noLay);
       auto = (ImageView)findViewById(R.id.btnAuto);
   backs = (ImageView)findViewById(R.id.btnback);
        scheduleAdpter = new ScheduleAdpter(this,schedulessList );
        final MyDBHandler dbHandler = new MyDBHandler(context);
        final List<Users> userses = dbHandler.getUserDetails();
        listView = (ListView) findViewById(R.id.list);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            longitde = extras.getString("long");
            latitude = extras.getString("lat");
            marker = extras.getString("mname");
        }
surb.setText(marker);
        for (Users g : userses) {
            user = g.userID.toString();
        }
        getSchedules(user, longitde, latitude, marker);

        listView.setAdapter(scheduleAdpter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                Animation ann = AnimationUtils.loadAnimation(context,R.anim.abc_fade_out);
                ann.setDuration(200);
                sview.startAnimation(ann);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        com.melnykov.fab.FloatingActionButton fab = (com.melnykov.fab.FloatingActionButton)findViewById(R.id.fab);

        fab.attachToListView(listView, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {

            }

            @Override
            public void onScrollUp() {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              showSaveAlert();
            }
        });

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.animateRotationY(auto, 200);
                getSchedules(user, longitde, latitude, marker);
                pda.setVisibility(View.VISIBLE);
            }
        });



        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation ann = AnimationUtils.loadAnimation(context,R.anim.slide_out_right);
                ann.setDuration(300);

                finish();
            }
        });

    }

    public  void showSaveAlert() {

        LayoutInflater inflater = LayoutInflater.from(Schedules.this);
        View promptView = inflater.inflate(R.layout.news_loca, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(Schedules.this);
        builder.setView(promptView);
        MyDBHandler db = new MyDBHandler(context);

        final String[] suburbNames = db.getAllSuburbs();
        final AutoCompleteTextView street = (AutoCompleteTextView) promptView.findViewById(R.id.edtStreetaddress);
        final AutoCompleteTextView city = (AutoCompleteTextView) promptView.findViewById(R.id.edtCity);
        final AutoCompleteTextView province = (AutoCompleteTextView) promptView.findViewById(R.id.edtProvince);

        final ProgressBar progressBar = (ProgressBar)promptView.findViewById(R.id.progressBar);
        String[] cities = getResources().getStringArray(R.array.list_ofcities);
        String[] locNames = getResources().getStringArray(R.array.list_ofLocations);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, suburbNames);
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locNames);
        ArrayAdapter<String> adapterss = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);

        province.setAdapter(adapterss);
        city.setAdapter(adapter);

        city.setContentDescription("Save Current Location");


        province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(province.getWindowToken(), 0);

            }
        });


        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(city.getWindowToken(), 0);
            }
        });

        // province.setAdapter(adapter);

// Add the buttons

        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cts = city.getText().toString();
                String str = street.getText().toString();
                String pvs = province.getText().toString();

                List<Address> geocodeMatches = null;


                if (street.getText().toString().isEmpty()) {
                    street.setError("Please enter valid address");
                    return;
                }

                if (city.getText().toString().isEmpty()) {
                    city.setError("Please Enter City");
                    return;
                }

                try {

                    geocodeMatches =
                            new Geocoder(context).getFromLocationName(
                                    str + "," + cts + "," + pvs, 1);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (geocodeMatches != null && geocodeMatches.size() > 0) {
                    final MyDBHandler dbHandler = new MyDBHandler(context);
                    final List<Users> userses = dbHandler.getUserDetails();

                    for (Users g : userses) {
                        user = g.userID.toString();
                    }

                    String lats = String.valueOf(geocodeMatches.get(0).getLatitude());
                    String longs = String.valueOf(geocodeMatches.get(0).getLongitude());
                    Double latidut = Double.valueOf(lats);
                    Double longitud = Double.valueOf(longs);
                    SavedLocations savedLocations = new SavedLocations(street.getText().toString(), 0, latidut, longitud);
                    dbHandler.addLocations(savedLocations);
                    Suburbs suburbs = dbHandler.getGroupID(city.getText().toString());
                    String groupId;
                 try{
                    groupId = suburbs.groupId.toString();
                    Log.e(LOG, groupId.toString());
                    SendArea(user, lats, longs, street.getText().toString(), groupId);
                    registerUserLocation(user, lats, longs, street.getText().toString(), "1");
                    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pDialog.setMessage("Saving....");
                    dialog.dismiss();

                      finish();


                 } catch (Exception e) {

                     SendArea(user, lats, longs, street.getText().toString(), "00");

                     pDialog.show();
                     dialog.cancel();
                 }
                } else {
                    dialog.dismiss();
                    Toast.makeText(context, "Sorry, the address could not be found.", Toast.LENGTH_LONG).show();

                }

            }

        });

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedules, menu);
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




    public void getSchedules(final String userID, final String longitude, final String latitude, final String markername) {
        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST,
                AppConfig.URL_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(LOG, "Marker schedules  " + response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONArray jsonArray = new JSONArray(response);
                        Scheduless scheduless = new Scheduless();


                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        scheduless.setStage(jsonObject.getString("stage"));
                        scheduless.setDate(jsonObject.getString("date"));
                        scheduless.setStart(jsonObject.getString("start"));
                        scheduless.setEnd(jsonObject.getString("end"));
                        scheduless.setGroup(jsonObject.getString("groupName"));
                        String res = jsonObject.getString("stage");
                        String error = "01";
                        if(res.equalsIgnoreCase(error)) {

                            pda.setVisibility(View.GONE);

                            noSch.setVisibility(View.VISIBLE);

                       }

                       else

                       {


                           Toast.makeText(context,"Success",Toast.LENGTH_LONG).show();
                           schedulessList.add(scheduless);
                           pda.setVisibility(View.GONE);
                           noSch.setVisibility(View.GONE);
                       }

                    } catch (JSONException e) {
                    }
                }
                scheduleAdpter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG, "Error: Getting schedules " + error.getMessage());
            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("function", "fetchScheduleForMarker");
                params.put("userID", userID);
                params.put("longitude",longitude);
                params.put("latitude", latitude);
                params.put("markerName",markername);
                Log.e(LOG, "PHP function used " + params);
                return params;
            }

        };


        strReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
    public void SendArea(final String user_id, final String userLat, final String userLong, final String markerName, final String groupID ) {
        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST,
                AppConfig.URL_ANSWER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(LOG, "Area Register Response: " + response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String res = jsonObject.getString("response");
                    String success = "00";
                    if(res.equalsIgnoreCase(success)) {
                        pDialog.dismiss();

                        Log.d(LOG, "User Area Register Response: " + res);
                        Log.i("tagconvertstr", "[" + jsonArray + "]");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG, "Registration Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("function", "markerInsManual");
                params.put("userID", user_id);
                params.put("latitude", userLat);
                params.put("longitude", userLong);
                params.put("markerName", markerName);
                params.put("groupID",groupID);
                params.put("keepUpdated","1");
                Log.e(LOG, "Values for the user Area or suburb " + params);
                return params;
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }


    public void registerUserLocation(final String user_id, final String userLat, final String userLong, final String markerName, final String keepUpdated) {
        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST,
                AppConfig.URL_ANSWER, new Response.Listener<String>() {
            String surbNames;
            @Override
            public void onResponse(String response) {
                Log.d(LOG, "User location Register Response: " + response.toString());
                try {


                    String answer;
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String res = jsonObject.getString("response");
                    String yes = "01";

                    Log.d(LOG, "User Location Register Response: " + res);
                    Log.i("tagconvertstr", "[" + jsonArray + "]");

                    if (res.equals(yes)) {


                        pDialog.dismiss();



                    }
                    else
                        pDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG, "Registration Error: " + error.getMessage());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("function", "markerIns");
                params.put("userID", user_id);
                params.put("latitude", userLat);
                params.put("longitude", userLong);
                params.put("markerName", markerName);
                params.put("keepUpdated", keepUpdated);

                Log.e(LOG, "Values for the user location " + params);
                return params;
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);

    }

}
