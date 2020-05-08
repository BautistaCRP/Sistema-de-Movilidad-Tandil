package bautistacarpintero.sistemamovilidadtandil.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Card.class, Movement.class}, version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract CardDao cardDao();
    public abstract MovementDao movimientoDao();

    private static AppDataBase INSTANCE;

    public static AppDataBase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "sumo")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
