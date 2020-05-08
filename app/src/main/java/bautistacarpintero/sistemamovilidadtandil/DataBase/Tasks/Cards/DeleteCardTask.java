package bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Cards;

import android.content.Context;
import android.os.AsyncTask;

import bautistacarpintero.sistemamovilidadtandil.DataBase.AppDataBase;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Card;

public class DeleteCardTask extends AsyncTask<Void,Void,Void> {
    private Context context;
    private Card card;

    public DeleteCardTask(Context context, Card card) {
        this.context = context;
        this.card = card;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AppDataBase.getAppDatabase(context).cardDao().deleteAllCards(card);
        return null;
    }
}
