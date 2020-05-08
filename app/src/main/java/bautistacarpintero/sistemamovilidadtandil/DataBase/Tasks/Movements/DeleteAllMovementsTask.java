package bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Movements;

import android.content.Context;
import android.os.AsyncTask;

import bautistacarpintero.sistemamovilidadtandil.DataBase.AppDataBase;

public class DeleteAllMovementsTask extends AsyncTask<Void,Void,Void> {
    private Context context;
    private String number;

    public DeleteAllMovementsTask(Context context, String number) {
        this.context = context;
        this.number = number;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AppDataBase.getAppDatabase(context).movimientoDao().deleteAllMovements(number);
        return null;
    }
}
