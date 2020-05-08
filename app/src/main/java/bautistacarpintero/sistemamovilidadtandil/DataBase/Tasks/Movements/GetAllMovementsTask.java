package bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Movements;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.DataBase.AppDataBase;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Movement;

public class GetAllMovementsTask extends AsyncTask<Void,Void,List<Movement>> {
    private Context context;
    private String number;
    private GetAllMovementsTaskListener listener;

    public GetAllMovementsTask(Context context, String number, GetAllMovementsTaskListener listener) {
        this.context = context;
        this.number = number;
        this.listener = listener;
    }


    @Override
    protected List<Movement> doInBackground(Void... voids) {
        List<Movement> movs = AppDataBase.getAppDatabase(context).movimientoDao().getAllMovements(number);
        return movs;
    }

    @Override
    protected void onPostExecute(List<Movement> movements) {
        listener.setMovementsFromDB(movements);
    }
}
