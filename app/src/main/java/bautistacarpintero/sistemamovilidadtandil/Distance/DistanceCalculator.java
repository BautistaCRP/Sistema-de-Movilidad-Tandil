package bautistacarpintero.sistemamovilidadtandil.Distance;

import android.location.Location;

import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.DataBase.RechargePoint;

public interface DistanceCalculator {

    float distanceTo(RechargePoint point);

    void distanceTo(List<RechargePoint> points);

    void updateCurrentLocation(Location currentLocation);


}
