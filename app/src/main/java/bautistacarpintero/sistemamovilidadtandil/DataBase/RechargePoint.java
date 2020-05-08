package bautistacarpintero.sistemamovilidadtandil.DataBase;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Objects;

public class RechargePoint implements Comparable<RechargePoint>, Parcelable{

    private String lat;
    private String lon;
    private String name;
    private String direccion;
    private Float distancia;

    public RechargePoint() {
        lat = "";
        lon = "";
        name = "";
        direccion = "";
        distancia = 0f;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Float getDistancia() {
        return distancia;
    }

    public void setDistancia(Float distancia) {
        this.distancia = distancia;
    }

    @Override
    public int compareTo(@NonNull RechargePoint o) {
        return distancia.compareTo(o.distancia);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RechargePoint that = (RechargePoint) o;
        return Objects.equals(lat, that.lat) &&
                Objects.equals(lon, that.lon) &&
                Objects.equals(name, that.name) &&
                Objects.equals(direccion, that.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon, name, direccion);
    }

    @Override
    public String toString() {
        return direccion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected RechargePoint(Parcel in) {
        lat = in.readString();
        lon = in.readString();
        name = in.readString();
        direccion = in.readString();
        if (in.readByte() == 0) {
            distancia = null;
        } else {
            distancia = in.readFloat();
        }
    }

    public static final Creator<RechargePoint> CREATOR = new Creator<RechargePoint>() {
        @Override
        public RechargePoint createFromParcel(Parcel in) {
            return new RechargePoint(in);
        }

        @Override
        public RechargePoint[] newArray(int size) {
            return new RechargePoint[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeString(name);
        dest.writeString(direccion);
        if (distancia == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(distancia);
        }
    }
}
