package bautistacarpintero.sistemamovilidadtandil.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao //DataAccessObject
public interface MovementDao {

    @Query("SELECT * FROM movimiento WHERE number = :number")
    List<Movement> getAllMovements(String number);

    @Query("DELETE FROM movimiento WHERE number = :number")
    void deleteAllMovements(String number);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMovements(Movement... movement);

}
