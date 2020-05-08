package bautistacarpintero.sistemamovilidadtandil.Apis.SUMO;

import java.util.ArrayList;

import bautistacarpintero.sistemamovilidadtandil.DataBase.Movement;
import bautistacarpintero.sistemamovilidadtandil.DataBase.RechargePoint;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SumoApiService {
    @GET("movimientos/get_movimientos/{nro}/")
    Call<ArrayList<Movement>> getMovimientos(@Path("nro") String nro);

    @GET("ajax/ebus_dev/pv_serialize/")
    Call<ArrayList<RechargePoint>> getPuestosRecarga();
}
