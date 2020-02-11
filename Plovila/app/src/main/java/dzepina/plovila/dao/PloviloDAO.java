package dzepina.plovila.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import dzepina.plovila.model.Plovilo;


@Dao
public interface PloviloDAO {

    @Query("select * from plovila order by id")
    LiveData<List<Plovilo>> dohvatiPlovila();

    @Insert
    void dodajNovoPlovilo(Plovilo plovilo);

    @Update
    void promjeniPlovilo(Plovilo plovilo);

    @Delete
    void obrisiPlovilo(Plovilo plovilo);


}
