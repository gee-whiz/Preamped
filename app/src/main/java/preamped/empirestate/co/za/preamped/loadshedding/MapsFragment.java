package preamped.empirestate.co.za.preamped.loadshedding;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import preamped.empirestate.co.za.preamped.Adapters.LocationCursorAdapter;


import preamped.empirestate.co.za.preamped.R;
import preamped.empirestate.co.za.preamped.Schedules;

public class MapsFragment extends android.support.v4.app.Fragment  implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final String TAG = MapsFragment.TAG;

    AutoCompleteTextView cLocation,street,city,province;

    private ProgressDialog pDialog;
    Button notOk;
    private MyDBHandler db;
    String user;
    View surbView;
    GoogleMap mMap;
    ImageView autoRefresh;
    String st;
    String ct;
    String pv;
    String cl;
    private List<AffectedMarkers> affectedItems;
    String mmmname = null;
    String currentTime;
    String eeendtime;
    String surbNames;
    Button bpPlus;
    View MapLayout;
    private static final int GPS_ERRORDIALOG_REQUEST = 9001;
    private static final float DEFAULTZOOM = 17;
    private LocationRequest locationRequest;
    String locationProvider = LocationManager.GPS_PROVIDER;
    Location lastKnownLocation;
    static final String LOG = MapsFragment.class.getSimpleName();
    String location, answer, question;
    double lat, longi;
    ImageView preLogo;
    View uptown;
    String answers;
    Button loSave;
    Button suburbSave;
    String stages = "0";
    TextView endsM,currentS,loccsName;
    AutoCompleteTextView suburb;
    ImageView done;
    String endingTime;
    ImageButton myplus, btnDrop,btnup;
    TextView locCancel;
    View user_location, locUser,pointOf;
    View view, saveloc, myFav, lstLayout;
    PointOfInterest pointOfInterest;
    ListView listView;
    View favView,floor;
    ImageView power,maps;
    View tabLay;
    TextView coming, timeText,minus;
    ImageView preamped;
    ImageView car;
    View confirm;
    View preampedView;
    TextView markerName,plus,markerText;
    MapView mapView;
    TextView topEndTime,topStage,currentSatge,end;
    Context context;
    Button yesQuestionOne, noQuestion1;
    View questionOne;
    Random mRandom = new Random(1984);
    AutoCompleteTextView userLocation;
    AffectedMarkers affectedMarkers;
    SavedLocations savedLocations;
    String lats;
    String longs;

    List<Address> geocodeMatches = null;
    static final Locale loc = Locale.getDefault();
    List<Marker> markers = new ArrayList<Marker>();

    // TODO: Rename and change types and number of parameters
    public static MapsFragment newInstance(int Page) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putInt("maps", Page);
        fragment.setArguments(args);
        return fragment;
    }

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {

        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        GPSTracker gsTracker = new GPSTracker(context);
        if (gsTracker.isGPSEnabled == false) {
            gsTracker.showSettingsAlert();
        }
        db = new MyDBHandler(context);
        context = getActivity();


        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

         affectedMarkers = new AffectedMarkers();

        view = inflater.inflate(R.layout.activity_map, container, false);
        autoRefresh = (ImageView)view.findViewById(R.id.btnAuto);
        myFav = (View) view.findViewById(R.id.fLoc);
        floor = (View)view.findViewById(R.id.fLoooc);
        myplus = (ImageButton)view.findViewById(R.id.btnPlus);
         preamped = (ImageView)view.findViewById(R.id.btnPreamped);
        mapView = (MapView) view.findViewById(R.id.mapView);
        favView = (View) view.findViewById(R.id.favlay);
        btnDrop = (ImageButton) view.findViewById(R.id.btndown);
        power = (ImageView)view.findViewById(R.id.btnPower);
        maps = (ImageView)view.findViewById(R.id.btnMap);
        pDialog = new ProgressDialog(context);
        btnup = (ImageButton)view.findViewById(R.id.btnup);
        userLocation = (AutoCompleteTextView)view.findViewById(R.id.edtuserLocation);
        listView = (ListView) view.findViewById(R.id.lstLocation);
        markerName = (TextView)view.findViewById(R.id.mName);
        markerText = (TextView)view.findViewById(R.id.mText);
        uptown = (View)view.findViewById(R.id.uptownmap);
        pointOf = (View)view.findViewById(R.id.pointloc);
        locUser = (View)view.findViewById(R.id.loclays);
        tabLay = (View)view.findViewById(R.id.tbW);
        bpPlus = (Button)view.findViewById(R.id.bPlus);
        user_location = (View) view.findViewById(R.id.locSaveLay);
        car = (ImageView)view.findViewById(R.id.imgcar);
        mapView.onCreate(savedInstanceState);
        topEndTime = (TextView)view.findViewById(R.id.txtTimeTop);
        end = (TextView)view.findViewById(R.id.txtEnds);
         surbView = (View)view.findViewById(R.id.suburbLay);
        topStage = (TextView)view.findViewById(R.id.txtStage);
        MapLayout = (View)view.findViewById(R.id.mapLayout);
        endsM = (TextView)view.findViewById(R.id.txtCTend);
        loccsName = (TextView)view.findViewById(R.id.txtClocation);
        currentS = (TextView)view.findViewById(R.id.txtCstage);
        mapView.onResume();
        preamped.setEnabled(true);
        maps.setEnabled(true);
        maps.setBackgroundColor(getResources().getColor(R.color.transluscent_green));
        maps.setImageDrawable(getResources().getDrawable(R.drawable.bitmaps));

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.getStackTrace();
        }
        mMap = mapView.getMap();
        if (isOnline()){
        if (servicesOK()) {
            context.getApplicationContext();
            setmMap();
            if (initMap()) {
                context.getApplicationContext();
                if (mMap == null) {
                    Toast.makeText(context, "Map not Available", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    System.exit(1);
                }
                Calendar calendar = Calendar.getInstance();

                final int hr = calendar.get(Calendar.HOUR);
                int minutes = calendar.get(Calendar.MINUTE);
                int sec = calendar.get(Calendar.SECOND);

                currentTime = Integer.toString(hr) + ":" + Integer.toString(minutes) + ":" + Integer.toString(sec);
                setmMap();
            }
            else
               ShowNetworkDialog();
                mMap.getUiSettings().setAllGesturesEnabled(false);
                power.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getAffectedAreas();
                        preampedView = (View)view.findViewById(R.id.preLay);
                        if (!user_location.isShown()) {
                            if (!locUser.isShown()) {
                                questionOne = (View) view.findViewById(R.id.Qlays);
                                maps.setBackgroundColor(getResources().getColor(R.color.white));
                                maps.setImageDrawable(getResources().getDrawable(R.drawable.map_i));
                                if (stages.equalsIgnoreCase("0")) {
                                    topStage.setText("NO LOAD SHEDDING");
                                } else {

                                    topStage.setText("CURRENT STAGE: " + stages);

                                }
                                if (!favView.isShown()) {
                                    if (questionOne.isShown()) {
                                        topStage.setVisibility(View.VISIBLE);
                                        btnDrop.setVisibility(View.VISIBLE);
                                        final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
                                        anIn.setDuration(300);
                                        questionOne.startAnimation(anIn);
                                        questionOne.setVisibility(View.GONE);
                                        maps.setEnabled(false);

                                        maps.setBackgroundColor(getResources().getColor(R.color.transluscent_green));
                                        maps.setImageDrawable(getResources().getDrawable(R.drawable.bitmaps));
                                        mMap.getUiSettings().setAllGesturesEnabled(true);
                                        power.setBackgroundColor(getResources().getColor(R.color.white));
                                        power.setImageDrawable(getResources().getDrawable(R.drawable.power_green));
                                    }
                                    else  {
                                        topStage.setVisibility(View.GONE);
                                        btnDrop.setVisibility(View.GONE);
                                        power.setBackgroundColor(getResources().getColor(R.color.transluscent_green));
                                        power.setImageDrawable(getResources().getDrawable(R.drawable.power_green));
                                        preamped.setBackgroundColor(getResources().getColor(R.color.white));
                                        preamped.setImageDrawable(getResources().getDrawable(R.drawable.pre_green));
                                        preampedView.setVisibility(View.GONE);

                                        askQuestion();
                                        mMap.getUiSettings().setAllGesturesEnabled(false);
                                        maps.setEnabled(true);
                                    }


                                }
                            }
                        }
                    }
                });
                maps.setEnabled(false);
            if(isOnline()) {
               //askQuestion();
 autoRefresh.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         postToEskom(currentTime);
         if (stages.equalsIgnoreCase("0")) {
             topStage.setText("NO LOAD SHEDDING");
         } else {

             topStage.setText("CURRENT STAGE: " + stages);

         }
         Location local = new Location(location);
         local = mMap.getMyLocation();
         if (local != null) {
             setmMap();
             LatLng pnt = new LatLng(local.getLatitude(), local.getLongitude());
             mMap.getCameraPosition();

             mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pnt, 15.0f));
         }
         topStage.setVisibility(View.VISIBLE);
       Util.flashSeveralTimes(topStage,200,5,null);
         Util.animateRotationY(autoRefresh,200);


     }
 });
                //preamped.setEnabled(false);
                topStage.setVisibility(View.VISIBLE);

                getAffectedAreas();

                postToEskom(currentTime);
            }
            else
               ShowNetworkDialog();


            preamped.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          preampedView = (View)view.findViewById(R.id.preLay);
          questionOne = (View) view.findViewById(R.id.Qlays);
          if(!preampedView.isShown()) {
              coming = (TextView)view.findViewById(R.id.txtComming);
              preLogo = (ImageView)view.findViewById(R.id.immgComing);
              final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
              anIn.setDuration(300);
              topStage.setVisibility(View.GONE);
              btnDrop.setVisibility(View.GONE);
              coming.setVisibility(View.VISIBLE);
              coming.startAnimation(anIn);
              preampedView.setVisibility(View.VISIBLE);
              preampedView.startAnimation(anIn);
              preamped.setBackgroundColor(getResources().getColor(R.color.transluscent_green));
              preamped.setImageDrawable(getResources().getDrawable(R.drawable.pre_white));
              final Animation an = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_top);
              an.setDuration(300);
              if(questionOne.isShown()) {
                  questionOne.setVisibility(View.GONE);
              }
              maps.setEnabled(true);
              power.setBackgroundColor(getResources().getColor(R.color.white));
              power.setImageDrawable(getResources().getDrawable(R.drawable.power_green));
              maps.setBackgroundColor(getResources().getColor(R.color.white));
              maps.setImageDrawable(getResources().getDrawable(R.drawable.map_i));
              mMap.getUiSettings().setAllGesturesEnabled(false);
              if (stages.equalsIgnoreCase("0")) {
                  topStage.setText("NO LOAD SHEDDING");
              } else {

                  topStage.setText("CURRENT STAGE: " + stages);

              }
          }

          else  if(preamped.isShown()){
              topStage.setVisibility(View.VISIBLE);
              btnDrop.setVisibility(View.VISIBLE);
              final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
              anIn.setDuration(300);
              maps.setEnabled(false);
              maps.setBackgroundColor(getResources().getColor(R.color.transluscent_green));
              maps.setImageDrawable(getResources().getDrawable(R.drawable.bitmaps));
              preampedView.setVisibility(View.GONE);
              preampedView.startAnimation(anIn); preamped.setBackgroundColor(getResources().getColor(R.color.white));
              preamped.setImageDrawable(getResources().getDrawable(R.drawable.pre_green));
              mMap.getUiSettings().setAllGesturesEnabled(true);

          }

      }
  });

            maps.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Location local = new Location(location);
        local = mMap.getMyLocation();
        final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
        anIn.setDuration(300);
        questionOne.startAnimation(anIn);
        maps.setBackgroundColor(getResources().getColor(R.color.transluscent_green));
        maps.setImageDrawable(getResources().getDrawable(R.drawable.bitmaps));
        maps.setEnabled(false);
        topStage.setVisibility(View.VISIBLE);
        btnDrop.setVisibility(View.VISIBLE);
        if (questionOne.isShown())  {
            questionOne.setVisibility(View.GONE);
            mMap.getUiSettings().setAllGesturesEnabled(true);
            power.setBackgroundColor(getResources().getColor(R.color.white));
            power.setImageDrawable(getResources().getDrawable(R.drawable.power_green));

            mMap.getUiSettings().setAllGesturesEnabled(true);

            if (local != null) {
                LatLng pnt = new LatLng(local.getLatitude(), local.getLongitude());
                mMap.getCameraPosition();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pnt, 15.0f));
            }
        }


         else if(preamped.isShown()){
            mMap.getUiSettings().setAllGesturesEnabled(true);

            preampedView.setVisibility(View.GONE);
            preampedView.startAnimation(anIn);
            preamped.setBackgroundColor(getResources().getColor(R.color.white));
            preamped.setImageDrawable(getResources().getDrawable(R.drawable.pre_green));
            mMap.getUiSettings().setAllGesturesEnabled(true);

            if (local != null) {
                LatLng pnt = new LatLng(local.getLatitude(), local.getLongitude());
                mMap.getCameraPosition();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pnt, 15.0f));
            }
                 }
            else {
           if (local != null) {
               LatLng pnt = new LatLng(local.getLatitude(), local.getLongitude());
               mMap.getCameraPosition();
               mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pnt, 15.0f));
           }
       }
    }
});

