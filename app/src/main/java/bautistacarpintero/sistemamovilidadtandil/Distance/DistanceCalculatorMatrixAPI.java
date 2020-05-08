package bautistacarpintero.sistemamovilidadtandil.Distance;

import android.location.Location;

import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.Apis.ORS.LongRequestHandler;
import bautistacarpintero.sistemamovilidadtandil.Apis.ORS.OrsApiHandler;
import bautistacarpintero.sistemamovilidadtandil.Apis.ORS.OrsApiUser;
import bautistacarpintero.sistemamovilidadtandil.DataBase.RechargePoint;

public class DistanceCalculatorMatrixAPI implements DistanceCalculator, OrsApiUser {

    private static final String TAG = "DistanceCalculatorMatrixAPI";
    private DistanceCalculatorUser user;
    private Location currentLocation;
    private OrsApiHandler orsApiHandler;


    public DistanceCalculatorMatrixAPI(DistanceCalculatorUser user, Location location) {
        this.user = user;
        orsApiHandler = new OrsApiHandler(this);
        currentLocation = location;
    }

    @Override
    public float distanceTo(RechargePoint point) {
        return 0;
    }

    @Override
    public void distanceTo(List<RechargePoint> points) {
        if(points.size() > 25){
            LongRequestHandler longRequestHandler = new LongRequestHandler(points,user,currentLocation);
            longRequestHandler.makeRequest();
            return;
        }
        String locations = locationsBuilder(points);
        String destinations = destinationsBuilder(points.size());
        orsApiHandler.getDistanceMatrix(locations,destinations);
    }

    @Override
    public void updateCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }


    private String locationsBuilder(List<RechargePoint> points){
        StringBuilder builder = new StringBuilder("");
        builder.append(currentLocation.getLongitude()).append(",").append(currentLocation.getLatitude()).append("|");
        for (RechargePoint point : points){
            builder.append(point.getLon())
                    .append(",")
                    .append(point.getLat())
                    .append("|");
        }
        //Delete the last pipe
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    private String destinationsBuilder(int destinationsSize){
        StringBuilder builder = new StringBuilder("");
        for (int i = 1; i <= destinationsSize; i++) {
            builder.append(i).append(",");
        }
        //Delete the last coma
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    @Override
    public void setDistances(List<Float> distances) {
        user.updateDistances(distances);
    }

}
