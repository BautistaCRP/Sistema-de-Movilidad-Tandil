
package bautistacarpintero.sistemamovilidadtandil.Apis.ORS.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Query {

    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("units")
    @Expose
    private String units;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

}
