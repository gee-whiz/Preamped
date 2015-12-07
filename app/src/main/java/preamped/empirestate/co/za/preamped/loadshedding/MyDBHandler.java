package preamped.empirestate.co.za.preamped.loadshedding;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by George kapoya on 2015-04-15.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    static final String LOG = MapsFragment.class.getSimpleName();
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "mlocations.db";
    private static final String TABLE_LOCATIONS = "mlocations";
    public static final String TABLE_USERS = "musers";
    public static final String TABLE_GCM = "mgcm";
    public static final String TABLE_POINT = "point";
    public static final String TABLE_SUBURBS = "suburbs";
    public static final String TABLE_AFECTED = "affected";
    public static final  String USER_ID = "user_id";
    public static final  String GCM_ID = "gcm_id";
    public static final  String GROUP_NAME = "group_name";
    public static final String COLUMN_MARKER_NAME = "marker_name";
    public static final String  COLUMN_END_TIME = "end_time";
    public static final String COLUMN_ID = "_id";
    public  static  final  String LOCATION_ID = "loc_id";
    public static final String COLUMN_LOCATION = "locationName";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_USER = "user";
    private static final String TAG = "";
    public static final String COLUMN_MARKERTEST = "markertext";
    public static final String COLUMN_IMAGE = "imageuri";
    public static final String COLUMN_SUBURBS = "suburb";
    public static final String GROUP_ID = "group_id";

    private SQLiteDatabase sqliteDBInstance = null;

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOCATIONS_TABLE = "CREATE TABLE " +
                TABLE_LOCATIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_LOCATION
                + " TEXT," + COLUMN_LATITUDE + " TEXT, " + COLUMN_LONGITUDE + " TEXT" + ");";
        db.execSQL(CREATE_LOCATIONS_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE " +
                TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_USER
                + " TEXT);";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_GCM_TABLE = "CREATE TABLE " +
                TABLE_GCM + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + GCM_ID
                + " TEXT);";
        db.execSQL(CREATE_GCM_TABLE);

        String CREATE_POINT_TABLE = "CREATE TABLE " +
                TABLE_POINT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_LATITUDE
                + " TEXT," + COLUMN_LONGITUDE + " TEXT, " + COLUMN_LOCATION + " TEXT, " + COLUMN_MARKERTEST + " TEXT,"+ COLUMN_IMAGE +" TEXT" + ");";
        db.execSQL(CREATE_POINT_TABLE);

        String CREATE_TABLE_AFECTED  = "CREATE TABLE " +
                TABLE_AFECTED + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_MARKER_NAME
                + " TEXT," + COLUMN_END_TIME  +" TEXT " + ");";
        db.execSQL(CREATE_TABLE_AFECTED);

        String CREATE_TABLE_SUBURBS  = "CREATE TABLE " +
                TABLE_SUBURBS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_SUBURBS
                + " TEXT,"+ GROUP_NAME + " TEXT, "+ GROUP_ID + " TEXT" + ");";
        db.execSQL(CREATE_TABLE_SUBURBS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_AFECTED);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POINT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GCM);
        }
        onCreate(db);
    }

