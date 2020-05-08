package bautistacarpintero.sistemamovilidadtandil.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao //DataAccessObject
public interface CardDao {
    @Query("SELECT * FROM card")
    List<Card> getAllCards();

    @Insert
    void insertAllCards(Card... cards);

    @Update
    void updateAllCards(Card... cards);

    @Delete
    void deleteAllCards(Card... cards);
}
