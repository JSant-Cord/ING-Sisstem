/**
 * Asignatura: Ingeniería de Software I (4.º Semestre)
 * Estudiante A: Santiago Córdoba Camacho
 * Entidad: Reporte de Incendio Forestal
 */
public class Reporte {
    private String codigo_reporte;
    private String codigo_categoria;
    private String fecha_registro;
    private double latitud;
    private double longitud;
    private String descripcion;

    public Reporte(String codigo_reporte, String codigo_categoria, String fecha_registro, 
                   double latitud, double longitud, String descripcion) {
        this.codigo_reporte = codigo_reporte;
        this.codigo_categoria = codigo_categoria;
        this.fecha_registro = fecha_registro;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
    }

    // Métodos Getters y Setters
    public String getCodigo_reporte() { return codigo_reporte; }
    public void setCodigo_reporte(String codigo_reporte) { this.codigo_reporte = codigo_reporte; }

    public String getCodigo_categoria() { return codigo_categoria; }
    public void setCodigo_categoria(String codigo_categoria) { this.codigo_categoria = codigo_categoria; }

    public String getFecha_registro() { return fecha_registro; }
    public void setFecha_registro(String fecha_registro) { this.fecha_registro = fecha_registro; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    // reportes.txt
    public String aFormatoArchivo() {
        return codigo_reporte + ";" + codigo_categoria + ";" + fecha_registro + ";" + 
               latitud + ";" + longitud + ";" + descripcion;
    }

    @Override
    public String toString() {
        return "ID: " + codigo_reporte + " | Categoría: " + codigo_categoria +
               " | Fecha: " + fecha_registro + " | Lat: " + latitud +
               " | Lon: " + longitud + " | Desc: " + descripcion;
    }
}
