
package bautistacarpintero.sistemamovilidadtandil.Apis.ORS.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("engine")
    @Expose
    private Engine engine;
    @SerializedName("attribution")
    @Expose
    private String attribution;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("query")
    @Expose
    private Query query;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

}
