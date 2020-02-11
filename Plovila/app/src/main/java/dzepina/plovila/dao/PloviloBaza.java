package dzepina.plovila.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import dzepina.plovila.model.Plovilo;


//singleton
@Database(entities = {Plovilo.class}, version = 1, exportSchema = false)
public abstract class PloviloBaza extends RoomDatabase {

    public abstract PloviloDAO ploviloDAO();

    private static PloviloBaza INSTANCE;

    public static PloviloBaza getBaza(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PloviloBaza.class, "plovila-baza").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
