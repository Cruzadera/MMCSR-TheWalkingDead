package es.iessaladillo.maria.mmcsr_thewalkingdead.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import es.iessaladillo.maria.mmcsr_thewalkingdead.R;
import es.iessaladillo.maria.mmcsr_thewalkingdead.data.Database;
import es.iessaladillo.maria.mmcsr_thewalkingdead.data.model.Personaje;
import es.iessaladillo.maria.mmcsr_thewalkingdead.databinding.ActivityMainBinding;
import es.iessaladillo.maria.mmcsr_thewalkingdead.ui.add.AgregarActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final int RC_ADD = 1;
    ActivityMainBinding b;
    MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;
    Personaje personaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(Database.getInstance()))
                .get(MainActivityViewModel.class);
        setupViews();
        observePersonajes();

    }

    private void setupViews() {
        b.lblEmptyView.setOnClickListener(l->agregar());
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        listAdapter = new MainActivityAdapter();
        listAdapter.setOnDeleteListener(position -> deleteUser(listAdapter.getItem(position)));
        listAdapter.setOnSearchListener(position -> buscarPersonaje(listAdapter.getItem(position)));
        b.lstPersonajes.setHasFixedSize(true);
        b.lstPersonajes.setLayoutManager(new GridLayoutManager(this,
                getResources().getInteger(R.integer.personajes_lstPersonajes_columns)));
        b.lstPersonajes.setItemAnimator(new DefaultItemAnimator());
        b.lstPersonajes.setAdapter(listAdapter);
    }

    private void observePersonajes() {
        viewModel.getPersonajesLiveData().observe(this, personajes -> {
            listAdapter.submitList(personajes);
            b.lblEmptyView.setVisibility(personajes.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void buscarPersonaje(Personaje personaje) {
        String web = personaje.getNombre();
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, web);
    }

    private void deleteUser(Personaje personaje){
        viewModel.borrarPersonaje(personaje);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflador = getMenuInflater();
        inflador.inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            agregar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void agregar() {
        AgregarActivity.startForResult(MainActivity.this, RC_ADD, new Personaje());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && (requestCode == RC_ADD)) {
            if (data != null && data.hasExtra(AgregarActivity.EXTRA_PERSON)) {
                personaje = data.getParcelableExtra(AgregarActivity.EXTRA_PERSON);
                agregarBD(personaje);
            }
        }
    }

    private void agregarBD(Personaje personaje) {
        viewModel.agregarPersonaje(personaje);
    }
}
