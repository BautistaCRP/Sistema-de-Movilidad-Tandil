package bautistacarpintero.sistemamovilidadtandil.Apis.SUMO;

import android.util.Log;

import java.util.ArrayList;

import bautistacarpintero.sistemamovilidadtandil.DataBase.Movement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovementCallback implements Callback<ArrayList<Movement>> {

    private String TAG ="MovementCallback";
    private SumoApiUser sumoApiUser;
    private String cardNumber;
    private int cardId;

    public MovementCallback(SumoApiUser sumoApiUser, String cardNumber, int cardId) {
        this.sumoApiUser = sumoApiUser;
        this.cardNumber = cardNumber;
        this.cardId = cardId;
    }

    @Override
    public void onResponse(Call<ArrayList<Movement>> call, Response<ArrayList<Movement>> response) {
        if(response.isSuccessful()){
            Log.d(TAG, "onResponse: Response is successful");
            ArrayList<Movement> movs = response.body();
            for (Movement m : movs)
                m.setNumber(cardNumber);
            sumoApiUser.setMovements(movs, cardNumber, cardId);
        } else {
            Log.d(TAG, " onResponse: " + response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Movement>> call, Throwable t) {
        Log.e(TAG, " onFailure: " + t.getMessage());
    }
}
