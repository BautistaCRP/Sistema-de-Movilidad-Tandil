package bautistacarpintero.sistemamovilidadtandil.Apis.SUMO;

import java.util.ArrayList;

import bautistacarpintero.sistemamovilidadtandil.DataBase.Movement;
import bautistacarpintero.sistemamovilidadtandil.DataBase.RechargePoint;

public interface SumoApiUser {
    void setMovements(ArrayList<Movement> movs, String number, int cardId);

    void setRechargePoint(ArrayList<RechargePoint> points);
}
