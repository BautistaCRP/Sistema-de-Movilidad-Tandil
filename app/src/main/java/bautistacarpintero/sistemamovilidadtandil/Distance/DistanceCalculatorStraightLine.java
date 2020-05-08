package bautistacarpintero.sistemamovilidadtandil.Distance;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.DataBase.RechargePoint;

public class DistanceCalculatorStraightLine implements DistanceCalculator {

    private static final String TAG = "DistanceCalculatorStraightLine";

    private DistanceCalculatorUser user;
    private Location currentLocation;

    public DistanceCalculatorStraightLine(DistanceCalculatorUser user) {
        this.user = user;
    }

    @Override
    public float distanceTo(RechargePoint point) {
        if(currentLocation != null ) {
            float[] results = new float[1];
            Double destinationLon = Double.parseDouble(point.getLon());
            Double destinationLat = Double.parseDouble(point.getLat());
            Location.distanceBetween(currentLocation.getLongitude(), currentLocation.getLatitude(), destinationLon, destinationLat, results);
            return results[0];
        }
        return 0;
    }

    @Override
    public void distanceTo(List<RechargePoint> points) {
        ArrayList<Float> distances = new ArrayList<>();
        for(RechargePoint point : points){
            Float distance = distanceTo(point);
            distances.add(distance);
        }
        user.updateDistances(distances);
    }

    @Override
    public void updateCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

}
