/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author usuario
 */
public class Turno {

    public String turno;
    public String modulo;
    public String hora;
    public String fecha;
    public String accionBanco;
    public String identificacion;
    public String correo;

    public Turno() {
        this.turno = "";
        this.modulo = "";
        this.hora = "";
        this.fecha = "";
        this.accionBanco = "";
        this.identificacion = "";
        this.correo = "";
}

public Turno(String turno, String modulo){
        this.turno = turno;
        this.modulo = modulo;
        this.hora = "";
        this.fecha = "";
        this.accionBanco = "";
        this.identificacion = "";
        this.correo = "";
    }
    
    public Turno(String turno, String modulo, String hora, String fecha, String accionBanco, String identificacion, String correo){
        this.turno = turno;
        this.modulo = modulo;
        this.hora = hora;
        this.fecha = fecha;
        this.accionBanco = accionBanco;
        this.identificacion = identificacion;
        this.correo = correo;
    }

    public String getAccionBanco() {
        return accionBanco;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTurno() {
        return turno;
    }

    public String getModulo() {
        return modulo;
    }

    public String getHora() {
        return hora;
    }

    public String getFecha() {
        return fecha;
    }

}
