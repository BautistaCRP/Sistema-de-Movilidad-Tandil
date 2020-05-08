package bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Cards;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.DataBase.AppDataBase;
import bautistacarpintero.sistemamovilidadtandil.DataBase.Card;

public class GetAllCardsTask extends AsyncTask<Void,Void,List<Card>> {
    private Context context;
    private GetAllCardsTaskListener listener;

    public GetAllCardsTask(Context context, GetAllCardsTaskListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<Card> doInBackground(Void... voids) {
        List<Card> cards = AppDataBase.getAppDatabase(context).cardDao().getAllCards();
        return cards;
    }

    @Override
    protected void onPostExecute(List<Card> cards) {
        listener.setCardsFromDB(cards);
    }
}
