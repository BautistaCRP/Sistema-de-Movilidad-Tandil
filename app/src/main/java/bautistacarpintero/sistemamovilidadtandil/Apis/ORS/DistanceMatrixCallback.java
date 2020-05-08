package bautistacarpintero.sistemamovilidadtandil.Apis.ORS;

import android.util.Log;

import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.Apis.ORS.Response.DistanceMatrixResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistanceMatrixCallback implements Callback<DistanceMatrixResponse> {

    private static final String TAG = "DistanceMatrixCallback";

    private OrsApiUser user;

    public DistanceMatrixCallback(OrsApiUser user) {
        this.user = user;
    }

    @Override
    public void onResponse(Call<DistanceMatrixResponse> call, Response<DistanceMatrixResponse> response) {
        if(response.isSuccessful()){
            Log.d(TAG, "onResponse: Response is successful");
            DistanceMatrixResponse respuesta = response.body();
            List<Float> distances = respuesta.getDistances().get(0);
            user.setDistances(distances);
        } else {
            Log.d(TAG, " onResponse: Response is not successful, error body: " + response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<DistanceMatrixResponse> call, Throwable t) {
        Log.e(TAG, " onFailure: " + t.getMessage());
    }
}
