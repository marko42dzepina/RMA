package dzepina.plovila.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import dzepina.plovila.dao.PloviloBaza;
import dzepina.plovila.dao.PloviloDAO;
import dzepina.plovila.model.Plovilo;


public class PloviloViewModel extends AndroidViewModel {

    PloviloDAO ploviloDAO;

    private Plovilo plovilo;

    public Plovilo getPlovilo() {
        return plovilo;
    }

    public void setPlovilo(Plovilo plovilo) {
        this.plovilo = plovilo;
    }

    private LiveData<List<Plovilo>> plovila;

    public PloviloViewModel(Application application) {
        super(application);
        ploviloDAO = PloviloBaza.getBaza(application.getApplicationContext()).ploviloDAO();

    }

    public LiveData<List<Plovilo>> dohvatiPlovila() {
        plovila = ploviloDAO.dohvatiPlovila();
        return plovila;
    }

    public void dodajNovoPlovilo() {

        ploviloDAO.dodajNovoPlovilo(plovilo);
    }

    public void promjeniPlovilo() {

        ploviloDAO.promjeniPlovilo(plovilo);
    }

    public void obrisiPlovilo() {

        ploviloDAO.obrisiPlovilo(plovilo);
    }

}
