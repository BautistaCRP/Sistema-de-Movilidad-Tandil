package bautistacarpintero.sistemamovilidadtandil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.Adapters.PagerAdapter;
import bautistacarpintero.sistemamovilidadtandil.DataBase.AppDataBase;
import bautistacarpintero.sistemamovilidadtandil.Tabs.TabMain;
import bautistacarpintero.sistemamovilidadtandil.Tabs.TabRechargePoints;


public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "MainActivity";
    private TabLayout tabLayout;
    private TabMain tabMain;
    private TabRechargePoints tabRechargePoints;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private Location currentLocation;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 56156;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        createNotificationChannel();

        tabMain = new TabMain();
        tabMain.setParentActivity(this);
        tabRechargePoints = new TabRechargePoints();
        pagerAdapter.addFragment(tabMain,"Tarjetas");
        pagerAdapter.addFragment(tabRechargePoints,"Puestos de recarga");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        checkPermissions();
        gpsStatusCheck();
    }

    @Override
    public void finish(){
        AppDataBase.destroyInstance();
        super.finish();
    }

    public void hideRechargeTab() {
        pagerAdapter.deleteLastFragment();
    }

    public void showRechargeTab() {
        pagerAdapter.addFragment(tabRechargePoints,"Puestos de recarga");
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        tabRechargePoints.updateCurrentLocation(currentLocation);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if(provider.equals(LocationManager.GPS_PROVIDER) && status == LocationProvider.AVAILABLE){
            checkPermissions();
        }
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * Checks the dynamically-controlled permissions and requests missing permissions from end user.
     */
    protected void checkPermissions() {
        List<String> missingPermissions = new ArrayList<>();

        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }

        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            String[] permissions = missingPermissions.toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            this.onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS, grantResults);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                List<String> providers = locationManager.getProviders(true);

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, this);
                if (!providers.isEmpty()) {
                    String provider = providers.get(0);
                    currentLocation = locationManager.getLastKnownLocation(provider);
                    tabRechargePoints.updateCurrentLocation(currentLocation);
                }
                break;
        }
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            NotificationChannel channel =
                    new NotificationChannel(TabMain.NOTIFICATION_CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public void gpsStatusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tu ubicacion esta desactivada")
                .setMessage("Algunas caracteristicas no estaran disponibles. \n¿Desea activar la ubicacion?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_manu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.about_me:
                startActivity(new Intent(this,AboutMeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}