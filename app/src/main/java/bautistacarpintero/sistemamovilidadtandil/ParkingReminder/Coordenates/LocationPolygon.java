package bautistacarpintero.sistemamovilidadtandil.ParkingReminder.Coordenates;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.polygon.SimplePolygon2D;

public class LocationPolygon {

    private SimplePolygon2D polygon;
    private ArrayList<Location> locations;
    private CoordinateConversion cc;

    public LocationPolygon() {
        polygon = new SimplePolygon2D();
        locations = new ArrayList<>();
        cc = new CoordinateConversion();
    }

    public LocationPolygon(List<Location> locations) {
        polygon = new SimplePolygon2D();
        cc = new CoordinateConversion();
        this.locations = new ArrayList<>(locations);
        for(Location location : this.locations){
            polygon.addVertex(locationToPoint(location));
        }
    }

    public void addLocation(Location location){
        locations.add(location);
        polygon.addVertex(locationToPoint(location));
    }

    public boolean removeLocation(Location location){
        boolean out;
        out = locations.remove(location);
        out = out && polygon.removeVertex(locationToPoint(location));
        return out;
    }

    public boolean containsLocation(Location location){
        return polygon.contains(locationToPoint(location));
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("LocationPolygon: ");
        for(Location location : locations){
            builder.append(location.getLatitude())
                    .append(" , ")
                    .append(location.getLongitude())
                    .append(";");
        }
        return builder.toString();
    }

    private Point2D locationToPoint(Location location){
        CoordinateMGRUTM coord = new CoordinateMGRUTM(cc.latLon2MGRUTM(location.getLatitude(),location.getLongitude()));
        Point2D point2D = new Point2D(coord.getEasting(),coord.getNorthing());
        return point2D;
    }


}
