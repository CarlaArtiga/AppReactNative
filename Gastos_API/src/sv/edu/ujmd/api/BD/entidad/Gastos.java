package sv.edu.ujmd.api.BD.entidad;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Gastos {
    private int id;
    private String descripcion;
    private double monto;
    private Date fecha;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String getFormattedFecha() {
        try {
            // Formatear la fecha en formato yyyy-MM-dd
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            return outputFormat.format(this.fecha);
        } catch (Exception e) {
            // Si ocurre un error al formatear la fecha, devolver la fecha original como un String
            return this.fecha.toString();
        }
    }
}