btnup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       postToEskom(currentTime);
        if (!locUser.isShown()) {
            if (stages.equalsIgnoreCase("0")) {
                topStage.setText("NO LOAD SHEDDING");
            } else {

                topStage.setText("CURRENT STAGE: " + stages);

            }
          preamped.setEnabled(true);
            power.setEnabled(true);
            btnup.setVisibility(View.GONE);
            final Animation ann = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_top);
            ann.setDuration(300);
            mMap.getUiSettings().setAllGesturesEnabled(true);
            favView.startAnimation(ann);
            final Animation ag = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
            ann.setDuration(200);
            preampedView = (View)view.findViewById(R.id.preLay);
            btnDrop.setVisibility(View.VISIBLE);
            btnDrop.startAnimation(ag);
            favView.setVisibility(View.GONE);


        }
    }
});
            if(isOnline()) {
                postToEskom(currentTime);
            }
            else
               ShowNetworkDialog();
               // Toast.makeText(context, "Current time "+ currentTime,Toast.LENGTH_LONG).show();
                btnDrop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setList();
                        preamped.setEnabled(false);
                          power.setEnabled(false);

                           topStage.setVisibility(View.VISIBLE);
                        if (!user_location.isShown()) {
                            if(!confirm.isShown()) {
                                if (!locUser.isShown()) {
                                    final Animation ans = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_top);
                                    ans.setDuration(300);
                                    btnup.startAnimation(ans);
                                    btnup.setVisibility(View.VISIBLE);
                                    pointOf = (View) getView().findViewById(R.id.pointloc);
                                    btnDrop.setVisibility(View.GONE);

                                    final Animation an = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
                                    an.setDuration(200);


                                    if (pointOf.isShown() == true) {
                                        final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
                                        anIn.setDuration(200);
                                        pointOf.startAnimation(anIn);

                                        pointOf.setVisibility(View.GONE);
                                    }
                                    if (favView.isShown()) {

                                        final Animation ann = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_top);
                                        ann.setDuration(200);
                                        mMap.getUiSettings().setAllGesturesEnabled(true);
                                        favView.startAnimation(ann);

                                        favView.setVisibility(View.GONE);
                                    } else {
                                        final Animation ann = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_top);
                                        ann.setDuration(200);

                                        mMap.getUiSettings().setAllGesturesEnabled(false);
                                        favView.startAnimation(ann);
                                        favView.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                    }
                });



                myplus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       showSaveAlert();

                    }
                });


                bpPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showSaveAlert();


                    }

                });

                GPSTracker gpsTracker = new GPSTracker(context);
                if (gpsTracker.canGetLocation()) {
                    LatLng ll = new LatLng(gpsTracker.latitude, gpsTracker.longitude);
                    CameraUpdate myLocation = CameraUpdateFactory.newLatLngZoom(ll, 15);
                    mMap.animateCamera(myLocation);
                }
            }
        }
        return view;
    }

    public boolean servicesOK() {
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, (Activity) context, GPS_ERRORDIALOG_REQUEST);
            dialog.show();
        } else {

        }
        return false;
    }

    public  void showSaveAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View promptView = inflater.inflate(R.layout.new_location_dialog, null);
        builder.setView(promptView);
        MyDBHandler db = new MyDBHandler(context);

        String[] suburbNames = db.getAllSuburbs();
        street = (AutoCompleteTextView) promptView.findViewById(R.id.edtStreetaddress);
        city = (AutoCompleteTextView) promptView.findViewById(R.id.edtCity);
        province = (AutoCompleteTextView) promptView.findViewById(R.id.edtProvince);
        cLocation = (AutoCompleteTextView) promptView.findViewById(R.id.edtNewSaves);
        final ProgressBar progressBar = (ProgressBar)promptView.findViewById(R.id.progressBar);
        String[] cities = getResources().getStringArray(R.array.list_ofcities);
        String[] locNames = getResources().getStringArray(R.array.list_ofLocations);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, suburbNames);
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, locNames);
        ArrayAdapter<String> adapterss = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, cities);
        cLocation.setAdapter(adapters);
        province.setAdapter(adapterss);
       city.setAdapter(adapter);

        city.setContentDescription("Save Current Location");
        cLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(city.getWindowToken(), 0);

            }
        });

        province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(province.getWindowToken(), 0);

            }
        });


        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
        builder.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
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
              String  cls = cLocation.getText().toString();
                List<Address> geocodeMatches = null;

                if (!cLocation.getText().toString().isEmpty()) {
                    final MyDBHandler dbHandler = new MyDBHandler(context);
                    final List<Users> userses = dbHandler.getUserDetails();
                    for (Users g : userses) {
                        user = g.userID.toString();
                    }
                    Location local;
                    local = mMap.getMyLocation();
                    lat = local.getLatitude();
                    longi = local.getLongitude();
                    String latitude = Double.toString(lat);
                    String longitude = Double.toString(longi);
                    preamped.setEnabled(true);
                   registerUserLocation(user,latitude,longitude,cLocation.getText().toString(),"1");
                    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pDialog.setMessage("Saving....");
                    SavedLocations savedLocations = new SavedLocations(cLocation.getText().toString(), 0, lat, longi);
                    dbHandler.addLocations(savedLocations);
                    setmMap();
                    pDialog.show();
                    dialog.dismiss();
                    final Animation ann = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_top);
                    favView.startAnimation(ann);
                    final Animation ag = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
                    ann.setDuration(200);
                    btnDrop.setVisibility(View.VISIBLE);
                    btnDrop.startAnimation(ag);
                    favView.setVisibility(View.GONE);
                    btnup.setVisibility(View.GONE);
                    preamped.setEnabled(true);
                    setmMap();
                } else {
                    if (street.getText().toString().isEmpty()) {
                        street.setError("Please enter valid address");
                        return;
                    }

                    if (city.getText().toString().isEmpty()) {
                        city.setError("Please enter your suburb");
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

                        lats = String.valueOf(geocodeMatches.get(0).getLatitude());
                        longs = String.valueOf(geocodeMatches.get(0).getLongitude());
                        Double latidut = Double.valueOf(lats);
                        Double longitud = Double.valueOf(longs);
                        SavedLocations savedLocations = new SavedLocations(street.getText().toString(), 0, latidut,longitud);
                        dbHandler.addLocations(savedLocations);
                        Suburbs suburbs = dbHandler.getGroupID(city.getText().toString());
                        String  groupId;
                      try{
                         groupId = suburbs.groupId.toString();
                        Log.e(LOG,groupId.toString());
                        SendArea(user,lats,longs,street.getText().toString(),groupId);
                        registerUserLocations(user,lats,longs,street.getText().toString(),"1");
                        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pDialog.setMessage("Saving....");
                        dialog.dismiss();
                        final Animation ann = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_top);
                        favView.startAnimation(ann);
                        final Animation ag = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
                        ann.setDuration(200);

                        btnDrop.setVisibility(View.VISIBLE);
                        btnDrop.startAnimation(ag);
                        favView.setVisibility(View.GONE);
                        btnup.setVisibility(View.GONE);
                        preamped.setEnabled(true);
                        setmMap();


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
            }
        });

dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialog.dismiss();
    }
});


    }
    public  void showSaveAlertA() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View promptView = inflater.inflate(R.layout.new_location_dialog_a, null);
        builder.setView(promptView);

        cLocation = (AutoCompleteTextView) promptView.findViewById(R.id.edtNewSaves);

        String[] locNames = getResources().getStringArray(R.array.list_ofLocations);

        ArrayAdapter<String> adapters = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, locNames);
        cLocation.setAdapter(adapters);
        cLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(cLocation.getWindowToken(), 0);
            }
        });


// Add the buttons

        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  cls = cLocation.getText().toString();
             if(cLocation.getText().toString().isEmpty())
             {
                 cLocation.setError("Can not be blank");
             }

                if (!cLocation.getText().toString().isEmpty()) {
                    final MyDBHandler dbHandler = new MyDBHandler(context);
                    final List<Users> userses = dbHandler.getUserDetails();
                    for (Users g : userses) {
                        user = g.userID.toString();
                    }

                    Location local;
                    local = mMap.getMyLocation();
                    lat = local.getLatitude();
                    longi = local.getLongitude();
                    String latitude = Double.toString(lat);
                    String longitude = Double.toString(longi);
                    preamped.setEnabled(true);
                    registerUserLocation(user,latitude,longitude,cLocation.getText().toString(),"1");
                    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pDialog.setMessage("Saving....");
                    SavedLocations savedLocations = new SavedLocations(cLocation.getText().toString(), 0, lat, longi);
                    dbHandler.addLocations(savedLocations);
                    setmMap();
                    pDialog.show();
                    dialog.dismiss();
                }
            }
        });

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                preamped.setEnabled(true);
            }
        });


    }


    public void AddSuburb(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View promptView = inflater.inflate(R.layout.location_popup, null);
        builder.setView(promptView);
        MyDBHandler db = new MyDBHandler(context);

        String[] suburbNames = db.getAllSuburbs();

        city = (AutoCompleteTextView) promptView.findViewById(R.id.edtNewSuburb);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, suburbNames);
        city.setAdapter(adapter);

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cts = city.getText().toString();
                final MyDBHandler dbHandler = new MyDBHandler(context);
                final List<Users> userses = dbHandler.getUserDetails();
                power.setEnabled(true);
                for (Users g : userses) {
                    user = g.userID.toString();
                }
                Location local;
                local = mMap.getMyLocation();
                lat = local.getLatitude();
                longi = local.getLongitude();
                String latitude = Double.toString(lat);
                if (city.getText().toString().isEmpty()) {
                    city.setError("Please Enter Suburb");
                    return;
                }
                String longitude = Double.toString(longi);
                Suburbs suburbs = dbHandler.getGroupID(city.getText().toString());
                String groupId;
                try {

                    groupId = suburbs.groupId.toString();
                    if (groupId != null) {
                        Log.e(LOG, groupId.toString());
                        SendArea(user, latitude, longitude, city.getText().toString(), groupId);
                        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pDialog.setMessage("Saving....");
                        pDialog.show();
                        dialog.dismiss();
                        thankYouMessage();
                    }


                } catch (Exception e) {

                    SendArea(user, latitude, longitude, city.getText().toString(), "00");
                    thankYouMessage();
                    pDialog.show();
                    dialog.cancel();
                }
            }


        });

dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        power.setEnabled(true);
        dialog.dismiss();
    }
});


    }






    private boolean initMap() {
        if (mMap == null) {
            GoogleMap mapFrag =
                    MapFragment.newInstance().getMap();
            mMap = mapFrag;
        }
        return (mMap != null);
    }


    private void getAnswer() {
        final MyDBHandler dbHandler = new MyDBHandler(context);
        final List<Users> userses = dbHandler.getUserDetails();
        for (Users g : userses) {
            user = g.userID.toString();
        }

        Location local;
        local = mMap.getMyLocation();
        if (local != null) {
            lat = local.getLatitude();
            longi = local.getLongitude();
            String latitude = Double.toString(lat);
            String longitude = Double.toString(longi);
            if (!latitude.isEmpty()) {
                registerAnswer(user, latitude, longitude);
            }
        }
    }

    int count;
    static final int MAX_FLASHES = 30;

    private void setmMap() {

        mMap.getUiSettings().isZoomControlsEnabled();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        getPointOfInterest();
        final MyDBHandler dbHandler = new MyDBHandler(context);
        final List<PointOfInterest> pointOfInterest = dbHandler.getAllPointLocations();
        if (pointOfInterest != null) {
            for (PointOfInterest p : pointOfInterest) {
                LatLng pnt = new LatLng((p.latitude), (p.longitude));
                IconGenerator iconFactory = new IconGenerator(context);
                iconFactory.setStyle(IconGenerator.STYLE_GREEN);
                Marker options = mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(p.locationName)))
                        .title(p.locationName)
                        .snippet(p.markerText)
                        .anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV())
                        .position(new LatLng((p.latitude), (p.longitude))));


                options.setTitle(p.locationName);

                options.getSnippet();

                options.getTitle();
            }

        }

        final List<SavedLocations> savedLocations = dbHandler.getAllLocations();
        if (savedLocations != null) {
            for (SavedLocations s : savedLocations) {
                LatLng pnt = new LatLng(s.latitude, s.longitude);
                IconGenerator iconFactory = new IconGenerator(context);
                iconFactory.setStyle(IconGenerator.STYLE_GREEN);
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_plus))
                        .title(s.locName)
                        .position(new LatLng(s.latitude, s.longitude)));
                mMap.getMaxZoomLevel();
                mMap.setMyLocationEnabled(true);
                mMap.getCameraPosition();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pnt, 15.0f));
               marker.setTitle(s.locName);
                marker.getSnippet();
                marker.getTitle();
                mMap.getUiSettings().setAllGesturesEnabled(true);
            }
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    mMap.setMyLocationEnabled(true);
                    mMap.setBuildingsEnabled(true);
                    mMap.setIndoorEnabled(true);
                    pointOf = (View)getView().findViewById(R.id.pointloc);
                    if(pointOf.isShown() == true){
                        final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
                        anIn.setDuration(200);
                    pointOf.startAnimation(anIn);
                    pointOf.setVisibility(View.GONE);}
                    Location local = new Location(location);
                    local = mMap.getMyLocation();
                    if (local != null) {
                        LatLng pnt = new LatLng(local.getLatitude(), local.getLongitude());
                        MarkerOptions options = new MarkerOptions()
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                .title(location)
                                .snippet(location)
                                .position(new LatLng(local.getLatitude(), local.getLongitude()));
                        mMap.setMyLocationEnabled(true);
                        Toast.makeText(context, "Tap the marker", Toast.LENGTH_LONG).show();
                        mMap.getMyLocation();
                        mMap.addMarker(options);
                        mMap.getCameraPosition();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pnt, 15.0f));
                        Log.w(LOG, "George has clicked the map");
                    }
                }
            });
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override

                public boolean onMarkerClick(final Marker marker) {

                    if (!favView.isShown()) {
                    final LatLng latLng = marker.getPosition();
                    Location loc = new Location(location);
                    loc.setLatitude(latLng.latitude);
                    loc.setLongitude(latLng.longitude);
                    LatLng ll = new LatLng(loc.getLatitude(), loc.getLongitude());
                    CameraUpdate myLocation = CameraUpdateFactory.newLatLngZoom(ll, 15);
                    mMap.animateCamera(myLocation);
                    loSave = (Button) getView().findViewById(R.id.btnsSave);
                    locCancel = (TextView) getView().findViewById(R.id.btncCancel);
                    loc.getAccuracy();
                    loc.getTime();
                    user_location = (View) getView().findViewById(R.id.locSaveLay);

                    mMap.getCameraPosition();
                    mMap.getMyLocation();
                    user_location.setVisibility(View.GONE);

                    getLocations();
                    locUser = (View) getView().findViewById(R.id.loclays);
                    Double laa = loc.getLatitude();
                    Double loo = loc.getLongitude();
                        String lola = String.valueOf(loo);
                    String lat = String.valueOf(laa);
                    Log.w(LOG, "George has clicked the marker");

                    View x = (View) getView().findViewById(R.id.btnClose);
                    locUser = (View) getView().findViewById(R.id.loclays);

                    if (marker.getTitle() == null) {
                        pointOf.setVisibility(View.GONE);
                        preamped.setEnabled(false);
                        locUser.setVisibility(View.GONE);
                        getLocations();
                       showSaveAlertA();
                    }
                    if ((marker.getTitle() != null) && (marker.getSnippet() != null)) {

                        Intent  intent = new Intent(context, Schedules.class);

                        intent.putExtra(marker.getTitle(),"mname");
                        intent.putExtra(lola,"long");
                        intent.putExtra(lat,"lat");
                        startActivity(intent);
                     /*
                        pointOf = (View) getView().findViewById(R.id.pointloc);
                        markerName.setText(marker.getTitle());
                        markerText.setText(marker.getSnippet());
                        locUser.setVisibility(View.GONE);

                        currentS.setText(stages);
                        loccsName.setText(marker.getTitle());
                        currentS.setText(stages);
                        final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.grow_fade_in_from_bottom);
                        anIn.setDuration(200);
                        locUser.startAnimation(anIn);
                        locUser.setVisibility(View.VISIBLE);

                        loccsName.setText(marker.getTitle());
                        x.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Animation ann = AnimationUtils.loadAnimation(context, R.anim.push_down_out);
                                anIn.setDuration(200);
                                locUser.startAnimation(ann);
                                locUser.setVisibility(View.GONE);
                                mMap.getUiSettings().setAllGesturesEnabled(true);
                            }
                        });
                        floor.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMap.getUiSettings().setAllGesturesEnabled(true);
                                final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
                                final Animation ann = AnimationUtils.loadAnimation(context, R.anim.push_down_out);
                                anIn.setDuration(200);
                                locUser.startAnimation(ann);
                                locUser.setVisibility(View.GONE);
                            }
                        });
                        */
                    }


                    if ((marker.getTitle() != null) && (marker.getSnippet() == null)) {

                        Intent  intent = new Intent(context, Schedules.class);

                        intent.putExtra("mname",marker.getTitle());
                        intent.putExtra("long",lola);
                        intent.putExtra("lat",lat);
                        startActivity(intent);

                  /*
                       pointOf.setVisibility(View.GONE);
                        mMap.getUiSettings().setAllGesturesEnabled(false);


                        getAffectedAreas();

                        final MyDBHandler dbHandler = new MyDBHandler(context);
                        final List<AffectedMarkers> affectedMarkersList = dbHandler.getAffectedMarkers();
                        mmmname = "";
                        for (AffectedMarkers g : affectedMarkersList) {
                            mmmname = g.markerName.toString();
                            eeendtime = g.endTime.toString();


                        }

                        if (stages.equalsIgnoreCase("0")) {

                            currentS.setText("NO LOAD SHEDDING");
                            loccsName.setText(marker.getTitle());


                        } else {

                            if (stages.equalsIgnoreCase("1")) {
                                 if(eeendtime.equalsIgnoreCase("01"))
                                 {
                                     endsM.setText("N/A");
                                 }
                                else {
                                     endsM.setText(eeendtime);
                                 }
                                currentS.setText(stages);
                                loccsName.setText(marker.getTitle());
                            }
                            if (stages.equalsIgnoreCase("2")) {

                                if(eeendtime.equalsIgnoreCase("01"))
                                {
                                    endsM.setText("N/A");
                                }
                                else {
                                    endsM.setText(eeendtime);
                                }
                                currentS.setText(stages);
                                loccsName.setText(marker.getTitle());
                            }

                            if (stages.equalsIgnoreCase("3")) {

                                if(eeendtime.equalsIgnoreCase("01"))
                                {
                                    endsM.setText("N/A");
                                }
                                else {
                                    endsM.setText(eeendtime);
                                }
                                currentS.setText(stages);
                                loccsName.setText(marker.getTitle());
                            }
                        }

                        final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.grow_fade_in_from_bottom);
                        anIn.setDuration(200);
                        locUser.startAnimation(anIn);
                        locUser.setVisibility(View.VISIBLE);
                        preamped.setEnabled(false);
                        x.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Animation ann = AnimationUtils.loadAnimation(context, R.anim.push_down_out);
                                anIn.setDuration(200);
                                locUser.startAnimation(ann);
                                locUser.setVisibility(View.GONE);
                                mMap.getUiSettings().setAllGesturesEnabled(true);
                                preamped.setEnabled(true);
                            }
                        });
                        floor.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMap.getUiSettings().setAllGesturesEnabled(true);
                                final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
                                final Animation ann = AnimationUtils.loadAnimation(context, R.anim.push_down_out);
                                anIn.setDuration(200);
                                locUser.startAnimation(ann);
                                locUser.setVisibility(View.GONE);
                                preamped.setEnabled(true);
                            }
                        });

                        */

                    }


                    locCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
                            anIn.setDuration(200);
                            user_location.startAnimation(anIn);
                            user_location = (View) getView().findViewById(R.id.locSaveLay);
                            user_location.setVisibility(View.GONE);
                        }
                    });
                }

                    if (stages.equalsIgnoreCase("0")) {
                        topStage.setText("NO LOAD SHEDDING");
                    } else {

                        topStage.setText("CURRENT STAGE: " + stages);

                    }
                        return true;
                    }


            });


        }

    }



    public void askQuestion() {
        topStage.setVisibility(View.GONE);
        btnDrop.setVisibility(View.GONE);
        questionOne = (View) view.findViewById(R.id.Qlays);
        final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
        anIn.setDuration(300);
        preamped.setEnabled(true);
        questionOne.setVisibility(View.VISIBLE);
        questionOne.startAnimation(anIn);
        yesQuestionOne = (Button) view.findViewById(R.id.btnYes);
        noQuestion1 = (Button) view.findViewById(R.id.btnQNo);
        power.setBackgroundColor(getResources().getColor(R.color.transluscent_green));
        power.setImageDrawable(getResources().getDrawable(R.drawable.powers));
        yesQuestionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topStage.setVisibility(View.VISIBLE);
                btnDrop.setVisibility(View.VISIBLE);
                answers = "yes";
                power.setBackgroundColor(getResources().getColor(R.color.white));
                power.setImageDrawable(getResources().getDrawable(R.drawable.power_green));
                getAffectedAreas();
                thankYouMessage();

                mMap.getUiSettings().setAllGesturesEnabled(true);
                Toast.makeText(context, "Tap your location to start", Toast.LENGTH_LONG).show();
                final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
                anIn.setDuration(200);
                maps.setEnabled(false);
                maps.setBackgroundColor(getResources().getColor(R.color.transluscent_green));
                maps.setImageDrawable(getResources().getDrawable(R.drawable.bitmaps));
                questionOne.startAnimation(anIn);
                questionOne.setVisibility(View.GONE);
                if (stages.equalsIgnoreCase("0")) {
                    topStage.setText("NO LOAD SHEDDING");
                }

                else{


                    topStage.setText("CURRENT STAGE: "+stages);

                }
            }
        });

        noQuestion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer();
                power.setBackgroundColor(getResources().getColor(R.color.white));
                power.setImageDrawable(getResources().getDrawable(R.drawable.power_green));
                getAffectedAreas();
                thankYouMessage();
                topStage.setVisibility(View.VISIBLE);
                btnDrop.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Tap your location to start", Toast.LENGTH_LONG).show();
                final Animation anIn = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
                anIn.setDuration(200);
                mMap.getUiSettings().setAllGesturesEnabled(true);
                questionOne.startAnimation(anIn);
                questionOne.setVisibility(View.GONE);
                maps.setEnabled(false);
                maps.setBackgroundColor(getResources().getColor(R.color.transluscent_green));
                maps.setImageDrawable(getResources().getDrawable(R.drawable.bitmaps));
                if (stages.equalsIgnoreCase("0")) {
                    topStage.setText("NO LOAD SHEDDING");
                }

                else{


                    topStage.setText("CURRENT STAGE: "+stages);

                }
            }
        });
    }

    public void getLocations() {
        MyDBHandler dbHandler = new MyDBHandler(context);
        List<SavedLocations> savedLocations = dbHandler.getAllLocations();
        for (SavedLocations sl : savedLocations) {
            String logo = sl.locName;
            Double lati = sl.latitude;
            Double longg = sl.longitude;
            Log.d("your locations ", logo);
            Log.d("Latitude ", lati.toString());
            Log.d("Longitude ", longg.toString());

        }
    }
    private void setList() {
        confirm = (View) view.findViewById(R.id.conFirmdeletelay);
        final Button cyes = (Button) view.findViewById(R.id.btncYes);
        final Button cno = (Button) view.findViewById(R.id.btnCno);
        final MyDBHandler handler = new MyDBHandler(context);
        final SQLiteDatabase db = handler.getWritableDatabase();
        final Cursor todoCursor = db.rawQuery("SELECT * FROM mlocations" , null);

        LocationCursorAdapter locationCursorAdapter = new LocationCursorAdapter(context, todoCursor);
        listView.setAdapter(locationCursorAdapter);

listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
   getAffectedAreas();
        postToEskom(stages);
        AffectedMarkers affectedMarkers1 = new AffectedMarkers();

        Stage stage = new Stage();
        stage.getStage();
        LinearLayout item = (LinearLayout) view;
        TextView nameView = (TextView) item.findViewById(R.id.txtLoca);

        final String name = nameView.getText().toString();
        final long deleteLocID = id;
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(100);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View promptView = inflater.inflate(R.layout.delete_alert, null);
        builder.setView(promptView);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.deleteLocation(id);
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setMessage("Deleting...");
                pDialog.show();
                preamped.setEnabled(true);
                final MyDBHandler dbHandler = new MyDBHandler(context);
                final List<Users> userses = dbHandler.getUserDetails();
                for (Users g : userses) {
                    user = g.userID.toString();
                }


                Location local;
                local = mMap.getMyLocation();
                if (local != null) {
                    lat = local.getLatitude();
                    longi = local.getLongitude();
                    String latitude = Double.toString(lat);
                    String longitude = Double.toString(longi);
                    if (!latitude.isEmpty()) {
                        deleteUserLocation(user, latitude, longitude, name);
                    }
                }
                todoCursor.close();
                locUser = (View) getView().findViewById(R.id.loclays);
                final Animation ann = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_top);
                ann.setDuration(200);
                favView.startAnimation(ann);
                final Animation ag = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
                ann.setDuration(200);
                btnDrop.setVisibility(View.VISIBLE);
                btnDrop.startAnimation(ag);
                favView.setVisibility(View.GONE);
                btnup.setVisibility(View.GONE);
                preamped.setEnabled(true);
                mMap.getUiSettings().setAllGesturesEnabled(true);
                mMap.clear();
                setmMap();
            }
        });
        builder.setNegativeButton("NO",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();



        return true;
    }
});

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final MyDBHandler dbHandler = new MyDBHandler(context);
                final List<SavedLocations> savedLocations = dbHandler.getAllLocations();
                View x = (View) getView().findViewById(R.id.btnClose);
                locUser = (View) getView().findViewById(R.id.loclays);
                final Animation ann = AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_top);
                ann.setDuration(200);
                favView.startAnimation(ann);
                final Animation ag = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
                ann.setDuration(200);
                btnDrop.setVisibility(View.VISIBLE);
                btnDrop.startAnimation(ag);
                favView.setVisibility(View.GONE);
                btnup.setVisibility(View.GONE);
                preamped.setEnabled(true);
                power.setEnabled(true);
                LinearLayout item = (LinearLayout) view;
                TextView nameView = (TextView) item.findViewById(R.id.txtLoca);
                final String name = nameView.getText().toString();
              //  Toast.makeText(context,"You have clicked "+ name,Toast.LENGTH_LONG).show();

                if (savedLocations != null) {
                   SavedLocations savedLocations1 = dbHandler.getSingleLocation(name);


                        LatLng pnt = new LatLng(savedLocations1.latitude, savedLocations1.longitude);

                        mMap.getMaxZoomLevel();
                        mMap.setMyLocationEnabled(true);
                        mMap.getCameraPosition();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pnt, 15.0f));
                        mMap.getUiSettings().setAllGesturesEnabled(true);

                }
            }

        });
    }

    private void thankYouMessage() {
        Toast.makeText(context, "Thank you.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(100000);
//        LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastKnownLocation = location;
    }
    public void registerUserLocations(final String user_id, final String userLat, final String userLong, final String markerName, final String keepUpdated) {
        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST,
                AppConfig.URL_ANSWER, new Response.Listener<String>() {
            String surbNames;
            @Override
            public void onResponse(String response) {
                Log.d(LOG, "User location Register Response: " + response.toString());
                try {

                    notOk = (Button) getView().findViewById(R.id.btnNotOk);
                    String answer;
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String res = jsonObject.getString("response");
                    String yes = "01";

                    Log.d(LOG, "User Location Register Response: " + res);
                    Log.i("tagconvertstr", "[" + jsonArray + "]");

                    if (res.equals(yes)) {

                       //AddSuburb();
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
    public void registerUserLocation(final String user_id, final String userLat, final String userLong, final String markerName, final String keepUpdated) {
        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST,
                AppConfig.URL_ANSWER, new Response.Listener<String>() {
            String surbNames;
            @Override
            public void onResponse(String response) {
                Log.d(LOG, "User location Register Response: " + response.toString());
                try {

                    notOk = (Button) getView().findViewById(R.id.btnNotOk);
                    String answer;
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String res = jsonObject.getString("response");
                    String yes = "01";

                    Log.d(LOG, "User Location Register Response: " + res);
                    Log.i("tagconvertstr", "[" + jsonArray + "]");

                    if (res.equals(yes)) {

                        AddSuburb();
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

    public void deleteUserLocation(final String user_id, final String userLat, final String userLong, final String markerName) {
        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST,
                AppConfig.URL_DELETE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(LOG, "User location deleted Response: " + response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String res = jsonObject.getString("response");
                    Log.d(LOG, "User Location Register Response: " + res);
                    Log.i("tagconvertstr", "[" + jsonArray + "]");
                    String success = "00";
                    if (res.equals(success)) {
                        pDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG, "Error: deleting " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("function", "markerDel");
                params.put("userID", user_id);
                params.put("latitude", userLat);
                params.put("longitude", userLong);
                params.put("markerName", markerName);
                Log.e(LOG, "Values for the deleted user location " + params);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    public void registerAnswer(final String user_id, final String userLat, final String userLong) {
        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST,
                AppConfig.URL_ANSWER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(LOG, "User Answer Register Response: " + response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String res = jsonObject.getString("response");
                    String no = "01";
                    Log.d(LOG, "User Answer Register Response: " + res);
                    Log.i("tagconvertstr", "[" + jsonArray + "]");
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
                params.put("function", "outageIns");
                params.put("userID", user_id);
                params.put("latitude", userLat);
                params.put("longitude", userLong);
                Log.e(LOG, "Values for the user " + params);
                return params;
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    //get Point of Interest locations
    public void getPointOfInterest() {
        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST,
                AppConfig.URL_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Point of Interest locations " + response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String lat = jsonObject.getString("lat");
                        String longi = jsonObject.getString("long");
                        String mname = jsonObject.getString("markerName");
                        String mtext = jsonObject.getString("markerText");
                        String logoi = jsonObject.getString("logoLocation");

                        pointOfInterest = new PointOfInterest(Double.parseDouble(lat), Double.parseDouble(longi), mname, mtext,logoi);
                        db.addPointLocations(pointOfInterest);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG, "Error: Getting Point of locations " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // matching the function url
                Map<String, String> params = new HashMap<String, String>();
                params.put("function", "pointOfInterest");
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

    //post to Eskom
    public void postToEskom(final String startTime) {
        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST,
                AppConfig.URL_ESKOM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(LOG, "Response From Eskom: " + response.toString());
                //JSONArray jsonArray = new JSONArray(response.toString());
                // JSONObject jsonObject = jsonArray.getJSONObject(0);
                //  int rest = Integer.getInteger(response) - 1;
                try {
                    if (response != null) {
                        String res = Integer.toString(Integer.parseInt(response) - 1);
                        Log.d(LOG, "Response From Eskom: " + res);
                        stages = res;
                        Stage stage = new Stage();
                        stage.setStage(stages);
                        Log.i("tagconvertstr", "[" + res + "]");
                        Log.d(LOG, "Current Stage from Eskom  " + res);
                    }

                } catch (Exception e) {
                    Log.d(LOG, "App Should crash here");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG, "Error: getting stage from Eskom " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();


                params.put(startTime,startTime);
                Log.e(LOG, "Values for the Eskom " + params);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    //get Affected locations



    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }
    private void ShowNetworkDialog(){
        AlertDialog.Builder d = new AlertDialog.Builder(context);
        d.setTitle(getString(R.string.no_network))
                .setMessage(getString(R.string.internet_message))
                .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        MapsFragment.this.startActivity(intent);
                    }
                })

                .show();
    }

    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private static final String URL_IMAGE = "" ;
        private final View myContentsView;
        ContextThemeWrapper cw = new ContextThemeWrapper (
               context, R.style.Transparent);
        LayoutInflater inflater = (LayoutInflater) cw
                .getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        MarkerInfoWindowAdapter(Marker options) {
            myContentsView = getActivity().getLayoutInflater().inflate(R.layout.info_window, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {

            return null;
        }

        @Override
        public View getInfoContents(final Marker marker) {

            return null;
        }
    }



    public void getAffectedAreas() {
        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST,
                AppConfig.URL_REQUEST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Affected locations " + response.toString());
                String endTime = "";
                String locationId = "";
                try {
                    affectedMarkers = new AffectedMarkers();
                    JSONArray jsonArray = new JSONArray(response);

                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    endTime = jsonObject.getString("endTime");
                    String mname = jsonObject.getString("markerName");

                    affectedMarkers.markerName = mname;
                    affectedMarkers.endTime = endTime;
                    db.addAffected(affectedMarkers);


                    endingTime = endTime;
                } catch (JSONException e) {
                }
                endingTime = endTime;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG, "Error: Getting Affected locations " + error.getMessage());
            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                // matching the function url
                final MyDBHandler dbHandler = new MyDBHandler(context);
                final List<Users> userses = dbHandler.getUserDetails();
                for (Users g : userses) {
                    user = g.userID.toString();
                }


                Map<String, String> params = new HashMap<String, String>();
                params.put("function", "affectedUserMarkers");
                params.put("userID",user);
                params.put("stage",stages);
                Log.e(LOG, "PHP function used " + params);
                return params;
            }

        };


        strReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }


}