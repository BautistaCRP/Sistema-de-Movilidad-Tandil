package bautistacarpintero.sistemamovilidadtandil.Adapters;

import bautistacarpintero.sistemamovilidadtandil.DataBase.Card;

public interface CardViewerUser {
    void updateButtonTriggered(int position);
    void parkingCarButtomTriggered(int position);
    void startMovementActivity(Card card);
    void startActionMode(int position, Card card);
}
