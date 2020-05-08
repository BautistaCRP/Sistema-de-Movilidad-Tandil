package bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Movements;

import android.content.Context;
import android.os.AsyncTask;

import bautistacarpintero.sistemamovilidadtandil.DataBase.AppDataBase;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Movement;

public class InsertMovementTask extends AsyncTask<Void,Void,Void> {
    private Context context;
    private Movement movement;

    public InsertMovementTask(Context context, Movement movement) {
        this.context = context;
        this.movement = movement;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        AppDataBase.getAppDatabase(context).movimientoDao().insertAllMovements(movement);
        return null;
    }
}
