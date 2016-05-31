package ng.max.mapengine;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import ng.max.mapengine.adapters.BalloonAdapter;
import ng.max.mapengine.utils.Constants;
import ng.max.mapengine.utils.FontsOverride;
import ng.max.mapengine.utils.Utils;


public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 1234;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;

    double latitude;
    double longitude;

    private SupportMapFragment mapFragment;
    private GoogleMap map;


    private Marker user_marker;
    private Dialog popup_caption;

    CameraPosition cameraPosition;
   // View places;
    Activity context;
    private EditText pickup;
    private EditText delivery;

    private Utils util;
    private Dialog dialog_waiting;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsOverride.setDefaultFont(this, "MONOSPACE", "NotoSans-Regular.ttf");
        getTheme().applyStyle(R.style.ConOver, true);
        setContentView(R.layout.activity_main);

        pickup = (EditText)findViewById(R.id.pickup_address);
        delivery = (EditText)findViewById(R.id.delivery_address);

        pickup.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                pickup.bringToFront();
                return true;
            }
        });
        delivery.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                delivery.bringToFront();
                return true;
            }
        });

        util = new Utils();
        context = this;

        checkPermission();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onResume(){
        super.onResume();

        View dV = getWindow().getDecorView();
        int uiOpts = View.SYSTEM_UI_FLAG_FULLSCREEN;
        //dV.setSystemUiVisibility(uiOpts);

        //CustomActionBar();
        checkPlayServices();

        /*if (cp != null) {
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
            cp = null;
        }*/

    }

    @Override
    protected void onPause() {

        super.onPause();

        //cp = map.getCameraPosition();
        //map = null;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    public void onStartService() {
        //Intent i = new Intent(this, GPlayService.class);
        //startService(i);
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }
    }

    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    private void displayLocation() {
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {

            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }
        else {
            util.onNotify("Couldn't get the location. Make sure location is enabled on the device", context);
        }
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(Constants.TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
        // Once connected with google api, get the location
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap map) {

        LatLng LAGOS = new LatLng(latitude, longitude);

        //map.setMyLocationEnabled(true);
        map.setBuildingsEnabled(true);

        map.getUiSettings().setZoomGesturesEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);
        map.getUiSettings().setScrollGesturesEnabled(true);
        map.getUiSettings().setTiltGesturesEnabled(false);
        //map.getUiSettings().setMyLocationButtonEnabled(true);


        map.setInfoWindowAdapter(new BalloonAdapter(getLayoutInflater(), MainActivity.this));
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

            }
        });

        final double[] oldLat = new double[1];


        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {


            }
        });

       // user_marker = map.addMarker(new MarkerOptions().position(LAGOS).title("SET PICKUP HERE"));
        //user_marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.user_on_map));
        //user_marker.showInfoWindow();

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
               // user_marker.showInfoWindow();
                //places.setVisibility(View.GONE);
            }
        });

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LAGOS, 16));
        this.map = map;


    }

    // @SuppressLint("NewApi")
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);


                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else{
            onStartService();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                   onStartService();
                   Log.e(Constants.TAG, "Permission granted");
                } else {
                    // Permission Denied
                    Log.e(Constants.TAG, "Permission denied");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public void showUserSettings(View v){
        Intent intent = new Intent(this, UserSettingsActivity.class);
        startActivity(intent);
    }



}
