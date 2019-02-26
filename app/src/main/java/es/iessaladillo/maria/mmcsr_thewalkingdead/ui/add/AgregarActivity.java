package es.iessaladillo.maria.mmcsr_thewalkingdead.ui.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import es.iessaladillo.maria.mmcsr_thewalkingdead.R;
import es.iessaladillo.maria.mmcsr_thewalkingdead.data.model.Personaje;
import es.iessaladillo.maria.mmcsr_thewalkingdead.databinding.ActivityAgregarBinding;
import es.iessaladillo.maria.mmcsr_thewalkingdead.utils.KeyboardUtils;
import es.iessaladillo.maria.mmcsr_thewalkingdead.utils.SnackbarUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class AgregarActivity extends AppCompatActivity {
    public static final String EXTRA_PERSON = "EXTRA_PERSON";
    ActivityAgregarBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        b = DataBindingUtil.setContentView(this, R.layout.activity_agregar);
        setupViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_agregar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuDone) {
            guardar();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void guardar() {
        if(comprobarCamposRequeridos()){
            KeyboardUtils.hideSoftKeyboard(this);
            SnackbarUtils.snackbar(b.imgPersonaje, getString(R.string.main_saved_succesfully));
            guardarPersonaje(new Personaje());
        }
    }

    private boolean comprobarCamposRequeridos() {
        return !(TextUtils.isEmpty(b.txtNombre.getText().toString()) && TextUtils.isEmpty(b.txtInterprete.getText().toString()));
    }

    private void setImagenDefecto(ImageView imageView){
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(imageView);
    }

    private void setupViews() {
        setImagenDefecto(b.imgPersonaje);
        b.txtInterprete.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                guardar();
                return true;
            }
            return false;
        });
    }

    public static void startForResult(Activity actividad, int requestCode, Personaje personaje) {
        Intent intencion = new Intent(actividad, AgregarActivity.class);
        intencion.putExtra(EXTRA_PERSON, personaje);
        actividad.startActivityForResult(intencion, requestCode);
    }

    private void guardarPersonaje(Personaje personaje) {
        Intent intent = new Intent();
        personaje.setNombre(b.txtNombre.getText().toString());
        personaje.setInterprete(b.txtInterprete.getText().toString());
        personaje.setDescripcion(b.txtDescripcion.getText().toString());
        personaje.setTemporada(b.spinnerTemporada.getSelectedItem().toString());
        personaje.setPrincipal(b.checkPrincipal.isChecked());
        if(TextUtils.isEmpty(b.txtURL.getText().toString())){
            personaje.setURLPoster("http://i.imgur.com/DvpvklR.png");
        }else{
            personaje.setURLPoster(b.txtURL.getText().toString());
        }
        intent.putExtra(EXTRA_PERSON, personaje);
        setResult(RESULT_OK, intent);
        finish();
    }


}
