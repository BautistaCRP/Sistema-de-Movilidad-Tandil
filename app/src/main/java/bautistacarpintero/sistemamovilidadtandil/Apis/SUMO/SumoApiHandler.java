package bautistacarpintero.sistemamovilidadtandil.Apis.SUMO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import bautistacarpintero.sistemamovilidadtandil.DataBase.Movement;
import bautistacarpintero.sistemamovilidadtandil.DataBase.RechargePoint;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SumoApiHandler {
    private Retrofit retrofit;
    private static final String TAG = "SumoApiHandler";
    private SumoApiUser sumoApiUser;

    public SumoApiHandler(SumoApiUser sumoApiUser) {
        this.sumoApiUser = sumoApiUser;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.gpssumo.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public void getMovements(String cardNumber, int cardId){
        SumoApiService service = retrofit.create(SumoApiService.class);
        final Call<ArrayList<Movement>> response = service.getMovimientos(cardNumber);
        response.enqueue(new MovementCallback(sumoApiUser,cardNumber, cardId));
    }

    public void getRechargePoints(){
        SumoApiService service = retrofit.create(SumoApiService.class);
        final Call<ArrayList<RechargePoint>> respuesta = service.getPuestosRecarga();
        respuesta.enqueue(new RechargePointCallback(sumoApiUser));
    }
}
