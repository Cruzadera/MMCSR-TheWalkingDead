package es.iessaladillo.maria.mmcsr_thewalkingdead.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Personaje implements Parcelable {
    private String nombre;
    private String interprete;
    private String URLPoster;
    private String temporada;
    private String descripcion;
    private boolean principal;

    public Personaje(String nombre, String interprete, String URLPoster, String temporada, String descripcion, boolean principal) {
        this.nombre = nombre;
        this.interprete = interprete;
        this.URLPoster = URLPoster;
        this.temporada =temporada;
        this.descripcion = descripcion;
        this.principal = principal;
    }

    public Personaje() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInterprete() {
        return interprete;
    }

    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }

    public String getURLPoster() {
        return URLPoster;
    }

    public void setURLPoster(String URLPoster) {
        this.URLPoster = URLPoster;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.interprete);
        dest.writeString(this.URLPoster);
        dest.writeString(this.temporada);
        dest.writeString(this.descripcion);
        dest.writeByte(this.principal ? (byte) 1 : (byte) 0);
    }

    protected Personaje(Parcel in) {
        this.nombre = in.readString();
        this.interprete = in.readString();
        this.URLPoster = in.readString();
        this.temporada = in.readString();
        this.descripcion = in.readString();
        this.principal = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Personaje> CREATOR = new Parcelable.Creator<Personaje>() {
        @Override
        public Personaje createFromParcel(Parcel source) {
            return new Personaje(source);
        }

        @Override
        public Personaje[] newArray(int size) {
            return new Personaje[size];
        }
    };


}
