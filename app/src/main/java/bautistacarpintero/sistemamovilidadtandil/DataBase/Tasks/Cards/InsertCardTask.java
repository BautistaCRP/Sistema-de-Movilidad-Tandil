package bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Cards;

import android.content.Context;
import android.os.AsyncTask;

import bautistacarpintero.sistemamovilidadtandil.DataBase.AppDataBase;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Card;

public class InsertCardTask extends AsyncTask<Void,Void,Void>{
    private Context context;
    private Card card;

    public InsertCardTask(Context context, Card card) {
        this.context = context;
        this.card = card;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AppDataBase.getAppDatabase(context).cardDao().insertAllCards(card);
        return null;
    }


}
