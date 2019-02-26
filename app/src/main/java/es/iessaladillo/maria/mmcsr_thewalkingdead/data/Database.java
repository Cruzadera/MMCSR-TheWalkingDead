package es.iessaladillo.maria.mmcsr_thewalkingdead.data;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.maria.mmcsr_thewalkingdead.data.model.Personaje;

public class Database {
    private static Database instance;
    private final ArrayList<Personaje> personajes;
    private final MutableLiveData<List<Personaje>> personajesLiveData = new MutableLiveData<>();

    private Database(){
        this.personajes = new ArrayList<>();
        update();
    }

    private void update() {
        personajesLiveData.setValue(new ArrayList<>(personajes));
    }

    public static Database getInstance(){
        if(instance==null){
            instance = new Database();
        }
        return instance;
    }

    public LiveData<List<Personaje>> queryPersonajes(){
        return personajesLiveData;
    }


    public void borrarPersonaje(Personaje personaje) {
        personajes.remove(personaje);
        update();
    }

    public void agregar(Personaje personaje) {
        personajes.add(personaje);
        update();
    }
}