//add users

  public void addUser(Users users){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = new ContentValues();
      values.put(COLUMN_USER,users.userID.toString());
      db.insert(TABLE_USERS,null,values);
      Log.d(TAG, "Added user to Sqlite: " + users.toString());
      db.close();
  }

     public void addSuburbs(Suburbs suburbs){
         String selectQuery = "SELECT  * FROM " + TABLE_SUBURBS;
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursor = db.rawQuery(selectQuery, null);
             ContentValues values = new ContentValues();
             values.put(COLUMN_SUBURBS, suburbs.surburbName);
             values.put(GROUP_NAME, suburbs.groupName);
             values.put(GROUP_ID, suburbs.groupId);
             db.insert(TABLE_SUBURBS, null, values);
             Log.d(TAG, "Added Suburb to Sqlite: ");


         db.close();
       }


  public  void addGcm(GcmDevice gcmDevice) {
      ContentValues values2 = new ContentValues();
      SQLiteDatabase db = this.getWritableDatabase();
      values2.put(GCM_ID, gcmDevice.deviceID.toString());
      db.insert(TABLE_GCM, null, values2);
      Log.d(TAG, "Added Gcm id from Sqlite: " + gcmDevice.toString());
      db.close();

  }
    public List<Suburbs> getMySuburbs() {
        List<Suburbs> suburbsList = new ArrayList<Suburbs>();
        String selectQuery = "SELECT  * FROM " + TABLE_SUBURBS;
        Suburbs suburbs= new Suburbs();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                String surbName = cursor.getString(1);
                String surbGroup = cursor.getString(2);
                String subId = cursor.getString(3);

                suburbs = new Suburbs(surbName,surbGroup,subId);
                suburbs.add(suburbs);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        if (!suburbs.isEmpty()) {
            Log.w(LOG, "got your surbubs");
        } else {
            Log.w(LOG, "can no get suburbs from the database");
        }
        return suburbsList;
    }

    public SavedLocations getSingleLocation(String locationName){


        String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS + " WHERE " + COLUMN_LOCATION + " = \"" + locationName +"\"" ;

     SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     SavedLocations savedLocations = new SavedLocations();
        if(cursor.moveToFirst())
        {

            savedLocations.latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE));
            savedLocations.longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE));
            cursor.close();
        }
        else
        {
            savedLocations = null;
        }
        db.close();


       return savedLocations;

    }

    public Suburbs getGroupID(String Suburb ){
        String selectQuery = "SELECT  * FROM " + TABLE_SUBURBS + " WHERE " + COLUMN_SUBURBS + " = \"" + Suburb +"\"" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
         Suburbs suburbs = new Suburbs();
        if(cursor.moveToFirst())
        {
            suburbs.groupId = cursor.getString(cursor.getColumnIndex(GROUP_ID));

            cursor.close();
        }
        else
        {
          suburbs = null;
        }
        db.close();
        return  suburbs;
    }

    public String[] getAllSuburbs(){
        String selectQuery = "SELECT  * FROM " + TABLE_SUBURBS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.getCount() >0)
        {
            String[] str = new String[cursor.getCount()];

            int i = 0;

            while (cursor.moveToNext())
            {
                str[i] = cursor.getString(cursor.getColumnIndex(COLUMN_SUBURBS));

                i++;
            }
            return str;
        }
        else
        {
            return new String[] {};
        }
    }


    //add user locations
    public void addLocations(SavedLocations savedLocations) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION, savedLocations.locName);
        values.put(COLUMN_LATITUDE, savedLocations.latitude.toString());
        values.put(COLUMN_LONGITUDE, savedLocations.longitude.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_LOCATIONS, null, values);
        db.close();
        Log.w(LOG, "Entries saved to the database");

    }
    //add user locations
    public void addAffected(AffectedMarkers affectedMarkers) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_MARKER_NAME, affectedMarkers.markerName);
        values.put(COLUMN_END_TIME,affectedMarkers.endTime);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_AFECTED, null, values);
        db.close();
        Log.w(LOG, "Entries saved to the database");

    }

    public void addPointLocations(PointOfInterest pointOfInterest) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION, pointOfInterest.locationName);
        values.put(COLUMN_LATITUDE, pointOfInterest.latitude.toString());
        values.put(COLUMN_LONGITUDE, pointOfInterest.longitude.toString());
        values.put(COLUMN_MARKERTEST,pointOfInterest.markerText);
        values.put(COLUMN_IMAGE,pointOfInterest.thumbnailUrl);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_POINT, null, values);
        db.close();
        Log.w(LOG, "Point of Entries saved to the database");

    }
    public List<Users> getUserDetails() {
  List<Users> userList = new ArrayList<Users>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                String userid = cursor.getString(1);

                Users users= new Users(userid);
               userList.add(users);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        if (userList.isEmpty()) {
            Log.w(LOG, "got your user id"+ userList.toString());
        } else {
            Log.w(LOG, "can no get user from the database");
        }
        return userList;
    }

    public List<AffectedMarkers> getAffectedMarkers() {
        List<AffectedMarkers> affectedMarkersList = new ArrayList<AffectedMarkers>();
        String selectQuery = "SELECT  * FROM " + TABLE_AFECTED;
        AffectedMarkers affectedMarkers = new AffectedMarkers();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                String makerNa = cursor.getString(1);
                String end = cursor.getString(2);

               affectedMarkers = new AffectedMarkers(makerNa,end);
                affectedMarkersList.add(affectedMarkers);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        if (!affectedMarkers.isEmpty()) {
            Log.w(LOG, "got your user id"+ affectedMarkersList.toString());
        } else {
            Log.w(LOG, "can no get user from the database");
        }
        return affectedMarkersList;
    }





    public List<GcmDevice> getGcm() {
        List<GcmDevice> gcmDevicesList = new ArrayList<GcmDevice>();

        String selectQuery = "SELECT  * FROM " + TABLE_GCM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                String gcmd = cursor.getString(1);

              GcmDevice gcmDevices = new GcmDevice(gcmd);
                gcmDevicesList.add(gcmDevices);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        if (gcmDevicesList.isEmpty()) {
            Log.w(LOG, "got your gcm device id");
        } else {
            Log.w(LOG, "can no get gcmfrom the database");
        }
        return gcmDevicesList;
    }


    public List<SavedLocations> getAllLocations() {
        List<SavedLocations> locationList = new ArrayList<SavedLocations>();

        //String query = "Select * FROM " + TABLE_LOCATIONS + " WHERE " + COLUMN_LOCATION  + " =  \"" +lacationName +"\"";

        String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {

                Integer locationID = cursor.getInt(0);
                String locationStr = cursor.getString(1);
                String mLat = cursor.getString(2);
                Log.w(LOG, "string before double");
                String mlongi = cursor.getString(3);

                Double lat = Double.parseDouble(mLat);
                Double longi = Double.parseDouble(mlongi);
                SavedLocations savedLocations = new SavedLocations(locationStr, locationID, lat, longi);
                locationList.add(savedLocations);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        if (locationList.isEmpty()) {
            Log.w(LOG, "Entries retrieved you should be able to see yo locations");
        } else {
            Log.w(LOG, "can no get saved locations from the database");
        }
        return locationList;

    }

    public List<PointOfInterest> getAllPointLocations() {
        List<PointOfInterest> PointlocationList = new ArrayList<PointOfInterest>();


        String selectQuery = "SELECT  * FROM " + TABLE_POINT;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {


                String mLat = cursor.getString(1);
                Log.w(LOG, "string before double");
                String mlongi = cursor.getString(2);
                String locationStr = cursor.getString(3);
                String markerTexts = cursor.getString(4);
                String imagei  = cursor.getString(5);

                Double lat = Double.parseDouble(mLat);
                Double longi = Double.parseDouble(mlongi);
                PointOfInterest p = new PointOfInterest(lat,longi,locationStr,markerTexts,imagei);
                PointlocationList.add(p);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        if (PointlocationList.isEmpty()) {
            Log.w(LOG, "Entries retrieved you should be able to see yo locations");
        } else {
            Log.w(LOG, "can no get saved locations from the database");
        }
        return PointlocationList;

    }

    public void deleteLocation(long locationName) {

        String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {


                db.delete(TABLE_LOCATIONS,COLUMN_ID + "= ?",new String[]{String.valueOf(locationName)});


            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }


    }

    public void deleteAffected() {

        String selectQuery = "SELECT  * FROM " + TABLE_AFECTED;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {


                db.delete(TABLE_AFECTED,COLUMN_ID,null);


            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }


    }
}