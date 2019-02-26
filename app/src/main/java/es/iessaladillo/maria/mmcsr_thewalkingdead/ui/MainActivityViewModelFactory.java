package es.iessaladillo.maria.mmcsr_thewalkingdead.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.maria.mmcsr_thewalkingdead.data.Database;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {
    private final Database personajes;

    MainActivityViewModelFactory(Database personajes){
        this.personajes = personajes;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(personajes);
    }
}
