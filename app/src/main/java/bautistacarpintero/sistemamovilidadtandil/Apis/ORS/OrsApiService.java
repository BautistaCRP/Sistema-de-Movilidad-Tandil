package bautistacarpintero.sistemamovilidadtandil.Apis.ORS;

import bautistacarpintero.sistemamovilidadtandil.Apis.ORS.Response.DistanceMatrixResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OrsApiService {

    @GET("matrix")
    Call<DistanceMatrixResponse> getDistanceMatrix(@Query("api_key") String apiKey,
                                                   @Query("profile") String profile,
                                                   @Query("locations") String locations,
                                                   @Query("sources") String sources,
                                                   @Query("destinations") String destinations,
                                                   @Query("metrics") String metrics,
                                                   @Query("resolve_locations") String resolve_locations,
                                                   @Query("units") String units,
                                                   @Query("optimized") String optimized );


}
