package es.iessaladillo.maria.mmcsr_thewalkingdead.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.maria.mmcsr_thewalkingdead.R;
import es.iessaladillo.maria.mmcsr_thewalkingdead.data.model.Personaje;

public class MainActivityAdapter extends ListAdapter<Personaje, MainActivityAdapter.ViewHolder> {
    interface OnDeleteListener{
        void onDelete(int position);
    }

    interface OnSearchListener{
        void onSearch(int position);
    }

    protected MainActivityAdapter(@NonNull DiffUtil.ItemCallback<Personaje> diffCallback) {
        super(diffCallback);
    }

    private OnDeleteListener onDeleteListener;

    private OnSearchListener onSearchListener;


    void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }
    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    MainActivityAdapter(){
        super(new DiffUtil.ItemCallback<Personaje>() {
            @Override
            public boolean areItemsTheSame(@NonNull Personaje oldItem, @NonNull Personaje newItem) {
                return oldItem.getNombre() == newItem.getNombre();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Personaje oldItem, @NonNull Personaje newItem) {
                return TextUtils.equals(oldItem.getInterprete(), newItem.getInterprete());
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    protected Personaje getItem(int position) {
        return super.getItem(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView lblNombre;
        private final TextView lblInterprete;
        private final ImageView imagenActor;
        private final TextView btnOptions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Context context = itemView.getContext();
            lblNombre = ViewCompat.requireViewById(itemView, R.id.nombrePersonaje);
            lblInterprete = ViewCompat.requireViewById(itemView, R.id.interpretePersonaje);
            imagenActor = ViewCompat.requireViewById(itemView, R.id.imagenPersonaje);
            btnOptions = ViewCompat.requireViewById(itemView, R.id.textViewOptions);
        }

        void bind(Personaje personaje){
            lblNombre.setText(personaje.getNombre());
            lblInterprete.setText(personaje.getInterprete());
            Picasso.with(itemView.getContext()).load(personaje.getURLPoster()).into(imagenActor);
            btnOptions.setOnClickListener(l->mostrarMenu(itemView.getContext()));
        }

        private void mostrarMenu(Context context) {
            PopupMenu popup = new PopupMenu(context, btnOptions);
            popup.inflate(R.menu.activity_main_item);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.mnuBorrar:
                        if(onDeleteListener != null){
                            btnOptions.setOnClickListener(v -> onDeleteListener.onDelete(getAdapterPosition()));
                        }
                        break;
                    case R.id.mnuBuscar:
                        if(onSearchListener !=null){
                            btnOptions.setOnClickListener(v -> onSearchListener.onSearch(getAdapterPosition()));

                        }
                        break;
                }
                return false;
            });
            popup.show();
        }
    }

}
