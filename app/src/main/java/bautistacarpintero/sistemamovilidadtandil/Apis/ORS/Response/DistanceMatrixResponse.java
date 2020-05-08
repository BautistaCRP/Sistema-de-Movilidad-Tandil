
package bautistacarpintero.sistemamovilidadtandil.Apis.ORS.Response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class and the whole package was generated using http://www.jsonschema2pojo.org/
 * from a sample response in Json code. I recommend use a tool call postman to make
 * requests and playing with the API that you are using.
 */
public class DistanceMatrixResponse {

    @SerializedName("distances")
    @Expose
    private List<List<Float>> distances = null;
    @SerializedName("destinations")
    @Expose
    private List<Destination> destinations = null;
    @SerializedName("sources")
    @Expose
    private List<Source> sources = null;
    @SerializedName("info")
    @Expose
    private Info info;

    public List<List<Float>> getDistances() {
        return distances;
    }

    public void setDistances(List<List<Float>> distances) {
        this.distances = distances;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
