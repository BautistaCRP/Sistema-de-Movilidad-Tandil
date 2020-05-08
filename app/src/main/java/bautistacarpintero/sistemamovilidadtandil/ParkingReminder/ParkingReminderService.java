package bautistacarpintero.sistemamovilidadtandil.ParkingReminder;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import bautistacarpintero.sistemamovilidadtandil.Apis.SUMO.SumoApiHandler;
import bautistacarpintero.sistemamovilidadtandil.Apis.SUMO.SumoApiUser;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Movement;
import bautistacarpintero.sistemamovilidadtandil.DataBase.RechargePoint;
import bautistacarpintero.sistemamovilidadtandil.ParkingReminder.Coordenates.LocationPolygon;
import bautistacarpintero.sistemamovilidadtandil.R;
import bautistacarpintero.sistemamovilidadtandil.Tabs.TabMain;

public class ParkingReminderService extends Service {

    private static final String TAG = "ParkingReminderService";

    private NotificationManager notificationManager;
    private NotificationCompat.Builder notifyBuilder;
    private static String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID";
    private static final int NOTIFICATION_ID = 0;

    private LocationManager locationManager;
    private LocationPolygon locationPolygon;
    private MyRunnable myRunnable;
    private String cardNumber;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void myStartForegroundWithNotification(){
        String CHANNEL_ID = TabMain.NOTIFICATION_CHANNEL_ID;
        Intent broadcastIntent = new Intent(this,NotificationReceiver.class);
        PendingIntent actionIntent = PendingIntent.getBroadcast(
                this,0,broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("El recordatorio se está ejecutando en segundo plano")
                .setContentText("Toca el boton para cancelarlo")
                .setSmallIcon(R.drawable.ic_sistema_de_movilidad)
                .setColor(Color.parseColor("#FF9800"))
                .addAction(R.drawable.ic_sistema_de_movilidad, "Cancelar Recordatorio",actionIntent)
                .setAutoCancel(true)
                .build();
        startForeground(1, notification);

    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        myStartForegroundWithNotification();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        cardNumber = intent.getStringExtra("cardNumber");
        ArrayList<Location> locations = intent.getParcelableArrayListExtra("locationPolygon");
        if (locations == null)
            locationPolygon = TabMain.getLocationPolygon();
        else
            locationPolygon = new LocationPolygon(locations);

        myRunnable = new MyRunnable(cardNumber, locationManager);
        Log.d(TAG, "onStartCommand: cardNumber = "+cardNumber+" locationPolygon = "+locationPolygon);
        new Handler(Looper.getMainLooper()).post(myRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        locationManager.removeUpdates(myRunnable);
        super.onDestroy();
    }

    private void addNotification(int notificationID, String ContentTitle, String ContentText, boolean onlyAlertOnce){
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifyBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setContentTitle(ContentTitle)
                .setContentText(ContentText)
                .setOnlyAlertOnce(onlyAlertOnce)
                .setSmallIcon(R.drawable.ic_sistema_de_movilidad);

        Notification myNotification = notifyBuilder.build();
        notificationManager.notify(notificationID, myNotification);
    }

    private void notifyCardOpen(String address){
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int unicodeWarning = 0x26a0;
        String emojiWarning = new String(Character.toChars(unicodeWarning));
        String message = "Te olvidaste de cerrar la tarjeta SUMO en el parquimetro de "+address;
        notifyBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Estacionamiento abierto "+emojiWarning)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setColor(Color.RED)
                .setSmallIcon(R.drawable.ic_sistema_de_movilidad);

        Notification myNotification = notifyBuilder.build();
        notificationManager.notify(NOTIFICATION_ID, myNotification);
    }

    @SuppressLint("MissingPermission")
    private class MyRunnable implements Runnable, SumoApiUser, LocationListener{

        private static final String TAG = "MyRunnable";

        private String cardNumber;
        private Location currentLocation;
        private SumoApiHandler sumoApiHandler;
        private boolean inLocationPolygon;


        public MyRunnable(String cardNumber, LocationManager mLocationManager) {
            this.cardNumber = cardNumber;
            sumoApiHandler = new SumoApiHandler(this);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            currentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (currentLocation == null)
                currentLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            inLocationPolygon = locationPolygon.containsLocation(currentLocation);
        }

        @Override
        public void run() {
            Log.d(TAG, "run: ");
        }

        @Override
        public void setMovements(ArrayList<Movement> movs, String number, int cardId) {
            Log.d(TAG, "setMovements: ");

            if(!isTheCardClosed(movs))
                notifyCardOpen(getParkingAddress(movs));

            stopForeground(true);
            stopSelf();
        }

        @Override
        public void setRechargePoint(ArrayList<RechargePoint> points) {}

        private boolean isTheCardClosed(ArrayList<Movement> movs){
            String in1 = "INGRESO ESTACIONAMIENTO";
            String in2 = "REINGRESO ESTACIONAMIENTO";
            String out1 = "SALIDA ESTACIONAMIENTO";
            String out2 = "CIERRE ESTACIONAMIENTO EN DESCUBIERTO";
            for (Movement mov : movs) {
                String type = mov.getTipo();
                if(type.equals(out1) || type.equals(out2)){
                    return true;
                } else if(type.equals(in1) || type.equals(in2)){
                    return false;
                }
            }
            return true;
        }

        private String getParkingAddress(ArrayList<Movement> movs){
            String in1 = "INGRESO ESTACIONAMIENTO";
            String in2 = "REINGRESO ESTACIONAMIENTO";
            for (Movement mov : movs) {
                String type = mov.getTipo();
                if(type.equals(in1) || type.equals(in2)){
                    return mov.getRecorrido();
                }
            }
            return "";
        }


        @Override
        public void onLocationChanged(Location location) {
            currentLocation = location;

            boolean actualInLocationPolygon = locationPolygon.containsLocation(currentLocation);
            if(inLocationPolygon == true && actualInLocationPolygon == false)
                sumoApiHandler.getMovements(cardNumber,0);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(lastKnownLocation!=null){
                currentLocation = lastKnownLocation;
            }

        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }



}
