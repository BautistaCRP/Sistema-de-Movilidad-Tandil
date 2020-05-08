
package bautistacarpintero.sistemamovilidadtandil.Apis.ORS.Response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Destination {

    @SerializedName("location")
    @Expose
    private List<Double> location = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("snapped_distance")
    @Expose
    private Double snappedDistance;

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSnappedDistance() {
        return snappedDistance;
    }

    public void setSnappedDistance(Double snappedDistance) {
        this.snappedDistance = snappedDistance;
    }

}
