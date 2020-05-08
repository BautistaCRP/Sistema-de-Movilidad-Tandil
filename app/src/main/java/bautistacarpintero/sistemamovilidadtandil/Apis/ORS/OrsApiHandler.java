package bautistacarpintero.sistemamovilidadtandil.Apis.ORS;

import bautistacarpintero.sistemamovilidadtandil.Apis.ORS.Response.DistanceMatrixResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrsApiHandler {
    private static final String TAG = "OrsApiHandler";
    private Retrofit retrofit;
    private OrsApiUser user;
    private static final String ORS_API_KEY = "5b3ce3597851110001cf62488f10074bb8ff43c9b68227565d7e86b8";

    public OrsApiHandler(OrsApiUser user) {
        this.user = user;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openrouteservice.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public  void getDistanceMatrix(String locations, String destinations){

        String sources = "0";
        String profile = "foot-walking";
        String metrics = "distance";
        String resolve_locations = "true";
        String units = "m";
        String optimized = "false";

        OrsApiService service = retrofit.create(OrsApiService.class);
        final Call<DistanceMatrixResponse> response = service.getDistanceMatrix(
                ORS_API_KEY,
                profile,
                locations,
                sources,
                destinations,
                metrics,
                resolve_locations,
                units,
                optimized);
        response.enqueue(new DistanceMatrixCallback(user));
    }

}
