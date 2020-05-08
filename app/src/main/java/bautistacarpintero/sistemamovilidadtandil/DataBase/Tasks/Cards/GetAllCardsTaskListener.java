package bautistacarpintero.sistemamovilidadtandil.DataBase.Tasks.Cards;

import java.util.List;

import bautistacarpintero.sistemamovilidadtandil.DataBase.Card;

public interface GetAllCardsTaskListener {
    void setCardsFromDB(List<Card> cards);
}
