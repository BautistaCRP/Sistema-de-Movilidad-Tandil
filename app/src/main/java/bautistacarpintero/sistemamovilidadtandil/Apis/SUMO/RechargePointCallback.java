package bautistacarpintero.sistemamovilidadtandil.Apis.SUMO;

import android.util.Log;

import java.util.ArrayList;

import bautistacarpintero.sistemamovilidadtandil.DataBase.RechargePoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RechargePointCallback implements Callback<ArrayList<RechargePoint>> {
    private static final String TAG = "RechargePointCallback";
    private SumoApiUser sumoApiUser;

    public RechargePointCallback(SumoApiUser sumoApiUser) {
        this.sumoApiUser = sumoApiUser;
    }

    @Override
    public void onResponse(Call<ArrayList<RechargePoint>> call, Response<ArrayList<RechargePoint>> response) {
        if(response.isSuccessful()){
            Log.d(TAG, "onResponse: Response is successful");
            ArrayList<RechargePoint> points = response.body();
            sumoApiUser.setRechargePoint(points);
        } else {
            Log.d(TAG, " onResponse: " + response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<ArrayList<RechargePoint>> call, Throwable t) {
        Log.e(TAG, " onFailure: " + t.getMessage());
    }
}
