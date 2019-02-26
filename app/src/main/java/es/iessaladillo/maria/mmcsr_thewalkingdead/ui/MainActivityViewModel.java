package es.iessaladillo.maria.mmcsr_thewalkingdead.ui;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.maria.mmcsr_thewalkingdead.data.Database;
import es.iessaladillo.maria.mmcsr_thewalkingdead.data.model.Personaje;

public class MainActivityViewModel extends ViewModel {
    private final Database personajes;
    private LiveData<List<Personaje>> personajesLiveData;

    public MainActivityViewModel(Database personajes){
        this.personajes=personajes;
        personajesLiveData = personajes.queryPersonajes();
    }

    public LiveData<List<Personaje>> getPersonajesLiveData() {
        return personajesLiveData;
    }

    void borrarPersonaje(Personaje personaje){
        personajes.borrarPersonaje(personaje);
    }

    public void agregarPersonaje(Personaje personaje) {
        personajes.agregar(personaje);
    }
}
