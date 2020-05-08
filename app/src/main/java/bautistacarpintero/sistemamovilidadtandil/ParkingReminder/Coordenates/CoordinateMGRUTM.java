package bautistacarpintero.sistemamovilidadtandil.ParkingReminder.Coordenates;

import java.util.Objects;

public class CoordinateMGRUTM  {

    private int zone;
    private String latZone;
    private String digraph1;
    private String digraph2;
    private double easting;
    private double northing;

    public CoordinateMGRUTM(String mgrutm) {
        zone = Integer.parseInt(mgrutm.substring(0, 2));
        latZone = mgrutm.substring(2, 3);
        digraph1 = mgrutm.substring(3, 4);
        digraph2 = mgrutm.substring(4, 5);
        easting = Double.parseDouble(mgrutm.substring(5, 10));
        northing = Double.parseDouble(mgrutm.substring(10, 15));
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public String getLatZone() {
        return latZone;
    }

    public void setLatZone(String latZone) {
        this.latZone = latZone;
    }

    public String getDigraph1() {
        return digraph1;
    }

    public void setDigraph1(String digraph1) {
        this.digraph1 = digraph1;
    }

    public String getDigraph2() {
        return digraph2;
    }

    public void setDigraph2(String digraph2) {
        this.digraph2 = digraph2;
    }

    public double getEasting() {
        return easting;
    }

    public void setEasting(double easting) {
        this.easting = easting;
    }

    public double getNorthing() {
        return northing;
    }

    public void setNorthing(double northing) {
        this.northing = northing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinateMGRUTM that = (CoordinateMGRUTM) o;
        return zone == that.zone &&
                Double.compare(that.easting, easting) == 0 &&
                Double.compare(that.northing, northing) == 0 &&
                Objects.equals(latZone, that.latZone) &&
                Objects.equals(digraph1, that.digraph1) &&
                Objects.equals(digraph2, that.digraph2);
    }

    @Override
    public int hashCode() {

        return Objects.hash(zone, latZone, digraph1, digraph2, easting, northing);
    }
}