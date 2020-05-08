package bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Movements;

import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.DataBase.Movement;

public interface GetAllMovementsTaskListener {
    void setMovementsFromDB(List<Movement> movements);
}
