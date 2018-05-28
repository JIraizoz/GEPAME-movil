package es.jiraizoz.gepame.LD;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jesus on 11/05/2018.
 */

public class Incidencia implements Serializable{
    @SerializedName("tipoIncidencia")
    private String tipo;
    @SerializedName("idIncidencia")
    private String id;
    @SerializedName("utm")
    private String utm;
    @SerializedName("fecha")
    private Date fecha;
    @SerializedName("estado")
    private boolean estado;
    @SerializedName("descripcion")
    private String descripcion;


//    public Incidencia(String a1, String id, String utm, Calendar instance, boolean estado, String descripcion) {
//    }

    public Incidencia(String tipo, String id, String utm, Date fecha, boolean estado, String descripcion) {

        this.tipo = tipo;
        this.id = id;
        this.utm = utm;
        this.fecha = fecha;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUtm() {
        return utm;
    }

    public void setUtm(String utm) {
        this.utm = utm;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
