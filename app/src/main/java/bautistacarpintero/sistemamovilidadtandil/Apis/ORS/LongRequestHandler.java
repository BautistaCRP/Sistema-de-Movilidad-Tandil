package bautistacarpintero.sistemamovilidadtandil.Apis.ORS;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.DataBase.RechargePoint;
import bautistacarpintero.sistemamovilidadtandil.Distance.DistanceCalculatorUser;

public class LongRequestHandler implements OrsApiUser{

    private static final String TAG = "LongRequestHandler";

    private DistanceCalculatorUser originalUser;
    private OrsApiHandler apiHandler;
    private Location currentLocation;
    private ArrayList<RechargePoint> points;
    private ArrayList<Float> distancesBuffer;


    public LongRequestHandler(List<RechargePoint> points, DistanceCalculatorUser originalUser, Location currentLocation) {
        this.points = new ArrayList<>(points);
        this.originalUser = originalUser;
        this.currentLocation = currentLocation;
        apiHandler = new OrsApiHandler(this);
        distancesBuffer = new ArrayList<>();
    }

    public void makeRequest(){
        ArrayList<RechargePoint> pointsSubList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            if (!points.isEmpty()) {
                pointsSubList.add(points.remove(0));
            }
        }
        String locations = locationsBuilder(pointsSubList);
        String destinations = destinationsBuilder(pointsSubList.size());
        apiHandler.getDistanceMatrix(locations,destinations);
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
        distancesBuffer.addAll(distances);
        if(!points.isEmpty())
            makeRequest();
        else
            originalUser.updateDistances(distancesBuffer);
    }
}
